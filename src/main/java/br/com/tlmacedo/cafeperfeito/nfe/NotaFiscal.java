package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoNfeDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilJSon;
import br.com.tlmacedo.cafeperfeito.service.ServiceValidarDado;
import br.com.tlmacedo.nfe.model.vo.*;
import br.com.tlmacedo.nfe.v400.EnviNfe_v400;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class NotaFiscal {

    private static EnviNfeVO enviNfeVO;
    private static SaidaProduto saidaProduto;
    private static SaidaProdutoNfe myNfe;
    private static SaidaProdutoDAO saidaProdutoDAO;
    private static TEnviNFe tEnviNFe;

    public static EnviNfeVO getEnviNfeVO(Long nPed) throws Exception {
        setSaidaProduto(getSaidaProdutoDAO().getById(SaidaProduto.class, nPed));
        gerarNotaFiscal();
        return getEnviNfeVO();
    }

    public static EnviNfeVO getEnviNfeVO(SaidaProduto saidaProduto) throws Exception {
        setSaidaProduto(saidaProduto);
        gerarNotaFiscal();
        return getEnviNfeVO();
    }

    public static void gerarNotaFiscal() throws Exception {
        ServiceUtilJSon.printJsonFromObject(getSaidaProduto().getContasAReceber(), "NewNotaFiscal002:");

        getEnviNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getEnviNfeVO().setIdLote(String.format("%015d", getSaidaProduto().idProperty().getValue()));
        getEnviNfeVO().setIndSinc(TCONFIG.getNfe().getIndSinc());

        NfeVO nfeVO = new NfeVO();
        getEnviNfeVO().setNfe(nfeVO);

        InfNfeVO infNfeVO = new InfNfeVO();
        nfeVO.setInfNfe(infNfeVO);

        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());

        getSaidaProduto().getSaidaProdutoNfeList().stream()
                .forEach(System.out::println);

        setMyNfe(getSaidaProduto().getSaidaProdutoNfeList().stream().filter(saidaProdutoNfe -> !saidaProdutoNfe.isCancelada()).findFirst().orElse(null));
        IdeVO ideVO = newNfeIde();
        infNfeVO.setIde(ideVO);
        infNfeVO.setId(ServiceValidarDado.gerarChaveNfe(ideVO));

        Empresa emissor = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));
        EnderVO emitEnderVO = new EnderVO();
        EmitVO emitVO = new EmitVO();
        infNfeVO.setEmit(emitVO);
        emitVO.setEnder(emitEnderVO);
        emitVO.setCnpj(emissor.getCnpj());
        emitVO.setxNome(emissor.getRazao(60));
        emitVO.setxFant(emissor.getFantasia(60));
        emitVO.setIE(emissor.getIe());
        emitVO.setCRT(String.valueOf(TCONFIG.getNfe().getCRT()));

        Endereco emissorEndereco = emissor.getEndereco(TipoEndereco.PRINCIPAL);
        emitEnderVO.setxLgr(emissorEndereco.getLogradouro());
        emitEnderVO.setNro(emissorEndereco.getNumero());
        emitEnderVO.setxCpl(emissorEndereco.getComplemento());
        emitEnderVO.setxBairro(emissorEndereco.getBairro());
        emitEnderVO.setcMun(emissorEndereco.getMunicipio().getIbge_codigo());
        emitEnderVO.setxMun(emissorEndereco.getMunicipio().getDescricao().toUpperCase());
        emitEnderVO.setUF(emissorEndereco.getMunicipio().getUf().getSigla().toUpperCase());
        emitEnderVO.setCEP(emissorEndereco.getCep());
        emitEnderVO.setcPais(TCONFIG.getNfe().getCPais());
        emitEnderVO.setxPais(TCONFIG.getNfe().getNPais());
        emitEnderVO.setFone(emissor.getFonePrincipal());

        Empresa destinatario = getSaidaProduto().getCliente();
        EnderVO destEnderVO = new EnderVO();
        DestVO destVO = new DestVO();
        infNfeVO.setDest(destVO);
        destVO.setEnder(destEnderVO);
        if (destinatario.isPessoaJuridica())
            destVO.setCnpj(destinatario.getCnpj());
        else
            destVO.setCpf(destinatario.getCnpj());
        destVO.setxNome(destinatario.getxNome(60));
        if (destinatario.getIe().length() > 0)
            destVO.setIndIEDest("1");
        else
            destVO.setIndIEDest("9");
        destVO.setIE(destinatario.getIe());
        if (destinatario.getiSuframa() != null)
            destVO.setISUF(destinatario.getiSuframa());
        if (destinatario.getiMunicpipal() != null)
            destVO.setIM(destinatario.getiMunicpipal());
        destVO.setEmail(destinatario.getEmailPrincipal());
        Endereco destinatarioEndereco = destinatario.getEndereco(TipoEndereco.PRINCIPAL);
        Endereco destinatarioEntrega = destinatario.getEndereco(TipoEndereco.ENTREGA);
        if (destinatarioEntrega != null) {
            EntregaVO entregaVO = new EntregaVO();
            infNfeVO.setEntrega(entregaVO);
            if (destinatario.isPessoaJuridica())
                entregaVO.setCnpj(destinatario.getCnpj());
            else
                entregaVO.setCpf(destinatario.getCnpj());
            entregaVO.setxNome(destinatario.getxNome(60));
            entregaVO.setxLgr(destinatarioEntrega.getLogradouro());
            entregaVO.setNro(destinatarioEntrega.getNumero());
            entregaVO.setxCpl(destinatarioEntrega.getComplemento());
            entregaVO.setxBairro(destinatarioEntrega.getBairro());
            entregaVO.setcMun(destinatarioEntrega.getMunicipio().getIbge_codigo());
            entregaVO.setxMun(destinatarioEntrega.getMunicipio().getDescricao().toUpperCase());
            entregaVO.setUF(destinatarioEntrega.getMunicipio().getUf().getSigla().toUpperCase());
            entregaVO.setCEP(destinatarioEntrega.getCep());
            entregaVO.setcPais(TCONFIG.getNfe().getCPais());
            entregaVO.setxPais(TCONFIG.getNfe().getNPais());
            entregaVO.setFone(destinatario.getFonePrincipal());
            entregaVO.setEmail(destinatario.getEmailPrincipal());
            if (!destinatario.ieProperty().getValue().equals(""))
                entregaVO.setIE(destinatario.ieProperty().getValue());
        }
        destEnderVO.setxLgr(destinatarioEndereco.getLogradouro());
        destEnderVO.setNro(destinatarioEndereco.getNumero());
        destEnderVO.setxCpl(destinatarioEndereco.getComplemento());
        destEnderVO.setxBairro(destinatarioEndereco.getBairro());
        destEnderVO.setcMun(destinatarioEndereco.getMunicipio().getIbge_codigo());
        destEnderVO.setxMun(destinatarioEndereco.getMunicipio().getDescricao().toUpperCase());
        destEnderVO.setUF(destinatarioEndereco.getMunicipio().getUf().getSigla().toUpperCase());
        destEnderVO.setCEP(destinatarioEndereco.getCep());
        destEnderVO.setcPais(TCONFIG.getNfe().getCPais());
        destEnderVO.setxPais(TCONFIG.getNfe().getNPais());
        destEnderVO.setFone(destinatario.getFonePrincipal());

        TotalVO totalVO = new TotalVO();
        infNfeVO.setTotal(totalVO);
        IcmsTotVO icmsTotVO = new IcmsTotVO();
        totalVO.setIcmsTot(icmsTotVO);

        if (getSaidaProduto().getSaidaProdutoProdutoList().size() > 0) {
            List<DetVO> detVOList = new ArrayList<>();

            for (int i = 0; i < getSaidaProduto().getSaidaProdutoProdutoList().size(); i++) {
                DetVO detVO = new DetVO();
                detVO.setnItem(String.valueOf(i + 1));
                detVO.setProd(newNfeProd(getSaidaProduto().getSaidaProdutoProdutoList().get(i)));
                detVO.setImposto(newNfeImposto(getSaidaProduto().getSaidaProdutoProdutoList().get(i).getProduto()));

                detVOList.add(detVO);

                if (detVO.getProd().getvProd() != null)
                    icmsTotVO.setvProd(icmsTotVO.getvProd().add(detVO.getProd().getvProd()));
                if (detVO.getProd().getvFrete() != null)
                    icmsTotVO.setvFrete(icmsTotVO.getvFrete().add(detVO.getProd().getvFrete()));
                if (detVO.getProd().getvSeg() != null)
                    icmsTotVO.setvSeg(icmsTotVO.getvSeg().add(detVO.getProd().getvSeg()));
                if (detVO.getProd().getvDesc() != null)
                    icmsTotVO.setvDesc(icmsTotVO.getvDesc().add(detVO.getProd().getvDesc()));
            }
            icmsTotVO.setvNF(icmsTotVO.getvProd().subtract(icmsTotVO.getvDesc()));

            infNfeVO.setDetList(detVOList);
        }

        TranspVO transpVO = new TranspVO();
        infNfeVO.setTransp(transpVO);
        transpVO.setModFrete(String.valueOf(getMyNfe().getModFrete().ordinal()));
        if (Integer.parseInt(transpVO.getModFrete()) < 3) {
            TransportaVO transportaVO = new TransportaVO();
            transpVO.setTransporta(transportaVO);
            Empresa tranportadora = getMyNfe().getTransportador();
            if (tranportadora.isPessoaJuridica())
                transportaVO.setCNPJ(tranportadora.getCnpj());
            else
                transportaVO.setCPF(tranportadora.getCnpj());
            transportaVO.setxNome(tranportadora.getxNome(60));

            if (!tranportadora.ieProperty().getValue().equals(""))
                transportaVO.setIE(tranportadora.ieProperty().getValue());

            Endereco end = tranportadora.getEndereco(TipoEndereco.PRINCIPAL);
            transportaVO.setxEnder(tranportadora.getEndereco(end));

            transportaVO.setxMun(end.getMunicipio().getDescricao().toUpperCase());
            transportaVO.setUF(end.getMunicipio().getUf().getSigla().toUpperCase());
        }

        if (totalVO.getIcmsTot().getvNF().compareTo(BigDecimal.ZERO) != 0) {
            CobrVO cobrVO = new CobrVO();
            infNfeVO.setCobr(cobrVO);
            FatVO fatVO = new FatVO();
            cobrVO.setFat(fatVO);
            Recebimento rec;
            if ((rec = getSaidaProduto().getContasAReceber().getRecebimentoList().stream().sorted(Comparator.comparing(Recebimento::getId).reversed())
                    .findFirst().orElse(null)) != null)
                fatVO.setnFat(rec.documentoProperty().getValue());

            if (fatVO.getnFat().equals(""))
                fatVO.setnFat(ideVO.getnNF());

            fatVO.setvOrig(icmsTotVO.getvProd());
            fatVO.setvDesc(icmsTotVO.getvDesc());
            fatVO.setvLiq(icmsTotVO.getvNF());
            if (getSaidaProduto().getContasAReceber() != null) {
                DupVO dupVO = new DupVO();
                cobrVO.getDupVOList().add(dupVO);
                dupVO.setnDup("001");
                dupVO.setdVenc(getSaidaProduto().getContasAReceber().dtVencimentoProperty().getValue());
                dupVO.setvDup(fatVO.getvLiq());
            }
        }
        PagVO pagVO = new PagVO();
        infNfeVO.setPag(pagVO);
        DetPagVO detPagVO = new DetPagVO();
        pagVO.setDetPag(detPagVO);
        detPagVO.setIndPag(getMyNfe().getPagamentoIndicador().getCod());
        detPagVO.settPag(getMyNfe().getPagamentoMeio().getCod());
        detPagVO.setvPag(icmsTotVO.getvNF());

        InfAdicVO infAdicVO = new InfAdicVO();
        infNfeVO.setInfAdic(infAdicVO);
        infAdicVO.setInfCpl(getMyNfe().informacaoAdicionalProperty().getValue());

        InfRespTecVO infRespTecVO = new InfRespTecVO();
        infNfeVO.setInfRespTec(infRespTecVO);
        infRespTecVO.setCnpj(TCONFIG.getNfe().getInfRespTec().getCnpj());
        infRespTecVO.setxContato(TCONFIG.getNfe().getInfRespTec().getXContato());
        infRespTecVO.setEmail(TCONFIG.getNfe().getInfRespTec().getEmail());
        infRespTecVO.setFone(TCONFIG.getNfe().getInfRespTec().getFone());

        settEnviNFe(new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe());
    }

    private static IdeVO newNfeIde() {
        IdeVO ideVO = new IdeVO();
        boolean salvar = false;
        if (getMyNfe() == null) {
            SaidaProdutoNfe nfeTemp = new SaidaProdutoNfeDAO().getAll(SaidaProdutoNfe.class, null, "numero DESC")
                    .stream().findFirst().orElse(null);
            setMyNfe(new SaidaProdutoNfe());
            getSaidaProduto().getSaidaProdutoNfeList().add(getMyNfe());
            if (nfeTemp == null) {
                nfeTemp.serieProperty().setValue(1);
                nfeTemp.numeroProperty().setValue(0);
            }
            getMyNfe().setImpressaoFinNFe(NfeImpressaoFinNFe.getList().stream()
                    .filter(impressaoFinNFe -> impressaoFinNFe.getCod() == TCONFIG.getNfe().getFinNFe())
                    .findFirst().orElse(NfeImpressaoFinNFe.NORMAL));
            getMyNfe().setPagamentoIndicador(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            getMyNfe().setPagamentoMeio(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            getMyNfe().setSaidaProduto(getSaidaProduto());
            getMyNfe().setStatusSefaz(NfeStatusSefaz.DIGITACAO);
            getMyNfe().setNaturezaOperacao(NfeDadosNaturezaOperacao.INTERNA);
            getMyNfe().setModelo(NfeDadosModelo.MOD55);
            getMyNfe().setModFrete(NfeTransporteModFrete.REMETENTE);
            getMyNfe().serieProperty().setValue(nfeTemp.serieProperty().getValue());
            getMyNfe().numeroProperty().setValue(nfeTemp.numeroProperty().getValue() + 1);
            getMyNfe().cobrancaNumeroProperty().setValue(getMyNfe().numeroProperty().getValue().toString());
            getMyNfe().setPagamentoIndicador(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            getMyNfe().setPagamentoMeio(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            getMyNfe().dtHoraEmissaoProperty().setValue(getSaidaProduto().getDtCadastro());
            getMyNfe().setInformacaoAdicional(String.format(TCONFIG.getNfe().getInfAdic(),
                    ServiceMascara.getMoeda(getSaidaProduto().getSaidaProdutoProdutoList().stream().map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                            .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add), 2),
                    (getSaidaProduto().getContasAReceber().dtVencimentoProperty().getValue() != null)
                            ? String.format(" dt. Venc.: %s",
                            getSaidaProduto().getContasAReceber().dtVencimentoProperty().getValue().format(Regex_Convert.DTF_DATA))
                            : "",
                    TCONFIG.getInfLoja().getBanco(),
                    TCONFIG.getInfLoja().getAgencia(), TCONFIG.getInfLoja().getContaCorrente())
                    .toUpperCase());
            getMyNfe().setXmlAssinatura(null);
            getMyNfe().setXmlProtNfe(null);
            if (getSaidaProduto().getDtSaida().compareTo(getSaidaProduto().getDtCadastro().toLocalDate()) <= 0) {
                getMyNfe().dtHoraSaidaProperty().setValue(getSaidaProduto().getDtCadastro());
            } else {
                getMyNfe().dtHoraSaidaProperty().setValue(getSaidaProduto().getDtSaida().atTime(8, 0, 0));
            }
            getMyNfe().setDestinoOperacao(NfeDadosDestinoOperacao.INTERNA);
            if (getSaidaProduto().getCliente().ieProperty().getValue().equals(""))
                getMyNfe().setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.FINAL);
            else
                getMyNfe().setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.NORMAL);
            getMyNfe().setIndicadorPresenca(NfeDadosIndicadorPresenca.TELEATENDIMENTO);
            salvar = true;
        }

        ideVO.setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
        ideVO.setNatOp(getMyNfe().getNaturezaOperacao().getDescricao());
        ideVO.setMod(getMyNfe().getModelo().getDescricao());
        ideVO.setSerie(getMyNfe().serieProperty().getValue().toString());
        ideVO.setnNF(getMyNfe().numeroProperty().getValue().toString());
        if (TCONFIG.getNfe().getTpAmb() == 2) {
            ideVO.setDhEmi(LocalDateTime.now());
            ideVO.setDhSaiEnt(LocalDateTime.now());
        } else {
            ideVO.setDhEmi(getMyNfe().dtHoraEmissaoProperty().getValue());
            ideVO.setDhSaiEnt(getMyNfe().dtHoraSaidaProperty().getValue());
        }
        ideVO.setTpNF(String.valueOf(TCONFIG.getNfe().getTpNF()));
        ideVO.setIdDest(String.valueOf(getMyNfe().getDestinoOperacao().getCod()));
        ideVO.setcMunFG(String.valueOf(TCONFIG.getInfLoja().getCMunFG()));
        ideVO.setTpImp((getMyNfe().getImpressaoTpImp() != null)
                ? String.valueOf(getMyNfe().getImpressaoTpImp().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpImp()));
        ideVO.setTpEmis((getMyNfe().getImpressaoTpEmis() != null)
                ? String.valueOf(getMyNfe().getImpressaoTpEmis().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpEmis()));
        ideVO.setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        ideVO.setFinNFe((getMyNfe().getImpressaoFinNFe() != null)
                ? String.valueOf(getMyNfe().getImpressaoFinNFe().getCod())
                : String.valueOf(NfeImpressaoFinNFe.NORMAL.getCod()));
        if (getMyNfe().getConsumidorFinal().equals(NfeDadosIndicadorConsumidorFinal.NORMAL)
                && getSaidaProduto().getCliente().ieProperty().getValue().equals(""))
            getMyNfe().setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.FINAL);
        ideVO.setIndFinal(String.valueOf(getMyNfe().getConsumidorFinal().getCod()));
        ideVO.setIndPres(String.valueOf(getMyNfe().getIndicadorPresenca().getCod()));
        ideVO.setProcEmi(String.valueOf(TCONFIG.getNfe().getProcEmi()));
        ideVO.setVerProc(TCONFIG.getNfe().getVerProc());

        if (salvar) {
            getMyNfe().chaveProperty().setValue(ServiceValidarDado.gerarChaveNfe(ideVO));
            try {
                getSaidaProdutoDAO().transactionBegin();
                setSaidaProduto(getSaidaProdutoDAO().setTransactionPersist(getSaidaProduto()));
                getSaidaProdutoDAO().transactionCommit();
            } catch (Exception e) {
                e.printStackTrace();
                getSaidaProdutoDAO().transactionRollback();
            }
        }

        return ideVO;
    }

    private static ProdVO newNfeProd(SaidaProdutoProduto saidaProdutoProduto) {
        Produto produto = saidaProdutoProduto.getProduto();
        ProdVO prodVO = new ProdVO();

        prodVO.setcProd(produto.getCodigo());
        prodVO.setcEAN(produto.getCEAN());
        prodVO.setxProd(produto.getDescricao());
        prodVO.setNCM(produto.getNcm());
        prodVO.setNVE("");
        prodVO.setCEST(produto.getCest());
        prodVO.setIndEscala("");
        prodVO.setCNPJFab("");
        prodVO.setcBenef("");
        prodVO.setEXTIPI("");
        prodVO.setCFOP("5" + saidaProdutoProduto.getTipoSaidaProduto().getCod());
        prodVO.setuCom(produto.getUnidadeComercial().getDescricao());
        BigDecimal qCom = new BigDecimal(saidaProdutoProduto.qtdProperty().getValue());
        prodVO.setqCom(qCom);
        prodVO.setvUnCom(saidaProdutoProduto.vlrVendaProperty().getValue());
        prodVO.setvProd(saidaProdutoProduto.vlrBrutoProperty().getValue());
        prodVO.setcEANTrib(produto.getCEAN());
        prodVO.setuTrib(produto.getUnidadeComercial().getDescricao());
        prodVO.setqTrib(qCom);
        prodVO.setvUnTrib(saidaProdutoProduto.vlrVendaProperty().getValue());
        prodVO.setvFrete(null);
        prodVO.setvSeg(null);
        if (saidaProdutoProduto.vlrDescontoProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
            prodVO.setvDesc(saidaProdutoProduto.vlrDescontoProperty().getValue());
        prodVO.setvOutro(null);
        prodVO.setIndTot("1");

        return prodVO;
    }

    private static ImpostoVO newNfeImposto(Produto produto) {
        ImpostoVO impostoVO = new ImpostoVO();
        if (produto.fiscalIcmsProperty().getValue() != null) {
            IcmsVO icmsVO = new IcmsVO();
            impostoVO.setIcms(icmsVO);
            switch (produto.fiscalIcmsProperty().getValue().idProperty().getValue().intValue()) {
                case 0:
                    Icms00VO icms00VO = new Icms00VO();
                    icmsVO.setIcms00(icms00VO);
                    icms00VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms00VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 10:
                    Icms10VO icms10VO = new Icms10VO();
                    icmsVO.setIcms10(icms10VO);
                    icms10VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms10VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 20:
                    Icms20VO icms20VO = new Icms20VO();
                    icmsVO.setIcms20(icms20VO);
                    icms20VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms20VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 30:
                    Icms30VO icms30VO = new Icms30VO();
                    icmsVO.setIcms30(icms30VO);
                    icms30VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms30VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 40:
                    Icms40_41_50VO icms404150VO = new Icms40_41_50VO();
                    icmsVO.setIcms40_41_50(icms404150VO);
                    icms404150VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms404150VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 50:
                    Icms51VO icms51VO = new Icms51VO();
                    icmsVO.setIcms51(icms51VO);
                    icms51VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms51VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 60:
                    Icms60VO icms60VO = new Icms60VO();
                    icmsVO.setIcms60(icms60VO);
                    icms60VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms60VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 70:
                    Icms70VO icms70VO = new Icms70VO();
                    icmsVO.setIcms70(icms70VO);
                    icms70VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms70VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
                case 90:
                    Icms90VO icms90VO = new Icms90VO();
                    icmsVO.setIcms90(icms90VO);
                    icms90VO.setOrig(produto.fiscalCstOrigemProperty().getValue().idProperty().getValue().toString());
                    icms90VO.setCST(String.format("%02d", produto.fiscalIcmsProperty().getValue().idProperty().getValue()));
                    break;
            }
        }

        if (produto.fiscalPisProperty().getValue() != null) {
            PisVO pisVO = new PisVO();
            impostoVO.setPis(pisVO);
            PisNTVO pisNTVO = new PisNTVO();
            pisVO.setPisNT(pisNTVO);
            pisNTVO.setCST(String.format("%02d", produto.fiscalPisProperty().getValue().idProperty().getValue()));
        }

        if (produto.fiscalCofinsProperty().getValue() != null) {
            CofinsVO cofinsVO = new CofinsVO();
            impostoVO.setCofins(cofinsVO);
            CofinsNTVO cofinsNTVO = new CofinsNTVO();
            cofinsVO.setCofinsNT(cofinsNTVO);
            cofinsNTVO.setCST(String.format("%02d", produto.fiscalCofinsProperty().getValue().idProperty().getValue()));
        }

        return impostoVO;
    }

    /**
     * Begin Getters and Setters
     */


    public static EnviNfeVO getEnviNfeVO() {
        return enviNfeVO;
    }

    public static void setEnviNfeVO(EnviNfeVO enviNfeVO) {
        NotaFiscal.enviNfeVO = enviNfeVO;
    }

    public static SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public static void setSaidaProduto(SaidaProduto saidaProduto) {
        NotaFiscal.saidaProduto = saidaProduto;
    }

    public static SaidaProdutoNfe getMyNfe() {
        return myNfe;
    }

    public static void setMyNfe(SaidaProdutoNfe myNfe) {
        NotaFiscal.myNfe = myNfe;
    }

    public static SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public static void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        NotaFiscal.saidaProdutoDAO = saidaProdutoDAO;
    }

    public static TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public static void settEnviNFe(TEnviNFe tEnviNFe) {
        NotaFiscal.tEnviNFe = tEnviNFe;
    }

    /**
     * END Getters and Setters
     */
}

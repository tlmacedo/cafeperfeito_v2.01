package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoNfeDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilXml;
import br.com.tlmacedo.cafeperfeito.service.ServiceValidarDado;
import br.com.tlmacedo.nfe.model.vo.*;
import br.com.tlmacedo.nfe.v400.EnviNfe_v400;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;
import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;


public class Nfe {

    private static SaidaProduto saidaProduto;
    private static SaidaProdutoDAO saidaProdutoDAO = new SaidaProdutoDAO();
    private static SaidaProdutoNfe myNFe = new SaidaProdutoNfe();
    private static boolean imprimirLote;
    private static int nItem = 0;
    private static DetVO detVO;
    private static TEnviNFe tEnviNFe;
    private static EnviNfeVO enviNfeVO = new EnviNfeVO();
    private static NfeVO nfeVO = new NfeVO();
    private static InfNfeVO infNfeVO = new InfNfeVO();
    private static IdeVO ideVO = new IdeVO();
    private static EmitVO emitVO = new EmitVO();
    private static DestVO destVO = new DestVO();
    private static EntregaVO entregaVO;
    private static List<DetVO> detVOList = new ArrayList<>();
    private static TotalVO totalVO = new TotalVO();
    private static IcmsTotVO icmsTotVO = new IcmsTotVO();
    private static TranspVO transpVO = new TranspVO();
    private static CobrVO cobrVO = new CobrVO();
    private static FatVO fatVO = new FatVO();
    private static PagVO pagVO = new PagVO();
    private static DetPagVO detPagVO = new DetPagVO();
    private static InfAdicVO infAdicVO = new InfAdicVO();
    private static InfRespTecVO infRespTecVO = new InfRespTecVO();

    public static String getNfe_Xml(Long nPed, boolean imprimirLote) throws Exception {
        setImprimirLote(imprimirLote);
        gerarNotaFiscal(getSaidaProdutoDAO().getById(SaidaProduto.class, nPed));
        return getNfe_Xml();
    }

    public static String getNfe_Xml(SaidaProduto saidaProduto, boolean imprimirLote) throws Exception {
        setImprimirLote(imprimirLote);
        gerarNotaFiscal(saidaProduto);
        return getNfe_Xml();
    }

    private static String getNfe_Xml(EnviNfeVO enviNfeVO, boolean imprimirLote) throws Exception {
        setImprimirLote(imprimirLote);
        setEnviNfeVO(enviNfeVO);
        return getNfe_Xml();
    }

    private static String getNfe_Xml() throws Exception {
        return ServiceUtilXml.objectToXml(gerartEnviNFe());
    }

    private static TEnviNFe gerartEnviNFe() throws Exception {
        return new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe();
    }


    public static void gerarNotaFiscal(SaidaProduto saidaProduto) {
        setSaidaProduto(saidaProduto);

        getEnviNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getEnviNfeVO().setIdLote(Nfe_Service.factoryIdLote(getSaidaProduto().idProperty().getValue()));
        getEnviNfeVO().setIndSinc(TCONFIG.getNfe().getIndSinc());
        getEnviNfeVO().setNfe(getNfeVO());

        getNfeVO().setInfNfe(getInfNfeVO());
        getInfNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getInfNfeVO().setIde(getIdeVO());
        ideVO_write();
        getInfNfeVO().setId(getMyNFe().chaveProperty().getValue());
        getInfNfeVO().setEmit(getEmitVO());
        emitVO_write();
        getInfNfeVO().setDest(getDestVO());
        destVO_write();
        Endereco destinatarioEntrega = null;
        if ((destinatarioEntrega = getSaidaProduto().clienteProperty().getValue().getEndereco(TipoEndereco.ENTREGA)) != null) {
            setEntregaVO(new EntregaVO());
            getInfNfeVO().setEntrega(getEntregaVO());
            entregaVO_write(destinatarioEntrega);
        }

        getInfNfeVO().setDetList(getDetVOList());
        getTotalVO().setIcmsTot(getIcmsTotVO());
        getInfNfeVO().setTotal(getTotalVO());
        detVOList_write();

        getTranspVO().setModFrete(String.valueOf(getMyNFe().getModFrete().ordinal()));
        getInfNfeVO().setTransp(getTranspVO());
        if (getMyNFe().getModFrete().ordinal() < 3)
            transpVO_write();

        if (getTotalVO().getIcmsTot().getvNF().compareTo(BigDecimal.ZERO) != 0)
            cobrVO_write();

        getPagVO().setDetPag(getDetPagVO());
        getInfNfeVO().setPag(getPagVO());
        detPagVO_write();

        getInfNfeVO().setInfAdic(getInfAdicVO());
        getInfAdicVO().setInfCpl(getMyNFe().informacaoAdicionalProperty().getValue());

        getInfNfeVO().setInfRespTec(getInfRespTecVO());
        infRespTecVO_write();

    }


    private static void ideVO_write() {
        boolean salvar = false;

        setMyNFe(getSaidaProduto().getSaidaProdutoNfeList().stream()
                .filter(saidaProdutoNfe1 -> !saidaProdutoNfe1.isCancelada()).findFirst().orElse(null));
        if (getMyNFe() == null) {
            salvar = true;
            setMyNFe(new SaidaProdutoNfe());
            getSaidaProduto().getSaidaProdutoNfeList().add(getMyNFe());


            getMyNFe().impressaoFinNFeProperty().setValue(NfeImpressaoFinNFe.getList().stream()
                    .filter(impressaoFinNFe -> impressaoFinNFe.getCod() == TCONFIG.getNfe().getFinNFe())
                    .findFirst().orElse(NfeImpressaoFinNFe.NORMAL));
            getMyNFe().pagamentoIndicadorProperty().setValue(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            getMyNFe().pagamentoMeioProperty().setValue(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            getMyNFe().saidaProdutoProperty().setValue(getSaidaProduto());
            getMyNFe().statusSefazProperty().setValue(NfeStatusSefaz.DIGITACAO);
            getMyNFe().naturezaOperacaoProperty().setValue(NfeDadosNaturezaOperacao.INTERNA);
            getMyNFe().modeloProperty().setValue(NfeDadosModelo.MOD55);
            getMyNFe().modFreteProperty().setValue(NfeTransporteModFrete.REMETENTE);

            Pair<Integer, Integer> pair = getNumeroSerieUltimaNfe();
            getMyNFe().serieProperty().setValue(pair.getKey());
            getMyNFe().numeroProperty().setValue(pair.getValue() + 1);

            getMyNFe().cobrancaNumeroProperty().setValue(getMyNFe().numeroProperty().getValue().toString());
            getMyNFe().pagamentoIndicadorProperty().setValue(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            getMyNFe().pagamentoMeioProperty().setValue(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            getMyNFe().dtHoraEmissaoProperty().setValue(getSaidaProduto().getDtCadastro());
            getMyNFe().informacaoAdicionalProperty().setValue(
                    String.format(TCONFIG.getNfe().getInfAdic(),
                            ServiceMascara.getMoeda(getSaidaProduto().getSaidaProdutoProdutoList().stream()
                                    .map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                                            .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add), 2),
                            (getSaidaProduto().contasAReceberProperty().getValue().dtVencimentoProperty().getValue() != null)
                                    ? String.format(" dt. Venc.: %s",
                                    getSaidaProduto().contasAReceberProperty().getValue()
                                            .dtVencimentoProperty().getValue().format(DTF_DATA))
                                    : "",
                            TCONFIG.getInfLoja().getBanco(),
                            TCONFIG.getInfLoja().getAgencia(), TCONFIG.getInfLoja().getContaCorrente())
                            .toUpperCase());
            getMyNFe().xmlAssinaturaProperty().setValue(null);
            getMyNFe().xmlProtNfeProperty().setValue(null);
            if (getSaidaProduto().dtSaidaProperty().getValue()
                    .compareTo(getSaidaProduto().dtCadastroProperty().getValue().toLocalDate()) <= 0) {
                getMyNFe().dtHoraSaidaProperty().setValue(getSaidaProduto()
                        .dtCadastroProperty().getValue());
            } else {
                getMyNFe().dtHoraSaidaProperty().setValue(getSaidaProduto()
                        .dtSaidaProperty().getValue().atTime(8, 0, 0));
            }
            getMyNFe().destinoOperacaoProperty().setValue(NfeDadosDestinoOperacao.INTERNA);
            if (getSaidaProduto().clienteProperty().getValue().ieProperty().getValue().equals(""))
                getMyNFe().consumidorFinalProperty().setValue(NfeDadosIndicadorConsumidorFinal.FINAL);
            else
                getMyNFe().consumidorFinalProperty().setValue(NfeDadosIndicadorConsumidorFinal.NORMAL);
            getMyNFe().indicadorPresencaProperty().setValue(NfeDadosIndicadorPresenca.TELEATENDIMENTO);
            getMyNFe().impressaoLtProdutoProperty().setValue(false);
        }

        getIdeVO().setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
        getIdeVO().setNatOp(getMyNFe().naturezaOperacaoProperty().getValue().getDescricao());
        getIdeVO().setMod(getMyNFe().modeloProperty().getValue().getDescricao());
        getIdeVO().setSerie(getMyNFe().serieProperty().getValue().toString());
        getIdeVO().setnNF(getMyNFe().numeroProperty().getValue().toString());
        getIdeVO().setDhEmi(getMyNFe().dtHoraEmissaoProperty().getValue());
        getIdeVO().setDhSaiEnt(getMyNFe().dtHoraSaidaProperty().getValue());
        getIdeVO().setTpNF(String.valueOf(TCONFIG.getNfe().getTpNF()));
        getIdeVO().setIdDest(String.valueOf(getMyNFe().destinoOperacaoProperty().getValue().getCod()));
        getIdeVO().setcMunFG(String.valueOf(TCONFIG.getInfLoja().getCMunFG()));
        getIdeVO().setTpImp((getMyNFe().impressaoTpImpProperty().getValue() != null)
                ? String.valueOf(getMyNFe().impressaoTpImpProperty().getValue().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpImp()));
        getIdeVO().setTpEmis((getMyNFe().impressaoTpEmisProperty().getValue() != null)
                ? String.valueOf(getMyNFe().impressaoTpEmisProperty().getValue().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpEmis()));
        getIdeVO().setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        getIdeVO().setFinNFe((getMyNFe().impressaoFinNFeProperty().getValue() != null)
                ? String.valueOf(getMyNFe().impressaoFinNFeProperty().getValue().getCod())
                : String.valueOf(NfeImpressaoFinNFe.NORMAL.getCod()));
        if (getMyNFe().consumidorFinalProperty().getValue().equals(NfeDadosIndicadorConsumidorFinal.NORMAL)
                && getSaidaProduto().clienteProperty().getValue().ieProperty().getValue().equals(""))
            getMyNFe().consumidorFinalProperty().setValue(NfeDadosIndicadorConsumidorFinal.FINAL);
        getIdeVO().setIndFinal(String.valueOf(getMyNFe().consumidorFinalProperty().getValue().getCod()));
        getIdeVO().setIndPres(String.valueOf(getMyNFe().indicadorPresencaProperty().getValue().getCod()));
        getIdeVO().setProcEmi(String.valueOf(TCONFIG.getNfe().getProcEmi()));
        getIdeVO().setVerProc(TCONFIG.getNfe().getVerProc());
        //setImprimirLote(getMyNFe().impressaoLtProdutoProperty().getValue());

        if (salvar) {
            getMyNFe().chaveProperty().setValue(ServiceValidarDado.gerarChaveNfe(getIdeVO()));
            try {
                getSaidaProdutoDAO().transactionBegin();
                setSaidaProduto(getSaidaProdutoDAO().setTransactionPersist(getSaidaProduto()));
                getSaidaProdutoDAO().transactionCommit();
            } catch (Exception e) {
                e.printStackTrace();
                getSaidaProdutoDAO().transactionRollback();
            }
        } else {
            getIdeVO().setcNF(getMyNFe().getChave().substring(35, 43));
            getIdeVO().setcDV(getMyNFe().getChave().substring(43, 44));
        }
    }

    private static void emitVO_write() {
        Empresa emissor = null;
        if (emissor == null)
            emissor = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));

        if (emissor.isPessoaJuridica())
            getEmitVO().setCnpj(emissor.cnpjProperty().getValue());
        else
            getEmitVO().setCpf(emissor.cnpjProperty().getValue());

        getEmitVO().setxNome(emissor.getRazao(60));
        getEmitVO().setxFant(emissor.getFantasia(60));
        getEmitVO().setIE(emissor.ieProperty().getValue());
        getEmitVO().setCRT(String.valueOf(TCONFIG.getNfe().getCRT()));

        EnderVO emitEnderVO = new EnderVO();
        getEmitVO().setEnder(emitEnderVO);
        Endereco emissorEndereco = emissor.getEndereco(TipoEndereco.PRINCIPAL);

        emitEnderVO.setxLgr(emissorEndereco.logradouroProperty().getValue());
        emitEnderVO.setNro(emissorEndereco.numeroProperty().getValue());
        emitEnderVO.setxCpl(emissorEndereco.complementoProperty().getValue());
        emitEnderVO.setxBairro(emissorEndereco.bairroProperty().getValue());
        emitEnderVO.setcMun(emissorEndereco.municipioProperty().getValue().ibge_codigoProperty().getValue());
        emitEnderVO.setxMun(emissorEndereco.municipioProperty().getValue().descricaoProperty().getValue().toUpperCase());
        emitEnderVO.setUF(emissorEndereco.municipioProperty().getValue().ufProperty().getValue().siglaProperty().getValue().toUpperCase());
        emitEnderVO.setCEP(emissorEndereco.cepProperty().getValue());
        emitEnderVO.setcPais(TCONFIG.getNfe().getCPais());
        emitEnderVO.setxPais(TCONFIG.getNfe().getNPais());
        emitEnderVO.setFone(emissor.getFonePrincipal());
    }

    private static void destVO_write() {
        Empresa destinatario = null;
        if (destinatario == null)
            if ((destinatario = getSaidaProduto().clienteProperty().getValue()) == null)
                destinatario = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));

        if (destinatario.isPessoaJuridica())
            getDestVO().setCnpj(destinatario.cnpjProperty().getValue());
        else
            getDestVO().setCpf(destinatario.cnpjProperty().getValue());

        getDestVO().setxNome(destinatario.getxNome(60));

        if (destinatario.ieProperty().getValue().length() > 0)
            getDestVO().setIndIEDest("1");
        else
            getDestVO().setIndIEDest("9");

        getDestVO().setIE(destinatario.ieProperty().getValue());

        if (destinatario.iSuframaProperty().getValue() != null)
            getDestVO().setISUF(destinatario.iSuframaProperty().getValue());

        if (destinatario.iMunicpipalProperty().getValue() != null)
            getDestVO().setIM(destinatario.iMunicpipalProperty().getValue());

        getDestVO().setEmail(destinatario.getEmailPrincipal());

        EnderVO destEnderVO = new EnderVO();
        getDestVO().setEnder(destEnderVO);
        Endereco destinatarioEndereco = destinatario.getEndereco(TipoEndereco.PRINCIPAL);

        destEnderVO.setxLgr(destinatarioEndereco.logradouroProperty().getValue());
        destEnderVO.setNro(destinatarioEndereco.numeroProperty().getValue());
        destEnderVO.setxCpl(destinatarioEndereco.complementoProperty().getValue());
        destEnderVO.setxBairro(destinatarioEndereco.bairroProperty().getValue());
        destEnderVO.setcMun(destinatarioEndereco.municipioProperty().getValue().ibge_codigoProperty().getValue());
        destEnderVO.setxMun(destinatarioEndereco.municipioProperty().getValue().descricaoProperty().getValue().toUpperCase());
        destEnderVO.setUF(destinatarioEndereco.municipioProperty().getValue().ufProperty().getValue().siglaProperty().getValue().toUpperCase());
        destEnderVO.setCEP(destinatarioEndereco.cepProperty().getValue());
        destEnderVO.setcPais(TCONFIG.getNfe().getCPais());
        destEnderVO.setxPais(TCONFIG.getNfe().getNPais());
        destEnderVO.setFone(destinatario.getFonePrincipal());
    }

    private static void entregaVO_write(Endereco destinatarioEntrega) {
        Empresa destinatario = getSaidaProduto().clienteProperty().getValue();
        if (destinatario == null)
            destinatario = getSaidaProduto().clienteProperty().getValue();

        if (destinatario.isPessoaJuridica())
            getEntregaVO().setCnpj(destinatario.cnpjProperty().getValue());
        else
            getEntregaVO().setCpf(destinatario.cnpjProperty().getValue());
        getEntregaVO().setxNome(destinatario.getxNome(60));

        getEntregaVO().setxLgr(destinatarioEntrega.logradouroProperty().getValue());
        getEntregaVO().setNro(destinatarioEntrega.numeroProperty().getValue());
        getEntregaVO().setxCpl(destinatarioEntrega.complementoProperty().getValue());
        getEntregaVO().setxBairro(destinatarioEntrega.bairroProperty().getValue());
        getEntregaVO().setcMun(destinatarioEntrega.municipioProperty().getValue().ibge_codigoProperty().getValue());
        getEntregaVO().setxMun(destinatarioEntrega.municipioProperty().getValue().descricaoProperty().getValue().toUpperCase());
        getEntregaVO().setUF(destinatarioEntrega.municipioProperty().getValue().ufProperty().getValue().siglaProperty().getValue().toUpperCase());
        getEntregaVO().setCEP(destinatarioEntrega.cepProperty().getValue());
        getEntregaVO().setcPais(TCONFIG.getNfe().getCPais());
        getEntregaVO().setxPais(TCONFIG.getNfe().getNPais());
        getEntregaVO().setFone(destinatario.getFonePrincipal());
    }

    private static void detVOList_write() {
        if (getSaidaProduto().getSaidaProdutoProdutoList().size() <= 0) return;
        getSaidaProduto().getSaidaProdutoProdutoList().stream()
                .sorted(Comparator.comparing(SaidaProdutoProduto::getProdId)
                        .thenComparing(SaidaProdutoProduto::getLote))
                .collect(Collectors.groupingBy(SaidaProdutoProduto::getProdId))
                .forEach((aLong, saidaProdutoProdutos) -> {
                    setDetVO(null);
                    for (SaidaProdutoProduto saidProd : saidaProdutoProdutos) {
                        new_ProdVO(saidProd);
                    }
                });
        getIcmsTotVO().setvNF(getIcmsTotVO().getvProd().subtract(getIcmsTotVO().getvDesc()));
        //getTotalVO().getIcmsTot().setvNF(null);

    }

    private static void new_ProdVO(SaidaProdutoProduto saidProd) {
        ProdVO prodVO = null;
        Produto produto = saidProd.produtoProperty().getValue();
        if (isImprimirLote()) {
            setDetVO(null);
        } else {
            getDetVO();
        }


        if (getDetVO() == null) {
            setDetVO(new DetVO());
            setnItem(getnItem() + 1);
            getDetVO().setnItem(String.valueOf(getnItem()));
            prodVO = new ProdVO();
            getDetVO().setProd(prodVO);
            prodImposto_write(produto);
            getDetVOList().add(getDetVO());
        }
        if (prodVO == null) return;

        prodVO.setcProd(produto.codigoProperty().getValue());
        prodVO.setcEAN(produto.getCEAN());
        String prodString = produto.descricaoProperty().getValue();
        if (isImprimirLote()) {
            prodString = String.format("%s Lt[%s] val.:%s",
                    prodString,
                    saidProd.loteProperty().getValue(),
                    saidProd.dtValidadeProperty().getValue().format(DTF_DATA));
        }
        prodVO.setxProd(prodString);
        prodVO.setNCM(produto.ncmProperty().getValue());
        prodVO.setNVE("");
        prodVO.setCEST(produto.cestProperty().getValue());
        prodVO.setIndEscala("");
        prodVO.setCNPJFab("");
        prodVO.setcBenef("");
        prodVO.setEXTIPI("");
        prodVO.setCFOP("5" + saidProd.codigoCFOPProperty().getValue().getCod());
        prodVO.setuCom(produto.unidadeComercialProperty().getValue().getDescricao());
        BigDecimal qCom = new BigDecimal(saidProd.qtdProperty().getValue());
        prodVO.setqCom(qCom);
        prodVO.setvUnCom(saidProd.vlrUnitarioProperty().getValue());
        prodVO.setvProd(saidProd.vlrBrutoProperty().getValue());
        prodVO.setcEANTrib(produto.getCEAN());
        prodVO.setuTrib(produto.unidadeComercialProperty().getValue().getDescricao());
        prodVO.setqTrib(qCom);
        prodVO.setvUnTrib(saidProd.vlrUnitarioProperty().getValue());
        prodVO.setvFrete(null);
        prodVO.setvSeg(null);
        if (saidProd.vlrDescontoProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
            prodVO.setvDesc(saidProd.vlrDescontoProperty().getValue());
        prodVO.setvOutro(null);
        prodVO.setIndTot("1");

        if (getDetVO().getProd().getvProd() != null)
            getIcmsTotVO().setvProd(getIcmsTotVO().getvProd().add(getDetVO().getProd().getvProd()));
        if (getDetVO().getProd().getvFrete() != null)
            getIcmsTotVO().setvFrete(getIcmsTotVO().getvFrete().add(getDetVO().getProd().getvFrete()));
        if (getDetVO().getProd().getvSeg() != null)
            getIcmsTotVO().setvSeg(getIcmsTotVO().getvSeg().add(getDetVO().getProd().getvSeg()));
        if (getDetVO().getProd().getvDesc() != null)
            getIcmsTotVO().setvDesc(getIcmsTotVO().getvDesc().add(getDetVO().getProd().getvDesc()));

    }

    private static void prodImposto_write(Produto produto) {
        ImpostoVO impostoVO = new ImpostoVO();
        getDetVO().setImposto(impostoVO);
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
    }

    private static void transpVO_write() {
        TransportaVO transportaVO = new TransportaVO();
        getTranspVO().setTransporta(transportaVO);
        Empresa transportadora = getMyNFe().getTransportador();
        if (transportadora.isPessoaJuridica())
            transportaVO.setCNPJ(transportadora.getCnpj());
        else
            transportaVO.setCPF(transportadora.getCnpj());
        transportaVO.setxNome(transportadora.getxNome(60));

        if (!transportadora.ieProperty().getValue().equals(""))
            transportaVO.setIE(transportadora.ieProperty().getValue());

        Endereco end = transportadora.getEndereco(TipoEndereco.PRINCIPAL);
        transportaVO.setxEnder(transportadora.getEndereco(end));
        transportaVO.setxMun(end.municipioProperty().getValue().descricaoProperty().getValue().toUpperCase());
        transportaVO.setUF(end.municipioProperty().getValue().ufProperty().getValue().siglaProperty().getValue().toUpperCase());
    }

    private static void cobrVO_write() {
        getInfNfeVO().setCobr(getCobrVO());
        getCobrVO().setFat(getFatVO());

        getFatVO().setnFat(!getSaidaProduto().idProperty().getValue().toString().equals("")
                ? getSaidaProduto().idProperty().getValue().toString()
                : getIdeVO().getnNF());
        getFatVO().setvOrig(getIcmsTotVO().getvProd());
        getFatVO().setvDesc(getIcmsTotVO().getvDesc());
        getFatVO().setvLiq(getIcmsTotVO().getvNF());
    }

    private static void detPagVO_write() {
        getDetPagVO().setIndPag(getMyNFe().pagamentoIndicadorProperty().getValue().getCod());
        getDetPagVO().settPag(getMyNFe().pagamentoMeioProperty().getValue().getCod());
        getDetPagVO().setvPag(getIcmsTotVO().getvNF());
    }

    private static void infRespTecVO_write() {
        getInfRespTecVO().setCnpj(TCONFIG.getNfe().getInfRespTec().getCnpj());
        getInfRespTecVO().setxContato(TCONFIG.getNfe().getInfRespTec().getXContato());
        getInfRespTecVO().setEmail(TCONFIG.getNfe().getInfRespTec().getEmail());
        getInfRespTecVO().setFone(TCONFIG.getNfe().getInfRespTec().getFone());
    }

    private static Pair<Integer, Integer> getNumeroSerieUltimaNfe() {
        SaidaProdutoNfe nfeTemp = new SaidaProdutoNfeDAO().getAll(SaidaProdutoNfe.class, null, "numero DESC")
                .stream().findFirst().orElse(null);
        if (nfeTemp != null)
            return new Pair<>(nfeTemp.serieProperty().getValue(), nfeTemp.numeroProperty().getValue());
        else
            return new Pair<>(1, 0);
    }


    public static SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public static void setSaidaProduto(SaidaProduto saidaProduto) {
        Nfe.saidaProduto = saidaProduto;
    }

    public static SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public static void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        Nfe.saidaProdutoDAO = saidaProdutoDAO;
    }

    public static SaidaProdutoNfe getMyNFe() {
        return myNFe;
    }

    public static void setMyNFe(SaidaProdutoNfe myNFe) {
        Nfe.myNFe = myNFe;
    }

    public static boolean isImprimirLote() {
        return imprimirLote;
    }

    public static void setImprimirLote(boolean imprimirLote) {
        Nfe.imprimirLote = imprimirLote;
    }

    public static int getnItem() {
        return nItem;
    }

    public static void setnItem(int nItem) {
        Nfe.nItem = nItem;
    }

    public static DetVO getDetVO() {
        return detVO;
    }

    public static void setDetVO(DetVO detVO) {
        Nfe.detVO = detVO;
    }

    public static TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public static void settEnviNFe(TEnviNFe tEnviNFe) {
        Nfe.tEnviNFe = tEnviNFe;
    }

    public static EnviNfeVO getEnviNfeVO() {
        return enviNfeVO;
    }

    public static void setEnviNfeVO(EnviNfeVO enviNfeVO) {
        Nfe.enviNfeVO = enviNfeVO;
    }

    public static NfeVO getNfeVO() {
        return nfeVO;
    }

    public static void setNfeVO(NfeVO nfeVO) {
        Nfe.nfeVO = nfeVO;
    }

    public static InfNfeVO getInfNfeVO() {
        return infNfeVO;
    }

    public static void setInfNfeVO(InfNfeVO infNfeVO) {
        Nfe.infNfeVO = infNfeVO;
    }

    public static IdeVO getIdeVO() {
        return ideVO;
    }

    public static void setIdeVO(IdeVO ideVO) {
        Nfe.ideVO = ideVO;
    }

    public static EmitVO getEmitVO() {
        return emitVO;
    }

    public static void setEmitVO(EmitVO emitVO) {
        Nfe.emitVO = emitVO;
    }

    public static DestVO getDestVO() {
        return destVO;
    }

    public static void setDestVO(DestVO destVO) {
        Nfe.destVO = destVO;
    }

    public static EntregaVO getEntregaVO() {
        return entregaVO;
    }

    public static void setEntregaVO(EntregaVO entregaVO) {
        Nfe.entregaVO = entregaVO;
    }

    public static List<DetVO> getDetVOList() {
        return detVOList;
    }

    public static void setDetVOList(List<DetVO> detVOList) {
        Nfe.detVOList = detVOList;
    }

    public static TotalVO getTotalVO() {
        return totalVO;
    }

    public static void setTotalVO(TotalVO totalVO) {
        Nfe.totalVO = totalVO;
    }

    public static IcmsTotVO getIcmsTotVO() {
        return icmsTotVO;
    }

    public static void setIcmsTotVO(IcmsTotVO icmsTotVO) {
        Nfe.icmsTotVO = icmsTotVO;
    }

    public static TranspVO getTranspVO() {
        return transpVO;
    }

    public static void setTranspVO(TranspVO transpVO) {
        Nfe.transpVO = transpVO;
    }

    public static CobrVO getCobrVO() {
        return cobrVO;
    }

    public static void setCobrVO(CobrVO cobrVO) {
        Nfe.cobrVO = cobrVO;
    }

    public static FatVO getFatVO() {
        return fatVO;
    }

    public static void setFatVO(FatVO fatVO) {
        Nfe.fatVO = fatVO;
    }

    public static PagVO getPagVO() {
        return pagVO;
    }

    public static void setPagVO(PagVO pagVO) {
        Nfe.pagVO = pagVO;
    }

    public static DetPagVO getDetPagVO() {
        return detPagVO;
    }

    public static void setDetPagVO(DetPagVO detPagVO) {
        Nfe.detPagVO = detPagVO;
    }

    public static InfAdicVO getInfAdicVO() {
        return infAdicVO;
    }

    public static void setInfAdicVO(InfAdicVO infAdicVO) {
        Nfe.infAdicVO = infAdicVO;
    }

    public static InfRespTecVO getInfRespTecVO() {
        return infRespTecVO;
    }

    public static void setInfRespTecVO(InfRespTecVO infRespTecVO) {
        Nfe.infRespTecVO = infRespTecVO;
    }
}

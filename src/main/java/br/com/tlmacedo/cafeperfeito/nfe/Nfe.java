package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoNfeDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Endereco;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoNfe;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.ServiceValidarDado;
import br.com.tlmacedo.nfe.model.vo.*;
import br.com.tlmacedo.nfe.v400.EnviNfe_v400;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class Nfe {

    private EnviNfeVO enviNfeVO;
    private SaidaProduto saidaProduto;
    private SaidaProdutoNfe saidaProdutoNfe;
    private SaidaProdutoDAO saidaProdutoDAO;
    private TEnviNFe tEnviNFe;

    public Nfe(SaidaProduto saidaProduto) {
        setSaidaProduto(saidaProduto);
        setSaidaProdutoDAO(new SaidaProdutoDAO());
    }

    //    private TEnviNFe gerartEnviNFe() {
//        return new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe();
//    }
//
//    public String getXml(EnviNfeVO enviNfeVO) throws JAXBException {
//        setEnviNfeVO(enviNfeVO);
//        return ServiceUtilXml.objectToXml(gerartEnviNFe());
//    }
//
//    public String getXml(SaidaProduto saidaProduto) throws Exception {
//        return getXml(getEnviNfeVO(saidaProduto));
//    }
//
//    public EnviNfeVO get_EnviNfeVO(Long nPed) {
//        setSaidaProdutoDAO(new SaidaProdutoDAO());
//        setSaidaProduto(getSaidaProdutoDAO().getById(SaidaProduto.class, nPed));
//        return getEnviNfeVO();
//    }
//
//    public EnviNfeVO get_EnviNfeVO(SaidaProduto saidaProduto) {
//        setSaidaProduto(saidaProduto);
//        return getEnviNfeVO();
//    }
//
//    public EnviNfeVO get_EnviNfeVO() {
//        gerarNotaFiscal();
//        return getEnviNfeVO();
//    }

    public void gerarNotaFiscal() {
        setEnviNfeVO(new EnviNfeVO());

        getEnviNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getEnviNfeVO().setIdLote(Nfe_Service.factoryIdLote(getSaidaProduto().idProperty().getValue()));
        getEnviNfeVO().setIndSinc(TCONFIG.getNfe().getIndSinc());


        NfeVO nfeVO = new_NfeVO();
        getEnviNfeVO().setNfe(nfeVO);

        InfNfeVO infNfeVO = new_InfNfeVO();
        nfeVO.setInfNfe(infNfeVO);

        IdeVO ideVO = new_IdeVO();
        infNfeVO.setId(ServiceValidarDado.gerarChaveNfe(ideVO));
        infNfeVO.setIde(ideVO);

        EmitVO emitVO = new_EmitVO(null);
        infNfeVO.setEmit(emitVO);

        DestVO destVO = new_DestVO(getSaidaProduto().clienteProperty().getValue());
        infNfeVO.setDest(destVO);

        Endereco destinatarioEntrega = null;
        if ((destinatarioEntrega = getSaidaProduto().clienteProperty().getValue().getEndereco(TipoEndereco.ENTREGA)) != null) {
            EntregaVO entregaVO = new_EntregaVO(destinatarioEntrega, getSaidaProduto().clienteProperty().getValue());
            infNfeVO.setEntrega(entregaVO);
        }

        TotalVO totalVO = new TotalVO();
        infNfeVO.setTotal(totalVO);
        IcmsTotVO icmsTotVO = new IcmsTotVO();
        totalVO.setIcmsTot(icmsTotVO);

        List<DetVO> detVOList;
        if ((detVOList = new_DetVOList(totalVO)) != null)
            infNfeVO.setDetList(detVOList);

        //************************************************************
        //************************************************************
        //************************************************************
        //************************************************************
        //************************DETVO
        //************************************************************
        //************************************************************
        //************************************************************
        //************************************************************
        //************************************************************
        //************************************************************


        settEnviNFe(new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe());

    }

    public NfeVO new_NfeVO() {
        return new NfeVO();
    }

    public IdeVO new_IdeVO() {
        IdeVO ideVO = new IdeVO();
        boolean salvar = false;
        SaidaProdutoNfe saidaProdutoNfe = getSaidaProduto().getSaidaProdutoNfeList().stream()
                .filter(saidaProdutoNfe1 -> !saidaProdutoNfe1.isCancelada()).findFirst().orElse(null);
        if (saidaProdutoNfe == null) {
            SaidaProdutoNfe nfeTemp = new SaidaProdutoNfeDAO().getAll(SaidaProdutoNfe.class, null, "numero DESC")
                    .stream().findFirst().orElse(null);
            saidaProdutoNfe = new SaidaProdutoNfe();
            getSaidaProduto().getSaidaProdutoNfeList().add(saidaProdutoNfe);
            if (nfeTemp == null) {
                nfeTemp.serieProperty().setValue(1);
                nfeTemp.numeroProperty().setValue(0);
            }
            saidaProdutoNfe.impressaoFinNFeProperty().setValue(NfeImpressaoFinNFe.getList().stream()
                    .filter(impressaoFinNFe -> impressaoFinNFe.getCod() == TCONFIG.getNfe().getFinNFe())
                    .findFirst().orElse(NfeImpressaoFinNFe.NORMAL));
            saidaProdutoNfe.pagamentoIndicadorProperty().setValue(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            saidaProdutoNfe.pagamentoMeioProperty().setValue(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            saidaProdutoNfe.saidaProdutoProperty().setValue(getSaidaProduto());
            saidaProdutoNfe.statusSefazProperty().setValue(NfeStatusSefaz.DIGITACAO);
            saidaProdutoNfe.naturezaOperacaoProperty().setValue(NfeDadosNaturezaOperacao.INTERNA);
            saidaProdutoNfe.modeloProperty().setValue(NfeDadosModelo.MOD55);
            saidaProdutoNfe.modFreteProperty().setValue(NfeTransporteModFrete.REMETENTE);
            saidaProdutoNfe.serieProperty().setValue(nfeTemp.serieProperty().getValue());
            saidaProdutoNfe.numeroProperty().setValue(nfeTemp.numeroProperty().getValue() + 1);
            saidaProdutoNfe.cobrancaNumeroProperty().setValue(saidaProdutoNfe.numeroProperty().getValue().toString());
            saidaProdutoNfe.pagamentoIndicadorProperty().setValue(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            saidaProdutoNfe.pagamentoMeioProperty().setValue(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            saidaProdutoNfe.dtHoraEmissaoProperty().setValue(getSaidaProduto().getDtCadastro());
            saidaProdutoNfe.informacaoAdicionalProperty().setValue(
                    String.format(TCONFIG.getNfe().getInfAdic(),
                            ServiceMascara.getMoeda(getSaidaProduto().getSaidaProdutoProdutoList().stream()
                                    .map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                                            .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add), 2),
                            (getSaidaProduto().contasAReceberProperty().getValue().dtVencimentoProperty().getValue() != null)
                                    ? String.format(" dt. Venc.: %s",
                                    getSaidaProduto().contasAReceberProperty().getValue()
                                            .dtVencimentoProperty().getValue().format(Regex_Convert.DTF_DATA))
                                    : "",
                            TCONFIG.getInfLoja().getBanco(),
                            TCONFIG.getInfLoja().getAgencia(), TCONFIG.getInfLoja().getContaCorrente())
                            .toUpperCase());
            saidaProdutoNfe.xmlAssinaturaProperty().setValue(null);
            saidaProdutoNfe.xmlProtNfeProperty().setValue(null);
            if (getSaidaProduto().dtSaidaProperty().getValue()
                    .compareTo(getSaidaProduto().dtCadastroProperty().getValue().toLocalDate()) <= 0) {
                saidaProdutoNfe.dtHoraSaidaProperty().setValue(getSaidaProduto()
                        .dtCadastroProperty().getValue());
            } else {
                saidaProdutoNfe.dtHoraSaidaProperty().setValue(getSaidaProduto()
                        .dtSaidaProperty().getValue().atTime(8, 0, 0));
            }
            saidaProdutoNfe.destinoOperacaoProperty().setValue(NfeDadosDestinoOperacao.INTERNA);
            if (getSaidaProduto().clienteProperty().getValue().ieProperty().getValue().equals(""))
                saidaProdutoNfe.consumidorFinalProperty().setValue(NfeDadosIndicadorConsumidorFinal.FINAL);
            else
                saidaProdutoNfe.consumidorFinalProperty().setValue(NfeDadosIndicadorConsumidorFinal.NORMAL);
            saidaProdutoNfe.indicadorPresencaProperty().setValue(NfeDadosIndicadorPresenca.TELEATENDIMENTO);
            salvar = true;
        }

        ideVO.setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
        ideVO.setNatOp(saidaProdutoNfe.naturezaOperacaoProperty().getValue().getDescricao());
        ideVO.setMod(saidaProdutoNfe.modeloProperty().getValue().getDescricao());
        ideVO.setSerie(saidaProdutoNfe.serieProperty().getValue().toString());
        ideVO.setnNF(saidaProdutoNfe.numeroProperty().getValue().toString());
        ideVO.setDhEmi(saidaProdutoNfe.dtHoraEmissaoProperty().getValue());
        ideVO.setDhSaiEnt(saidaProdutoNfe.dtHoraSaidaProperty().getValue());
        ideVO.setTpNF(String.valueOf(TCONFIG.getNfe().getTpNF()));
        ideVO.setIdDest(String.valueOf(saidaProdutoNfe.destinoOperacaoProperty().getValue().getCod()));
        ideVO.setcMunFG(String.valueOf(TCONFIG.getInfLoja().getCMunFG()));
        ideVO.setTpImp((saidaProdutoNfe.impressaoTpImpProperty().getValue() != null)
                ? String.valueOf(saidaProdutoNfe.impressaoTpImpProperty().getValue().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpImp()));
        ideVO.setTpEmis((saidaProdutoNfe.impressaoTpEmisProperty().getValue() != null)
                ? String.valueOf(saidaProdutoNfe.impressaoTpEmisProperty().getValue().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpEmis()));
        ideVO.setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        ideVO.setFinNFe((saidaProdutoNfe.impressaoFinNFeProperty().getValue() != null)
                ? String.valueOf(saidaProdutoNfe.impressaoFinNFeProperty().getValue().getCod())
                : String.valueOf(NfeImpressaoFinNFe.NORMAL.getCod()));
        if (saidaProdutoNfe.consumidorFinalProperty().getValue().equals(NfeDadosIndicadorConsumidorFinal.NORMAL)
                && getSaidaProduto().clienteProperty().getValue().ieProperty().getValue().equals(""))
            saidaProdutoNfe.consumidorFinalProperty().setValue(NfeDadosIndicadorConsumidorFinal.FINAL);
        ideVO.setIndFinal(String.valueOf(saidaProdutoNfe.consumidorFinalProperty().getValue().getCod()));
        ideVO.setIndPres(String.valueOf(saidaProdutoNfe.indicadorPresencaProperty().getValue().getCod()));
        ideVO.setProcEmi(String.valueOf(TCONFIG.getNfe().getProcEmi()));
        ideVO.setVerProc(TCONFIG.getNfe().getVerProc());

        if (salvar) {
            saidaProdutoNfe.chaveProperty().setValue(ServiceValidarDado.gerarChaveNfe(ideVO));
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

    public InfNfeVO new_InfNfeVO() {
        InfNfeVO infNfeVO = new InfNfeVO();
        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());
        infNfeVO.setEmit(new_EmitVO(null));

        return infNfeVO;
    }

    public EmitVO new_EmitVO(Empresa emissor) {
        EmitVO emitVO = new EmitVO();
        if (emissor == null)
            emissor = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));
        if (emissor.isPessoaJuridica())
            emitVO.setCnpj(emissor.cnpjProperty().getValue());
        else
            emitVO.setCpf(emissor.cnpjProperty().getValue());
        emitVO.setxNome(emissor.getRazao(60));
        emitVO.setxFant(emissor.getFantasia(60));
        emitVO.setIE(emissor.ieProperty().getValue());
        emitVO.setCRT(String.valueOf(TCONFIG.getNfe().getCRT()));

        EnderVO emitEnderVO = new EnderVO();
        emitVO.setEnder(emitEnderVO);
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

        return emitVO;
    }

    public DestVO new_DestVO(Empresa destinatario) {
        DestVO destVO = new DestVO();
//        if (destinatario == null)
        if (destinatario == null)
            if ((destinatario = getSaidaProduto().clienteProperty().getValue()) == null)
                destinatario = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));
        if (destinatario.isPessoaJuridica())
            destVO.setCnpj(destinatario.cnpjProperty().getValue());
        else
            destVO.setCpf(destinatario.cnpjProperty().getValue());
        destVO.setxNome(destinatario.getxNome(60));
        if (destinatario.ieProperty().getValue().length() > 0)
            destVO.setIndIEDest("1");
        else
            destVO.setIndIEDest("9");
        destVO.setIE(destinatario.ieProperty().getValue());
        if (destinatario.iSuframaProperty().getValue() != null)
            destVO.setISUF(destinatario.iSuframaProperty().getValue());
        if (destinatario.iMunicpipalProperty().getValue() != null)
            destVO.setIM(destinatario.iMunicpipalProperty().getValue());
        destVO.setEmail(destinatario.getEmailPrincipal());


        EnderVO destEnderVO = new EnderVO();
        destVO.setEnder(destEnderVO);
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

        return destVO;
    }

    public EntregaVO new_EntregaVO(Endereco destinatarioEntrega, Empresa destinatario) {
        EntregaVO entregaVO = new EntregaVO();
        if (destinatario == null)
            destinatario = getSaidaProduto().clienteProperty().getValue();

        if (destinatario.isPessoaJuridica())
            entregaVO.setCnpj(destinatario.cnpjProperty().getValue());
        else
            entregaVO.setCpf(destinatario.cnpjProperty().getValue());
        entregaVO.setxNome(destinatario.getxNome(60));

        entregaVO.setxLgr(destinatarioEntrega.logradouroProperty().getValue());
        entregaVO.setNro(destinatarioEntrega.numeroProperty().getValue());
        entregaVO.setxCpl(destinatarioEntrega.complementoProperty().getValue());
        entregaVO.setxBairro(destinatarioEntrega.bairroProperty().getValue());
        entregaVO.setcMun(destinatarioEntrega.municipioProperty().getValue().ibge_codigoProperty().getValue());
        entregaVO.setxMun(destinatarioEntrega.municipioProperty().getValue().descricaoProperty().getValue().toUpperCase());
        entregaVO.setUF(destinatarioEntrega.municipioProperty().getValue().ufProperty().getValue().siglaProperty().getValue().toUpperCase());
        entregaVO.setCEP(destinatarioEntrega.cepProperty().getValue());
        entregaVO.setcPais(TCONFIG.getNfe().getCPais());
        entregaVO.setxPais(TCONFIG.getNfe().getNPais());
        entregaVO.setFone(destinatario.getFonePrincipal());

        return entregaVO;
    }

    public List<DetVO> new_DetVOList(TotalVO totalVO) {
        if (getSaidaProduto().getSaidaProdutoProdutoList().size() > 0) {
            List<DetVO> detVOList = new ArrayList<>();


            totalVO.getIcmsTot().setvNF(null);
            return detVOList;
        }
        return null;
    }

    public EnviNfeVO getEnviNfeVO() {
        return enviNfeVO;
    }

    public void setEnviNfeVO(EnviNfeVO enviNfeVO) {
        this.enviNfeVO = enviNfeVO;
    }

    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

    public SaidaProdutoNfe getSaidaProdutoNfe() {
        return saidaProdutoNfe;
    }

    public void setSaidaProdutoNfe(SaidaProdutoNfe saidaProdutoNfe) {
        this.saidaProdutoNfe = saidaProdutoNfe;
    }

    public SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        this.saidaProdutoDAO = saidaProdutoDAO;
    }

    public TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public void settEnviNFe(TEnviNFe tEnviNFe) {
        this.tEnviNFe = tEnviNFe;
    }
}

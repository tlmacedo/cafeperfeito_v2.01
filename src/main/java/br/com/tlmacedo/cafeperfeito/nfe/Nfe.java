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

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class Nfe {

    private static EnviNfeVO enviNfeVO;
    private static SaidaProduto saidaProduto;
    private static SaidaProdutoNfe saidaProdutoNfe;
    private static SaidaProdutoDAO saidaProdutoDAO;
    private static TEnviNFe tEnviNFe;

//    private static TEnviNFe gerartEnviNFe() {
//        return new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe();
//    }
//
//    public static String getXml(EnviNfeVO enviNfeVO) throws JAXBException {
//        setEnviNfeVO(enviNfeVO);
//        return ServiceUtilXml.objectToXml(gerartEnviNFe());
//    }
//
//    public static String getXml(SaidaProduto saidaProduto) throws Exception {
//        return getXml(getEnviNfeVO(saidaProduto));
//    }
//
//    public static EnviNfeVO get_EnviNfeVO(Long nPed) {
//        setSaidaProdutoDAO(new SaidaProdutoDAO());
//        setSaidaProduto(getSaidaProdutoDAO().getById(SaidaProduto.class, nPed));
//        return getEnviNfeVO();
//    }
//
//    public static EnviNfeVO get_EnviNfeVO(SaidaProduto saidaProduto) {
//        setSaidaProduto(saidaProduto);
//        return getEnviNfeVO();
//    }
//
//    public static EnviNfeVO get_EnviNfeVO() {
//        gerarNotaFiscal();
//        return getEnviNfeVO();
//    }

    public static void gerarNotaFiscal() {
        setEnviNfeVO(new EnviNfeVO());

        getEnviNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getEnviNfeVO().setIdLote(Nfe_Service.factoryIdLote(getSaidaProduto().idProperty().getValue()));
        getEnviNfeVO().setIndSinc(TCONFIG.getNfe().getIndSinc());

        NfeVO nfeVO = new_NfeVO();
        getEnviNfeVO().setNfe(nfeVO);

        IdeVO ideVO = new_IdeVO();


        InfNfeVO infNfeVO = new_InfNfeVO(ideVO);
        nfeVO.setInfNfe(infNfeVO);


        settEnviNFe(new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe());

    }

    public static NfeVO new_NfeVO() {
        return new NfeVO();
    }

    public static IdeVO new_IdeVO() {
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
            saidaProdutoNfe.setImpressaoFinNFe(NfeImpressaoFinNFe.getList().stream()
                    .filter(impressaoFinNFe -> impressaoFinNFe.getCod() == TCONFIG.getNfe().getFinNFe())
                    .findFirst().orElse(NfeImpressaoFinNFe.NORMAL));
            saidaProdutoNfe.setPagamentoIndicador(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            saidaProdutoNfe.setPagamentoMeio(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            saidaProdutoNfe.setSaidaProduto(getSaidaProduto());
            saidaProdutoNfe.setStatusSefaz(NfeStatusSefaz.DIGITACAO);
            saidaProdutoNfe.setNaturezaOperacao(NfeDadosNaturezaOperacao.INTERNA);
            saidaProdutoNfe.setModelo(NfeDadosModelo.MOD55);
            saidaProdutoNfe.setModFrete(NfeTransporteModFrete.REMETENTE);
            saidaProdutoNfe.serieProperty().setValue(nfeTemp.serieProperty().getValue());
            saidaProdutoNfe.numeroProperty().setValue(nfeTemp.numeroProperty().getValue() + 1);
            saidaProdutoNfe.cobrancaNumeroProperty().setValue(saidaProdutoNfe.numeroProperty().getValue().toString());
            saidaProdutoNfe.setPagamentoIndicador(NfeCobrancaDuplicataPagamentoIndicador.PRAZO);
            saidaProdutoNfe.setPagamentoMeio(NfeCobrancaDuplicataPagamentoMeio.OUTROS);
            saidaProdutoNfe.dtHoraEmissaoProperty().setValue(getSaidaProduto().getDtCadastro());
            saidaProdutoNfe.setInformacaoAdicional(String.format(TCONFIG.getNfe().getInfAdic(),
                    ServiceMascara.getMoeda(getSaidaProduto().getSaidaProdutoProdutoList().stream()
                            .map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                                    .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add), 2),
                    (getSaidaProduto().getContasAReceber().dtVencimentoProperty().getValue() != null)
                            ? String.format(" dt. Venc.: %s",
                            getSaidaProduto().getContasAReceber().dtVencimentoProperty().getValue().format(Regex_Convert.DTF_DATA))
                            : "",
                    TCONFIG.getInfLoja().getBanco(),
                    TCONFIG.getInfLoja().getAgencia(), TCONFIG.getInfLoja().getContaCorrente())
                    .toUpperCase());
            saidaProdutoNfe.setXmlAssinatura(null);
            saidaProdutoNfe.setXmlProtNfe(null);
            if (getSaidaProduto().getDtSaida().compareTo(getSaidaProduto().getDtCadastro().toLocalDate()) <= 0) {
                saidaProdutoNfe.dtHoraSaidaProperty().setValue(getSaidaProduto().getDtCadastro());
            } else {
                saidaProdutoNfe.dtHoraSaidaProperty().setValue(getSaidaProduto().getDtSaida().atTime(8, 0, 0));
            }
            saidaProdutoNfe.setDestinoOperacao(NfeDadosDestinoOperacao.INTERNA);
            if (getSaidaProduto().getCliente().ieProperty().getValue().equals(""))
                saidaProdutoNfe.setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.FINAL);
            else
                saidaProdutoNfe.setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.NORMAL);
            saidaProdutoNfe.setIndicadorPresenca(NfeDadosIndicadorPresenca.TELEATENDIMENTO);
            salvar = true;
        }

        ideVO.setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
        ideVO.setNatOp(saidaProdutoNfe.getNaturezaOperacao().getDescricao());
        ideVO.setMod(saidaProdutoNfe.getModelo().getDescricao());
        ideVO.setSerie(saidaProdutoNfe.serieProperty().getValue().toString());
        ideVO.setnNF(saidaProdutoNfe.numeroProperty().getValue().toString());
        ideVO.setDhEmi(saidaProdutoNfe.dtHoraEmissaoProperty().getValue());
        ideVO.setDhSaiEnt(saidaProdutoNfe.dtHoraSaidaProperty().getValue());
        ideVO.setTpNF(String.valueOf(TCONFIG.getNfe().getTpNF()));
        ideVO.setIdDest(String.valueOf(saidaProdutoNfe.getDestinoOperacao().getCod()));
        ideVO.setcMunFG(String.valueOf(TCONFIG.getInfLoja().getCMunFG()));
        ideVO.setTpImp((saidaProdutoNfe.getImpressaoTpImp() != null)
                ? String.valueOf(saidaProdutoNfe.getImpressaoTpImp().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpImp()));
        ideVO.setTpEmis((saidaProdutoNfe.getImpressaoTpEmis() != null)
                ? String.valueOf(saidaProdutoNfe.getImpressaoTpEmis().getCod())
                : String.valueOf(TCONFIG.getNfe().getTpEmis()));
        ideVO.setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        ideVO.setFinNFe((saidaProdutoNfe.getImpressaoFinNFe() != null)
                ? String.valueOf(saidaProdutoNfe.getImpressaoFinNFe().getCod())
                : String.valueOf(NfeImpressaoFinNFe.NORMAL.getCod()));
        if (saidaProdutoNfe.getConsumidorFinal().equals(NfeDadosIndicadorConsumidorFinal.NORMAL)
                && getSaidaProduto().getCliente().ieProperty().getValue().equals(""))
            saidaProdutoNfe.setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.FINAL);
        ideVO.setIndFinal(String.valueOf(saidaProdutoNfe.getConsumidorFinal().getCod()));
        ideVO.setIndPres(String.valueOf(saidaProdutoNfe.getIndicadorPresenca().getCod()));
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

    public static InfNfeVO new_InfNfeVO(IdeVO ideVO) {
        InfNfeVO infNfeVO = new InfNfeVO();
        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());
        infNfeVO.setIde(ideVO);
        infNfeVO.setId(ServiceValidarDado.gerarChaveNfe(ideVO));
        infNfeVO.setEmit(new_EmitVO(null));

        return infNfeVO;
    }

    public static EmitVO new_EmitVO(Empresa emissor) {
        EmitVO emitVO = new EmitVO();
        emitVO.setEnder(new_EmitEnderVO(emissor.getEndereco(TipoEndereco.PRINCIPAL), emissor.getFonePrincipal()));
        if (emissor == null)
            if ((emissor = getSaidaProduto().getCliente()) == null)
                emissor = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));
        if (emissor.isPessoaJuridica())
            emitVO.setCnpj(emissor.getCnpj());
        else
            emitVO.setCpf(emissor.getCnpj());
        emitVO.setxNome(emissor.getRazao(60));
        emitVO.setxFant(emissor.getFantasia(60));
        emitVO.setIE(emissor.getIe());
        emitVO.setCRT(String.valueOf(TCONFIG.getNfe().getCRT()));

        return emitVO;
    }

    public static EnderVO new_EmitEnderVO(Endereco emissorEndereco, String telefone) {
        EnderVO emitEnderVO = new EnderVO();
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

        return emitEnderVO;
    }


    public static EnviNfeVO getEnviNfeVO() {
        return enviNfeVO;
    }

    public static void setEnviNfeVO(EnviNfeVO enviNfeVO) {
        Nfe.enviNfeVO = enviNfeVO;
    }

    public static SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public static void setSaidaProduto(SaidaProduto saidaProduto) {
        Nfe.saidaProduto = saidaProduto;
    }

    public static SaidaProdutoNfe getSaidaProdutoNfe() {
        return saidaProdutoNfe;
    }

    public static void setSaidaProdutoNfe(SaidaProdutoNfe saidaProdutoNfe) {
        Nfe.saidaProdutoNfe = saidaProdutoNfe;
    }

    public static SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public static void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        Nfe.saidaProdutoDAO = saidaProdutoDAO;
    }

    public static TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public static void settEnviNFe(TEnviNFe tEnviNFe) {
        Nfe.tEnviNFe = tEnviNFe;
    }
}

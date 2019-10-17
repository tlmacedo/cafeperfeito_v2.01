package br.com.tlmacedo.cafeperfeito.nfe.v400;

import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.service.ServiceValidarDado;
import br.com.tlmacedo.nfe.model.vo.*;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class NotaFiscal {

    EnviNfeVO enviNfeVO;
    SaidaProduto saidaProduto;

    public NotaFiscal(SaidaProduto saidaProduto) {
        setEnviNfeVO(new EnviNfeVO());
        setSaidaProduto(saidaProduto);

        getEnviNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getEnviNfeVO().setIdLote(String.format("%015d", getSaidaProduto().idProperty().getValue()));
        getEnviNfeVO().setIndSinc(TCONFIG.getNfe().getIndSinc());

        NfeVO nfeVO = new NfeVO();
        getEnviNfeVO().setNfe(nfeVO);

        InfNfeVO infNfeVO = new InfNfeVO();
        nfeVO.setInfNfe(infNfeVO);

        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());

        IdeVO ideVO = new IdeVO();
        infNfeVO.setIde(ideVO);
        ideVO.setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
//        System.out.printf("%s\n", getSaidaProduto().saidaProdutoNfeProperty().getValue().getNaturezaOperacao());
        ideVO.setNatOp(getSaidaProduto().saidaProdutoNfeProperty().getValue().getNaturezaOperacao().getDescricao());
        ideVO.setMod(getSaidaProduto().saidaProdutoNfeProperty().getValue().getModelo().getDescricao());
        ideVO.setSerie(getSaidaProduto().saidaProdutoNfeProperty().getValue().serieProperty().getValue().toString());
        ideVO.setnNF(getSaidaProduto().saidaProdutoNfeProperty().getValue().numeroProperty().getValue().toString());
        ideVO.setDhEmi(getSaidaProduto().saidaProdutoNfeProperty().getValue().dtHoraEmissaoProperty().getValue());
        ideVO.setDhSaiEnt(getSaidaProduto().saidaProdutoNfeProperty().getValue().dtHoraSaidaProperty().getValue());
        ideVO.setTpNF(String.valueOf(TCONFIG.getNfe().getTpNF()));
        ideVO.setIdDest(String.valueOf(getSaidaProduto().saidaProdutoNfeProperty().getValue().getDestinoOperacao().getCod()));
        ideVO.setcMunFG(String.valueOf(TCONFIG.getInfLoja().getCMunFG()));
        ideVO.setTpImp(String.valueOf(TCONFIG.getNfe().getTpImp()));
        ideVO.setTpEmis(String.valueOf(TCONFIG.getNfe().getTpEmis()));
        ideVO.setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        ideVO.setFinNFe(String.valueOf(TCONFIG.getNfe().getFinNFe()));
        ideVO.setIndFinal(String.valueOf(getSaidaProduto().saidaProdutoNfeProperty().getValue().getConsumidorFinal().getCod()));
        ideVO.setIndPres(String.valueOf(getSaidaProduto().saidaProdutoNfeProperty().getValue().getIndicadorPresenca().getCod()));
        ideVO.setProcEmi(String.valueOf(TCONFIG.getNfe().getProcEmi()));
        ideVO.setVerProc(TCONFIG.getNfe().getVerProc());
        infNfeVO.setId(ServiceValidarDado.gerarChaveNfe(ideVO));

        Empresa emissor = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));
        EnderVO enderVO = new EnderVO();
        EmitVO emitVO = new EmitVO();
        infNfeVO.setEmit(emitVO);
        emitVO.setEnder(enderVO);
        emitVO.setCnpj(emissor.getCnpj());
        emitVO.setxNome(emissor.getRazao());
        emitVO.setxFant(emissor.getFantasia());
        emitVO.setIE(emissor.getIe());
        emitVO.setCRT(String.valueOf(TCONFIG.getNfe().getCRT()));
        infNfeVO.setEmit(emitVO);


    }

    //    NfeVO nfeVO =
//            NfeVO.criaNfeVO();
//    SaidaProduto saidaProduto;
//
//    public NotaFiscal(SaidaProduto saidaProduto) {
////        setNfeVO(
////                new NfeVO()
////        );
//        setSaidaProduto(saidaProduto);
//
//        InfNfeVO infNfeVO = new InfNfeVO();
//        ServiceGerarChaveNfe chaveNfe = new ServiceGerarChaveNfe();
//        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());
//        nfeVO.setInfNfe(infNfeVO);
//        //getNfeVO().setInfNfe(infNfeVO);
//
//        IdeVO ideVO = new IdeVO();
//        ideVO.setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
//        ideVO.setNatOp(getSaidaProduto().saidaProdutoNfeProperty().getValue().getNaturezaOperacao().getDescricao());
//        ideVO.setMod(getSaidaProduto().saidaProdutoNfeProperty().getValue().getModelo().getDescricao());
//        ideVO.setSerie(getSaidaProduto().saidaProdutoNfeProperty().getValue().serieProperty().getValue().toString());
//        ideVO.setnNF(getSaidaProduto().saidaProdutoNfeProperty().getValue().numeroProperty().getValue().toString());
//        ideVO.setDhEmi(getSaidaProduto().saidaProdutoNfeProperty().getValue().dtHoraEmissaoProperty().getValue());
//        ideVO.setDhSaiEnt(getSaidaProduto().saidaProdutoNfeProperty().getValue().dtHoraSaidaProperty().getValue());
//        ideVO.setTpNF("1");
//        ideVO.setIdDest("1");
//        ideVO.setcMunFG("1302603");
//        ideVO.setTpImp("1");
//        ideVO.setTpEmis("1");
//        ideVO.setTpAmb("2");
//        ideVO.setFinNFe("1");
//        ideVO.setIndFinal("0");
//        ideVO.setIndPres("3");
//        ideVO.setProcEmi("0");
//        ideVO.setVerProc("2.01");
//        infNfeVO.setId(chaveNfe.Gerar(ideVO));
//        infNfeVO.setIde(ideVO);
//
//        EnderVO enderEmitVO = new EnderVO();
//        enderEmitVO.setxLgr("R TREZE DE MAIO");
//        enderEmitVO.setNro("159");
//        enderEmitVO.setxBairro("COROADO");
//        enderEmitVO.setcMun("1302603");
//        enderEmitVO.setxMun("MANAUS");
//        enderEmitVO.setUF("AM");
//        enderEmitVO.setCEP("69080440");
//        enderEmitVO.setcPais("1058");
//        enderEmitVO.setxPais("BRASIL");
//        enderEmitVO.setFone("92981686148");
//
//        EmitVO emitVO = new EmitVO();
//        emitVO.setCnpj("08009246000136");
//        emitVO.setxNome("T. L. MACEDO");
//        emitVO.setxFant("CAFE PERFEITO");
//        emitVO.setEnder(enderEmitVO);
//        emitVO.setIE("042171865");
//        emitVO.setCRT("3");
//        infNfeVO.setEmit(emitVO);
//
//        EnderVO enderDestVO = new EnderVO();
//        enderDestVO.setxLgr("AV GOVERNADOR DANILO AREOSA");
//        enderDestVO.setNro("1170");
//        enderDestVO.setxCpl("LOTE 164");
//        enderDestVO.setxBairro("DISTRITO INDUSTRIAL I");
//        enderDestVO.setcMun("1302603");
//        enderDestVO.setxMun("MANAUS");
//        enderDestVO.setUF("AM");
//        enderDestVO.setCEP("69075351");
//        enderDestVO.setcPais("1058");
//        enderDestVO.setxPais("BRASIL");
//        enderDestVO.setFone("9221239797");
//
//        DestVO destVO = new DestVO();
//        destVO.setCnpj("02844344000102");
//        destVO.setxNome("FUNDACAO AMAZONICA DE AMPARO A PESQUISA E DESENVOLVIMENTO TE");
//        destVO.setEnder(enderDestVO);
//        destVO.setIndIEDest("9");
//        destVO.setEmail("contabil@fpf.br");
//        infNfeVO.setDest(destVO);
//
//    }

    /**
     * Begin Getters and Setters
     */

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

    /**
     * END Getters and Setters
     */
}

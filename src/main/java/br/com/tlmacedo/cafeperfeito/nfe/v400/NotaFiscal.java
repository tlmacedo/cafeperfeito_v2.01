package br.com.tlmacedo.cafeperfeito.nfe.v400;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.nfe.model.vo.NfeVO;

public class NotaFiscal {

    NfeVO nfeVO;
    SaidaProduto saidaProduto;

    public NotaFiscal(SaidaProduto saidaProduto) {
        setNfeVO(new NfeVO());
        setSaidaProduto(saidaProduto);

//        InfNfeVO infNfeVO = new InfNfeVO();
//        ServiceGerarChaveNfe chaveNfe = new ServiceGerarChaveNfe();
//        infNfeVO.setId(chaveNfe.Gerar(getSaidaProduto().getSaidaProdutoNfe().getNumero()));
//        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());
//        getNfeVO().setInfNfe(infNfeVO);
//
//        IdeVO ideVO = new IdeVO();
//        ideVO.setcUF("13");
//        ideVO.setcNF(chaveNfe.getCNF());
//        ideVO.setNatOp("VENDA DENTRO DO ESTADO");
//        ideVO.setMod(chaveNfe.getMod());
//        ideVO.setSerie(chaveNfe.getSerie());
//        ideVO.setnNF(chaveNfe.getnNF());
//        ideVO.setDhEmi(LocalDateTime.now().toString());
//        ideVO.setDhSaiEnt(LocalDateTime.now().toString());
//        ideVO.setTpNF("1");
//        ideVO.setIdDest("1");
//        ideVO.setcMunFG("1302603");
//        ideVO.setTpImp("1");
//        ideVO.setTpEmis("1");
//        ideVO.setcDV(chaveNfe.getChave().substring(chaveNfe.getChave().length() - 1));
//        ideVO.setTpAmb("2");
//        ideVO.setFinNFe("1");
//        ideVO.setIndFinal("0");
//        ideVO.setIndPres("3");
//        ideVO.setProcEmi("0");
//        ideVO.setVerProc("2.01");
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

    }

    /**
     * Begin Getters and Setters
     */

    public NfeVO getNfeVO() {
        return nfeVO;
    }

    public void setNfeVO(NfeVO nfeVO) {
        this.nfeVO = nfeVO;
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

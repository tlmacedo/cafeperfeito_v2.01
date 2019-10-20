package br.com.tlmacedo.cafeperfeito.nfe.v400;

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

import java.math.BigDecimal;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class NotaFiscal {

    EnviNfeVO enviNfeVO;
    SaidaProduto saidaProduto;
    SaidaProdutoNfe nfe;
    SaidaProdutoDAO saidaProdutoDAO;

    public NotaFiscal(SaidaProdutoDAO saidaProdutoDAO, SaidaProduto saidaProduto) {
        setEnviNfeVO(new EnviNfeVO());
        setSaidaProduto(saidaProduto);
        setSaidaProdutoDAO(saidaProdutoDAO);

        getEnviNfeVO().setVersao(TCONFIG.getNfe().getVersao());
        getEnviNfeVO().setIdLote(String.format("%015d", getSaidaProduto().idProperty().getValue()));
        getEnviNfeVO().setIndSinc(TCONFIG.getNfe().getIndSinc());

        NfeVO nfeVO = new NfeVO();
        getEnviNfeVO().setNfe(nfeVO);

        InfNfeVO infNfeVO = new InfNfeVO();
        nfeVO.setInfNfe(infNfeVO);

        infNfeVO.setVersao(TCONFIG.getNfe().getVersao());

        IdeVO ideVO = newNfeIde(getSaidaProduto().getSaidaProdutoNfe());
        infNfeVO.setIde(ideVO);
        infNfeVO.setId(ServiceValidarDado.gerarChaveNfe(ideVO));

        Empresa emissor = new EmpresaDAO().getById(Empresa.class, Long.valueOf(TCONFIG.getInfLoja().getId()));
        EnderVO emitEnderVO = new EnderVO();
        EmitVO emitVO = new EmitVO();
        infNfeVO.setEmit(emitVO);
        emitVO.setEnder(emitEnderVO);
        emitVO.setCnpj(emissor.getCnpj());
        emitVO.setxNome(emissor.getRazao());
        emitVO.setxFant(emissor.getFantasia());
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
            destVO.setIndIEDest("2");
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
    }

    private IdeVO newNfeIde(SaidaProdutoNfe nfe) {
        IdeVO ideVO = new IdeVO();
        boolean salvar = false;
        if (nfe == null) {
            SaidaProdutoNfe nfeTemp = new SaidaProdutoNfeDAO().getAll(SaidaProdutoNfe.class, null, "numero DESC")
                    .stream().findFirst().orElse(null);
            nfe = new SaidaProdutoNfe();
            if (nfeTemp == null) {
                nfeTemp.serieProperty().setValue(1);
                nfeTemp.numeroProperty().setValue(0);
            }
            nfe.setSaidaProduto(getSaidaProduto());
            nfe.setStatusSefaz(NfeStatusSefaz.DIGITACAO);
            nfe.setNaturezaOperacao(NfeDadosNaturezaOperacao.INTERNA);
            nfe.setModelo(NfeDadosModelo.MOD55);
            nfe.setModFrete(NfeTransporteModFrete.REMETENTE);
            nfe.serieProperty().setValue(nfeTemp.serieProperty().getValue());
            nfe.numeroProperty().setValue(nfeTemp.numeroProperty().getValue() + 1);
            nfe.cobrancaNumeroProperty().setValue(nfe.numeroProperty().getValue().toString());
            nfe.dtHoraEmissaoProperty().setValue(getSaidaProduto().getDtCadastro());
            nfe.setInformacaoAdicional(String.format(TCONFIG.getNfe().getInfAdic(),
                    ServiceMascara.getMoeda(getSaidaProduto().getSaidaProdutoProdutoList().stream().map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                            .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add), 2),
                    TCONFIG.getInfLoja().getBanco(),
                    TCONFIG.getInfLoja().getAgencia(), TCONFIG.getInfLoja().getContaCorrente()));
            nfe.xmlAssinaturaProperty().setValue("");
            nfe.xmlProtNfeProperty().setValue("");
            if (getSaidaProduto().getDtSaida().compareTo(getSaidaProduto().getDtCadastro().toLocalDate()) <= 0) {
                nfe.dtHoraSaidaProperty().setValue(getSaidaProduto().getDtCadastro());
            } else {
                nfe.dtHoraSaidaProperty().setValue(getSaidaProduto().getDtSaida().atTime(8, 0, 0));
            }
            nfe.setDestinoOperacao(NfeDadosDestinoOperacao.INTERNA);
            if (getSaidaProduto().getCliente().ieProperty().getValue().equals(""))
                nfe.setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.FINAL);
            else
                nfe.setConsumidorFinal(NfeDadosIndicadorConsumidorFinal.NORMAL);
            nfe.setIndicadorPresenca(NfeDadosIndicadorPresenca.TELEATENDIMENTO);
            salvar = true;
        }

        ideVO.setcUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
        ideVO.setNatOp(nfe.getNaturezaOperacao().getDescricao());
        ideVO.setMod(nfe.getModelo().getDescricao());
        ideVO.setSerie(nfe.serieProperty().getValue().toString());
        ideVO.setnNF(nfe.numeroProperty().getValue().toString());
        ideVO.setDhEmi(nfe.dtHoraEmissaoProperty().getValue());
        ideVO.setDhSaiEnt(nfe.dtHoraSaidaProperty().getValue());
        ideVO.setTpNF(String.valueOf(TCONFIG.getNfe().getTpNF()));
        ideVO.setIdDest(String.valueOf(nfe.getDestinoOperacao().getCod()));
        ideVO.setcMunFG(String.valueOf(TCONFIG.getInfLoja().getCMunFG()));
        ideVO.setTpImp(String.valueOf(TCONFIG.getNfe().getTpImp()));
        ideVO.setTpEmis(String.valueOf(TCONFIG.getNfe().getTpEmis()));
        ideVO.setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        ideVO.setFinNFe(String.valueOf(TCONFIG.getNfe().getFinNFe()));
        ideVO.setIndFinal(String.valueOf(nfe.getConsumidorFinal().getCod()));
        ideVO.setIndPres(String.valueOf(nfe.getIndicadorPresenca().getCod()));
        ideVO.setProcEmi(String.valueOf(TCONFIG.getNfe().getProcEmi()));
        ideVO.setVerProc(TCONFIG.getNfe().getVerProc());

        if (salvar) {
            nfe.chaveProperty().setValue(ServiceValidarDado.gerarChaveNfe(ideVO));
            try {
                getSaidaProdutoDAO().transactionBegin();
                getSaidaProduto().setSaidaProdutoNfe(nfe);
                setSaidaProduto(getSaidaProdutoDAO().setTransactionPersist(getSaidaProduto()));
                getSaidaProdutoDAO().transactionCommit();
            } catch (Exception e) {
                e.printStackTrace();
                getSaidaProdutoDAO().transactionRollback();
            }
        }

        return ideVO;
    }


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

    public SaidaProdutoNfe getNfe() {
        return nfe;
    }

    public void setNfe(SaidaProdutoNfe nfe) {
        this.nfe = nfe;
    }

    public SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        this.saidaProdutoDAO = saidaProdutoDAO;
    }

    /**
     * END Getters and Setters
     */
}

package br.com.tlmacedo.cafeperfeito.service.serialize_deserialize;

import br.com.tlmacedo.cafeperfeito.model.vo.EntradaProduto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

//import br.com.tlmacedo.cafeperfeito.model.vo.enums.CteTomadorServico;
//import br.com.tlmacedo.cafeperfeito.model.vo.enums.NfeCteModelo;
//import br.com.tlmacedo.cafeperfeito.model.vo.enums.SituacaoEntrada;

//import static br.com.tlmacedo.cafeperfeito.interfaces.Convert_Date_Key.DTF_DATA;

/**
 * Created by IntelliJ IDEA.
 * User: thiagomacedo
 * Date: 2019-03-17
 * Time: 17:52
 */

public class EntradaProdutoDeserializer extends StdDeserializer<EntradaProduto> {
    protected EntradaProdutoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EntradaProduto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }

//    public EntradaProdutoDeserializer() {
//        this(null);
//    }
//
//    public EntradaProdutoDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @SuppressWarnings("Duplicates")
//    @Override
//    public EntradaProduto deserialize(JsonParser jp, DeserializationContext ctxt)
//            throws IOException, JsonProcessingException {
//        JsonNode node = jp.getCodec().readTree(jp);
//        int id = node.get("id").intValue();
//        int situacao = node.get("situacao").intValue();
//        Empresa loja = new EmpresaDAO().getById(Empresa.class, node.get("loja").get("id").longValue());
//
//        JsonNode nodeNfe = node.get("entradaNfe");
//
//        EntradaNfe entradaNfe = null;
//        EntradaFiscal nfeEntradaFiscal = null;
//        if (!nodeNfe.get("entradaFiscal").asText().equals("null")) {
//            JsonNode nodeNfeEntradaFiscal = nodeNfe.get("entradaFiscal");
//            nfeEntradaFiscal = new EntradaFiscal();
//            nfeEntradaFiscal.setId(nodeNfeEntradaFiscal.get("id").longValue());
//            nfeEntradaFiscal.setControle(nodeNfeEntradaFiscal.get("controle").asText());
//            nfeEntradaFiscal.setDocOrigem(nodeNfeEntradaFiscal.get("docOrigem").asText());
//            nfeEntradaFiscal.setVlrJuros(nodeNfeEntradaFiscal.get("vlrJuros").decimalValue().setScale(2));
//            nfeEntradaFiscal.setVlrMulta(nodeNfeEntradaFiscal.get("vlrMulta").decimalValue().setScale(2));
//            nfeEntradaFiscal.setVlrDocumento(nodeNfeEntradaFiscal.get("vlrNfe").decimalValue().setScale(2));
//            nfeEntradaFiscal.setVlrTaxa(nodeNfeEntradaFiscal.get("vlrTaxa").decimalValue().setScale(2));
//            nfeEntradaFiscal.setVlrTributo(nodeNfeEntradaFiscal.get("vlrTributo").decimalValue().setScale(2));
//            nfeEntradaFiscal.setTributosSefazAm(new FiscalTributosSefazAmDAO().getById(FiscalTributosSefazAm.class,
//                    nodeNfeEntradaFiscal.get("tributoSefazAm").get("id").longValue()));
//        }
//        if (nodeNfe != null) {
//            entradaNfe = new EntradaNfe();
//            entradaNfe.setId(nodeNfe.get("id").longValue());
//            entradaNfe.setChave(nodeNfe.get("chave").asText());
//            entradaNfe.setNumero(nodeNfe.get("numero").asText());
//            entradaNfe.setSerie(nodeNfe.get("serie").asText());
//            entradaNfe.setModelo(NfeCteModelo.toEnum(nodeNfe.get("modelo").intValue()));
//            entradaNfe.setDataEmissao(LocalDate.parse(nodeNfe.get("dataEmissao").asText(), DTF_DATA));
//            entradaNfe.setDataEntrada(LocalDate.parse(nodeNfe.get("dataEntrada").asText(), DTF_DATA));
//            entradaNfe.setEmissor(new EmpresaDAO().getById(Empresa.class, nodeNfe.get("emissor").get("id").longValue()));
//            entradaNfe.setEntradaFiscal(nfeEntradaFiscal);
//        }
//
//        EntradaCte entradaCte = null;
//        EntradaFiscal cteEntradaFiscal = null;
//        if (!node.get("entradaCte").asText().equals("null")) {
//            JsonNode nodeCte = node.get("entradaCte");
//            if (!nodeCte.get("entradaFiscal").asText().equals("null")) {
//                JsonNode nodeCteEntradaFiscal = nodeCte.get("entradaFiscal");
//                cteEntradaFiscal = new EntradaFiscal();
//                cteEntradaFiscal.setId(nodeCteEntradaFiscal.get("id").longValue());
//                cteEntradaFiscal.setControle(nodeCteEntradaFiscal.get("controle").asText());
//                cteEntradaFiscal.setDocOrigem(nodeCteEntradaFiscal.get("docOrigem").asText());
//                cteEntradaFiscal.setVlrJuros(nodeCteEntradaFiscal.get("vlrJuros").decimalValue().setScale(2));
//                cteEntradaFiscal.setVlrMulta(nodeCteEntradaFiscal.get("vlrMulta").decimalValue().setScale(2));
//                cteEntradaFiscal.setVlrDocumento(nodeCteEntradaFiscal.get("vlrNfe").decimalValue().setScale(2));
//                cteEntradaFiscal.setVlrTaxa(nodeCteEntradaFiscal.get("vlrTaxa").decimalValue().setScale(2));
//                cteEntradaFiscal.setVlrTributo(nodeCteEntradaFiscal.get("vlrTributo").decimalValue().setScale(2));
//                cteEntradaFiscal.setTributosSefazAm(new FiscalTributosSefazAmDAO().getById(FiscalTributosSefazAm.class,
//                        nodeCteEntradaFiscal.get("tributoSefazAm").get("id").longValue()));
//            }
//
//            if (nodeCte.asText() != null) {
//                entradaCte = new EntradaCte();
//                entradaCte.setId(nodeCte.get("id").longValue());
//                entradaCte.setChave(nodeCte.get("chave").asText());
//                entradaCte.setNumero(nodeCte.get("numero").asText());
//                entradaCte.setSerie(nodeCte.get("serie").asText());
//                entradaCte.setQtdVolume(nodeCte.get("qtdVolume").intValue());
//                entradaCte.setTomadorServico(CteTomadorServico.toEnum(nodeCte.get("tomadorServico").intValue()));
//                entradaCte.setModelo(NfeCteModelo.toEnum(nodeCte.get("modelo").intValue()));
//                entradaCte.setVlrCte(nodeCte.get("vlrCte").decimalValue().setScale(2));
//                entradaCte.setPesoBruto(nodeCte.get("pesoBruto").decimalValue().setScale(2));
//                entradaCte.setVlrFreteBruto(nodeCte.get("vlrFreteBruto").decimalValue().setScale(2));
//                entradaCte.setVlrTaxas(nodeCte.get("vlrTaxas").decimalValue().setScale(2));
//                entradaCte.setVlrColeta(nodeCte.get("vlrColeta").decimalValue().setScale(2));
//                entradaCte.setVlrImpostoFrete(nodeCte.get("vlrImpostoFrete").decimalValue().setScale(2));
//                entradaCte.setDataEmissao(LocalDate.parse(nodeCte.get("dataEmissao").asText(), DTF_DATA));
//                entradaCte.setSituacaoTributaria(new FiscalFreteSituacaoTributariaDAO().getById(FiscalFreteSituacaoTributaria.class,
//                        nodeCte.get("situacaoTributaria").get("id").longValue()));
//                entradaCte.setEmissor(new EmpresaDAO().getById(Empresa.class, nodeCte.get("emissor").get("id").longValue()));
//                entradaCte.setEntradaFiscal(cteEntradaFiscal);
//
//            }
//        }
//
//        JsonNode nodeProdutos = node.get("produtos");
//        List<EntradaProdutoProduto> produtoList = new ArrayList<>();
//        if (nodeProdutos.size() != 0)
//            for (JsonNode getProduto : nodeProdutos) {
//                EntradaProdutoProduto produtoEntrada = new EntradaProdutoProduto();
//                produtoEntrada.setId(getProduto.get("id").longValue());
//                produtoEntrada.setCodigo(getProduto.get("codigo").asText());
//                produtoEntrada.setDescricao(getProduto.get("descricao").asText());
//                produtoEntrada.setLote(getProduto.get("lote").asText());
//                produtoEntrada.setValidade(LocalDate.parse(getProduto.get("validade").asText(), DTF_DATA));
//                produtoEntrada.setQtd(getProduto.get("qtd").intValue());
//                produtoEntrada.setVlrFabrica(getProduto.get("vlrFabrica").decimalValue().setScale(2));
//                produtoEntrada.setVlrBruto(getProduto.get("vlrBruto").decimalValue().setScale(2));
//                produtoEntrada.setVlrImposto(getProduto.get("vlrImposto").decimalValue().setScale(2));
//                produtoEntrada.setVlrDesconto(getProduto.get("vlrDesconto").decimalValue().setScale(2));
//                produtoEntrada.setVlrLiquido(getProduto.get("vlrLiquido").decimalValue().setScale(2));
//                produtoEntrada.setEstoque(getProduto.get("estoque").intValue());
//                produtoEntrada.setVarejo(getProduto.get("varejo").intValue());
//                produtoEntrada.setVolume(getProduto.get("volume").intValue());
//                produtoEntrada.setProduto(new ProdutoDAO().getById(Produto.class, getProduto.get("produto_id").longValue()));
//                produtoList.add(produtoEntrada);
//            }
//                        /*
//    private LongProperty id = new SimpleLongProperty();
//    private StringProperty codigo = new SimpleStringProperty();
//    private StringProperty descricao = new SimpleStringProperty();
//    private StringProperty lote = new SimpleStringProperty();
//    private ObjectProperty<LocalDate> validade = new SimpleObjectProperty<>();
//    private IntegerProperty qtd = new SimpleIntegerProperty();
//    private ObjectProperty<BigDecimal> vlrFabrica = new SimpleObjectProperty<>();
//    private ObjectProperty<BigDecimal> vlrBruto = new SimpleObjectProperty<>();
//    private ObjectProperty<BigDecimal> vlrDesconto = new SimpleObjectProperty<>();
//    private ObjectProperty<BigDecimal> vlrImposto = new SimpleObjectProperty<>();
//    private ObjectProperty<BigDecimal> vlrLiquido = new SimpleObjectProperty<>();
//    private IntegerProperty estoque = new SimpleIntegerProperty();
//    private IntegerProperty varejo = new SimpleIntegerProperty();
//    private IntegerProperty volume = new SimpleIntegerProperty();
//                         */
//
//        EntradaProduto entradaProduto = new EntradaProduto(SituacaoEntrada.toEnum(situacao), loja, entradaNfe, entradaCte,
//                null, null);
//        entradaProduto.setEntradaProdutoProdutoList(produtoList);
//        return entradaProduto;
//    }
}

package br.com.tlmacedo.cafeperfeito.service;

import com.google.common.base.Splitter;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.REGEX_PONTUACAO;
import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.REGEX_TELEFONE;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class ServiceMascara {

    private static Pattern pattern;
    private static Matcher matcher;
    private String mascara;
    private StringBuilder resultado;

    public static String getTextoMask(int len, String caractere) {
        if (len == 0) len = 120;
        if (caractere == null || caractere.equals(""))
            caractere = TCONFIG.getSis().getMaskCaracter().getUpper();
        return String.format("%s" + len + "d", 0).replace("0", caractere);
    }

    public static String getNumeroMask(int len, int decimal) {
        if (len == 0) len = 12;
        String retorno = String.format("%0" + (len - 1) + "d", 0).replace("0", TCONFIG.getSis().getMaskCaracter().getDigit());
        retorno += "0";
        if (decimal > 0)
            retorno = String.format("%s.%0" + decimal + "d", retorno.substring(decimal), 0);
        return retorno;
    }

    public static String getRgMask(int len) {
        if (len == 0) len = 11;
        return getNumeroMask(len, 0);
    }

    public static String getNumeroMilMask(int len) {
        return getNumeroMask(len, 0) + ".";
    }

    public static HashMap<String, String> getFieldFormatMap(String accessibleText) {
        if (accessibleText.equals("") || accessibleText == null)
            return null;
        return new HashMap<String, String>(Splitter.on(";").omitEmptyStrings().withKeyValueSeparator(Splitter.onPattern("\\:\\:")).split(accessibleText));
    }

    private static String formataNumeroDecimal(String value, int decimal) {
        String sinal = "";
        if (value.substring(0, 1).equals("-"))
            sinal = "-";
        value = Long.valueOf(value.replaceAll("\\D", "")).toString();
        int addZeros = ((decimal + 1) - value.length());
        if (addZeros > 0)
            value = String.format("%0" + addZeros + "d", 0) + value;

        value = value.replaceAll("(\\d{1})(\\d{" + (decimal + 18) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (decimal + 15) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (decimal + 12) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (decimal + 9) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (decimal + 6) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (decimal + 3) + "})$", "$1.$2");
        if (decimal > 0)
            value = value.replaceAll("(\\d{1})(\\d{" + decimal + "})$", "$1,$2");
        return sinal + value;
    }

    public static String getTelefone(String value) {
        String strValue = value.replaceAll("\\D", "").trim();
        if (strValue.length() > 11) strValue = strValue.substring(0, 11);
        pattern = Pattern.compile(REGEX_TELEFONE);
        matcher = pattern.matcher(strValue);
        if (matcher.find()) {
            return String.format("%s%s%s",
                    matcher.group(1) == null
                            ? ""
                            : String.format("(%s) ", matcher.group(1)),
                    matcher.group(2) == null
                            ? ""
                            : String.format("%s-", matcher.group(2)),
                    matcher.group(3) == null
                            ? ""
                            : String.format("%s", matcher.group(3))
            );
        }
        return strValue;
    }

    public void fieldMask(TextField textField, String tipMascara) {
        setMascara(tipMascara);
        textField.textProperty().addListener((ov, o, n) -> {
            StringBuilder resultado = new StringBuilder("");
            int posicao = 0;
            if (n != null && !n.equals("")) {
                try {
                    posicao = textField.getCaretPosition() + ((n.length() > o.length()) ? 1 : 0);
                } catch (Exception ex) {
                    posicao = 0;
                }
                String strValue = n != null ? n : "",
                        value = n != null ? n : "",
                        maskDigit = "";
                if (getMascara().contains("#0.")) {
                    if (strValue.equals(""))
                        strValue = "0";
                    int qtdMax = getMascara().replaceAll(".-/]", "").length();
                    int qtdDecimal = (getMascara().replaceAll("\\D", "").length() - 1);
                    if (strValue.length() > qtdMax)
                        strValue = strValue.substring(0, qtdMax);
                    resultado.append(formataNumeroDecimal(strValue, qtdDecimal));
                } else if (getMascara().contains("(##) ")) {
                    if (value.length() > 2) {
                        resultado.append(getTelefone(value));
                    } else {
                        resultado.append(value);
                    }
                } else if (getMascara().equals("##############") || getMascara().equals("##############")) {
                    if (value.length() >= 13 && Integer.valueOf(value.substring(1, 2)) <= 6)
                        setMascara("##############");
                    else
                        setMascara("#############");
                    resultado.append(value);
                } else {
                    if (strValue.length() > 0) {
                        int digitado = 0;
                        Pattern p = Pattern.compile(REGEX_PONTUACAO);
                        Matcher m = p.matcher(getMascara());
                        if (m.find())
                            value = strValue.replaceAll("\\W", "");
                        for (int i = 0; i < getMascara().length(); i++) {
                            if (digitado < value.length()) {
                                switch ((maskDigit = getMascara().substring(i, i + 1))) {
                                    case "#":
                                    case "0":
                                        if (Character.isDigit(value.charAt(digitado))) {
                                            resultado.append(value.substring(digitado, digitado + 1));
                                            digitado++;
                                        }
                                        break;
                                    case "U":
                                    case "A":
                                    case "L":
                                        if ((Character.isLetterOrDigit(value.charAt(digitado))
                                                || Character.isSpaceChar(value.charAt(digitado))
                                                || Character.isDefined(value.charAt(digitado)))) {
                                            if (maskDigit.equals("L"))
                                                resultado.append(value.substring(digitado, digitado + 1).toLowerCase());
                                            else
                                                resultado.append(value.substring(digitado, digitado + 1).toUpperCase());
                                            digitado++;
                                        }
                                        break;
                                    case "?":
                                    case "*":
                                        resultado.append(value.substring(digitado, digitado + 1));
                                        digitado++;
                                        break;
                                    default:
                                        resultado.append(getMascara().substring(i, i + 1));
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            int finalPosicao = posicao;
//            Platform.runLater(() -> {
            textField.setText(resultado.toString());
//            Platform.runLater(() -> {
            if (getMascara().contains(".0"))
                textField.positionCaret(resultado.length());
            else
                textField.positionCaret(finalPosicao);
        });
//        });
    }


    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public StringBuilder getResultado() {
        return resultado;
    }

    public void setResultado(StringBuilder resultado) {
        this.resultado = resultado;
    }

    public static Pattern getPattern() {
        return pattern;
    }

    public static void setPattern(Pattern pattern) {
        ServiceMascara.pattern = pattern;
    }

    public static Matcher getMatcher() {
        return matcher;
    }

    public static void setMatcher(Matcher matcher) {
        ServiceMascara.matcher = matcher;
    }
}

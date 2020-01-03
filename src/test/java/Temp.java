public class Temp {


    public static void main(String[] args) {
        StringBuffer buf = new StringBuffer("versão 1");
        chamaMetodoPorReferencia1(buf);
        System.out.println(buf);
        chamaMetodoPorReferencia2(buf);
        System.out.println(buf);
    }

    public static void chamaMetodoPorReferencia1(StringBuffer x) {
        x.append("\npassou por aqui.");
    }

    public static void chamaMetodoPorReferencia2(StringBuffer x) {
        x = new StringBuffer("versão 2");
    }


}

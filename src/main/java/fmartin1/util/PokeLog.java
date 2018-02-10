package fmartin1.util;

public class PokeLog {
    private static boolean logging = false;
    private static String offset = "";

    public static void startLogging() { logging = true; }

    public static void stopLogging() { logging = false; }

    public static void log(Object object) {
        if(logging && object!=null) System.out.println(offset + object.toString());
    }

    public static void log(Object object, boolean startProcess) {
        if(logging) log(object);
        if(startProcess) offset = offset + "  ";
        else if(offset.length()>0) offset = offset.substring(0,offset.length()-2);
    }
}

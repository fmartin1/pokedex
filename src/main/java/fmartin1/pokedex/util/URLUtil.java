package fmartin1.pokedex.util;

public class URLUtil {
    public static int getIdFromUrl(String url) {
        return Integer.parseInt(url.split("/")[6]);
    }
}

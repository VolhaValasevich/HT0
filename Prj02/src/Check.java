import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {

    public static boolean ifDirectory (String s) {
        Pattern p = Pattern.compile("^[a-zA-Z]{1}:/(([a-zA-Z0-9/_-]|\\s)+|/{1})");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean ifHtml (String s) {
        Pattern p = Pattern.compile("^[a-zA-Z]{1}:/(([a-zA-Z0-9/_-]|\\s)+|/{1})\\.[hH][tT][mM][lL]$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean ifMp3 (String s) {
        Pattern p = Pattern.compile("^[a-zA-Z]{1}:/(([a-zA-Z0-9/_-]|\\s)+|/{1})\\.[Mm][pP]3$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}

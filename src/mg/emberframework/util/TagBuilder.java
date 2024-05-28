package mg.emberframework.util;

public class TagBuilder {
    public static String enclose(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String bold(String text) {
        return enclose("strong", text);
    }
    
}

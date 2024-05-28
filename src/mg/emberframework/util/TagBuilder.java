package mg.emberframework.util;

public class TagBuilder {
    public static String enclose(String text, String tag) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String bold(String text) {
        return enclose(text, "strong");
    }

    public static String heading(String text, int level) {
        String tag = "h" + level;
        return enclose(text, tag);
    }
    
    public static String paragraph(String text) {
        return enclose(text, "p");
    }
}

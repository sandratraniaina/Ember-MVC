package mg.emberframework.util;

public class FileUtils {
    public static String createFilePath(String dirPath, String fileName) {
        dirPath = dirPath.replaceAll("/+$", "");
        fileName = fileName.replaceAll("^/+", "");

        return dirPath + "/" + fileName;
    }

    public static String getSimpleFileName(String fileName, String extension) {
        return fileName.substring(0, (fileName.length() - extension.length()) - 1);
    }

    private FileUtils() {
    }
}

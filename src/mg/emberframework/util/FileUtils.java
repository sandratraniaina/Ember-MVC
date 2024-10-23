package mg.emberframework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import mg.emberframework.manager.data.File;

public class FileUtils {
    public static File createRequestFile(String name, HttpServletRequest request) throws IOException, ServletException {
        Part part = request.getPart(name);

        String fileName = part.getSubmittedFileName();
        byte[] bytes = getPartByte(part);

        return new File(fileName, bytes);
    } 

    public static byte[] getPartByte(String name, HttpServletRequest request) throws IOException, ServletException {
        Part part = request.getPart(name);
        return getPartByte(part);
    }

    public static byte[] getPartByte(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }

    public static String createFilePath(String dirPath, String fileName) {
        dirPath = dirPath.replaceAll("\\\\+$", "");
        fileName = fileName.replaceAll("^\\\\+", "");

        return dirPath + "\\" + fileName;
    }

    public static String getSimpleFileName(String fileName, String extension) {
        return fileName.substring(0, (fileName.length() - extension.length()) - 1);
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return ""; 
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private FileUtils() {
    }
}

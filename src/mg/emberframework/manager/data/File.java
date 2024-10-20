package mg.emberframework.manager.data;

public class File {
    String fileName;
    byte[] fileBytes;

    // Contructor
    public File() {}
    public File(String fileName, byte[] fileBytes) {
        setFileName(fileName);
        setFileBytes(fileBytes);
    }

    // Getters and setters
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public byte[] getFileBytes() {
        return fileBytes;
    }
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}

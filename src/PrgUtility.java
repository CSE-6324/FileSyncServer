import java.nio.charset.StandardCharsets;

/**
 * @author sharif
 */

public class PrgUtility {
    public static final int TCP_PORT_NUM = 5555;
    public static final String HOST_NAME = "localhost";
    public static final String PROJECT_FILE_PATH = "/Users/sudiptasharif/repos/FileSyncer/project_files/";
    public static final String SERVER_LOG_FILE = PROJECT_FILE_PATH + "log_files/server_log.txt";

    public static String getFileExtension(String fileName) {
        String extension = "";
        int idx = fileName.lastIndexOf(".");
        if (idx > 0) {
            extension = fileName.substring(idx + 1);
        }
        return extension;
    }

    public static boolean hasFileExtension(String fileName) {
        String extension = getFileExtension(fileName);
        return extension.length() > 0;
    }

    public static boolean isFileNameValid(String fileName) {
        byte[] bArray = null;
        boolean answer = true;
        try {
            bArray = fileName.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            answer = false;
        }
        return answer;
    }
}

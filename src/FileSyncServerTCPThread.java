import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author sharif
 */

public class FileSyncServerTCPThread extends Thread {
    private static final String TAG = "FileSyncServerTCPThread";
    private Socket socket = null;
    private static Message message;

    public FileSyncServerTCPThread(Socket socket) {
        super("FileSyncServerTCPThread");
        this.socket = socket;
    }

    public void run() {
        Message msg = new Message();
        final String METHOD_NAME = "run";
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String clientRequest;
            FileSyncTCPProtocol fsp = new FileSyncTCPProtocol();
            while ((clientRequest = in.readLine()) != null) {
                msg.printToTerminal(clientRequest);
                msg = fsp.processInput(clientRequest);
                if (msg.getMessage().equalsIgnoreCase("exit")) {
                    break;
                } else {
                    out.println(msg.getMessage());
                }
            }
            socket.close();
        } catch (IOException e) {
            msg.setErrorMessage(TAG, METHOD_NAME, "IOException", e.getMessage());
            msg.printToTerminal(message.getMessage());
        }
    }
}

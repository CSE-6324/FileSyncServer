import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author sharif
 */

public class FileSyncServerTCPThread extends Thread {
    private Socket socket = null;

    public FileSyncServerTCPThread(Socket socket) {
        super("FileSyncServerTCPThread");
        this.socket = socket;
    }

    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String inputLine, outputLine;
            FileSyncTCPProtocol fsp = new FileSyncTCPProtocol();
            outputLine = fsp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = fsp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("exit"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

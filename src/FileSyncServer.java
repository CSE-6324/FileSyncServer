import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author sharif
 */

public class FileSyncServer {
    public static void main (String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java FileSyncServer <port number>");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new FileSyncServerTCPThread(serverSocket.accept()).start();
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}

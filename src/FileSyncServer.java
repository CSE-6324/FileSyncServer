import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author sharif
 */

public class FileSyncServer {
    private static Message msg;
    public static void main (String[] args) throws IOException {
        boolean listening = true;
        try (ServerSocket tcpServerSocket = new ServerSocket(PrgUtility.TCP_PORT_NUM)) {
            System.out.println("> server listening for tcp connections on port: " + PrgUtility.TCP_PORT_NUM);
            while (listening) {
                new FileSyncServerTCPThread(tcpServerSocket.accept()).start();
            }
        }catch (IOException e) {
            msg.printToTerminal("> could not listen for TCP connection on port " + PrgUtility.TCP_PORT_NUM);
            System.exit(-1);
        }
    }
}

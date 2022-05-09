import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author sharif
 */

public class FileSyncServerApp {
    public static void main (String[] args) throws IOException {
        Message msg = new Message();
        boolean listening = true;
        try (ServerSocket tcpServerSocket = new ServerSocket(PrgUtility.TCP_PORT_NUM)) {
            msg.printToTerminal("> server listening for tcp connection(s) on port: " + PrgUtility.TCP_PORT_NUM);
            while (listening) {
                new FileSyncServerTCPThread(tcpServerSocket.accept()).start();
            }
        }catch (IOException e) {
            msg.printToTerminal("> could not listen for connection on port " + PrgUtility.TCP_PORT_NUM);
            System.exit(-1);
        }
    }
}

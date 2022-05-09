import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author sharif
 */

public class FileSyncTCPProtocol {
    private static final String TAG = "FileSyncTCPProtocol";
    public Message processInput(String clientRequest) {
        Message msg = new Message();
        String[] requestTokens = clientRequest.split("=");
        String clientName = requestTokens[0];
        String request = requestTokens[1];
        String requestValue = requestTokens[2];

        String serverResponse;
        if (request.equalsIgnoreCase("handshake-client")) {
            msg.printToTerminal("client handshake request acknowledged");
            msg.printToTerminal("connected to client: " + clientName);
            serverResponse = clientName +"-client-handshake-acknowledged";
            msg.setMessage(serverResponse);
        }
        else if (request.equalsIgnoreCase("upload")) {
            int udpPort = getFreeLocalPort();
            serverResponse = "UDP_PORT=" + udpPort;
            msg.setMessage(serverResponse);
            msg.printToTerminal(clientName +" client upload request acknowledged for file: " + requestValue);
            msg.printToTerminal("port server listening to receive file from client = " + udpPort);
            UDPFileReceive udpFileReceive = new UDPFileReceive(udpPort, SyncServer.LOCALHOST.getServerFolderPath());
            Thread fileReceiveThread = new Thread(udpFileReceive);
            fileReceiveThread.start();
        }
        return msg;
    }

    public int getFreeLocalPort() {
        final String METHOD_NAME = "getFreePort";
        Message msg = new Message();

        try (ServerSocket socket = new ServerSocket(0)) {
            int portNum = socket.getLocalPort();
            return portNum;
        } catch (IOException e) {
            msg.setErrorMessage(TAG, METHOD_NAME, "IOException" + e.getMessage());
            msg.printToTerminal("");
        }
        throw new IllegalStateException("could not find a free local port");
    }
}

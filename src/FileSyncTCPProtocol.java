import java.io.File;
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
        String requestValue;

        String serverResponse;
        if (request.equalsIgnoreCase("handshake-client")) {
            msg.printToTerminal("client handshake request acknowledged");
            msg.printToTerminal("connected to client: " + clientName);
            serverResponse = clientName +"-client-handshake-acknowledged";
            msg.setMessage(serverResponse);
        }
        else if (request.equalsIgnoreCase("upload")) {
            requestValue = requestTokens[2];
            int udpPort = getFreeLocalPort();
            serverResponse = "UDP_PORT=" + udpPort;
            msg.setMessage(serverResponse);
            msg.printToTerminal(clientName +" client upload request acknowledged for file: " + requestValue);
            msg.printToTerminal("port server listening to receive file from client = " + udpPort);
            receiveFileBlockFromClient(udpPort, clientName);
        } else if (request.equalsIgnoreCase("download")) {
            requestValue = requestTokens[2];
            String udpRequest = requestTokens[3];
            String udpPortNumStr = requestTokens[4];
            msg.printToTerminal(clientName + " client download request acknowledged for file: " + requestValue);
            msg.printToTerminal("to send file to client server will use requested port = " + udpPortNumStr);
            serverResponse = "ok";
            msg.setMessage(serverResponse);
            sendFileBlockToClient(Integer.parseInt(udpPortNumStr), clientName, requestValue);
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

    private synchronized void sendFileBlockToClient(int udpPort, String clientName, String fileBlockName) {
        final String METHOD_NAME = "sendFileBlockToClient";
        Message msg = new Message();
        String fullFilePath = SyncServer.LOCALHOST.getServerFolderPath() + "/" +fileBlockName;
        UDPFileSend udpFileSend = new UDPFileSend("localhost", udpPort, new File(fullFilePath));
        Thread fileSendThread = new Thread(udpFileSend);
        try {
            fileSendThread.start();
        } catch (Exception e) {
            msg.setErrorMessage(TAG, METHOD_NAME, "UnableToSendFileToClient: " + clientName, e.getMessage());
            msg.printToTerminal(msg.getMessage());
        }
    }

    private synchronized void receiveFileBlockFromClient(int udpPort, String clientName) {
        final String METHOD_NAME = "receiveFileBlockFromClient";
        Message msg = new Message();
        UDPFileReceive udpFileReceive = new UDPFileReceive(udpPort, SyncServer.LOCALHOST.getServerFolderPath());
        Thread fileReceiveThread = new Thread(udpFileReceive);
        try {
            fileReceiveThread.start();
        } catch (Exception e) {
            msg.setErrorMessage(TAG, METHOD_NAME, "UnableToReceiveFileFromClient: " + clientName, e.getMessage());
        }
    }
}

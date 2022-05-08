/**
 * @author sharif
 */

public class FileSyncTCPProtocol {

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
            serverResponse = clientName +"-client-upload-request-acknowledged";
            msg.setMessage(serverResponse);
            msg.printToTerminal(clientName +" client upload request acknowledged for file: " + requestValue);
        }
        return msg;
    }
}

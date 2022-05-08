/**
 * @author sharif
 */

public class FileSyncTCPProtocol {

    public Message processInput(String clientRequest) {
        Message msg = new Message();
        String[] requestTokens = clientRequest.split("=");
        String request = requestTokens[0];

        if (request.equalsIgnoreCase("handshake-client")) {
            msg.printToTerminal("> connected to client: " + requestTokens[1]);
            msg.setMessage("handshake-server");
        }
        else if (request.equalsIgnoreCase("upload")) {
            msg.setMessage("Request to Upload File: " + requestTokens[1]);
        } else if (request.equalsIgnoreCase("exit-client")) {
            msg.setMessage("exit-server");
        } else {
            msg.setMessage("TODO: rest");
        }
        return msg;
    }
}

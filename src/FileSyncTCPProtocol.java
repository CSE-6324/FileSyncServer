/**
 * @author sharif
 */

public class FileSyncTCPProtocol {

    public Message processInput(String clientRequest) {
        Message msg = new Message();
        String[] requestTokens = clientRequest.split("~");
        String clientReqCommand = requestTokens[0];

        if (clientReqCommand.equalsIgnoreCase("hello")) {
            msg.setMessage("Hello Client: " + requestTokens[1]);
        }
        else if (clientReqCommand.equalsIgnoreCase("upload")) {
            msg.setMessage("Request to Upload File: " + requestTokens[1]);
        } else if (clientReqCommand.equalsIgnoreCase("exit")) {
            msg.setMessage("Bye from Server.");
        } else {
            msg.setMessage("TODO: rest");
        }
        return msg;
    }
}

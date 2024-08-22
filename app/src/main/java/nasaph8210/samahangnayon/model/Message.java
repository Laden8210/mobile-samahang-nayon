package nasaph8210.samahangnayon.model;

public class Message {

    private String message;
    private boolean isReply;

    public Message() {
    }



    public Message(String message, boolean isReply) {
        this.message = message;
        this.isReply = isReply;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }
}

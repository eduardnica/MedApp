package csie.aplicatielicenta.Models;

public class Messages {

    private String name, id, lastMessage, chatKey;
    private int unseenMessage;


    public Messages(String name, String id, String lastMessage, int unseenMessage, String chatKey) {
        this.name = name;
        this.id = id;
        this.lastMessage = lastMessage;
        this.unseenMessage = unseenMessage;
        this.chatKey = chatKey;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessage() {
        return unseenMessage;
    }

    public String getChatKey() {
        return chatKey;
    }
}

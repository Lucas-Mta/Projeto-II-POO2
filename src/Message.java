/*
 * Representa as mensagens trocadas entre cliente e servidor
 * GPT FEZ ESSA
 */

import java.awt.*;
import java.io.Serializable;

public class Message implements Serializable {
    private TrayIcon.MessageType type;
    private Object data;

    public Message(TrayIcon.MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public TrayIcon.MessageType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}

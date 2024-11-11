package clientServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ElectionData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String question;  // Pergunta da eleição
    private final List<String> options;  // Opções de voto

    public ElectionData(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Pergunta: " + getQuestion()
                + "\nOpções:\n" + getOptions();
    }
}

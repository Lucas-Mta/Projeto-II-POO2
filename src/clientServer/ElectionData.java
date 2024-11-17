package clientServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/** Represents the data structure for an election in the distributed voting system.
  * This class implements Serializable to allow transmission over network connections.
  * It contains the election question and the list of voting options.                    */
public class ElectionData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;        //Serial version UID for serialization
    private final String question;                          // The main question or topic of the election
    private final List<String> options;                     // List of available voting options for the election

    /** Constructs a new ElectionData object with the specified question and options.
      * @param question The main question or topic of the election
      * @param options The list of available voting options              */
    public ElectionData(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    /** Retrieves the election question.
      * @return The question or topic of the election     */
    public String getQuestion() {
        return question;
    }

    /** Retrieves the list of voting options.
      *
      * @return The list of available options for voting   */
    public List<String> getOptions() {
        return options;
    }

    /** Provides a string representation of the election data.
      * The format includes the question and available options,
      * formatted with separators for better readability.
      * @return A formatted string containing the election question and options      */
    @Override
    public String toString() {
        return "\n----------"
                + "\nPergunta: " + getQuestion()
                + "\nOpções: " + getOptions()
                + "\n----------\n";
    }
}

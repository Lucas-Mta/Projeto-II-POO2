package clientServer;

import java.io.Serial;
import java.io.Serializable;

/** Represents a vote in the distributed voting system.
  * This class implements Serializable to enable network transmission of vote data.
  * Each vote contains the voter's CPF (Brazilian ID) and their chosen option.     */
public class Vote implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;         // Serial version UID for serialization
    private final String cpf;                                // The CPF (Brazilian individual taxpayer registry) of the voter
    private final String option;                             // The voting option selected by the voter

    /** Constructs a new Vote object with the specified voter CPF and selected option.
      * @param cpf The CPF identification of the voter
      * @param option The voting option selected by the voter                    */
    public Vote(String cpf, String option) {
        this.cpf = cpf;
        this.option = option;
    }

    /** Retrieves the voter's CPF.
      * @return The CPF identification of the voter            */
    public String getCpf() {
        return cpf;
    }

    /** Retrieves the selected voting option.
      * @return The option chosen by the voter     */
    public String getOption() {
        return option;
    }

    /** Provides a string representation of the vote.
      * The format includes both the CPF and the selected option
      * in a bracketed format for easy reading.
      * @return A formatted string containing the vote information        */
    @Override
    public String toString() {
        return "Voto = [" + cpf + ", " + option + "]";
    }
}

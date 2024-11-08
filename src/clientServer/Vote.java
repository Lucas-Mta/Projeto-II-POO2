package clientServer;

import java.io.Serial;
import java.io.Serializable;

public class Vote implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String cpf; // CPF do cliente
    private String option; // Opção votada pelo eleitor

    public Vote(String cpf, String option) {
        this.cpf = cpf;
        this.option = option;
    }

    public String getCpf() {
        return cpf;
    }

    public String getOption() {
        return option;
    }

    @Override
    public String toString() {
        return "Voto = ["
                + cpf + ", "
                + option + "]";
    }
}

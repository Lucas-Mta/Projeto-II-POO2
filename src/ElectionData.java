/*
 * Contém os dados da eleição
 */

import java.util.Arrays;
import java.util.List;

public class ElectionData {
    private String question;  // Pergunta da eleição
    private List<String> options;  // Opções de voto

    public ElectionData() {
        this.question = "bla bla bla";
        this.options = Arrays.asList("opcao 1", "opcao 2", "opcao 3");
    }

    /* ----------- não sei
    public List<String> getOptions() {
        // Retorna as opções de voto
    }
    --------------- */
}

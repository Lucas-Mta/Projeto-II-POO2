/*
 * Gerencia a votação e a validação dos CPFs
 */

import java.util.HashMap;
import java.util.Map;

public class VoteManager {
    private Map<String, String> votes;  // Mapeamento de CPF para opção de voto
    private ElectionData electionData;

    public VoteManager() {
        this.votes = new HashMap<>();
        this.electionData = new ElectionData();
    }

    public boolean validateVote(String cpf, String option) {
        // Valida se o CPF já votou E se a opção de voto é válida
        return true;
    }

    public void addVote(String cpf, String option) {
        // Adiciona o voto ao Map, se for válido
    }

//    public Map<String, Integer> getResults() {
//        // Retorna o total de votos por opção
//        return /**/;
//    }

}

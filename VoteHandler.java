package clientServer;

import java.util.HashMap;
import java.util.Map;


public class VoteHandler {
    private Map<String, Vote> votes; // Armazena o CPF e o Voto
    private static Map<String, Integer> voteCounts; // Static pode não ser necessário recomendação do eclipse 
    
    public VoteHandler() {
        votes = new HashMap<>();
        voteCounts = new HashMap<>();

    }

    // Método para registrar um voto
    public boolean vote(String cpf, String option) {
        if (votes.containsKey(cpf)) {
            System.out.println("Voto já registrado para este CPF.");
            return false;
        }
        Vote newVote = new Vote(cpf, option);
        votes.put(cpf, newVote); 

        voteCounts.put(option, voteCounts.getOrDefault(option, 0) + 1);
        return true;
    }

    // Método para calcular e retornar os resultados
    public static Map<String, Integer> calculateResults() {
        return new HashMap<>(voteCounts); 
    }
    

}
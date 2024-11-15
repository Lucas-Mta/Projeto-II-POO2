package clientServer;

import java.util.HashMap;
import java.util.Map;

public class VoteHandler {
    private final Map<String, String> votes; // Armazena o CPF e o Voto
    private static Map<String, Integer> voteCounts; // Conta os votos por opção
    
    public VoteHandler() {
        votes = new HashMap<>();
        voteCounts = new HashMap<>();
    }

    // Verifica se um CPF já votou
    public boolean hasVoted(String cpf) {
        return votes.containsKey(cpf);
    }

    // Registra um voto
    public boolean addVote(Vote vote) {
        String cpf = vote.getCpf();
        String option = vote.getOption();

        // Mais uma verificação se o CPF já votou
        if (hasVoted(cpf)) {
            System.out.println("Voto já registrado para este CPF.");
            return false;
        }

        // Registra o voto
        votes.put(cpf, option);

        // Atualiza a contagem de votos para a opção selecionada
        voteCounts.put(option, voteCounts.getOrDefault(option, 0) + 1);

        return true;
    }

    public static Map<String, Integer> calculateResults() {
        return new HashMap<>(voteCounts); // Retorna uma cópia das contagens de votos
    }

}
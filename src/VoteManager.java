//package urna;

import java.util.HashMap;
import java.util.Map;

public class VoteManager {
    private Map<String, Integer> votes; 
    private Map<String, Integer> candidates; 
    public VoteManager() {
        votes = new HashMap<>();
        candidates = new HashMap<>();
    }

    // Método para validar CPF
    private boolean isValidCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");     }

    // Método para registrar um voto
    public boolean vote(String cpf, String candidate) {
        if (!isValidCPF(cpf)) {
            System.out.println("CPF inválido.");
            return false;
        }
        if (votes.containsKey(cpf)) {
            System.out.println("Voto já registrado para este CPF.");
            return false;
        }
        votes.put(cpf, 1);
        candidates.put(candidate, candidates.getOrDefault(candidate, 0) + 1); // Adiciona voto ao candidato
        return true;
    }

      public Map<String, Integer> calculateResults() {
        return candidates;
    }

      public void generateFinalReport() {
        System.out.println("Relatório Final da Eleição:");
        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            System.out.println("Candidato: " + entry.getKey() + ", Votos: " + entry.getValue());
        }
    }
}

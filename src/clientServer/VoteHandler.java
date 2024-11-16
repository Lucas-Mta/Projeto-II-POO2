package clientServer;

import java.util.HashMap;
import java.util.Map;

/** Handles vote management and counting in the distributed voting system.
  * This class is responsible for storing votes, preventing duplicate voting,
  * and calculating election results.                                             */
public class VoteHandler {
    private final Map<String, String> votes;            // Maps CPF to voting option to track individual votes
    private static Map<String, Integer> voteCounts;     // Counts the total votes for each option

    /** Constructs a new VoteHandler instance.
      * Initializes the maps for storing votes and counting vote totals.           */
    public VoteHandler() {
        votes = new HashMap<>();
        voteCounts = new HashMap<>();
    }

    /** Checks if a voter with the given CPF has already voted.
      * @param cpf The CPF (Brazilian ID) to check
      * @return true if the CPF has already been used to vote, false otherwise      */
    public boolean hasVoted(String cpf) {
        return votes.containsKey(cpf);
    }

    /** Registers a new vote in the system.
      * Performs duplicate vote checking and updates vote counts.
      * @param vote The Vote object containing the CPF and selected option
      * @return true if the vote was successfully registered, false if the CPF has already voted    */
    public boolean addVote(Vote vote) {
        String cpf = vote.getCpf();
        String option = vote.getOption();

        if (hasVoted(cpf)) {
            System.out.println("Voto já registrado para este CPF.");
            return false;
        }
        votes.put(cpf, option);
        voteCounts.put(option, voteCounts.getOrDefault(option, 0) + 1);
        return true;
    }

    /** Calculates and returns the current election results.
      * Returns a copy of the vote counts to maintain encapsulation.
      * @return A map containing the total votes for each option               */
    public static Map<String, Integer> calculateResults() {
        return new HashMap<>(voteCounts); // Retorna uma cópia das contagens de votos
    }

}
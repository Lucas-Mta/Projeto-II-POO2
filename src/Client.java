/*
 * Gerencia a conexão com o servidor.
 */

public class Client {
    private int port;
    private String serverAddress;
    private ClientHandler handler;

    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void connect() {
        // Conectar ao servidor e iniciar o ClientHandler
    }

    public boolean hasVoted(String cpf) {
        return false;
    }

    public void sendVote(String cpf, String voteOption) {
        // Envia voto ao servidor via o handler
    }

    public void disconnect() {
        // Desconectar do servidor
    }
}

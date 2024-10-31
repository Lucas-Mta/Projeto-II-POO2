/*
 * Essa classe vai ser responsável por gerenciar as
 * conexões com os clientes e coordenar a eleição.
 */

import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;  // Porta do servidor
    private VoteManager voteManager;  // Gerenciador de votos
    private List<ServerThread> clients;  // Lista de threads? para cada cliente

    public Server(int port) {
        this.port = port;
        this.voteManager = new VoteManager();
        this.clients = new ArrayList<>();
    }

    public void startServer() {
        // Iniciar o servidor e aceitar conexões dos clientes
    }

    public void closeElection() {
        // Encerrar eleição e salvar relatório
    }
}

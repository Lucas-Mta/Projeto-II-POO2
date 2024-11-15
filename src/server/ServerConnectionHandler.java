package server;

import clientServer.ElectionData;
import clientServer.VoteHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;

public class ServerConnectionHandler {
    private final ServerSocket serverSocket;
    private final ExecutorService clientPool;
    private final List<ClientHandler> clientHandlers = new ArrayList<>();
    private final ElectionData electionData;
    private final VoteHandler voteHandler;
    private volatile boolean isRunning = true;

    // Construtor que inicia o servidor e armazena dados da eleição
    public ServerConnectionHandler(int port, ElectionData electionData) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clientPool = Executors.newCachedThreadPool();
        this.electionData = electionData;
        this.voteHandler = new VoteHandler();
        System.out.println("Servidor iniciado na porta " + port);
    }

    // Inicia a escuta para conexões de clientes
    public void startServer() {
        System.out.println("Servidor aguardando conexões...");
        try {
            while (isRunning) {
                // Aceitar novas conexões de clientes
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress());

                // Cria e inicia um novo ClientHandler para cada cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket, electionData, voteHandler);
                clientHandlers.add(clientHandler);
                clientPool.execute(clientHandler); // Executa o cliente em uma thread separada
            }
        } catch (IOException e) {
            if (isRunning) {
                    System.out.println("Erro ao aceitar conexões: " + e.getMessage());
            }
        }
    }

    // Encerra o servidor e desconecta todos os clientes
    public void shutdown() {
        try {
            System.out.println("Encerrando o servidor...");
            isRunning = false;

            // Fechar o socket do servidor
            serverSocket.close();

            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.disconnectClient();
            }

            // Finalizar o pool de threads
            clientPool.shutdown();
            System.out.println("Servidor encerrado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao encerrar o servidor: " + e.getMessage());
        }
    }
}

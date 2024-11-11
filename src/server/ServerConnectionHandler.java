package server;

import clientServer.ElectionData;
import clientServer.Vote;

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
    private final List<ClientHandler> clientHandlers;
    private final ElectionData electionData;
    private volatile boolean isRunning = true; // Flag pra controlar o loop

    // Construtor que inicia o servidor e armazena dados da eleição
    public ServerConnectionHandler(int port, ElectionData electionData) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clientPool = Executors.newCachedThreadPool();
        this.clientHandlers = new ArrayList<>();
        this.electionData = electionData;
        System.out.println("Servidor iniciado na porta " + port);
    }

    // Inicia a escuta para conexões de clientes
    public void startServer() {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress());

                // Cria e inicia um novo ClientHandler para cada cliente
                ClientHandler handler = new ClientHandler(clientSocket, electionData);
                clientHandlers.add(handler);
                clientPool.execute(handler);
            } catch (IOException e) {
                if (isRunning) {
                    System.out.println("Erro ao conectar com o cliente: " + e.getMessage());
                }
            }
        }
    }

    // Encerra o servidor e desconecta todos os clientes
    public void shutdown() throws IOException {
        System.out.println("Encerrando o servidor e desconectando clientes...");
        isRunning = false;
        serverSocket.close();

        for (ClientHandler handler : clientHandlers) {
            handler.disconnectClient();
        }

        clientPool.shutdown();
    }
}

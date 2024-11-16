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

/** Handles server connections for the distributed voting system.
  * This class manages client connections, initializes the server socket,
  * and coordinates the multithreaded handling of client requests.             */
public class ServerConnectionHandler {
    private final ServerSocket serverSocket;                                     // Server socket for accepting client connections
    private final ExecutorService clientPool;                                    // Thread pool for managing concurrent client connections
    private final List<ClientHandler> clientHandlers = new ArrayList<>();        // List to keep track of all connected clients
    private final ElectionData electionData;                                     // Stores election configuration and voting options
    private final VoteHandler voteHandler;                                       // Manages and processes all votes received from clients
    private volatile boolean isRunning = true;                                   // Flag to control server execution state

    /** Initializes the server with specified port and election data.
      * @param port The port number on which the server will listen
      * @param electionData The election configuration data including voting options
      * @throws IOException If there's an error creating the server socket                */
    public ServerConnectionHandler(int port, ElectionData electionData) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clientPool = Executors.newCachedThreadPool();
        this.electionData = electionData;
        this.voteHandler = new VoteHandler();
        System.out.println("Servidor iniciado na porta " + port);
    }

    /** Starts the server and begins accepting client connections.
      * Uses a thread pool to handle multiple clients concurrently.
      * Continues running until shutdown is called.                                       */
    public void startServer() {
        System.out.println("Servidor aguardando conexões...");
        try {
            while (isRunning) {
                // Accept new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress());

                // Create and initialize a new handler for the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, electionData, voteHandler);
                clientHandlers.add(clientHandler);

                // Execute client handler in a separate thread from the pool
                clientPool.execute(clientHandler); // Executa o cliente em uma thread separada
            }
        } catch (IOException e) {
            if (isRunning) {
                    System.out.println("Erro ao aceitar conexões: " + e.getMessage());
            }
        }
    }

    /** Performs a graceful shutdown of the server.
      * Closes all client connections, stops accepting new connections,
      * and cleanly terminates the thread pool.                                      */
    public void shutdown() {
        try {
            System.out.println("Encerrando o servidor...");
            isRunning = false;

            serverSocket.close();

            // Disconnect all connected clients
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.disconnectClient();
            }

            clientPool.shutdown();
            System.out.println("Servidor encerrado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao encerrar o servidor: " + e.getMessage());
        }
    }
}

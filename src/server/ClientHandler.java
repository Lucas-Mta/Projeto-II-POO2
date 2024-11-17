package server;

import clientServer.ElectionData;
import clientServer.Vote;
import clientServer.VoteHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** ClientHandler manages individual client connections in the distributed voting system.
  * It runs as a separate thread for each connected client, handling all communication
  * including login validation, vote processing, and election data distribution.             */
public class ClientHandler implements Runnable {
    private final Socket clientSocket;                      // Socket connection to the client
    private final ElectionData electionData;                // Election configuration and options data
    private final VoteHandler voteHandler;                  // Manages vote storage and validation
    private ObjectOutputStream out;                         // Stream for sending objects to client
    private ObjectInputStream in;                           // Stream for receiving objects from client
    private boolean isConnected = true;                     // Flag indicating if client connection is active

    /** Creates a new client handler with the specified socket and election data.
      * @param clientSocket Socket connection to the client
      * @param electionData Current election configuration
      * @param voteHandler Vote storage and validation manager                             */
    public ClientHandler(Socket clientSocket, ElectionData electionData, VoteHandler voteHandler) {
        this.clientSocket = clientSocket;
        this.electionData = electionData;
        this.voteHandler = voteHandler;
    }

    /** Main execution loop for handling client communication.
      * Initializes streams, sends election data, and processes client messages:
      * - CPF validation requests
      * - Vote submissions
      * - Disconnect requests                                                               */
    @Override
    public void run() {
        try {
            // Initialize communication streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Send initial election configuration
            sendElectionData();

            // Process client messages until disconnection
            while (isConnected) {
                Object receivedObject = in.readObject();

                if (receivedObject instanceof String) {
                    String cpf = (String) receivedObject;
                    processLogin(cpf);
                } else if (receivedObject instanceof Vote vote) {
                    processVote(vote);
                } else if ("DISCONNECT".equals(receivedObject)) {
                    isConnected = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (e.getMessage().contains("Connection reset")) {
                System.out.println("Cliente desconectado.");
            } else {
                System.out.println("Erro na conexão com o cliente: " + e.getMessage());
            }
        }
    }

    /** Sends election configuration data to the client.
      * This includes voting options and any relevant election parameters.
      *
      * @throws IOException If there's an error sending the data                   */
    private void sendElectionData() throws IOException {
        out.writeObject(electionData);
        out.flush();
        System.out.println("Dados da eleição enviados ao cliente.");
    }

    /** Validates if a CPF has already voted in the election.
      * Sends appropriate response message to the client.
      * @param cpf The CPF number to validate                                    */
    private void processLogin(String cpf) {
        if (voteHandler.hasVoted(cpf)) {
            sendMessage("CPF já votou");
        } else {
            sendMessage("CPF não votou");
        }
    }
    /** Processes a vote submission from the client.
      * Validates and records the vote, then sends confirmation message.
      * @param vote The Vote object containing the CPF and voting choice           */
    private void processVote(Vote vote) {
        if (voteHandler.addVote(vote)) {
            sendMessage("Voto registrado com sucesso!");
        } else {
            sendMessage("Erro ao registrar o voto.");
        }
    }

    /** Sends a message to the connected client.
      * @param message The message to send                                       */
    private void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem ao cliente: " + e.getMessage());
        }
    }
    /** Cleanly closes all connections and streams with the client.
      * Should be called when shutting down the connection or handling errors.     */
    public void disconnectClient() {
        try {
            isConnected = false;
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Cliente desconectado.\n");
        } catch (IOException e) {
            System.out.println("Erro ao desconectar o cliente: " + e.getMessage());
        }
    }
}
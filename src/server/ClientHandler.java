package server;

/* Classe para gerenciar as conexões com os clientes */

import clientServer.ElectionData;
import clientServer.Vote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ElectionData electionData;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isConnected = true;

    public ClientHandler(Socket clientSocket, ElectionData electionData) {
        this.clientSocket = clientSocket;
        this.electionData = electionData;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Enviar os dados da votação para o cliente
            sendElectionData();

            // Escuta a resposta de voto do cliente
            while (isConnected) {
                Object receivedObject = in.readObject();
                if (receivedObject instanceof Vote) {
                    Vote vote = (Vote) receivedObject;
                    processVote(vote);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na conexão com o cliente: " + e.getMessage());
        } finally {
            disconnectClient();
        }
    }

    // Envia o ElectionData ao Cliente
    private void sendElectionData() throws IOException {
        // Fazer
    }

    // Manda o voto recebido do Cliente para ser processado
    private void processVote(Vote vote) {
        // Fazer
    }

    // Encerra a conexão com o cliente
    public void disconnectClient() {
        isConnected = false;
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.out.println("Erro ao desconectar o cliente: " + e.getMessage());
        }
        System.out.println("Cliente desconectado.");

    }
}

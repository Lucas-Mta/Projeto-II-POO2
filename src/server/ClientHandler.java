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

    // Construtor -> Recebe o socket do cliente e os dados da eleição
    public ClientHandler(Socket clientSocket, ElectionData electionData) {
        this.clientSocket = clientSocket;
        this.electionData = electionData;
    }

    @Override
    public void run() {
        try {
            // Iniciar streams de comunicação
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Enviar os dados da votação para o cliente
            sendElectionData();

            // Loop pra escutar mensagens do cliente
            while (isConnected) {
                Object receivedObject = in.readObject();

                if (receivedObject instanceof Vote vote) {
                    processVote(vote);
                } else if ("DISCONNECT".equals(receivedObject)) {
                    // Cliente desconectou
                    isConnected = false;
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
        out.writeObject(electionData);
        out.flush();
        System.out.println("Dados da eleição enviados ao cliente.");
    }

    // Manda o voto recebido do Cliente para ser processado
    private void processVote(Vote vote) {
        /* if (voteHandler.hasVoted(vote.getCpf())) {
            sendMessage("Erro: CPF já votou.");
        } else {
            voteHandler.registerVote(vote.getCpf(), vote.getOption());
            sendMessage("Voto registrado com sucesso!");
        } */
    }

    // Envia uma mensagem ao cliente
    private void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem ao cliente: " + e.getMessage());
        }
    }

    // Encerra a conexão com o cliente
    public void disconnectClient() {
        try {
            isConnected = false;
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Cliente desconectado.");
        } catch (IOException e) {
            System.out.println("Erro ao desconectar o cliente: " + e.getMessage());
        }
    }
}

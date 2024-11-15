package server;

/* Classe para gerenciar as conexões com os clientes */

import clientServer.ElectionData;
import clientServer.Vote;
import clientServer.VoteHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ElectionData electionData;
    private final VoteHandler voteHandler;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isConnected = true;

    // Construtor -> Recebe o socket do cliente e os dados da eleição
    public ClientHandler(Socket clientSocket, ElectionData electionData, VoteHandler voteHandler) {
        this.clientSocket = clientSocket;
        this.electionData = electionData;
        this.voteHandler = voteHandler;
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

                if (receivedObject instanceof String) {
                    String cpf = (String) receivedObject;
                    processLogin(cpf);
                } else if (receivedObject instanceof Vote vote) {
                    processVote(vote);
                } else if ("DISCONNECT".equals(receivedObject)) {
                    // Cliente desconectou
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

    // Envia o ElectionData ao Cliente
    private void sendElectionData() throws IOException {
        out.writeObject(electionData);
        out.flush();
        System.out.println("Dados da eleição enviados ao cliente.");
    }

    // Verifica se o CPF já votou
    private void processLogin(String cpf) {
        if (voteHandler.hasVoted(cpf)) {
            sendMessage("CPF já votou");
        } else {
            sendMessage("CPF não votou");
        }
    }

    // Processa o voto recebido e registra no VoteHandler
    private void processVote(Vote vote) {
        if (voteHandler.addVote(vote)) {
            sendMessage("Voto registrado com sucesso!");
        } else {
            sendMessage("Erro ao registrar o voto.");
        }
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
            System.out.println("Cliente desconectado.\n");
        } catch (IOException e) {
            System.out.println("Erro ao desconectar o cliente: " + e.getMessage());
        }
    }
}

package client;

import clientServer.ElectionData;
import clientServer.Vote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionHandler {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ElectionData electionData;

    // Construtor que conecta ao servidor e recebe dados de eleição
    public ClientConnectionHandler(String serverAdress, int port) throws IOException, ClassNotFoundException {
        socket = new Socket(serverAdress, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        // Recebe os dados da eleição do servidor
        this.electionData = (ElectionData) in.readObject();
    }

    public ElectionData getElectionData() {
        return electionData;
    }

    // Envia o voto para o servidor
    public void sendVote(Vote vote) throws IOException {
        out.writeObject(vote);
        out.flush();
    }

    // Envia o CPF ao servidor
    public void sendCPFToServer(String cpf) throws IOException {
        out.writeObject(cpf);
        out.flush();
    }

    // Recebe as mensagens do servidor
    public String receiveServerResponse() throws IOException, ClassNotFoundException {
        Object response = in.readObject();
        return (String) response;
    }

    // Fecha a conexão
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}

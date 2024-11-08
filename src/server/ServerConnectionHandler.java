package server;

import clientServer.ElectionData;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectionHandler {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;

    // Construtor que inicia o servidor
    public ServerConnectionHandler(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado na porta " + port);
    }

    // Espera pela conexão de um cliente
    public void waitForClient() throws IOException {
        clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
    }

    // Envia ElectionData ao cliente
    public void sendElectionData(ElectionData electionData) throws IOException {
        if (out != null) {
            out.writeObject(electionData);
            out.flush();
            System.out.println("ElectionData enviado ao cliente.");
        }
    }

    // Encerra a conexão
    public void close() throws IOException {
        if (out != null) out.close();
        if (clientSocket != null) clientSocket.close();
        if (serverSocket != null) serverSocket.close();
    }
}

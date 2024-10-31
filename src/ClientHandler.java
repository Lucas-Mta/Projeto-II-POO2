/*
 * Gerencia a comunicação do lado do cliente com o servidor.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void sendVote(String cpf, String voteOption) {
        // Enviar objeto voto ao servidor
    }

}

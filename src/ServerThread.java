/*
 * Responsável por gerenciar a comunicação
 * com um cliente específico.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ServerThread(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }
}

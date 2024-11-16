package client;

import clientServer.ElectionData;
import clientServer.Vote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Manages the connection between the client and the server in the distributed voting system.
  * This class is responsible for establishing the TCP/IP connection with the server,
  * managing object streams for communication, and performing operations for sending/receiving
  * voting-related data.                                                                         */
public class ClientConnectionHandler {
    private Socket socket;                  // Socket for connecting to the server
    private ObjectOutputStream out;         // Output stream for sending objects to the server
    private ObjectInputStream in;           // Input stream for receiving objects from the server
    private ElectionData electionData;      // Election data received from the server

    /** Establishes a connection with the server and initializes the communication streams.
     * After connecting, it automatically receives the election data from the server.
     * @param serverAddress IP address or hostname of the server
     * @param port Port on which the server is listening
     * @throws IOException If an error occurs during the connection or stream creation
     * @throws ClassNotFoundException If an error occurs while deserializing the election data
     */
    public ClientConnectionHandler(String serverAddress, int port) throws IOException, ClassNotFoundException {
        socket = new Socket(serverAddress, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        // Receives the election data from the server
        this.electionData = (ElectionData) in.readObject();
    }

    /** Returns the election data received from the server.
      * @return ElectionData object containing the election information              */
    public ElectionData getElectionData() {
        return electionData;
    }

    /** Sends a vote to the server.
      * The vote is serialized and transmitted through the ObjectOutputStream.
      * @param vote Vote object containing the voter's CPF and their choice
      * @throws IOException If an error occurs while sending the vote                */
    public void sendVote(Vote vote) throws IOException {
        out.writeObject(vote);
        out.flush();
    }

    /** Envia um CPF ao servidor para validação.
      * O CPF é serializado e transmitido através do ObjectOutputStream.
      * @param cpf String contendo o CPF a ser validado
      * @throws IOException Em caso de erro no envio do CPF          */
    public void sendCPFToServer(String cpf) throws IOException {
        out.writeObject(cpf);
        out.flush();
    }

    /** Receives and processes a response from the server.
      * Waits for receiving an object from the server and converts it to a String.
      * @return String containing the server's response message
      * @throws IOException If an error occurs while reading the response
      * @throws ClassNotFoundException If an error occurs while deserializing the response      */
    public String receiveServerResponse() throws IOException, ClassNotFoundException {
        Object response = in.readObject();
        return (String) response;
    }

    /** Closes the connection with the server.
      * Terminates all streams and the connection socket.
      * @throws IOException If an error occurs while closing the connections         */
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}

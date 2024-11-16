package client;

import clientServer.ElectionData;
import clientServer.Vote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Gerencia a conexão entre o cliente e o servidor no sistema de votação distribuído.
  * Esta classe é responsável por estabelecer a conexão TCP/IP com o servidor,
  * gerenciar streams de objetos para comunicação e realizar operações de envio/recebimento
  * de dados relacionados à votação.                                              */
public class ClientConnectionHandler {
    private Socket socket;                  //Socket para conexão com o servidor
    private ObjectOutputStream out;         //Stream de saída para envio de objetos ao servidor
    private ObjectInputStream in;           //Stream de entrada para recebimento de objetos do servidor
    private ElectionData electionData;      //Dados da eleição recebidos do servidor

    /** Estabelece uma conexão com o servidor e inicializa os streams de comunicação.
      * Após a conexão, recebe automaticamente os dados da eleição do servidor.
      * @param serverAdress Endereço IP ou hostname do servidor
      * @param port Porta na qual o servidor está escutando
      * @throws IOException Em caso de erro na conexão ou criação dos streams
      * @throws ClassNotFoundException Se houver erro ao deserializar os dados da eleição */
    public ClientConnectionHandler(String serverAdress, int port) throws IOException, ClassNotFoundException {
        socket = new Socket(serverAdress, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        // Recebe os dados da eleição do servidor
        this.electionData = (ElectionData) in.readObject();
    }

    /** Retorna os dados da eleição recebidos do servidor.
      * @return Objeto ElectionData contendo as informações da eleição   */
    public ElectionData getElectionData() {
        return electionData;
    }

    /** Envia um voto para o servidor.
      * O voto é serializado e transmitido através do ObjectOutputStream.
      * @param vote Objeto Vote contendo o CPF do eleitor e sua escolha
      * @throws IOException Em caso de erro no envio do voto          */
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

    /** Recebe e processa uma resposta do servidor.
      * Aguarda o recebimento de um objeto do servidor e o converte para String.
      * @return String contendo a mensagem de resposta do servidor
      * @throws IOException Em caso de erro na leitura da resposta
      * @throws ClassNotFoundException Se houver erro ao deserializar a resposta    */
    public String receiveServerResponse() throws IOException, ClassNotFoundException {
        Object response = in.readObject();
        return (String) response;
    }

    /** Fecha a conexão com o servidor.
      * Encerra todos os streams e o socket de conexão.
      * @throws IOException Em caso de erro ao fechar as conexões */
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}

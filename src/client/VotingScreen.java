package client;

import clientServer.ElectionData;
import clientServer.Vote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VotingScreen extends JFrame {
    private ElectionData electionData;
    private List<JRadioButton> optionButtons;
    private ButtonGroup buttonGroup;
    private JButton voteButton;
    private String cpf;

    public VotingScreen(ElectionData electionData, String cpf) {
        this.electionData = electionData;
        this.cpf = cpf;
        this.optionButtons = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("Tela de Votação - Sistema de Votação");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header -> Pergunta da votação
        JLabel questionLabel = new JLabel("<html><div style='text-align: center;'>"
                + electionData.getQuestion() + "</div></html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        // Painel de Opções de Voto
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Botões ou Checkbox pra as opções
        buttonGroup = new ButtonGroup();
        for (String option : electionData.getOptions()) {
            JRadioButton optionButton = new JRadioButton(option);
            optionButton.setFont(new Font("Arial", Font.PLAIN, 16));
            buttonGroup.add(optionButton);
            optionButtons.add(optionButton);
            optionsPanel.add(optionButton);
        }

        // Adiciona o painel de opções ao centro do painel principal
        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

        // Footer -> Botão de Votar
        voteButton = new JButton("Confirmar Voto");
        voteButton.setFont(new Font("Arial", Font.BOLD, 14));
        voteButton.setBackground(new Color(100, 149, 237));
        voteButton.setForeground(Color.WHITE);
        voteButton.setFocusPainted(false);
        voteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        voteButton.addActionListener(e -> confirmVote());
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.add(voteButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Confirmação de voto
    private void confirmVote() {
        String selectedOption = null;

        for (JRadioButton option : optionButtons) {
            if (option.isSelected()) {
                selectedOption = option.getText();
                break;
            }
        }

        if (selectedOption != null) {

            // Cria um objeto Vote
            Vote vote = new Vote(cpf, selectedOption);

            // Vai enviar o objeto para o servidor
            sendVoteToServer(vote);

            JOptionPane.showMessageDialog(this, "Voto confirmado para: " + selectedOption);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma opção antes de confirmar.");
        }
    }

    private void sendVoteToServer(Vote vote) {
        // Implementar a lógica para enviar voto ao servidor via TCP/IP
        // Isso envolve estabelecer uma conexão de socket com o servidor e enviar o objeto serializado
        // A classe Vote já implementa Serializable
        // Exemplo de código de envio:
        // socketOutputStream.writeObject(vote); // Exemplo fictício de envio pelo socket

    }
}

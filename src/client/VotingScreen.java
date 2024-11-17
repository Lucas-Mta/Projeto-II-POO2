package client;

import clientServer.ElectionData;
import clientServer.Vote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** A graphical user interface for the voting screen in the distributed voting system.
  * This class represents the screen where users can cast their votes after logging in.
  * It displays the election question and voting options as radio buttons.           */
public class VotingScreen extends JFrame {
    private ElectionData electionData;                             // Election data containing the question and voting options
    private List<JRadioButton> optionButtons;                      // List of radio buttons for voting options
    private ButtonGroup buttonGroup;                               // Button group to ensure only one option can be selected
    private JButton voteButton;                                    // Button to confirm and submit the vote
    private String cpf;                                            // User's CPF (Brazilian individual taxpayer registry identification)
    private ClientConnectionHandler clientConnectionHandler;       // Handler for client-server communication

    /** Constructs a new voting screen with the specified election data and user information.
      * @param electionData The data containing the election question and options
      * @param cpf The user's CPF identification
      * @param clientConnectionHandler Handler for managing server communication              */
    public VotingScreen(ElectionData electionData, String cpf, ClientConnectionHandler clientConnectionHandler) {
        this.electionData = electionData;
        this.cpf = cpf;
        this.clientConnectionHandler = clientConnectionHandler;
        this.optionButtons = new ArrayList<>();
        initUI();
    }

    /** Initializes the user interface components of the voting screen.
      * Sets up the main panel, question display, voting options, and confirmation button.
      * The screen is divided into three main sections:
      * - Header: Displays the election question
      * - Center: Shows the voting options as radio buttons
      * - Footer: Contains the vote confirmation button                               */
    private void initUI() {
        setTitle("Tela de Votação - Sistema de Votação");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel questionLabel = new JLabel("<html><div style='text-align: center;'>"
                + electionData.getQuestion() + "</div></html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        buttonGroup = new ButtonGroup();
        for (String option : electionData.getOptions()) {
            JRadioButton optionButton = new JRadioButton(option);
            optionButton.setFont(new Font("Arial", Font.PLAIN, 16));
            buttonGroup.add(optionButton);
            optionButtons.add(optionButton);
            optionsPanel.add(optionButton);
        }

        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

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

    /** Handles the vote confirmation process when the user clicks the confirm button.
      * Validates the selection, creates a Vote object, and sends it to the server.
      * Displays appropriate messages for successful submission or errors.       */
    private void confirmVote() {
        String selectedOption = null;

        for (JRadioButton option : optionButtons) {
            if (option.isSelected()) {
                selectedOption = option.getText();
                break;
            }
        }

        if (selectedOption != null) {
            Vote vote = new Vote(cpf, selectedOption);

            try {
                sendVoteToServer(vote);
                JOptionPane.showMessageDialog(this, "Voto confirmado para: " + selectedOption);
                dispose();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao enviar o voto. Tente novamente.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma opção antes de confirmar.");
        }
    }
    /** Sends the vote to the server through the client connection handler.
      * @param vote The Vote object containing the user's CPF and selected option
      * @throws IOException If there is an error in sending the vote to the server   */
    private void sendVoteToServer(Vote vote) throws IOException {
        clientConnectionHandler.sendVote(vote);
    }
}

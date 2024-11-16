package server;

import clientServer.ElectionData;
import clientServer.VoteHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

/** AdminScreen represents the administrative interface for the distributed voting system.
  * This class provides a graphical user interface for administrators to:
  * - Create new voting sessions
  * - Configure voting questions and options
  * - Start and end voting sessions
  * - View voting results
  * The interface is built using Swing components and follows a responsive design pattern.  */
public class AdminScreen extends JFrame {
    private JTextField questionField;                       // Field for entering the voting question
    private ArrayList<JTextField> optionFields;             // List of fields for entering voting options
    private JPanel optionsPanel;                            // Panel containing the voting options
    private JPanel footerPanel;                             // Panel containing the control buttons
    private ServerConnectionHandler serverHander;           // Handles server connections and communication

    /** Constructs the initial admin interface with a button to create new voting sessions
      * and a help option. Sets up the main window with proper sizing and positioning.        */
    public AdminScreen() {
        // Configuração da Janela Principal
        setTitle("Administração - Sistema de Votação");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel initialPanel = new JPanel(new GridBagLayout());
        initialPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;

        JButton createVoteButton = new JButton("Nova Votação");
        createVoteButton.setFont(new Font("Arial", Font.BOLD, 16));
        createVoteButton.setBackground(new Color(0, 102, 204));
        createVoteButton.setForeground(Color.WHITE);
        createVoteButton.setFocusPainted(false);
        createVoteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createVoteButton.addActionListener(e -> {
            this.dispose();
            openVotingSetup();
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        initialPanel.add(createVoteButton, gbc);

        JLabel helpLabel = new JLabel("Precisa de ajuda? Clique aqui");
        helpLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        helpLabel.setForeground(Color.GRAY);
        helpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showHelp();
            }
        });
        gbc.gridy = 1;
        initialPanel.add(helpLabel, gbc);

        add(initialPanel);
        setVisible(true);
    }

    /** Opens the voting setup interface where the administrator can:
      * - Enter the voting question
      * - Add multiple voting options
      * - Start the voting session
      * The interface includes a scrollable panel for options and control buttons
      * in the footer.                                                                     */
    private void openVotingSetup() {
        JFrame votingFrame = new JFrame("Configurações da Nova Votação");
        votingFrame.setSize(500, 400);
        votingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        votingFrame.setLocationRelativeTo(null);
        votingFrame.setResizable(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel questionLabel = new JLabel("Pergunta da Votação:");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(questionLabel, BorderLayout.NORTH);

        questionField = new JTextField();
        questionField.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(questionField, BorderLayout.CENTER);

        votingFrame.add(headerPanel, BorderLayout.NORTH);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionFields = new ArrayList<>();
        addOptionField();

        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        votingFrame.add(mainPanel, BorderLayout.CENTER);

        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(0, 102, 204));

        JButton addOptionButton = new JButton("Adicionar Opção");
        addOptionButton.setBackground(new Color(0, 153, 255));
        addOptionButton.setFont(new Font("Arial", Font.BOLD, 12));
        addOptionButton.setForeground(Color.WHITE);
        addOptionButton.setFocusPainted(false);
        addOptionButton.addActionListener(e -> {
            addOptionField();
            votingFrame.revalidate();
            votingFrame.repaint();
        });

        JButton startVoteButton = new JButton("Iniciar Votação");
        startVoteButton.setBackground(new Color(0, 153, 255));
        startVoteButton.setFont(new Font("Arial", Font.BOLD, 12));
        startVoteButton.setForeground(Color.WHITE);
        startVoteButton.setFocusPainted(false);
        startVoteButton.addActionListener(e -> startVoting(votingFrame));

        JButton backButton = new JButton("Voltar");
        backButton.setBackground(new Color(0, 153, 255));
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            votingFrame.dispose();
            new AdminScreen();
        });

        footerPanel.add(addOptionButton);
        footerPanel.add(startVoteButton);
        footerPanel.add(backButton);

        votingFrame.add(footerPanel, BorderLayout.SOUTH);

        votingFrame.setVisible(true);
    }

    /** Adds a new text field to the options panel for entering an additional
      * voting option. The new field is automatically added to the optionFields list
      * and displayed in the UI.                                                            */
    private void addOptionField() {
        JTextField optionField = new JTextField(20);
        optionFields.add(optionField);
        optionsPanel.add(optionField);
    }

    /** Initiates the voting session with the configured question and options.
      * Validates the input to ensure there is a question and at least two options.
      * Starts the server and updates the UI to show the active voting session.
      * @param votingFrame The JFrame containing the voting configuration                    */
    private void startVoting(JFrame votingFrame) {
        String question = questionField.getText().trim();
        ArrayList<String> options = new ArrayList<>();

        for (JTextField optionField : optionFields) {
            String option = optionField.getText().trim();
            if (!option.isEmpty()) {
                options.add(option);
            }
        }

        if (!question.isEmpty() && options.size() > 1) {
            // Creates the ElectionData object that contains the voting information
            ElectionData electionData = new ElectionData(question, options);

            try {
                serverHander = new ServerConnectionHandler(1234, electionData); // Porta de exemplo
                new Thread(serverHander::startServer).start(); // Starts the server in a new thread
                JOptionPane.showMessageDialog(votingFrame, "Votação Iniciada com Sucesso!");

                optionsPanel.removeAll();
                footerPanel.removeAll();
                questionField.setEditable(false);

                JLabel optionsLabel = new JLabel("Opções:");
                optionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
                optionsPanel.add(optionsLabel);

                for (String option : electionData.getOptions()) {
                    JLabel optionLabel = new JLabel("\n" + option);
                    optionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    optionsPanel.add(optionLabel);
                }

                JButton endVoteButton = new JButton("Encerrar Votação");
                endVoteButton.setBackground(new Color(0, 102, 204));
                endVoteButton.setFont(new Font("Arial", Font.BOLD, 12));
                endVoteButton.setForeground(Color.WHITE);
                endVoteButton.setFocusPainted(false);
                endVoteButton.addActionListener(e -> {
                    try {
                        endVoting(votingFrame);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                footerPanel.add(endVoteButton);

                votingFrame.revalidate();
                votingFrame.repaint();
                System.out.println(electionData);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(votingFrame, "Erro ao iniciar o servidor: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(votingFrame, "Insira uma pergunta e ao menos duas opções de resposta.");
        }
    }

    /** Ends the current voting session, disconnects all clients, and displays the results.
      * Shuts down the server and launches the report generator to show voting statistics.
      * @param votingFrame The JFrame of the active voting session
      * @throws IOException If there's an error while shutting down the server             */
    private void endVoting(JFrame votingFrame) throws IOException {
        // Finaliza o servidor e desconecta todos os clientes
        if (serverHander != null) {
            serverHander.shutdown();
        }

        JOptionPane.showMessageDialog(votingFrame, "Votação Encerrada! Resultados disponíveis.");
        votingFrame.dispose();

        new ReportGenerator(VoteHandler.calculateResults()).setVisible(true);
    }

    /** Displays the help screen with information about using the admin interface.
      * Creates and shows a new AdminHelpScreen instance.                                  */
    private void showHelp() {
        new AdminHelpScreen(this).setVisible(true);
    }

    /** Main entry point for the admin interface.
      * Creates and displays the initial admin screen.
      * @param args Command line arguments (not used)     */
    public static void main(String[] args) {
        new AdminScreen();
    }
}
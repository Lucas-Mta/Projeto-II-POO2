package client;

import clientServer.CPFValidator;
import clientServer.ElectionData;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;

/** Represents the login screen of the distributed voting system.
  * This class provides a graphical user interface for users to input their CPF
  * and access the voting system.
  * It handles CPF validation and connects to the server to check if the user has already voted. */
public class LoginScreen extends JFrame {
    private JFormattedTextField cpfField;                          // Field for CPF input with formatting mask
    private ClientConnectionHandler clientConnectionHandler;       // Handles the connection with the server
    private ElectionData electionData;                             // Stores election data received from server


    /** Creates a new login screen and establishes connection with the server.
      * @param serverAdress The IP address or hostname of the server
      * @param port The port number where the server is listening        */
    public LoginScreen(String serverAdress, int port) {
        try {
            clientConnectionHandler = new ClientConnectionHandler(serverAdress, port);
            electionData = clientConnectionHandler.getElectionData();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o servidor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        initUI();
    }

    /** Initializes the user interface components.
      * Sets up the login screen with CPF input field, submit button, and help link.
      * Configures the layout, styling, and event handlers for all UI components.   */
    private void initUI() {
        setTitle("Login - Sistema de Votação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel instructionLabel = new JLabel("Digite seu CPF para votar:");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(instructionLabel, gbc);


        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(cpfLabel, gbc);

        cpfField = new JFormattedTextField(createCpfFormatter());
        cpfField.setColumns(8);
        cpfField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        JButton submitButton = new JButton("Confirmar");
        submitButton.setBackground(new Color(100, 149, 237));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();

                // Validate CPF format and digits
                if (CPFValidator.isValid(cpf)) {
                    try {
                        // Send CPF to server for duplicate vote check
                        clientConnectionHandler.sendCPFToServer(cpf);
                        String serverResponse = clientConnectionHandler.receiveServerResponse();
                        // Handle different server responses
                        if ("CPF já votou".equals(serverResponse)) {
                            JOptionPane.showMessageDialog(LoginScreen.this, "CPF já votou!", "Erro", JOptionPane.ERROR_MESSAGE);
                        } else if ("CPF não votou".equals(serverResponse)) {
                            openVotingScreen(cpf); // Proceed to voting screen
                        } else {
                            JOptionPane.showMessageDialog(LoginScreen.this, "Erro desconhecido!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(LoginScreen.this, "Erro ao comunicar com o servidor: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        JLabel helpLabel = new JLabel("Precisa de ajuda? Clique aqui");
        helpLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        helpLabel.setForeground(new Color(0, 102, 204));
        helpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add click listener for help link
        helpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openHelpScreen();
            }
        });
        gbc.gridy = 3;
        panel.add(helpLabel, gbc);

        add(panel);
    }

    /** Creates a formatter for the CPF input field.
      * Applies the mask format ###.###.###-## to ensure proper CPF formatting.
      * @return MaskFormatter configured for CPF format              */
    private MaskFormatter createCpfFormatter() {
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');
            return cpfFormatter;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Opens the voting screen after successful CPF validation.
      * Closes the current login screen and creates a new voting screen instance.
      * @param cpf The validated CPF to be used in the voting process        */
    private void openVotingScreen(String cpf) {
        this.dispose();
        new VotingScreen(electionData, cpf, clientConnectionHandler).setVisible(true);
    }

    /** Opens the help screen when the help link is clicked.
      * Creates and displays a new help screen instance.                       */
    private void openHelpScreen() {
        new ClientHelpScreen(this).setVisible(true);
    }



    /** Main entry point of the application.
      * Prompts for server configuration and initializes the login screen.
      * Uses SwingUtilities.invokeLater to ensure thread safety in Swing.
      * @param args Command line arguments (not used)                 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String serverAddress = JOptionPane.showInputDialog(null, "Digite o endereço IP do servidor:", "Configuração do Servidor", JOptionPane.QUESTION_MESSAGE);
            String portInput = JOptionPane.showInputDialog(null, "Digite a porta do servidor:", "Configuração do Servidor", JOptionPane.QUESTION_MESSAGE);

            int port;
            try {
                port = Integer.parseInt(portInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Porta inválida! Usando porta padrão 1234.", "Aviso", JOptionPane.WARNING_MESSAGE);
                port = 1234;
            }

            new LoginScreen(serverAddress, port).setVisible(true);
        });
    }
}

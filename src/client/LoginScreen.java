package client;

import clientServer.CPFValidator;
import clientServer.ElectionData;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;

public class LoginScreen extends JFrame {
    private JFormattedTextField cpfField;
    private ClientConnectionHandler clientConnectionHandler;
    private ElectionData electionData;

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

    private void initUI() {
        setTitle("Login - Sistema de Votação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Texto de instruções
        JLabel instructionLabel = new JLabel("Digite seu CPF para votar:");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(instructionLabel, gbc);

        // Área de CPF
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
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();

                // Verifica se o CPF é válido
                if (CPFValidator.isValid(cpf)) {
                    try {
                        // Envia o CPF ao servidor para verificar se já votou
                        clientConnectionHandler.sendCPFToServer(cpf);
                        String serverResponse = clientConnectionHandler.receiveServerResponse();

                        if ("CPF já votou".equals(serverResponse)) {
                            JOptionPane.showMessageDialog(LoginScreen.this, "CPF já votou!", "Erro", JOptionPane.ERROR_MESSAGE);
                        } else if ("CPF não votou".equals(serverResponse)) {
                            openVotingScreen(cpf);
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

    private void openVotingScreen(String cpf) {
        this.dispose();
        new VotingScreen(electionData, cpf, clientConnectionHandler).setVisible(true);
    }

    private void openHelpScreen() {
        this.dispose();
        new ClientHelpScreen().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Permitir entrada de IP e Port para configuração dinâmica
            String serverAddress = JOptionPane.showInputDialog(null, "Digite o endereço IP do servidor:", "Configuração do Servidor", JOptionPane.QUESTION_MESSAGE);
            String portInput = JOptionPane.showInputDialog(null, "Digite a porta do servidor:", "Configuração do Servidor", JOptionPane.QUESTION_MESSAGE);

            int port;
            try {
                port = Integer.parseInt(portInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Porta inválida! Usando porta padrão 1234.", "Aviso", JOptionPane.WARNING_MESSAGE);
                port = 1234; // Porta padrão
            }

            new LoginScreen(serverAddress, port).setVisible(true);
        });
    }
}

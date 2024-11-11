package client;

import clientServer.CPFValidator;
import clientServer.ElectionData;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class LoginScreen extends JFrame {
    private JFormattedTextField cpfField;
    private JButton submitButton;
    private JLabel helpLabel;
    private ClientConnectionHandler clientConnectionHandler;
    private ElectionData electionData;

/*
    // APENAS TESTE DE ITENS PARA ELECTION DATA ---- ESSAS INFORMAÇÕES VÃO SER PASSADAS PELA REDE
    String question = "Pergunta teste";
    List<String> options = List.of(new String[]{"opção1", "opção2"});
    ElectionData electionData = new ElectionData(question, options);
    ///////////////////////////////////////////////////////////////////////////

 */

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

        submitButton = new JButton("Confirmar");
        submitButton.setBackground(new Color(100, 149, 237));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                if (CPFValidator.isValid(cpf)) {
                    openVotingScreen(cpf);
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        helpLabel = new JLabel("Precisa de ajuda? Clique aqui");
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
        new VotingScreen(electionData, cpf).setVisible(true);
    }

    private void openHelpScreen() {
        this.dispose();
        new ClientHelpScreen().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginScreen("localhost", 1234).setVisible(true);
        });
    }
}

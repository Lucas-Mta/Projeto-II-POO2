/*
package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class VotingScreen extends JFrame {
    private JTextField numberField;
    private JLabel candidateLabel;
    private JButton submitButton;
    private Client client;
    private String cpf;
    private Map<String, String> candidates;

    public VotingScreen(Client client, String cpf) {
        this.client = client;
        this.cpf = cpf;
        initCandidates();
        initUI();
    }

    private void initCandidates() {
        candidates = new HashMap<>();
        candidates.put("01", "Candidato A");
        candidates.put("02", "Candidato B");
        candidates.put("03", "Candidato C");
        // Adicione mais candidatos conforme necessário
    }

    private void initUI() {
        setTitle("Tela de Votação - Sistema de Votação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Coluna esquerda - Exibição do Candidato
        JLabel labelCandidate = new JLabel("Candidato:");
        labelCandidate.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelCandidate, gbc);

        candidateLabel = new JLabel(" ");
        candidateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        panel.add(candidateLabel, gbc);

        // Coluna direita - Entrada do Número do Candidato
        JLabel labelNumber = new JLabel("Número:");
        labelNumber.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(labelNumber, gbc);

        numberField = new JTextField(5);
        numberField.setFont(new Font("Arial", Font.PLAIN, 18));
        numberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String candidateNumber = numberField.getText();
                String candidateName = candidates.get(candidateNumber);
                if (candidateName != null) {
                    candidateLabel.setText(candidateName);
                } else {
                    candidateLabel.setText("Número inválido");
                }
            }
        });
        gbc.gridy = 1;
        panel.add(numberField, gbc);

        // Botão Confirmar - Centralizado
        submitButton = new JButton("Confirmar Voto");
        submitButton.addActionListener(e -> {
            String candidateNumber = numberField.getText();
            if (candidates.containsKey(candidateNumber)) {
                client.sendVote(cpf, candidateNumber);
                JOptionPane.showMessageDialog(null, "Voto confirmado! Obrigado por votar.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Número inválido. Tente novamente.");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        add(panel);
    }
}
*/

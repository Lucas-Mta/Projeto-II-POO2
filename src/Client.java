//package urna;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class Client {
    private VoteManager voteManager; // Instância do gerenciador de votos
    private JFrame frame;
    private JTextField cpfField;
    private JComboBox<String> candidateComboBox;
    private JTextArea resultArea;

    public Client(VoteManager voteManager) {
        this.voteManager = voteManager;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Sistema de Votação");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(15);
        JLabel candidateLabel = new JLabel("Candidato:");
        candidateComboBox = new JComboBox<>(new String[]{"Candidato A", "Candidato B", "Candidato C"});
        JButton voteButton = new JButton("Votar");
        JButton resultButton = new JButton("Resultados");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        // Adiciona ação ao botão de voto
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                String candidate = (String) candidateComboBox.getSelectedItem();
                if (voteManager.vote(cpf, candidate)) {
                    JOptionPane.showMessageDialog(frame, "Voto registrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Erro ao registrar voto.");
                }
                cpfField.setText(""); // Limpa o campo CPF
            }
        });

        // Adiciona ação ao botão de resultados
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder results = new StringBuilder();
                for (Map.Entry<String, Integer> entry : voteManager.calculateResults().entrySet()) {
                    results.append("Candidato: ").append(entry.getKey()).append(", Votos: ").append(entry.getValue()).append("\n");
                }
                resultArea.setText(results.toString());
            }
        });

        // Adiciona componentes à janela
        frame.add(cpfLabel);
        frame.add(cpfField);
        frame.add(candidateLabel);
        frame.add(candidateComboBox);
        frame.add(voteButton);
        frame.add(resultButton);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VoteManager voteManager = new VoteManager();
        new Client(voteManager);
    }
}


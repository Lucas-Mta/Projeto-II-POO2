package server;

import java.awt.*;
import java.io.Serial;
import java.util.Map;
import javax.swing.*;

public class ReportGenerator extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public ReportGenerator(Map<String, Integer> voteCounts) {
        // Configurações da janela
        setTitle("Relatório Final da Eleição");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Relatório Final da Eleição");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Área de relatório com texto
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setText(generateReport(voteCounts));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Botão de OK
        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(100, 149, 237));
        okButton.setForeground(Color.WHITE); // Texto branco
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setFocusPainted(false); // Sem borda de foco
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> dispose());
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o botão
        panel.add(okButton, gbc);

        // Adiciona o painel à janela
        add(panel);
    }

    // Gera o texto do relatório final
    private String generateReport(Map<String, Integer> voteCounts) {
        StringBuilder report = new StringBuilder("Relatório Final da Eleição:\n\n");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            report.append("Opção: ").append(entry.getKey())
                    .append(", Votos: ").append(entry.getValue()).append("\n");
        }
        return report.toString();
    }
}
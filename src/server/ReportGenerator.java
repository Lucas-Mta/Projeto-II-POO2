package server;

import java.awt.*;
import java.io.Serial;
import java.util.Map;
import javax.swing.*;

/** ReportGenerator creates a graphical window to display the final election results.
  * It presents vote counts for each option in a clean, readable format using Swing components.  */
public class ReportGenerator extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;       // Serialization identifier for the class

    /** Creates a new window to display election results.
      * Initializes the GUI components and displays the vote counts.
      * @param voteCounts Map containing voting options and their respective vote counts          */
    public ReportGenerator(Map<String, Integer> voteCounts) {
        setTitle("Relatório Final da Eleição");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel titleLabel = new JLabel("Relatório Final da Eleição");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        panel.add(titleLabel, gbc);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setText(generateReport(voteCounts));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setLineWrap(true);  // Quebra de linha automática
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

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
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(okButton, gbc);
        add(panel);
    }
    /** Generates a formatted string containing the election results.
      * Creates a report showing the vote count for each option.
      * @param voteCounts Map containing voting options and their respective vote counts
      * @return Formatted string containing the election report                              */
    private String generateReport(Map<String, Integer> voteCounts) {
        StringBuilder report = new StringBuilder("Relatório Final da Eleição:\n\n");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            report.append("Opção: ").append(entry.getKey())
                    .append(", Votos: ").append(entry.getValue()).append("\n");
        }
        return report.toString();
    }
}
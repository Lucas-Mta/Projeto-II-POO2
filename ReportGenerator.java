package server;

import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ReportGenerator extends JFrame {
    private static final long serialVersionUID = 1L;

    public ReportGenerator(Map<String, Integer> voteCounts) {
        // Configurações da janela
        setTitle("Relatório Final da Eleição");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cria um JTextArea para exibir o relatório
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); 
        textArea.setText(generateReport(voteCounts)); 

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane); // Adiciona o JScrollPane à janela
    }
    // Método para gerar o texto do relatório final
    private String generateReport(Map<String, Integer> voteCounts) {
        StringBuilder report = new StringBuilder("Relatório Final da Eleição:\n");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            report.append("Opção: ").append(entry.getKey()).append(", Votos: ").append(entry.getValue()).append("\n");
        }
        return report.toString();
    }
}
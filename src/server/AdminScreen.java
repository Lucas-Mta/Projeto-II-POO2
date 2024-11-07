package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminScreen extends JFrame {

    private JTextField questionField;
    private ArrayList<JTextField> optionFields;
    private JPanel optionsPanel;
    private JPanel footerPanel;

    public AdminScreen() {
        // Configuração da Janela Principal
        setTitle("Administração de Votação");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tela Inicial com Botão para Criar Nova Votação
        JButton createVoteButton = new JButton("Criar Nova Votação");
        createVoteButton.addActionListener(e -> openVotingSetup());

        JButton helpButton = new JButton("Ajuda");
        helpButton.addActionListener(e -> showHelp());

        JPanel initialPanel = new JPanel(new FlowLayout());
        initialPanel.add(createVoteButton);
        initialPanel.add(helpButton);

        add(initialPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Método para abrir a interface de criação da votação
    private void openVotingSetup() {
        JFrame votingFrame = new JFrame("Configuração de Votação");
        votingFrame.setSize(500, 400);
        votingFrame.setLayout(new BorderLayout());

        // Header com a pergunta da votação
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(new JLabel("Pergunta da Votação:"), BorderLayout.NORTH);
        questionField = new JTextField();
        headerPanel.add(questionField, BorderLayout.CENTER);
        votingFrame.add(headerPanel, BorderLayout.NORTH);

        // Painel de Opções
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionFields = new ArrayList<>();
        addOptionField(); // Adiciona uma opção inicial

        // Painel central para opções de resposta
        JPanel mainPanel = new JPanel();
        mainPanel.add(optionsPanel); // Centraliza as opções no painel principal
        votingFrame.add(mainPanel, BorderLayout.CENTER);

        // Footer com os botões
        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addOptionButton = new JButton("Adicionar Opção");
        addOptionButton.addActionListener(e -> {
            addOptionField();
            votingFrame.revalidate();
            votingFrame.repaint();
        });

        JButton startVoteButton = new JButton("Iniciar Votação");
        startVoteButton.addActionListener(e -> startVoting(votingFrame));

        footerPanel.add(addOptionButton);
        footerPanel.add(startVoteButton);
        votingFrame.add(footerPanel, BorderLayout.SOUTH);

        votingFrame.setVisible(true);
    }

    // Método para adicionar um campo de opção
    private void addOptionField() {
        JTextField optionField = new JTextField(20); // Campo reduzido para centralizar
        optionFields.add(optionField);
        optionsPanel.add(optionField);
    }

    // Método para iniciar a votação (simula envio ao servidor e altera a interface)
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
            // Aqui seria enviado `question` e `options` ao servidor
            JOptionPane.showMessageDialog(votingFrame, "Votação Iniciada com Sucesso!");

            // Modifica a interface para o modo "Votação Ativa"
            optionsPanel.removeAll();
            footerPanel.removeAll();

            JButton endVoteButton = new JButton("Encerrar Votação");
            endVoteButton.addActionListener(e -> endVoting(votingFrame));
            footerPanel.add(endVoteButton);

            votingFrame.revalidate();
            votingFrame.repaint();
        } else {
            JOptionPane.showMessageDialog(votingFrame, "Insira uma pergunta e ao menos duas opções de resposta.");
        }
    }

    // Método para encerrar a votação e exibir os resultados
    private void endVoting(JFrame votingFrame) {
        // Simula exibição de resultados
        JOptionPane.showMessageDialog(votingFrame, "Votação Encerrada! Resultados disponíveis.");
        votingFrame.dispose();
    }

    // Método para exibir a Ajuda
    private void showHelp() {
        JOptionPane.showMessageDialog(this, "Ajuda:\n1. Clique em 'Criar Nova Votação' para configurar uma votação.\n" +
                "2. Insira a pergunta e as opções de resposta.\n3. Clique em 'Iniciar Votação' para iniciar a sessão.");
    }

    public static void main(String[] args) {
        new AdminScreen();
    }
}

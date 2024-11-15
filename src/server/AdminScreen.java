package server;

import clientServer.ElectionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class AdminScreen extends JFrame {

    private JTextField questionField;
    private ArrayList<JTextField> optionFields;
    private JPanel optionsPanel;
    private JPanel footerPanel;

    private ServerConnectionHandler serverHander;

    public AdminScreen() {
        // Configuração da Janela Principal
        setTitle("Administração - Sistema de Votação");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel initialPanel = new JPanel(new GridBagLayout());
        initialPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;

        // Botão para Nova Votação
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

        // Label de ajuda abaixo do botão
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

    // Abre a interface de criação da votação
    private void openVotingSetup() {

        // Configurações da janela de votação
        JFrame votingFrame = new JFrame("Configurações da Nova Votação");
        votingFrame.setSize(500, 400);
        votingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        votingFrame.setLocationRelativeTo(null);
        votingFrame.setResizable(false);

        // Header com a pergunta da votação
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

        // Painel de Opções
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionFields = new ArrayList<>();
        addOptionField(); // Adiciona uma opção inicial

        // JScrollPane para as opções
        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        // Painel central para opções de resposta
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        votingFrame.add(mainPanel, BorderLayout.CENTER);

        // Footer com os botões
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

    // Adiciona um campo de opção da votação
    private void addOptionField() {
        JTextField optionField = new JTextField(20);
        optionFields.add(optionField);
        optionsPanel.add(optionField);
    }

    // Iniciar a votação
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
            // Cria o objeto ElectionData que contém as informações da votação
            ElectionData electionData = new ElectionData(question, options);

            try {
                serverHander = new ServerConnectionHandler(1234, electionData); // Porta de exemplo
                new Thread(serverHander::startServer).start(); // Inicia o servidor em uma nova Thread
                JOptionPane.showMessageDialog(votingFrame, "Votação Iniciada com Sucesso!");

                // Modifica a interface quando a votação está ativa
                optionsPanel.removeAll();
                footerPanel.removeAll();

                // Mostra apenas a pergunta sem poder editar
                questionField.setEditable(false);

                // Mostra as opções que foram definidas
                JLabel optionsLabel = new JLabel("Opções:");
                optionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
                optionsPanel.add(optionsLabel);

                for (String option : electionData.getOptions()) {
                    JLabel optionLabel = new JLabel("\n" + option);
                    optionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    optionsPanel.add(optionLabel);
                }

                // Botão para encerrar a votação
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

    // Encerra a votação e exibe os resultados
    private void endVoting(JFrame votingFrame) throws IOException {
        // Finaliza o servidor e desconecta todos os clientes
        if (serverHander != null) {
            serverHander.shutdown();
        }

        JOptionPane.showMessageDialog(votingFrame, "Votação Encerrada! Resultados disponíveis.");
        votingFrame.dispose();
    }

    // Método para exibir a Ajuda
    private void showHelp() {
        this.dispose();
        new AdminHelpScreen(this).setVisible(true);
    }

    public static void main(String[] args) {
        new AdminScreen();
    }
}
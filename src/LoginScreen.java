import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class LoginScreen extends JFrame {
    private JTextField cpfField;
    private JButton submitButton;
    private Client client;

    public LoginScreen(Client client) {
        this.client = client;
        initUI();
    }

    private void initUI() {
        setTitle("Tela de Login - Sistema de Votação");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Digite seu CPF:");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        cpfField = new JFormattedTextField(createCpfFormatter());
        cpfField.setColumns(8);
        cpfField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        panel.add(cpfField, gbc);

        submitButton = new JButton("Verificar");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                if (CPFValidator.isValid(cpf)) { // Fazer o metodo
                    if (!client.hasVoted(cpf)) {
                        openVotingScreen(cpf);
                    } else {
                        JOptionPane.showMessageDialog(null, "CPF já utilizado para votação.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "CPF inválido!");
                }
            }
        });
        gbc.gridy = 2;
        panel.add(submitButton, gbc);

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
        this.dispose(); // Fecha a tela atual
        new VotingScreen(client, cpf).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client client1 = new Client("localhost", 8080); // teste
            new LoginScreen(client1).setVisible(true);
        });
    }


}

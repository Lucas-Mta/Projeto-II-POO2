package client;

import javax.swing.*;
import java.awt.*;

public class ClientHelpScreen extends JFrame {
    private JLabel so_um_teste;

    public ClientHelpScreen() {
        initUI();
    }

    private void initUI() {
        setTitle("Ajuda Cliente - Sistema de Votação");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Teste apenas
        JPanel panel = new JPanel();
        so_um_teste = new JLabel("Fazer a página de ajuda");
        so_um_teste.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(so_um_teste);

        add(panel);

    }

}

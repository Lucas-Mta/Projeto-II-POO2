package server;

import javax.swing.*;

public class AdminMain {
    public static void main(String[] args) {
        // Inicializa a tela de administração
        SwingUtilities.invokeLater(AdminScreen::new);
    }
}

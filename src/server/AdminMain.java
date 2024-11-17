package server;

import javax.swing.*;

/** Main entry point for the admin interface.
 * Creates and displays the initial admin screen.
 * param args Command line arguments (not used)
 * */

public class AdminMain {
    public static void main(String[] args) {
        // Inicializa a tela de administração
        SwingUtilities.invokeLater(AdminScreen::new);
    }
}

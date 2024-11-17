package client;

import javax.swing.*;

/** Main entry point of the application.
 * Prompts for server configuration and initializes the login screen.
 * Uses SwingUtilities.invokeLater to ensure thread safety in Swing.
 * param args Command line arguments (not used)
 */

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String serverAddress;
            int port;

            // Loop para obter um endereço IP válido
            while (true) {
                serverAddress = JOptionPane.showInputDialog(null, "Digite o endereço IP do servidor:", "Configuração do Servidor", JOptionPane.QUESTION_MESSAGE);

                if (serverAddress == null || serverAddress.isBlank()) {
                    int retry = JOptionPane.showConfirmDialog(null, "Endereço IP inválido! Deseja tentar novamente?", "Erro", JOptionPane.YES_NO_OPTION);
                    if (retry == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "Encerrando aplicação.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                } else {
                    break; // Endereço IP válido foi inserido
                }
            }

            // Loop para obter uma porta válida
            while (true) {
                String portInput = JOptionPane.showInputDialog(null, "Digite a porta do servidor:", "Configuração do Servidor", JOptionPane.QUESTION_MESSAGE);
                try {
                    port = Integer.parseInt(portInput);
                    break; // Porta válida foi inserida
                } catch (NumberFormatException e) {
                    int retry = JOptionPane.showConfirmDialog(null, "Porta inválida! Deseja tentar novamente?", "Erro", JOptionPane.YES_NO_OPTION);
                    if (retry == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "Encerrando aplicação.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                }
            }

            // Inicializar a tela de login
            new LoginScreen(serverAddress, port).setVisible(true);
        });
    }
}

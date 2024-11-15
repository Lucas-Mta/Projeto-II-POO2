package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** ClientHelpScreen Class - Implements a help dialog window with
 * multiple pages using a card system for navigation.  */
public class ClientHelpScreen extends JDialog {
    private JPanel mainPanel;           // Main panel containing cards
    private JPanel buttonPanel;         // Navigation buttons panel
    private CardLayout cardLayout;      // Layout to manage card switching
    private int currentCard = 0;        // Current card index
    private List<String> helpTexts;     // List of help texts
    private JButton prevButton;         // Previous button
    private JButton nextButton;         // Next button
    private JLabel pageIndicator;       // Page indicator
    private JButton backButton;         // Button to go back to AdminScreen

    /** ClientHelpScreen dialog constructor
     * @param parent - ClientHelpScreen parent to which the dialog is linked  */
    public ClientHelpScreen(LoginScreen parent) {
        super(parent, "Ajuda ao Cliente", true); // true para modal
        initializeComponents();
        setupLayout();
        setupActions();
        setSize(700, 600);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    // Initializes all dialog components and configures help texts
    private void initializeComponents() {
        // Initializes help texts
        helpTexts = new ArrayList<>();
        helpTexts.add("""
            Bem-vindo ao Sistema de Votação Distribuída!
            
            Este sistema permite que você participe de votações eletrônicas de forma segura e eficiente.
            
            Principais características do sistema:
            
            • Votação segura através de validação por CPF
            • Interface simples e intuitiva
            • Confirmação instantânea do seu voto
            • Garantia de voto único por CPF
            
            O sistema utiliza tecnologia cliente-servidor e protocolo TCP/IP para garantir
            a confiabilidade do seu voto, além de implementar validação de CPF para
            evitar votos duplicados.
            
            Nas próximas páginas, você encontrará o passo a passo detalhado para
            participar da votação.
            """);

        helpTexts.add("""
            Como Participar da Votação
            
            1. Tela de Login
               • Digite seu CPF no campo indicado
               • O CPF deve ser válido e no formato: XXX.XXX.XXX-XX
               • Pressione o botão confirmar
            
            2. Validação do CPF
               • O sistema verifica se o formato está correto
               • Confirma se o CPF é matematicamente válido
               • Caso o CPF seja inválido, você será notificado
            
            3. Validação do eleitor
               • O sistema verifica se este CPF ainda não votou
               • Caso o CPF já tenha sido utilizado, você será notificado
            
            Dicas importantes:
               • Digite todos os números do CPF
               • A formatação (pontos e traço) é automática
               • Em caso de erro, verifique se digitou corretamente
               • Cada CPF só pode votar uma vez
            """);

        helpTexts.add("""
            Realizando seu Voto
            
            1. Tela de Votação
               • Após a validação do CPF, você verá:
                 - A pergunta da votação
                 - As opções disponíveis para voto
            
            2. Como votar:
               • Leia atentamente a pergunta
               • Analise todas as opções disponíveis
               • Selecione sua escolha
               • Clique em "Confirmar Voto"
            
            3. Confirmação:
               • Após votar, você receberá uma confirmação
               • Seu voto será registrado com segurança
               • A sessão será encerrada automaticamente
            
            Importante:
            • Seu voto é secreto e seguro
            • Não é possível alterar o voto após a confirmação
            • Em caso de dúvidas, consulte o administrador da votação
            """);

        // Initializes the panels
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Creates the cards with content
        for (int i = 0; i < helpTexts.size(); i++) {
            mainPanel.add(createHelpCard(i), "card" + i);
        }

        // Initializes the button panel
        buttonPanel = new JPanel(new FlowLayout());
        prevButton = new JButton("← Anterior");
        nextButton = new JButton("Próximo →");
        backButton = new JButton("Voltar à Votação");
        pageIndicator = new JLabel("1/" + helpTexts.size());

        // Styling of the back button
        backButton.setBackground(new Color(0, 102, 204));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        // Sets the initial state of the buttons
        prevButton.setEnabled(false);
        updateNavigationButtons();
    }

    /** Creates an individual card for each help page
     * @param index - Index of the card to be created
     * @return - JPanel configured with card content    */
    private JPanel createHelpCard(int index) {
        String path = System.getProperty("user.dir");
        path += (index == 0) ? "/images/helpAD" : "/images/helpCL";;
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Creates the top panel with the title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = createStylizedTitleLabel(index);
        topPanel.add(titleLabel);

        // Creates a scrollable text area
        JTextArea textArea = new JTextArea(helpTexts.get(index));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        // Configures the text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Creates a panel for the images
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
        imagePanel.setPreferredSize(new Dimension(0, 200)); // Altura fixa de 150 pixels
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adds the images
        String[] imageFiles;
        imageFiles = new String[]{index + "1.png", index + "2.png", index + "3.png"};
        for (String fileName : imageFiles) {
            ImageIcon scaledIcon = getImageIcon(fileName, path);
            // Creates a panel for each image with uniform spacing
            JPanel imageWrapper = new JPanel();
            imageWrapper.add(new JLabel(scaledIcon));
            imageWrapper.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
            imagePanel.add(imageWrapper);
        }

        // Arranges the information on the card
        card.add(topPanel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(imagePanel, BorderLayout.SOUTH);

        return card;
    }

    /** Resizes an image while maintaining its proportion
     * @param fileName - Image file name
     * @param path - Path to the file
     * @return - Resized ImageIcon            */
    private static ImageIcon getImageIcon(String fileName, String path) {
        ImageIcon originalIcon = new ImageIcon(path + fileName);
        Image image = originalIcon.getImage();

        // Calculates to maintain the image's aspect ratio
        int newHeight = 155;
        double ratio = (double) originalIcon.getIconWidth() / originalIcon.getIconHeight();
        int newWidth = (int) (newHeight * ratio);

        // Resizes the image
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /** Creates a stylized label with icon for each page title
     * @param index - Page index
     * @return - JLabel configured with specific style               */
    private JLabel createStylizedTitleLabel(int index) {
        String title;
        Color iconColor;
        Icon icon = null;

        switch (index) {
            case 0:
                title = "Bem-vindo!";
                iconColor = new Color(255, 200, 80);
                icon = UIManager.getIcon("FileChooser.homeFolderIcon");
                break;
            case 1:
                title = "Login e Validação";
                iconColor = new Color(51, 100, 255);
                icon = UIManager.getIcon("FileView.computerIcon");
                break;
            case 2:
                title = "Como Votar";
                iconColor = new Color(76, 175, 80);
                icon = UIManager.getIcon("OptionPane.questionIcon");
                break;
            default:
                title = "";
                iconColor = Color.BLACK;
        }

        return getjLabel(title, iconColor, icon);
    }

    /**Creates and configures a stylized JLabel with title, icon and custom borders
     * @param title - Text to be displayed on the label
     * @param iconColor - Color to be used for text and label border
     * @param icon - Icon to be displayed next to the text
     * @return - JLabel Component label configured with defined styles         */
    private static JLabel getjLabel(String title, Color iconColor, Icon icon) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(iconColor);
        label.setIcon(icon);
        label.setIconTextGap(10);

        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(iconColor, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return label;
    }

    // Configures the main dialog layout
    private void setupLayout() {
        setLayout(new BorderLayout());

        // Creates a separate panel for the back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);

        // Creates a panel for the navigation buttons
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigationPanel.add(prevButton);
        navigationPanel.add(pageIndicator);
        navigationPanel.add(nextButton);

        // Combines the panels into a main button panel
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(backButtonPanel, BorderLayout.WEST);
        buttonPanel.add(navigationPanel, BorderLayout.CENTER);

        // Adds all components to the dialog
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Configures navigation button actions
    private void setupActions() {
        prevButton.addActionListener(e -> {
            if (currentCard > 0) {
                currentCard--;
                cardLayout.previous(mainPanel);
                updateNavigationButtons();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentCard < helpTexts.size() - 1) {
                currentCard++;
                cardLayout.next(mainPanel);
                updateNavigationButtons();
            }
        });

        backButton.addActionListener(e -> dispose());

        // Adds a handler for window closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
            }
        });
    }

    // Updates navigation buttons state and page indicator
    private void updateNavigationButtons() {
        prevButton.setEnabled(currentCard > 0);
        nextButton.setEnabled(currentCard < helpTexts.size() - 1);
        pageIndicator.setText((currentCard + 1) + "/" + helpTexts.size());
    }
}
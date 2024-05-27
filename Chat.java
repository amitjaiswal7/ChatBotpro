

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Chat extends JFrame {

    private JPanel chatArea;
    private JTextField chatBox;

    public Chat() {
        // Set look and feel to Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame settings
        setTitle("ChatBot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);

        // Main panel settings
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("kakashi.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Chat area panel with scroll pane
        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Chat input box
        chatBox = new JTextField();
        chatBox.setFont(new Font("Arial", Font.PLAIN, 14));
        chatBox.setMargin(new Insets(5, 5, 5, 5));
        chatBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        chatBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userText = chatBox.getText();
                addChatBubble(userText, true);
                chatBox.setText("");

                // Basic response system
                String botResponse = getBotResponse(userText);
                addChatBubble(botResponse, false);
            }
        });

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        inputPanel.add(chatBox, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Simple rule-based response system
    private String getBotResponse(String input) {
        input = input.toLowerCase();
        if (input.contains("hi") || input.contains("hello")) {
            return "Hello! How can I assist you today?";
        } else if (input.contains("how are you")) {
            return "I'm just a bot, but I'm here to help you!";
        } else if (input.contains("what is your name")) {
            return "I am a simple chatbot created to assist you.";
        } else if (input.contains("weather")) {
            return "I can't provide real-time weather updates, but I recommend checking a weather website.";
        } else if (input.contains("time")) {
            return "I don't have the ability to tell the current time. You can check your device's clock.";
        } else if (input.contains("bye") || input.contains("goodbye")) {
            return "Goodbye! Have a nice day!";
        } else {
            return "I'm sorry, I don't understand. Can you please rephrase?";
        }
    }

    // Method to add a chat bubble to the chat area
    private void addChatBubble(String message, boolean isUser) {
        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BoxLayout(bubblePanel, BoxLayout.X_AXIS));
        bubblePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        bubblePanel.setOpaque(false);

        JTextArea bubble = new JTextArea(message);
        bubble.setFont(new Font("Arial", Font.PLAIN, 14));
        bubble.setLineWrap(true);
        bubble.setWrapStyleWord(true);
        bubble.setEditable(false);
        bubble.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bubble.setBackground(isUser ? new Color(173, 216, 230) : new Color(144, 238, 144));
        bubble.setOpaque(true);

        // Calculate the preferred size based on text content
        bubble.setSize(new Dimension(400, Short.MAX_VALUE));
        bubble.setMaximumSize(new Dimension(400, bubble.getPreferredSize().height)); // Limit bubble width to 400px
        bubble.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to left within bubble

        if (isUser) {
            bubblePanel.add(Box.createHorizontalGlue());
            bubblePanel.add(bubble);
        } else {
            bubblePanel.add(bubble);
            bubblePanel.add(Box.createHorizontalGlue());
        }

        chatArea.add(bubblePanel);
        chatArea.revalidate();
        chatArea.repaint();
        JScrollBar verticalScrollBar = ((JScrollPane) chatArea.getParent().getParent()).getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum()); // Auto-scroll to bottom
    }

    public static void main(String[] args) {
        new Chat();
    }
}

import java.awt.*;
import javax.swing.*;

public class GymManagement extends JFrame {
    public GymManagement() {
        setTitle("Gym Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color of the JFrame
        getContentPane().setBackground(new Color(0xE6DDCA));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel title = new JLabel("Gym Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        JButton registerButton = createStyledButton("Register New Member", new Color(0xAE9372)); // Stone
        gbc.gridy = 1;
        add(registerButton, gbc);

        JButton updateButton = createStyledButton("Update Member Details", new Color(0xB27D57)); // Coffee
        gbc.gridy = 2;
        add(updateButton, gbc);

        JButton changePlanButton = createStyledButton("Change Membership Plan", new Color(0x7F4B30)); // Ochre
        gbc.gridy = 3;
        add(changePlanButton, gbc);

        JButton paymentButton = createStyledButton("Payment Portal", new Color(0x7D8769)); // Gum
        gbc.gridy = 4;
        add(paymentButton, gbc);

        JButton recordExerciseButton = createStyledButton("Record Exercise", new Color(0x173125)); // Forest
        gbc.gridy = 5;
        add(recordExerciseButton, gbc);

        JButton fetchDataButton = createStyledButton("Fetch Data", new Color(0x212E40)); // Basalt
        gbc.gridy = 6;
        add(fetchDataButton, gbc);

        registerButton.addActionListener(e -> new RegisterMember().setVisible(true));
        updateButton.addActionListener(e -> new UpdateMember().setVisible(true));
        changePlanButton.addActionListener(e -> new ChangePlan().setVisible(true));
        paymentButton.addActionListener(e -> new PaymentPortal().setVisible(true));
        recordExerciseButton.addActionListener(e -> new RecordExercise().setVisible(true));
        fetchDataButton.addActionListener(e -> new FetchData().setVisible(true)); // Action for Fetch Data
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 50));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymManagement().setVisible(true));
    }
}
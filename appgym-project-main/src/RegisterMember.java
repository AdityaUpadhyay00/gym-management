import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;

public class RegisterMember extends JFrame {

    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextField emailField;
    private final JButton registerButton;

    public RegisterMember() {
        setTitle("New Member Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set the background color
        getContentPane().setBackground(new Color(0xAE9372));  // #AE9372
    
        // Set GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margin around elements
    
        JLabel title = new JLabel("Register New Member", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);
    
        // Name field setup
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameField, gbc);

        // Age field setup
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(ageLabel, gbc);

        ageField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(ageField, gbc);

        // Email field setup
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Register button setup
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setForeground(Color.BLACK);
        registerButton.addActionListener(new RegisterMemberAction());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);
    }

    private class RegisterMemberAction implements ActionListener {
        private static final String URL = "jdbc:mysql://localhost:3306/gym_ms?useSSL=false&serverTimezone=UTC";
        private static final String USER = "root";
        private static final String PASS = "12345678"; // Your MySQL password here

        @Override
        @SuppressWarnings("CallToPrintStackTrace")
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || ageText.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            try {
                int age = Integer.parseInt(ageText); // Ensure age is a valid integer
                
                // Try to connect to the database
                try {
                    // Explicitly load the MySQL JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                        String sql = "INSERT INTO members (name, age, email) VALUES (?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, name);
                        stmt.setInt(2, age);
                        stmt.setString(3, email);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Member Registered Successfully!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                    }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "JDBC Driver not found! " + ex.getMessage());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Age must be a valid number!");
            }
        }
    }

    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new RegisterMember().setVisible(true));
    }
}

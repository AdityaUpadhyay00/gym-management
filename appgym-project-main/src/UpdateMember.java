import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class UpdateMember extends JFrame {

    private final JTextField memberIdField;
    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextField emailField;
    private final JButton updateButton;

    public UpdateMember() {
        setTitle("Update Member Details");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(0xB27D57)); // Coffee color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Update Member Details", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(memberIdLabel, gbc);

        memberIdField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(memberIdField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(ageLabel, gbc);

        ageField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(ageField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        updateButton = new JButton("Update Member");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(new Color(52, 152, 219)); // Blue shade
        updateButton.setForeground(Color.BLACK);
        updateButton.addActionListener(new UpdateMemberAction());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(updateButton, gbc);
    }

    private class UpdateMemberAction implements ActionListener {
        private static final String URL = "jdbc:mysql://localhost:3306/gym_ms";
        private static final String USER = "root";
        private static final String PASS = "12345678";

        @Override
        public void actionPerformed(ActionEvent e) {
            String idText = memberIdField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();

            // Validate input fields
            if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id, age;
            try {
                id = Integer.parseInt(idText);
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Age and Member ID must be valid numbers.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                String sql = "UPDATE members SET name = ?, age = ?, email = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, email);
                stmt.setInt(4, id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Member details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Member not found with ID: " + id);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

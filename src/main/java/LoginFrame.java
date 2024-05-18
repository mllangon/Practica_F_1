import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        LoginPanel loginPanel = new LoginPanel("/fondo.png");
        JTextField usernameField = new JTextField(15);
        usernameField.setName("usernameField");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setName("passwordField");
        JButton loginButton = new JButton("Login");

        loginPanel.addComponents(usernameField, passwordField, loginButton);

        add(loginPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple authentication logic (replace with real authentication logic)
                if (username.equals("admin") && password.equals("password")) {
                    // Open the main application window
                    ExperimentoGUI mainApp = new ExperimentoGUI();
                    mainApp.setVisible(true);
                    // Close the login window
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

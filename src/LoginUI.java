//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI() {
        this.setTitle("Login");
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());
        JLabel onlineAttendanceLabel = new JLabel("Online Attendance");
        onlineAttendanceLabel.setHorizontalAlignment(0);
        onlineAttendanceLabel.setFont(new Font("Arial", 1, 20));
        this.add(onlineAttendanceLabel, "North");
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 255, 255));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2), BorderFactory.createEmptyBorder(20, 10, 20, 10)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        ImageIcon icon = new ImageIcon("C:\\Users\\kames\\IdeaProjects\\Virtual Attendance\\ext_files\\icons8-login-64.png");
        JLabel iconLabel = new JLabel(icon);
        loginPanel.add(iconLabel, gbc);
        gbc.gridx = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setFont(new Font("Arial", 1, 18));
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 2;
        this.usernameField = new JTextField(20);
        loginPanel.add(this.usernameField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.DARK_GRAY);
        passwordLabel.setFont(new Font("Arial", 1, 18));
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 2;
        this.passwordField = new JPasswordField(20);
        loginPanel.add(this.passwordField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", 1, 20));
        loginPanel.add(loginButton, gbc);
        this.add(loginPanel, "Center");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginUI.this.login();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(LoginUI.this, "Are you sure you want to exit?", "Confirm Exit", 0);
                if (option == 0) {
                    System.exit(0);
                }

            }
        });
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
    }

    private void login() {
        String username = this.usernameField.getText();
        String password = new String(this.passwordField.getPassword());
        if (this.authenticate(username, password)) {
            AttendanceUI attendanceUI = new AttendanceUI();
            attendanceUI.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
        }

    }

    private boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });
    }
}

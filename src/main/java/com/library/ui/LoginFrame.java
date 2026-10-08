package com.library.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.library.dao.UserDAO;
import com.library.model.User;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO;

    public LoginFrame() {

        userDAO = new UserDAO();

        setTitle("Library Management System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 244, 248));

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 450));
        leftPanel.setBackground(new Color(35, 55, 75));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel libraryIcon = new JLabel("📚");
        libraryIcon.setFont(new Font("Arial", Font.PLAIN, 55));
        libraryIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel libraryTitle =
                new JLabel("Library");

        libraryTitle.setFont(
                new Font("Arial", Font.BOLD, 32)
        );
        libraryTitle.setForeground(Color.WHITE);
        libraryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel managementTitle =
                new JLabel("Management System");

        managementTitle.setFont(
                new Font("Arial", Font.PLAIN, 20)
        );
        managementTitle.setForeground(
                new Color(220, 230, 240)
        );
        managementTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel description =
                new JLabel(
                        "<html><center>Manage books, members<br>" +
                        "and issue records easily.</center></html>"
                );

        description.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        description.setForeground(
                new Color(200, 215, 230)
        );
        description.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(libraryIcon);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(libraryTitle);
        leftPanel.add(managementTitle);
        leftPanel.add(Box.createVerticalStrut(25));
        leftPanel.add(description);
        leftPanel.add(Box.createVerticalGlue());

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(
                new EmptyBorder(40, 50, 40, 50)
        );

        rightPanel.setLayout(
                new BoxLayout(rightPanel, BoxLayout.Y_AXIS)
        );

        JLabel welcomeLabel =
                new JLabel("Welcome Back!");

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );
        welcomeLabel.setForeground(
                new Color(35, 55, 75)
        );
        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel loginLabel =
                new JLabel("Login to continue");

        loginLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );
        loginLabel.setForeground(Color.GRAY);
        loginLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel usernameLabel =
                new JLabel("Username");

        usernameLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        usernameLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        usernameField =
                new JTextField();

        usernameField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );
        usernameField.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        JLabel passwordLabel =
                new JLabel("Password");

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        passwordLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        passwordField =
                new JPasswordField();

        passwordField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );
        passwordField.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(
                new Color(35, 55, 75)
        );
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );
        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Add components
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(loginLabel);

        rightPanel.add(Box.createVerticalStrut(30));

        rightPanel.add(usernameLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(usernameField);

        rightPanel.add(Box.createVerticalStrut(18));

        rightPanel.add(passwordLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(passwordField);

        rightPanel.add(Box.createVerticalStrut(28));

        rightPanel.add(loginButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Login action
        loginButton.addActionListener(e -> login());

        // Press Enter to login
        passwordField.addActionListener(e -> login());
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password!",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        User user =
                userDAO.login(username, password);

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            Dashboard dashboard =
                    new Dashboard();

            dashboard.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame frame =
                    new LoginFrame();

            frame.setVisible(true);
        });
    }
}
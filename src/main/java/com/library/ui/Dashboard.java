package com.library.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.library.dao.BookDAO;
import com.library.dao.MemberDAO;
import com.library.service.IssueService;

public class Dashboard extends JFrame {

    private JLabel totalBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel totalMembersLabel;
    private JLabel issuedBooksLabel;

    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private IssueService issueService;

    private final Color DARK = new Color(35, 55, 75);
    private final Color BACKGROUND = new Color(240, 244, 248);
    private final Color WHITE = Color.WHITE;

    public Dashboard() {

        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        issueService = new IssueService();

        setTitle("Library Management System - Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // ================= HEADER =================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel("Library Management System");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );
        titleLabel.setForeground(DARK);

        JLabel subtitleLabel =
                new JLabel("Dashboard");

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );
        subtitleLabel.setForeground(Color.GRAY);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );
        titlePanel.setOpaque(false);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= STATISTICS =================

        JPanel statsPanel =
                new JPanel(new GridLayout(1, 4, 15, 15));

        statsPanel.setOpaque(false);

        totalBooksLabel =
                createCard(
                        "TOTAL BOOK TITLES",
                        "0"
                );

        availableBooksLabel =
                createCard(
                        "AVAILABLE BOOKS",
                        "0"
                );

        totalMembersLabel =
                createCard(
                        "TOTAL MEMBERS",
                        "0"
                );

        issuedBooksLabel =
                createCard(
                        "ISSUED BOOKS",
                        "0"
                );

        statsPanel.add(
                createStatPanel(
                        "📚",
                        "Total Book Titles",
                        totalBooksLabel
                )
        );

        statsPanel.add(
                createStatPanel(
                        "📖",
                        "Available Books",
                        availableBooksLabel
                )
        );

        statsPanel.add(
                createStatPanel(
                        "👥",
                        "Total Members",
                        totalMembersLabel
                )
        );

        statsPanel.add(
                createStatPanel(
                        "📕",
                        "Issued Books",
                        issuedBooksLabel
                )
        );

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // ================= BUTTONS =================

        JPanel buttonPanel =
                new JPanel(new GridLayout(2, 3, 15, 15));

        buttonPanel.setOpaque(false);

        JButton bookButton =
                createButton("📚  Book Management");

        JButton memberButton =
                createButton("👥  Member Management");

        JButton issueButton =
                createButton("🔄  Issue / Return");

        JButton refreshButton =
                createButton("🔃  Refresh Dashboard");

        JButton logoutButton =
                createButton("🚪  Logout");

        buttonPanel.add(bookButton);
        buttonPanel.add(memberButton);
        buttonPanel.add(issueButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(new JLabel());

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ================= ACTIONS =================

        bookButton.addActionListener(e -> {
            new BookFrame().setVisible(true);
        });

        memberButton.addActionListener(e -> {
            new MemberFrame().setVisible(true);
        });

        issueButton.addActionListener(e -> {
            new IssueReturnFrame().setVisible(true);
        });

        refreshButton.addActionListener(e -> {
            loadStatistics();
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        add(mainPanel);

        // Load database statistics
        loadStatistics();
    }

    // ================= STATISTICS CARD =================

    private JPanel createStatPanel(
            String icon,
            String title,
            JLabel valueLabel) {

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        panel.setBackground(WHITE);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 230)
                        ),
                        new EmptyBorder(15, 10, 15, 10)
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setFont(
                new Font("Arial", Font.PLAIN, 28)
        );
        iconLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 13)
        );
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(valueLabel);

        return panel;
    }

    private JLabel createCard(
            String title,
            String value) {

        JLabel label =
                new JLabel(value);

        label.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        label.setForeground(DARK);

        return label;
    }

    // ================= BUTTON DESIGN =================

    private JButton createButton(String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(DARK);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // ================= LOAD STATISTICS =================

    private void loadStatistics() {

        int totalBookTitles =
                bookDAO.getTotalBookTitles();

        int availableBooks =
                bookDAO.getTotalBookQuantity();

        int totalMembers =
                memberDAO.getTotalMembers();

        int issuedBooks =
                issueService.getIssuedBookCount();

        totalBooksLabel.setText(
                String.valueOf(totalBookTitles)
        );

        availableBooksLabel.setText(
                String.valueOf(availableBooks)
        );

        totalMembersLabel.setText(
                String.valueOf(totalMembers)
        );

        issuedBooksLabel.setText(
                String.valueOf(issuedBooks)
        );
    }
}
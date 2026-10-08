package com.library.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.library.model.IssueRecord;
import com.library.service.IssueService;

public class IssueReturnFrame extends JFrame {

    private JTextField bookIdField;
    private JTextField memberIdField;
    private JTextField issueIdField;

    private JTable issueTable;
    private DefaultTableModel tableModel;

    private IssueService issueService;

    private final Color DARK = new Color(35, 55, 75);
    private final Color BACKGROUND = new Color(240, 244, 248);
    private final Color WHITE = Color.WHITE;

    public IssueReturnFrame() {

        issueService = new IssueService();

        setTitle("Library Management System - Issue / Return");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ================= MAIN PANEL =================

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(20, 25, 20, 25)
        );

        // ================= HEADER =================

        JLabel titleLabel =
                new JLabel("Issue / Return Management");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setForeground(DARK);

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // ================= FORM =================

        JPanel formPanel =
                new JPanel(new GridLayout(2, 3, 12, 8));

        formPanel.setBackground(WHITE);

        formPanel.setBorder(
                new EmptyBorder(12, 12, 12, 12)
        );

        bookIdField = new JTextField();
        memberIdField = new JTextField();
        issueIdField = new JTextField();

        formPanel.add(
                createLabel("Book ID")
        );

        formPanel.add(
                createLabel("Member ID")
        );

        formPanel.add(
                createLabel("Issue ID")
        );

        formPanel.add(bookIdField);
        formPanel.add(memberIdField);
        formPanel.add(issueIdField);

        // ================= ACTION BUTTONS =================

        JButton issueButton =
                createButton("Issue Book");

        JButton returnButton =
                createButton("Return Book");

        JButton refreshButton =
                createButton("Refresh");

        JButton clearButton =
                createButton("Clear");

        JPanel actionPanel =
                new JPanel(new GridLayout(1, 4, 10, 10));

        actionPanel.setOpaque(false);

        actionPanel.add(issueButton);
        actionPanel.add(returnButton);
        actionPanel.add(refreshButton);
        actionPanel.add(clearButton);

        // ================= TABLE =================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Issue ID",
                                "Book ID",
                                "Member ID",
                                "Issue Date",
                                "Return Date"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        issueTable =
                new JTable(tableModel);

        issueTable.setRowHeight(30);

        issueTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        issueTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        issueTable.getTableHeader().setBackground(DARK);
        issueTable.getTableHeader().setForeground(Color.WHITE);

        issueTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // Column sizes

        issueTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(90);

        issueTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(100);

        issueTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(110);

        issueTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(180);

        issueTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(180);

        JScrollPane scrollPane =
                new JScrollPane(issueTable);

        // ================= TOP PANEL =================

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.setOpaque(false);

        topPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        topPanel.add(
                actionPanel,
                BorderLayout.SOUTH
        );

        // ================= TABLE PANEL =================

        JPanel tablePanel =
                new JPanel(new BorderLayout());

        tablePanel.setOpaque(false);

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ================= CENTER =================

        JPanel centerPanel =
                new JPanel(new BorderLayout(12, 12));

        centerPanel.setOpaque(false);

        centerPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // ================= ACTIONS =================

        issueButton.addActionListener(
                e -> issueBook()
        );

        returnButton.addActionListener(
                e -> returnBook()
        );

        refreshButton.addActionListener(
                e -> loadIssueRecords()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        // ================= TABLE SELECTION =================

        issueTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                issueTable.getSelectedRow();

                        if (row >= 0) {

                            issueIdField.setText(
                                    tableModel
                                            .getValueAt(row, 0)
                                            .toString()
                            );

                            bookIdField.setText(
                                    tableModel
                                            .getValueAt(row, 1)
                                            .toString()
                            );

                            memberIdField.setText(
                                    tableModel
                                            .getValueAt(row, 2)
                                            .toString()
                            );
                        }
                    }
                });

        loadIssueRecords();
    }

    // ================= ISSUE BOOK =================

    private void issueBook() {

        try {

            int bookId =
                    Integer.parseInt(
                            bookIdField.getText().trim()
                    );

            int memberId =
                    Integer.parseInt(
                            memberIdField.getText().trim()
                    );

            IssueRecord record =
                    new IssueRecord(
                            0,
                            bookId,
                            memberId,
                            LocalDate.now(),
                            null
                    );

            boolean success =
                    issueService.issueBook(record);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book issued successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadIssueRecords();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Book could not be issued!\n" +
                        "Check Book ID and available quantity.",
                        "Issue Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book ID and Member ID must be numbers!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= RETURN BOOK =================

    private void returnBook() {

        try {

            int issueId =
                    Integer.parseInt(
                            issueIdField.getText().trim()
                    );

            boolean success =
                    issueService.returnBook(issueId);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book returned successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadIssueRecords();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Book could not be returned!\n" +
                        "Check Issue ID or book may already be returned.",
                        "Return Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Issue ID must be a number!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= LOAD RECORDS =================

    private void loadIssueRecords() {

        tableModel.setRowCount(0);

        List<IssueRecord> records =
                issueService.getAllIssueRecords();

        for (IssueRecord record : records) {

            tableModel.addRow(
                    new Object[]{
                            record.getIssueId(),
                            record.getBookId(),
                            record.getMemberId(),
                            record.getIssueDate(),
                            record.getReturnDate()
                    }
            );
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        bookIdField.setText("");
        memberIdField.setText("");
        issueIdField.setText("");

        issueTable.clearSelection();
    }

    // ================= LABEL =================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        label.setForeground(DARK);

        return label;
    }

    // ================= BUTTON =================

    private JButton createButton(String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
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
}
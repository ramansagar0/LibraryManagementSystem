package com.library.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
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

import com.library.model.Member;
import com.library.service.MemberService;

public class MemberFrame extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField searchField;

    private JTable memberTable;
    private DefaultTableModel tableModel;

    private MemberService memberService;

    private final Color DARK = new Color(35, 55, 75);
    private final Color BACKGROUND = new Color(240, 244, 248);
    private final Color WHITE = Color.WHITE;

    public MemberFrame() {

        memberService = new MemberService();

        setTitle("Library Management System - Member Management");
        setSize(950, 650);
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
                new JLabel("Member Management");

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
                new JPanel(new GridLayout(2, 4, 10, 8));

        formPanel.setBackground(WHITE);

        formPanel.setBorder(
                new EmptyBorder(12, 12, 12, 12)
        );

        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        formPanel.add(createLabel("Member ID"));
        formPanel.add(createLabel("Name"));
        formPanel.add(createLabel("Email"));
        formPanel.add(createLabel("Phone"));

        formPanel.add(idField);
        formPanel.add(nameField);
        formPanel.add(emailField);
        formPanel.add(phoneField);

        // ================= SEARCH =================

        JPanel searchPanel =
                new JPanel(new BorderLayout(10, 10));

        searchPanel.setOpaque(false);

        JLabel searchLabel =
                new JLabel("Search:");

        searchLabel.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        searchField = new JTextField();

        JButton searchButton =
                createButton("Search");

        JButton refreshButton =
                createButton("Refresh");

        JPanel searchButtons =
                new JPanel(new GridLayout(1, 2, 8, 0));

        searchButtons.setOpaque(false);

        searchButtons.add(searchButton);
        searchButtons.add(refreshButton);

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButtons,
                BorderLayout.EAST
        );

        // ================= TABLE =================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Member ID",
                                "Name",
                                "Email",
                                "Phone"
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

        memberTable =
                new JTable(tableModel);

        memberTable.setRowHeight(30);

        memberTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        memberTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        memberTable.getTableHeader().setBackground(DARK);
        memberTable.getTableHeader().setForeground(Color.WHITE);

        memberTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // Column width

        memberTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);

        memberTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(200);

        memberTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(300);

        memberTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(180);

        JScrollPane scrollPane =
                new JScrollPane(memberTable);

        // ================= BUTTONS =================

        JButton addButton =
                createButton("Add Member");

        JButton updateButton =
                createButton("Update Member");

        JButton deleteButton =
                createButton("Delete Member");

        JButton clearButton =
                createButton("Clear");

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 4, 10, 10));

        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // ================= TOP PANEL =================

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.setOpaque(false);

        topPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        topPanel.add(
                searchPanel,
                BorderLayout.SOUTH
        );

        // ================= TABLE PANEL =================

        JPanel tablePanel =
                new JPanel(new BorderLayout(10, 10));

        tablePanel.setOpaque(false);

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        tablePanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ================= CENTER PANEL =================

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

        addButton.addActionListener(
                e -> addMember()
        );

        updateButton.addActionListener(
                e -> updateMember()
        );

        deleteButton.addActionListener(
                e -> deleteMember()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        searchButton.addActionListener(
                e -> searchMembers()
        );

        refreshButton.addActionListener(
                e -> loadMembers()
        );

        // ================= ROW SELECTION =================

        memberTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                memberTable.getSelectedRow();

                        if (row >= 0) {

                            idField.setText(
                                    tableModel
                                            .getValueAt(row, 0)
                                            .toString()
                            );

                            nameField.setText(
                                    tableModel
                                            .getValueAt(row, 1)
                                            .toString()
                            );

                            emailField.setText(
                                    tableModel
                                            .getValueAt(row, 2)
                                            .toString()
                            );

                            phoneField.setText(
                                    tableModel
                                            .getValueAt(row, 3)
                                            .toString()
                            );
                        }
                    }
                });

        // Load members when window opens

        loadMembers();
    }

    // ================= ADD MEMBER =================

    private void addMember() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        Member member =
                new Member(
                        0,
                        name,
                        email,
                        phone
                );

        boolean success =
                memberService.addMember(member);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Member added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadMembers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Member could not be added!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= UPDATE MEMBER =================

    private void updateMember() {

        try {

            int memberId =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            String name =
                    nameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            Member member =
                    new Member(
                            memberId,
                            name,
                            email,
                            phone
                    );

            boolean success =
                    memberService.updateMember(member);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Member updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadMembers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Member could not be updated!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Member ID must be a number!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= DELETE MEMBER =================

    private void deleteMember() {

        try {

            int memberId =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this member?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success =
                    memberService.deleteMember(memberId);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Member deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadMembers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Member could not be deleted!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Member ID!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= SEARCH MEMBERS =================

    private void searchMembers() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadMembers();
            return;
        }

        List<Member> members =
                memberService.searchMembers(keyword);

        displayMembers(members);
    }

    // ================= LOAD MEMBERS =================

    private void loadMembers() {

        List<Member> members =
                memberService.getAllMembers();

        displayMembers(members);
    }

    // ================= DISPLAY MEMBERS =================

    private void displayMembers(
            List<Member> members) {

        tableModel.setRowCount(0);

        for (Member member : members) {

            tableModel.addRow(
                    new Object[]{
                            member.getMemberId(),
                            member.getName(),
                            member.getEmail(),
                            member.getPhone()
                    }
            );
        }
    }

    // ================= CLEAR FIELDS =================

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        searchField.setText("");

        memberTable.clearSelection();
    }

    // ================= CREATE LABEL =================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        label.setForeground(DARK);

        return label;
    }

    // ================= CREATE BUTTON =================

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
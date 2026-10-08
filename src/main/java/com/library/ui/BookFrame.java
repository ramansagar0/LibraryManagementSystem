package com.library.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import com.library.model.Book;
import com.library.service.BookService;

public class BookFrame extends JFrame {

    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField searchField;

    private JTable bookTable;
    private DefaultTableModel tableModel;

    private BookService bookService;

    private final Color DARK = new Color(35, 55, 75);
    private final Color BACKGROUND = new Color(240, 244, 248);
    private final Color WHITE = Color.WHITE;

    public BookFrame() {

        bookService = new BookService();

        setTitle("Library Management System - Book Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // ================= HEADER =================

        JLabel titleLabel = new JLabel("Book Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(DARK);

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ================= FORM =================

        JPanel formPanel = new JPanel(new GridLayout(2, 5, 10, 8));
        formPanel.setBackground(WHITE);
        formPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        categoryField = new JTextField();
        quantityField = new JTextField();

        formPanel.add(createLabel("Book ID"));
        formPanel.add(createLabel("Title"));
        formPanel.add(createLabel("Author"));
        formPanel.add(createLabel("Category"));
        formPanel.add(createLabel("Quantity"));

        formPanel.add(idField);
        formPanel.add(titleField);
        formPanel.add(authorField);
        formPanel.add(categoryField);
        formPanel.add(quantityField);

        // ================= SEARCH PANEL =================

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(WHITE);
        searchPanel.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel searchLabel = new JLabel("Search Book:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchLabel.setForeground(DARK);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 35));

        JButton searchButton = createButton("Search");
        JButton refreshButton = createButton("Refresh");

        JPanel searchButtonPanel =
                new JPanel(new GridLayout(1, 2, 8, 0));

        searchButtonPanel.setOpaque(false);

        searchButtonPanel.add(searchButton);
        searchButtonPanel.add(refreshButton);

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButtonPanel,
                BorderLayout.EAST
        );

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

        // ================= TABLE =================

        tableModel = new DefaultTableModel(
                new String[]{
                        "Book ID",
                        "Title",
                        "Author",
                        "Category",
                        "Quantity"
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

        bookTable = new JTable(tableModel);

        bookTable.setRowHeight(30);
        bookTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        bookTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        bookTable.getTableHeader().setBackground(DARK);
        bookTable.getTableHeader().setForeground(Color.WHITE);

        bookTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(bookTable);

        // ================= BUTTONS =================

        JButton addButton = createButton("Add Book");
        JButton updateButton = createButton("Update Book");
        JButton deleteButton = createButton("Delete Book");
        JButton clearButton = createButton("Clear");

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 4, 10, 10));

        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // ================= CENTER PANEL =================

        JPanel centerPanel =
                new JPanel(new BorderLayout(12, 12));

        centerPanel.setOpaque(false);

        centerPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        centerPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // ================= ACTIONS =================

        addButton.addActionListener(
                e -> addBook()
        );

        updateButton.addActionListener(
                e -> updateBook()
        );

        deleteButton.addActionListener(
                e -> deleteBook()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        searchButton.addActionListener(
                e -> searchBooks()
        );

        refreshButton.addActionListener(
                e -> loadBooks()
        );

        // ================= TABLE SELECTION =================

        bookTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                bookTable.getSelectedRow();

                        if (row >= 0) {

                            idField.setText(
                                    tableModel
                                            .getValueAt(row, 0)
                                            .toString()
                            );

                            titleField.setText(
                                    tableModel
                                            .getValueAt(row, 1)
                                            .toString()
                            );

                            authorField.setText(
                                    tableModel
                                            .getValueAt(row, 2)
                                            .toString()
                            );

                            categoryField.setText(
                                    tableModel
                                            .getValueAt(row, 3)
                                            .toString()
                            );

                            quantityField.setText(
                                    tableModel
                                            .getValueAt(row, 4)
                                            .toString()
                            );
                        }
                    }
                });

        loadBooks();
    }

    // ================= ADD BOOK =================

    private void addBook() {

        try {

            String title =
                    titleField.getText().trim();

            String author =
                    authorField.getText().trim();

            String category =
                    categoryField.getText().trim();

            int quantity =
                    Integer.parseInt(
                            quantityField.getText().trim()
                    );

            Book book =
                    new Book(
                            0,
                            title,
                            author,
                            category,
                            quantity
                    );

            boolean success =
                    bookService.addBook(book);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadBooks();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Book could not be added!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a number!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= UPDATE BOOK =================

    private void updateBook() {

        try {

            int bookId =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            String title =
                    titleField.getText().trim();

            String author =
                    authorField.getText().trim();

            String category =
                    categoryField.getText().trim();

            int quantity =
                    Integer.parseInt(
                            quantityField.getText().trim()
                    );

            Book book =
                    new Book(
                            bookId,
                            title,
                            author,
                            category,
                            quantity
                    );

            boolean success =
                    bookService.updateBook(book);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadBooks();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Book could not be updated!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book ID and Quantity must be numbers!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= DELETE BOOK =================

    private void deleteBook() {

        try {

            int bookId =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this book?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success =
                    bookService.deleteBook(bookId);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadBooks();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Book could not be deleted!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Book ID!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= SEARCH BOOKS =================

    private void searchBooks() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadBooks();
            return;
        }

        List<Book> books =
                bookService.searchBooks(keyword);

        displayBooks(books);
    }

    // ================= LOAD BOOKS =================

    private void loadBooks() {

        List<Book> books =
                bookService.getAllBooks();

        displayBooks(books);
    }

    // ================= DISPLAY BOOKS =================

    private void displayBooks(List<Book> books) {

        tableModel.setRowCount(0);

        for (Book book : books) {

            tableModel.addRow(
                    new Object[]{
                            book.getBookId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory(),
                            book.getQuantity()
                    }
            );
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        searchField.setText("");

        bookTable.clearSelection();
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
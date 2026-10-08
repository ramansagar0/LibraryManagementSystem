package com.library.service;

import java.time.LocalDate;
import java.util.List;

import com.library.dao.BookDAO;
import com.library.dao.IssueRecordDAO;
import com.library.model.IssueRecord;

public class IssueService {

    private final IssueRecordDAO issueRecordDAO;
    private final BookDAO bookDAO;

    public IssueService() {
        issueRecordDAO = new IssueRecordDAO();
        bookDAO = new BookDAO();
    }

    // Issue a book
    public boolean issueBook(IssueRecord record) {

        if (record.getBookId() <= 0) {
            return false;
        }

        if (record.getMemberId() <= 0) {
            return false;
        }

        if (record.getIssueDate() == null) {
            return false;
        }

        // Decrease book quantity
        boolean quantityUpdated =
                bookDAO.decreaseQuantity(record.getBookId());

        if (!quantityUpdated) {
            return false;
        }

        // Create issue record
        boolean issued =
                issueRecordDAO.issueBook(record);

        // If issue record failed, restore quantity
        if (!issued) {
            bookDAO.increaseQuantity(record.getBookId());
            return false;
        }

        return true;
    }

    // Get all issue records
    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordDAO.getAllIssueRecords();
    }

    // Return a book
    public boolean returnBook(int issueId) {

        if (issueId <= 0) {
            return false;
        }

        List<IssueRecord> records =
                issueRecordDAO.getAllIssueRecords();

        for (IssueRecord record : records) {

            if (record.getIssueId() == issueId) {

                // Check if book is already returned
                if (record.getReturnDate() != null) {
                    return false;
                }

                // Update return date
                boolean returned =
                        issueRecordDAO.returnBook(
                                issueId,
                                LocalDate.now()
                        );

                if (!returned) {
                    return false;
                }

                // Increase book quantity
                return bookDAO.increaseQuantity(
                        record.getBookId()
                );
            }
        }

        return false;
    }

    // Get currently issued book count
    public int getIssuedBookCount() {
        return issueRecordDAO.getIssuedBookCount();
    }
}
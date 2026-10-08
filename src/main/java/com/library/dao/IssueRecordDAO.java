package com.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.library.database.DatabaseConnection;
import com.library.model.IssueRecord;

public class IssueRecordDAO {

    // Issue a book
    public boolean issueBook(IssueRecord record) {

        String sql = "INSERT INTO issue_records " +
                     "(book_id, member_id, issue_date, return_date) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, record.getBookId());
            statement.setInt(2, record.getMemberId());
            statement.setDate(3, Date.valueOf(record.getIssueDate()));

            if (record.getReturnDate() != null) {
                statement.setDate(
                        4,
                        Date.valueOf(record.getReturnDate())
                );
            } else {
                statement.setNull(4, Types.DATE);
            }

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all issue records
    public List<IssueRecord> getAllIssueRecords() {

        List<IssueRecord> records = new ArrayList<>();

        String sql = "SELECT * FROM issue_records";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {

                Date returnDate =
                        resultSet.getDate("return_date");

                IssueRecord record = new IssueRecord(
                        resultSet.getInt("issue_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getDate("issue_date").toLocalDate(),
                        returnDate != null
                                ? returnDate.toLocalDate()
                                : null
                );

                records.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    // Return a book
    public boolean returnBook(
            int issueId,
            LocalDate returnDate) {

        String sql = "UPDATE issue_records " +
                     "SET return_date = ? " +
                     "WHERE issue_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(
                    1,
                    Date.valueOf(returnDate)
            );

            statement.setInt(2, issueId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get number of currently issued books
    public int getIssuedBookCount() {

        String sql = "SELECT COUNT(*) AS total " +
                     "FROM issue_records " +
                     "WHERE return_date IS NULL";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
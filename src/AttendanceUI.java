import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class AttendanceUI extends JFrame {
    private JCheckBox[][] attendanceCheckboxes;
    private JButton submitButton;
    private JButton displayButton;
    private JButton searchButton;
    private JButton clearButton;
    private JButton downloadButton;
    private DefaultTableModel tableModel;
    private Connection connection;

    public AttendanceUI() {
        this.setTitle("Attendance System");
        this.setSize(850, 550);
        this.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Weekly Attendance");
        titleLabel.setFont(new Font("Arial", 1, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setHorizontalAlignment(0);
        int bottomSpace = 12;
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, bottomSpace, 0);
        titleLabel.setBorder(emptyBorder);
        this.add(titleLabel, "North");
        String[] studentNames = new String[]{"1.Pranay", "2.Atharva", "3.Aditya", "4.Shreyas", "5.Aryan", "6.Prem", "7.Sarang", "8.Pawan"};
        String[] dayNames = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] dates = new String[]{"01-05-2023", "2-05-2023", "03-05-2023", "04-05-2023", "05-05-2023", "06-05-2023"};
        JPanel checkboxPanel = new JPanel(new GridLayout(studentNames.length + 1, dayNames.length + 1));
        checkboxPanel.setPreferredSize(new Dimension(600, 300));
        checkboxPanel.add(new JLabel());

        int i;
        JLabel studentLabel;
        JPanel labelContainer;
        for(i = 0; i < dayNames.length; ++i) {
            JLabel dayLabel = new JLabel(dayNames[i]);
            dayLabel.setFont(new Font("Arial", 1, 17));
            dayLabel.setForeground(new Color(52, 73, 94));
            dayLabel.setHorizontalAlignment(0);
            studentLabel = new JLabel(dates[i]);
            studentLabel.setFont(new Font("Arial", 0, 14));
            studentLabel.setForeground(Color.GRAY);
            studentLabel.setHorizontalAlignment(0);
            labelContainer = new JPanel(new BorderLayout());
            labelContainer.setBackground(Color.WHITE);
            labelContainer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            labelContainer.add(dayLabel, "North");
            labelContainer.add(studentLabel, "South");
            checkboxPanel.add(labelContainer);
        }

        this.attendanceCheckboxes = new JCheckBox[studentNames.length][dayNames.length];

        for(i = 0; i < studentNames.length; ++i) {
            JPanel studentPanel = new JPanel(new BorderLayout());
            studentPanel.setBackground(new Color(240, 240, 240));
            studentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            studentLabel = new JLabel(studentNames[i]);
            studentLabel.setFont(new Font("Arial", 1, 20));
            studentLabel.setForeground(new Color(52, 73, 94));
            labelContainer = new JPanel(new FlowLayout(1, 10, 0));
            labelContainer.setOpaque(false);
            labelContainer.add(studentLabel);
            studentPanel.add(labelContainer, "Center");
            checkboxPanel.add(studentPanel);

            for(int j = 0; j < dayNames.length; ++j) {
                this.attendanceCheckboxes[i][j] = new JCheckBox();
                this.attendanceCheckboxes[i][j].setBackground(new Color(0, 240, 240));
                this.attendanceCheckboxes[i][j].setBorderPainted(false);
                this.attendanceCheckboxes[i][j].setContentAreaFilled(false);
                this.attendanceCheckboxes[i][j].setForeground(new Color(52, 152, 219));
                this.attendanceCheckboxes[i][j].setFont(new Font("Arial", 0, 12));
                ImageIcon uncheckedIcon = new ImageIcon("C:\\Users\\kames\\IdeaProjects\\Virtual Attendance\\ext_files\\icons8-a-button-(blood-type)-32.png");
                ImageIcon checkedIcon = new ImageIcon("C:\\Users\\kames\\IdeaProjects\\Virtual Attendance\\ext_files\\icons8-p-32.png");
                this.attendanceCheckboxes[i][j].setIcon(uncheckedIcon);
                this.attendanceCheckboxes[i][j].setSelectedIcon(checkedIcon);
                this.attendanceCheckboxes[i][j].setVerticalAlignment(0);
                this.attendanceCheckboxes[i][j].setHorizontalAlignment(0);
                this.attendanceCheckboxes[i][j].setToolTipText("Mark attendance for " + studentNames[i] + " on " + dayNames[j]);
                checkboxPanel.add(this.attendanceCheckboxes[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(1));
        this.submitButton = new JButton("Submit");
        this.submitButton.setBackground(new Color(23, 162, 184));
        this.submitButton.setForeground(Color.WHITE);
        this.submitButton.setPreferredSize(new Dimension(120, 40));
        this.submitButton.setFont(new Font("Arial", 1, 16));
        this.displayButton = new JButton("Display ");
        this.displayButton.setBackground(new Color(39, 174, 96));
        this.displayButton.setForeground(Color.WHITE);
        this.displayButton.setPreferredSize(new Dimension(160, 40));
        this.displayButton.setFont(new Font("Arial", 1, 16));
        this.searchButton = new JButton("Search");
        this.searchButton.setBackground(new Color(0, 123, 255));
        this.searchButton.setForeground(Color.WHITE);
        this.searchButton.setPreferredSize(new Dimension(100, 40));
        this.searchButton.setFont(new Font("Arial", 1, 16));
        this.clearButton = new JButton("Clear");
        this.clearButton.setBackground(new Color(220, 53, 69));
        this.clearButton.setForeground(Color.WHITE);
        this.clearButton.setPreferredSize(new Dimension(100, 40));
        this.clearButton.setFont(new Font("Arial", 1, 16));
        this.downloadButton = new JButton("Download");
        this.downloadButton.setBackground(new Color(200, 59, 200));
        this.downloadButton.setForeground(Color.WHITE);
        this.downloadButton.setPreferredSize(new Dimension(150, 40));
        this.downloadButton.setFont(new Font("Arial", 1, 16));
        buttonPanel.add(this.submitButton);
        buttonPanel.add(this.displayButton);
        buttonPanel.add(this.searchButton);
        buttonPanel.add(this.clearButton);
        buttonPanel.add(this.downloadButton);
        this.add(checkboxPanel, "Center");
        this.add(buttonPanel, "South");
        this.submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceUI.this.processAttendance();
            }
        });
        this.displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceUI.this.displayAttendance();
            }
        });
        this.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceUI.this.searchAttendance();
            }
        });
        this.clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceUI.this.clearAttendanceRecords();
            }
        });
        this.downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceUI.this.generateAttendancePDFFromDatabase();
            }
        });
        this.setDefaultCloseOperation(3);
    }

    private void clearAttendanceRecords() {
        String url = "jdbc:mysql://localhost:3306/onlineattendance";
        String username = "root";
        String password = "your_password";

        try {
            this.connection = DriverManager.getConnection(url, username, password);
            String query = "TRUNCATE TABLE attendance";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            JOptionPane.showMessageDialog(this, "Attendance records cleared successfully.");
        } catch (SQLException var14) {
            var14.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error clearing attendance records.");
        } finally {
            if (this.connection != null) {
                try {
                    this.connection.close();
                } catch (SQLException var13) {
                    var13.printStackTrace();
                }
            }

        }

    }

    private void processAttendance() {
        String url = "jdbc:mysql://localhost:3306/onlineattendance";
        String username = "root";
        String password = "your_password";

        try {
            this.connection = DriverManager.getConnection(url, username, password);

            int i;
            int j;
            for(i = 0; i < this.attendanceCheckboxes.length; ++i) {
                for(j = 0; j < this.attendanceCheckboxes[i].length; ++j) {
                    JCheckBox checkbox = this.attendanceCheckboxes[i][j];
                    boolean attended = checkbox.isSelected();
                    String studentName = this.getStudentName(i);
                    String dayName = this.getDayName(j);
                    String attendanceDate = this.getAttendanceDate(j);
                    this.insertAttendanceRecord(studentName, dayName, attended, attendanceDate);
                }
            }

            JOptionPane.showMessageDialog(this, "Attendance records inserted successfully.");

            for(i = 0; i < this.attendanceCheckboxes.length; ++i) {
                for(j = 0; j < this.attendanceCheckboxes[i].length; ++j) {
                    this.attendanceCheckboxes[i][j].setSelected(false);
                }
            }
        } catch (SQLException var19) {
            var19.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting attendance records.");
        } finally {
            if (this.connection != null) {
                try {
                    this.connection.close();
                } catch (SQLException var18) {
                    var18.printStackTrace();
                }
            }

        }

    }

    private void insertAttendanceRecord(String studentName, String dayName, boolean attended, String attendanceDate) throws SQLException {
        String query = "INSERT INTO attendance (student_name, day_name, attended, attendance_date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.setString(1, studentName);
        statement.setString(2, dayName);
        statement.setBoolean(3, attended);
        statement.setString(4, attendanceDate);
        statement.executeUpdate();
        statement.close();
    }

    private String getStudentName(int index) {
        String[] studentNames = new String[]{"1.Pranay", "2.Atharva", "3.Aditya", "4.Shreyas", "5.Aryan", "6.Prem", "7.Sarang", "8.Pawan"};
        return studentNames[index];
    }

    private String getDayName(int index) {
        String[] dayNames = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return dayNames[index];
    }

    private String getAttendanceDate(int index) {
        String[] dates = new String[]{"2023-06-01", "2023-06-02", "2023-06-03", "2023-06-04", "2023-06-05", "2023-06-06"};
        return dates[index];
    }

    private void displayAttendance() {
        String url = "jdbc:mysql://localhost:3306/onlineattendance";
        String username = "root";
        String password = "your_password";

        try {
            this.connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM attendance";
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Student Name");
            tableModel.addColumn("Day Name");
            tableModel.addColumn("Attended");
            tableModel.addColumn("Attendance Date");

            while(resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                String dayName = resultSet.getString("day_name");
                String attendanceStatus = resultSet.getBoolean("attended") ? "Present" : "Absent";
                String attendanceDate = resultSet.getString("attendance_date");
                tableModel.addRow(new Object[]{studentName, dayName, attendanceStatus, attendanceDate});
            }

            JTable attendanceTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(attendanceTable);
            JFrame tableFrame = new JFrame("Attendance Records");
            tableFrame.setDefaultCloseOperation(2);
            tableFrame.setSize(800, 600);
            tableFrame.setLayout(new BorderLayout());
            tableFrame.add(scrollPane, "Center");
            tableFrame.setVisible(true);
            resultSet.close();
            statement.close();
        } catch (SQLException var20) {
            var20.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching attendance records.");
        } finally {
            if (this.connection != null) {
                try {
                    this.connection.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

        }

    }

    private void generateAttendancePDFFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/onlineattendance";
        String username = "root";
        String password = "your_password";
        String outputFile = "attendance_records.pdf";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM attendance";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();
            PdfPTable table = new PdfPTable(resultSet.getMetaData().getColumnCount());
            table.setWidthPercentage(100.0F);
            com.itextpdf.text.Font headerFont = FontFactory.getFont("Helvetica-Bold", 12.0F, BaseColor.WHITE);

            for(int columnIndex = 1; columnIndex <= resultSet.getMetaData().getColumnCount(); ++columnIndex) {
                PdfPCell headerCell = new PdfPCell(new Paragraph(resultSet.getMetaData().getColumnName(columnIndex), headerFont));
                headerCell.setHorizontalAlignment(1);
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setPadding(5.0F);
                table.addCell(headerCell);
            }

            com.itextpdf.text.Font cellFont = FontFactory.getFont("Helvetica", 10.0F);

            while(resultSet.next()) {
                for(int columnIndex = 1; columnIndex <= resultSet.getMetaData().getColumnCount(); ++columnIndex) {
                    Object cellValue;
                    if (resultSet.getMetaData().getColumnName(columnIndex).equals("attended")) {
                        boolean attendanceStatus = resultSet.getBoolean(columnIndex);
                        cellValue = attendanceStatus ? "Present" : "Absent";
                    } else {
                        cellValue = resultSet.getObject(columnIndex);
                    }

                    PdfPCell dataCell = new PdfPCell(new Paragraph(cellValue.toString(), cellFont));
                    dataCell.setHorizontalAlignment(1);
                    dataCell.setPadding(5.0F);
                    table.addCell(dataCell);
                }
            }

            document.add(table);
            document.close();
            resultSet.close();
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(this, "Attendance records saved as PDF: " + outputFile);
        } catch (Exception var16) {
            var16.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating PDF: " + var16.getMessage());
        }

    }

    private void searchAttendance() {
        String studentName = JOptionPane.showInputDialog(this, "Enter student name:");
        String url = "jdbc:mysql://localhost:3306/onlineattendance";
        String username = "root";
        String password = "your_password";

        try {
            this.connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM attendance WHERE student_name = ?";
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, studentName);
            ResultSet resultSet = statement.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Student Name");
            tableModel.addColumn("Day Name");
            tableModel.addColumn("Attended");
            tableModel.addColumn("Attendance Date");

            while(resultSet.next()) {
                String dayName = resultSet.getString("day_name");
                String attendanceStatus = resultSet.getBoolean("attended") ? "Present" : "Absent";
                String attendanceDate = resultSet.getString("attendance_date");
                tableModel.addRow(new Object[]{studentName, dayName, attendanceStatus, attendanceDate});
            }

            JTable attendanceTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(attendanceTable);
            JFrame tableFrame = new JFrame("Attendance Records for " + studentName);
            tableFrame.setDefaultCloseOperation(2);
            tableFrame.setSize(800, 600);
            tableFrame.setLayout(new BorderLayout());
            tableFrame.add(scrollPane, "Center");
            tableFrame.setVisible(true);
            resultSet.close();
            statement.close();
        } catch (SQLException var20) {
            var20.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching attendance records.");
        } finally {
            if (this.connection != null) {
                try {
                    this.connection.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AttendanceUI attendanceUI = new AttendanceUI();
                attendanceUI.setVisible(true);
            }
        });
    }
}

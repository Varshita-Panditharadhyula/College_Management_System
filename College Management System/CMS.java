import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CollegeManagementSystem extends JFrame {
    private JButton studentButton;
    private JButton facultyButton;
    private JButton courseButton;
    private JButton attendanceButton;
    private JButton libraryButton;

    private List<Student> students;
    private List<Faculty> faculties;
    private List<Course> courses;
    private List<AttendanceRecord> attendanceRecords;
    private Library library;

    public CollegeManagementSystem() {
        students = new ArrayList<>();
        faculties = new ArrayList<>();
        courses = new ArrayList<>();
        attendanceRecords = new ArrayList<>();
        library = new Library();
        studentButton = new JButton("Student");
        facultyButton = new JButton("Faculty");
        courseButton = new JButton("Course");
        attendanceButton = new JButton("Attendance");
        libraryButton = new JButton("Library");
        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStudentPanel();
            }
        });
        facultyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFacultyPanel();
            }
        });
        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCoursePanel();
            }
        });
        attendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAttendancePanel();
            }
        });
        libraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLibraryPanel();
            }
        });
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(studentButton);
        add(facultyButton);
        add(courseButton);
        add(attendanceButton);
        add(libraryButton);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void showStudentPanel() {
        JFrame studentFrame = new JFrame("Student Panel");
        studentFrame.setLayout(new FlowLayout());
        JButton enrollCourseButton = new JButton("Enroll in Course");
        JButton enterDetailsButton = new JButton("Enter Student Details");
        enrollCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = JOptionPane.showInputDialog("Enter Course Name:");
                Course selectedCourse = findCourse(courseName);
                if (selectedCourse != null) {
                    Student currentStudent = getCurrentStudent();
                    currentStudent.enrollInCourse(selectedCourse);
                    JOptionPane.showMessageDialog(null, "Enrolled in Course: " + courseName);
                } else {
                    JOptionPane.showMessageDialog(null, "Course not found.");
                }
            }
        });
        enterDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = JOptionPane.showInputDialog("Enter Student Name:");
                int studentId = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
                students.add(new Student(studentName, studentId));
                JOptionPane.showMessageDialog(null, "Student Details Entered");
            }
        });
        studentFrame.add(enrollCourseButton);
        studentFrame.add(enterDetailsButton);
        studentFrame.setSize(300, 200);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentFrame.setVisible(true);
    }
    private void showFacultyPanel() {
        JFrame facultyFrame = new JFrame("Faculty Panel");
        facultyFrame.setLayout(new FlowLayout());
        JButton markAttendanceButton = new JButton("Mark Attendance");
        JButton giveRemarksButton = new JButton("Give Remarks");
        markAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = JOptionPane.showInputDialog("Enter Student Name:");
                Student student = findStudent(studentName);
                if (student != null) {
                    facultyMarkAttendance(student);
                    JOptionPane.showMessageDialog(null, "Attendance Marked for: " + studentName);
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            }
        });

        giveRemarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = JOptionPane.showInputDialog("Enter Student Name:");
                String remarks = JOptionPane.showInputDialog("Enter Remarks:");
                Student student = findStudent(studentName);
                if (student != null) {
                    facultyGiveRemarks(student, remarks);
                    JOptionPane.showMessageDialog(null, "Remarks Given for: " + studentName);
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            }
        });
        facultyFrame.add(markAttendanceButton);
        facultyFrame.add(giveRemarksButton);
        facultyFrame.setSize(300, 200);
        facultyFrame.setLocationRelativeTo(null);
        facultyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        facultyFrame.setVisible(true);
    }

    private void showCoursePanel() {
        JFrame courseFrame = new JFrame("Course Panel");
        courseFrame.setLayout(new FlowLayout());
        JButton addCourseButton = new JButton("Add Course");
        JButton viewAssignedCoursesButton = new JButton("View Assigned Courses");
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = JOptionPane.showInputDialog("Enter Course Name:");
                int courseId = Integer.parseInt(JOptionPane.showInputDialog("Enter Course ID:"));
                courses.add(new Course(courseName, courseId));
                JOptionPane.showMessageDialog(null, "Course Added: " + courseName);
            }
        });
        viewAssignedCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder assignedCourses = new StringBuilder("Assigned Courses:\n");
                Student currentStudent = getCurrentStudent();
                if (currentStudent != null) {
                    Course enrolledCourse = currentStudent.getEnrolledCourse();
                    assignedCourses.append(enrolledCourse != null ? enrolledCourse.getCourseName() : "None");
                } else {
                    assignedCourses.append("No student selected");
                }
                JOptionPane.showMessageDialog(null, assignedCourses.toString());
            }
        });
        courseFrame.add(addCourseButton);
        courseFrame.add(viewAssignedCoursesButton)
        courseFrame.setSize(300, 200);
        courseFrame.setLocationRelativeTo(null);
        courseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        courseFrame.setVisible(true);
    }
    private void showAttendancePanel() {
        JFrame attendanceFrame = new JFrame("Attendance Panel");
        attendanceFrame.setLayout(new FlowLayout());
        JButton studentCheckAttendanceButton = new JButton("Check Attendance");
        JButton facultyMarkAttendanceButton = new JButton("Mark Attendance");
        studentCheckAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student currentStudent = getCurrentStudent();
                if (currentStudent != null) {
                    String attendanceInfo = studentCheckAttendance(currentStudent);
                    JOptionPane.showMessageDialog(null, "Attendance: " + attendanceInfo);
                } else {
                    JOptionPane.showMessageDialog(null, "No student selected");
                }
            }
        });
        facultyMarkAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = JOptionPane.showInputDialog("Enter Student Name:");
                Student student = findStudent(studentName);
                if (student != null) {
                    facultyMarkAttendance(student);
                    JOptionPane.showMessageDialog(null, "Attendance Marked for: " + studentName);
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            }
        });
        attendanceFrame.add(studentCheckAttendanceButton);
        attendanceFrame.add(facultyMarkAttendanceButton);
        attendanceFrame.setSize(300, 200);
        attendanceFrame.setLocationRelativeTo(null);
        attendanceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        attendanceFrame.setVisible(true);
    }
    private void showLibraryPanel() {
        JFrame libraryFrame = new JFrame("Library Panel");
        libraryFrame.setLayout(new FlowLayout());
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton dueDateButton = new JButton("Book Due Date");
        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = JOptionPane.showInputDialog("Enter Book Title:");
                String author = JOptionPane.showInputDialog("Enter Author:");
                Book book = new Book(bookTitle, author);
                Student currentStudent = getCurrentStudent();
                if (currentStudent != null) {
                    currentStudent.borrowBook(book);
                    library.issueBook(currentStudent, book);
                    JOptionPane.showMessageDialog(null, "Book Borrowed: " + book.getTitle());
                } else {
                    JOptionPane.showMessageDialog(null, "No student selected");
                }
            }
        });
        dueDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student currentStudent = getCurrentStudent();
                if (currentStudent != null && currentStudent.getBorrowedBook() != null) {
                    Book borrowedBook = currentStudent.getBorrowedBook();
                    JOptionPane.showMessageDialog(null, "Due Date: " library.getDueDate(borrowedBook));
                } else {
                    JOptionPane.showMessageDialog(null, "No student or book selected");
                }
            }
        });
        libraryFrame.add(borrowBookButton);
        libraryFrame.add(dueDateButton);
        libraryFrame.setSize(300, 200);
        libraryFrame.setLocationRelativeTo(null);
        libraryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        libraryFrame.setVisible(true);
    }
    // Helper methods
    private Student getCurrentStudent() {
        String studentName = JOptionPane.showInputDialog("Enter Student Name:");
        return findStudent(studentName);
    }
    private Course findCourse(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }
    private Student findStudent(String studentName) {
        for (Student student : students) {
            if (student.getName().equals(studentName)) {
                return student;
            }
        }
        return null;
    }
    private void facultyMarkAttendance(Student student) {
        AttendanceRecord record = new AttendanceRecord(student, true,
        		new java.util.Date());
        attendanceRecords.add(record);
    }
    private void facultyGiveRemarks(Student student, String remarks) {
        student.setRemarks(remarks);
    }
    private String studentCheckAttendance(Student student) {
        StringBuilder attendanceInfo = new StringBuilder("Attendance:\n");
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getStudent().equals(student)) {
                attendanceInfo.append(record.isPresent() ? "Present" : "Absent")
                        .append(" on ")
                        .append(record.getDate())
                        .append("\n");
            }
        }
        return attendanceInfo.toString();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CollegeManagementSystem();
            }
        });
    }
}
class Student {
    private String name;
    private int id;
    private Course enrolledCourse;
    private Book borrowedBook;
    private String remarks;
    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void enrollInCourse(Course course) {
        enrolledCourse = course;
    }
    public Course getEnrolledCourse() {
        return enrolledCourse;
    }
    public void borrowBook(Book book) {
        borrowedBook = book;
    }
    public Book getBorrowedBook() {
        return borrowedBook;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
class Faculty {}
class Course {
    private String courseName;
    private int courseId;
    public Course(String courseName, int courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
    }
    public String getCourseName() {
        return courseName;
    }
}
class AttendanceRecord {
    private Student student;
    private boolean present;
    private java.util.Date date;
    public AttendanceRecord(Student student, boolean present,java.util.Date date) {
        this.student = student;
        this.present = present;
        this.date = date;
    }
    public Student getStudent() {
        return student;
    }
    public boolean isPresent() {
        return present;
    }
    public java.util.Date getDate() {
        return date;
 }
}
class Library {
    private List<Book> availableBooks;
    private List<Book> borrowedBooks;
    public Library() {
        availableBooks = new ArrayList();
        borrowedBooks = new ArrayList<>();
    }
    public void issueBook(Student student, Book book) {
        availableBooks.remove(book);
        borrowedBooks.add(book);
    }
    public String getDueDate(Book book) {   return "Due in 14 days";    }
}
class Book {
    private String title;
    private String author;
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;    }}

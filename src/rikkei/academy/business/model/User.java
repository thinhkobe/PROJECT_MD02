package rikkei.academy.business.model;

import rikkei.academy.business.until.InputMethods;
import rikkei.academy.business.until.validation.UserValidate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class
User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String email;
    private boolean status=true;
    private UserRoles role;
    private String phone;
    private LocalDate createdDate;
    private List<Exam> examSessions;

    private List<Message> messageList=new ArrayList<>();

    public User() {
    }

    public User(int userId, String username, String password, String email, boolean status, UserRoles role, String phone, LocalDate createdDate, List<Exam> examSessions, List<Message> messageList) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
        this.phone = phone;
        this.createdDate = createdDate;
        this.examSessions = examSessions;
        this.messageList = messageList;
    }

    // Getter và setter cho các thuộc tính


    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public List<Exam> getExamSessions() {
        return examSessions;
    }

    public void setExamSessions(List<Exam> examSessions) {
        this.examSessions = examSessions;
    }



    // Phương thức để người dùng nhập thông tin với kiểm tra
    public void inputData(boolean isAdd) {

        // Nhập và kiểm tra tên đăng nhập
        while (true) {
            System.out.print("Nhập tên đăng nhập: ");
            String inputUsername = InputMethods.getString();

            if (UserValidate.isUsernameUnique(inputUsername)) {
                this.username = inputUsername;
                break;
            } else {
                System.err.println("Lỗi: Tên đăng nhập đã tồn tại. Vui lòng chọn tên đăng nhập khác.");
            }
        }

        // Nhập và kiểm tra mật khẩu
        while (true) {
            System.out.print("Nhập mật khẩu: ");
            String inputPassword = InputMethods.getString();

            if (UserValidate.isPasswordValid(inputPassword)) {
                this.password = inputPassword;
                break;
            } else {
                System.err.println("Lỗi: Mật khẩu phải có ít nhất 6 kí tự. Vui lòng nhập lại.");
            }
        }

        // Nhập và kiểm tra vai trò
        while (true) {
            System.out.print("Nhập vai trò 1.student hoặc 2.teacher): ");
            int inputRole = InputMethods.getInteger();

            if (UserValidate.isRoleValid(inputRole)) {
                if (inputRole==1){
                    this.role=UserRoles.STUDENT;
                    break;

                } else if (inputRole==2) {
                    this.role=UserRoles.TEACHER;
                    break;
                }
                break;
            } else {
                System.err.println("Lỗi: Vai trò phải là 1 hoặc 2. Vui lòng nhập lại.");
            }
        }
        // Gán ngày tạo với định dạng "dd/MM/yyyy"
        this.createdDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", status=" +( status?"hoạt động":"đã bị chặn") +
                ", role=" + role +
                ", phone=" + phone +
                ", createdDate=" + createdDate +
                ", examSessions=" + examSessions +
                '}';
    }
    public void setMessage(Message message){
        this.messageList.add(message);
    }
}

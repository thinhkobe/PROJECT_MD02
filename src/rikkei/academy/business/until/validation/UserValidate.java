package rikkei.academy.business.until.validation;

import rikkei.academy.business.model.User;
import rikkei.academy.business.until.IOFile;

import java.util.List;
import java.util.regex.Pattern;

public class UserValidate {

    // Phương thức để kiểm tra tên đăng nhập có tồn tại hay không
    public static boolean isUsernameUnique(String username) {
        List<User> userList=IOFile.readFromFile(IOFile.USERS_PATH);
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return false;
            }
        }
        return true; // Tên đăng nhập là duy nhất
    }

    // Phương thức để kiểm tra mật khẩu có đủ 6 kí tự hay không
    public static boolean isPasswordValid(String password) {
        return Pattern.matches("^[a-zA-Z0-9]{6,}$",password);
    }

    // Phương thức để kiểm tra role có hợp lệ không
    public static boolean isRoleValid(int role) {
        // Nếu role là "1" hoặc "2", trả về true
        return role==1 || role==2;
    }
    public static boolean validateEmail(String email) {
        // Sử dụng biểu thức chính quy để kiểm tra định dạng email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }
    public static boolean validatePhone(String phone) {
        // Thực hiện kiểm tra số điện thoại, ví dụ: chỉ chấp nhận số và có độ dài từ 10 đến 15 ký tự
        return phone.matches("\\d{10,15}");
    }

}

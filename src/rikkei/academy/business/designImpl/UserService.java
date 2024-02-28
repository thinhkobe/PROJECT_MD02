package rikkei.academy.business.designImpl;

import rikkei.academy.business.design.IUserService;
import rikkei.academy.business.model.Message;
import rikkei.academy.business.model.User;
import rikkei.academy.business.model.UserRoles;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;
import rikkei.academy.business.until.validation.UserValidate;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    public static List<User> userList;

    public UserService() {
        //đọc từ file
        userList = IOFile.readFromFile(IOFile.USERS_PATH);
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public User findById(Integer id) {
        return (User) userList.stream().filter(e -> e.getUserId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(User user) {
        if (findById(user.getUserId()) != null) {
            //cập nhật
            userList.set(userList.indexOf(findById(user.getUserId())), user);
        } else {
            //thêm mới
            user.setUserId(getNewId());
            userList.add(user);

        }
        //luu lai
        IOFile.writeToFile(IOFile.USERS_PATH, userList);
    }
    public int getNewId(){
        int idMax=0;
        for (User user : userList) {
            if (user.getUserId()>idMax){
                idMax= user.getUserId();
            }
        }
        idMax+=1;
        return idMax;
    }

    @Override
    public void deleteById(Integer id) {
        userList.remove(findById(id));
        //luu lai
        IOFile.writeToFile(IOFile.USERS_PATH, userList);

    }


    // Phương thức để đăng ký người dùng
    @Override
    public void registerUser(User newUser) {

        // Thêm người dùng mới vào danh sách
        newUser.setUserId(getNewId());
        userList.add(newUser);
        System.out.println("đăng kí thành công");
        // Lưu lại danh sách người dùng vào tệp tin
        IOFile.writeToFile(IOFile.USERS_PATH, userList);
    }

    // Phương thức để kiểm tra đăng nhập người dùng
    public static User loginUser(String username, String password) {
        // Đảm bảo userList được khởi tạo
        if (userList == null) {
            System.err.println("Danh sách người dùng không tồn tại.");
            return null;
        }
        // Kiểm tra đăng nhập
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Đăng nhập thành công");
                return user;
            }
        }

        System.err.println("Tên đăng nhập hoặc mật khẩu không chính xác.");
        return null;
    }

    public static void upDateInfo(){
        int idUserLogin=IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUserId();
        for (User user : userList) {
            if (user.getUserId()==idUserLogin){
                user.inputData(false);
            }
        }
    }
    public void updateUser(int userId) {
        // Lấy danh sách người dùng từ file hoặc nơi lưu trữ dữ liệu
        List<User> userList = IOFile.readFromFile(IOFile.USERS_PATH);

        // Kiểm tra xem danh sách người dùng có rỗng không
        if (userList.isEmpty()) {
            System.out.println("Danh sách người dùng rỗng. Không thể cập nhật.");
            return;
        }

        // Tìm người dùng cần cập nhật
        Optional<User> userToUpdate = userList.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst();

        // Nếu tìm thấy người dùng, thực hiện cập nhật thông tin
        userToUpdate.ifPresent(user -> {
            user.inputData(false);

            System.out.println("bạn muốn thay đổi Email của mình ?");
            boolean changeEmail=InputMethods.getBoolean();
            if(changeEmail){
                // Nhập và kiểm tra email mới
                System.out.println("Nhập email mới:");
                while (true) {
                    String newEmail = InputMethods.getString();
                    if (UserValidate.validateEmail(newEmail)) {
                        user.setEmail(newEmail);
                        break;
                    } else {
                        System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
                    }
                }
            }

            System.out.println("bạn muốn thay đổi số điện thoại không ");
            boolean changePhone=InputMethods.getBoolean();
            if (changePhone){
            // Nhập và kiểm tra số điện thoại mới
            System.out.println("Nhập số điện thoại mới:");
            while (true) {
                String newPhone = InputMethods.getString();
                if (UserValidate.validatePhone(newPhone)) {
                    user.setPhone(newPhone);
                    break;
                } else {
                    System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                }
            }}
            System.out.println("thay đổi thông tin thành công");
            // Lưu lại danh sách người dùng sau khi cập nhật
            IOFile.writeToFile(IOFile.USERS_PATH, userList);
        });
    }

    public static void getPW(){
        List<User> users=IOFile.readFromFile(IOFile.USERS_PATH);

        System.out.println("nhập thông tin tài khoản :");
        System.out.println("nhập tên đăng nhập :");
        String userName=InputMethods.getString();
        boolean isEmailValid = false;

        while (!isEmailValid) {
            System.out.println("Nhập email đăng ký: ");
            String userEmail = InputMethods.getString();

            isEmailValid = UserValidate.validateEmail(userEmail);

            if (!isEmailValid) {
                System.err.println("Nhập sai định dạng email");
            } else {
                boolean isEmailRegistered = false;
                for (User user : users) {
                    if (user.getEmail() != null && user.getEmail().equals(userEmail)) {
                        isEmailRegistered = true;
                        break;
                    }
                }
                if (isEmailRegistered) {
                } else {
                    break;
                }
            }
        }


        System.out.println("số điện thoại liên hệ ");
        String phone = InputMethods.getString();
        Message message = new Message();
        message.setContent("hỗ trợ lấy lại mật khẩu");
        message.setTitle("tên đăng nhập người dùng " + userName + "số điện thoại liên hệ :" + phone);
        message.setCreatAt(LocalDate.now());
        User userLogin = IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        message.setSender(userLogin);
        for (User user : users) {
            if (user.getRole() == UserRoles.ADMIN) {
                message.setReceiver(user);
            }
        }

        MessageService.messageList.add(message);
        MessageService.save();
        System.out.println("đã nhận thông tin chờ phản hồi từ admin");

    }





}



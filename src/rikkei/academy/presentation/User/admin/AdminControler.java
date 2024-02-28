package rikkei.academy.presentation.User.admin;

import rikkei.academy.business.designImpl.AdminService;
import rikkei.academy.business.designImpl.ExamService;
import rikkei.academy.business.designImpl.UserService;
import rikkei.academy.business.model.User;
import rikkei.academy.business.model.UserRoles;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;
import rikkei.academy.presentation.User.MessageControler;

public class AdminControler {
    static UserService userService=new UserService();
    // Mã màu ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String name= IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUsername();
    public static void adminMenu(User userLogin){
        while (true){
            // Xóa màn hình
            System.out.print("\u001B[H\u001B[2J");

            // In menu với màu chữ và khung khác nhau
            System.out.println(ANSI_BLUE + "╔══════════════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "   Chào mừng " + name + "                                      " + ANSI_RESET);
            System.out.println(ANSI_BLUE + "╠══════════════════════════════════════════════════════╣" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 1. Hiển thị danh sách người dùng                     ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 2. Khóa / mở người dùng                              ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 4. Thông kê danh sách người dự thi                   ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 5. Thống kê danh sách bài thi                        ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 3. Tìm kiếm thông tin người dùng                     ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 6. Thống kê điểm thi trung bình theo tháng           ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 7. Thống kê top 10 bạn có điểm thi cao nhất theo môn ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 8. Kiểm tra tin nhắn                                 ║" + ANSI_RESET);
            System.out.println(ANSI_RED + "║ 0. Đăng xuất                                         ║" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "╚══════════════════════════════════════════════════════╝" + ANSI_RESET);

            int choice= InputMethods.getInteger();
            switch (choice){
                case 1:
                    displayUsers();
                    break;
                case 2:
                    blockUnblockUser();
                    break;
                case 3:
                    seachr();
                    break;
                case 4:
                    quantity();
                    break;
                case 5:
                    examQuantity();
                    break;
                case 6:
                    AdminService.calculateAverageScoreByMonth();
                    break;
                case 7:
                    AdminService.getTopTen();
                    break;
                    case 8:
                        MessageControler.checkMessage();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Nhập sai lựa chọn");
            }
        }
    }
    public static void displayUsers(){
        userService.findAll().forEach(System.out::println);
    }
    public static void blockUnblockUser() {
        displayUsers();
        System.out.println("Nhập ID người dùng cần khóa/mở khóa: ");
        int idUser = InputMethods.getByte();

        // Kiểm tra userList có giá trị và không null
        if (userService.findAll() != null) {
            boolean isUserFound = false;
            for (User user : userService.findAll()) {
                if (user.getUserId() == idUser) {
                    isUserFound = true;
                    // Thay đổi trạng thái của người dùng
                    user.setStatus(!user.isStatus());
                    System.out.println("Trạng thái của người dùng đã được cập nhật.");

                    // Lưu lại file (giả sử đây là userList, thay đổi thành tên phù hợp)
                    IOFile.writeToFile(IOFile.USERS_PATH, userService.findAll());
                    break;
                }
            }

            if (!isUserFound) {
                System.out.println("Không tìm thấy người dùng có ID: " + idUser);
            }
        } else {
            System.out.println("Danh sách người dùng không có giá trị hoặc là null.");
        }
    }
    public static void seachr(){
        System.out.println("nhập từ khóa tìm kiếm");
        String key=InputMethods.getString();
        AdminService.searchUser(key);
    }
    public static void quantity(){
        int student=0;
        for (User user : UserService.userList) {
            if (user.getRole().equals(UserRoles.STUDENT)){
                student+=1;
            }
        }
        int teacher=0;
        for (User user : UserService.userList) {
            if (user.getRole().equals(UserRoles.TEACHER)){
                teacher+=1;
            }
        }
        System.out.println("số lượng học sinh là :"+student);
        System.out.println("số lượng giáo viên là :"+teacher);
    }
    public static void examQuantity(){
        ExamService examService=new ExamService();

        if (examService.findAll().isEmpty()){
            System.out.println("chưa có bài thi nào !");
            return;
        }
        System.out.println("số lượng bài thi là :"+ExamService.examList.size());
    }
}

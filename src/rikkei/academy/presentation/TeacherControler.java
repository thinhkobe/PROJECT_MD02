package rikkei.academy.presentation;

import rikkei.academy.business.designImpl.UserService;
import rikkei.academy.business.model.User;
import rikkei.academy.business.designImpl.TeacherService;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;
import rikkei.academy.presentation.User.MessageControler;

public class TeacherControler {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    static TeacherService teacherService = new TeacherService();

    public static void teacherMenu(User userLogin) {
        UserService service=new UserService();
        String name= IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUsername();

        while (true) {
            System.out.print("\u001B[H\u001B[2J");

            // In menu với màu chữ và khung khác nhau
            System.out.println(ANSI_BLUE + "╔════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "    Chào mừng " +name + "                      " + ANSI_RESET);
            System.out.println(ANSI_BLUE + "╠════════════════════════════════════════╣" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 1. Hiển thị danh sách đề thi đã đăng   ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 2. Thêm mới đề thi                     ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 3. Sửa đề thi                          ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 4. Xóa đề thi                          ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 5. Xem thống kê người dự thi           ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 6. Sửa thông tin cá nhân               ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 7. kiểm tra tin nhắn                   ║" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 8. nhận xét bài thi của học viên       ║" + ANSI_RESET);
            System.out.println(ANSI_RED + "║ 0. Đăng xuất                           ║" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "╚════════════════════════════════════════╝" + ANSI_RESET);

            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    teacherService.displayExamByTeacher();
                    break;
                case 2:
                    teacherService.creatExam();
                    break;
                case 3:
                    teacherService.editExam();
                    break;
                case 4:
                    teacherService.deleteExam();
                    break;
                case 5:
                    teacherService.seeStatistics();
                    break;
                case 6:
                    StudentControler.updateInfo();
                    break;
                case 7:
                    MessageControler.checkMessage();
                    break;
                case 8:
                    TeacherService.feadbackResult();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("lựa chọn không hợp lệ");
                    break;
            }
        }

    }
}

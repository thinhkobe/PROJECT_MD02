package rikkei.academy.presentation;

import rikkei.academy.business.designImpl.*;
import rikkei.academy.business.model.User;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;
import rikkei.academy.presentation.User.MessageControler;

public class StudentControler {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
  static   StudentService service=new StudentService();
   public static void studentMenu(User userLogin){
       String name=IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUsername();

       while (true) {
           // Xóa màn hình
           System.out.print("\u001B[H\u001B[2J");

           // In menu với màu chữ và khung khác nhau
           System.out.println(ANSI_BLUE + "╔════════════════════════════════════════════╗" + ANSI_RESET);
           System.out.println(ANSI_CYAN + "   Chào mừng " +name + "                          " + ANSI_RESET);
           System.out.println(ANSI_BLUE + "╠════════════════════════════════════════════╣" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ Nhập lựa chọn của bạn:                     ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 1. Danh sách đề thi                        ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 2. Hiển thị danh sách các đề thi theo mô tả║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 3. Bắt đầu thi                             ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 4. Xem thông tin cá nhân                   ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 5. Chỉnh sửa thông tin cá nhân             ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 6. Lịch sử các đề đã thi                   ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 7. Đổi mật khẩu                            ║" + ANSI_RESET);
           System.out.println(ANSI_YELLOW + "║ 8. kiểm tra tin nhắn                       ║" + ANSI_RESET);
           System.out.println(ANSI_RED + "║ 0. Đăng xuất                               ║" + ANSI_RESET);
           System.out.println(ANSI_BLUE + "╚════════════════════════════════════════════╝" + ANSI_RESET);


       int choice= InputMethods.getInteger();
           switch (choice) {
               case 1:
                   service.displayExam();
                   break;
               case 2:
                    service.searchExam();
                   break;
               case 3:
                   startExam();
                   break;
               case 4:
                   service.viewInfo();
                   break;
               case 5:
                   updateInfo();
                   break;
               case 6:
                   StudentService.displayHistory();
                   break;
               case 7:
                   StudentService.changePW();
                   break;
                   case 8:
                       MessageControler.checkMessage();
                   break;
               case 0:

                   return;
               default:
                   System.out.println("lựa chọn không hợp lệ");
                   break;
           }
       }
   }
   public static void startExam(){
       ExamService examService=new ExamService();
       service.displayExam();
       //nếu chưa có bài thi nào được thêm thì cho quay lại
       if (ExamService.examList.isEmpty()){
           return;
       }
       System.out.println("nhập id bài thi ");
       int examId=InputMethods.getByte();
       service.takeExam(examService.findById(examId));



   }
   public static void updateInfo(){
       //lấy id của userLogin
       int id= IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUserId();
       service.updateUser(id);
   }

}

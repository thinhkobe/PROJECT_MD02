package rikkei.academy.presentation.User;

import rikkei.academy.business.designImpl.UserService;
import rikkei.academy.business.model.Message;
import rikkei.academy.business.model.User;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;

import java.util.List;

public class MessageControler {
    public static UserService userService = new UserService();
    // Mã màu ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void checkMessage() {
        while (true) {
            // In khung đăng nhập với màu
            // In khung đăng nhập với màu chữ và khung khác nhau
            System.out.println(ANSI_PURPLE + "╔══════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║     Quản lí tin nhắn             ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╠══════════════════════════════════╣" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 1.hiển thị toàn bộ tin nhắn      ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╠══════════════════════════════════╣" + ANSI_RESET);

            System.out.println(ANSI_YELLOW + "║ 2. hiển thị tin nhắn chưa đọc    ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╠══════════════════════════════════╣" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "║ 3. hiển thị tin nhắn đã đọc      ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╠══════════════════════════════════╣" + ANSI_RESET);

            System.out.println(ANSI_RED + "║ 0. Quay lại menu người dùng      ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╚══════════════════════════════════╝" + ANSI_RESET);


            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showMessage();
                    break;
                case 2:
                    unReadMessage();
                    break;
                case 3:
                    seen();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("lựa chọn không hợp lệ");
                    break;
            }
        }
    }

    public static void showMessage() {
       User userLogin=IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        List<Message> list = IOFile.readFromFile(IOFile.MESSAGE_PATH);
        if (list.isEmpty()) {
            System.out.println("không có thông báo nào");
            return;
        }
        for (Message message : list) {
            if (message.getReceiver().getUserId()==userLogin.getUserId()){

                System.out.println(message);
                message.setStatus(true);
                //lưu lại
                IOFile.writeToFile(IOFile.MESSAGE_PATH,list);
            }
        }
    }

    public static void unReadMessage() {


        User userLogin=IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        List<Message> list = IOFile.readFromFile(IOFile.MESSAGE_PATH);
        if (list.isEmpty()) {
            System.out.println("không có thông báo nào");
            return;
        }
        for (Message message : list) {
            if (message.getReceiver().getUserId()==userLogin.getUserId()&& !message.isStatus()){
                System.out.println(message);
            }
        }
    }

    public static void seen() {
        User userLogin=IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        List<Message> list = IOFile.readFromFile(IOFile.MESSAGE_PATH);
        if (list.isEmpty()) {
            System.out.println("không có thông báo nào");
            return;
        }
        for (Message message : list) {
            if (message.getReceiver().getUserId()==userLogin.getUserId()&& message.isStatus()){
                System.out.println(message);
                message.setStatus(true);
                //lưu lại
                IOFile.writeToFile(IOFile.MESSAGE_PATH,list);
            }
        }
    }
}

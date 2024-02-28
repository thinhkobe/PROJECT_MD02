package rikkei.academy.business.designImpl;

import rikkei.academy.business.design.IStudenService;
import rikkei.academy.business.designImpl.UserService;
import rikkei.academy.business.model.*;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;
import rikkei.academy.presentation.run.Main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService extends UserService implements IStudenService {
    //phương thức xem kì thi
    public void displayExam() {

        ExamService examService = new ExamService();
        List<Exam> examList = examService.findAll();
        if (examList.isEmpty()) {
            System.err.println("chưa có đề thi nào được thêm");
            return;
        }
            System.out.println("<><><><><><><><><><><Danh sách đề thi><><><><><><><><><><><>");
        for (Exam exam : examList) {
            System.out.printf("id:%d |Tên:%s |Mô tả :%s |Thời gian: %d |Số câu hỏi %d |số người dự thi %d |",exam.getExamId(),exam.getName(),exam.getDescription(),exam.getDuration(),
                    exam.getQuantityQuestion(),exam.getStudents().size());
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        }
    }

    //phương thức thi
    public void takeExam(Exam exam) {
        System.out.println("Bắt đầu làm bài thi " + exam.getName());

        int totalQ = exam.getQuantityQuestion();
        int trueAnswer = 0;

        // Thời điểm bắt đầu làm bài thi
        LocalDateTime startTime = LocalDateTime.now();

        // Tạo một luồng riêng biệt để theo dõi thời gian
        Thread timerThread = new Thread(() -> {
            long remainingTime = exam.getDuration();
            while (remainingTime > 0) {
                try {
                    Thread.sleep(60000); // Chờ 1 phút (60,000 milliseconds)
                    remainingTime -= 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // In cảnh báo nếu thời gian làm bài đã vượt quá thời gian yêu cầu
            System.out.println("Chú ý: Thời gian làm bài đã vượt quá thời gian yêu cầu.");
        });

        timerThread.start();

        // Lặp qua danh sách câu hỏi và yêu cầu sinh viên nhập câu trả lời
        for (Question question : exam.getQuestions()) {
            System.out.println(question.getContent());

            // Hiển thị đáp án theo định dạng A, B, C, D
            for (int i = 0; i < 4; i++) {
                System.out.println((char) ('A' + i) + ". " + question.getOptions()[i]);
            }

            // Vòng lặp để yêu cầu người dùng nhập câu trả lời đúng kiểu dữ liệu
            String userAnswer = null;
            boolean validAnswer = false;
            while (!validAnswer) {
                System.out.print("Nhập câu trả lời (a, b, c, d): ");
                userAnswer = InputMethods.getString().toUpperCase(); // Chuyển đổi sang chữ in hoa

                // Kiểm tra và lưu câu trả lời vào ExamSession
                validAnswer = isValidUserAnswer(userAnswer);

                if (!validAnswer) {
                    System.out.println("Câu trả lời không hợp lệ. Vui lòng nhập lại.");
                }
            }

            if (userAnswer.equals(String.valueOf(question.getAnswer()))) {
                System.out.println("\u001B[32mBạn trả lời đúng\u001B[0m");
                trueAnswer += 1;
            }else{
                System.err.println("trả lời sai đáp án đúng là :"+question.getAnswer());
            }
        }


        // Thời điểm kết thúc làm bài thi
        LocalDateTime endTime = LocalDateTime.now();


        // Kết thúc bài thi
        // thêm mới kết quả
        Result result = new Result();
        double point = 0;

        if (totalQ != 0) {
            point = (double) trueAnswer / totalQ * 100; // Tính điểm theo tỷ lệ phần trăm
        }
        // Tính thời gian hoàn thành bài thi
        long timeTakenMinutes = ChronoUnit.MINUTES.between(startTime, endTime);
        System.out.println("Thời gian hoàn thành: " + timeTakenMinutes + " phút");
        System.out.println("số điểm của bạn là :"+point);
        result.setExam(exam);
        result.setScore((int) point);
        User userLoggin = IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        result.setUser(userLoggin);
        result.setTime(timeTakenMinutes);
        //ngày hoàn thành bài thi bằng thời gian hiện tại
        result.setCreatedAT(LocalDate.now().atStartOfDay());
       ResultService.saveResult(result);
        //cập nhật số lượng người đã thi bài
        for (Exam exam1 : ExamService.examList) {

            if (exam1.getExamId()==exam.getExamId()){
                exam1.setStudents(userLoggin);
            }
        }
        IOFile.writeToFile(IOFile.EXAM_PATH,ExamService.examList);

        System.out.println("bạn có muốn gửi kết quả này cho giáo viên ");
        boolean check=InputMethods.getBoolean();
        if (check){
            Message newMessage =new Message();
            newMessage.setId(getNewId());
            newMessage.setContent("kết quả bài thi học sinh "+IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUsername());

            newMessage.setTitle("thời gian làm bài :"+timeTakenMinutes+"phút |\n số điểm "+point);

            newMessage.setSender(IOFile.readDataLogin(IOFile.USERLOGIN_PATH));
            newMessage.setReceiver(exam.getAuthor());
            newMessage.setCreatAt(LocalDate.now());

            MessageService.messageList.add(newMessage);
            MessageService.save();
            System.out.println("gửi thành công chờ phản hồi");
        }



    }


    private boolean isValidUserAnswer(String answer) {
        if (answer.length() == 1) {
            char answerChar = Character.toUpperCase(answer.charAt(0));
            return answerChar >= 'A' && answerChar <= 'D';
        }
        return false;
    }

    public void  searchExam(){
        System.out.println("Nhập mô tả:");
        String searchKey = InputMethods.getString();
        //đọc từ file để lấy thông tin của bài thi
        List<Exam>examList= IOFile.readFromFile(IOFile.EXAM_PATH);
        examList.stream()
                .filter(exam -> exam.getDescription().contains(searchKey) || exam.getName().contains(searchKey))
                .forEach(exam -> System.out.println("ID:"+exam.getExamId()+" | Tên bài thi :"+exam.getName()+"  | Mô tả :"+exam.getDescription()));
    }
    public void viewInfo(){
        UserService userService=new StudentService();
        int id=IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUserId();
        System.out.println("=======THÔNG TIN CÁ NHÂN=======");
        System.out.println(userService.findById(id));
    }
    public static void displayHistory(){
        List<Result> resultList=new ArrayList<>();
        int id=IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUserId();

        for (Result result : ResultService.resultList) {
            if (result.getUser().getUserId()==id){
                resultList.add(result);
            }
        }
        if (resultList.isEmpty()){
            System.out.println("bạn chưa tham gia đề thi nào");
            return;
        }
        for (Result result : resultList) {
            System.out.println(result);
        }
    }

    public static void changePW() {
        int maxAttempts = 5;
        int attempts = 0;
        System.err.println("lưu ý nhập sai 5 lần bạn sẽ phải đăng nhập lại");
        while (attempts < maxAttempts) {
            System.out.println("Nhập mật khẩu hiện tại:");
            String currentPW = InputMethods.getString();

            if (currentPW.equals(IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getPassword())) {
                // Cho phép đổi mật khẩu
                System.out.println("Nhập mật khẩu mới:");
                String newPW2 = InputMethods.getString();
                System.out.println("Xác nhận lại mật khẩu mới:");
                String newPW1 = InputMethods.getString();

                if (newPW1.equals(newPW2)) {
                    int idUserLogin = IOFile.readDataLogin(IOFile.USERLOGIN_PATH).getUserId();
                    for (User user : userList) {
                        if (user.getUserId() == idUserLogin) {
                            user.setPassword(newPW1);
                            // Lưu lại
                            IOFile.writeToFile(IOFile.USERS_PATH, userList);
                            System.out.println("Đổi mật khẩu thành công!");
                            return;  // Kết thúc phương thức sau khi đổi mật khẩu thành công
                        }
                    }
                } else {
                    System.err.println("Nhập sai mật khẩu. Thử lại.");
                    attempts++;
                }
            } else {
                System.err.println("Nhập sai mật khẩu. Thử lại.");
                attempts++;
            }
        }

        System.out.println("Bạn đã nhập sai mật khẩu " + maxAttempts + " lần. Đang thực hiện đăng nhập lại.");
        Main.login();
    }

}


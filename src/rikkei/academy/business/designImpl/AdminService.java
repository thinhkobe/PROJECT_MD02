package rikkei.academy.business.designImpl;

import rikkei.academy.business.model.Exam;
import rikkei.academy.business.model.Result;
import rikkei.academy.business.model.User;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;

import java.util.*;
import java.util.stream.Collectors;

public class AdminService {
    public static void searchUser(String searchKey){
        for (User user : UserService.userList) {
            if (user.getUsername().contains(searchKey)){
                System.out.println(user);
            }
        }
    }

    public static void calculateAverageScoreByMonth() {
        // Lấy list kết quả từ file
        List<Result> results = IOFile.readFromFile(IOFile.RESULT_PATH);

        // Tạo danh sách để lưu trữ điểm trung bình của từng tháng
        double[] averageScores = new double[12];

        // Tạo map để lưu tổng điểm và số lượng bài thi của từng tháng
        Map<Integer, int[]> monthlyStats = new HashMap<>();

        // Tính tổng điểm và số lượng bài thi của từng tháng
        for (Result result : results) {
            int month = result.getCreatedAT().getMonthValue();
            monthlyStats.computeIfAbsent(month, k -> new int[2])[0] += result.getScore();
            monthlyStats.get(month)[1]++;
        }

        // Tính điểm trung bình của từng tháng và in kết quả
        for (int month = 1; month <= 12; month++) {
            int[] stats = monthlyStats.getOrDefault(month, new int[2]);
            int totalScores = stats[0];
            int countExams = stats[1];

            if (countExams > 0) {
                averageScores[month - 1] = (double) totalScores / countExams;
                System.out.println("Tháng " + month + ": Điểm trung bình = " + averageScores[month - 1]);
            } else {
                System.out.println("Tháng " + month + ": Không có bài thi");
            }
        }
    }

//    public static void getTopTen(){
//        ExamService examService=new ExamService();
//        System.out.println("Danh sách các bài thi");
//        for (Exam exam : ExamService.examList) {
//            System.out.println( "ID bài thi :"+exam.getExamId());
//            System.out.println(exam.getName());
//        }
//        System.out.println("nhập id của bài thi cần thống kê");
//        int examId= InputMethods.getInteger();
//
//        //lấy ra bài thi
//        Exam exam= examService.findById(examId);
//        //lấy ra danh sách kết quả của bài thi
//        List<Result> resultList=IOFile.readFromFile(IOFile.RESULT_PATH);
//
//        List<Result> resultList1=new ArrayList<>();
//
//        for (Result result : resultList) {
//            if (result.getExam().getExamId()==examId){
//                resultList1.add(result);
//            }
//        }
//
//        resultList1.stream().sorted(Comparator.comparingDouble(Result::getScore)).limit(10);
//        System.out.println("Danh sách top 10 bạn có điểm thi trong bài ["+exam.getName()+"] :");
//        for (Result result : resultList1) {
//            System.out.println("Tên học sinh :"+result.getUser().getUsername());
//            System.out.println("Điểm :"+result.getScore());
//        }
//    }
public static void getTopTen() {
    ExamService examService = new ExamService();
    System.out.println("Danh sách các bài thi");
    ExamService.examList.forEach(exam -> System.out.println("ID bài thi: " + exam.getExamId() + "\n" + exam.getName()));

    System.out.println("Nhập ID của bài thi cần thống kê");
    int examId = InputMethods.getInteger();

    // Lấy ra bài thi
    Exam exam = examService.findById(examId);

    // Lấy ra danh sách kết quả của bài thi
    List<Result> resultList = IOFile.readFromFile(IOFile.RESULT_PATH);

    // Lọc danh sách kết quả theo ID của bài thi
    List<Result> resultList1 = resultList.stream()
            .filter(result -> result.getExam().getExamId() == examId)
            .sorted(Comparator.comparingDouble(Result::getScore))
            .limit(10)
            .toList();

    System.out.println("Danh sách top 10 bạn có điểm thi trong bài [" + exam.getName() + "] :");
    resultList1.forEach(result -> System.out.println("Tên học sinh: " + result.getUser().getUsername() +
            "\nĐiểm: " + result.getScore()));
}


}

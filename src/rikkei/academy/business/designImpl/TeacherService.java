package rikkei.academy.business.designImpl;


import rikkei.academy.business.design.ITeacherService;
import rikkei.academy.business.model.Exam;
import rikkei.academy.business.model.Result;
import rikkei.academy.business.model.User;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TeacherService extends UserService implements ITeacherService {
    @Override
    public void displayExamByTeacher() {
        // Lấy thông tin người đăng nhập
        User userLogin = IOFile.readDataLogin(IOFile.USERLOGIN_PATH);

        // Lấy danh sách đề thi
        ExamService examService = new ExamService();
        List<Exam> exams = examService.findAll();

        if (exams == null || exams.isEmpty()) {
            System.out.println("Không có bài thi nào được tạo.");
            return;
        }

        System.out.println("Danh sách bài thi đã đăng:");

        int count = 1;
        for (Exam exam : exams) {
            // Kiểm tra nếu exam.getAuthor() hoặc userLogin có giá trị null
            if (exam.getAuthor() == null || userLogin == null) {
                System.out.println("danh sach trong hoacj chuaw dang nhap");
                continue;
            }

            // So sánh đối tượng User
            if (exam.getAuthor().getUserId()==userLogin.getUserId()) {
                System.out.println("ID bài thi thứ " + count + ": " + exam.getExamId());
                count += 1;
                System.out.println("Tên bài thi: " + exam.getName());
            }
        }
    }


    //thêm bài kiểm tra
    public void creatExam(){
        ExamService examService=new ExamService();
        System.out.println("nhập thông tin bài thi");
        Exam newExam=new Exam();
        newExam.creatExam(true);
        //lấy thông tin người đăng nhập
       User userLogin=IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        newExam.setAuthor(userLogin);
        examService.save(newExam);
    }

    @Override
    public void editExam() {
        displayExamByTeacher();
        System.out.println("nhập ID bài thi cần sửa");
        int editId= InputMethods.getInteger();
       //lấy danh sách bài thi từ data về
        ExamService examService=new ExamService();
        Objects.requireNonNull(examService.findAll().stream().filter(exam -> exam.getExamId() == editId)
                .findFirst().orElse(null)).creatExam(false);
        //lưu lại file
        IOFile.writeToFile(IOFile.EXAM_PATH,ExamService.examList);

    }
    public void deleteExam(){
        displayExamByTeacher();
        System.out.println("nhập ID bài thi cần xóa");
        int editId= InputMethods.getInteger();
        ExamService examService=new ExamService();
        examService.deleteById(editId);
        //lưu lại file
        IOFile.writeToFile(IOFile.EXAM_PATH,ExamService.examList);

    }
    public void seeStatistics(){
        // Lấy thông tin người đăng nhập
        User userLogin = IOFile.readDataLogin(IOFile.USERLOGIN_PATH);

        // Lấy danh sách đề thi
        ExamService examService = new ExamService();
        List<Exam> exams = examService.findAll();

        if (exams == null || exams.isEmpty()) {
            System.out.println("Không có bài thi nào được tạo.");
            return;
        }

        System.out.println("Danh sách bài thi đã đăng:");

        int count = 1;
        for (Exam exam : exams) {
            // Kiểm tra nếu exam.getAuthor() hoặc userLogin có giá trị null
            if (exam.getAuthor() == null || userLogin == null) {
                System.out.println("danh sach trong hoacj chuaw dang nhap");
                continue;
            }

            // So sánh đối tượng User
            if (exam.getAuthor().getUserId()==userLogin.getUserId()) {
                System.out.println("ID bài thi thứ " + count + ": " + exam.getExamId());
                count += 1;
                System.out.println("Tên bài thi: " + exam.getName());
                System.out.println("số thí sinh đã dự thi : "+exam.getStudents().size());
            }
        }
    }
    public static void feadbackResult(){
        User userLogin=IOFile.readDataLogin(IOFile.USERLOGIN_PATH);
        System.out.println("danh sách kết quả bài thi của học sinh");
//        List<Result> resultList=new ArrayList<>();
//        for (Result result : ResultService.resultList) {
//            if (result.getExam().getAuthor().getUserId()==userLogin.getUserId()){
//                System.out.println("id :"+result.getResultId()+"|"+"tên học sinh :"+result.getUser().getUsername());
//                resultList.add(result);
//            }
//        }
        List<Result> resultList = ResultService.resultList.stream()
                .filter(result -> result.getExam().getAuthor().getUserId() == userLogin.getUserId())
                .peek(result -> System.out.println("id: " + result.getResultId() + " | " + "tên học sinh: " + result.getUser().getUsername()))
                .toList();

        boolean isValidId = false;

        while (!isValidId) {
            System.out.println("Nhập id kết quả muốn nhận xét:");
            int idResult = InputMethods.getInteger();

            Result selectedResult = resultList.stream()
                    .filter(result -> result.getResultId() == idResult)
                    .findFirst()
                    .orElse(null);

            if (selectedResult != null) {
                MessageService.creatMessage(selectedResult.getUser());
                isValidId = true;
            } else {
                System.out.println("Không tìm thấy kết quả với id đã nhập. Vui lòng nhập lại.");
            }
        }

    }




}

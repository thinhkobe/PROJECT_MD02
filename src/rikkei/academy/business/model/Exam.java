package rikkei.academy.business.model;

import rikkei.academy.business.designImpl.ExamService;
import rikkei.academy.business.until.InputMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exam implements Serializable {
    private int examId;
    private String name;
    private String description;
    private int duration;
    private List<Question> questions=new ArrayList<>();
    private  List<User> students=new ArrayList<>();
    private  int quantityQuestion;
    private User author;

    public Exam() {
    }

    public Exam(int examId, String name, String description, int duration, List<Question> questions, List<User> students, int quantityQuestion, User author) {
        this.examId = examId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.questions = questions;
        this.students = students;
        this.quantityQuestion = quantityQuestion;
        this.author = author;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(User student) {
        this.students.add(student);
    }

    public int getQuantityQuestion() {
        return quantityQuestion;
    }

    public void setQuantityQuestion(int quantityQuestion) {
        this.quantityQuestion = quantityQuestion;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public void creatExam(boolean isAdd){
        if (isAdd){
            this.examId= ExamService.getNewId();
        }
        System.out.println("nhập tên bài thi");
        this.name= InputMethods.getString();

        System.out.println("nhập mô tả bài thi");
        this.description= InputMethods.getString();

        System.out.println("nhập thời gian làm bài");
        this.duration= InputMethods.getInteger();

        System.out.println("nhập số lượng câu hỏi");
        int number=InputMethods.getByte();
        this.quantityQuestion=number;
        for (int i = 1; i <= number; i++) {
            System.out.print("Nhập nội dung câu hỏi thứ "+i);
            Question question=new Question();
            question.inputData();
            this.questions.add(question);
        }

    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", questions=" + questions +
                ", students=" + students +
                ", quantityQuestion=" + quantityQuestion +
                ", author=" + author +
                '}';
    }
}


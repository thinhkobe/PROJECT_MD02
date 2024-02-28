package rikkei.academy.business.model;

import rikkei.academy.business.until.InputMethods;

import java.io.Serializable;

public class Question implements Serializable {
    private int questionId;
    private String content;
    private String[] options = new String[4];
    private String answer;


    public Question() {
    }

    public Question(int questionId, String content, String[] options, String answer) {
        this.questionId = questionId;
        this.content = content;
        this.options = options;
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getContent() {
        return content;
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Phương thức để người dùng nhập thông tin cho câu hỏi
    public void inputData() {

        this.content = InputMethods.getString();

        System.out.println("Nhập 4 đáp án cho câu hỏi:");
        for (int i = 0; i < 4; i++) {
            System.out.print("Đáp án " + (char) ('A' + i) + ": ");
            options[i] = InputMethods.getString();
        }

        // Nhập đáp án cho câu hỏi trắc nhiệm (dưới dạng ký tự)
        while (true) {
            System.out.print("Nhập đáp án cho câu hỏi trắc nhiệm (A, B, C, hoặc D): ");
            String answerInput = InputMethods.getString().toUpperCase();

            if (answerInput.length() == 1 && answerInput.charAt(0) >= 'A' && answerInput.charAt(0) <= 'D') {
                this.answer =answerInput;
                break;
            } else {
                System.out.println("Lỗi: Đáp án không hợp lệ. Vui lòng nhập lại.");
            }
        }


    }



    public void displayQuestion(){
        System.out.println("Câu hỏi :"+this.content);
        System.out.println("A ,"+this.options[0]);
        System.out.println("B ,"+this.options[1]);
        System.out.println("C ,"+this.options[2]);
        System.out.println("D ,"+this.options[3]);
    }
}

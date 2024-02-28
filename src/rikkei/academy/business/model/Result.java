package rikkei.academy.business.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;

public class Result implements Serializable {
    private int resultId;
    private User user;
    private Exam exam;
    private double score;
    private  long time;
    private LocalDateTime createdAT;

    public Result() {
    }

    public Result(int resultId, User user, Exam exam, double score, long time, LocalDateTime createdAT) {
        this.resultId = resultId;
        this.user = user;
        this.exam = exam;
        this.score = score;
        this.time = time;
        this.createdAT = createdAT;
    }

// Getter và setter cho các thuộc tính


    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", user=" + user.getUsername() +
                ", exam=" + exam.getName() +
                ", score=" + score +
                ", time=" + time +
                ", createdAT=" + createdAT +
                '}';
    }


}

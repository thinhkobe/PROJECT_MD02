package rikkei.academy.business.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    private int id;
    private String content,title;
    private LocalDate creatAt ;
    private User sender,receiver;
    private boolean status;

    public Message(int id, String content, String title, LocalDate creatAt, User sender) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.creatAt = creatAt;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public Message() {
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDate creatAt) {
        this.creatAt = creatAt;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String colorStart = "\u001B[";
        String colorEnd = "\u001B[0m";

        if (this.status) {
            return "Message{" +
                    " người gửi=" + colorStart + "31m" + sender.getUsername() + colorEnd +
                    ", tiêu đề='" + colorStart + "31m" + title + colorEnd + '\'' +
                    ", nội dung='" + colorStart + "31m" + content + colorEnd + '\'' +
                    ", ngày gửi=" + colorStart + "31m" + creatAt + colorEnd +
                    ", status=" + colorStart + "31m" + (status ? "đã đọc" : "chưa đọc") + colorEnd +
                    '}';
        }

        return "Message{" +
                " người gửi=" + sender.getUsername() +
                ", tiêu đề='" + content + '\'' +
                ", nội dung='" + title + '\'' +
                ", ngày gửi=" + creatAt +
                ", status=" + (status ? "đã đọc" : "chưa đọc") +
                '}';
    }

}

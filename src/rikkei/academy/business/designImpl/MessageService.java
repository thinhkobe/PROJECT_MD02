package rikkei.academy.business.designImpl;

import rikkei.academy.business.model.Message;
import rikkei.academy.business.model.User;
import rikkei.academy.business.until.IOFile;
import rikkei.academy.business.until.InputMethods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    public static List<Message> messageList=new ArrayList<>();
    public  MessageService(){
        messageList= IOFile.readFromFile(IOFile.MESSAGE_PATH);
    }
    public static void save(){
        IOFile.writeToFile(IOFile.MESSAGE_PATH,messageList);
    }
    public static void creatMessage(User receiver){
        Message newMessage =new Message();
        newMessage.setId(getNewId());

        System.out.println("nhập tiêu đề ");
        newMessage.setContent(InputMethods.getString());

        System.out.println("nhập nội dung ");
        newMessage.setTitle(InputMethods.getString());

        newMessage.setSender(IOFile.readDataLogin(IOFile.USERLOGIN_PATH));
        newMessage.setReceiver(receiver);
        newMessage.setCreatAt(LocalDate.now());

        messageList.add(newMessage);
        save();


    }
    public static int getNewId(){
        int idMax=0;
        for (Message message : messageList) {
            if (message.getId()>idMax){
                idMax= message.getId();
            }
        }
        return idMax+1;
    }
}

package rikkei.academy.business.until;

import rikkei.academy.business.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    public static final String USERS_PATH = "src/rikkei/academy/business/data/users.txt";
    public static final String USERLOGIN_PATH = "src/rikkei/academy/business/data/userLogin.txt";
    public static final String EXAM_PATH = "src/rikkei/academy/business/data/Exam.txt";
    public static final String RESULT_PATH = "src/rikkei/academy/business/data/rerults.txt";
    public static final String MESSAGE_PATH = "src/rikkei/academy/business/data/message.txt";
    public static <T> void  writeToFile(String path, List<T> list){
        File file = new File(path);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static <T> void  writeToFile(String path, T obj){
        File file = new File(path);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static <T> List<T> readFromFile(String path){
        File file = new File(path);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<T> list = new ArrayList<>();
        try{
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();
            ois.read();
            ois.close();
        }catch (EOFException | FileNotFoundException e){

        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }

    public static  User readDataLogin(String path) {
        File file = new File(path);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        User object = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            object = (User) ois.readObject();
            ois.close();
        } catch (EOFException | FileNotFoundException e) {
            // Xử lý các ngoại lệ nếu cần
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
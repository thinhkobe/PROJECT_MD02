package rikkei.academy.business.designImpl;

import rikkei.academy.business.design.IExamService;
import rikkei.academy.business.model.Exam;
import rikkei.academy.business.until.IOFile;

import java.util.ArrayList;
import java.util.List;

public class ExamService implements IExamService {
    public static List<Exam> examList=new ArrayList<>();
    public ExamService(){
        examList=IOFile.readFromFile(IOFile.EXAM_PATH);
    }
    @Override
    public List<Exam> findAll() {
        return examList;
    }

    @Override
    public  Exam findById(Integer id) {
        return examList.stream().filter(e->e.getExamId()==id).findFirst().orElse(null);
    }

    @Override
    public void save(Exam exam) {
        examList.add(exam);
        IOFile.writeToFile(IOFile.EXAM_PATH,examList);
    }

    @Override
    public void deleteById(Integer id) {
        examList.remove(findById(id));
        IOFile.writeToFile(IOFile.EXAM_PATH,examList);

    }
    public static int getNewId(){
        int idMax=0;
        for (Exam exam : examList) {
           int id=exam.getExamId();
           if (idMax<id){
               idMax=id;
           }
        }
        return idMax+=1;
    }
}

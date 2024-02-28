package rikkei.academy.business.designImpl;

import rikkei.academy.business.model.Exam;
import rikkei.academy.business.model.Result;
import rikkei.academy.business.until.IOFile;

import java.util.ArrayList;
import java.util.List;

public class ResultService  {
    public static List<Result> resultList = new ArrayList<>();

    public static void saveResult(Result result) {
        resultList=IOFile.readFromFile(IOFile.RESULT_PATH);

        resultList.add(result);
        result.setResultId(getNewId());
        //lưu lại kết quả
        IOFile.writeToFile(IOFile.RESULT_PATH, resultList);
    }

    public ResultService() {
        resultList = IOFile.readFromFile(IOFile.RESULT_PATH);
    }

    public List<Result> findAll() {
        return resultList;
    }

    public static int getNewId() {
        int idMax = 0;
        for (Result result : resultList) {
            int id = result.getResultId();
            if (idMax < id) {
                idMax = id;
            }
        }
        return idMax += 1;
    }

}

package rikkei.academy.business.design;

import java.util.List;

public interface IGenericServive <T,E>{
    List<T> findAll();
    T findById(E id);
    void save(T t);
    void deleteById(E id);
}

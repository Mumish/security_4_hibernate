package by.it.service;

import java.util.List;

public interface DataService<T> {

    T findById(long id);

    List<T> getAll();

    void persist(T t);

    void saveOrUpdate(T t);

    void delete(T t);

}

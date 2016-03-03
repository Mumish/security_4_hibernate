package by.it.service;

public interface DataService<T> {

    T findById(long id);

    void persist(T t);

    void delete(T t);

}

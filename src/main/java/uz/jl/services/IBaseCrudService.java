package uz.jl.services;

import uz.jl.models.BaseEntity;

import java.util.List;

/**
 * @author Elmurodov Javohir, Tue 12:22 PM. 12/7/2021
 */
public interface IBaseCrudService<E extends BaseEntity> {
    void create(E e);

    void delete(String id);

    E get(String id);

    List<E> getAll();

    void update(String id, E e);
}

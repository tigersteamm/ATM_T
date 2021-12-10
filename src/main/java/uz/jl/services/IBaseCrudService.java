package uz.jl.services;

import uz.jl.models.BaseEntity;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;

import java.util.List;

/**
 * @author Elmurodov Javohir, Tue 12:22 PM. 12/7/2021
 */
public interface IBaseCrudService<E extends BaseEntity> {
    void create(E e);

    ResponseEntity<String> create(String userName, String password);

    ResponseEntity<String> delete(E e);

    Branch get(String id);

    List<E> getAll();

    void update(String id, E e);
}

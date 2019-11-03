package integration;

import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

public interface IDAO<PK, E> {
    E create(E entity) throws DuplicateKeyException;
    E findById(PK id) throws KeyNotFoundException;
    void update(E entity) throws KeyNotFoundException;
    void deleteById(PK id) throws KeyNotFoundException;
}

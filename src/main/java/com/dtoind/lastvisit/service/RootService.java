package com.dtoind.lastvisit.service;

import java.util.Optional;

/**
 * The RootService interface provides basic CRUD operations for a generic entity type T.
 *
 * @param <T> The type of entity this service handles.
 */
public interface RootService<T> {

    /**
     * Saves the given entity in the data store.
     *
     * @param entity The entity to be saved.
     * @return true if the entity was successfully saved, false otherwise.
     */
    boolean save(T entity);

    /**
     * Deletes the given entity from the data store.
     *
     * @param entity The entity to be deleted.
     */
    void delete(T entity);

    /**
     * Retrieves an entity from the data store by its ID.
     *
     * @param id The ID of the entity to be retrieved.
     * @return An Optional containing the entity if found, or an empty Optional if not found.
     */
    Optional<T> findById(int id);
    
}

package com.dtoind.lastvisit.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.dtoind.lastvisit.model.BaseModel;

/**
 * A generic service class that provides CRUD operations for entities extending BaseModel.
 *
 * @param <T> the type of the entity, which must extend BaseModel
 */
@Service
public class BaseService<T extends BaseModel> implements RootService<T> {

    private static final Logger logger = LogManager.getLogger(BaseService.class);
    private final JpaRepository<T, Integer> repo;

    /**
     * Constructs a new BaseService with the specified JpaRepository.
     *
     * @param repo the JpaRepository used for data access
     */
    public BaseService(JpaRepository<T, Integer> repo) {
        this.repo = repo;
    }

    /**
     * Saves the given entity to the repository.
     *
     * @param entity the entity to be saved
     * @return {@code true} if the entity is successfully saved, {@code false} otherwise
     * @throws IllegalArgumentException if the entity is null
     */
    public boolean save(T entity) {
        if (entity == null) {
            logger.warn("Attempt to save a null entity");
            return false;
        }

        // check to make sure not saving any categories or links w/o names
        if (entity.getName() != null || !entity.getName().isEmpty()) {
            entity.setDateTime(LocalDateTime.now());
            try {
                repo.save(entity);
                logger.info("Saved entity: {}", entity);
                return true;
            } catch (Exception e) {
                logger.error("Error saving entity: {}", entity, e);
                return false;
            }
        } else {
            logger.warn("Attempt to save entity with no name");
            return false;
        }
    }

    /**
     * Deletes the given entity from the repository.
     *
     * @param entity the entity to be deleted
     * @throws IllegalArgumentException if the entity with the given ID is not found
     */
    public void delete(T entity) {
        if (entity == null) {
            logger.warn("Attempt to delete a null entity");
            return;
        }

        if (repo.existsById(entity.getId())) {
            try {
                logger.info("Deleting entity: {}", entity);
                repo.delete(entity);
            } catch (Exception e) {
                logger.error("Error deleting entity: {}", entity, e);
            }
        } else {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " not found");
        }
    }

    /**
     * Retrieves an entity from the repository by its unique identifier.
     *
     * @param id the unique identifier of the entity to retrieve
     * @return an {@code Optional} containing the entity if found, otherwise an empty {@code Optional}
     */
    public Optional<T> findById(int id) {
        return repo.findById(id);
    }

}


package com.dtoind.lastvisit.repository;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.CategoryImpl;

/**
 * The primary repository for Category entities.
 * Extends {@link JpaRepository} for basic CRUD operations and provides additional methods for custom queries.
 */
@Primary
@Repository
public interface CategoryRepository extends JpaRepository<CategoryImpl, Integer> {

  /**
   * Finds a Category entity by its name.
   *
   * @param name the name of the Category to search for
   * @return an Optional containing the Category entity if found, or an empty Optional otherwise
   */
  Optional<Category> findByName(String name);

}

package com.dtoind.lastvisit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.Link;
import com.dtoind.lastvisit.model.LinkImpl;

/**
 * Repository interface for managing {@link Link} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and provides additional methods for custom queries.
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkImpl, Integer> {

  /**
   * Finds a {@link Link} entity by its URL.
   *
   * @param url the URL of the link
   * @return an {@link Optional} containing the link if found, or an empty {@link Optional} if not found
   */
  Optional<Link> findByUrl(String url);

  /**
   * Finds a list of {@link Link} entities associated with a given {@link Category}.
   *
   * @param category the category to search for
   * @return a list of links associated with the specified category
   */
  List<Link> findByCategoryImpl(Category category);

  /**
   * Checks if a {@link Link} entity with the given URL exists.
   *
   * @param url the URL to check
   * @return true if a link with the specified URL exists, false otherwise
   */
  boolean existsByUrl(String url);

}

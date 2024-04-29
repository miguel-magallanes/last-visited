package com.dtoind.lastvisit.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.dtoind.lastvisit.exception.CategoryNotFoundException;
import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.CategoryImpl;
import com.dtoind.lastvisit.repository.CategoryRepository;

/**
 * Service class for managing categories.
 */
@Service
public class CategoryService extends BaseService<CategoryImpl> {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    private final CategoryRepository catRepo;

    /**
     * Constructs a new CategoryService with the specified CategoryRepository.
     *
     * @param catRepo the CategoryRepository used for data access
     */
    public CategoryService(CategoryRepository catRepo) {
        super(catRepo);
        this.catRepo = catRepo;
    }

    /**
     * Creates a new category with the specified name.
     *
     * @param category the name of the category to create
     * @return the newly created category
     */
    public Category createCategory(String category) {
        return new CategoryImpl(category);
    }

    /**
     * Saves the given category.
     *
     * @param category the category to be saved
     * @return true if the category was saved successfully, false otherwise
     * @throws IllegalArgumentException if the category is null or already exists
     */
    public boolean save(CategoryImpl category) {
        if (category == null) {
            logger.error("Attempt to save a null category");
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (!catRepo.exists(Example.of(category))) {
            return super.save(category);
        } else {
            logger.warn("Attempt to save an existing category: {}", category.getName());
            throw new IllegalArgumentException("Category already exists");
        }
    }

    /**
     * Saves a category.
     *
     * @param category the category to be saved
     * @return true if the category is saved successfully, false otherwise
     */
    public boolean save(Category category) {
        return save((CategoryImpl) category);
    }

    /**
     * Deletes the specified category.
     *
     * @param category the category to be deleted
     */
    public void delete(Category category) {
        super.delete((CategoryImpl) category);
    }

    /**
     * Checks if a category with the specified name exists.
     *
     * @param name the name of the category to check for existence
     * @return {@code true} if a category with the specified name exists, {@code false} otherwise
     */
    public boolean categoryExists(String name) {
        return findByName(name).isPresent();
    }

    /**
     * Finds a category by its name.
     *
     * @param catName the name of the category to find
     * @return an {@code Optional} containing the category with the specified name if found, otherwise an empty {@code Optional}
     */
    public Optional<Category> findByName(String catName) {
        return catRepo.findByName(catName);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param catName the name of the category to retrieve
     * @return the category with the specified name
     * @throws CategoryNotFoundException if no category with the specified name is found
     */
    public Category findOneCategory(String catName) {
        return findByName(catName)
                .orElseThrow(() -> {
                    logger.warn("Category not found: {}", catName);
                    return new CategoryNotFoundException("Category not found: " + catName);
                });
    }

    /**
     * Retrieves all categories from the repository.
     *
     * @return a list containing all categories
     */
    public List<Category> findAllCategories() {
        return catRepo.findAll().stream()
                .map(c -> (Category) c)
                .toList();
    }

    /**
     * Returns an existing category or creates and saves a new one if it doesn't exist.
     *
     * @param catName the name of the category
     * @return the existing or newly created category
     */
    public Category returnCategory(String catName) {
        if (!categoryExists(catName)) {
            Category category = createCategory(catName);
            save(category);
            return category;
        } else {
            return findOneCategory(catName);
        }
    }

}

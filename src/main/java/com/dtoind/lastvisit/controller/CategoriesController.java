package com.dtoind.lastvisit.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtoind.lastvisit.exception.CategoryNotFoundException;
import com.dtoind.lastvisit.exception.DeletionFailedException;
import com.dtoind.lastvisit.exception.LinkAlreadyExistsException;
import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.Link;
import com.dtoind.lastvisit.service.CategoryService;
import com.dtoind.lastvisit.service.LinkService;

import static com.dtoind.lastvisit.util.JSONify.toJson;

/**
 * Controller class for managing categories and associated links.
 *
 * This controller provides endpoints for creating links and associating them with categories,
 * deleting categories and their associated links, and retrieving all available categories.
 */
@RestController
public class CategoriesController {

    private static final Logger logger = LogManager.getLogger(CategoriesController.class);

    private final LinkService linkService;
    private final CategoryService catService;

    /**
     * Constructs a new CategoriesController with the specified LinkService and CategoryService.
     *
     * @param linkService the LinkService used for managing links
     * @param catService the CategoryService used for managing categories
     */
    CategoriesController(LinkService linkService, CategoryService catService) {
        this.linkService = linkService;
        this.catService = catService;
    }

    /**
     * Endpoint to create a link and associate it with a category.
     *
     * @param requestBody A map containing the request body parameters:
     *                    - "url": The URL of the link to be created.
     *                    - "link": The name of the link.
     *                    - "category": The name of the category to associate the link with.
     * @return A ResponseEntity containing a JSON string with the category name, link name, URL,
     *         formatted creation date/time, and link ID on successful link creation and association.
     *         If there are any validation errors or exceptions, an appropriate error response is returned.
     */
    @PostMapping(value = "/create-link-and-cat")
    public ResponseEntity<String> createLinkAndAssociateWithCategory(@RequestBody Map<String, String> requestBody) {
        String url = requestBody.get("url");
        String linkName = requestBody.get("link");
        String catName = requestBody.get("category");
        logger.info("Entering createLinkAndAssociateWithCategory(), url: {}, link: {}," +
                        " category: {}", url, linkName, catName);

        ResponseEntity<String> validationResponse = validateRequestBody(url, catName, linkName);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            Category cat = catService.returnCategory(catName);
            logger.info("Category retrieved successfully: {}", cat.getName());

            Link link = linkService.createLink(linkName, url, cat);
            linkService.save(link);
            logger.info("Link saved successfully: {} {}", linkName, url);

            String[] catLink = new String[]
                    {catName, linkName, url, link.getFormattedDateTime(), link.getId() + ""};

            return ResponseEntity.ok(toJson(catLink));
        } catch (LinkAlreadyExistsException ex) {
            logger.error("Failed to create and save link {} with category {}.  Exception: {}",
                    url, catName, ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Failed to create and save link {} with category {}.  Exception: {}",
                    url, catName, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    private ResponseEntity<String> validateRequestBody(String url, String catName, String linkName) {
        if (url == null || url.isBlank()) {
            logger.warn("URL is missing or empty in the request body");
            return ResponseEntity.badRequest().body("URL is missing or empty");
        }

        if (linkName == null || linkName.isBlank()) {
            logger.warn("Link name is missing or empty in the request body");
            return ResponseEntity.badRequest().body("Link name is missing or empty");
        }

        if (catName == null || catName.isBlank()) {
            logger.warn("Category name is missing or empty in the request body");
            return ResponseEntity.badRequest().body("Category name is missing or empty");
        }

        if (linkService.urlExist(url)) {
            logger.info("URL already exists: {}", url);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("URL already exists");
        }

        return null;
    }

    /**
     * Endpoint to delete a category and its associated links.
     *
     * @param requestBody A map containing the request body parameters:
     *                    - "category": The name of the category to be deleted.
     * @return A ResponseEntity containing a success message if the category and its associated links are
     *         deleted successfully.
     *         If the category is not found, it returns a NOT_FOUND response with the exception message.
     *         If any other exception occurs during the deletion process, it returns an INTERNAL_SERVER_ERROR
     *         response with the exception details.
     */
    @DeleteMapping("/delete-cat")
    public ResponseEntity<?> deleteCategoryAndLinks(@RequestBody Map<String, String> requestBody) {
        String catName = requestBody.get("category");
        logger.info("Entering deleteCatAndLinks(), catName: {}", catName);

        try {
            logger.info("Deleting category with name: {}", catName);
            Category catToDelete = catService.findOneCategory(catName);
            linkService.deleteAllLinks(catToDelete);
            catService.delete(catToDelete);
            logger.info("Category with name '{}' and associated links deleted successfully", catName);
            Map<String, Boolean> response = Collections.singletonMap("success", true);
            return ResponseEntity.ok(response);
        } catch (CategoryNotFoundException e) {
            logger.warn("Category with name '{}' not found", catName, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DeletionFailedException e) {
            logger.error("Failed to delete category with name '{}' and associated links.", catName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Endpoint to retrieve all available categories.
     *
     * @return A ResponseEntity containing a list of all categories if the operation is successful.
     *         If an exception occurs, it returns an INTERNAL_SERVER_ERROR response with the exception message.
     */
    @GetMapping("cats")
    public ResponseEntity<?> getCategories() {
        logger.info("Entering getCategories()");

        try {
            List<Category> cats = catService.findAllCategories();
            logger.info("Returning {} categories", cats.size());
            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            logger.error("Error retrieving categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
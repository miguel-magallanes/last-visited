package com.dtoind.lastvisit.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dtoind.lastvisit.exception.LinkNotFoundException;
import com.dtoind.lastvisit.model.Link;
import com.dtoind.lastvisit.service.LinkService;

import static com.dtoind.lastvisit.util.JSONify.toJson;

/**
 * Controller class for managing links.
 *
 * This controller provides endpoints for updating the number of visits for a link,
 * deleting links by ID, and retrieving all links.
 */
@RestController
public class LinksController {

    private static final Logger logger = LogManager.getLogger(LinksController.class);

    private final LinkService linkService;

    LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * Endpoint to update the number of visits for a given link.
     *
     * @param jsonURL a map containing the URL of the link to be updated.
     * @return a ResponseEntity with the appropriate HTTP status and response body
     *         <ul>
     *             <li>HTTP 200 OK with an empty response body if the link is updated successfully</li>
     *             <li>HTTP 404 Not Found with an empty response body if the link is not found</li>
     *             <li>HTTP 500 Internal Server Error with the error message if an unexpected
     *             error occurs during the method execution</li>
     *         </ul>
     */
    @PostMapping("/update-link")
    public ResponseEntity<String> updateLink(@RequestBody Map<String, String> jsonURL) {
        String url = jsonURL.get("url");
        logger.info("Entering updateLink(), url: {}", url);

        try {
            Optional<Link> link = linkService.findByUrl(url);

            if (link.isPresent()) {
                Link linkFromRepo = link.get();
                linkFromRepo.incNumVisits();
                linkService.update(linkFromRepo);
                logger.info("Link updated successfully");
                return ResponseEntity.ok().build();
            } else {
                logger.warn("Link not found: {}", url);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    /**
     * Endpoint to delete a link by its ID.
     *
     * @param id the ID of the link to be deleted
     * @return a ResponseEntity with the appropriate HTTP status and response body
     *         <ul>
     *             <li>HTTP 200 OK with a JSON response body containing a "success" key
     *             with a boolean value of true if the link is deleted successfully</li>
     *             <li>HTTP 404 Not Found with the error message if the link with the
     *             given ID is not found</li>
     *             <li>HTTP 500 Internal Server Error with the error message if an unexpected
     *             error occurs during the method execution</li>
     *         </ul>
     */
    @DeleteMapping("/delete-link")
    public ResponseEntity<?> deleteLink(@RequestParam int id) {
        logger.info("Entering deleteLink(), id: {}", id);

        try {
            linkService.deleteLink(id);
            logger.info("Link deleted successfully");
            Map<String, Boolean> response = Collections.singletonMap("success", true);
            return ResponseEntity.ok(toJson(response));
        } catch (LinkNotFoundException e) {
            logger.warn("Link not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    /**
     * Endpoint that retrieves all links as a JSON array.
     *
     * @return a ResponseEntity with the appropriate HTTP status and response body
     *         <ul>
     *             <li>HTTP 200 OK with a JSON array of links as the response body
     *             if the links are retrieved successfully</li>
     *             <li>HTTP 500 Internal Server Error with the error message if an
     *             unexpected error occurs during the method execution</li>
     *         </ul>
     */
    @GetMapping("links")
    public ResponseEntity<String> getLinksAsJson() {
        logger.info("Entering getLinksAsJson()");

        try {
            List<String> links = linkService.findAllLinksAsJson();
            logger.debug("Returning {} links", links.size());
            return ResponseEntity.ok(toJson(links));
        } catch (Exception e) {
            logger.error("Error retrieving links", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

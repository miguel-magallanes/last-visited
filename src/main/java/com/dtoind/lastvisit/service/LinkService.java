package com.dtoind.lastvisit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import com.dtoind.lastvisit.exception.LinkAlreadyExistsException;
import com.dtoind.lastvisit.exception.LinkNotFoundException;
import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.Link;
import com.dtoind.lastvisit.model.LinkImpl;
import com.dtoind.lastvisit.repository.LinkRepository;
import com.dtoind.lastvisit.util.JSONify;

/**
 * Service class for managing Link entities.
 */
@Service
public class LinkService extends BaseService<LinkImpl> {

    private static final Logger logger = LogManager.getLogger(LinkService.class);

    private final LinkRepository linkRepo;

    /**
     * Constructs a new LinkService with the specified LinkRepository.
     *
     * @param linkRepo the LinkRepository used for data access
     */
    public LinkService(LinkRepository linkRepo) {
        super(linkRepo);
        this.linkRepo = linkRepo;
    }

    /**
     * Creates a new Link object with the given name, url, and category.
     *
     * @param  name the name of the link
     * @param  url the URL of the link
     * @param  category the category of the link
     * @return a new Link object
     */
    public Link createLink(String name, String url, Category category) {
        return new LinkImpl(name, url, category);
    }

    /**
     * Saves a link.
     *
     * @param  link	the link to be saved
     * @return true if the link was successfully saved, false otherwise
     */
    public boolean save(Link link) {
        return save((LinkImpl) link);
    }

    /**
     * Saves the given LinkImpl instance.
     *
     * @param link the LinkImpl instance to be saved
     * @return true if the LinkImpl was saved successfully, false otherwise
     * @throws IllegalArgumentException if the link is null
     * @throws LinkAlreadyExistsException if a link with the same URL already exists
     */
    public boolean save(LinkImpl link) {
        if (link == null) {
            logger.error("Attempt to save a null link");
            throw new IllegalArgumentException("Link cannot be null");
        }

        if (!linkRepo.existsByUrl(link.getUrl())) {
            return update(link);
        } else {
            throw new LinkAlreadyExistsException("Link already exists: " + link.getUrl());
        }
    }

    /**
     * Updates the specified link.
     *
     * @param link the link to be updated
     * @return {@code true} if the link is successfully updated, {@code false} otherwise
     */
    public boolean update(Link link) {
        link.setDateTime(LocalDateTime.now());
        return super.save((LinkImpl) link);
    }

    /**
     * Deletes the specified link.
     *
     * @param link the link to be deleted
     */
    public void delete(Link link) {
        super.delete((LinkImpl) link);
    }

    /**
     * Deletes the Link instance with the given ID.
     *
     * @param id the ID of the Link instance to be deleted
     * @throws LinkNotFoundException if no Link with the given ID is found
     */
    public void deleteLink(int id) {
        Optional<LinkImpl> link = super.findById(id);
        link.ifPresentOrElse(
                this::delete,
                () -> {
                    logger.error("Link with ID {} not found", id);
                    throw new LinkNotFoundException("Link with ID " + id + " not found");
                }
        );
    }

    /**
     * Deletes all links associated with the specified category.
     *
     * @param catToDelete the category whose links are to be deleted
     */
    public void deleteAllLinks(Category catToDelete) {
        List<Link> linksToDelete = findByCategory(catToDelete);
        linksToDelete.forEach(this::delete);
    }

    /**
     * Checks if a link with the specified URL exists.
     *
     * @param url the URL to check for existence
     * @return {@code true} if a link with the specified URL exists, {@code false} otherwise
     */
    public boolean urlExist(String url) {
        return findByUrl(url).isPresent();
    }

    /**
     * Finds a link by its URL.
     *
     * @param url the URL of the link to find
     * @return an {@code Optional} containing the link with the specified URL if found, otherwise an empty {@code Optional}
     */
    public Optional<Link> findByUrl(String url) {
        return linkRepo.findByUrl(url);
    }

    /**
     * Finds links associated with the specified category.
     *
     * @param category the category to find links for
     * @return a list of links associated with the specified category
     */
    public List<Link> findByCategory(Category category) {
        return linkRepo.findByCategoryImpl(category);
    }

    /**
     * Retrieves all links from the repository and converts them to JSON format.
     *
     * @return a list of strings representing the JSON format of all links
     */
    public List<String> findAllLinksAsJson() {
        return linkRepo.findAll().stream()
                .map(JSONify::toJson)
                .collect(Collectors.toList());
    }

}

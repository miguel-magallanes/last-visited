package com.dtoind.lastvisit.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * Entity class representing a Link object, which is a web link associated with a category.
 * This class implements the Link interface and provides methods for managing link properties
 * such as name, URL, date/time, visit count, and category association.
 */
@Entity
@Table(name = "LINKIMPL")
public class LinkImpl implements Link {

    private static final Logger logger = LogManager.getLogger(LinkImpl.class);

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(name = "CUST_SEQ", allocationSize = 1)
    @Column(name = "LINKIMPL_ID")
    @JsonProperty("id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "CATIMPL_ID", nullable = false)
    private CategoryImpl categoryImpl;

    private String name = "";
    private String url;
    private LocalDateTime dateTime;
    private int numVisits = 0;

    public LinkImpl() {
    }

    /**
     * Constructor for creating a new LinkImpl instance with the specified name, URL, and category.
     *
     * @param name The name of the link.
     * @param url  The URL of the link.
     * @param category The category associated with the link.
     * @throws NullPointerException     if the name, URL, or category is null.
     * @throws IllegalArgumentException if the name, URL, or category name is empty.
     */
    public LinkImpl(String name, String url, Category category) {
        if (name == null || url == null || category == null) {
            logger.warn("Attempt to create a link with a null name, url, or category");
            throw new NullPointerException("Name, URL, or Category cannot be null");
        } else if (name.isEmpty() || url.isBlank() || category.getName().isEmpty()) {
            logger.warn("Attempt to create a link with an empty name, url, or category");
            throw new IllegalArgumentException("Name, URL, or Category cannot be empty");
        }

        this.name = name;
        this.url = url;
        this.categoryImpl = (CategoryImpl) category;
        this.dateTime = LocalDateTime.now();
        logger.info("Created new link: {}", this);
    }

    /**
     * Get the ID of the object.
     *
     * @return the ID of the object
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the object.
     *
     * @return the name of the object
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the link.
     *
     * @param name The new name of the link.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    @Override
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("Attempt to set a link's name to null or empty");
            throw new IllegalArgumentException("Name cannot be null or empty");
        } else {
            this.name = name;
        }
    }

    /**
     * Retrieves the URL associated with this visit.
     *
     * @return The URL.
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL for this visit.
     *
     * @param url The URL to set.
     */
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retrieves the date and time of this visit.
     *
     * @return The date and time.
     */
    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time for this visit.
     *
     * @param dateTime The date and time to set.
     */
    @Override
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the formatted date and time of this visit.
     *
     * @return The formatted date and time.
     */
    @Override
    public String getFormattedDateTime() {
        return formatDefaultDateTime(dateTime);
    }

    /**
     * Retrieves the number of visits to this URL.
     *
     * @return The number of visits.
     */
    @Override
    public int getNumVisits() {
        return numVisits;
    }

    /**
     * Sets the number of visits to this URL.
     *
     * @param numVisits The number of visits to set.
     */
    @Override
    public void setNumVisits(int numVisits) {
        this.numVisits = numVisits;
    }

    /**
     * Retrieves the category of this visit.
     *
     * @return The category.
     */
    @Override
    public Category getCategory() {
        return categoryImpl;
    }

    /**
     * Increases the number of visits to this URL by one.
     */
    @Override
    public void incNumVisits() {
        numVisits++;
    }

    /**
     * Returns a string representation of this Link object.
     *
     * @return A string containing the ID, category, name, URL, formatted date and time,
     *         and number of visits of this Link.
     */
    @Override
    public String toString() {
        return "Link { " +
                "id=" + id +
                ", category='" + categoryImpl.getName() + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", dateTime=" + getFormattedDateTime() +
                ", numVisits=" + numVisits +
                " }";
    }

    /**
     * Compares the current object with the specified object for equality.
     * Two LinkImpl objects are considered equal if they have the same values
     * for the id, name, url, categoryImpl, and dateTime fields.
     *
     * @param obj the object to be compared for equality with this object
     * @return true if the specified object is equal to this object; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LinkImpl other = (LinkImpl) obj;
        return id == other.id && Objects.equals(name, other.name) && Objects.equals(url, other.url)
                && Objects.equals(categoryImpl, other.categoryImpl) && Objects.equals(dateTime, other.dateTime);
    }

    /**
     * Returns a hash code value for the Link object. This method computes the hash code based on the
     * ID, name, URL, category, and date and time of the Link.
     *
     * @return A hash code value for the Link object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, categoryImpl, dateTime);
    }

}

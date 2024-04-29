package com.dtoind.lastvisit.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Implementation class for the Category interface.
 * Represents a category entity in the application.
 */
@Entity
@Table(name = "CATIMPL")
public class CategoryImpl implements Category {

    private static final Logger logger = LogManager.getLogger(CategoryImpl.class);

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(name = "CUST_SEQ", allocationSize = 1)
    @Column(name = "CATIMPL_ID")
    private int id;
    private String name = "";
    private LocalDateTime dateTime;

    public CategoryImpl() {
    }

    /**
     * Parameterized constructor to create a new category with the given name.
     *
     * @param name The name of the category.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public CategoryImpl(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("Attempt to create a category with a null or empty name");
            throw new IllegalArgumentException("Name cannot be null or empty");
        } else {
            this.name = name;
            this.dateTime = LocalDateTime.now();
            logger.info("Created new category: {}", this);
        }
    }

    /**
     * Retrieves the ID of this Link.
     *
     * @return The ID of this Link.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of this Link.
     *
     * @return The name of this Link.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name The new name of the category.
     * @throws IllegalArgumentException If the provided name is null or empty.
     */
    @Override
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("Attempt to set a category's name to null or empty");
            throw new IllegalArgumentException("Name cannot be null or empty");
        } else {
            this.name = name;
        }
    }

    /**
     * Retrieves the date and time associated with this Category.
     *
     * @return The date and time of this Category.
     */
    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time for this Category.
     *
     * @param dateTime The date and time to set.
     */
    @Override
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the formatted date and time associated with this Category.
     *
     * @return The formatted date and time of this Category.
     */
    @Override
    public String getFormattedDateTime() {
        return formatDefaultDateTime(dateTime);
    }

    /**
     * Returns a string representation of this Category object.
     *
     * @return A string containing the ID, name, and formatted date and time of this Category.
     */
    @Override
    public String toString() {
        return "Category { " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + getFormattedDateTime() +
                " }";
    }

    /**
     * Compares the current object with the specified object for equality.
     * Checks if the provided object is of the same class,
     * and if so, compares their ID, name, and date and time attributes.
     *
     * @param obj The object to compare for equality.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CategoryImpl category = (CategoryImpl) obj;
        return id == category.id && Objects.equals(name, category.name)
                && Objects.equals(dateTime, category.dateTime);
    }

    /**
     * Returns a hash code value for the Category object. This method computes the hash code based on
     * the ID, name, and date and time attributes of the Category.
     *
     * @return A hash code value for this Category object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateTime);
    }

}

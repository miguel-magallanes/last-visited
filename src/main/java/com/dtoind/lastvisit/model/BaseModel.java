package com.dtoind.lastvisit.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Interface representing a base model with common attributes and methods.
 *
 * This interface defines methods for accessing and manipulating common attributes
 * such as ID, name, and date/time, as well as methods for formatting date/time values.
 */
public interface BaseModel {

    /**
     * Returns the ID associated with this object.
     *
     * @return The ID of the object.
     */
    int getId();

    /**
     * Returns the name associated with this object.
     *
     * @return The name of the object.
     */
    String getName();

    /**
     * Returns the date and time associated with this object.
     *
     * @return The date and time of the object.
     */
    LocalDateTime getDateTime();

    /**
     * Returns the formatted date and time associated with this object.
     *
     * @return The formatted date and time of the object.
     */
    String getFormattedDateTime();

    /**
     * Sets the name of this object.
     *
     * @param name The new name for the object.
     */
    void setName(String name);

    /**
     * Sets the date and time of this object.
     *
     * @param dateTime The new date and time for the object.
     */
    void setDateTime(LocalDateTime dateTime);

    /**
     * Formats a LocalDateTime using the provided DateTimeFormatter.
     *
     * @param ldt the LocalDateTime to format
     * @param dateFormat the DateTimeFormatter to use
     * @return the formatted date and time string
     * @throws IllegalArgumentException if ldt or dateFormat is null
     */
    default String formatCustomDateTime(LocalDateTime ldt, DateTimeFormatter dateFormat) {
        if (ldt == null || dateFormat == null) {
            throw new IllegalArgumentException("LocalDateTime and DateTimeFormatter cannot be null");
        }

        String formattedDate = dateFormat.format(ldt);
        formattedDate = formattedDate.replace("AM", " am").replace("PM", " pm");
        return formattedDate;
    }

    /**
     * Formats a LocalDateTime using a default pattern.
     *
     * @param ldt the LocalDateTime to format
     * @return the formatted date and time string
     */
    default String formatDefaultDateTime(LocalDateTime ldt) {
        return formatCustomDateTime(ldt, ofPattern("h:mma 'on' MM.dd.uu"));
    }

}
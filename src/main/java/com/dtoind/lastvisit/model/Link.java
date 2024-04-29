package com.dtoind.lastvisit.model;

/**
 * Represents a hyperlink with its associated properties.
 * This interface extends the {@link BaseModel} interface.
 */
public interface Link extends BaseModel {

    /**
     * Retrieves the URL of the hyperlink.
     *
     * @return The URL of the hyperlink.
     */
    String getUrl();

    /**
     * Retrieves the number of visits to the hyperlink.
     *
     * @return The number of visits to the hyperlink.
     */
    int getNumVisits();

    /**
     * Retrieves the category of the hyperlink.
     *
     * @return The category of the hyperlink.
     */
    Category getCategory();

    /**
     * Sets the URL of the hyperlink.
     *
     * @param url The new URL of the hyperlink.
     */
    void setUrl(String url);

    /**
     * Sets the number of visits to the hyperlink.
     *
     * @param numVisits The new number of visits to the hyperlink.
     */
    void setNumVisits(int numVisits);

    /**
     * Increments the number of visits to the hyperlink by 1.
     */
    void incNumVisits();
}


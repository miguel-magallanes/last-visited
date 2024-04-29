package com.dtoind.lastvisit.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class LinkImplTest {
    
    @Test // per Bard 12.31.23
    void testConstructor_ValidInput() {
        // Arrange
        String name = "Test Link";
        String url = "https://example.com";
        CategoryImpl category = new CategoryImpl("Test Category");
        
        // Act
        LinkImpl link = new LinkImpl(name, url, category);
        
        // Assert
        assertEquals(name, link.getName());
        assertEquals(url, link.getUrl());
        assertSame(category, link.getCategory()); // Ensure same object reference
        assertNotNull(link.getDateTime());

        // Assuming dateTime is set to current time, check it's within a reasonable range
        assertTrue(LocalDateTime.now().minusSeconds(1).isBefore(link.getDateTime()));
        assertTrue(LocalDateTime.now().plusSeconds(1).isAfter(link.getDateTime()));
        
        // AssertJ syntax
        assertThat(link.getDateTime()).isBefore(LocalDateTime.now().plusSeconds(1));
        assertThat(link.getDateTime()).isAfter(LocalDateTime.now().minusSeconds(1));
    }
    
    // Start - PB 12.31.23
    
    // Test for null name
    @Test
    void testConstructor_NullName() {
        assertThrows(NullPointerException.class, () -> new LinkImpl(null, "https://example.com", new CategoryImpl("Test")));
    }
    
    // Test for empty name
    @Test
    void testConstructor_EmptyName() {
        // Assuming empty name is not allowed
        assertThrows(IllegalArgumentException.class, () -> new LinkImpl("", "https://example.com", new CategoryImpl("Test")));
    }
    
    // Test for null URL
    @Test
    void testConstructor_NullUrl() {
        assertThrows(NullPointerException.class, () -> new LinkImpl("Test Link", null, new CategoryImpl("Test")));
    }
    
    // Test for empty URL
    @Test
    void testConstructor_EmptyUrl() {
        // Assuming empty URL is not allowed
        assertThrows(IllegalArgumentException.class, () -> new LinkImpl("Test Link", "", new CategoryImpl("Test")));
    }
    
    // Test for null category
//    @Test
//    void testConstructor_NullCategory() {
//        assertThrows(NullPointerException.class, () -> new LinkImpl("Test Link", "https://example.com", null));
//    }
    // using AssertJ syntax
    // Test for null category
    @Test
    void testConstructor_NullCategory() {
        assertThatThrownBy(() -> new LinkImpl("Test Link", "https://example.com", null))
            .isInstanceOf(NullPointerException.class);
    }
    // End - PB 12.31.23

    // test the toString() method
    @Test
    void testToString() {
        LinkImpl link = new LinkImpl("Test Link", "https://example.com", new CategoryImpl("Test"));
        assertThat(link.toString()).isEqualTo("Link { id=0, category='Test', name='Test Link', " +
                "url='https://example.com', dateTime=" + link.getFormattedDateTime() + ", numVisits=0 }");
    }

}
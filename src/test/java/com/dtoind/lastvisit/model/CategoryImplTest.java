package com.dtoind.lastvisit.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryImplTest {
    
    @Test
    public void testCategoryImplConstructorWithValidName() {
        String validName = "TestCategory";
        CategoryImpl category = new CategoryImpl(validName);
        
        assertThat(category.getName()).isEqualTo(validName);
        assertThat(category.getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
    }
    
    @Test
    public void testCategoryImplConstructorWithNullName() {
        assertThatThrownBy(() -> new CategoryImpl(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void testCategoryImplConstructorWithEmptyName() {
        assertThatThrownBy(() -> new CategoryImpl(""))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void testToString() {
        CategoryImpl cat = new CategoryImpl("Test Category");
        
        assertThat(cat.toString()).isEqualTo("Category { id=0, name='Test Category', " +
                                                 "dateTime=" + cat.getFormattedDateTime() + " }");
    }
}
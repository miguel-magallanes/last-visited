package com.dtoind.lastvisit.service;

import com.dtoind.lastvisit.exception.LinkAlreadyExistsException;
import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.CategoryImpl;
import com.dtoind.lastvisit.model.Link;
import com.dtoind.lastvisit.model.LinkImpl;
import com.dtoind.lastvisit.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private LinkRepository mockRepo;

    @InjectMocks
    private LinkService mockService; // calls BasicService.setRepository(mockRepo);

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testSaveItemDoesNotExist() {
        // Arrange
        LinkImpl link = new LinkImpl("javit", "https://example.com", new CategoryImpl("java"));

        // Act
        boolean result = mockService.save(link);

        // Assert
        verify(mockRepo, times(1)).save(link);
        assert result;
    }

    @Test
    void testSaveItemAlreadyExist() {
        // Arrange
        // create the link
        Category category = new CategoryImpl("java");
        Link link = new LinkImpl("javit", "https://example.com", category);
        when(mockRepo.existsByUrl(link.getUrl())).thenReturn(true);

        try {
            // Act
            System.out.println(mockService.save(link));
            fail("Should have thrown an exception");
        } catch (LinkAlreadyExistsException e) {
            // assert
            System.out.println("In saveItemAlreadyExist(), Exception: " + e.getMessage());
            assertThat(e).isInstanceOf(LinkAlreadyExistsException.class);
        }
    }
}
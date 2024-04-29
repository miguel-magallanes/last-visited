package com.dtoind.lastvisit.service;

import com.dtoind.lastvisit.model.Category;
import com.dtoind.lastvisit.model.CategoryImpl;
import com.dtoind.lastvisit.repository.CategoryRepository;
import com.dtoind.lastvisit.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository mockRepo;

    @InjectMocks
    private CategoryService catService;

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testSaveItemDoesNotExist() {
        // don't actually want to save to repo, so will mock it

        // Arrange
        CategoryImpl entity = new CategoryImpl("Test Category");
        when(mockRepo.save(entity)).thenReturn(entity);

        //Act
        boolean result = catService.save(entity);

        //Assert
        verify(mockRepo, times(1)).save(entity);
        assert result;
    }

    @Test
    void testSaveItemAlreadyExist() {
        // Arrange
        Category entityToSave = new CategoryImpl("Test Category"); // Entity to attempt saving

        // sets the mocked repository to return true when exists(entityToSave) is called
        when(mockRepo.exists(Example.of((CategoryImpl) entityToSave))).thenReturn(true);

        try {
            // Act
            boolean result = catService.save(entityToSave);
//            System.out.println("CatSrv.save()Test.result: " + result);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            verify(mockRepo, never()).save((CategoryImpl) entityToSave);
        }
    }

    @Test
    void testSaveItemAlreadyExist2() {
        // Arrange
        CategoryImpl entity = new CategoryImpl("Test Category");
        when(mockRepo.save(entity)).thenReturn(entity);

        try {
            // Act
            boolean result = catService.save(entity);
            System.out.println("cS.result: " + result);
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            verify(mockRepo, never()).save(entity);
        }
    }


}
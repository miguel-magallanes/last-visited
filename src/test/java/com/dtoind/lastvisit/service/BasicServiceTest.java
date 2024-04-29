package com.dtoind.lastvisit.service;

import com.dtoind.lastvisit.model.BaseModel;
import com.dtoind.lastvisit.model.CategoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicServiceTest {

    @Mock
    private JpaRepository<BaseModel, Integer> mockRepository;

    @InjectMocks
    private BaseService<BaseModel> baseService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
////        baseService = new BaseService<>(mockRepository);
////        baseService.setRepo(mockRepository);
//    }

    @Test
    void testSaveWithValidEntity() {
        // Arrange
        BaseModel entity = new CategoryImpl("Test Category");
        when(mockRepository.save(entity)).thenReturn(entity);

        // Act
        boolean saveResult = baseService.save(entity);

        // Assert
        verify(mockRepository, times(1)).save(entity);
        assert saveResult;
    }

//    @Test
//    void testSaveWithInvalidEntity() {
//        // Arrange
//        LinkImpl linkImp = new LinkImpl(null);
//        CategoryImpl category = new CategoryImpl("Test Category");
//        linkImp.setCat(category);
//
//        // Act
//        boolean saveResult = baseService.save(linkImp);
//
//        // Assert
//        verify(mockRepository, never()).save(any());
//        assert !saveResult;
//    }

    @Test
    void testDeleteWithValidEntity() {
        // Arrange
//        BasicService<BaseModel> mockService = Mockito.mock(BaseService.class);
        CategoryImpl category = new CategoryImpl("Test Category");
        doNothing().when(mockRepository).delete(category);
        when(mockRepository.existsById(category.getId())).thenReturn(true);

        // Act
        baseService.delete(category);

        // Assert
        verify(mockRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteWithInvalidEntity() {
        // Arrange
        BaseModel entity = new CategoryImpl("Test Category");
//        doNothing().when(mockRepository).delete(entity);

        // Act
        try {
            baseService.delete(entity);
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            verify(mockRepository, never()).delete(entity);
        }
    }

}
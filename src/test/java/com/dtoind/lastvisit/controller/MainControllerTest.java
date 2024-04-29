//package com.dtoind.lastvisit.controller;
//
//import com.dtoind.lastvisit.model.Category;
//import com.dtoind.lastvisit.model.CategoryImpl;
//import com.dtoind.lastvisit.model.Link;
//import com.dtoind.lastvisit.model.LinkImpl;
//import com.dtoind.lastvisit.repository.CategoryRepository;
//import com.dtoind.lastvisit.repository.LinkRepository;
//import com.dtoind.lastvisit.service.CategoryService;
//import com.dtoind.lastvisit.service.LinkService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//
//import java.util.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MainControllerTest {
//
//    @Mock
//    private CategoryRepository categoryRepository;
//    @Mock
//    private LinkRepository linkRepository;
//
//    @Mock
//    private CategoryService categoryService;
//
//    @Mock
//    private LinkService linkService;
//
//    @InjectMocks
//    private MainController controller;
//
//    @Test
//    void testCreateCatLink() {
//        // Arrange
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("category", "Category 1");
//        requestBody.put("link", "Link 1");
//        requestBody.put("url", "https://example.com");
//
//        // need to stub catService.createCat
//        Category expectedCat = new CategoryImpl("Category 1");
//        when(categoryService.createCat("Category 1")).thenReturn(expectedCat);
//        // need to stub linkService.createLink
//        Link expectedLink = new LinkImpl("Link 1", "https://example.com", expectedCat);
//        when(linkService.createLink("Link 1", "https://example.com", expectedCat)).thenReturn(expectedLink);
//
//        // Act
//        ResponseEntity<String> responseEntity = controller.createCatLink(requestBody);
//        String result = responseEntity.getBody();
//        System.out.println("\nIn testCreateCatLink, result: " + result);
//
//        // Assert
//        assertThat(result).contains("Category 1", "Link 1", "https://example.com");
//    }
//
//    @Test
//    public void testReturnCats() {
//        // Arrange
//        List<Category> expectedCats = new ArrayList<>();
//        expectedCats.add(new CategoryImpl("Category 1"));
//        expectedCats.add(new CategoryImpl("Category 2"));
////        when(categoryRepository.findAll()).thenReturn(expectedCats);
//        when(categoryService.findAllCategories()).thenReturn(expectedCats);
//
//        // Act
//        ResponseEntity<List<Category>> responseEntity = controller.returnCats();
//        List<Category> result = responseEntity.getBody();
//
//        // Assert
//        assertThat(result)
//                .extracting("name")
//                .containsExactlyInAnyOrder("Category 1", "Category 2");
//    }
//
//    @Test
//    void testReturnLinks() {
//        // Arrange
//        List<Link> expectedLinks = new ArrayList<>();
//        expectedLinks.add(new LinkImpl("Link 1", "https://example.com", new CategoryImpl("Category 1")));
//        expectedLinks.add(new LinkImpl("Link 2", "https://example.com", new CategoryImpl("Category 2")));
////        when(linkRepository.findAll()).thenReturn(expectedLinks);
//        when(linkService.findAllLinks()).thenReturn(expectedLinks);
//
//        // Act
//        ResponseEntity<String> responseEntity = controller.returnLinks();
//        String result = responseEntity.getBody();
//
//        System.out.println("\nIn testReturnLinks, result: " + result);
//
//        // Assert
//        assertThat(result)
//                .contains("Link 1")
//                .contains("Link 2");
//    }
//
//    @Test
//    void testUpdateLink() {
//        // Arrange
////        when(linkRepository.findByUrl("http://example.com"))
////                .thenReturn(Collections
////                        .singletonList(new LinkImpl("Link 1", "http://example.com",
////                                new CategoryImpl("Category 1"))));
//
////        LinkService mockLinkService = Mockito.mock(LinkService.class);
//
//        Link mockLink = new LinkImpl("Link 1", "http://example.com", new CategoryImpl("Category 1"));
////        when(linkRepository.findByUrl("http://example.com")).thenReturn(Collections.singletonList(mockLink));
//        when(linkService.findByUrl("http://example.com")).thenReturn(Collections.singletonList(mockLink));
//
//        // Act
//        controller.updateLink("http://example.com");
//
//        // Assert
//        verify(linkService, times(1)).update(any(LinkImpl.class));
//    }
//
//    @Test
//    void testDeleteLink() {
//        // Arrange
//        Link link = new LinkImpl("Link 1", "https://example.com", new CategoryImpl("Category 1"));
//        when(linkService.getLinkById(link.getId())).thenReturn(Optional.of(link));
////        doNothing().when(linkService).delete(link);
//
//        // Act
//        controller.deleteLink(link.getId());
//
//        // Assert
//        verify(linkService, times(1)).delete(link);
//    }
//
//    @Test
//    void testDeleteCat() {
//        // Arrange
//        // need to create a cat so then I can stub catService.findByName()
//        // & linkService.findByCategory() & lS.delete()
//        // or just stub cS.fbName & cS.delete()
//        // create cat
//        Category catToDel = new CategoryImpl("Category 1");
//        // create link
//        Link link = new LinkImpl("Link 1", "https://example.com", catToDel);
//
//        when(categoryService.findByName(catToDel.getName())).thenReturn(Collections.singletonList(catToDel));
////        doNothing().when(categoryService).delete(catToDel);
//
//        when(linkService.findByCategory(catToDel)).thenReturn(Collections.singletonList(link));
////        doNothing().when(linkService).delete(link);
//
//        // Act
//        controller.deleteCat(catToDel.getName());
//
//        // Assert
//        verify(linkService, times(1)).delete(link);
//        verify(categoryService, times(1)).delete(catToDel);
//    }
//
//}

package com.dtoind.lastvisit.util;

import static org.junit.jupiter.api.Assertions.*;

import com.dtoind.lastvisit.model.CategoryImpl;
import com.dtoind.lastvisit.model.Link;
import com.dtoind.lastvisit.model.LinkImpl;
import com.dtoind.lastvisit.util.JSONify;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONifyTest {

    @Test
    public void testToJson() throws JsonProcessingException {
        // Arrange
        CategoryImpl cat = new CategoryImpl("Technology");
        ObjectMapper mapper = new ObjectMapper();

        // Act
        String jsonString = JSONify.toJson(cat);
        JsonNode jsonNode = mapper.readTree(jsonString);

        // Assert
        assertFalse(jsonString.isEmpty());
        assertEquals(0, jsonNode.get("id").asInt());
        assertEquals("Technology", jsonNode.get("name").asText());
        assertNotNull(jsonNode.get("dateTime"));
    }

//    @Test
//    public void testToLinkJson() {
//        // Arrange
//        CategoryImpl cat = new CategoryImpl("Technology");
//        LinkImpl link = new LinkImpl("Google", "https://www.google.com", cat);
//
//        // Act
//        String jsonString = new JSONify().toLinkJson((Link) link);
//        JsonNode jsonNode = null;
//        try {
//            jsonNode = new ObjectMapper().readTree(jsonString);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        // Assert
//        assertFalse(jsonString.isEmpty());
//        assertEquals(0, jsonNode.get("id").asInt());
//        assertEquals("Technology", jsonNode.get("category").asText());
//        assertEquals("Google", jsonNode.get("name").asText());
//        assertEquals("https://www.google.com", jsonNode.get("url").asText());
//        assertNotNull(jsonNode.get("dateTime"));
//        assertEquals(0, jsonNode.get("numVisits").asInt());
//    }
}
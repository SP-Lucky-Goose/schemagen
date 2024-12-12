package com.semanticpartners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 *   
 *   @author Jeremy Debattista
 *   file ShaclParserTest
 *   package com.semanticpartners
 *
**/class ShaclParserTest {

    @Test
    void getNodeShapes() {
        int nodeShapesCount = ShaclParser.getNodeShapes(ShaclParserTest.class.getClassLoader().getResource("test.ttl").getPath()).size();
        assertEquals(5, nodeShapesCount, "Node shapes count did not match the expected value.");
    }

    @Test
    void getPropertyShapes() {
        int propertyShapesCount = ShaclParser.getPropertyShapes(ShaclParserTest.class.getClassLoader().getResource("test.ttl").getPath()).size();
        assertEquals(9, propertyShapesCount, "Property shapes count did not match the expected value.");
    }

    @Test
    void getNamespace() {
        String namespace = ShaclParser.getNamespace(ShaclParserTest.class.getClassLoader().getResource("test.ttl").getPath(), "test");
        assertEquals("http://www.example.org/test#", namespace, "Namespace did not match the expected value.");
    }
}
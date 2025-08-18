package com.keyin.binarysearchtreeapi;

import com.keyin.binarysearchtreeapi.model.TreeRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TreeRecordTest {

    @Test
    public void testParameterizedConstructorAndGetters() {
        TreeRecord record = new TreeRecord("10,20,30", "{value:10}");

        // Verify values set through constructor
        assertEquals("10,20,30", record.getNumbers());
        assertEquals("{value:10}", record.getTreeJson());
    }

    @Test
    public void testSettersAndGetters() {
        TreeRecord record = new TreeRecord();
        record.setNumbers("5,15");
        record.setTreeJson("{value:5}");

        // Verify values set through setters
        assertEquals("5,15", record.getNumbers());
        assertEquals("{value:5}", record.getTreeJson());
    }

    @Test
    public void testNoArgsConstructorDefaults() {
        TreeRecord record = new TreeRecord();

        // With no values set, defaults should be null
        assertNull(record.getNumbers());
        assertNull(record.getTreeJson());
    }
}

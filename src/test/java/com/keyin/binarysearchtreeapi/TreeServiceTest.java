package com.keyin.binarysearchtreeapi;

import com.keyin.binarysearchtreeapi.model.TreeRecord;
import com.keyin.binarysearchtreeapi.repository.TreeRecordRepository;
import com.keyin.binarysearchtreeapi.service.TreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TreeServiceTest {

    private TreeService treeService;
    private TreeRecordRepository mockRepo;

    @BeforeEach
    public void setUp() {
        mockRepo = mock(TreeRecordRepository.class);
        treeService = new TreeService(mockRepo);
    }

    @Test
    public void testInsertAndSearch() {
        treeService.insert(10);
        treeService.insert(5);
        treeService.insert(15);

        assertTrue(treeService.search(10));
        assertTrue(treeService.search(5));
        assertTrue(treeService.search(15));
        assertFalse(treeService.search(20));
    }

    @Test
    public void testInorderTraversal() {
        treeService.insert(20);
        treeService.insert(10);
        treeService.insert(30);

        List<Integer> result = treeService.inorderTraversal();
        assertEquals(List.of(10, 20, 30), result);
    }

    @Test
    public void testDeleteLeafNode() {
        treeService.insert(10);
        treeService.insert(5);
        treeService.insert(15);

        treeService.delete(5);

        assertFalse(treeService.search(5));
        assertEquals(List.of(10, 15), treeService.inorderTraversal());
    }

    @Test
    public void testProcessNumbers() {
        List<Integer> input = List.of(8, 3, 10);
        Map<String, Object> jsonTree = treeService.processNumbers(input);

        assertNotNull(jsonTree);
        assertEquals(8, jsonTree.get("value"));       // root check
        assertNotNull(jsonTree.get("left"));          // left child (3) should exist
        assertNotNull(jsonTree.get("right"));         // right child (10) should exist

        // verify DB save was triggered
        verify(mockRepo, times(1)).save(any(TreeRecord.class));
    }
}

package com.keyin.binarysearchtreeapi.service;

import com.keyin.binarysearchtreeapi.model.Node;
import com.keyin.binarysearchtreeapi.model.TreeRecord;
import com.keyin.binarysearchtreeapi.repository.TreeRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TreeService {

    private Node root; // Root of the BST (in-memory structure)
    private final TreeRecordRepository treeRecordRepository;

    public TreeService(TreeRecordRepository treeRecordRepository) {
        this.treeRecordRepository = treeRecordRepository;
        rebuildFromDatabase();
    }

    // --- MAIN FEATURE: process a whole batch of numbers ---
    @Transactional
    public Map<String, Object> processNumbers(List<Integer> numbers) {
        clear(); // reset
        if (numbers != null && !numbers.isEmpty()) {
            for (int val : numbers) {
                root = insertRec(root, val);
            }
        }

        // Build full response object for frontend
        Map<String, Object> response = new HashMap<>();
        response.put("entered", numbers);              // raw numbers
        response.put("count", numbers.size());         // how many
        response.put("sorted", inorderTraversal());    // inorder traversal (sorted list)
        response.put("tree", buildJson(root));         // JSON tree structure

        // Save record (numbers + treeJson as String)
        String numbersStr = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String treeJsonStr = response.get("tree") != null ? response.get("tree").toString() : "{}";
        treeRecordRepository.save(new TreeRecord(numbersStr, treeJsonStr));

        return response;
    }

    // --- Return all previously saved records ---
    public List<TreeRecord> getPreviousTrees() {
        return treeRecordRepository.findAll();
    }

    // --- INSERT (public) ---
    public void insert(int value) {
        root = insertRec(root, value);
    }

    // --- INSERT (internal) ---
    private Node insertRec(Node root, int value) {
        if (root == null) return new Node(value);
        if (value < root.getValue()) root.setLeft(insertRec(root.getLeft(), value));
        else if (value > root.getValue()) root.setRight(insertRec(root.getRight(), value));
        return root; // duplicates ignored
    }

    // --- SEARCH ---
    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(Node root, int value) {
        if (root == null) return false;
        if (root.getValue() == value) return true;
        return value < root.getValue()
                ? searchRec(root.getLeft(), value)
                : searchRec(root.getRight(), value);
    }

    // --- DELETE ---
    public void delete(int value) {
        root = deleteRec(root, value);
    }

    private Node deleteRec(Node root, int value) {
        if (root == null) return null;

        if (value < root.getValue()) {
            root.setLeft(deleteRec(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(deleteRec(root.getRight(), value));
        } else {
            // Node found
            if (root.getLeft() == null) return root.getRight();
            else if (root.getRight() == null) return root.getLeft();

            // Node with two children → replace with inorder successor
            root.setValue(minValue(root.getRight()));
            root.setRight(deleteRec(root.getRight(), root.getValue()));
        }
        return root;
    }

    private int minValue(Node node) {
        int min = node.getValue();
        while (node.getLeft() != null) {
            min = node.getLeft().getValue();
            node = node.getLeft();
        }
        return min;
    }

    // --- INORDER TRAVERSAL ---
    public List<Integer> inorderTraversal() {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(Node node, List<Integer> result) {
        if (node != null) {
            inorderRec(node.getLeft(), result);
            result.add(node.getValue());
            inorderRec(node.getRight(), result);
        }
    }

    // --- CLEAR ---
    @Transactional
    public void clear() {
        root = null;
        treeRecordRepository.deleteAll();
    }

    // --- DB SYNC ---
    public void rebuildFromDatabase() {
        root = null;
        List<TreeRecord> records = treeRecordRepository.findAll();
        if (!records.isEmpty()) {
            // use last record to rebuild tree
            String[] nums = records.get(records.size() - 1).getNumbers().split(",");
            for (String n : nums) {
                if (!n.isBlank()) {
                    root = insertRec(root, Integer.parseInt(n.trim()));
                }
            }
        }
    }

    // --- Build JSON representation ---
    private Map<String, Object> buildJson(Node node) {
        if (node == null) return null;
        Map<String, Object> json = new HashMap<>();
        json.put("value", node.getValue());
        json.put("left", buildJson(node.getLeft()));
        json.put("right", buildJson(node.getRight()));
        return json;
    }
}

package com.keyin.binarysearchtreeapi.controller;

import com.keyin.binarysearchtreeapi.model.TreeRecord;
import com.keyin.binarysearchtreeapi.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    // ✅ Process a full list of numbers into a BST and save
    @PostMapping("/process-numbers")
    public ResponseEntity<Map<String, Object>> processNumbers(@RequestBody List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return buildResponse(400, "No numbers provided", null);
        }

        Map<String, Object> treeJson = treeService.processNumbers(numbers);
        return buildResponse(200, "Tree processed and saved", treeJson);
    }

    // ✅ Insert a single number into the current BST
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertValue(@RequestParam int value) {
        treeService.insert(value);
        return buildResponse(200, "Value inserted into tree", treeService.inorderTraversal());
    }

    // ✅ Delete a single number from the current BST
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteValue(@RequestParam int value) {
        treeService.delete(value);
        return buildResponse(200, "Value deleted from tree (if existed)", treeService.inorderTraversal());
    }

    // ✅ Search for a value in the current tree
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchValue(@RequestParam int value) {
        boolean found = treeService.search(value);
        return buildResponse(200, found ? "Value found" : "Value not found", found);
    }

    // ✅ Return inorder traversal of the current tree
    @GetMapping("/traversal")
    public ResponseEntity<Map<String, Object>> getInorderTraversal() {
        List<Integer> traversal = treeService.inorderTraversal();
        return buildResponse(200, "Inorder traversal retrieved", traversal);
    }

    // ✅ Return all previous trees (for frontend "Show Previous Trees")
    @GetMapping("/previous-trees")
    public ResponseEntity<Map<String, Object>> getPreviousTrees() {
        List<TreeRecord> records = treeService.getPreviousTrees();
        if (records.isEmpty()) {
            return buildResponse(200, "No previous trees found", List.of());
        }
        return buildResponse(200, "Previous trees retrieved", records);
    }

    // ✅ Rebuild current in-memory tree from DB
    @PostMapping("/rebuild-tree")
    public ResponseEntity<Map<String, Object>> rebuildTree() {
        treeService.rebuildFromDatabase();
        return buildResponse(200, "Tree rebuilt from database", treeService.inorderTraversal());
    }

    // ✅ Clear DB and in-memory tree
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, Object>> clearTree() {
        treeService.clear();
        return buildResponse(200, "Tree and DB cleared", null);
    }

    // 🔹 Shared response builder
    private ResponseEntity<Map<String, Object>> buildResponse(int status, String message, Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("message", message);
        if (data != null) {
            body.put("data", data);
        }
        return ResponseEntity.status(status).body(body);
    }
}

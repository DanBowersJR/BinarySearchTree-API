package com.keyin.binarysearchtreeapi.model;

import jakarta.persistence.*;

@Entity
public class TreeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store all numbers entered (e.g., "10,5,15,3,7")
    @Column(nullable = false, length = 1000)
    private String numbers;

    // Store serialized tree structure (e.g., JSON of the BST)
    @Column(nullable = false, length = 5000)
    private String treeJson;

    public TreeRecord() {}

    public TreeRecord(String numbers, String treeJson) {
        this.numbers = numbers;
        this.treeJson = treeJson;
    }

    public Long getId() {
        return id;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getTreeJson() {
        return treeJson;
    }

    public void setTreeJson(String treeJson) {
        this.treeJson = treeJson;
    }
}

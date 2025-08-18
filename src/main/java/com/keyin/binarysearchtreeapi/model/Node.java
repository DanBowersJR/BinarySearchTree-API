package com.keyin.binarysearchtreeapi.model;

public class Node {
    private int value;
    private Node left = null;
    private Node right = null;

    public Node(int value) {
        this.value = value;
    }

    // Getter & Setter for value
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // Getters & Setters for children
    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", left=" + (left != null ? left.getValue() : "null") +
                ", right=" + (right != null ? right.getValue() : "null") +
                '}';
    }
}

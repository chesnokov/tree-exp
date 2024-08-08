package com.example.tree;

import java.util.List;

public interface Node<T> {
	List<Node<T>> getAdjacencyList();
	T getData();
}

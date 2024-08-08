package com.example.tree;

import java.util.function.Consumer;

public interface GraphTraverser<T> {
	void traverse(Node<T> start, Consumer<T> consumer);
}

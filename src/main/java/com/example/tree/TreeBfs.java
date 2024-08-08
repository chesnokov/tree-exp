package com.example.tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

public class TreeBfs<T> extends TreeDfs<T> {

	@Override
	public void traverse(Node<T> root, Consumer<T> consumer) {
		Queue<VisitableNode<T>> nodeToVisit = new ArrayDeque<>();
		nodeToVisit.add(new VisitableNode<>(root));
		while(!nodeToVisit.isEmpty()) {
			VisitableNode<T> node = nodeToVisit.remove();
			if(node.notVisited()) {
				node.setVisited();
				consumer.accept(node.getData());
				nodeToVisit.addAll(getAdjacencyList(node));
			}
		}

	}
}

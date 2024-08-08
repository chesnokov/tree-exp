package com.example.tree;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TreeDfs<T> implements GraphTraverser<T> {

	@Override
	public void traverse(Node<T> start, Consumer<T> consumer) {
		Deque<VisitableNode<T>> nodeToVisit = new ArrayDeque<>();
		nodeToVisit.push(new VisitableNode<>(start));
		while(!nodeToVisit.isEmpty()) {
			VisitableNode<T> node = nodeToVisit.pop();
			if(node.notVisited()) {
				node.setVisited();
				consumer.accept(node.getData());
				getAdjacencyList(node).forEach(nodeToVisit::push);
			}
		}
	}

	protected List<VisitableNode<T>> getAdjacencyList(Node<T> node) {
		return node.getAdjacencyList().stream()
			.map(VisitableNode::new)
			.collect(Collectors.toList());
	}


	@RequiredArgsConstructor
	protected static class VisitableNode<T> implements Node<T> {

		private final Node<T> wrappedNode;
		private boolean visited;

		@Override
		public List<Node<T>> getAdjacencyList() {
			return wrappedNode.getAdjacencyList();
		}

		@Override
		public T getData() {
			return wrappedNode.getData();
		}

		public boolean notVisited() {
			return !visited;
		}

		public void setVisited() {
			visited = true;
		}
	}
}

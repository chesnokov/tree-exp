package com.example.tree;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class WrapperNode<T> implements Node<T> {
	private final T data;
	private final AdjacencySupplier<T> adjacencySupplier;

	@Override
	public List<Node<T>> getAdjacencyList() {
		return adjacencySupplier.getAdjacencyList(data).stream()
			.map( o -> new WrapperNode<>(o, adjacencySupplier))
			.collect(Collectors.toList());
	}

	@Override
	public T getData() {
		return data;
	}

	public interface AdjacencySupplier<T> {
		List<T> getAdjacencyList(T o);
	}
}

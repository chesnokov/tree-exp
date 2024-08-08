package com.example.ast;

import com.example.tree.Node;

import java.util.Collections;
import java.util.List;


public class IntNode implements Node<AstData> {
	private final static List<Node<AstData>> EMPTY_ADJACENCY_LIST = Collections.unmodifiableList(Collections.emptyList());
	private final IntValue value;

	public IntNode(Integer value) {
		this.value = new IntValue(value);
	}

	@Override
	public List<Node<AstData>> getAdjacencyList() {
		return EMPTY_ADJACENCY_LIST;
	}

	@Override
	public AstData getData() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value.getValue());
	}
}

package com.example.ast;

import com.example.tree.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class OperatorNode implements Node<AstData> {

	private final AstData data;
	private Node<AstData> left;
	private Node<AstData> right;

	@Override
	public List<Node<AstData>> getAdjacencyList() {
		return Arrays.asList(left, right);
	}

	@Override
	public AstData getData() {
		return data;
	}
}

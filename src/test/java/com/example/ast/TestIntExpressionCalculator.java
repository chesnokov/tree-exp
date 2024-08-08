package com.example.ast;

import com.example.tree.GraphTraverser;
import com.example.tree.TreeDfs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIntExpressionCalculator {

	GraphTraverser<AstData> traverser;
	IntExpressionCalculator calc;

	@BeforeEach
	public void beforeEach() {
		traverser = new TreeDfs<>();
		calc = new IntExpressionCalculator(traverser);
	}

	@Test
	public void test1() {
		OperatorNode minus1 = OperatorNode.builder()
			.left(new IntNode(5))
			.right(new IntNode(1))
			.data(new MinusIntOperator())
			.build();

		OperatorNode minus2 = OperatorNode.builder()
			.left(new IntNode(5))
			.right(new IntNode(4))
			.data(new MinusIntOperator())
			.build();

		OperatorNode minus3 = OperatorNode.builder()
				.left(minus1)
				.right(minus2)
				.data(new MinusIntOperator())
				.build();

		int result = calc.evaluate(minus3);
		System.out.println(result);
	}


	@Test
	public void test2() {
		OperatorNode plus = OperatorNode.builder()
				.left(new IntNode(2))
				.right(new IntNode(3))
				.data(new PlusIntOperator())
				.build();

		OperatorNode multiply = OperatorNode.builder()
				.left(plus)
				.right(new IntNode(2))
				.data(new MultiplyIntOperator())
				.build();

		OperatorNode minus = OperatorNode.builder()
				.left(new IntNode(4))
				.right(new IntNode(2))
				.data(new MinusIntOperator())
				.build();

		OperatorNode divide = OperatorNode.builder()
				.left(multiply)
				.right(minus)
				.data(new DivideIntOperator())
				.build();

		int result = calc.evaluate(divide);
		System.out.println(result);
	}

}

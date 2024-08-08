package com.example.ast;

import com.example.tree.GraphTraverser;
import com.example.tree.Node;
import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

import static com.example.ast.AstData.AstType.BINARY_OPERATOR;
import static com.example.ast.AstData.AstType.VALUE;


@RequiredArgsConstructor
public class IntExpressionCalculator implements Consumer<AstData> {

	private final GraphTraverser<AstData> graphTraverser;

	private AstData operator;
	private AstData rightOperand;
	private final Deque<AstData> stack = new ArrayDeque<>();

	int result;

	@Override
	public void accept(AstData data) {
		if(BINARY_OPERATOR == data.getType()) {
			acceptOperator(data);
		} else if(VALUE == data.getType()) {
			acceptOperand(data);
		}
	}

	public int evaluate(Node<AstData> expression) {
		graphTraverser.traverse(expression, this);
		return result;
	}

	private void acceptOperand(AstData data) {
		if(operator == null) {
			result = ((IntValue)data).getValue();
		} else if(rightOperand == null) {
			rightOperand = data;
		} else {
			int value = evaluateOperation(((IntValue)data).getValue());
			processStack(value);
		}
	}

	private void processStack(int value) {

		boolean evaluated;
		do {
			evaluated = false;
			if(stack.isEmpty()) {
				operator = null;
				rightOperand = null;
				result = value;
				return;
			}

			AstData data = stack.pop();
			if(VALUE == data.getType()) {
				rightOperand = data;
				operator = stack.pop(); // TODO check here that it is operator
				value = evaluateOperation(value);
				evaluated = true;
			} else {
				operator = data;
				rightOperand = new IntValue(value);
			}
		} while(evaluated);
	}

	private void acceptOperator(AstData data) {
		if(operator != null) {
			pushOperatorAndOperand();
		}
		operator = data;
	}

	private int evaluateOperation(int leftOperand) {
		return ((IntOperator)operator).apply(leftOperand,
				((IntValue)rightOperand).getValue());
	}

	private void pushOperatorAndOperand() {
		stack.push(operator);
		if(rightOperand != null) {
			stack.push(rightOperand);
		}
		rightOperand = null;
		operator = null;
	}
}

package com.example.ast;

public class MultiplyIntOperator extends IntOperator {

	public MultiplyIntOperator() {
		super(Priorities.MULTIPLY_OPERATOR_PRIORITY);
	}

	@Override
	public int apply(int a, int b) {
		return a * b;
	}

	@Override
	public String toString() {
		return "*";
	}

	@Override
	public OperatorType getOperatorType() {
		return OperatorType.MULTIPLY;
	}
}

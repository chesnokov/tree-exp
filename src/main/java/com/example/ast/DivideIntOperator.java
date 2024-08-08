package com.example.ast;

public class DivideIntOperator extends IntOperator {
	public DivideIntOperator() {
		super(Priorities.DIVIDE_OPERATOR_PRIORITY);
	}

	@Override
	public int apply(int a, int b) {
		return a / b;
	}

	@Override
	public String toString() {
		return "/";
	}

	@Override
	public OperatorType getOperatorType() {
		return OperatorType.DIVIDE;
	}
}

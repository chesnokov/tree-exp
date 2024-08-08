package com.example.ast;

public class PlusIntOperator extends IntOperator {

	public PlusIntOperator() {
		super(Priorities.PLUS_OPERATOR_PRIORITY);
	}
	@Override
	public int apply(int a, int b) {
		return a + b;
	}

	@Override
	public String toString() {
		return "+";
	}

	@Override
	public OperatorType getOperatorType() {
		return OperatorType.PLUS;
	}
}

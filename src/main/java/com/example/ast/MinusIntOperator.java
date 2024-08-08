package com.example.ast;

public class MinusIntOperator extends IntOperator {

	public MinusIntOperator() {
		super(Priorities.MINUS_OPERATOR_PRIORITY);
	}

	@Override
	public int apply(int a, int b) {
		return a - b;
	}

	@Override
	public String toString() {
		return "-";
	}

	@Override
	public OperatorType getOperatorType() {
		return OperatorType.MINUS;
	}
}

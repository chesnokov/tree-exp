package com.example.ast;

public abstract class IntOperator extends AstData implements Operator {

	public IntOperator(int priority) {
		super(priority);
	}

	public abstract int apply(int a, int b);

	@Override
	public AstType getType() {
		return AstType.BINARY_OPERATOR;
	}
}

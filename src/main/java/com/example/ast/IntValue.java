package com.example.ast;

import lombok.Getter;

@Getter
public class IntValue extends AstData {
	private final int value;

	public IntValue(int value) {
		super(Priorities.MAX_PRIORITY);
		this.value = value;
	}

	public String toString() {
		return Integer.toString(value);
	}
}

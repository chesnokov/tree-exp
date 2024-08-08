package com.example.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AstData {
	public enum AstType {
		VALUE,
		BINARY_OPERATOR,
	}

	int priority;

	AstType getType() {
		return AstType.VALUE;
	}
}

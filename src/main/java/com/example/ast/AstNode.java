package com.example.ast;

import com.example.ast.AstData.AstType;
import com.example.tree.Node;

public class AstNode {
    public static int getPriority(Node<AstData> node) {
        return node.getData().getPriority();
    }

    public static AstType getType(Node<AstData> node) {
        return node.getData().getType();
    }
}

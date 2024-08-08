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
public class ExpressionToString implements Consumer<AstData> {

    private final GraphTraverser<AstData> graphTraverser;

    private AstData operator;
    private AstData rightOperand;
    private final Deque<AstData> stack = new ArrayDeque<>();
    private String result;

    @Override
    public void accept(AstData data) {
        if(BINARY_OPERATOR == data.getType()) {
            acceptOperator(data);
        } else if(VALUE == data.getType()) {
            acceptOperand(data);
        }
    }

    public String evaluate(Node<AstData> expression) {
        graphTraverser.traverse(expression, this);
        return result;
    }

    private void acceptOperator(AstData data) {
        if(operator != null) {
            pushOperatorAndOperand();
        }
        operator = data;
    }

    private void acceptOperand(AstData data) {
        if(operator == null) {
            result = Integer.toString(((IntValue)data).getValue());
        } else if(rightOperand == null) {
            rightOperand = data;
        } else {
            String value = evaluateOperation(data.toString());
            processStack(value);
        }
    }

    private void pushOperatorAndOperand() {
        stack.push(operator);
        if(rightOperand != null) {
            stack.push(rightOperand);
        }
        rightOperand = null;
        operator = null;
    }

    private void processStack(String value) {

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
                rightOperand = new StringValue(value);
            }
        } while(evaluated);
    }

    private String evaluateOperation(String leftOperand) {
        String value = leftOperand + operator.toString() + rightOperand.toString();
        return isParenthesesRequired(operator, stack) ? "(" + value + ")" : value;
    }

    private boolean isParenthesesRequired(AstData operator, Deque<AstData> stack) {
        if(stack.isEmpty()) {
            return false;
        } else {
            AstData data = stack.pop();
            AstData parentOperator;
            boolean isLeft = false;
            if(BINARY_OPERATOR == data.getType()) {
                parentOperator = data;
                isLeft = true;
            } else {
                parentOperator = stack.peek();
                if(parentOperator == null) {
                    throw new AstException("Operator has to be at the bottom of the stack");
                }
            }
            stack.push(data);
            if(parentOperator.getPriority() > operator.getPriority()) {
                return true;
            }
            return isLeft && ((Operator)parentOperator).getOperatorType() == Operator.OperatorType.MINUS &&
                ((Operator) operator).getOperatorType() == Operator.OperatorType.MINUS;
        }
    }
}

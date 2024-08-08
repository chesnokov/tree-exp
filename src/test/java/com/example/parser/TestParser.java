package com.example.parser;

import com.example.ast.AstData;
import com.example.ast.ExpressionToString;
import com.example.ast.IntExpressionCalculator;
import com.example.parse.Eof;
import com.example.parse.EofParser;
import com.example.parse.ExpressionParser;
import com.example.parse.IntLiteral;
import com.example.parse.Parser;
import com.example.parse.RootParser;
import com.example.parse.Symbol;
import com.example.tree.GraphTraverser;
import com.example.tree.Node;
import com.example.tree.TreeDfs;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.example.parse.Parser.Status.CONTINUE;

public class TestParser {

    GraphTraverser<AstData> traverser;
    ExpressionToString calc;
    IntExpressionCalculator intCalc;

    Deque<Parser> stack;

    RootParser rootParser;

    @BeforeEach
    public void beforeEach() {
        traverser = new TreeDfs<>();
        calc = new ExpressionToString(traverser);
        intCalc = new IntExpressionCalculator(traverser);

        stack = new ArrayDeque<>();
        stack.push(new EofParser());
        stack.push(new ExpressionParser());

        rootParser = new RootParser();
    }

    @Test
    public void shouldReturnConstant() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(3))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void shouldReturnFail() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(3))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.FAIL);
    }

    @Test
    public void shouldMake2plus2() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void shouldReturnFailOnIncompleteOperation() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.FAIL);
    }

    @Test
    public void shouldMake2Plus2Div2() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void shouldMake2() {
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void make2Plus2InBrackets() {
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));

    }

    @Test
    public void shouldMake2plus2div2() {
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void shouldMake2plus4div4mul2() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("+"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("*"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void shouldMake6minus4div2minus3() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(6))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(3))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void test1() {
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(13))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(3))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("*"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void test2() {
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(13))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(3))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("*"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }

    @Test
    public void test3() {
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(13))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("/"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(3))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("*"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("("))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(2))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol("-"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new IntLiteral(4))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Symbol(")"))).isEqualTo(CONTINUE);
        Assertions.assertThat(rootParser.accept(stack, null, new Eof())).isEqualTo(Parser.Status.READY);
        Node<AstData> expression = rootParser.getValue();
        Assertions.assertThat(expression).isNotNull();
        System.out.println(calc.evaluate(expression) + " = " + intCalc.evaluate(expression));
    }


}

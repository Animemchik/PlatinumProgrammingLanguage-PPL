package me.Animemchik.parser.visitors;

import me.Animemchik.parser.ast.*;

public abstract class AbstractVisitor implements Visitor {

    @Override
    public void visit(ArrayAccessExpression s) {
        for (Expression index : s.indices) {
            index.accept(this);
        }
    }

    @Override
    public void visit(ArrayAssignmentStatement s) {
        s.array.accept(this);
        s.expression.accept(this);
    }

    @Override
    public void visit(ArrayExpression s) {
        for (Expression index : s.elements) {
            index.accept(this);
        }
    }

    @Override
    public void visit(AssignmentStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(AssignmentEqStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(BinaryAssignmentStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(BinaryExpression s) {
        s.expr1.accept(this);
        s.expr2.accept(this);
    }

    @Override
    public void visit(BinaryAssignmentExpression s) {
    }

    @Override
    public void visit(BlockStatement s) {
        for (Statement statement : s.statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(BreakStatement s) {
    }

    @Override
    public void visit(ConditionalExpression s) {
        s.expr1.accept(this);
        s.expr2.accept(this);
    }

    @Override
    public void visit(ContinueStatement s) {
    }

    @Override
    public void visit(DoWhileStatement s) {
        s.condition.accept(this);
        s.statement.accept(this);
    }

    @Override
    public void visit(ForStatement s) {
        s.initialization.accept(this);
        s.termination.accept(this);
        s.increment.accept(this);
        s.statement.accept(this);
    }

    @Override
    public void visit(FunctionDefineStatement s) {
        s.body.accept(this);
    }

    @Override
    public void visit(FunctionStatement s) {
        s.function.accept(this);
    }

    @Override
    public void visit(FunctionalExpression s) {
        for (Expression argument : s.arguments) {
            argument.accept(this);
        }
    }

    @Override
    public void visit(IfStatement s) {
        s.expression.accept(this);
        s.ifStatement.accept(this);
        if (s.elseStatement != null) {
            s.elseStatement.accept(this);
        }
    }

    @Override
    public void visit(IncludeStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(PrintStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(PrintlnStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(ReturnStatement s) {
        s.expression.accept(this);
    }

    @Override
    public void visit(UnaryExpression s) {
        s.expr.accept(this);
    }

    @Override
    public void visit(ValueExpression s) {
    }

    @Override
    public void visit(VariableExpression s) {
    }

    @Override
    public void visit(WhileStatement st) {
        st.condition.accept(this);
        st.statement.accept(this);
    }

    @Override
    public void visit(UseStatement st) {
        st.expression.accept(this);
    }
}
package me.Animemchik.parser.ast;

public final class IfStatement implements Statement{

    public final Expression expression;
    public final Statement ifStatement, elseStatement;

    public IfStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final double result = expression.eval().asNumber();
        if (result != 0) {
            ifStatement.execute();
        } else if (elseStatement != null) {
            elseStatement.execute();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("if (").append(expression).append(") {\n").append(ifStatement).append("}");
        if (elseStatement != null) {
            builder.append("\nelse {\n").append(elseStatement).append("}");
        }
        return builder.toString();
    }
}

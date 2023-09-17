package me.Animemchik.parser.ast;

public final class DoWhileStatement implements Statement {

    public final Statement statement;
    public final Expression condition;

    public DoWhileStatement(Statement statement, Expression condition) {
        this.statement = statement;
        this.condition = condition;
    }

    @Override
    public void execute() {
        do {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement bs) {}
        } while (condition.eval().asNumber() != 0);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "do {\n" + statement + "} while (" + condition + ")";
    }
}

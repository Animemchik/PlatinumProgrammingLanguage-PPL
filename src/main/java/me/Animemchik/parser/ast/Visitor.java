package me.Animemchik.parser.ast;

public interface Visitor {

    void visit(ArrayAccessExpression s);
    void visit(ArrayAssignmentStatement s);
    void visit(ArrayExpression s);
    void visit(AssignmentStatement s);
    void visit(AssignmentEqStatement s);
    void visit(BinaryExpression s);
    void visit(BinaryAssignmentExpression s);
    void visit(BinaryAssignmentStatement s);
    void visit(BlockStatement s);
    void visit(BreakStatement s);
    void visit(ConditionalExpression s);
    void visit(ContinueStatement s);
    void visit(DoWhileStatement s);
    void visit(ForStatement s);
    void visit(FunctionDefineStatement s);
    void visit(FunctionStatement s);
    void visit(FunctionalExpression s);
    void visit(IfStatement s);
    void visit(IncludeStatement s);
    void visit(PrintStatement s);
    void visit(PrintlnStatement s);
    void visit(ReturnStatement s);
    void visit(UnaryExpression s);
    void visit(UseStatement s);
    void visit(ValueExpression s);
    void visit(VariableExpression s);
    void visit(WhileStatement s);
}

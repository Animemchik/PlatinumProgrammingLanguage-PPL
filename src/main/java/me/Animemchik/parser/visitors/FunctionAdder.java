package me.Animemchik.parser.visitors;

import me.Animemchik.parser.ast.*;

public final class FunctionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }
}
package me.Animemchik.parser.ast;

import me.Animemchik.exceptions.ParseException;
import me.Animemchik.parser.Lexer;
import me.Animemchik.parser.Parser;
import me.Animemchik.parser.Token;
import me.Animemchik.parser.visitors.FunctionAdder;
import me.Animemchik.parser.visitors.SourceLoader;

import java.io.IOException;
import java.util.List;

public final class IncludeStatement extends InterruptableNode implements Statement {

    public final Expression expression;

    public IncludeStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        try {
            final Statement program = loadProgram(expression.eval().asString());
            if (program != null) {
                program.accept(new FunctionAdder());
                program.execute();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Statement loadProgram(String path) throws IOException {
        final String input = SourceLoader.readSource(path);
        final List<Token> tokens = new Lexer(input).tokenize();
        final Parser parser = new Parser(tokens);
        final Statement program = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            throw new ParseException(parser.getParseErrors().toString());
        }
        return program;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "include " + expression;
    }
}
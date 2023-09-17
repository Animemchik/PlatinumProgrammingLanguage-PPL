package me.Animemchik.parser;

import me.Animemchik.parser.ast.*;

import java.util.ArrayList;
import java.util.List;

public final class Parser {

    private static final Token EOF = new Token(TokenType.EOF, "", -1, -1);

    private final List<Token> tokens;
    private final int size;
    private final ParseErrors parseErrors;
    private Statement parsedStatement;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
        parseErrors = new ParseErrors();
    }

    public Statement getParsedStatement() {
        return parsedStatement;
    }

    public ParseErrors getParseErrors() {
        return parseErrors;
    }

    public Statement parse() {
        parseErrors.clear();
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            try {
                result.add(statement());
            } catch (Exception ex) {
                parseErrors.add(ex, getErrorLine());
                recover();
            }
        }
        parsedStatement = result;
        return result;
    }

    private int getErrorLine() {
        if (size == 0) return 0;
        if (pos >= size) return tokens.get(size - 1).getRow();
        return tokens.get(pos).getRow();
    }

    private void recover() {
        int preRecoverPosition = pos;
        for (int i = preRecoverPosition; i <= size; i++) {
            pos = i;
            try {
                statement();
                // successfully parsed,
                pos = i; // restore position
                return;
            } catch (Exception ex) {
                // fail
            }
        }
    }


    private Statement block() {
        final BlockStatement block = new BlockStatement();
        consume(TokenType.LBRACE);
        while (!match(TokenType.RBRACE)) {
            block.add(statement());
        }
        return block;
    }

    private Statement statementOrBlock() {
        if (lookMatch(0, TokenType.LBRACE)) return block();
        return statement();
    }

    private Statement statement() {
        if (match(TokenType.PRINT)) {
            consume(TokenType.LPAREN);
            final Expression expression = expression();
            consume(TokenType.RPAREN);
            return new PrintStatement(expression);
        }
        if (match(TokenType.PRINTLN)) {
            consume(TokenType.LPAREN);
            final Expression expression = expression();
            consume(TokenType.RPAREN);
            return new PrintlnStatement(expression);
        }
        if (match(TokenType.IF)) {
            return ifElse();
        }
        if (match(TokenType.WHILE)) {
            return whileStatement();
        }
        if (match(TokenType.DOWHILE)) {
            return doWhileStatement();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }
        if (match(TokenType.BREAK)) {
            return new BreakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            return new ContinueStatement();
        }
        if (match(TokenType.DEF)) {
            return functionDefine();
        }
        if (match(TokenType.RETURN)) {
            return new ReturnStatement(expression());
        }
        if (match(TokenType.USE)) {
            return new UseStatement(expression());
        }
        if (match(TokenType.INCLUDE)) {
            return new IncludeStatement(expression());
        }
        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.LPAREN)) {
            return new FunctionStatement(function());
        }
        return assignmentStatement();
    }

    private FunctionalExpression function() {
        final String name = consume(TokenType.WORD).getValue();
        consume(TokenType.LPAREN);
        final FunctionalExpression function = new FunctionalExpression(name);
        while (!match(TokenType.RPAREN)) {
            function.addArgument(expression());
            match(TokenType.COMMA);
        }
        return function;
    }

    private FunctionDefineStatement functionDefine() {
        final String name = consume(TokenType.WORD).getValue();
        consume(TokenType.LPAREN);
        final List<String> argNames = new ArrayList<>();
        while (!match(TokenType.RPAREN)) {
            argNames.add(consume(TokenType.WORD).getValue());
            match(TokenType.COMMA);
        }
        final Statement body = statementOrBlock();
        return new FunctionDefineStatement(name, argNames, body);
    }

    private Statement assignmentStatement() {
        // WORD EQ
        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.EQ)) {
            final String variable = consume(TokenType.WORD).getValue();
            consume(TokenType.EQ);
            return new AssignmentStatement(variable, expression());
        }
        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.LBRACKET)) {
            ArrayAccessExpression array = element();
            consume(TokenType.EQ);
            return new ArrayAssignmentStatement(array, expression());
        }
        // WORD (+-*/)=
        if (lookMatch(0, TokenType.WORD)) {
            final String variable;
            if (lookMatch(1, TokenType.PLUSEQ)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.PLUSEQ);
                return new AssignmentEqStatement(variable, TokenType.PLUSEQ, expression());
            } else if (lookMatch(1, TokenType.MINUSEQ)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.MINUSEQ);
                return new AssignmentEqStatement(variable, TokenType.MINUSEQ, expression());
            } else if (lookMatch(1, TokenType.MULTIEQ)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.MULTIEQ);
                return new AssignmentEqStatement(variable, TokenType.MULTIEQ, expression());
            } else if (lookMatch(1, TokenType.DIVIDEEQ)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.DIVIDEEQ);
                return new AssignmentEqStatement(variable, TokenType.DIVIDEEQ, expression());
            } else if (lookMatch(1, TokenType.PERCENTEQ)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.PERCENTEQ);
                return new AssignmentEqStatement(variable, TokenType.PERCENTEQ, expression());
            }
        }
        // WORD ++ -- **
        if (lookMatch(0, TokenType.WORD)) {
            final String variable;
            if (lookMatch(1, TokenType.PLUSPLUS)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.PLUSPLUS);
                return new BinaryAssignmentStatement(variable, TokenType.PLUSPLUS);
            } else if (lookMatch(1, TokenType.MINUSMINUS)) {
                variable = consume(TokenType.WORD).getValue();
                consume(TokenType.MINUSMINUS);
                return new BinaryAssignmentStatement(variable, TokenType.MINUSMINUS);
            }
        }
        throw new RuntimeException("Unknown statement");
    }

    private Statement ifElse() {
        consume(TokenType.LPAREN);
        final Expression condition = expression();
        consume(TokenType.RPAREN);
        final Statement ifStatement = statementOrBlock();
        final Statement elseStatement;
        if (match(TokenType.ELSE)) {
            elseStatement = statementOrBlock();
        } else {
            elseStatement = null;
        }
        return new IfStatement(condition, ifStatement, elseStatement);
    }

    private Statement whileStatement() {
        consume(TokenType.LPAREN);
        final Expression condition = expression();
        consume(TokenType.RPAREN);
        final Statement statement = statementOrBlock();
        return new WhileStatement(condition, statement);
    }

    private Statement doWhileStatement() {
        final Statement statement = statementOrBlock();
        consume(TokenType.WHILE);
        consume(TokenType.LPAREN);
        final Expression condition = expression();
        consume(TokenType.RPAREN);
        return new DoWhileStatement(statement, condition);
    }

    private Statement forStatement() {
        consume(TokenType.LPAREN);
        final Statement initialization = assignmentStatement();
        consume(TokenType.BAR);
        final Expression termination = expression();
        consume(TokenType.BAR);
        final Statement increment = assignmentStatement();
        consume(TokenType.RPAREN);
        final Statement statement = statementOrBlock();
        return new ForStatement(initialization, termination, increment, statement);
    }

    private Expression array() {
        consume(TokenType.LBRACKET);
        final List<Expression> elements = new ArrayList<>();
        while (!match(TokenType.RBRACKET)) {
            elements.add(expression());
            match(TokenType.COMMA);
        }
        return new ArrayExpression(elements);
    }

    private ArrayAccessExpression element() {
        final String variable = consume(TokenType.WORD).getValue();
        List<Expression> indices = new ArrayList<>();
        do {
            consume(TokenType.LBRACKET);
            indices.add(expression());
            consume(TokenType.RBRACKET);
        } while (lookMatch(0, TokenType.LBRACKET));
        return new ArrayAccessExpression(variable, indices);
    }

    private Expression expression() {
        return logicalOr();
    }

    private Expression logicalOr() {
        Expression result = logicalAnd();

        while (true) {
            if (match(TokenType.OR)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.OR, result, logicalAnd());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression logicalAnd() {
        Expression result = equality();

        while (true) {
            if (match(TokenType.AND)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.AND, result, equality());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression equality() {
        Expression result = conditional();

        if (match(TokenType.EQEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditional());
        }
        if (match(TokenType.EXCLEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditional());
        }

        return result;
    }

    private Expression conditional() {
        Expression result = additive();

        while (true) {
            if (match(TokenType.LT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, additive());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, additive());
                continue;
            }
            if (match(TokenType.GT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, additive());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, additive());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression additive() {
        Expression result = multiplicative();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression multiplicative() {
        Expression result = unary();

        while (true) {
            // 2 * 6 / 3
            if (match(TokenType.MULTI)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.DIVIDE)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            if (match(TokenType.PERCENT)) {
                result = new BinaryExpression('%', result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        return primary();
    }

    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new ValueExpression(Double.parseDouble(current.getValue()));
        }
        if (match(TokenType.HEX_NUMBER)) {
            return new ValueExpression(Long.parseLong(current.getValue(), 16));
        }
        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.LPAREN)) {
            return function();
        }
        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.LBRACKET)) {
            return element();
        }
        if (lookMatch(0, TokenType.LBRACKET)) {
            return array();
        }
        if (match(TokenType.WORD)) {
            return new VariableExpression(current.getValue());
        }
        if (match(TokenType.TEXT)) {
            return new ValueExpression(current.getValue());
        }
        if (match(TokenType.LPAREN)) {
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }
        System.out.println(current.getType().toString());
        throw new RuntimeException("Unknown expression");
    }

    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) throw new RuntimeException("Token " + current + " doesn't match " + type);
        pos++;
        return current;
    }

    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }

    private boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}
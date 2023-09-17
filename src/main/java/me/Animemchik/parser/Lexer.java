package me.Animemchik.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Lexer {
    private static final String OPERATOR_CHARS = "+-*/%()=<>[]{},!&|^~?:;";

    private static final Map<String, TokenType> OPERATORS;
    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.MULTI);
        OPERATORS.put("/", TokenType.DIVIDE);
        OPERATORS.put("%", TokenType.PERCENT); // none

        OPERATORS.put("++", TokenType.PLUSPLUS);
        OPERATORS.put("--", TokenType.MINUSMINUS);
        OPERATORS.put("+=", TokenType.PLUSEQ);
        OPERATORS.put("-=", TokenType.MINUSEQ);
        OPERATORS.put("*=", TokenType.MULTIEQ);
        OPERATORS.put("/=", TokenType.DIVIDEEQ);
        OPERATORS.put("%=", TokenType.PERCENTEQ);


        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
        OPERATORS.put("[", TokenType.LBRACKET);
        OPERATORS.put("]", TokenType.RBRACKET);
        OPERATORS.put("{", TokenType.LBRACE);
        OPERATORS.put("}", TokenType.RBRACE);
        OPERATORS.put(",", TokenType.COMMA);
        OPERATORS.put("=", TokenType.EQ);
        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);

        OPERATORS.put("^", TokenType.CARET); // none
        OPERATORS.put("~", TokenType.TILDE); // none

        OPERATORS.put("!", TokenType.EXCL);
        OPERATORS.put("&", TokenType.AMP);
        OPERATORS.put("|", TokenType.BAR);

        OPERATORS.put("==", TokenType.EQEQ);
        OPERATORS.put("!=", TokenType.EXCLEQ);
        OPERATORS.put("<=", TokenType.LTEQ);
        OPERATORS.put(">=", TokenType.GTEQ);

        OPERATORS.put("&&", TokenType.AND);
        OPERATORS.put("||", TokenType.OR);

        OPERATORS.put("?", TokenType.QUESTION); // none
        OPERATORS.put(":", TokenType.COLON); // none
        OPERATORS.put(";", TokenType.SEMICOLON); // none
    }
    private final String code;
    private final int length;

    private final List<Token> tokens;

    private int pos;
    private int row, col;

    public Lexer(String code) {
        this.code = code;
        this.length = code.length();
        this.tokens = new ArrayList<>();
        row = col = 1;
    }

    public List<Token> tokenize() {
        while (pos < length) {
            final char current = pick(0);
            if (Character.isLetter(current)) tokenizeWord();
            else if (Character.isDigit(current)) tokenizeNumber();
            else if (current == '"') tokenizeText();
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                next();
            }
        }

        return tokens;
    }

    private void tokenizeWord() {
        final StringBuilder builder = new StringBuilder();
        char current = pick(0);
        while(true) {
            if (!Character.isLetterOrDigit(current) && (current != '_')) {
                break;
            }
            builder.append(current);
            current = next();
        }
        final String word = builder.toString();
        switch (word) {
            case "print": addToken(TokenType.PRINT); break;
            case "println": addToken(TokenType.PRINTLN); break;
            case "if": addToken(TokenType.IF); break;
            case "else": addToken(TokenType.ELSE); break;
            case "while": addToken(TokenType.WHILE); break;
            case "for": addToken(TokenType.FOR); break;
            case "do": addToken(TokenType.DOWHILE); break;
            case "break": addToken(TokenType.BREAK); break;
            case "continue": addToken(TokenType.CONTINUE); break;
            case "def": addToken(TokenType.DEF); break;
            case "return": addToken(TokenType.RETURN); break;
            case "use": addToken(TokenType.USE); break;
            case "include": addToken(TokenType.INCLUDE); break;

            case "and": addToken(TokenType.AND); break;
            case "or": addToken(TokenType.OR); break;
            case "not": addToken(TokenType.NOT); break;
            default:
                addToken(TokenType.WORD, word); break;
        }
    }

    private void tokenizeText() {
        next(); // skip "
        final StringBuilder builder = new StringBuilder();
        char current = pick(0);
        while(true) {
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"': current = next(); builder.append('"'); continue;
                    case '0': current = next(); builder.append('\0'); continue;
                    case 'b': current = next(); builder.append('\b'); continue;
                    case 'f': current = next(); builder.append('\f'); continue;
                    case 'n': current = next(); builder.append('\n'); continue;
                    case 'r': current = next(); builder.append('\r'); continue;
                    case 't': current = next(); builder.append('\t'); continue;
                    case 'u': // http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.3
                        int rollbackPosition = pos;
                        while (current == 'u') current = next();
                        int escapedValue = 0;
                        for (int i = 12; i >= 0 && escapedValue != -1; i -= 4) {
                            if (isHexNumber(current)) {
                                escapedValue |= (Character.digit(current, 16) << i);
                            } else {
                                escapedValue = -1;
                            }
                            current = next();
                        }
                        if (escapedValue >= 0) {
                            builder.append((char) escapedValue);
                        } else {
                            // rollback
                            builder.append("\\u");
                            pos = rollbackPosition;
                        }
                        continue;
                }
                builder.append('\\');
                continue;
            }
            if (current == '"') break;
            builder.append(current);
            current = next();
        }
        next(); // skip "

        addToken(TokenType.TEXT, builder.toString());
    }

    private void tokenizeNumber() {
        final StringBuilder builder = new StringBuilder();
        char current = pick(0);
        if (current == '0' && (pick(1) == 'x' || (pick(1) == 'X'))) {
            next();
            next();
            tokenizeHexNumber();
            return;
        }
        while(true) {
            if (current == '.') {
                if (builder.indexOf(".") != -1) throw new RuntimeException("Invalide float number");
            } else if (!Character.isDigit(current)) {
                break;
            }
            builder.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER, builder.toString());
    }

    private static boolean isHexNumber(char current) {
        return Character.isDigit(current) || ("abcdef".indexOf(Character.toLowerCase(current)) != -1);
    }

    private void tokenizeHexNumber() {
        final StringBuilder builder = new StringBuilder();
        char current = pick(0);
        while(isHexNumber(current)) {
            builder.append(current);
            current = next();
        }
        addToken(TokenType.HEX_NUMBER, builder.toString());
    }

    private void tokenizeOperator() {
        char current = pick(0);
        if (current == '/') {
            if (pick(1) == '/') {
                next();
                next();
                tokenizeComment();
                return;
            } else if (pick(1) == '*') {
                next();
                next();
                tokenizeMultilineComment();
                return;
            }
        }
        final StringBuilder builder = new StringBuilder();
        while (true) {
            final String text = builder.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                addToken(OPERATORS.get(text));
                return;
            }
            builder.append(current);
            current = next();
        }
    }

    private void tokenizeComment() {
        char current = pick(0);
        while ("\r\n\0".indexOf(current) == -1) {
            current = next();
        }
    }

    private void tokenizeMultilineComment() {
        char current = pick(0);
        while (true) {
            if (current == '*' && pick(1) == '/') break;
            if (current == '\0') throw new RuntimeException("Missing comment close tag");
            current = next();
        }
        next(); // Skip *
        next(); // Skip /
    }

    private char next() {
        pos++;
        final char result = pick(0);
        if (result == '\n') {
            row++;
            col = 1;
        } else col++;
        return result;
    }

    private char pick(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= length) return '\0';
        return code.charAt(position);
    }

    private void addToken(TokenType type) {
        addToken(type, "");
    }

    private void addToken(TokenType type, String value) {
        tokens.add(new Token(type, value, row, col));
    }
}

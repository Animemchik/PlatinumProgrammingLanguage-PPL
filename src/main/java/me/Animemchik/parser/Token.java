package me.Animemchik.parser;

public final class Token {

    private final TokenType type;
    private final String value;
    private final int row, col;

    public Token(TokenType type, String value, int row, int col) {
        this.type = type;
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + value;
    }
}
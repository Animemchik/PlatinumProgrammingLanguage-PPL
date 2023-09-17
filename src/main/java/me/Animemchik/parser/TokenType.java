package me.Animemchik.parser;

public enum TokenType {

    // Data types
    WORD,           // a
    TEXT,           // "aa"
    CHAR,           // 'a'
    NUMBER,         // 1
    HEX_NUMBER,     // #f = 15

    // keys
    PRINTLN,
    PRINT,
    IF,
    ELSE,
    WHILE,
    FOR,
    DOWHILE,
    BREAK,
    CONTINUE,
    DEF,
    RETURN,
    USE,
    INCLUDE,

    // Binary symbols
    PLUS,           // +
    MINUS,          // -
    MULTI,          // *
    DIVIDE,         // /
    EQ,             // =

    PERCENT,        // %
    PLUSPLUS,       // ++
    MINUSMINUS,     // --
    PLUSEQ,         // +=
    MINUSEQ,        // -=
    MULTIEQ,        // *=
    DIVIDEEQ,       // /=
    PERCENTEQ,      // %=

    // Conditional symbols
    EQEQ,           // ==
    EXCLEQ,         // !=
    LT,             // <
    LTEQ,           // <=
    GT,             // >
    GTEQ,           // >=

    AND,            // and
    AMP,            // &
    OR,             // or
    BAR,            // |
    NOT,            // not
    EXCL,           // ! = not

    QUESTION,       // ?
    COLON,          // :

    CARET,          // ^
    TILDE,          // ~


    LPAREN,         // (
    RPAREN,         // )
    LBRACKET,       // [
    RBRACKET,       // ]
    LBRACE,         // {
    RBRACE,         // }
    COMMA,          // ,
    SEMICOLON,      // ;

    EOF             // End Of File
}

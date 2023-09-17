package me.Animemchik;


import me.Animemchik.parser.Lexer;
import me.Animemchik.parser.Parser;
import me.Animemchik.parser.Token;
import me.Animemchik.parser.ast.Statement;
import me.Animemchik.parser.visitors.AssignValidator;
import me.Animemchik.parser.visitors.FunctionAdder;
import me.Animemchik.parser.visitors.VariablePrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public final class Main {

    public static final int VERSION_MAJOR = 1;
    public static final int VERSION_MINOR = 0;
    public static final int VERSION_PATCH = 2;
    public static final String VERSION = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH;

    private static String[] pllangArgs = new String[0];

    public static String[] getPllangArgs() {
        return pllangArgs;
    }

    public static void main(String[] args) throws IOException {

        final String file = "src/main.pl"; // new Scanner(System.in).next();
        final String code = Files.readString(Paths.get(file).toAbsolutePath());
        final List<Token> tokens = new Lexer(code).tokenize();

        System.out.println("\n\nPART 1: TOKENIZE");
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println("COMPLETE");

        System.out.println("\n\nPART 2: PARSING");
        final Statement parsed = new Parser(tokens).parse();
        System.out.println("COMPLETE");

        System.out.println("\n\nSTATEMENTS");
        System.out.println(parsed.toString());

        System.out.println("\n\nPART 3: OUTPUT");
        parsed.accept(new FunctionAdder());
        // parsed.accept(new VariablePrinter());
        parsed.accept(new AssignValidator());
        parsed.execute();

        System.out.println("\n\nPROCESS ENDED\n");
    }
}
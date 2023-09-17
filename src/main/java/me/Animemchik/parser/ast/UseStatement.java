package me.Animemchik.parser.ast;

import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Value;
import me.Animemchik.modules.Module;

import java.lang.reflect.Method;

public final class UseStatement extends InterruptableNode implements Statement {

    private static final String PACKAGE = "me.Animemchik.modules.%s.%s";
    private static final String INIT_CONSTANTS_METHOD = "initConstants";

    public final Expression expression;

    public UseStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        final Value value = expression.eval();
        switch (value.type()) {
            case ARRAY -> {
                for (Value module : ((ArrayValue) value)) {
                    switch (module.asString()) {
                        case "Math" -> loadModule("math");
                        default -> loadModule(module.asString());
                    }
                }
            }
            case STRING -> {
                switch (value.asString()) {
                    case "Math" -> loadModule("math");
                    default -> loadModule(value.asString());
                }
            }
            default -> throw typeException(value);
        }
    }

    private void loadModule(String name) {
        try {
            final Module module = (Module) Class.forName(String.format(PACKAGE, name, name)).newInstance();
            module.init();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load module " + name, ex);
        }
    }

    public void loadConstants() {
        if (expression instanceof ArrayExpression) {
            ArrayExpression ae = (ArrayExpression) expression;
            for (Expression expr : ae.elements) {
                loadConstants(expr.eval().asString());
            }
        }
        if (expression instanceof ValueExpression) {
            ValueExpression ve = (ValueExpression) expression;
            loadConstants(ve.value.asString());
        }
    }

    private TypeException typeException(Value value) {
        return new TypeException("Array or string required in 'use' statement, " +
                "got " + value.type().typeToString() + " " + value);
    }

    private void loadConstants(String moduleName) {
        try {
            final Class<?> moduleClass = Class.forName(String.format(PACKAGE, moduleName, moduleName));
            final Method method = moduleClass.getMethod(INIT_CONSTANTS_METHOD);
            if (method != null) {
                method.invoke(this);
            }
        } catch (Exception ex) {
            // ignore
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "use " + expression;
    }
}
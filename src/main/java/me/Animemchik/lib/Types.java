package me.Animemchik.lib;

public enum Types {
    OBJECT("object"),
    NUMBER("number"),
    STRING("string"),
    ARRAY("array"),
    MAP("map"),
    FUNCTION("function"),
    CLASS("class");

    public final String TypeName;

    Types(String TypeName) {
        this.TypeName = TypeName;
    }

    public final String typeToString() {
        return this.getName();
    }

    public final String getName() {
        return this.TypeName;
    }
}

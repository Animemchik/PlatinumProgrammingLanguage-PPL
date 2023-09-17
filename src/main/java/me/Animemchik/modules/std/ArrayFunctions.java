package me.Animemchik.modules.std;

import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;
import me.Animemchik.lib.ValueUtils;
import java.io.UnsupportedEncodingException;

public final class ArrayFunctions {
    
    private ArrayFunctions() { }
    
    static StringValue stringFromBytes(Value[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new TypeException("Array expected at first argument");
        }
        final byte[] bytes = ValueUtils.toByteArray((ArrayValue) args[0]);
        final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
        try {
            return new StringValue(new String(bytes, charset));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }
}

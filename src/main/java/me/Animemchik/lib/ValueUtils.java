package me.Animemchik.lib;

import me.Animemchik.exceptions.TypeException;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ValueUtils {

    private ValueUtils() { }

    public static Object toObject(Value val) {
        switch (val.type()) {
            case ARRAY:
                return toObject((ArrayValue) val);
            case NUMBER:
                return val.raw();
            case STRING:
                return val.asString();
            default:
                return JSONObject.NULL;
        }
    }

    public static JSONArray toObject(ArrayValue array) {
        final JSONArray result = new JSONArray();
        for (Value value : array) {
            result.put(toObject(value));
        }
        return result;
    }

    public static Value toValue(Object obj) {
        if (obj instanceof JSONObject) {
            return toValue((JSONObject) obj);
        }
        if (obj instanceof JSONArray) {
            return toValue((JSONArray) obj);
        }
        if (obj instanceof String) {
            return new StringValue((String) obj);
        }
        if (obj instanceof Number) {
            return NumberValue.of(((Number) obj));
        }
        if (obj instanceof Boolean) {
            return NumberValue.fromBoolean((Boolean) obj);
        }
        // NULL or other
        return NumberValue.ZERO;
    }

    public static ArrayValue toValue(JSONArray json) {
        final int length = json.length();
        final ArrayValue result = new ArrayValue(length);
        for (int i = 0; i < length; i++) {
            final Value value = toValue(json.get(i));
            result.set(i, value);
        }
        return result;
    }

    public static Number getNumber(Value value) {
        if (value.type() == Types.NUMBER) return ((NumberValue) value).raw();
        return value.asInteger();
    }

    public static float getFloatNumber(Value value) {
        if (value.type() == Types.NUMBER) return ((NumberValue) value).raw().floatValue();
        return (float) value.asNumber();
    }

    public static byte[] toByteArray(ArrayValue array) {
        final int size = array.size();
        final byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = (byte) array.get(i).asInteger();
        }
        return result;
    }

    public static Function consumeFunction(Value value, int argumentNumber) {
        return consumeFunction(value, " at argument " + (argumentNumber + 1));
    }

    public static Function consumeFunction(Value value, String errorMessage) {
        final Types type = value.type();
        if (type != Types.FUNCTION) {
            throw new TypeException("Function expected" + errorMessage
                    + ", but found " + type.typeToString());
        }
        return ((FunctionValue) value).getValue();
    }
}
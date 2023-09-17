package me.Animemchik.lib;

import java.util.Objects;

public final class StringValue implements Value{

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Object raw() {
        return null;
    }

    @Override
    public int asInteger() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public int length() {
        return value.length();
    }

    @Override
    public double asNumber() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public Types type() {
        return Types.STRING;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final StringValue other = (StringValue) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public int compareTo(Value o) {
        if (o.type() == Types.STRING) {
            return value.compareTo(((StringValue) o).value);
        }
        return asString().compareTo(o.asString());
    }

    @Override
    public String toString() {
        return asString();
    }
}

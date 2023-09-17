package me.Animemchik.modules.std;

import me.Animemchik.Main;
import me.Animemchik.lib.*;
import me.Animemchik.modules.Module;

public final class std implements Module {

    public static void initConstants() {
        MapValue pllang = new MapValue(5);
        pllang.set("PLATFORM", new StringValue("desktop"));
        pllang.set("VERSION", new StringValue(Main.VERSION));
        pllang.set("VERSION_MAJOR", NumberValue.of(Main.VERSION_MAJOR));
        pllang.set("VERSION_MINOR", NumberValue.of(Main.VERSION_MINOR));
        pllang.set("VERSION_PATCH", NumberValue.of(Main.VERSION_PATCH));
        Variables.define("PlLang", pllang);
    }

    @Override
    public void init() {
        initConstants();
        Variables.define("ARGS", ArrayValue.of(Main.getPllangArgs())); // is not constant
        Functions.set("echo", new std_echo());
        Functions.set("readln", new std_input());
        Functions.set("length", new std_length());
        Functions.set("rand", new std_rand());
        Functions.set("time", new std_time());
        Functions.set("sleep", new std_sleep());
        Functions.set("thread", new std_thread());
        Functions.set("sync", new std_sync());
        Functions.set("try", new std_try());
        Functions.set("default", new std_default());

        // Numbers
        Functions.set("toHexString", NumberFunctions::toHexString);

        // String
        Functions.set("getBytes", StringFunctions::getBytes);
        Functions.set("sprintf", new std_sprintf());
        Functions.set("split", new std_split());
        Functions.set("indexOf", new std_indexof());
        Functions.set("lastIndexOf", new std_lastindexof());
        Functions.set("charAt", new std_charat());
        Functions.set("toChar", new std_tochar());
        Functions.set("substring", new std_substring());
        Functions.set("toLowerCase", new std_tolowercase());
        Functions.set("toUpperCase", new std_touppercase());
        Functions.set("trim", new std_trim());
        Functions.set("replace", new std_replace());
        Functions.set("replaceAll", new std_replaceall());
        Functions.set("replaceFirst", new std_replacefirst());
        Functions.set("parseInt", StringFunctions::parseInt);
        Functions.set("parseLong", StringFunctions::parseLong);
        Functions.set("stripMargin", StringFunctions::stripMargin);

        // Arrays and maps
        Functions.set("newarray", new std_newarray());
        Functions.set("join", new std_join());
        Functions.set("sort", new std_sort());
        Functions.set("arrayCombine", new std_arrayCombine());
        Functions.set("arrayKeyExists", new std_arrayKeyExists());
        Functions.set("arrayKeys", new std_arrayKeys());
        Functions.set("arrayValues", new std_arrayValues());
        Functions.set("arraySplice", new std_arraySplice());
        Functions.set("range", new std_range());
        Functions.set("stringFromBytes", ArrayFunctions::stringFromBytes);
    }
}

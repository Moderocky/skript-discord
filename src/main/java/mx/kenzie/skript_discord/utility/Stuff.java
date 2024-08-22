package mx.kenzie.skript_discord.utility;

import java.util.Arrays;

public class Stuff {

    public static <Type> Type[] arrayOf(Type[] initial, Type... extras) {
        if (initial == null || initial.length == 0) return extras;
        final Type[] array = Arrays.copyOf(initial, initial.length + extras.length);
        System.arraycopy(extras, 0, array, initial.length, extras.length);
        return array;
    }

}

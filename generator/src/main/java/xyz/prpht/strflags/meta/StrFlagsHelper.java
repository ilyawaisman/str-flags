package xyz.prpht.strflags.meta;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Helper functions for generated StrFlags conversion helpers.
 */

public final class StrFlagsHelper {

    private static final char EMPTY_CHAR = '-';

    public static boolean fromStringBool(String str, int charIdx, char trueValue) {
        final char c = str.charAt(charIdx);
        if (c == trueValue) {
            return true;
        } else if (c == EMPTY_CHAR) {
            return false;
        }

        throw fromStringException(str, String.format("must have either %s or %s at position %d", trueValue, EMPTY_CHAR, charIdx));
    }

    public static <E extends Enum<E>> E fromStringSingleEnum(String str, int charIdx, E[] all) {
        final char c = str.charAt(charIdx);
        if (c == EMPTY_CHAR) {
            return null;
        }

        for (E e : all) {
            if (toStringSingleEnum(e) == c) {
                return e;
            }
        }

        final List<Character> allSymbols = Arrays.stream(all).map(StrFlagsHelper::toStringSingleEnum).collect(Collectors.toList());
        throw fromStringException(str, String.format("must have one of %s at position %d", allSymbols, charIdx));
    }

    public static <E extends Enum<E>> Set<E> fromStringMultiEnum(String str, int charIdx, E[] all, Class<E> klass) {
        final Set<E> result = EnumSet.noneOf(klass);
        for (int i = 0; i < all.length; i++) {
            E e = all[i];
            if (fromStringBool(str, charIdx + i, toStringSingleEnum(e))) {
                result.add(e);
            }
        }

        return result;
    }

    public static IllegalArgumentException fromStringException(String str, String msg) {
        return new IllegalArgumentException(String.format("Conversion failed for string `%s`: %s", str, msg));
    }

    public static char toStringBoolean(boolean value, char trueValue) {
        return value ? trueValue : EMPTY_CHAR;
    }

    public static <E extends Enum<E>> char toStringSingleEnum(E value) {
        if (value == null)
            return EMPTY_CHAR;

        return Character.toLowerCase(value.name().charAt(0));
    }

    public static <E extends Enum<E>> String toStringMultiEnum(Set<E> value, E[] all) {
        final StringBuilder result = new StringBuilder();
        for (E e : all) {
            result.append(toStringBoolean(value != null && value.contains(e), toStringSingleEnum(e)));
        }

        return result.toString();
    }
}

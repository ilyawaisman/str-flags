package xyz.prpht.strflags.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an interface with this annotation to have its implementation and string conversions generated.
 * Target interface may only contain no parameter methods returning boolean, enum or enum set types.
 * For boolean returning method one flag is generated, symbol is method first letter or if method name starts with "is" or "are" next to this prefix letter is used.
 * For enum returning method one flag is generated which is not genuinely a flag but field that can have *n* value (and not '-' one) where *n* is the number of values in the enum, one for each enum value.
 * For enum set returning method *n* flags are generated where *n* is the number of values in the enum, one for each enum value.
 * Symbols are the first letters of enum values.
 * In the former case all enum values must have different first letters.
 * In the string representation symbols are ordered the same way methods are (and enum elements for multivalue groups).
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface StrFlags {
}

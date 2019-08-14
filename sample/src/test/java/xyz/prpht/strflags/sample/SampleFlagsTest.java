package xyz.prpht.strflags.sample;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

final class SampleFlagsTest {
    @Test
    void backAndForth() {
        assertCorrectBackAndForth("abcxyzx");
        assertCorrectBackAndForth("-b-x-zy");
        assertCorrectBackAndForth("-------");
    }

    @Test
    void bools() {
        final SampleFlags positive = SampleFlagsConvert.fromString("abc----");
        Assertions.assertTrue(positive.alice());
        Assertions.assertTrue(positive.isBob());
        Assertions.assertTrue(positive.areCharlie());

        final SampleFlags negative = SampleFlagsConvert.fromString("-------");
        Assertions.assertFalse(negative.alice());
        Assertions.assertFalse(negative.isBob());
        Assertions.assertFalse(negative.areCharlie());
    }

    @Test
    void singleEnum() {
        final SampleFlags yankee = SampleFlagsConvert.fromString("------y");
        Assertions.assertEquals(Option.Yankee, yankee.singleValue());

        final SampleFlags nil = SampleFlagsConvert.fromString("-------");
        Assertions.assertNull(nil.singleValue());
    }

    @Test
    void multiEnum() {
        final SampleFlags full = SampleFlagsConvert.fromString("---xyz-");
        Assertions.assertEquals(EnumSet.allOf(Option.class), full.multiValue());

        final SampleFlags empty = SampleFlagsConvert.fromString("-------");
        Assertions.assertEquals(EnumSet.noneOf(Option.class), empty.multiValue());

        final SampleFlags some = SampleFlagsConvert.fromString("---x-z-");
        Assertions.assertEquals(EnumSet.of(Option.XRay, Option.Zulu), some.multiValue());
    }

    @Test
    void objToString() {
        final String str = "a-cx--y";
        final SampleFlags sampleFlags = SampleFlagsConvert.fromString(str);
        Assertions.assertEquals(String.format("SampleFlags[%s]", str), sampleFlags.toString());
    }

    @Test
    void tooShort() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SampleFlagsConvert.fromString("------"));
    }

    @Test
    void tooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SampleFlagsConvert.fromString("--------"));
    }

    @Test
    void incorrectBoolSymbol() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SampleFlagsConvert.fromString("-i-----"));
    }

    @Test
    void incorrectSingleEnumSymbol() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SampleFlagsConvert.fromString("------w"));
    }

    @Test
    void incorrectMultiEnumSymbol() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SampleFlagsConvert.fromString("-----x-"));
    }

    private void assertCorrectBackAndForth(@NotNull String in) {
        final SampleFlags flags = SampleFlagsConvert.fromString(in);
        final String out = SampleFlagsConvert.toString(flags);
        Assertions.assertEquals(in, out);
    }
}

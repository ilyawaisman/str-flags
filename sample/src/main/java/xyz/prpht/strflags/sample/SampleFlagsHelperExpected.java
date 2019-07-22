package xyz.prpht.strflags.sample;

import java.util.Set;

import static xyz.prpht.strflags.meta.StrFlagsHelper.*;

@SuppressWarnings({"SameParameterValue"})
public final class SampleFlagsHelperExpected {

    public static SampleFlags fromString(String str) {
        if (str.length() != 7) {
            throw fromStringException(str, "length must be 7");
        }

        return new __SampleFlagsImpl(
                fromStringBool(str, 0, 'a'),
                fromStringBool(str, 1, 'b'),
                fromStringBool(str, 2, 'c'),
                fromStringMultiEnum(str, 3, Option.values(), Option.class),
                fromStringSingleEnum(str, 6, Option.values())
        );
    }

    public static String toString(SampleFlags obj) {
        return ""
                + toStringBoolean(obj.alice(), 'a')
                + toStringBoolean(obj.isBob(), 'b')
                + toStringBoolean(obj.areCharlie(), 'c')
                + toStringMultiEnum(obj.multiValue(), Option.values())
                + toStringSingleEnum(obj.singleValue())
                ;
    }

    private static class __SampleFlagsImpl implements SampleFlags {


        private final boolean alice;
        private final boolean isBob;
        private final boolean areCharlie;
        private final Set<Option> multiValue;
        private final Option singleValue;

        __SampleFlagsImpl(
                boolean alice,
                boolean isBob,
                boolean areCharlie,
                Set<Option> multiValue,
                Option singleValue
        ) {
            this.alice = alice;
            this.isBob = isBob;
            this.areCharlie = areCharlie;
            this.multiValue = multiValue;
            this.singleValue = singleValue;
        }

        @Override
        public boolean alice() {
            return alice;
        }

        @Override
        public boolean isBob() {
            return isBob;
        }

        @Override
        public boolean areCharlie() {
            return areCharlie;
        }

        @Override
        public Set<Option> multiValue() {
            return multiValue;
        }

        @Override
        public Option singleValue() {
            return singleValue;
        }
    }
}

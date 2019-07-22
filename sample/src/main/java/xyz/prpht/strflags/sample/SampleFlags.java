package xyz.prpht.strflags.sample;

import xyz.prpht.strflags.meta.StrFlags;

import java.util.Set;

@StrFlags
public interface SampleFlags {

    boolean alice();

    boolean isBob();

    boolean areCharlie();

    Set<Option> multiValue();

    Option singleValue();
}

package xyz.prpht.strflags.generator;

import org.jetbrains.annotations.NotNull;

final class ModelMemberEnumMulti extends ModelMember {
    private final int length;
    private final String type;

    ModelMemberEnumMulti(@NotNull String name, int idx, int length, String type) {
        super(name, idx);
        this.length = length;
        this.type = type;
    }

    @NotNull
    @Override
    public String fromStringExpr(@NotNull String paramName) {
        return String.format("fromStringMultiEnum(%s, %d, %s.values(), %s.class)", paramName, idx(), type, type);
    }

    @NotNull
    @Override
    public String toStringExpr(@NotNull String paramName) {
        return String.format("toStringMultiEnum(%s.%s(), %s.values())", paramName, getName(), type);
    }

    @NotNull
    @Override
    public String getType() {
        return String.format("Set<%s>", type);
    }

    @Override
    protected int length() {
        return length;
    }
}

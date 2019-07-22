package xyz.prpht.strflags.generator;

import org.jetbrains.annotations.NotNull;

final class ModelMemberEnumSingle extends ModelMember {
    private final String type;

    ModelMemberEnumSingle(@NotNull String name, int idx, String type) {
        super(name, idx);
        this.type = type;
    }

    @NotNull
    @Override
    public String fromStringExpr(@NotNull String paramName) {
        return String.format("fromStringSingleEnum(%s, %d, %s.values())", paramName, idx(), type);
    }

    @NotNull
    @Override
    public String toStringExpr(@NotNull String paramName) {
        return String.format("toStringSingleEnum(%s.%s())", paramName, getName());
    }

    @NotNull
    @Override
    public String getType() {
        return type;
    }

    @Override
    protected int length() {
        return 1;
    }
}

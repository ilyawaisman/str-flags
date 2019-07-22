package xyz.prpht.strflags.generator;

import org.jetbrains.annotations.NotNull;

final class ModelMemberBoolean extends ModelMember {
    private final char symbol;

    ModelMemberBoolean(@NotNull String name, int idx) {
        super(name, idx);
        symbol = booleanChar(name);
    }

    @NotNull
    @Override
    public String fromStringExpr(@NotNull String paramName) {
        return String.format("fromStringBool(%s, %d, '%s')", paramName, idx(), symbol);
    }

    @NotNull
    @Override
    public String toStringExpr(@NotNull String paramName) {
        return String.format("toStringBoolean(%s.%s(), '%s')", paramName, getName(), symbol);
    }

    @NotNull
    @Override
    public String getType() {
        return "boolean";
    }

    @Override
    protected int length() {
        return 1;
    }

    private static char booleanChar(@NotNull String name) {
        final int idx;

        if (name.startsWith("is")) {
            idx = 2;
        } else if (name.startsWith("are")) {
            idx = 3;
        } else {
            idx = 0;
        }

        return Character.toLowerCase(name.charAt(idx));
    }

}

package xyz.prpht.strflags.generator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ModelMember {
    @NotNull
    private final String name;
    private final int idx;

    ModelMember(@NotNull String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    @NotNull
    public abstract String fromStringExpr(@NotNull String paramName);

    @NotNull
    public abstract String toStringExpr(@NotNull String paramName);

    @NotNull
    public final String getName() {
        return name;
    }

    @NotNull
    public abstract String getType();

    protected abstract int length();

    final int idx() {
        return idx;
    }

    static ModelMember create(@NotNull ExecutableElement element, int idx) {
        final String name = element.getSimpleName().toString();

        if (!element.getParameters().isEmpty())
            throw new IllegalArgumentException(String.format("Method %s has parameters", name));

        final TypeMirror returnType = element.getReturnType();
        final TypeKind kind = returnType.getKind();
        if (kind == TypeKind.BOOLEAN) {
            return new ModelMemberBoolean(name, idx);
        }

        if (kind == TypeKind.DECLARED) {
            final TypeElement asEnum = asEnum(returnType);
            if (asEnum != null) {
                checkEnumElementsLetterDiffers(asEnum);
                return new ModelMemberEnumSingle(name, idx, returnType.toString());
            }

            final TypeElement enumElement = asEnumInSet(returnType);
            if (enumElement != null) {
                return new ModelMemberEnumMulti(name, idx, enumElements(enumElement).size(), enumElement.getQualifiedName().toString());
            }
        }

        throw new IllegalArgumentException(String.format("Method %s return type %s is neither boolean nor Enum or Set<Enum>}", element, returnType));
    }

    @Nullable
    private static TypeElement asEnum(@NotNull TypeMirror type) {
        final TypeElement typeElement = (TypeElement) ((DeclaredType) type).asElement();
        final TypeMirror superclass = typeElement.getSuperclass();
        if (!(superclass instanceof DeclaredType)) {
            return null;
        }

        final Element element = ((DeclaredType) superclass).asElement();
        final PackageElement packageElement = (PackageElement) element.getEnclosingElement();
        if (!packageElement.getQualifiedName().contentEquals("java.lang")) {
            return null;
        }

        return element.getSimpleName().contentEquals("Enum") ? typeElement : null;
    }

    @Nullable
    private static TypeElement asEnumInSet(@NotNull TypeMirror type) {
        final TypeElement typeElement = (TypeElement) ((DeclaredType) type).asElement();
        if (!typeElement.getQualifiedName().contentEquals("java.util.Set")) {
            return null;
        }

        final List<? extends TypeMirror> typeArguments = ((DeclaredType) type).getTypeArguments();
        if (typeArguments.size() != 1) {
            return null;
        }

        return asEnum(typeArguments.get(0));
    }

    @NotNull
    private static List<? extends Element> enumElements(@NotNull TypeElement enumElement) {
        return enumElement.getEnclosedElements().stream()
                .filter((e) -> e.getKind() == ElementKind.ENUM_CONSTANT)
                .collect(Collectors.toList());
    }

    private static void checkEnumElementsLetterDiffers(@NotNull TypeElement enumElement) {
        final List<? extends Element> elements = enumElements(enumElement);
        final Set<Character> letters = elements.stream().map((e) -> Character.toLowerCase(e.getSimpleName().charAt(0))).collect(Collectors.toSet());
        if (elements.size() != letters.size()) {
            throw new IllegalArgumentException(String.format("Cannot generate string conversions for method returning enum %s; it has elements starting with same letters %s", enumElement, elements));
        }
    }
}

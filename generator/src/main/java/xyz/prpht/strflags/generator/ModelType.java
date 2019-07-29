package xyz.prpht.strflags.generator;

import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public final class ModelType {
    @NotNull
    private final String path;
    @NotNull
    private final String name;
    @NotNull
    private final String pack;
    @NotNull
    private final List<ModelMember> members;
    private final int length;

    private ModelType(@NotNull String path, @NotNull String name, @NotNull String pack, @NotNull List<ModelMember> members, int length) {
        this.path = path;
        this.name = name;
        this.pack = pack;
        this.members = members;
        this.length = length;
    }

    static ModelType create(@NotNull TypeElement element) {
        final String pack = ((PackageElement) element.getEnclosingElement()).getQualifiedName().toString();
        final String name = element.getSimpleName().toString();
        final List<? extends TypeMirror> interfaces = element.getInterfaces();
        if (!interfaces.isEmpty())
            throw new IllegalArgumentException(String.format("Type %s has supertype(s): %s; this is not supprted", name, interfaces));

        final String path = String.format("%s.%sConvert", pack, name);
        int idx = 0;
        final ArrayList<ModelMember> members = new ArrayList<>();
        for (Element memberElement : element.getEnclosedElements()) {
            final ModelMember result = ModelMember.create((ExecutableElement) memberElement, idx);
            idx += result.length();
            members.add(result);
        }
        return new ModelType(path, name, pack, members, idx);
    }

    @NotNull
    String getPath() {
        return path;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPack() {
        return pack;
    }

    @NotNull
    public List<ModelMember> getMembers() {
        return members;
    }

    public int getLength() {
        return length;
    }
}

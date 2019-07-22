package xyz.prpht.strflags.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.jetbrains.annotations.NotNull;
import xyz.prpht.strflags.meta.StrFlags;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

public final class Generator extends AbstractProcessor {

    private final Configuration freemarker = new Configuration(Configuration.VERSION_2_3_28);
    private final Template template;

    public Generator() throws IOException {
        configureFreemarker();
        template = freemarker.getTemplate("StrFlagsConvert.java.ftl");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(StrFlags.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(@NotNull Set<? extends TypeElement> annotations, @NotNull RoundEnvironment roundEnv) {
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(StrFlags.class);
        for (Element element : elements) {
            generate(element);
        }

        return true;
    }

    private void generate(@NotNull Element element) {
        if (element.getKind() != ElementKind.INTERFACE) {
            throw new IllegalArgumentException(String.format("%s is not an interface, only support @StrFlags for interfaces", element));
        }

        if (!(element instanceof TypeElement)) {
            throw new IllegalStateException(String.format("%s must be TypeElement", element));
        }

        log(String.format("generating for %s", ((TypeElement) element).getQualifiedName()));

        final ModelType model = ModelType.create(element);

        try (Writer writer = processingEnv.getFiler().createSourceFile(model.getPath()).openWriter()) {
            template.process(model, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(String.format("Failed generation for %s", element), e);
        }
    }

    private void log(@NotNull String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("str-flags: %s", msg));
    }

    private void configureFreemarker() {
        freemarker.setClassForTemplateLoading(getClass(), "/templates");
        freemarker.setDefaultEncoding("UTF-8");
        freemarker.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
}

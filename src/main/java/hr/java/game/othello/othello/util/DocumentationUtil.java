package hr.java.game.othello.othello.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DocumentationUtil {
    public static void generateDocumentation() {
        StringBuilder documentationGenerator = new StringBuilder();

        String path = "src/main/java";

        try {
            List<Path> classNameList =  Files.walk(Paths.get(path))
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .filter(p -> !p.getFileName().toString().equals("module-info.java"))
                    .toList();

            for(Path classPath : classNameList) {

                int indexOfHr = classPath.toString().indexOf("hr");
                String fqcn = classPath.toString().substring(indexOfHr);
                fqcn = fqcn.replace('\\', '.');
                fqcn = fqcn.substring(0, fqcn.length() - 5);

                Class<?> documentationClass = Class.forName(fqcn);

                String classModifiers = Modifier.toString(documentationClass.getModifiers());

                documentationGenerator.append("<h2>"
                        + classModifiers + " "
                        + fqcn
                        + "</h2>\n");

                documentationGenerator.append("<h3>List of fields:</h3>\n");

                Field[] classVariables = documentationClass.getFields();
                for(Field field : classVariables) {
                    String modifiers = Modifier.toString(field.getModifiers());
                    documentationGenerator.append("<h4>"
                            + modifiers + " "
                            + field.getType().getName() + " "
                            + field.getName()
                            + "</h4>\n");
                }

                documentationGenerator.append("<h3>List of constructors:</h3>\n");

                Constructor[] classConstructors = documentationClass.getConstructors();
                for(Constructor constructor : classConstructors) {
                    String modifiers = Modifier.toString(constructor.getModifiers());
                    documentationGenerator.append("<h4>"
                    + modifiers + " "
                    + constructor.getName()
                    + "</h4>\n");
                }

                documentationGenerator.append("<h3>List of methods:</h3>\n");

                Method[] classMethods = documentationClass.getDeclaredMethods();
                for (Method method : classMethods) {
                    String modifiers = Modifier.toString(method.getModifiers());
                    documentationGenerator.append("<h4>"
                    + modifiers + " "
                    + method.getReturnType() + " "
                    + method.getName()
                    + "</h4>\n");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                <title>Documentation</title>
                </head>
                <body>
                <h1>List of classes:</h1>
                """
                + documentationGenerator +
                """
                </body>
                </html>
                """;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("documentation/doc.html"))) {
            writer.write(html);
            DialogUtils.showActionSuccess("Documentation was successfully generated!");
        } catch(IOException ex) {
            DialogUtils.showActionFailure("Failed to generate documentation!");
        }
    }
}

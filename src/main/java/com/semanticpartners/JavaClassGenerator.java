package com.semanticpartners;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * @author Jeremy Debattista
 * file JavaClassGenerator
 * package com.semanticpartners
 **/
public class JavaClassGenerator {

    public static void generateJavaClass(Set<Resource> nodeShapes, Set<Property> propertyShapes, String namespace, String packageName, String className, String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("package " + packageName + ";\n\n");
            writer.write("import org.apache.jena.rdf.model.Property;\n");
            writer.write("import org.apache.jena.rdf.model.Resource;\n");
            writer.write("import org.apache.jena.rdf.model.Model;\n");
            writer.write("import org.apache.jena.rdf.model.ModelFactory;\n\n");
            writer.write("public class " + className + " {\n\n");

            // Write namespace constant
            writer.write("    public static final String NS = \"" + namespace + "\";\n\n");
            writer.write("    private static final Model M_MODEL = ModelFactory.createDefaultModel();\n\n");

            writer.write("    public static String getURI() { return NS; } \n");
            writer.write("    public static final Resource NAMESPACE = M_MODEL.createResource( NS ); \n\n");


            writer.write("    // Classes\n");
            for (Resource resource : nodeShapes) {
                writer.write("    public static final Resource " + resource.getLocalName() + " = M_MODEL.createResource(NS + \"" + resource.getLocalName() + "\");\n\n");
            }
            writer.write("\n");

            writer.write("    // Properties\n");
            for (Property property : propertyShapes) {
                writer.write("    public static final Property " + property.getLocalName() + " = M_MODEL.createProperty(NS, \"" + property.getLocalName() + "\");\n\n");
            }

            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

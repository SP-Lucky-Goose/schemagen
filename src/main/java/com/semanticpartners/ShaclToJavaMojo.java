package com.semanticpartners;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFLanguages;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

/**
 * @author Jeremy Debattista
 * file ShaclToJavaMojo
 * package com.semanticpartners
 **/
@Mojo(name = "generate-java", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ShaclToJavaMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(property = "shapesFolder", required = true)
    private File shapesFolder;

    @Parameter(property = "outputDirectory", defaultValue = "${project.build.directory}/generated-sources/shapes")
    private File outputDirectory;

    @Parameter(property = "packageName", required = true)
    private String packageName;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting SHACL to Java generation...");

        if (!this.shapesFolder.exists()) {
            throw new MojoExecutionException("Shapes folder does not exist: " + this.shapesFolder.getPath());
        }
        
        if (!this.shapesFolder.isDirectory()){
            throw new MojoExecutionException("Shapes folder parameter should point to a folder with shapes files.");
        }

        try {

            File packageDir = new File(this.outputDirectory, packageName.replace('.', '/'));
            if (!packageDir.exists()) {
                Files.createDirectories(packageDir.toPath());
            }

            for (File file : this.shapesFolder.listFiles()){
                if (file.isHidden()) return;
                getLog().info("File being processed: " + file.getPath());

                if (RDFLanguages.filenameToLang(file.getName()) != null) {
                    String filename = file.getName().substring(0, file.getName().lastIndexOf('.'));
                    String outputPath = Paths.get(packageDir.getPath(), filename.toUpperCase(Locale.ROOT).replace("-","") + ".java").toString();


                    Set<Resource> nodeShapes = ShaclParser.getNodeShapes(file.getPath());
                    Set<Property> propertyShapes = ShaclParser.getPropertyShapes(file.getPath());
                    String namespace = ShaclParser.getNamespace(file.getPath(), filename);


                    JavaClassGenerator.generateJavaClass(nodeShapes, propertyShapes, namespace, this.packageName, filename.toUpperCase(Locale.ROOT).replace("-",""), outputPath);
                    project.addCompileSourceRoot(this.outputDirectory.getPath());

                    getLog().info("Generated Java file at: " + outputPath);
                } else {
                    getLog().info("Skipping file: " + file.getPath());
                }
            };

        } catch (IOException e) {
            throw new MojoExecutionException("Error creating output directory", e);
        }
    }
}

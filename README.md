# schemagen
Generates Java Classes from well-formed SHACL Shapes in a maven build.

### How to use
In maven add the following and update the configuration parameters.
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.semanticpartners</groupId>
            <artifactId>schemagen</artifactId>
            <version>1.0</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate-java</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <shapesFolder>/path/to/shapes/files</shapesFolder>
                <outputDirectory>/path/to/src/main/java/</outputDirectory>
                <packageName>com.example.vocabularies</packageName>
            </configuration>
        </plugin>
    </plugins>
</build>
```
# schemagen
Generates Java Classes from well-formed SHACL Shapes in a maven build.

### How to use
In maven add the following and update the configuration parameters.
```xml
<pluginRepositories>
    <pluginRepository>
        <id>github</id>
        <url>https://maven.pkg.github.com/SP-Lucky-Goose/schemagen</url>
    </pluginRepository>
</pluginRepositories>

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

In settings.xml (`~/m2/settings.xml) you need to have the following:
```xml
<servers>
    <server>
        <id>github</id>
        <username>YOUR_GITHUB_USERNAME</username>
        <password>YOUR_GITHUB_PERSONAL_ACCESS_TOKEN</password>
    </server>
</servers>
```

Personal access tokens can be generated from here: https://github.com/settings/tokens
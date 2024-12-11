package com.semanticpartners;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jeremy Debattista
 * file ShaclParser
 * package com.semanticpartners
 **/
public class ShaclParser {

    public static Set<Resource> getNodeShapes(String filePath) {
        Set<Resource> nodeShapes = new HashSet<>();

        Model model = RDFDataMgr.loadModel(filePath);
        ResIterator iter = model.listResourcesWithProperty(RDF.type, SH.NodeShape);

        while (iter.hasNext()) {
            Resource shape = iter.next();
            if (shape.hasProperty(SH.targetClass)) {
                Resource targetClass = shape.getPropertyResourceValue(SH.targetClass);
                nodeShapes.add(targetClass);
            } else if (shape.hasProperty(RDF.type, RDFS.Class) || shape.hasProperty(RDF.type, OWL.Class)) {
                nodeShapes.add(shape);
            }
        }

        return nodeShapes;
    }

    public static Set<Property> getPropertyShapes(String filePath) {
        Set<Property> propertyShapes = new HashSet<>();

        Model model = RDFDataMgr.loadModel(filePath);
        ResIterator iter = model.listResourcesWithProperty(RDF.type, SH.PropertyShape);

        while (iter.hasNext()) {
            Resource shape = iter.next();
            if (shape.hasProperty(SH.path)) {
                try {
                    Property path = model.createProperty(shape.getPropertyResourceValue(SH.path).getURI());
                    propertyShapes.add(path);
                } catch (Exception e){
                    System.out.println("Shape not processed due to missing path: " + shape);
                }
            }
        }

        return propertyShapes;
    }

    public static String getNamespace(String filePath, String prefix) {
        Model model = RDFDataMgr.loadModel(filePath);
        return model.getNsPrefixURI(prefix);
    }

}

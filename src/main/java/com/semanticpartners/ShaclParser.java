package com.semanticpartners;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.vocabulary.SHACLM;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import java.util.logging.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jeremy Debattista
 * file ShaclParser
 * package com.semanticpartners
 **/
public class ShaclParser {

    static final Logger logger = Logger.getLogger(ShaclParser.class.getName());

    private ShaclParser() {}

    public static Set<Resource> getNodeShapes(String filePath) {
        Set<Resource> nodeShapes = new HashSet<>();

        Model model = RDFDataMgr.loadModel(filePath);
        ResIterator iter = model.listResourcesWithProperty(RDF.type, SHACLM.NodeShape);
        while (iter.hasNext()) {
            Resource shape = iter.next();
            if (shape.hasProperty(SHACLM.targetClass)) {
                Resource targetClass = shape.getPropertyResourceValue(SHACLM.targetClass);
                nodeShapes.add(targetClass);
            } else if (shape.hasProperty(RDF.type, RDFS.Class) || shape.hasProperty(RDF.type, OWL2.Class)) {
                nodeShapes.add(shape);
            }
        }

        return nodeShapes;
    }

    public static Set<Property> getPropertyShapes(String filePath) {
        Set<Property> propertyShapes = new HashSet<>();

        Model model = RDFDataMgr.loadModel(filePath);

        Set<Resource> psWithType =  model.listResourcesWithProperty(RDF.type, SHACLM.PropertyShape).toSet();
        Set<Resource> psWithoutType =  model.listObjectsOfProperty(null, SHACLM.property).mapWith(RDFNode::asResource).toSet();

        Set<Resource> allShapes = new HashSet<>();
        allShapes.addAll(psWithType);
        allShapes.addAll(psWithoutType);

        allShapes.forEach(shape -> {
            if (shape.hasProperty(SHACLM.path)) {
                    Property path = model.createProperty(shape.getPropertyResourceValue(SHACLM.path).getURI());
                    propertyShapes.add(path);
            } else{
                logger.info("Shape not processed due to missing path: " + shape);
            }
        });

        return propertyShapes;
    }

    public static String getNamespace(String filePath, String prefix) {
        Model model = RDFDataMgr.loadModel(filePath);
        return model.getNsPrefixURI(prefix);
    }

}

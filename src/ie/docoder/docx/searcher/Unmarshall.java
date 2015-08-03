/**
 * 	Copyright 2015 Derek Keogh
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 */
package ie.docoder.docx.searcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import ie.docoder.docx.searcher.Sequences;

import org.xml.sax.SAXException;

public class Unmarshall {

    /** Path to XML schema */
    private final static String SCHEMA = "resources/SequenceMatch.xsd";
    private final static String EXAMPLE_XML = "resources/Example.xml";
    
    /** XML document used to define the searches */
    private String FILE;

    public Unmarshall(String sequenceFile) {
    	FILE = sequenceFile;
    }
    
    public Sequences UnmarshallTheDocument() throws Exception, NullPointerException, JAXBException, SAXException {
        
    	Sequences sec = null;
        try {
            File xmlFile = new File(FILE);
            if(!xmlFile.exists()) {
                String errMsg = "Did not find xml : " + FILE + " at the specified location:";
                throw new Exception(errMsg);
            }

            ClassLoader classLoader = getClass().getClassLoader();
            File xsdFile = new File(classLoader.getResource(SCHEMA).getFile());
            if(!xsdFile.exists()) {
                String errMsg = "Did not find xsd : " + SCHEMA + " at the specified location:";
                throw new Exception(errMsg);
            }

            SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema;
            schema = sf.newSchema(xsdFile);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Sequences.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new DocumentEventHandler());
            sec = (Sequences) unmarshaller.unmarshal(xmlFile);
            
        } catch (NullPointerException  ex) {
            throw new Exception(ex);     
        } catch (JAXBException ex) {
            throw new Exception(ex);     
        } catch (SAXException ex) {
            throw new Exception(ex);     
        }
        return sec;
    }
    
    public class DocumentEventHandler implements ValidationEventHandler {
        public boolean handleEvent(ValidationEvent event) {
            
           String eventMsg = "\nEvent: Severity:  " + event.getSeverity()
                   + ", Message:  " + event.getMessage()
                   + ", Linked Exception:  " + event.getLinkedException()
                   + ", LOCATOR   Line Number:  " + event.getLocator().getLineNumber()
                   + ", Column Number:  " + event.getLocator().getColumnNumber()
                   + ", Offset:  " + event.getLocator().getOffset()
                   + ", Object:  " + event.getLocator().getObject()
                   + ", Node:  " + event.getLocator().getNode()
                   + ", Url:  " + event.getLocator().getURL();
           System.out.println("DocumentEventHandler : " + eventMsg);

           return false;
        }
     }
}

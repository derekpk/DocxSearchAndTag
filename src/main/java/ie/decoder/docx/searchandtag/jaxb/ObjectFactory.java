//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.06 at 09:11:19 AM IST 
//


package ie.decoder.docx.searchandtag.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ie.decoder.docx.searchandtag.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ie.decoder.docx.searchandtag.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sequences }
     * 
     */
    public Sequences createSequences() {
        return new Sequences();
    }

    /**
     * Create an instance of {@link Sequences.Sequence }
     * 
     */
    public Sequences.Sequence createSequencesSequence() {
        return new Sequences.Sequence();
    }

    /**
     * Create an instance of {@link Sequences.Sequence.Piece }
     * 
     */
    public Sequences.Sequence.Piece createSequencesSequencePiece() {
        return new Sequences.Sequence.Piece();
    }

    /**
     * Create an instance of {@link Sequences.Sequence.Piece.Bit }
     * 
     */
    public Sequences.Sequence.Piece.Bit createSequencesSequencePieceBit() {
        return new Sequences.Sequence.Piece.Bit();
    }

    /**
     * Create an instance of {@link Sequences.Sequence.Piece.Bit.Multi }
     * 
     */
    public Sequences.Sequence.Piece.Bit.Multi createSequencesSequencePieceBitMulti() {
        return new Sequences.Sequence.Piece.Bit.Multi();
    }

    /**
     * Create an instance of {@link Sequences.Sequence.Piece.Bit.Exact }
     * 
     */
    public Sequences.Sequence.Piece.Bit.Exact createSequencesSequencePieceBitExact() {
        return new Sequences.Sequence.Piece.Bit.Exact();
    }

}

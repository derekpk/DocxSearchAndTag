//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.06 at 09:11:19 AM IST 
//


package ie.decoder.docx.searchandtag.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAP_ALPHA"/>
 *     &lt;enumeration value="LOW_ALPHA"/>
 *     &lt;enumeration value="ALPHA"/>
 *     &lt;enumeration value="NUMERIC"/>
 *     &lt;enumeration value="FULL_STOP"/>
 *     &lt;enumeration value="COMMA"/>
 *     &lt;enumeration value="SPACE"/>
 *     &lt;enumeration value="OPEN_TAG"/>
 *     &lt;enumeration value="CLOSE_TAG_SLASH"/>
 *     &lt;enumeration value="CLOSE_TAG"/>
 *     &lt;enumeration value="BOLD"/>
 *     &lt;enumeration value="ITALIC"/>
 *     &lt;enumeration value="OPEN_BRACKET"/>
 *     &lt;enumeration value="CLOSE_BRACKET"/>
 *     &lt;enumeration value="OPEN_QUOTE"/>
 *     &lt;enumeration value="CLOSE_QUOTE"/>
 *     &lt;enumeration value="COLON"/>
 *     &lt;enumeration value="SEMI_COLON"/>
 *     &lt;enumeration value="LAST_CHAR"/>
 *     &lt;enumeration value="MULTI"/>
 *     &lt;enumeration value="EXACT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BitType")
@XmlEnum
public enum BitType {

    CAP_ALPHA,
    LOW_ALPHA,
    ALPHA,
    NUMERIC,
    FULL_STOP,
    COMMA,
    SPACE,
    OPEN_TAG,
    CLOSE_TAG_SLASH,
    CLOSE_TAG,
    BOLD,
    ITALIC,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_QUOTE,
    CLOSE_QUOTE,
    COLON,
    SEMI_COLON,
    LAST_CHAR,
    MULTI,
    EXACT;

    public String value() {
        return name();
    }

    public static BitType fromValue(String v) {
        return valueOf(v);
    }

}
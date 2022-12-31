package eutil.file;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader extends DefaultHandler {
	
	private static SAXParserFactory factory = SAXParserFactory.newInstance();
	private StringBuilder value = new StringBuilder();
	
	public static XMLReader read(File f) {
		try {
			SAXParser saxParser = factory.newSAXParser();
			XMLReader handler = new XMLReader();
			saxParser.parse(f, handler);
			return handler;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void startDocument() {
		System.out.println("Start");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// reset the tag value
		value.setLength(0);
		
		System.out.printf("Start Element : %s%n", qName);
		
		if (qName.equalsIgnoreCase("staff")) {
			// get tag's attribute by name
			String id = attributes.getValue("id");
			System.out.printf("Staff id : %s%n", id);
		}
		
		if (qName.equalsIgnoreCase("salary")) {
			// get tag's attribute by index, 0 = first attribute
			String currency = attributes.getValue(0);
			System.out.printf("Currency :%s%n", currency);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
		System.out.printf("End Element : %s%n", qName);
		System.out.println(qName + " : " + value);
	}
	
	// http://www.saxproject.org/apidoc/org/xml/sax/ContentHandler.html#characters%28char%5B%5D,%20int,%20int%29
	// SAX parsers may return all contiguous character data in a single chunk,
	// or they may split it into several chunks
	@Override
	public void characters(char ch[], int start, int length) {
		
		// The characters() method can be called multiple times for a single text node.
		// Some values may missing if assign to a new string
		
		// avoid doing this
		// value = new String(ch, start, length);
		
		// better append it, works for single or multiple calls
		value.append(ch, start, length);
	}
	
}

package utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    boolean description;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("command")) {
            String name;
            String usage;
            if((name = attributes.getValue("name")) != null){
                System.out.println("Command: "+name);
            }else{
                System.out.println("Command: "+"NO NAME");
            }
            if((usage = attributes.getValue("usage")) != null){
                System.out.print("\tUsage: ");System.out.println(usage);
            }else{
                System.out.print("\tUsage: ");System.out.println("NONE");
            }

        }

        if(qName.equals("description")){
            description = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(description){
            System.out.println("\tDescription: "+new String(ch,start,length));
            System.out.println("");
            description = false;
        }
    }
}

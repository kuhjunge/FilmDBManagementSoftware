/* Ein bisschen Beschreibung
 * http://www.sjmp.de/java/xml-datei-von-fremden-server-lesen-urlconnection/
 * http://www.omdbapi.com/
 * http://www.omdbapi.com/?r=xml&i=" + imdbId
 * http://www.omdbapi.com/?r=xml&r=" + title
 * */

package sort;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class xmlConnect {
	public ArrayList<String> getXml(String name) {
		ArrayList<String> ret = new ArrayList<String>();
        try {
        	String var;
        	if (name != null) var = "s="+name.replace(" ", "%20");
        	else return null;
        	//String[] ret = null;
        	DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder;
            dbuilder = dbc.newDocumentBuilder();
            Document doc = dbuilder.parse(new URL("http://www.omdbapi.com/?r=xml&" + var).openStream());
            NodeList nl = doc.getElementsByTagName("Movie");
            for(int i = 0 ; i < nl.getLength(); i++){
                //if(i == row){                   
                    Element e = (Element)nl.item(i);
                    ret.add(e.getAttribute("imdbID"));
                    //System.out.println(e.getAttribute("imdbID"));
                //}
            }
        } catch (ParserConfigurationException e) { 
        	JOptionPane.showMessageDialog(null, "Fehler XML 1");
            e.printStackTrace();
        } catch (SAXException e) { 
        	JOptionPane.showMessageDialog(null, "Fehler XML 2");
            e.printStackTrace();
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Fehler XML 3");
            e.printStackTrace();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Fehler XML 4");
            e.printStackTrace();
        }
   	 return ret;
    }
	
	public String[] getMovieObject(String id) {
		String[] erg = new String[14];
    	DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder;
        try {
            dbuilder = dbc.newDocumentBuilder();
            Document doc = dbuilder.parse(new URL("http://www.omdbapi.com/?r=xml&i=" + id).openStream());
            NodeList nl = doc.getElementsByTagName("movie");
            for(int i = 0 ; i < nl.getLength(); i++){                  
                    Element e = (Element)nl.item(i);
                    erg[0] = e.getAttribute("title");
                    erg[1] = e.getAttribute("year");
                    erg[2] = e.getAttribute("rated");
                    erg[3] = e.getAttribute("released");
                    erg[4] = e.getAttribute("runtime");
                    erg[5] = e.getAttribute("genre");
                    erg[6] = e.getAttribute("director");
                    erg[7] = e.getAttribute("writer");
                    erg[8] = e.getAttribute("actors");
                    erg[9] = e.getAttribute("plot");
                    erg[10] = e.getAttribute("poster");
                    erg[11] = e.getAttribute("imdbRating");
                    erg[12] = e.getAttribute("imdbVotes");
                    erg[13] = e.getAttribute("imdbID");
            }
        } catch (ParserConfigurationException e) {          
            e.printStackTrace();
        } catch (SAXException e) {          
            e.printStackTrace();
        } catch (IOException e) {           
            e.printStackTrace();
        }
   	 return erg;
    }
	
	public String readXML(String xml) {
		ArrayList<String> rolev = new ArrayList<String>();
		String id = null;
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();
            
            id = getTextValue(id, doc, "id");
            if (id != null) {
                if (!id.isEmpty())
                    rolev.add(id);
            }
            /*
            role2 = getTextValue(role2, doc, "role2");
            if (role2 != null) {
                if (!role2.isEmpty())
                    rolev.add(role2);
            }
            role3 = getTextValue(role3, doc, "role3");
            if (role3 != null) {
                if (!role3.isEmpty())
                    rolev.add(role3);
            }
            role4 = getTextValue(role4, doc, "role4");
            if ( role4 != null) {
                if (!role4.isEmpty())
                    rolev.add(role4);
            }
            */
            return id;
        } catch (Exception e) {           
            e.printStackTrace();
        }
    /*    } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }*/

        return null;
    }
	
	public void saveToXML(String xml, String imdbid) {
	    Document dom;
	    Element e = null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("movie");

	        // create data elements and place them under root
	        e = dom.createElement("id");
	        e.appendChild(dom.createTextNode(imdbid));
	        rootEle.appendChild(e);
	       /*
	        e = dom.createElement("role2");
	        e.appendChild(dom.createTextNode(role2));
	        rootEle.appendChild(e);

	        e = dom.createElement("role3");
	        e.appendChild(dom.createTextNode(role3));
	        rootEle.appendChild(e);

	        e = dom.createElement("role4");
	        e.appendChild(dom.createTextNode(role4));
	        rootEle.appendChild(e);*/

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "movie.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
}

/* Ein bisschen Beschreibung
 * http://www.sjmp.de/java/xml-datei-von-fremden-server-lesen-urlconnection/
 * http://www.omdbapi.com/
 * http://www.omdbapi.com/?r=xml&i=" + imdbId
 * http://www.omdbapi.com/?r=xml&r=" + title
 * */

package sort;
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

public class xmlConnect {
	public ArrayList<String> getXml(String name) {
		ArrayList<String> ret = new ArrayList<String>();
        try {
        	String var = "s="+name.replace(" ", "%20");
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
}

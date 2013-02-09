/* Ein bisschen Beschreibung
 * http://www.sjmp.de/java/xml-datei-von-fremden-server-lesen-urlconnection/
 * http://www.omdbapi.com/
 * http://www.omdbapi.com/?r=xml&i=" + imdbId
 * http://www.omdbapi.com/?r=xml&r=" + title
 * */

package sort;
import java.io.IOException;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlConnect {
	public String getXml(String id, String name) {
    	String var= "";
    	if (id == "")
    	{
    		var = "r="+name;
    	}
    	else var = "i="+id;
    	String ret = "(Fehler beim XML Abruf!)";
    	DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder;
        try {
            dbuilder = dbc.newDocumentBuilder();
            Document doc = dbuilder.parse(new URL("http://www.omdbapi.com/?r=xml&" + var).openStream());
            NodeList nl = doc.getElementsByTagName("movie");
            for(int i = 0 ; i < nl.getLength(); i++){
                //if(i == row){                   
                    Element e = (Element)nl.item(i);
                    ret = e.getAttribute("title");
               // }
            }
        } catch (ParserConfigurationException e) {          
            e.printStackTrace();
        } catch (SAXException e) {          
            e.printStackTrace();
        } catch (IOException e) {           
            e.printStackTrace();
        }
   	 return ret;
    }
}

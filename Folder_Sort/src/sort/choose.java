package sort;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;


//Cooperation von Chris und Merlin!!!
public class choose
{
	static FileWriter writer;
	static JTextArea ta; // Text area
	static JButton btnQuit;
	static JButton btnCSV;
	
	
	// gibt die Gr��e eines ausgw�hlten Datenpfades zur�ck
	public static long getDirSize(File dir) {
		
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					size += getDirSize(files[i]); // Gesamtgr��e des Verzeichnisses aufaddieren
				}
				else {
					size += files[i].length(); // Gr��e der Datei aufaddieren
				}
			}
		}
		return size;
	}
	
	// Gibt die gr��e mit Einheit zur�ck
	private static String filesize(long groesse)
	{
		String erg = "";
		if (groesse < 1024)
		{
			erg = " " + (groesse)+ " Byte \n";
		}
		else if (groesse < 1024 * 1024)
		{
			erg = " " + (groesse/1024)+ " KB \n";
		}
		else if (groesse < 1024 * 1024 * 1024)
		{
			erg = " " + (groesse/1024/1024)+ " MB \n";
		}
		else erg = " " + (groesse/1024/1024/1024)+ "GB \n";
		return erg;
	}
	
	// Analysiert irgendwas?
	public static void analyse(File dir) {

		File[] files = dir.listFiles();
		if (files != null) 
		{ // Erforderliche Berechtigungen etc. sind vorhanden
			for (int i = 0; i < files.length; i++) 
			{
				if (files[i].isDirectory()) 
				{
					ta.append(files[i].getName());
					File m = new File (files[i].getAbsolutePath());
					// Gr��enberechnung 
					ta.append(filesize(getDirSize(m))); // Funktion Filesize gibt einen String mit Einhait zur�ck
				}
			}
		}
	}

	// FUCKING MAIN METHODE DIE ALLES AUSF�HRT UND SO!!!einself
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Ordnersort"); //Frame f�r Text Area
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default Bedeutung "X"
        
    	 ta = new JTextArea("", 20, 50); //Text Area generieren
    	 ta.setLineWrap(true); //verhindert automatisches verbreitern nach rechts und links
    	 
    	 JScrollPane sbrText = new JScrollPane(ta); //Scrollbalken
    	 sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    	 //Auswahlfenster Verzeichnis
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner ausw�hlbar
        int returnVal = fc.showOpenDialog(null);
        final File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gew�hlt, dann
        {
            f = fc.getSelectedFile();	//Verzeichnis Holen
	        final File f1 = new File(f.getPath());	//In File speichern
	        
	        //f1.renameTo(new File(f.getPath() + "\\Data"));	//f1 umbenennen

	        

	        //Texte in Textarea
	        ta.append("Pfad: \n" + f.getPath() + "\n");
	        ta.append("Gesamtgr��e \n" +(filesize(getDirSize(f1))) +" \n");
	        ta.append("------------------------------------------------------------------------------ \n");
	        analyse(f1);	//analyse funktion
	        // Create Quit Button
      	  btnQuit = new JButton("Quit");
      	  btnQuit.addActionListener
      	  (
      			  new ActionListener()
      			  {
      				  public void actionPerformed(ActionEvent e)
      				  {
      					  System.exit(0);         
      				  }
      			  }
      	  );
      	  
      	  btnCSV = new JButton("Save in CSV");
      	  btnCSV.addActionListener
      	  (
      			  new ActionListener()
      			  {
      				  public void actionPerformed(ActionEvent e)
      				  {
      					try
      					{
      					    writer = new FileWriter(f.getPath() + "Filmliste.csv");
      				
      					    writer.append("Pfad: \n" + f.getPath() + "\n");
      						writer.append("Gesamtgr��e \n" +(getDirSize(f1)/1024/1024/1024)+ "GB \n");
      						writer.append("------------------------------------------------------------------------------ \n");
      				 
      					    writer.flush();
      					    writer.close();
      					}
      					catch(IOException e1)
      					{
      					     e1.printStackTrace();
      					} 
      			      	     
      				  }
      			  }
      	  );
      	  
      	
     //Scrollbalken und Textarea in panel
 	 frame.getContentPane().add(sbrText);  
 	 frame.getContentPane().add(btnQuit);
 	 frame.getContentPane().add(btnCSV);
 	 frame.pack(); // Adjusts frame to size of components
 	 frame.setVisible(true);
        }

    }    
}
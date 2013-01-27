package sort;
//import java.awt.FlowLayout;
//import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;


//Cooperation von Chris und Merlin!!!
public class choose
{
	static FileWriter writer;
	//static JTextArea ta; // Text area
	//static JButton btnQuit;
	//static JButton btnCSV;
	
	// gibt die Größe eines ausgwählten Datenpfades zurück
	public long getDirSize(File dir) {
		
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					size += getDirSize(files[i]); // Gesamtgröße des Verzeichnisses aufaddieren
				}
				else {
					size += files[i].length(); // Größe der Datei aufaddieren
				}
			}
		}
		return size;
	}
	
	// Schreibt eine CSV mit einer Filmliste in den ausgewählten Ordner
	void writeCSV(File f)
	{
		try
			{
				final File f1 = new File(f.getPath());	//In File speichern
			    writer = new FileWriter(f.getPath() + "\\Filmliste.csv");
		
			    writer.append("Pfad: ;" + f.getPath() + "\n");
				writer.append("Gesamtgröße ;" +(filesize(getDirSize(f1))) +" \n");
				writer.append("-----------------\n");
				
				// Auslesen der Unterordner
				
				File[] files = f1.listFiles();
				if (files != null) 
				{ // Erforderliche Berechtigungen etc. sind vorhanden
					for (int i = 0; i < files.length; i++) 
					{
						if (files[i].isDirectory()) 
						{
							String foldername = files[i].getName();
							String[] splitFolder = foldername.split("_",4);
							// Schreiben der einzelnen Filme in die CSV
							//writer.append(foldername);

							writer.append(splitFolder[splitFolder.length - 1]+ ";"); // Letzten Teil des Strings ausgeben
							
							// Vorne fehlende Daten leer setzen
							for (int ii = 0; ii < (4 - splitFolder.length);ii++)
							{
								writer.append(";");
							}
							// Ausgabe des Split STrings
							for (int ii = 0; ii < (splitFolder.length - 1); ii++) // Dann die restlichen Informationen im String ausgeben
							{
								writer.append(splitFolder[ii]+ ";");
							}
							// Ausgabe der größe
							File m = new File (files[i].getAbsolutePath());
							writer.append(";");
							// Größenberechnung 
							writer.append(filesize(getDirSize(m))); // Funktion Filesize gibt einen String mit Einhait zurück
							writer.append("");
						}
					}
				}
			    writer.flush();
			    writer.close();
    			JOptionPane.showMessageDialog(null, "Speichern erfolgreich! \nDie CSV Datei liegt im Zielordner.");
			}
			catch (FileNotFoundException ex)
			{
				JOptionPane.showMessageDialog(null, "Der Zugriff auf die Datei ist gescheitert!");
			}
			catch(IOException e1)
			{
			     e1.printStackTrace();
    			JOptionPane.showMessageDialog(null, "Es ist ein Fehler aufgetreten!");
			} 	
	}
	
	// Gibt die größe mit Einheit zurück
	String filesize(long groesse)
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
	
	/*	
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
					// Größenberechnung 
					ta.append(filesize(getDirSize(m))); // Funktion Filesize gibt einen String mit Einhait zurück
				}
			}
		}
	}

	// FUCKING MAIN METHODE DIE ALLES AUSFÜHRT UND SO!!!einself
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Ordnersort"); //Frame für Text Area
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default Bedeutung "X"
        
    	ta = new JTextArea("", 20, 50); //Text Area generieren
    	ta.setLineWrap(true); //verhindert automatisches verbreitern nach rechts und links
    	
    	JScrollPane sbrText = new JScrollPane(ta); //Scrollbalken
    	sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    	//Auswahlfenster Verzeichnis
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner auswählbar
        int returnVal = fc.showOpenDialog(null);
        final File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gewählt, dann
        {
			f = fc.getSelectedFile();	//Verzeichnis Holen
			final File f1 = new File(f.getPath());	//In File speichern
			
			//f1.renameTo(new File(f.getPath() + "\\Data"));	//f1 umbenennen
			
			//Texte in Textarea
			ta.append("Pfad: \n" + f.getPath() + "\n");
			ta.append("Gesamtgröße \n" +(filesize(getDirSize(f1))) +" \n");
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
			 
			 // Create Save CSV Button
			 btnCSV = new JButton("Save in CSV");
			 btnCSV.addActionListener // Listener für Button
			 (
				  new ActionListener()
				  {
					  public void actionPerformed(ActionEvent e)
					  {
						 //writeCSV(f); // Schreibt die CSV Datei
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
   */
}
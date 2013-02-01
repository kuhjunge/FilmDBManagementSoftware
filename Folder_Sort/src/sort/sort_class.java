package sort;
//import java.awt.FlowLayout;
//import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;


//Cooperation von Chris und Merlin!!!
public class sort_class
{
	static FileWriter writer;

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
}
package sort;
import java.io.File;

public class analyse { 
	public analyse(File dir) {

		File[] files = dir.listFiles();
		if (files != null) 
		{ // Erforderliche Berechtigungen etc. sind vorhanden!
			for (int i = 0; i < files.length; i++) 
			{
				if (files[i].isDirectory()) 
				{
					System.out.print(files[i].getAbsolutePath());
					System.out.print(" (Ordner)\n");
				}
			}
		}
	}
}

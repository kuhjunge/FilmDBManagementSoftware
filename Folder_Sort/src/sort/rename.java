package sort;
import java.io.File;

//renamer schon implementiert 
public class rename {
    public static void main(String[] args)
    {
	        File f1 = new File("Data");
	 
	        f1.renameTo(new File("Data_org"));
	 
	}
}

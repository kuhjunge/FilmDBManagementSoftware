package sort;

import java.awt.EventQueue;

public class FilmManagerMain {

	/**
	* Hauptmethode - wird automatisch ausgef�hrt und �ffnet GUI
	*/ 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final FilmManagerGUI fmgui = new FilmManagerGUI();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fmgui.frmFilmManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}


/**
* Beschreibung
* @param variable
* @return r�ckgabewert
*/
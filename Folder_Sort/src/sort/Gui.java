package sort;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		// Kontrolpanel Gedöns
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setToolTipText("BlaText");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Checkbox
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setBounds(8, 9, 129, 25);
		contentPane.add(chckbxNewCheckBox);
		
		// Array für unsere JList
        String interessen[] = {"Politik", "Autos", "Mode", 
            "Film- und Fernsehen", "Computer", "Tiere", "Sport"};
		JList<String> list = new JList<String>(interessen);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(428, 249, -200, -251);
		contentPane.add(list);
		
		JButton btnAngriff = new JButton("Angriff");
		btnAngriff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Button Angriff ausführungscode
				// Listener Verknüpfugn

				 //list.revalidate(); // list neu laden
			}
		});
		btnAngriff.setBounds(18, 43, 97, 25);
		contentPane.add(btnAngriff);
	}
}

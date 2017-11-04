package net.tfobz.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Diese Klasse öffnet ein Fenster in dem die von openDialog() gewählte Datei in einem  JTable dargestellt wird.
 * Diese Darstellung kann verändert werden um die Eingabe von Werten zu erleichtern.
 * 
 * Wurde von openDialog keine Datei ausgewählt wird ein leerer Table angezeigt, dieser kann bearbeitet und gespeichert werden.
 * Wird gespeichert, werden die Inhalte in eine Datei geschrieben, die automatisch als Lesedatei ausgewählt wird.
 * 
 * Die Eingabe kann vom Benutzer abgebrochen werden. In diesem Fall werden die Daten nicht gespeichert.
 */
public class ManuellGUI extends JDialog {
	
	public ManuellGUI(JFrame owner) {
		
		JDialog manuell = new JDialog(owner);
		manuell.setTitle("Manuelle Eingabe");
		manuell.setBounds(250,250,700,300);
		manuell.getContentPane().setLayout(null);
		
		JTable manuelTable = new JTable();
		manuelTable.setEnabled(true);
		
		JScrollPane scroll = new JScrollPane(manuelTable); 
		scroll.setBounds(10,10,670,200);
		manuell.add(scroll);
		
		JButton buttonSave = new JButton("Speichern");
		buttonSave.setBounds(470, 220, 100, 40);
		buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		manuell.add(buttonSave);
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.setBounds(570, 220, 100, 40);
		buttonCancel.addActionListener(new ActionListener() {
			//Bricht die Manuelle Eingabe ab ohne die Änderungen zu sichern
			@Override
			public void actionPerformed(ActionEvent e) {
				int cancel = JOptionPane.showConfirmDialog(ManuellGUI.this, "Wollen Sie wirklich abbrechen?", "Sind Sie sicher?", JOptionPane.OK_CANCEL_OPTION);
				if(cancel == JOptionPane.OK_CANCEL_OPTION) {
					setVisible(false);
					dispose();
				}
				
			}
		});
		manuell.add(buttonCancel);
		
		manuell.setVisible(true);
		
		
		
	}
	
	
}

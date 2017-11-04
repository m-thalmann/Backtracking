package net.tfobz.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * Diese Klasse �ffnet ein Fenster in dem die von openDialog() gew�hlte Datei in einem  JTable dargestellt wird.
 * Diese Darstellung kann ver�ndert werden um die Eingabe von Werten zu erleichtern.
 * 
 * Wurde von openDialog keine Datei ausgew�hlt wird ein leerer Table angezeigt, dieser kann bearbeitet und gespeichert werden.
 * Wird gespeichert, werden die Inhalte in eine Datei geschrieben, die automatisch als Lesedatei ausgew�hlt wird.
 * 
 * Die Eingabe kann vom Benutzer abgebrochen werden. In diesem Fall werden die Daten nicht gespeichert.
 */
public class ManuellGUI extends JDialog {
	
	String[] columnName = {"Name","Gewicht","Wert"};
	String[][] data;
	
	public ManuellGUI(JFrame owner, String path,int[][] items, String[] itemsName) {
		
		super(owner);
		this.setTitle("Manuelle Eingabe");
		this.setBounds(250,250,700,300);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		JTable manuellTable;
		if(path == null || path.isEmpty()) {
			manuellTable= new JTable(new Object[1000][3],columnName);
		}else {
			data = new String[items.length][items[0].length+1];
			
			for(int i = 0 ; i < items.length ; i++) {
				for(int j = 0 ; j < items[0].length ; j++) {
					data[i][j+1] = String.valueOf(items[i][j]);
				}
				data[i][0] = itemsName[i];
			}
			
			manuellTable = new JTable(data, columnName);
		}
		
		JScrollPane scroll = new JScrollPane(manuellTable); 
		scroll.setBounds(10,10,670,200);
		this.add(scroll);
		
		JButton buttonSave = new JButton("Speichern");
		buttonSave.setBounds(464, 220, 106, 40);
		buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		this.add(buttonSave);
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.setBounds(570, 220, 106, 40);
		buttonCancel.addActionListener(new ActionListener() {
			//Bricht die Manuelle Eingabe ab ohne die �nderungen zu sichern
			@Override
			public void actionPerformed(ActionEvent e) {
				int cancel = JOptionPane.showConfirmDialog(ManuellGUI.this, "Wollen Sie wirklich abbrechen?", "Sind Sie sicher?", JOptionPane.OK_CANCEL_OPTION);
				if(cancel != JOptionPane.OK_CANCEL_OPTION) {
					ManuellGUI.this.dispose();
				}
				
			}
		});
		this.add(buttonCancel);
		
		this.setVisible(true);
		
		
		
	}
	
	
}

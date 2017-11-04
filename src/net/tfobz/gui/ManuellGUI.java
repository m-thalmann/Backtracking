package net.tfobz.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

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
	
	String[] columnName = {"Name","Gewicht","Wert"};
	String[][] data;
	String path;
	int[][] items;
	String[] itemsName;
	
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
			data = new String[1000][items[0].length+1];
			
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
				try{
					writeFile();
				}catch(IOException exc) {
					JOptionPane.showMessageDialog(ManuellGUI.this, "Es gab einen Fehler beim Schreiben","Fehler" , JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(buttonSave);
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.setBounds(570, 220, 106, 40);
		buttonCancel.addActionListener(new ActionListener() {
			//Bricht die Manuelle Eingabe ab ohne die Änderungen zu sichern
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
	
	private void writeFile() throws IOException {
		FileWriter fw = new FileWriter(new File("path"));
        for(int i = 0 ; i < items.length ; i++) {
        	fw.write(itemsName[i]+";"+data[i][0]+";"+data[i][1]+";"+"\n");
        }
        fw.flush();
        fw.close();
	}
	
	
}

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
	
	private String[] columnName = {"Name","Gewicht","Wert"};
	private String[][] data;
	private String path;
	private int[][] items;
	private String[] itemsName;
	private JTable manuellTable;
	
	public ManuellGUI(JFrame owner, String path,int[][] items, String[] itemsName, int maxElements) {
		
		super(owner);
		this.setTitle("Manuelle Eingabe");
		this.setSize(700,300);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		
		this.path = path;
		
		if(path == null || path.isEmpty()) {
			manuellTable= new JTable(new Object[maxElements][3],columnName);
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
					dispose();
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
		File f = new File(path);
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		int size = 0;
		while(manuellTable.getValueAt(size, 0) != null) {
			size++;
		}
        for(int i = 0 ; i < size ; i++) {
        	fw.write(manuellTable.getValueAt(i, 0)+";"+ ((manuellTable.getValueAt(i, 1)).toString()) +";"+((manuellTable.getValueAt(i, 2)).toString())+";"+"\n");
        }
        
        fw.flush();
        fw.close();
	}
	
	
}

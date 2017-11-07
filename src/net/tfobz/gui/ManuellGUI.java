package net.tfobz.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Diese Klasse öffnet ein Fenster in dem die von openDialog() gewählte Datei in einem  JTable dargestellt wird.
 * Diese Darstellung kann verändert werden um die Eingabe von Werten zu erleichtern.
 * 
 * Wurde von openDialog keine Datei ausgewählt wird ein leerer Table angezeigt, dieser kann bearbeitet und gespeichert werden.
 * Wird gespeichert, werden die Inhalte in eine Datei geschrieben, die automatisch als Lesedatei ausgewählt wird.
 * 
 * Die Eingabe kann vom Benutzer abgebrochen werden. In diesem Fall werden die Daten nicht gespeichert.
 * 
 * @author 14untpat
 * 
 */
public class ManuellGUI extends JDialog {
	
	private String[] columnName = {"Name","Gewicht","Wert"};
	private String path;
	private JTable manuellTable;
	private JFileChooser fc;
	
	public ManuellGUI(JFrame owner, String path, int[][] items, String[] itemsName, int maxElements) {
		super(owner, "Daten bearbeiten");
		this.setSize(700,300);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		
		this.path = path;
		
		//Tabelle (mit Inhalt) erzeugen
		if(path == null || !(items.length > 0) || !(itemsName.length > 0)) {
			manuellTable = new JTable(new Object[maxElements][3],columnName);
		}else {
			String[][] data = new String[maxElements][items[0].length + 1];
			
			for(int i = 0 ; i < items.length ; i++) {
				data[i][0] = itemsName[i];
				
				for(int j = 0 ; j < items[0].length ; j++) {
					data[i][j+1] = String.valueOf(items[i][j]);
				}
			}
			
			manuellTable = new JTable(data, columnName);
		}
		
		this.manuellTable.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scroll = new JScrollPane(manuellTable); 
		scroll.setBounds(10,10,670,200);
		this.getContentPane().add(scroll);
		
		JButton buttonSave = new JButton("Speichern");
		buttonSave.setBounds(464, 220, 106, 40);
		buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					writeFile();
				}catch(IOException exc) {
					JOptionPane.showMessageDialog(ManuellGUI.this, "Es gab einen Fehler beim Schreiben", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		buttonSave.setFocusPainted(false);
		this.getContentPane().add(buttonSave);
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.setBounds(573, 220, 106, 40);
		buttonCancel.addActionListener(new ActionListener() {
			//Bricht die manuelle Eingabe ab ohne die Änderungen zu sichern
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonCancel.setFocusPainted(false);
		this.getContentPane().add(buttonCancel);
		
		this.setVisible(true);
	
	}
	
	/**
	 * Diese Methode öffnet, falls noch kein Pfad gesetz wurde, einen FileChooser um
	 * das Ziel zu wählen. Es ist nur möglich .csv Dateien zu wählen. Falls die Datei
	 * bereits existiert wird der Benutzer darauf hingewiesen.
	 * Dann wird Zeile für Zeile der Inhalt der Tabelle in das Ziel geschrieben, bis
	 * in mindestens einer der Zelle der Reihe nichts enthalten ist.
	 * Die Methode setzt dann die Eigenschaft visible auf false um das Hauptfenster zu
	 * reaktivieren. 
	 * 
	 * @throws IOException
	 */
	private void writeFile() throws IOException {
		if(path == null) {
			fc = new JFileChooser();
			fc.setMultiSelectionEnabled(false);
			fc.setFileFilter(new FileNameExtensionFilter("CSV Dateien", "csv"));
			fc.setAcceptAllFileFilterUsed(false);
			
			if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				
				path = fc.getSelectedFile().getAbsolutePath();
				
				if(!path.endsWith(".csv")) {
					path += ".csv";
				}
				
				File file = new File(path);
				
				if(file.isFile()) {
					int ret = JOptionPane.showConfirmDialog(this, "Die Datei: \""+ path + "\" existiert bereits. Überschreiben?");
					
					if(ret == JOptionPane.YES_OPTION){
						file.delete();
					}
					else if(ret == JOptionPane.NO_OPTION){
						path = null;
						writeFile();
						return;
					}
					else{
						return;
					}
				}
			}
			else{
				return;
			}
		}
		else{
			int ret = JOptionPane.showConfirmDialog(this, "Die Datei: \""+ path + "\" überschreiben?");
			
			if(ret == JOptionPane.YES_OPTION){
				new File(path).delete();
			}
			else if(ret == JOptionPane.NO_OPTION){
				path = null;
				writeFile();
				return;
			}
			else{
				return;
			}
		}
		
		//Der New-Line Operator des derzeitigen Betriebssystems (\r\n = Windows)
		String nl = System.getProperty("line.separator");
		
		File f = new File(path);
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		int i = 0;
		
		while(manuellTable.getValueAt(i, 0) != null && manuellTable.getValueAt(i, 1) != null && manuellTable.getValueAt(i, 2) != null) {
			fw.write(manuellTable.getValueAt(i, 0)+ ";" + ((manuellTable.getValueAt(i, 1)).toString()) + ";" + ((manuellTable.getValueAt(i, 2)).toString()) + nl);
			i++;
    }
        
    fw.flush();
    fw.close();
    
    this.setVisible(false);
	}

	public String getPath() {
		return path;
	}
	
}

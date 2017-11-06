package net.tfobz.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.tfobz.backtracking.Backtracking;
import net.tfobz.backtracking.BacktrackingException;

/**
 * Diese Klasse erstellt eine GUI, über welche man mithilfe
 * des Backtrackings die beste Lösung findet.
 * 
 * @author 14thamat
 *
 */

public class BacktrackingGUI extends JFrame
{
	private final int MAX_LINES = 1000;
	private final int MAX_LINES_VIS = 6;	
	
	private JButton buttonChooseFile = new JButton("Datei wählen...");
	private JButton buttonHelpFile = new JButton("?");
	private JButton buttonCompute = new JButton("Berechnen");
	private JButton buttonVisualize = new JButton("Visuell darstellen");
	private JButton buttonManual = new JButton("Bearbeiten...");
	
	private JTextField textPath = new JTextField();
	private JTextField textSolution = new JTextField("");
	private JTextField textWeight = new JTextField();
	
	private JLabel labelWeight = new JLabel("Maximal Gewicht");
	
	/**
	 * Liste der Elemente welche im besten Ergebnis sind
	 */
	private JList<String> listAll = new JList<>();
	
	private JScrollPane scrollListAll;
	
	private JFileChooser fileChooser;
	
	private String path = null;
	private String pathTmp = null;
	private String[] noSolutions = {"Noch keine Lösung"};
	
	private Vector<String> solutions = new Vector();
	
	private int maxWeight = 0;
	
	/**
	 * Die Elemente (Gewicht, Wert)
	 */
	private int[][] items = null;
	/**
	 * Die Namen der Elemente
	 */
	private String[] itemsName = null;
	
	public BacktrackingGUI(){
		super("Backtracking");
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 440);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
		
		this.buttonChooseFile.setBounds(10, 10, 145, 30);
		this.buttonChooseFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				openDialog();
			}
		});
		this.buttonChooseFile.setFocusPainted(false);
		this.buttonChooseFile.setMnemonic('D');
		this.getContentPane().add(this.buttonChooseFile);
		
		this.buttonHelpFile.setBounds(160, 10, 50, 30);
		this.buttonHelpFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				//Hilfe anzeigen
				new FileHelpGUI(BacktrackingGUI.this);
			}
		});
		this.buttonHelpFile.setFocusPainted(false);
		this.buttonHelpFile.setMnemonic('?');
		this.getContentPane().add(this.buttonHelpFile);
		
		this.buttonManual.setBounds(230, 50, 250, 30);
		this.buttonManual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Ruft ein Fenster auf, in welchem eine Tabelle mit den Elementen bearbeitet werden kann
				readFile();
				ManuellGUI m = new ManuellGUI(BacktrackingGUI.this,path,items,itemsName, MAX_LINES);
			}
		});
		this.buttonManual.setMnemonic('E');
		this.buttonManual.setFocusPainted(false);
		this.getContentPane().add(this.buttonManual);
		
		this.textPath.setBounds(230, 10, 250, 30);
		this.textPath.setEditable(false);
		this.getContentPane().add(this.textPath);
		
		this.labelWeight.setBounds(10, 90, 200, 30);
		this.labelWeight.setHorizontalAlignment(SwingConstants.CENTER);
		this.labelWeight.setDisplayedMnemonic('G');
		this.labelWeight.setLabelFor(this.textWeight);
		this.getContentPane().add(this.labelWeight);
		
		this.textWeight.setBounds(230, 90, 250, 30);
		this.textWeight.addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e) {
				//Überprüfung des Inhaltes
				if(!textWeight.getText().isEmpty()){
					textWeight.setText(textWeight.getText().replaceAll(" ", ""));
					
					try{
						maxWeight = Integer.parseInt(textWeight.getText());
					} catch (NumberFormatException ex){
						JOptionPane.showMessageDialog(BacktrackingGUI.this, "Bitte geben Sie eine gültige, natürliche Zahl ein", "Achtung", JOptionPane.WARNING_MESSAGE);
						textWeight.requestFocus();
					}
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				//Alles markieren beim Klicken in das Textfeld
				((JTextField) e.getSource()).selectAll();
			}
		});
		this.textWeight.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					//Beim drücken der Enter-Taste im Textfeld, den Berechnen-Knopf drücken
					
					//Überprüfung des Inhaltes
					if(!textWeight.getText().isEmpty()){
						textWeight.setText(textWeight.getText().replaceAll(" ", ""));
						
						try{
							maxWeight = Integer.parseInt(textWeight.getText());
						} catch (NumberFormatException ex){
							JOptionPane.showMessageDialog(BacktrackingGUI.this, "Bitte geben Sie eine gültige, natürliche Zahl ein", "Achtung", JOptionPane.WARNING_MESSAGE);
							textWeight.requestFocus();
						}
					}
					buttonCompute.doClick();
				}
			}
			
		});
		this.getContentPane().add(this.textWeight);
		
		this.buttonCompute.setBounds(10, 130, 200, 30);
		this.buttonCompute.setFocusPainted(false);
		this.buttonCompute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(path != null){
					try{
						//Während das Programm läuft kann die Datei noch bearbeitet werden.
						//Deshalb erst einlesen beim Berechnen
						readFile();
						
						try{
							int[] best = Backtracking.getBest(items, maxWeight);
							int weight = 0;
							int value = 0;
							
							solutions.clear();
							
							for(int i = 0; i < best.length; i++){
								weight += items[best[i]][0];
								value += items[best[i]][1];
								
								solutions.add(itemsName[best[i]] + " (Gewicht: " + items[best[i]][0] + ", Wert: " + items[best[i]][1] + ")");
							}
							
							textSolution.setText("Gewicht: " + weight + ", Wert: " + value);
							listAll.setListData(solutions);
							
							if(best.length == 0){
								JOptionPane.showMessageDialog(BacktrackingGUI.this, "Keine Lösung!", "Information", JOptionPane.INFORMATION_MESSAGE);
							}
							
						}catch(BacktrackingException exc){
							JOptionPane.showMessageDialog(BacktrackingGUI.this, exc.getMessage(), "Achtung", JOptionPane.WARNING_MESSAGE);
							listAll.setListData(noSolutions);
							textSolution.setText("");
						}
					}catch(NumberFormatException ex){
						JOptionPane.showMessageDialog(BacktrackingGUI.this, "Geben Sie eine gültige Zahl ein", "Fehler", JOptionPane.WARNING_MESSAGE);
						textWeight.requestFocus();
					}
				}
				else{
					JOptionPane.showMessageDialog(BacktrackingGUI.this, "Wählen Sie zuerst eine Datei aus", "Achtung", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.buttonCompute.setMnemonic('B');
		this.getContentPane().add(this.buttonCompute);
		
		this.textSolution.setBounds(230, 130, 250, 30);
		this.textSolution.setEditable(false);
		this.getContentPane().add(this.textSolution);
		
		listAll.setListData(noSolutions);
		this.scrollListAll = new JScrollPane(this.listAll);
		this.scrollListAll.setBounds(230, 170, 250, 226);
		this.getContentPane().add(this.scrollListAll);
		
		this.buttonVisualize.setBounds(10, 365, 200, 30);
		this.buttonVisualize.setFocusPainted(false);
		this.buttonVisualize.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(path != null){
					readFile();
					if(items.length <= MAX_LINES_VIS){
						try{
							try{
								new BacktrackingVisualGUI(BacktrackingGUI.this, Backtracking.getVerlauf(items, maxWeight)).setVisible(true);
							}catch(BacktrackingException exc){
								JOptionPane.showMessageDialog(BacktrackingGUI.this, exc.getMessage(), "Information", JOptionPane.INFORMATION_MESSAGE);
							}
						}catch(NumberFormatException ex){
							JOptionPane.showMessageDialog(BacktrackingGUI.this, "Geben Sie eine gültige Zahl ein", "Fehler", JOptionPane.ERROR_MESSAGE);
							textWeight.requestFocus();
						}
					}
					else{
						JOptionPane.showMessageDialog(BacktrackingGUI.this, "Zu viele Elemente zum visualisieren (max. " + MAX_LINES_VIS + ")!", "Achtung", JOptionPane.WARNING_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(BacktrackingGUI.this, "Wählen Sie zuerst eine Datei aus", "Achtung", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.buttonVisualize.setMnemonic('V');
		this.getContentPane().add(this.buttonVisualize);
		
		this.setVisible(true);
	}
	
	/**
	 * Öffnet einen Dateibrowser um eine Datei zu wählen.
	 * Wurde diese Methode bereits einmal aufgerufen (in pathTmp steht schon etwas)
	 * wird der Browser an der Stelle wieder aufgerufen.
	 * Es wird überprüft ob es diese Datei gibt und ob sie eine *.csv-Datei ist.
	 * Falls nicht wird eine Fehlermeldung ausgegeben und die Methode ruft sich selbst
	 * noch einmal auf.
	 * Falls alles richtig funktioniert hat, wird der Pfad der Datei
	 * abgespeichert und im dafür vorgesehenen Textfeld ausgegeben.
	 * 
	 * @return 	false - wenn keine (gültige) Datei gewählt wurde
	 * 					true - wenn eine gültige Datei gewählt wurde
	 */
	private boolean openDialog(){
		boolean ret = false;
		
		if(this.pathTmp == null)
			this.fileChooser = new JFileChooser();
		else
			this.fileChooser = new JFileChooser(this.pathTmp.substring(0, this.pathTmp.lastIndexOf("\\")));
		
		this.fileChooser.setMultiSelectionEnabled(false);
		this.fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Dateien", "csv"));
		this.fileChooser.setAcceptAllFileFilterUsed(false);
		
		if(this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			this.pathTmp = this.fileChooser.getSelectedFile().getAbsolutePath();
			
			if(!new File(this.pathTmp).isFile() && new File(this.pathTmp + ".csv").isFile()){
				this.pathTmp += ".csv";
			}
			
			if(!new File(this.pathTmp).isFile()){
				JOptionPane.showMessageDialog(this, "Die Datei: \"" + this.pathTmp + "\" existiert nicht!", "Achtung", JOptionPane.WARNING_MESSAGE);
				this.fileChooser = null;
				ret = openDialog();
			}
			else if(!this.pathTmp.endsWith(".csv")){
				JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine *.csv Datei", "Achtung", JOptionPane.WARNING_MESSAGE);
				this.fileChooser = null;
				ret = openDialog();
			} 
			else{
				ret = true;
				
				this.path = this.pathTmp;
				this.textPath.setText(this.path);
				this.textSolution.setText("");
				this.listAll.setListData(noSolutions);
				this.solutions.clear();
				
				this.textWeight.requestFocus();
			}
			
		}
		
		this.fileChooser = null;

		return ret;
	}
	
	/**
	 * Diese Methode liest aus der ausgewählten Datei, Zeile für Zeile,
	 * die Elemente. Die Bezeichnungen werden in itemsName abgespeichert,
	 * das Gewicht und der Wert in items (Gewicth, Wert).
	 * Es werden maximal MAX_LINES Elemente eingelesen. Falls diese Zahl überschritten wird,
	 * meldet das Programm dem Benutzer, dass nur MAX_LINES aus der Datei verwendet,
	 * der Rest ignoriert wird.
	 * Falls eine Zeile ein falsches Format hat, meldet das die Methode dem Benutzer,
	 * löscht den Pfad und beendet sich.
	 */
	private void readFile(){
		if(this.path == null){
			return;
		}
		
		if(items != null){
			items = null;
		}
		if(itemsName != null){
			itemsName = null;
		}
		
		BufferedReader br = null;;
		int num = 0;
		String[] allLines = new String[MAX_LINES];
		try {
			br = new BufferedReader(new FileReader(this.path));
			String line;
	    while ((line = br.readLine()) != null && num < MAX_LINES){
	    	if(!line.isEmpty()){
	    		allLines[num] = line;
	    		num++;
	    		
	    		if(num == MAX_LINES){
	    			JOptionPane.showMessageDialog(BacktrackingGUI.this, "Das Limit für Elemente ist " + MAX_LINES + ". Es werden deshalb nur die ersten " + MAX_LINES + " Elemente verwendet!", "Fehler", JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    }
	    
	    items = new int[num][2];
	    itemsName = new String[num];
	    
	    for(int i = 0; i < num; i++){
	    	String[] components = allLines[i].split(";");
	    	
	    	if(components.length != 3){
	    		JOptionPane.showMessageDialog(this, "Falsches Format der Datei!", "Achtung", JOptionPane.WARNING_MESSAGE);
	    		
	    		return;
	    	}
	    	
	    	itemsName[i] = components[0];
	    	try{
	    		items[i][0] = Integer.parseInt(components[1]);
	    		items[i][1] = Integer.parseInt(components[2]);
	    	} catch (NumberFormatException ex){
	    		JOptionPane.showMessageDialog(this, "Falsches Format der Datei!", "Achtung", JOptionPane.WARNING_MESSAGE);
	    		
	    		return;
	    	}
	    }
		} catch (IOException e) {
		} finally {
	    try {
				br.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		new BacktrackingGUI();
	}
	
}

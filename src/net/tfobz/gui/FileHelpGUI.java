package net.tfobz.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Fenster, welches Hilfe zum Aufbau der *.csv Eingabedatei
 * liefert.
 * 
 * @author 14thamat
 *
 */

public class FileHelpGUI extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private String helpText = "<html><head><style>body{font-family: 'Arial', sans-serif;}</style></head><body><b>Hilfe: Daten aus Datei importieren</b><br/><div id=\"content\">Um Daten in das Programm zu importieren, ben&ouml;tigen Sie eine *.csv-Datei (Daten durch ; getrennt).<br/>Der Aufbau der Datei sollte wie folgt sein:<br/><br/>&lt;Bezeichnung des Elementes&gt;;&lt;Gewicht&gt;;&lt;Wert&gt;<br/><br/>Die Bezeichnung sollte aus Text bestehen, die beiden anderen Werte sollten <b>nur</b> Zahlen sein.<br/><br/><b>Beispiel:</b><br/><br/>Wasserflasche;1;10<br/>Buch;2;2<br/>Geldtasche;1;20</div></body></html>";

	/**
	 * Create the dialog.
	 */
	public FileHelpGUI(JFrame owner) {
		setSize(450, 500);
		setLocationRelativeTo(owner);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Schlie\u00DFen");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FileHelpGUI.this.setVisible(false);
					}
				});
				okButton.setFocusPainted(false);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		JEditorPane editor = new JEditorPane("text/html", helpText);
		editor.setEditable(false);
		JScrollPane scroll = new JScrollPane(editor);
		getContentPane().add(scroll);
		
		setVisible(true);
	}

}

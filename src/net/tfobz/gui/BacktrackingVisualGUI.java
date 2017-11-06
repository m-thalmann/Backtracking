package net.tfobz.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.tfobz.backtracking.KnotenExt;

/**
 * Dieses Fenster stellt den Verlauf der Berechnung durch das Backtracking
 * in Form eines JTrees dar. In jedem Ast können maximal zwei Unteräste (usw.) sein:
 * einer für links (einpacken) und einer für rechts (nicht einpacken)
 * Durch klicken der 4 Knöpfe kann man sich den Verlauf Schritt für Schritt anzeigen
 * lassen.
 * 
 * @author matth
 *
 */

public class BacktrackingVisualGUI extends JDialog
{

	private ArrayList<KnotenExt> verlauf;
	private int pos = 0;
	private int size = 0;
	private JTree tree = new JTree();
	
	public BacktrackingVisualGUI(JFrame owner, ArrayList<KnotenExt> verlauf) {
		super(owner, "Backtracking - Visualisieren");
		
		this.verlauf = verlauf;
		this.size = verlauf.size();
		
		setResizable(false);
		getContentPane().setLayout(null);
		
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBounds(12, 13, 458, 385);
		getContentPane().add(treeView);
		
		//An den Anfang
		JButton button = new JButton("<<");
		button.setBounds(12, 411, 50, 35);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pos = 0;
				showVerlauf();
			}
		});
		button.setFocusPainted(false);
		getContentPane().add(button);
		
		//1 zurück
		JButton button_1 = new JButton("<");
		button_1.setBounds(74, 411, 50, 35);
		button_1.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pos > 0){
					pos--;
					showVerlauf();
				}
			}
		});
		button_1.setFocusPainted(false);
		getContentPane().add(button_1);
		
		//1 vor
		JButton button_2 = new JButton(">");
		button_2.setBounds(136, 411, 50, 35);
		button_2.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pos < size -1){
					pos++;
					showVerlauf();
				}
			}
		});
		button_2.setFocusPainted(false);
		getContentPane().add(button_2);
		
		//An das Ende
		JButton button_3 = new JButton(">>");
		button_3.setBounds(198, 411, 50, 35);
		button_3.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pos = size -1;
				showVerlauf();
			}
		});
		getContentPane().add(button_3);
		
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		
		showVerlauf();
	}
	
	/**
	 * Diese Methode zeigt den Verlauf im JTree jenachdem wie weit
	 * pos gesetzt wurde.
	 */
	private void showVerlauf(){
		tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("Start") {
					{
						ArrayList<DefaultMutableTreeNode> list = new ArrayList<>();
						
						for(int i = 0; i < pos; i++){
							KnotenExt k = verlauf.get(i);
							list.add(new DefaultMutableTreeNode(k.toString()));
							
							if(k.isLinks()){
								if(i == 0){
									add(list.get(0));
								}else{
									list.get(i - 1).add(list.get(i));
								}
							}else{
								boolean found = false;
								int j = i;
								for(; j >= 0; j--){
									if(verlauf.get(j).getLevel() + 1 == verlauf.get(i).getLevel()){
										if(verlauf.get(j).getContent().size() == verlauf.get(i).getContent().size()){
											boolean ok = true;
											for(int l = 0; l < verlauf.get(j).getContent().size(); l++){
												if(verlauf.get(j).getContent().get(l) != verlauf.get(i).getContent().get(l)){
													ok = false;
												}
											}
											
											if(ok){
												found = true;
												list.get(j).add(list.get(i));
											}
										}
									}
								}
								
								if(!found){
									add(list.get(i));
								}
							}
						}
					}
				}
			));
		
		//Alle Knoten des Baumes öffnen
		expandAllNodes(tree, 0, tree.getRowCount());
	}
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
    for(int i = startingIndex; i < rowCount; ++i){
      tree.expandRow(i);
    }

    if(tree.getRowCount() != rowCount){
      expandAllNodes(tree, rowCount, tree.getRowCount());
    }
	}
	
}

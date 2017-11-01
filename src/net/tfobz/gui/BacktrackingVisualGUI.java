package net.tfobz.gui;

import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * 
 * @author 14thamat
 *
 */

public class BacktrackingVisualGUI extends JDialog
{
	JButton bb = new JButton("<<");
	JButton b = new JButton("<");
	JButton f = new JButton(">");
	JButton ff = new JButton(">>");
	
	int[] verlauf = null;
	
	public BacktrackingVisualGUI(JFrame owner, int[] verlauf){
		super(owner, "Backtracking - Visualisieren");
		setLayout(null);
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).left - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).right;
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).top - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;		
		setBounds(0, 0, width, height);
		
		this.verlauf = verlauf;
		
		this.bb.setBounds((int) this.getSize().getWidth() - 4 * 60 - 20, (int) this.getSize().getHeight() - 100, 50, 40);
		this.bb.setFocusPainted(false);
		this.getContentPane().add(bb);
		
		this.b.setBounds((int) this.getSize().getWidth() - 3 * 60 - 20, (int) this.getSize().getHeight() - 100, 50, 40);
		this.b.setFocusPainted(false);
		this.getContentPane().add(b);
		
		this.f.setBounds((int) this.getSize().getWidth() - 2 * 60 - 20, (int) this.getSize().getHeight() - 100, 50, 40);
		this.f.setFocusPainted(false);
		this.getContentPane().add(f);
		
		this.ff.setBounds((int) this.getSize().getWidth() - 1 * 60 - 20, (int) this.getSize().getHeight() - 100, 50, 40);
		this.ff.setFocusPainted(false);
		this.getContentPane().add(ff);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
}

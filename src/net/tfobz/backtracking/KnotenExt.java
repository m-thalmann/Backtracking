package net.tfobz.backtracking;

import java.util.ArrayList;

/**
 * Diese Klasse besitzt zusätzlich zu den Eigenschaften der Klasse Knoten noch zwei Eigenschaften:
 * 	- links: Wenn true wurde die Zahl des Levels eingepackt, sonst nicht
 * 	- level: Auf welchem Level sich der Knoten befindet
 *
 */

public class KnotenExt extends Knoten
{
	private int level = 0;
	private boolean links = true;
	
	public KnotenExt(){
		super();
	}
	
	public int getLevel() {
		return level;
	}

	public boolean isLinks() {
		return links;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLinks(boolean links) {
		this.links = links;
	}
	
	public String toString(){
		return content.toString() + " w = " + weight + ", v = " + value + ", Schranke: " + schranke + ", Level: " + level + ", " + ((links) ? "Links" : "Rechts");	
	}
	
	public KnotenExt clone(){
		KnotenExt k = new KnotenExt();
		k.content = (ArrayList<Integer>) this.content.clone();
		k.weight = this.weight;
		k.value = this.value;
		return k;
	}
}

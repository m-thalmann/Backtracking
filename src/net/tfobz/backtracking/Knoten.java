package net.tfobz.backtracking;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen Knoten dar, welcher die folgenden
 * Eigenschaften besitzt:
 * 	- content: Der Inhalt des Knotens
 * 	- weight: Das Gesamtgewicht des Knotens
 * 	- value: Der Gesamtwert des Knotens
 * 	- schranke: Die Summe aller Werte wenn man immer nur nach links (einpacken) gehen würde
 *
 */

public class Knoten
{
	protected ArrayList<Integer> content = new ArrayList<>();
	
	protected int weight = 0;
	protected int value = 0;
	
	protected int schranke = 0;
	
	public ArrayList<Integer> getContent() {
		return content;
	}

	public int getWeight() {
		return weight;
	}

	public int getValue() {
		return value;
	}

	public int getSchranke() {
		return schranke;
	}
	
	/**
	 * Diese Methode wandelt die ArrayList content in ein int-Array um
	 * @return
	 */
	public int[] getElements(){
		int[] ret = new int[this.content.size()];
		
		for(int i = 0; i < ret.length; i++){
			ret[i] = content.get(i);
		}
		
		return ret;
	}

	public void setContent(ArrayList<Integer> content) {
		this.content = content;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setSchranke(int schranke) {
		this.schranke = schranke;
	}
	
	public Knoten clone(){
		Knoten k = new Knoten();
		k.content = (ArrayList<Integer>) this.content.clone();
		k.weight = this.weight;
		k.value = this.value;
		return k;
	}
	
	public String toString(){
		return content.toString() + " w = " + weight + ", v = " + value + ", Schranke: " + schranke;
	}
	
}
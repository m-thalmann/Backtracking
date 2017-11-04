package net.tfobz.backtracking;

import java.util.ArrayList;

public class Knoten
{
	private ArrayList<Integer> content = new ArrayList<>();
	
	private int weight = 0;
	private int value = 0;
	
	private int schranke = 0;
	
	private int level = 0;
	private boolean links = true;
	
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
	
	public int getLevel() {
		return level;
	}

	public boolean isLinks() {
		return links;
	}

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
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLinks(boolean links) {
		this.links = links;
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
	
	public String toString(boolean extendet){
		if(extendet) {
			return content.toString() + " w = " + weight + ", v = " + value + ", Schranke: " + schranke + ", Level: " + level + ", " + ((links) ? "Links" : "Rechts");	
		}else {
			return this.toString();
		}
	}
	
}
package net.tfobz.backtracking;

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
		return (KnotenExt) super.clone();
	}
}

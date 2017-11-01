package net.tfobz.backtracking;

import java.util.HashSet;

public class Backtracking{

	private static int max_weigth = 0;
	private static int[][] items = null;
	private static int maxSchranke = 0;
	
	public static int[] getBest(int[][] i, int maxWeight) throws BacktrackingException{
		items = i;
		max_weigth = maxWeight;
		maxSchranke = 0;
		
		allPossibilities(new Knoten(), 0);
		
		int[] test = {0};
		return test;
	}
	
	public static void allPossibilities(Knoten k, int level) {
		if(level == items.length){
			System.out.println(k.toString());
			maxSchranke = k.value;
		}else{
			Knoten l = null;
			Knoten r = null;
			l = k.clone();
			l.content.add(level);
			l.weight+=items[level][0];
			l.value+=items[level][1];
			r = k.clone();
			
			l.schranke=l.value;
			r.schranke=r.value;
			
			for(int i = level+1; i < items.length; i++){
				l.schranke+=items[i][1];
				r.schranke+=items[i][1];
			}
			
			if(l.schranke > maxSchranke && l.weight < max_weigth)
				allPossibilities(l, level + 1);
			if(r.schranke > maxSchranke)
				allPossibilities(r, level + 1);
		}
	}

	public static int[] getVerlauf(int[][] items, int maxWeight) throws BacktrackingException{
		return null;
	}
	
	private static class Knoten
	{
		private HashSet<Integer> content = new HashSet<Integer>();
		
		private int weight = 0;
		private int value = 0;
		
		private int schranke = 0;
		
		public Knoten clone(){
			Knoten k = new Knoten();
			k.content = (HashSet<Integer>) this.content.clone();
			k.weight = this.weight;
			k.value = this.value;
			return k;
		}
		
		public String toString(){
			return content.toString() + " w = " + weight + ", v = " + value + ", Schranke: " + schranke;
		}
	}
}
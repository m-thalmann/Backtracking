package net.tfobz.backtracking;

import java.util.ArrayList;

public class Backtracking{

	private static int max_weigth = 0;
	private static int[][] items = null;
	private static int maxSchranke = 0;
	private static Knoten best = null;
	private static KnotenExt bestExt = null;
	
	private static ArrayList<KnotenExt> verlauf = null;
	
	public static int[] getBest(int[][] i, int maxWeight) throws BacktrackingException{
		if(i == null){
			return null;
		}
		if(maxWeight <= 0){
			throw new BacktrackingException("Maximal Gewicht muss größer als null sein");
		}
		if(i.length == 0){
			throw new BacktrackingException("Es muss mindestens ein Element enthalten sein");
		}
		
		items = i;
		max_weigth = maxWeight;
		maxSchranke = 0;
		best = new Knoten();
		
		computeBest(new Knoten(), 0);
		
		return best.getElements();
	}
	
	private static void computeBest(Knoten k, int level){
		if(level == items.length){
			maxSchranke = k.getValue();
			
			if(k.getValue() > best.getValue()){
				best = k;
			}
		}else{
			Knoten l = null;
			Knoten r = null;
			l = k.clone();
			l.getContent().add(level);
			l.setWeight(l.getWeight() + items[level][0]);
			l.setValue(l.getValue() + items[level][1]);
			r = k.clone();
			
			l.setSchranke(l.getValue());
			r.setSchranke(r.getValue());
			
			for(int i = level+1; i < items.length; i++){
				l.setSchranke(l.getSchranke() + items[i][1]);
				r.setSchranke(r.getSchranke() + items[i][1]);
			}
			
			if(l.getSchranke() > maxSchranke && l.getWeight() <= max_weigth)
				computeBest(l, level + 1);
			if(r.getSchranke() > maxSchranke)
				computeBest(r, level + 1);
		}
	}

	public static ArrayList<KnotenExt> getVerlauf(int[][] i, int maxWeight) throws BacktrackingException{
		if(i == null){
			return null;
		}
		if(maxWeight <= 0){
			throw new BacktrackingException("Maximal Gewicht muss größer als null sein");
		}
		if(i.length == 0){
			throw new BacktrackingException("Es muss mindestens ein Element enthalten sein");
		}
		
		items = i;
		max_weigth = maxWeight;
		maxSchranke = 0;
		bestExt = new KnotenExt();
		verlauf = new ArrayList<>();
		
		computeVerlauf(new KnotenExt(), 0);
		
		return verlauf;
	}
	
	private static void computeVerlauf(KnotenExt k, int level){
		if(level == items.length){
			maxSchranke = k.getValue();
			
			if(k.getValue() > bestExt.getValue()){
				bestExt = k;
			}
		}else{
			KnotenExt l = null;
			KnotenExt r = null;
			l = k.clone();
			l.getContent().add(level);
			l.setWeight(l.getWeight() + items[level][0]);
			l.setValue(l.getValue() + items[level][1]);
			l.setLevel(level+1);
			l.setLinks(true);
			r = k.clone();
			r.setLevel(level+1);
			r.setLinks(false);
			
			l.setSchranke(l.getValue());
			r.setSchranke(r.getValue());
			
			for(int i = level+1; i < items.length; i++){
				l.setSchranke(l.getSchranke() + items[i][1]);
				r.setSchranke(r.getSchranke() + items[i][1]);
			}
			
			if(l.getSchranke() > maxSchranke && l.getWeight() <= max_weigth){
				verlauf.add(l);
				computeVerlauf(l, level + 1);
			}
			if(r.getSchranke() > maxSchranke){
				verlauf.add(r);
				computeVerlauf(r, level + 1);
			}
		}
	}
	
}
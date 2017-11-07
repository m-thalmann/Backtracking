package net.tfobz.backtracking;

import java.util.ArrayList;


/**
 * Diese Klasse enthällt Methoden und Eigenschaften,
 * mit deren Hilfe man den Backtracking-Algorithmus anwendet um 
 * das gegebene "Rucksackproblem zu lösen"
 * 
 * @author 14untpat
 *
 */
public class Backtracking{
	
	//Das Maxiamlgewicht des Rucksacks
	private static int max_weigth = 0;
	
	//Mögliche Elemente mit ihrem Gewicht und Wert
	private static int[][] items = null;
	
	//Wert vom bisher vielversprechendsten Zweig
	private static int maxSchranke = 0;
	
	//Bisher bester Knoten
	private static Knoten best = null;
	
	//Einige zu Knoten hinzugefügte Eigenschaften und Methoden (Zur Errechnung des Verlaufs)
	private static KnotenExt bestExt = null;
	
	//Knoten, die bis besten Knoten durchlaufen wurden
	private static ArrayList<KnotenExt> verlauf = null;
	
	/**
	 * Setzt die Items und das Maximalgewicht.
	 * Ruft die Methode computeBest auf, welche das Ergebnis in best schreibt.
	 * Das Ergebnis wird dann über die Methode getElements von Knoten aufgerufen.
	 * 
	 * @param i Elemente, die zur Berechnung verwendet werden
	 * @param maxWeight Maximalgewicht im Rucksack
	 * 
	 * @return Elemente, die im Besten Knoten enthalten sind
	 * @throws BacktrackingException => Wenn entweder das Gewicht oder die Items ungültige Werte haben
	 */
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
	
	/**
	 * Methode, die mit den Knoten und dem Level über Backtracking die beste Lösung errechnet.
	 * Die Lösung wird mithilfe der Schranke und dem bisher besten Knoten errechnet:
	 * Ist der jetzige Knoten besser als der bisher beste und auf dem Letzten Level,
	 * wird er als Bester gestzt.
	 * 
	 * @param k Knoten bei dem sich die Methode befindet
	 * @param level Level im Baum der Möglichkeiten auf dem sich das Programm befindet
	 */
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
	
	/**
	 * Funktioniert wie getBest, wobei der Verlauf (alle durchlaufenen Knoten) in verlauf abgespeichert wird.
	 * 
	 * @param i Items
	 * @param maxWeight Maxiamlgewicht
	 * @return Alle durchlaufenen Knoten
	 * @throws BacktrackingException Wenn Gewicht oder Items ungültig sind
	 */
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
	 /**
	  * Wie computeBest, nur dass der Verlauf in der ArrayList verlauf abgespeichert wird.
	  * 
	  * @param k Knoten bei dem man sich befindet
	  * @param level Level auf dem man sich befindet
	  */
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
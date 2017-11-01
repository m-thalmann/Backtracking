package net.tfobz.backtracking;

public class Backtracking{
	private Backtracking(){}
	
	public static int[] getBest(int[][] items, int maxWeight) throws BacktrackingException{
		int[] ret = new int[3];
		
		ret[0] = 0;
		ret[1] = 1;
		ret[2] = 2;
		
		return ret;
	}

	public static int[] getVerlauf(int[][] items, int maxWeight) throws BacktrackingException{
		return null;
	}
}
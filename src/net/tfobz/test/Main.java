package net.tfobz.test;

import net.tfobz.backtracking.Backtracking;
import net.tfobz.backtracking.BacktrackingException;

/**
 * 
 * @author matth
 *
 */

public class Main
{

	public static void main(String[] args){
		int[][] items = {{8, 8}, {16, 14}, {21, 16}, {17, 11}, {12, 7}};
		
		try {
			Backtracking.getBest(items, 38);
		} catch (BacktrackingException e) {
			e.printStackTrace();
		}
	}
	
}

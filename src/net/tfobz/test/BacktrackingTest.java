package net.tfobz.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import net.tfobz.backtracking.Backtracking;
import net.tfobz.backtracking.BacktrackingException;

import org.junit.Test;

public class BacktrackingTest
{
	private int[][] items = {{8, 8}, {16, 14}, {21, 16}, {17, 11}, {12, 7}};
	
	@Test
	public void test1(){
		int[] expected = {1, 2}; 
		assertArrayEquals(expected, Backtracking.getBest(items, 38));
	}
	
	@Test
	public void test2(){
		int[] expected = {0, 1, 2, 3, 4};
		assertArrayEquals(expected, Backtracking.getBest(items, Integer.MAX_VALUE));
	}
	
	@Test
	public void test3(){
		int[] expected = {0, 4};
		assertArrayEquals(expected, Backtracking.getBest(items, 20));
	}
	
	@Test
	public void test4(){
		assertEquals(0, Backtracking.getBest(items, 1).length);
	}
	
	@Test (expected=BacktrackingException.class)
	public void maxWeigthNull(){
		Backtracking.getBest(items, 0);
	}
	
	@Test (expected=BacktrackingException.class)
	public void maxWeigthNegativ(){
		Backtracking.getBest(items, -1);
	}

	@Test
	public void itemsNULL(){
		assertEquals(null, Backtracking.getBest(null, 38));
	}
	
	@Test (expected=BacktrackingException.class)
	public void itemsLengthNull(){
		Backtracking.getBest(new int[0][0], 38);
	}
	
}

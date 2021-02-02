/*-------------------
 * Author: Andrew Squires
 * 0300026151
 * CSI2120
 * Jan 25 2021
-------------------*/

import java.util.*;
import java.io.*;

public class KnapsackProblem {
	
	//turn this true for getting info about what the program is doing
	private final boolean VERBOSE = true;
	
	public static void main(String args[]) {
		try {
			String fileName = args[0];
			char solveType = Character.toUpperCase(args[1].charAt(0));
			KnapsackProblem knapsackProblem = new KnapsackProblem();
			knapsackProblem.solve(fileName, solveType);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Please specifiy the arguments");
		}
	}
	
	private Knapsack bestKnapsack;
	private void solve(String fileName, char solveType) {
		if(VERBOSE)System.out.println("Solving file "+fileName+"\n");
		
		long startTime = System.currentTimeMillis();
		
		int maxWeight = getMaxWeightFromFile(fileName);
		List<Item> items = getAllItemsFromFile(fileName);
		
		if(VERBOSE) {
			for(Item item:items) {
				System.out.println(item);
			}
		}

		bestKnapsack = new Knapsack(maxWeight);
		
		switch(solveType) {
		case 'F':
			if(VERBOSE)System.out.println("\nSolving via Brute Force");
			bruteForce(new Knapsack(maxWeight), items);
			break;
		case 'D':
			if(VERBOSE)System.out.println("\nSolving via Dynamic Table");
			dynamicSolve(maxWeight, items);
			break;
		default:
			System.out.println("You must choose D or F");
			break;
			
		}
		if(VERBOSE)System.out.println("BEST KNAPSACK: "+bestKnapsack);
		if(VERBOSE)System.out.println("TIME TAKEN = "+Long.toString((System.currentTimeMillis()-startTime)/1000)+"s");
		if(!outputToFile(fileName, bestKnapsack))System.out.println("Something went wrong sending to file");
	}
		
	private void bruteForce(Knapsack knapsack, List<Item> items) {
		//if we find a new knapsack is better than the current one
		if(knapsack.getCurrentValue()>bestKnapsack.getCurrentValue()) {
			bestKnapsack = new Knapsack(knapsack); //makes a copy but not a reference
		}
		if(!items.isEmpty()) {
			//try adding the item
			if(knapsack.addItem(items.get(0))) {
				bruteForce(knapsack,items.subList(1,items.size()));
				knapsack.removeItem(items.get(0));
			}
			//try skipping the item
			bruteForce(knapsack,items.subList(1,items.size()));
		}
	}
	
	private void dynamicSolve(int maxWeight, List<Item> items) {
		KTable ktable = new KTable(items.size(), maxWeight);
		ktable.populate(items);
		bestKnapsack = ktable.getKnapsackAt(items.size() , maxWeight);
		if(VERBOSE)System.out.println(ktable.toString());
	}
	
	private int getMaxWeightFromFile(String fileName) {
		try {
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			String weight="-1";
			while(sc.hasNext()) {
				weight=sc.next();
			}
			sc.close();
			return Integer.parseInt(weight);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	private List<Item> getAllItemsFromFile(String fileName) {
		try {
			ArrayList<Item> items = new ArrayList<Item>();
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			String str = "";
			str = sc.nextLine();
			int i=Integer.parseInt(str.split(" ")[0]);
			int a=0;
			while(sc.hasNextLine() && a<i) {
				String name = sc.next();
				int weight = Integer.parseInt(sc.next());
				int value = Integer.parseInt(sc.next());
				items.add(new Item(name,value,weight));
				a++;
			}
			sc.close();
			return items;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
	private boolean outputToFile(String fileName, Knapsack knapsack) {
		try{
			String toWrite = "";
			toWrite+=knapsack.getCurrentValue()+"\n";
			
			Comparator<Item> comparator = (i, o) -> i.getName().compareTo(o.getName()); //Just to sort in alphabetical order
			knapsack.getCurrentItems().sort(comparator);
			for(Item i:knapsack.getCurrentItems()) {
				toWrite+=i.getName()+" ";
			}
			FileWriter fWriter = new FileWriter(fileName.split("\\.")[0]+".sol");//remove the .txt add .sol
			if(VERBOSE)System.out.println("\nFILE OUTPUT:\n"+toWrite);
			fWriter.write(toWrite);
			fWriter.close();
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
}


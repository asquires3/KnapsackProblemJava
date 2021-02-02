/*-------------------
 * Author: Andrew Squires
 * 0300026151
 * CSI2120
 * Jan 25 2021
-------------------*/

import java.util.ArrayList;
import java.util.List;

public class Knapsack{

	private int maxWeight;
	private int currentWeight;
	private int currentValue;
	private List<Item> currentItems;
	
	Knapsack(int maxWeight){
		this.maxWeight = maxWeight;
		this.currentWeight = 0;
		this.currentValue = 0;
		this.currentItems = new ArrayList<Item>();
	}
	
	Knapsack(Knapsack knapsack){ 
		//basically makes a clone of another knapsack
		this.maxWeight = knapsack.getMaxWeight();
		this.currentValue = knapsack.getCurrentValue();
		this.currentWeight = knapsack.getCurrentWeight();
		this.currentItems = new ArrayList<Item>();
		for(Item item:knapsack.getCurrentItems()) {
			this.currentItems.add(item);
		}
	}
	
	public int getCurrentWeight() {
		return this.currentWeight;
	}
	
	public int getCurrentValue() {
		return this.currentValue;
	}
	
	public int getMaxWeight() {
		return this.maxWeight;
	}
	
	public List<Item> getCurrentItems(){
		return this.currentItems;
	}
	
	public boolean addItem(Item newItem){
		if(newItem.getWeight()+this.currentWeight<=this.maxWeight) { //only adds if it has enough space
			this.currentItems.add(newItem);
			this.currentValue+=newItem.getValue();
			this.currentWeight+=newItem.getWeight();
			return true;
		}
		return false;
	}
	
	public boolean removeItem(Item toRemove) {
		if(this.currentItems.remove(toRemove)) { //only subtract weight if it does remove
			this.currentWeight-=toRemove.getWeight();
			this.currentValue-=toRemove.getValue();
			return true;
		}
		return false;
	}
	
	public void addAll(Knapsack knapsack) { 
		//add all items in another knapsack to this one, useful for dynamic table
		for(Item i:knapsack.getCurrentItems()) {
			if(!this.currentItems.contains(i)) {//prevents duplicates
				this.addItem(i);
			}
		}
	}
	
	public String toString() {
		String items="";
		for(Item item:this.currentItems) {
			items+=item.toString()+",";
		}
		items = items.substring(0,items.length()-1);
		return "Knapsack(maxWeight="+Integer.toString(this.maxWeight)+",currentWeight="+Integer.toString(this.currentWeight)+
				",currentValue="+Integer.toString(this.currentValue)+",Items=["+items+"])";
	}
}
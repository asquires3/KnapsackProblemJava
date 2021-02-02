/*-------------------
 * Author: Andrew Squires
 * 0300026151
 * CSI2120
 * Jan 25 2021
-------------------*/

public class Item{


	private String name;
	private int weight;
	private int value;
	
	Item(String name, int weight, int value){
		this.name = name;
		this.weight = weight;
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return "Item{Name="+this.name+",Value="+Integer.toString(this.value)+",Weight="+Integer.toString(this.weight)+"}";
	}
}
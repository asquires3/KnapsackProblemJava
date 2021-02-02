/*-------------------
 * Author: Andrew Squires
 * 0300026151
 * CSI2120
 * Jan 25 2021
-------------------*/

import java.util.List;

public class KTable{
		private Knapsack[][] table;
		private final int numRows;
		private final int numCols;
		
		KTable(int numItems, int maxWeight){
			this.numRows = numItems+1;
			this.numCols = maxWeight+1;
			this.table = new Knapsack[numRows][numCols];
			for(int i=0;i<this.numCols;i++) {
				table[0][i]=new Knapsack(0);
			}
			for(int i=0;i<this.numRows;i++) {
				table[i][0]=new Knapsack(0);
			}
		}
		
		public int getNumCols() {
			return this.numCols;
		}
		
		public int getNumRows() {
			return this.numRows;
		}
				
		public Knapsack getKnapsackAt(int row, int col) {
			return table[row][col];
		}
		
		//from a list of items, populates itself 
		public void populate(List<Item> items) {
			for(int r=0;r<items.size();r++) {
				for(int c=1;c<this.numCols;c++) {
					this.table[r+1][c] = new Knapsack(c);//knapsack is made with capacity equal to column number
					if(items.get(r).getWeight()>c) { //if the new item cant fit, next best is the one above
						this.table[r+1][c].addAll(this.table[r][c]);
					}
					
					else { //if it can fit
						
						//if the one above is worth more value than the object and the most valuable set of items that can still fit
						//it will just choose the one above as more valuable
						if(this.table[r][c].getCurrentValue()>(items.get(r).getValue()+(this.table[r][c-items.get(r).getWeight()].getCurrentValue()))){
							this.table[r+1][c].addAll(this.table[r][c]);
						}
						//otherwise it adds the item and the most valuable set of items that still fits in the remaining space
						else {
							this.table[r+1][c].addItem(items.get(r));
							if((c-items.get(r).getWeight())>0) {
								this.table[r+1][c].addAll(this.table[r][c-items.get(r).getWeight()]);
							}
						}
					}
				}
			}
		}
		
		public String toString() {
			int max = 0;
			for(int r=0;r<this.numRows;r++) {
				for(int c=0;c<this.numCols;c++) {
					if(this.table[r][c].getCurrentValue()>max) {
						max = this.table[r][c].getCurrentValue();
					}
				}
			}
			
			String toReturn="";
			for(int r=0;r<this.numRows;r++) {
				for(int c=0;c<this.numCols;c++) {
					toReturn+=Integer.toString(this.table[r][c].getCurrentValue());
					for(int s=0;s<(Integer.toString(max).length()-Integer.toString(this.table[r][c].getCurrentValue()).length())+1;s++) {
						toReturn+=" ";
					}
				}
				toReturn+="\n";
			}
			return toReturn;
		}
	}
package Game;

import java.io.Serializable;

public class Item implements Serializable {

	private String name;
	private String weight;
	
	public Item(String n) {
		name = n;
		weight = "l";
	}
	
	public void setWeight(String x) {
		weight = x;
	}
	
	public String getItem() {
		return name;
	}
	
	
	public String getWeight() {
		return weight;
	}
	
	public void setItem(String n, String w)  {
		name = n;
		weight = w;
	}
	
	public String toString() {
		String s = name;
		return s;
	}
	
	public void use() {
		Game.print("You can not use this item!");
	}
}

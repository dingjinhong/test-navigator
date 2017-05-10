package testnavigator.mode;

import java.util.ArrayList;

public class Mode {
	String name;
	ArrayList<String> children=new ArrayList<String>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
}

package edu.ucla.cs.cs144;

public class SearchResult {
	private String itemId;
	private String name;
    
	public SearchResult() {}
    
	public SearchResult(String itemId, String name) {
		this.itemId = itemId;
		this.name = name;
	}
    
	public String getItemID() {
		return itemId;
	}
	
	public void setItemID(String itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

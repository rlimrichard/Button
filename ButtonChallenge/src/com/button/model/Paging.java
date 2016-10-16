package com.button.model;

public class Paging {
    private String previous;
    private String next;

    public Paging(String previous, String next){
        this.previous = previous;
        this.next = next;
    }
    
    public Paging(){
        this.previous = null;
        this.next = null;
    }
    
	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "Paging [previous=" + previous + ", next=" + next + "]";
	}
    
    
}
package com.button.model;

import java.util.List;

public class Posts {
    private  List<Post> data;
    private  Paging paging;

    public Posts(){
    	this.data = null;
        this.paging = new Paging();
    }
    
    public Posts(List<Post> data, Paging paging){
        this.data = data;
        this.paging = paging;
    }
    
	public List<Post> getData() {
		return data;
	}

	public void setData(List<Post> data) {
		this.data = data;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "Posts [data=" + data + ", paging=" + paging + "]";
	}
    
}
package com.button.model.facebook;

import java.util.List;

import com.button.model.Paging;
import com.button.model.Posts;

public class PostsFacebook extends Posts {

    private  List<PostFacebook> data;
    private  Paging paging;

    public PostsFacebook(){
    	this.data = null;
        this.paging = null;
    }
    
    public PostsFacebook(List<PostFacebook> data, Paging paging){
        this.data = data;
        this.paging = paging;
    }
    
	@Override
	public String toString() {
		String dataResult = "";
		for (int i=0 ; i<data.size() ; i++)
			dataResult = data.get(i) + "\n";
		
		return "Posts [data=" + dataResult + ", \npaging=" + paging.toString() + "]";
	}
	
	
	
}

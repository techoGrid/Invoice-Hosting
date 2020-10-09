package com.invoice.bean;

public class ResponseCA {
	private boolean success;
    private String message;  
    private Object object;
 
    public boolean isSuccess() {
        return success;
    }
 
    public void setSuccess(boolean success) {
        this.success = success;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
    
    
}

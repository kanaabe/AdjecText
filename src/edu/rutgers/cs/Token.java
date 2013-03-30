package edu.rutgers.cs;


public abstract class Token {
	
	public String val;
	public String type;
	
	public Token after = null;
	public Token before = null;
	
	
	public String toString(){		
		return val;
	}
	
	
}

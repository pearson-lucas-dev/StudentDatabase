package com.lucaspearson.studentdatabase;

public class Student {
public String fname;
public String lname;
public String email;
public int id;

@Override
public String toString(){
	return fname + " "+ lname +"\n"+email;
	
}
}

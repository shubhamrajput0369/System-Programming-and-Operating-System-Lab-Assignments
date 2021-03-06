import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter; 
import java.util.HashMap;

class Symbol
{
	String sname; 
	int addr;
	int length;

	Symbol(String name) 
	{
		this.sname = name;
	}
	
	Symbol(String name, int addr) 
	{
		this.sname = name;
		this.addr = addr;
	}

    void setLen(int l)
	{ 
		this.length = l;
	}
}

class Literal{
		String literal; 
		int addr;
}

public class PassOne{
		HashMap<String, Integer> POT = new HashMap <String, Integer>();
		HashMap<String, Integer> MOT = new HashMap <String, Integer>(); 
		HashMap<String, Integer> BCCODE = new HashMap <String, Integer>();
		HashMap<String, Integer> RT = new HashMap <String, Integer>(); 
		int PTAB[]=new int[10];


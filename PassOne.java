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

		Symbol ST[] = new Symbol[20]; 
		Literal LT[] = new Literal[20];
		int sIndex, lIndex, loc, pindex;

		PassOne(){
			sIndex = 0;
			lIndex = 0;
			loc = 0;
			pindex = 0; 
			PTAB[0] = 0;
		
			
			//imperative 
			MOT.put("STOP", 0);
			MOT.put("MOVER",1); 
			MOT.put("MOVEM",2);
			MOT.put("ADD", 3);
			MOT.put("SUB", 4); 
			MOT.put("MUL", 5);
			MOT.put("DIV", 6); 
			MOT.put ("BC", 7);

			//assembler dir
			POT.put("START", 1);
			POT.put("END" ,  2);
			POT.put("EQU",   3); 
			POT.put("LTORG", 4);
			POT.put("ORIGIN",5);
			POT.put("DS", 6);
			POT.put("DC", 7);


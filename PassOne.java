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
	
	

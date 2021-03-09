//Author: Shubham Bhagwansing Rajput
//Java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap; 
import java.util.Iterator;

class Symbol
{
	String sname;
	int addr;
	int length;

	Symbol(String name, int addr, int len) {
			this.sname = name; 
			this.addr = addr;
			this.length = len;
	}
}

class Literal {
	String literal; int addr;
	
	Literal(String lit, int addr){
			this.literal = lit;
			this.addr = addr;	
	}
}


class PassTwo
{
	
	HashMap<Integer, String> POT = new HashMap <Integer, String>(); 
	
	Symbol ST[] = new Symbol[20];
	Literal LT[] = new Literal[20];

	int PTAB[]=new int[10]; 
	int sIndex,lIndex, loc, pindex,pcount;

		PassTwo(){
		sIndex = 0; 
		lIndex = 0;
		loc = 0;
		pindex = 0;
		pcount = 0;

				POT.put(1, "START");
		POT.put(2, "END"); 
		POT.put(3, "EQU");
		POT.put (4, "LTORG");
		POT.put(5, "ORIGIN"); 
		//POT.put(6, "DS");
		//POT.put(7, "DC");
	}

	

	void initTables() throws Exception
	{
		File fSymTab = new File("symbolTable.txt");
		File flitTab = new File("literalTable.txt"); 
		File pTab = new File("POOLTable.txt"); 
		BufferedReader br = new BufferedReader (new FileReader (fSymTab));	
		
		String st=br.readLine();
		
		while((st = br.readLine()) != null){
			String token[] = st.split("\t");
			ST[sIndex] = new Symbol(token[0], Integer.parseInt(token[1]), Integer.parseInt(token[2]));
			sIndex++;		


	public static void main(String[] args) throws Exception {
			File f = new File("Pass1_output.txt"); 
			BufferedReader br = new BufferedReader(new FileReader (f));

			PassTwo obj = new PassTwo();
			obj.initTables();
			obj.passTwo(br);
	}

}

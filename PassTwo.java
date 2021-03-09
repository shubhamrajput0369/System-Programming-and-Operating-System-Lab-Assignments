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
		}
		br.close();
		br = new BufferedReader(new FileReader (flitTab));
		br.readLine();

		while((st = br.readLine()) != null){
			String token[] = st.split("\t");
			LT[lIndex] = new Literal(token[0], Integer.parseInt(token[1]));
			lIndex++;
		}

		br.close();
		br = new BufferedReader (new FileReader (pTab));
        	br.readLine();

		while((st = br.readLine()) != null){ 
			String token[] = st.split("\t");
			PTAB[pindex] = Integer.parseInt(token[0]);
			pindex++;
		}
		
		br.close();
		System.out.println("******Symbol Table*******"); 
		System.out.println("sIndex\tsName\tsAddr\tsLen");
		for (int i=0; i<sIndex; i++) {
			System.out.println(i+"\t"+ST[i].sname+"\t"+ST[i].addr+"\t"+ST[i].length);
		}
		
		System.out.println("\n***Literal Table***"); 
		System.out.println("Index\t]Name\tAddr");
		for(int i=0; i<lIndex; i++){
			System.out.println(i+"\t"+LT[i].literal+"\t"+LT[i].addr);
		}

		System.out.println("\n***POOL Table***");
		System.out.println("PIndex");
		for (int i=0; i<pindex; i++) {
			System.out.println(PTAB[i]);
		}
}

	void passTwo(BufferedReader br) throws Exception {
			String st = "";
			int k;
			FileWriter fw = new FileWriter("output.txt");

			System.out.println("\n\n***Machine Code***\n");
			while((st = br.readLine()) != null){ 
				
				String token[] = st.split(" ");
				//System.out.println(st);
				//check AD
				if(token[0].equals("AD")){
					String pot_content = POT.get(Integer.parseInt(token[1]));
					if (pot_content.equals("START")){
						if(token.length==4)
							loc = Integer.parseInt(token[3]);
						else
							loc = 0;
					}
					else if (pot_content.equals("END")){
							for (int i=PTAB[pcount]; i<lIndex; i++){ 
								System.out.println(loc+") 00 00"+LT[i].literal);
								fw.write(loc+") 00 00"+LT[i].literal+"\n");
								loc++;
							}
						pcount++;
					}
					else if (pot_content.equals("LTORG")){
							for (int i=PTAB[pcount]; i<PTAB[pcount+1]; i++){ 
								System.out.println(loc+") 00 00"+LT[i].literal);
								fw.write(loc+") 00 00"+LT[i].literal+"\n");
								loc++;
							}
						pcount++;
					}					

				}		
				else if (token[0].equals("DL")) {
					int c=Integer.parseInt(token[1]);
					if (c==2)
					{ 
						System.out.println(loc+") 00 00"+token[4]);
						fw.write(loc+") 00 00"+token[4]+"\n");
						loc+=1;
					}
					else	
					{
						System.out.println(loc+")");
						fw.write(loc+")\n");
						loc+=Integer.parseInt(token[4]);
					}
				}
	
				else{
					//IS
					//symbol
					if(token[3].equals("S")){
						int symAddr = ST[Integer.parseInt(token[4])].addr; 
						System.out.println(loc+") 0"+token[1]+" "+token[2]+" "+symAddr);
						fw.write(loc+") 0"+token[1]+" "+token[2]+" "+symAddr+"\n");
						loc+=1;
					}


	public static void main(String[] args) throws Exception {
			File f = new File("Pass1_output.txt"); 
			BufferedReader br = new BufferedReader(new FileReader (f));

			PassTwo obj = new PassTwo();
			obj.initTables();
			obj.passTwo(br);
	}

}

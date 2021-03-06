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

			//bccode
			BCCODE.put("LT", 1);
			BCCODE.put("LE", 2); 
			BCCODE.put("GT", 3);
			BCCODE.put("GE", 4); 
			BCCODE.put("EQ", 5);
			BCCODE.put("ANY",6);

			//Registers

			RT.put("AREG", 1);
			RT.put("BREG", 2); 
			RT.put("CREG", 3);
			RT.put("DREG", 4);

		}
		int search(String s){

		for(int i = 0; i<sIndex; i++){ 
			if(ST[i].sname.equals(s))
				return i;
			}
		return -1;
		}
		void printTables() throws Exception{

				FileWriter fw = new FileWriter("symbolTable.txt");
				fw.write("sname\taddress\tlength\n");

				for (int i=0; i<sIndex; i++){ 
					fw.write(ST[i].sname+"\t"+ST[i].addr+"\t"+ST[i].length+"\n");
				}
				fw.close();

				fw = new FileWriter("literalTable.txt");
				fw.write("lname\taddress\n");

				for(int i=0; i<lIndex; i++){ 
					fw.write(LT[i].literal+"\t"+LT[i].addr+"\n");
				}
				fw.close();

				fw = new FileWriter("poolTable.txt");
				fw.write("PoolIndex\n");
				for (int i=0; i<=pindex; i++){
					fw.write(PTAB[i]+"\n");
				}
				fw.close();
		}
	
void passOne(BufferedReader br) throws Exception {

			String st = "";
			int k;

			FileWriter fw = new FileWriter("Pass1_output.txt");

			while((st = br.readLine())!= null){
					String token[] = st.split("[ \t]+");
					
					if(token.length>4){
						System.out.println("invalid");
					} 
					else{
						if(token.length==4)
						{
							k = search(token[0]);
							if (k == -1){
								ST[sIndex] = new Symbol(token[0], loc);
								sIndex++;

							}
							else {
								System.out.println("duplicate Lable");
							}
						token[0]=token[1];
						token[1]=token[2];
						token[2]=token[3];
						}

						if(POT.containsKey(token[0]))
						{
								int val = POT.get(token[0]);

								if(token[0].equals("START")){ 
									if(token.length == 2) {
											loc = Integer.parseInt(token[1]);
									}
									else if(token.length>2){
										System.out.println("Error");
										fw.write("Error");
									}
									System.out.println("(AD "+val+") (C "+loc+")");
									fw.write("AD "+val+" C "+ loc +"\n"); 	//after correction
								}

								else if(token[0].equals("END")){ 
									for (int i=PTAB[pindex]; i<lIndex; i++) {
										LT[i].addr = loc;
										loc++;
									}
								System.out.println("(AD "+val+")"); 
								fw.write("AD "+val+"\n");
								}

								else if(token[0].equals("LTORG")){ 
									for(int i=PTAB[pindex]; i<lIndex; i++) {
									LT[i].addr = loc;
									loc++;
									}
									System.out.println("(AD "+val+")"); 
									fw.write("AD "+val+"\n");
									PTAB[++pindex]=lIndex;
								}
						}

						else if(MOT.containsKey(token[0])) 
						{
										int val = MOT.get(token[0]);
										int reg = 0,flag=0,cc_val=0;
										char ch;

										if(RT.containsKey(token[1])) 
											reg = RT.get(token[1]);

										if(BCCODE.containsKey(token[1]))
										{
											flag=1;
											cc_val = BCCODE.get(token[1]);
										}
										
										if(token[2].charAt(0) == '=') {
											ch = 'L';
											LT[lIndex] = new Literal();
											LT[lIndex].literal = token[2].substring(1);
											k = lIndex;
											lIndex++;
										} 

										else {
											ch = 'S';
											k = search(token[2]);
											if(k == -1){
												ST[sIndex] = new Symbol(token[2]);
												k = sIndex;
												sIndex++;
											}
										}
											if(flag==0){
												System.out.println("(IS "+val+") "+reg+" ("+ch+" "+k+")"); 
												fw.write("IS "+val+" "+reg+" "+ch+" "+k+"\n");
											}
											else if(flag==1){
												System.out.println("IS "+val+" "+cc_val+" "+ch+" "+k);
												fw.write("IS "+val+" "+cc_val+" "+ch+" "+k+"\n");
											}
												loc++;
			
											}
						
						else{
							if(token[1].equals("DS") || token[1].equals("DC")) {
									
									int len = Integer.parseInt(token[2]);
									k = search(token[0]);

									if (k == -1){
										ST[sIndex] = new Symbol(token[2]);
										k = sIndex;
										sIndex++;
									}

									if(token[1].equals("DS")){
										int val = POT.get(token[1]);
										System.out.println("(DL "+ val+") - (C "+token[2]+")"); 
										fw.write("DL 1 - C "+token[2]+"\n");
										ST[k].setLen(len);
										ST[k].addr = loc;
										loc+=len;
									}
									
									else{ 
										int val = POT.get(token[1]);
										System.out.println("(DL "+val+ ") - (C "+token[2]+")");
										fw.write("DL 2 - C "+token[2]+"\n"); 
										ST[k].setLen(1);
										ST[k].addr = loc;
										loc+=1;
									}
							}
					}
				}
			}
			fw.close();
			printTables();
			System.out.println("-----------SYMBOL TABLE------------");
			System.out.println("___________________________________");
			System.out.println("SIndex\tSname\tAddress\tLength");

			for (int i=0; i < sIndex; i++){	
				System.out.println(i+"\t"+ST[i].sname+"\t"+ST[i].addr+"\t"+ST[i].length);
			}

			System.out.println("-------LITERAL TABLE---------");
			System.out.println("_____________________________"); 
			System.out.println("Lindex\tliteral\tAddress");

			for(int i=0; i<lIndex; i++) 
			{ 
				System.out.println(i+"\t"+LT[i].literal+"\t"+LT[i].addr);
			}

			System.out.println("-------------POOL TABLE-----------");

			for (int i=0; i<=pindex; i++){
				System.out.println(PTAB[i]);
			}
			}
	public static void main(String[] args) throws Exception {

	File f = new File("input.txt");
	BufferedReader br = new BufferedReader(new FileReader(f));
	
	PassOne obj = new PassOne();

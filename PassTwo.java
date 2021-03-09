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


	public static void main(String[] args) throws Exception {
			File f = new File("Pass1_output.txt"); 
			BufferedReader br = new BufferedReader(new FileReader (f));

			PassTwo obj = new PassTwo();
			obj.initTables();
			obj.passTwo(br);
	}

}

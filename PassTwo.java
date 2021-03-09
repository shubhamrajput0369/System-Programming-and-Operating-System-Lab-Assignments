//Author: Shubham Bhagwansing Rajput
//Java

	public static void main(String[] args) throws Exception {
			File f = new File("Pass1_output.txt"); 
			BufferedReader br = new BufferedReader(new FileReader (f));

			PassTwo obj = new PassTwo();
			obj.initTables();
			obj.passTwo(br);
	}

}

package inb_bach;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class CodePermutation {
	String[] Code={"Leu","Pro","His","Gln","Arg","Ile","Met","Thr","Asn","Lys","Ser","Val","Ala","Asp","Glu","Gly","Phe","Tyr","Cys","Trp"};
	private int Count=0;
	private FileWriter fw;
	private Random rnd;
	
	public void generateCodes(){
		String[] Snip=new String[20];
		try {
			fw=new FileWriter("data/codes.txt");
		} catch (IOException e) {
			System.out.println("FileWriter Error!");
			return;
		}
		rnd=new Random();
		for (int i=0;i<10000000;i++){
			writeRandomCode();
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void writeRandomCode(){
		String[] rCode=new String[20];
		for (int i=0;i<20;i++){
			boolean found=false;
			int acid=0;
			while (found==false){
				acid=rnd.nextInt(20);
				if (!(Contains(rCode,Code[acid],i)))found=true;;
			}
			rCode[i]=Code[acid];
		}
		Count++;
		try {
			fw.write(Count+"  "+rCode[0]+"~"+rCode[1]+"~"+rCode[2]+"~"+rCode[3]+"~"+rCode[4]+"~"+rCode[5]+"~"+rCode[6]+"~"+rCode[7]+"~"+rCode[8]+"~"+rCode[9]+"~"+rCode[10]+"~"+rCode[11]+"~"+rCode[12]+"~"+rCode[13]+"~"+rCode[14]+"~"+rCode[15]+"~"+rCode[16]+"~"+rCode[17]+"~"+rCode[18]+"~"+rCode[19]+'\n');
		} catch (IOException e) {
			System.out.println("FileWriter Error on "+Count);
		}
		if (Count%100000==0)System.out.println("Generated "+Count+" Codes...");
		return;
	}
	private boolean Contains(String[]Snip, String Code, int Level){
		for (int i=0;i<Level;i++){
			if (Snip[i].equals(Code))return true;
		}
		return false;
	}
	public void CalculateStabilities(String Filename){
		for (int i=0;i<10000000;i++){
		     new Calculator().start();
		}
	}
	
	public class Calculator extends Thread {

	    public void run() {
	    	long test=0;
	    	for (int i=0;i<999999;i++){
	    		test=test+i;
	    	}
	        System.out.println("Hello from a thread!");
	    }
	}
}


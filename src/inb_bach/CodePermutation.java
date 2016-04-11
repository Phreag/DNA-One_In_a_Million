package inb_bach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import Objects.GeneCode;

public class CodePermutation {
	String[] Code={"Leu","Pro","His","Gln","Arg","Ile","Met","Thr","Asn","Lys","Ser","Val","Ala","Asp","Glu","Gly","Phe","Tyr","Cys","Trp"};
	private int CodeCount=0;
	private int ValueCount=0;
	private FileWriter codes;
	private FileWriter values;
	private Random rnd;
	
	public void generateCodes(){
		rnd=new Random();
		File f=new File("data/codes.txt");
		if (f.exists()){
			System.out.println("codes.txt existiert bereits.");
			return;
		}
		try {
			codes=new FileWriter("data/codes.txt");
			GeneCode g=new GeneCode();
			//1st is Natural code
	    	writeCodeLine(Code);
	    	
	    	for (int i=0;i<1000000;i++){
		    	String[] rCode=getRandomCode();
		    	writeCodeLine(rCode);
		    }
	    	codes.close();
		} catch (IOException e) {
			System.out.println("FileWriter Error!");
			return;
		}
	}
	public void calculateValues(){
		try {
			values=new FileWriter("data/codeValues.csv");
			values.write("Code#, MS1, MS2, MS3, rMS, lMS, MS0, fMS"+'\n');
		} catch (IOException e) {
			System.out.println("FileWriter Error!");
			return;
		}
		GeneCode g=new GeneCode();
    	StabilityCalculator S=new StabilityCalculator(g);
    	S.setBaseWeighting(MainClass.factors.clone());
    	S.setTripletWeighting(MainClass.tweights.clone());
    	BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("data/Codes.txt"));
			String line = null;
	    	while ((line = br.readLine()) != null) {
	    		String[] Mapping=line.split(" ")[1].split("~");
	    		g.changeCode(Mapping);
		    	double MS1=S.get_Deviation(1);
		    	double MS2=S.get_Deviation(2);
		    	double MS3=S.get_Deviation(3);
		    	double rMS=S.get_Deviation(4);
		    	double lMS=S.get_Deviation(5);
		    	double MS0=S.getMS0(MS1, MS2, MS3);
		    	double fMS=S.getfMS(rMS, lMS);
		    	writeValueLine(MS1, MS2, MS3, rMS, lMS, MS0, fMS);
	    	}
	    	br.close();
	    	values.close();
		} catch (FileNotFoundException e) {
			System.out.println("FEHLER: Codes wurden noch nicht generiert!");
			return;
		} catch (IOException e) {
			System.out.println("FEHLER: IOException");
			return;
		}
	}
	
	private void writeValueLine(double MS1, double MS2, double MS3, double rMS, double lMS, double MS0, double fMS){
		ValueCount++;{
		try {
			values.write(ValueCount+","+MS1+","+MS2+","+MS3+","+rMS+","+lMS+","+MS0+","+fMS+'\n');
		} catch (IOException e) {
			System.out.println("FileWriter Error on "+ValueCount);
		}
		if (ValueCount%10000==0)System.out.println("Calculated "+ValueCount+" Codes with stabilities");
		return;
		}
	}
	
	private void writeCodeLine(String[] rCode){
		CodeCount++;{
		try {
			codes.write(CodeCount+" "+rCode[0]+"~"+rCode[1]+"~"+rCode[2]+"~"+rCode[3]+"~"+rCode[4]+"~"+rCode[5]+"~"+rCode[6]+"~"+rCode[7]+"~"+rCode[8]+"~"+rCode[9]+"~"+rCode[10]+"~"+rCode[11]+"~"+rCode[12]+"~"+rCode[13]+"~"+rCode[14]+"~"+rCode[15]+"~"+rCode[16]+"~"+rCode[17]+"~"+rCode[18]+"~"+rCode[19]+'\n');;
		} catch (IOException e) {
			System.out.println("FileWriter Error on "+CodeCount);
		}
		if (CodeCount%10000==0)System.out.println("Generated "+CodeCount+" Codes");
		return;
		}
	}
	
	private String[] getRandomCode(){
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
		return rCode;
	}
	private boolean Contains(String[]Snip, String Code, int Level){
		for (int i=0;i<Level;i++){
			if (Snip[i].equals(Code))return true;
		}
		return false;
	}
}


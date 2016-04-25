package inb_bach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import Objects.GeneCode;

public class CodePermutation {
	String[] Code={"Leu","Pro","His","Gln","Arg","Ile","Met","Thr","Asn","Lys","Ser","Val","Ala","Asp","Glu","Gly","Phe","Tyr","Cys","Trp"};
	String[] RegC={"Phe","Leu","Ile","Met","Val","Ser","Pro","Thr","Ala","Tyr","His","Gln","Asn","Lys","Asp","Glu","Cys","Trp","Arg","Gly"};
	int[] DiffCode={16,   -1,   3,    3,     7,    5,   -5,    0,    4,    8,    -8, -8,    -4,    -4,   -1,   -1,   2,    2,    -14,   -4};
	private int CodeCount=0;
	private String[] ValueBuffer;
	private List<String> CodeBuffer;
	private int nextValue=0;
	private FileWriter codes;
	private FileWriter values;
	private Random rnd;
	private int Threads=10;
	private int ThreadsFinished=0;
	
	public class ThreadedCalculator extends Thread {
	    public void run() {
	    	GeneCode g=new GeneCode();
	    	//Weighting Parameters here
	    	StabilityCalculator S=new StabilityCalculator(g);
	    	//S.setBaseAprioriWeighting(MainClass.baseAprioriWeights);
	    	//S.setTripletAprioriWeighting(MainClass.tripletAprioriWeights);
	    	S.setTripletTransitionWeighting(MainClass.tripletTransitionWeights);
	    	while (true){
	    		int currentCode=getNextValue();
	    		
	    		//if (currentCode%10000==0)System.out.println("Generating Code "+currentCode);
	    		if(currentCode>=CodeBuffer.size())break;
	    		String[] rCode=CodeBuffer.get(currentCode).split(" ")[1].split("~");
	    		g.changeCode(rCode);
	    		double MS1=S.get_BaseDeviation(1);
		    	double MS2=S.get_BaseDeviation(2);
		    	double MS3=S.get_BaseDeviation(3);
		    	double rMS=S.get_ShiftDeviation(1);
		    	double lMS=S.get_ShiftDeviation(2);
		    	double MS0=S.getMS0(MS1, MS2, MS3);
		    	double fMS=S.getfMS(rMS, lMS);
	    		String Line=MS1+","+MS2+","+MS3+","+MS0+","+rMS+","+lMS+","+fMS;
	    		ValueBuffer[currentCode]=Line;
	    	}
	    	System.out.println("Threads finished: "+(++ThreadsFinished));
	    }
	}
	
	private String[] ConvertCode(String[] Import){
		String[]C=new String[20];
		for (int i=0;i<20;i++){
			C[i+DiffCode[i]]=Import[i];
		}
		return C;
	}
	
	public void importCodes(){
		BufferedReader br;
		try {
			codes=new FileWriter("data/codes.txt");
			br = new BufferedReader(new FileReader("data/CodeImport.txt"));
			String line = null;
			CodeCount=0;
	    	while ((line = br.readLine()) != null) {
		    	String[]Temp=line.split(",");
		    	String[]ImportedCode=new String[20];
		    	for (int i=0;i<20;i++){
		    		ImportedCode[i]=RegC[Integer.parseInt(Temp[i])-1];
		    	}
		    	writeCodeLine(ConvertCode(ImportedCode));
	    	}
	    	br.close();
	    	codes.close();
		} catch (Exception e) {
			System.out.println("FEHLER:");
			e.printStackTrace();
			return;
		}
	}

	public void calculateValues(){
		//Initialize Writer for Value Output
		try {
			values=new FileWriter("data/codeValues.csv");
		} catch (IOException e) {
			System.out.println("FileWriter Error!");
			return;
		}
		
		//Cache all Codes into Buffer
		CodeBuffer=new ArrayList<String>();
		System.out.println("Buffering Codes...");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("data/Codes.txt"));
			String line = null;
	    	while ((line = br.readLine()) != null) {
	    		CodeBuffer.add(line);
	    	}
	    	br.close();
		} catch (Exception e) {
			System.out.println("FEHLER: Filereader Error");
			return;
		}
		//Intitalize output Buffer Array
		ValueBuffer=new String[CodeBuffer.size()];
		
		System.out.println("Start calculation with " +Threads+" Threads...");
		//Start the Calculation Threads
		for (int i=0;i<Threads;i++){
			new ThreadedCalculator().start();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Write Task to save the Results
		//Sleeps for 10ms if it is too fast
		for (int i=0;i<CodeBuffer.size();i++){
			int timeout=0;
			boolean repeat=false;
			if (ValueBuffer[i]==null){
				if (ThreadsFinished==Threads)break;
				try {
					Thread.sleep(100);
					if (ValueBuffer[i]!=null)repeat=true;;
					timeout+=100;
				} catch (InterruptedException e) {}
				if (timeout>3000)System.out.println("Timeout: "+timeout);
			}
			if (repeat){
				i--;
			}else{
				writeValueLine(i+1, ValueBuffer[i]);
			}
		}
		try {
			values.close();
		} catch (IOException e) {}
	}
	private synchronized int getNextValue(){
		return nextValue++;
	}
	
	
	
	private void writeValueLine(int Number,String Line){
		//Writes Value Line To File
		try {
			values.write(Number+","+Line+'\n');
		} catch (IOException e) {
			System.out.println("FileWriter Error on "+Line);
		}
		if (Number%10000==0)System.out.println("Calculated "+Number+" Codes with stabilities");

	}
	
	//Writes random generated Code line to File
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
	
	
	//returns a random generated Code
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
}


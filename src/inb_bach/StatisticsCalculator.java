package inb_bach;

import java.text.DecimalFormat;

public class StatisticsCalculator {
	static DecimalFormat df = new DecimalFormat("0.0000"); 
	private String[]Bases={"T","C","A","G"};
	
	public double[][] getMatrix(String seq){
		System.out.println("Berechne Übergangsmatrix...");
		//C - T - A - G
		int[][] Sum=new int[4][4];
		double[][]Proz=new double[4][4];
		for (int i=0;i<seq.length()-1;i++){
			int y=5;//G
			if (seq.charAt(i)=='C'){y=0;}
			if (seq.charAt(i)=='T'){y=1;}
			if (seq.charAt(i)=='A'){y=2;}
			if (seq.charAt(i)=='G'){y=3;}
			int x=5;//G
			if (seq.charAt(i+1)=='C'){x=0;}
			if (seq.charAt(i+1)=='T'){x=1;}
			if (seq.charAt(i+1)=='A'){x=2;}
			if (seq.charAt(i+1)=='G'){x=3;}
			if (x==5||y==5){continue;}//Ignore others
			Sum[x][y]+=1;
		}
		for (int i=0;i<4;i++){
			int Summe=Sum[0][i]+Sum[1][i]+Sum[2][i]+Sum[3][i];
			for (int j=0;j<4;j++){
				Proz[j][i]=((double)Sum[j][i]/(double)Summe)*4;
			}
		}
		//PrintMatrix(Proz);
		return Proz;
	}
	//Berechnet die Elementeweise Differenz zwischen den beiden Input-Matrizen
	public double[][] MatrixDiff(double[][] M1,double[][] M2){
		double[][]Erg=new double[4][4];
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				Erg[i][j]=M1[i][j]-M2[i][j];
			}
		}
		double sum=0;
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				sum+=Math.abs(Erg[i][j]);
			}
		}
		System.out.println("Gesamtdiferenz: "+df.format(sum));
		PrintMatrix(Erg);
		return Erg;
	}
	//Druckt eine 4x4 Übergangsmatrix auf der Konsole aus
	public void PrintMatrix(double[][] Proz){
		System.out.println("Vertikal: s(n) horizontal: s(n+1)");
		System.out.println("--- C ------- T ------- A ------- G");
		System.out.println("C "+df.format(Proz[0][0])+" -- "+df.format(Proz[1][0])+" -- "+df.format(Proz[2][0])+" -- "+df.format(Proz[3][0]));
		System.out.println("T "+df.format(Proz[0][1])+" -- "+df.format(Proz[1][1])+" -- "+df.format(Proz[2][1])+" -- "+df.format(Proz[3][1]));
		System.out.println("A "+df.format(Proz[0][2])+" -- "+df.format(Proz[1][2])+" -- "+df.format(Proz[2][2])+" -- "+df.format(Proz[3][2]));
		System.out.println("G "+df.format(Proz[0][3])+" -- "+df.format(Proz[1][3])+" -- "+df.format(Proz[2][3])+" -- "+df.format(Proz[3][3]));	
	}
	public double[] getNucleotideDistribution(String seq){
		int[]stat={0,0,0,0};
		for (int i=0;i<seq.length();i++){
			if(seq.charAt(i)=='T'){
				stat[0]++;
			}else if(seq.charAt(i)=='C'){
				stat[1]++;
			}else if(seq.charAt(i)=='A'){
				stat[2]++;
			}else if(seq.charAt(i)=='G'){
				stat[3]++;
			}
		}
		int count=stat[0]+stat[1]+stat[2]+stat[3];
		double[] proz={((double)stat[0]/(double)count),((double)stat[1]/(double)count),((double)stat[2]/(double)count),((double)stat[3]/(double)count)};
		System.out.println("Nucleotide Distribution:");
		System.out.println("  T:"+df.format(proz[0])+" C:"+df.format(proz[1])+" A:"+df.format(proz[2])+" G:"+df.format(proz[3])+"");
		return proz;
	}
	
	public double[][][] getTripletDistribution(String seq){
		int[][][]triplets=new int[4][4][4];
		int count=0;
		System.out.println("Berechne Triplet-Verteilung (ohne Leseraster)");	
		for (int x=0;x<seq.length()-3;x++){
			int i=-1;
			int j=-1;
			int k=-1;
			switch(seq.charAt(x)){//1. Base
			case 'T':
				i=0;break;
			case 'C':
				i=1;break;
			case 'A':
				i=2;break;
			case 'G':
				i=3;break;
			}
			
			switch(seq.charAt(x+1)){
			case 'T':
				j=0;break;
			case 'C':
				j=1;break;
			case 'A':
				j=2;break;
			case 'G':
				j=3;break;
			}
			
			switch(seq.charAt(x+2)){
			case 'T':
				k=0;break;
			case 'C':
				k=1;break;
			case 'A':
				k=2;break;
			case 'G':
				k=3;break;
			}
			if(i==-1||j==-1||k==-1)continue;
			String Pattern=Bases[i]+Bases[j]+Bases[k];
			if (Pattern.equalsIgnoreCase("TAA")||Pattern.equalsIgnoreCase("TAG")||Pattern.equalsIgnoreCase("TGA"))continue;
			if (seq.substring(x,x+3).equalsIgnoreCase(Pattern)){
				triplets[i][j][k]++;
				count++;
			}
		}
		double[][][]relativeCount=new double[4][4][4];
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				for (int k=0;k<4;k++){
					
					relativeCount[i][j][k]=((double)triplets[i][j][k]*61)/(double)count;
					//System.out.println(Bases[i]+Bases[j]+Bases[k]+" - "+relativeCount[i][j][k]);
				}
			}
		}
		double sum=0;
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				for (int k=0;k<4;k++){
					String Triplet=Bases[i]+Bases[j]+Bases[k];
					if (Triplet.equalsIgnoreCase("TAA")||Triplet.equalsIgnoreCase("TAG")||Triplet.equalsIgnoreCase("TGA"))continue;
					sum+=relativeCount[i][j][k];
					//System.out.println(Bases[i]+Bases[j]+Bases[k]+" - "+relativeCount[i][j][k]);
				}
			}
		}
		sum=sum/61;
		//System.out.println("Average:" +sum);
		return relativeCount;
	}
}

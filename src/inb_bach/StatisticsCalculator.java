package inb_bach;

import java.text.DecimalFormat;

public class StatisticsCalculator {
	static DecimalFormat df = new DecimalFormat("0.0000"); 
	
	public double[][] getMatrix(String seq){
		System.out.println("");
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
				Proz[i][j]=(double)Sum[j][i]/(double)Summe;
			}
		}
		//PrintMatrix(Proz);
		return Proz;
	}
	//Berechnet die Elementeweise Different zwischen den beiden Input-Matrizen
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
	

	//Test Methode zum Berechnen von Häufigkeiten
	public void Statistics(String seq){
		String[]LeuCodes={"CTT", "CTC", "CTA", "CTG", "TTA", "TTG"};
		String Start="ATG";
		String[]Stop={"TGA","TAG","TAA"};
		System.out.println("");
		System.out.println("#### Statistics for nucleotides after a specific triplet: ####");
		for (int i=0;i<LeuCodes.length;i++){
			int count=0;
			int[]stat={0,0,0,0};
			for (int j=0;j<seq.length()-3;j++){
				if (seq.substring(j, j+3).equals(LeuCodes[i])){
					count++;
					if(seq.substring(j+3, j+4).equals("C")){
						stat[0]++;
					}else if(seq.substring(j+3, j+4).equals("T")){
						stat[1]++;
					}else if(seq.substring(j+3, j+4).equals("A")){
						stat[2]++;
					}else if(seq.substring(j+3, j+4).equals("G")){
						stat[3]++;
					}
				}
			}
			
			double[] proz={((double)stat[0]/(double)count)*100,((double)stat[1]/(double)count)*100,((double)stat[2]/(double)count)*100,((double)stat[3]/(double)count)*100};
			System.out.println("Current triplet: '"+LeuCodes[i]+"' Total count:"+count);
			System.out.println("  C:"+df.format(proz[0])+"% T:"+df.format(proz[1])+"% A:"+df.format(proz[2])+"% G:"+df.format(proz[3])+"%");
			System.out.println("  C:"+stat[0]+" T:"+stat[1]+" A:"+stat[2]+" G:"+stat[3]);
		}
		System.out.println("");
		System.out.println("#### Statistics for nucleotides in front of a specific triplet: ####");
		for (int i=0;i<LeuCodes.length;i++){
			int count=0;
			int[]stat={0,0,0,0};
			for (int j=1;j<seq.length()-2;j++){
				if (seq.substring(j, j+3).equals(LeuCodes[i])){
					count++;
					if(seq.substring(j-1, j).equals("C")){
						stat[0]++;
					}else if(seq.substring(j-1, j).equals("T")){
						stat[1]++;
					}else if(seq.substring(j-1, j).equals("A")){
						stat[2]++;
					}else if(seq.substring(j-1, j).equals("G")){
						stat[3]++;
					}
				}
			}
			
			double[] proz={((double)stat[0]/(double)count)*100,((double)stat[1]/(double)count)*100,((double)stat[2]/(double)count)*100,((double)stat[3]/(double)count)*100};
			System.out.println("Current triplet: '"+LeuCodes[i]+"' Total count:"+count);
			System.out.println("  C:"+df.format(proz[0])+"% T:"+df.format(proz[1])+"% A:"+df.format(proz[2])+"% G:"+df.format(proz[3])+"%");
			System.out.println("  C:"+stat[0]+" T:"+stat[1]+" A:"+stat[2]+" G:"+stat[3]);
		}
		int count=0;
		int[]stat={0,0,0,0};
		for (int i=0;i<seq.length();i++){
			
			count++;
			if(seq.charAt(i)=='C'){
				stat[0]++;
			}else if(seq.charAt(i)=='T'){
				stat[1]++;
			}else if(seq.charAt(i)=='G'){
				stat[2]++;
			}else if(seq.charAt(i)=='A'){
				stat[3]++;
			}
			
		}
		System.out.println("");
		System.out.println("Total distribution of nucleotides in the given sequence:");
		double[] proz={((double)stat[0]/(double)count)*100,((double)stat[1]/(double)count)*100,((double)stat[2]/(double)count)*100,((double)stat[3]/(double)count)*100};
		System.out.println("  C:"+df.format(proz[0])+"% T:"+df.format(proz[1])+"% A:"+df.format(proz[2])+"% G:"+df.format(proz[3])+"%");
		System.out.println("  C:"+stat[0]+" T:"+stat[1]+" A:"+stat[2]+" G:"+stat[3]);
	}

}

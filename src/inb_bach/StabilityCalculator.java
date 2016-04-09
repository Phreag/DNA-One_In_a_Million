package inb_bach;

import Objects.Constants;
import Objects.GeneCode;

public class StabilityCalculator {
	private GeneCode Code;
	private String[]Bases={"T","C","A","G"};
	private boolean baseweighting=false;
	private double[] baseWeights;
	private boolean tripletWeighting=false;
	private double[][][] tripletWeights;
	/* Deviationmode:
	 * 1=MS1 - Default
	 * 2=MS2
	 * 3=MS3
	 * 4=rMS
	 * 5=lMS
	 */
	public StabilityCalculator(GeneCode Code){
		this.Code=Code;
	}
	public void setBaseWeighting(double[] weighting){
		baseweighting=true;
		baseWeights=weighting;
	}
	public void setTripletWeighting(double[][][] weighting){
		tripletWeighting=true;
		tripletWeights=weighting;
	}
	
	public double get_Deviation(int Modus){
		if(!(Modus>=1&&Modus<=5)){
			System.out.println("Fehlerhafter Modus: Zahlen von 1-5 erlaubt.");
			return 0;
		}
		double deviation=0.0;
		for (int i=0;i<4;i++){
			String a=Bases[i];
			for (int j=0;j<4;j++){
				String b=Bases[j];
				for (int k=0;k<4;k++){
					String c=Bases[k];
					String Amino=Code.getAminoAcid(a+b+c);
					if (Amino.length()!=3)continue; //Filtert Stop Codons
					double Polar1=Constants.getPolarReq(Amino);
					Double Diff=0.0;
					for (int m=0;m<4;m++){
						String x=Bases[m];
						String Amino2="";
						switch (Modus){
						case 1:
							Amino2=Code.getAminoAcid(x+b+c);
							break;
						case 2:
							Amino2=Code.getAminoAcid(a+x+c);
							break;
						case 3:
							Amino2=Code.getAminoAcid(a+b+x);
							break;
						case 4:
							Amino2=Code.getAminoAcid(x+a+b);
							break;
						case 5:
							Amino2=Code.getAminoAcid(b+c+x);
							break;
						}
						if (Amino2.length()!=3)continue; //Filtert Stop Codons
						double Polar2=Constants.getPolarReq(Amino2);
						double difference=(Polar1-Polar2)*(Polar1-Polar2);
						if (baseweighting){
							difference=difference*baseWeights[m];
						}
						if (tripletWeighting){
							difference=difference*tripletWeights[i][j][k];
						}
						Diff+=difference;
					}
					deviation=deviation+Diff;
				}
			}
		}
		switch (Modus){
		case 1:
			deviation=deviation/174;
			System.out.println("MS1: "+deviation );
			break;
		case 2:
			deviation=deviation/176;
			System.out.println("MS2: "+deviation );
			break;
		case 3:
			deviation=deviation/176;
			System.out.println("MS3: "+deviation );
			break;
		case 4:
			deviation=deviation/232;
			System.out.println("rMS: "+deviation );
			break;
		case 5:
			deviation=deviation/232;
			System.out.println("lMS: "+deviation );
			break;
		}
		return deviation;
	}
	
	
	
	public double get_rMS(){
		double deviation=0.0;
		for (String a:Bases){
			for (String b:Bases){
				for (String c:Bases){
					String Amino=Code.getAminoAcid(a+b+c);
					if (Amino.length()!=3)continue; //Filtert Stop Codons
					double Polar1=Constants.getPolarReq(Amino);
					Double Diff=0.0;
					for (String x:Bases){
						String Amino2=Code.getAminoAcid(x+a+b);
						if (Amino2.length()!=3)continue; //Filtert Stop Codons
						double Polar2=Constants.getPolarReq(Amino2);
						double difference=(Polar1-Polar2);
						Diff=Diff+(difference)*(difference);
					}
					deviation=deviation+Diff;
				}
			}
		}
		deviation=deviation/232;
		System.out.println("rMS: "+deviation );
		return deviation;
	}
	
	public double get_lMS(){
		double deviation=0.0;
		for (String a:Bases){
			for (String b:Bases){
				for (String c:Bases){
					String Amino=Code.getAminoAcid(a+b+c);
					if (Amino.length()!=3)continue; //Filtert Stop Codons
					double Polar1=Constants.getPolarReq(Amino);
					Double Diff=0.0;
					for (String x:Bases){
						String Amino2=Code.getAminoAcid(b+c+x);
						if (Amino2.length()!=3)continue; //Filtert Stop Codons
						double Polar2=Constants.getPolarReq(Amino2);
						double difference=(Polar1-Polar2);
						Diff=Diff+(difference)*(difference);
					}
					deviation=deviation+Diff;
				}
			}
		}
		deviation=deviation/232;
		System.out.println("lMS: "+deviation );
		return deviation;
	}
}

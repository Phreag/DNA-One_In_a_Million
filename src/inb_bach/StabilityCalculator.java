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
	private int Bias=1;
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
	public void ChangeCode(String[] Mapping){
		Code.changeCode(Mapping);
	}
	public void setBaseWeighting(double[] weighting){
		baseweighting=true;
		baseWeights=weighting;
	}
	public void setTripletWeighting(double[][][] weighting){
		tripletWeighting=true;
		tripletWeights=weighting;
	}
	public void setTransitionTransversionBias(int Bias){
		this.Bias=Bias;
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
						if(Bias!=1&&Modus<4){
							String from="";
							switch (Modus){
							case 1:
								from=a;
								break;
							case 2:
								from=b;
								break;
							case 3:
								from=c;
								break;
							}
							if (isTransition(from, x)){
								difference=difference*Bias;
							}
						}
						Diff+=difference;
					}
					deviation=deviation+Diff;
				}
			}
		}
		switch (Modus){
		case 1:
			if (Bias==1){
				deviation=deviation/174;
			}else{
				deviation=deviation/(174+(Bias*58));
			}
			//System.out.println("MS1: "+deviation );
			break;
		case 2:
			if (Bias==1){
				deviation=deviation/176;
			}else{
				deviation=deviation/(176+(Bias*60));
			}
			//System.out.println("MS2: "+deviation );
			break;
		case 3:
			if (Bias==1){
				deviation=deviation/176;
			}else{
				deviation=deviation/(176+(Bias*60));
			}
			//System.out.println("MS3: "+deviation );
			break;
		case 4:
			deviation=deviation/232;
			//System.out.println("rMS: "+deviation );
			break;
		case 5:
			deviation=deviation/232;
			//System.out.println("lMS: "+deviation );
			break;
		}
		return deviation;
	}
	
	private boolean isTransition(String from, String to){
		if(from.equalsIgnoreCase("A")&&to.equalsIgnoreCase("G"))return true;
		if(from.equalsIgnoreCase("G")&&to.equalsIgnoreCase("A"))return true;
		if(from.equalsIgnoreCase("C")&&to.equalsIgnoreCase("T"))return true;
		if(from.equalsIgnoreCase("T")&&to.equalsIgnoreCase("C"))return true;
		return false;
	}
	
	public double getWMS0(int Bias, double WMS1, double WMS2, double WMS3){
		WMS1=WMS1*(174+(Bias*58));
		WMS2=WMS2*(176+(Bias*60));
		WMS3=WMS3*(176+(Bias*60));
		return (WMS1+WMS2+WMS3)/((174+(Bias*58))+176+(Bias*60)+176+(Bias*60));
	}
	
	public double getMS0(double MS1, double MS2, double MS3){
		MS1=MS1*174;
		MS2=MS2*176;
		MS3=MS3*176;
		return(MS1+MS2+MS3)/(174+176+176);
	}
	
	public double getfMS(double rMS, double lMS){
		rMS=rMS*232;
		lMS=lMS*232;
		return(rMS+lMS)/(232+232);
	}
}

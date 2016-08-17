package inb_bach;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.io.FastaReader;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.GenericFastaHeaderParser;
import org.biojava.nbio.core.sequence.io.ProteinSequenceCreator;

import Objects.GeneCode;
import Objects.SequenceStats;

public class MainClass {
	static String[] Code={"Leu","Pro","His","Gln","Arg","Ile","Met","Thr","Asn","Lys","Ser","Val","Ala","Asp","Glu","Gly","Phe","Tyr","Cys","Trp"};
	static DecimalFormat df = new DecimalFormat("0.0000"); 
	static GenBankConnection conn=new GenBankConnection();
	public static double[] baseAprioriWeights;
	public static double[][][] tripletAprioriWeights;
	public static double[][] baseTransitionWeights;
	public static double[][][][][]tripletTransitionWeights;
	public static boolean baseAprioriEnabled=false;
	public static boolean tripletAprioriEnabled=false;
	public static boolean baseTransitionEnabled=false;
	public static boolean tripletTransitionEnabled=false;
	public static int TransitionTransversionBias=1;
	public static void main (String[] args){
		//###################################################################
		//#########################   DEBUG AREA   ##########################
		//###################################################################
		
		// 55417888 = 33MB Mixed
		// 51847843 = 158 MB Mixed Chromosom 7
		// 568815597 = 250 MB Mixed Chromosom 1
		// 671162122 = 32 MB Drosophila melanogaster chromosome 2R
//		List<DNASequence> Mixed=conn.LoadMixedFile();
//		StringBuilder builder = new StringBuilder();
//		for(DNASequence seq : Mixed) {		    
//		builder.append(seq.getSequenceAsString());
//    	}
		
//		String MixedSeq=builder.toString();
//		System.out.println("Mixed Length: "+MixedSeq.length());
//		DNASequence Seq1=conn.LoadFastaFile(47118301);
//		
//		DNASequence Seq2=conn.LoadFastaFile(51847843);
//		DNASequence DNA=conn.LoadFastaFile(568815597);

				
//		List<DNASequence> Mixed=conn.LoadMixedFile();
//		StringBuilder builder = new StringBuilder();
//		for(DNASequence seq : Mixed) {		    
//			builder.append(seq.getSequenceAsString());
//    	}
//		String MixedSeq=builder.toString();
//		double[] w=stat.getNucleotideDistribution(MixedSeq);
//		double[] tempfactors={w[0]/0.25,w[1]/0.25,w[2]/0.25,w[3]/0.25};
//		factors=tempfactors;
//		tweights=stat.getTripletDistribution(MixedSeq);
//		List<String> Sequences=new ArrayList<String>();
//		Sequences.add(568815597); //H.S 1
//		Sequences.add(568815596);//2
//		Sequences.add(568815595);//3
//		Sequences.add(568815594);//4
//		Sequences.add(568815593);//5
//		Sequences.add(568815592);//6
//		Sequences.add(568815591);//7
//		Sequences.add(568815590);//9
//		Sequences.add(568815588);//10
//		Sequences.add(568815587);//11
//		Sequences.add(568815586);//12
//		Sequences.add(568815585);//13
//		Sequences.add(568815584);//14
//		Sequences.add(568815583);//15
//		Sequences.add(568815582);//16
//		Sequences.add(568815581);//17
//		Sequences.add(568815580);//18
//		Sequences.add(568815579);//19
//		Sequences.add(568815578);//20
//		Sequences.add(568815577);//21
//		Sequences.add(568815576);//22
//		Sequences.add(568815575);//X
//		Sequences.add(568815574);//Y
//		Sequences.add(671162317);//D. Melanogaster 3L
//		Sequences.add(671162315);//D. Melanogaster 2R
//		Sequences.add(56384585);// E.Coli
//		Sequences.add(1537050);// HIV
//		Sequences.add("NC_001806"); //Herpes
//		Sequences.add("NC_001591"); //Papillomavirus
//		Sequences.add("NC_003461"); //Parainfluenza
//		Sequences.add("NC_001959"); //Norovirus G1
//		Sequences.add("NC_002031");//Gelbfieber
//		Sequences.add("NC_012532");//Zika
//		Sequences.add("NC_001477");//Dengue
//		Sequences.add("NC_004102");//Hepatitis C
//		Sequences.add("NC_002549");//Zaire Ebola

		
//		for (String Str:Sequences){
//			DNASequence Seq1=conn.LoadFastaFile(Str);
//			SequenceStats Stat=new SequenceStats(Seq1.getSequenceAsString());
//			baseAprioriWeights=Stat.getBase_aPriori();
//			tripletAprioriWeights=Stat.getTriplet_aPriori();
//			baseTransitionWeights=Stat.getBaseTransition();
//			tripletTransitionWeights=Stat.getTripletTransition();
//			TransitionTransversionBias=2;
//			setWeightings(true, true, true, true);
//			GeneCode G=new GeneCode(Code);
//			StabilityCalculator S=new StabilityCalculator(G);
//			double MS1=S.get_BaseDeviation(1);//MS1
//			double MS2=S.get_BaseDeviation(2);//MS2
//			double MS3=S.get_BaseDeviation(3);//MS3
//			double MS0=S.getMS0(MS1, MS2, MS3);//MS0
//			double rMS=S.get_ShiftDeviation(1);//rMS
//			double lMS=S.get_ShiftDeviation(2);//lMS
//			double fMS=S.getfMS(rMS, lMS);//fMS
//			double GMS=S.getGMS(MS1, MS2, MS3, rMS, lMS);
//			FileWriter Log;
//			try {
//				Log = new FileWriter("data/Log.txt", true);
//				Log.write(Str+", "+MS1+", "+MS2+", "+MS3+", "+MS0+", "+rMS+", "+lMS+", "+fMS+", "+GMS+"\n");
//				Log.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		DNASequence Seq1=conn.LoadFastaFile(568815597);
//		SequenceStats Stat=new SequenceStats(Seq1.getSequenceAsString());
//		
//		baseAprioriWeights=Stat.getBase_aPriori();
//		tripletAprioriWeights=Stat.getTriplet_aPriori();
//		baseTransitionWeights=Stat.getBaseTransition();
//		tripletTransitionWeights=Stat.getTripletTransition();
//		TransitionTransversionBias=2;
//		CodePermutation P=new CodePermutation();
//		P.generateCodes();
		CodeFinder C=new CodeFinder();
//		setWeightings(false,true,false,true);
//		CodePermutation P=new CodePermutation();
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
		C.RunCodeFinder(20);
//		
		//CodePermutation P=new CodePermutation();
		
//		//Ohne Gewichtung
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		//Eine Aktive
//		setWeightings(true, false, false, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, true, false, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, false, true, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, false, false, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		//Zwei aktive
//		setWeightings(true, true, false, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(true, false, true, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(true, false, false, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, true, true, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, true, false, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, false, true, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		//3 Aktive
//		setWeightings(true, true, true, false);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(true, true, false, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(true, false, true, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		setWeightings(false, true, true, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
//		
//		//4 Aktive
//		setWeightings(true, true, true, true);
//		new CodeEvaluation(P.calculateValues()).countBetterCodes();
		
		

		//###################################################################
		//###################################################################
		//###################################################################
		
	/*
	 * Fahrplan:
	 * 1. Zeigen, dass a-priori Wahrscheinlichkeiten Artspezifisch und Genom-Global sind
	 * 	Vergleich Chr 1 zu Codierende Regionen auf Chr1
	 * 	
	 * 2. Berechnung Sabilität gemäß Paper (H.Sapiens)
	 *   2.1 + Mit a-priori-WS
	 *   2.2 + Mit Triplet-a-priori-WS
	 *   2.3 Mit WS bzgl Rechts-Links shift -> Macht keinen Unterschied da das Symmetrisch ist.
	 *   
	 *   
	 * 
	 *  Transition matrix Chromosom 1
	 *	--- C ------- T ------- A ------- G
	 *	C 0,2594 -- 0,2059 -- 0,1729 -- 0,2109
	 *	T 0,3422 -- 0,3279 -- 0,2553 -- 0,2417
	 *	A 0,3489 -- 0,2164 -- 0,3265 -- 0,2878
	 *	G 0,0494 -- 0,2498 -- 0,2452 -- 0,2596
	 * 
	 */

	}
	private static void setWeightings(boolean ba, boolean ta, boolean bt, boolean tt){
		baseAprioriEnabled=ba;
		tripletAprioriEnabled=ta;
		baseTransitionEnabled=bt;
		tripletTransitionEnabled=tt;
	}
	
}

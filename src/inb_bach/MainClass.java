package inb_bach;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
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
		
		
		DNASequence Seq1=conn.LoadFastaFile(568815597);
		SequenceStats Stat=new SequenceStats(Seq1.getSequenceAsString());
		baseAprioriWeights=Stat.getBase_aPriori();
		tripletAprioriWeights=Stat.getTriplet_aPriori();
		tripletTransitionWeights=Stat.getTripletTransition();
		baseTransitionWeights=Stat.getBaseTransition();
		
		CodePermutation P=new CodePermutation();
		new CodeEvaluation(P.calculateValues()).countBetterCodes();

		

		//###################################################################
		//###################################################################
		//###################################################################
		
	/*
	 * Fahrplan:
	 * 1. Zeigen, dass a-priori Wahrscheinlichkeiten Artspezifisch und Genom-Global sind
	 * 	Vergleich Chr 1 zu Codierende Regionen auf Chr1
	 * 	
	 * 2. Berechnung Sabilit�t gem�� Paper (H.Sapiens)
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
}

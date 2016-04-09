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

public class MainClass {
	static DecimalFormat df = new DecimalFormat("0.0000"); 
	static StatisticsCalculator stat=new StatisticsCalculator();
	static GenBankConnection conn=new GenBankConnection();
	public static void main (String[] args){
		//###################################################################
		//#########################   DEBUG AREA   ##########################
		//###################################################################
		
		// 55417888 = 33MB Mixed
		// 51847843 = 158 MB Mixed Chromosom 7
		// 568815597 = 250 MB Mixed Chromosom 1
		// 671162122 = 32 MB Drosophila melanogaster chromosome 2R
		//DNASequence DNA=conn.LoadFastaFile(568815597);
		//stat.Statistics(DNA.getSequenceAsString());
		//double[][] C1=stat.getMatrix(DNA.getSequenceAsString());
//		double Chr1[][]={
//			    {0.2594,0.3422,0.3489,0.0494},
//			    {0.2059,0.3279,0.2164,0.2498},
//			    {0.1729,0.2553,0.3265,0.2452},
//			    {0.2109,0.2417,0.2878,0.2596}
//			};
//		
//		List<DNASequence> Mixed=conn.LoadMixedFile();
//		StringBuilder builder = new StringBuilder();
//		for(DNASequence seq : Mixed) {		    
//		builder.append(seq.getSequenceAsString());
//    	}
//		String MixedSeq=builder.toString();
//		System.out.println("Mixed Length: "+MixedSeq.length());
		DNASequence Seq1=conn.LoadFastaFile(47118301);
		double[][] Seq1Matrix=stat.getMatrix(Seq1.getSequenceAsString());
		
		DNASequence Seq2=conn.LoadFastaFile(51847843);
		double[][] Seq2Matrix=stat.getMatrix(Seq2.getSequenceAsString());
		
		stat.MatrixDiff(Seq1Matrix, Seq2Matrix);
		
		

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
	 *   2.3 Mit WS bzgl Rechts-Links shift
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

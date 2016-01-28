package inb_bach;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
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
		
		DNASequence DNA=conn.LoadFastaFile(568815597);
		stat.Statistics(DNA.getSequenceAsString());
		stat.getMatrix(DNA.getSequenceAsString());

		
		//###################################################################
		//###################################################################
		//###################################################################

	}
}

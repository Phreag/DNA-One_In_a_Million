package inb_bach;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
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
	public static void main (String[] args){
		//DownloadFasta(51847843);
		String seq=LoadFastaFile(51847843);
		String[]LeuCodes={"CTT", "CTC", "CTA", "CTG", "TTA", "TTG"};
		
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
			System.out.println("Statistik für "+LeuCodes[i]+"("+count+")");
			System.out.println("C:"+proz[0]+"% T:"+proz[1]+"% A:"+proz[2]+"% G:"+proz[3]+"%");
			System.out.println("C:"+stat[0]+" T:"+stat[1]+" A:"+stat[2]+" G:"+stat[3]);
		}

	}
	//Lädt die FASTA file die zuvor heruntergeladen wurde.
	public static String LoadFastaFile (int GeneID){
		try{
			LinkedHashMap<String, DNASequence> a = FastaReaderHelper.readFastaDNASequence(new File("data/"+GeneID+".fasta"));
			for (  Entry<String, DNASequence> entry : a.entrySet() ) {
				System.out.println("Geladen:" + entry.getValue().getOriginalHeader() + " length = " + entry.getValue().getSequenceAsString().length() );
				return entry.getValue().getSequenceAsString();
			}
		} catch (Exception e) {
			System.out.println("FEHLER: FASTA-File konnte nicht geladen werden.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null;
		
	}
	
	//Benötigt GI aus Genbank. Sequenz kann dann selbstständig geladen werden.
	public static void DownloadFasta(int GeneID){
		URL u;
		try {
			u = new URL("http://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&sendto=on&log$=seqview&db=nuccore&dopt=fasta&sort=&val="+GeneID+"&from=begin&to=end&maxplex=1");
			File f=new File("data/"+GeneID+".fasta");
			System.out.println("Downloading...");
			FileUtils.copyURLToFile(u, f);
			System.out.println("Download abgeschlossen!");
		} catch (Exception e) {
			System.out.println("FEHLER: Download konnte nicht durchgeführt werden!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	

}

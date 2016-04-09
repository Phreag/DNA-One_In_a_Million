package inb_bach;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

public class GenBankConnection {
	public List<DNASequence> LoadMixedFile(){
		File f=new File("data/Mixed.fasta");
		System.out.println("Loading file Mixed.fasta to memory ("+(f.length()/1024)+" KB)...");
		try{
			LinkedHashMap<String, DNASequence> a = FastaReaderHelper.readFastaDNASequence(new File("data/Mixed.fasta"));
			List<DNASequence> Result=new ArrayList<DNASequence>();
			for (  Entry<String, DNASequence> entry : a.entrySet() ) {
				System.out.println("Loaded: " + entry.getValue().getOriginalHeader() + " length = " + entry.getValue().getSequenceAsString().length() + " nucleotides" );
				Result.add (entry.getValue());
			}
			if (Result.size()==0)return null;
			return Result;
		} catch (Exception e) {
			System.out.println("ERROR: FASTA-File could not be loaded.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	//Loads a FASTA file from /data
	//If the File doesnt exist it donwloads the sequence from the GenBank.
		public DNASequence LoadFastaFile (int GeneID){
			File f=new File("data/"+GeneID+".fasta");
			if (!f.exists()){
				System.out.println("Sequence not found. Downloading from GenBank...");
				if(!DownloadFasta(GeneID)){return null;}
				System.out.println("Repairing File and removing disabled characters...");
				try {
					int headerend=0;
					String File=FileUtils.readFileToString(f);
					for (int i=0;i<File.length();i++){
						if (File.charAt(i)=='\n'||File.charAt(i)=='\r'){
							headerend=i;
							break;
						}
					}
					System.out.println("Headerend = "+headerend);
					String Header=File.substring(0, headerend);
					String Seq= File.substring(headerend);
				
					System.out.println("replace M by A... (C also possible)");
					Seq=Seq.replaceAll("M", "A");
					System.out.println("replace R by G... (A also possible)");
					Seq=Seq.replaceAll("R","G");
					System.out.println("Writing changes to file...");
					File=Header+Seq;
					f.delete();
					f.createNewFile();
					PrintWriter out = new PrintWriter("data/"+GeneID+".fasta");
					out.println(File);
					out.close();
					
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			
			System.out.println("Loading file "+GeneID+".fasta to memory ("+(f.length()/1024)+" KB)...");
			try{
				LinkedHashMap<String, DNASequence> a = FastaReaderHelper.readFastaDNASequence(new File("data/"+GeneID+".fasta"));
				for (  Entry<String, DNASequence> entry : a.entrySet() ) {
					System.out.println("Loaded: " + entry.getValue().getOriginalHeader() + " length = " + entry.getValue().getSequenceAsString().length() + " nucleotides" );
					return entry.getValue();
				}
			} catch (Exception e) {
				System.out.println("ERROR: FASTA-File could not be loaded.");
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
			return null;
		}
		//Benötigt GI aus Genbank. Sequenz kann dann selbstständig geladen werden.
		private boolean DownloadFasta(int GeneID){
			URL u;
			try {
				u = new URL("http://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&sendto=on&log$=seqview&db=nuccore&dopt=fasta&sort=&val="+GeneID+"&from=begin&to=end&maxplex=1");
				File f=new File("data/"+GeneID+".fasta");
				FileUtils.copyURLToFile(u, f);
				System.out.println("Download finished.");
			} catch (Exception e) {
				System.out.println("ERROR: Could not complete download!");
				System.out.println(e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
	}

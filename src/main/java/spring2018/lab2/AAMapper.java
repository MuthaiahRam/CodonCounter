package spring2018.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;

@SuppressWarnings("deprecation")
public class AAMapper extends Mapper<LongWritable, Text, Text, Text> {

	Map<String, String> codon2aaMap = new HashMap<String, String>();
	public final static Text outputFrameOne = new Text("f1");
	public final static Text outputFrameTwo = new Text("s1");
	public final static Text outputFrameThree = new Text("t1");

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		try {
			Path[] codon2aaPath = DistributedCache.getLocalCacheFiles(context.getConfiguration());
			if (codon2aaPath != null && codon2aaPath.length > 0) {
				codon2aaMap = readFile(codon2aaPath);
			}
		} catch (IOException ex) {
			System.err.println("Exception in mapper setup: " + ex.getMessage());
			System.exit(1);
		}
	}

	protected HashMap<String, String> readFile(Path[] codonFilePath) {
		HashMap<String, String> codonMap = new HashMap<String, String>();
		BufferedReader cacheReader = null;
		String line = null;
		String[] lineArray = null;
		try {
			cacheReader = new BufferedReader(new FileReader(codonFilePath[0].toString()));
			while ((line = cacheReader.readLine()) != null) {
				// Isoleucine I ATT, ATC, ATA
				lineArray = line.split("\\t");
				String aminoAcid = lineArray[0];
				String[] sequencesArray = lineArray[2].split(",");
				for (String sequence : sequencesArray) {
					codonMap.put(sequence.trim(), aminoAcid.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return codonMap;
	}

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		System.out.println("Inside mapper");

		// TODO declare String variable for Text value
		String chromosomeInput = value.toString();
		

		// TODO: declare and initialize variables for tokens of each of 3
		// reading frames

		// TODO: read, process, and write reading frame 1
		// TCA GCC TTT TCT TTG ACC TCT TCT TTC TGT TCA TGT GTA TTT GCT GTC TCT
		// TAG CCC AGA
		// does TCA exist in codon2aaMap?
		// if so, write (key, value) pair to context
		if (!chromosomeInput.startsWith(">")) {
			String codonValueFrameOne = null;
			for (int i = 0; i <=chromosomeInput.length() - 3; i = i + 3) {
				codonValueFrameOne = check(chromosomeInput.substring(i, i + 3));
				if (codonValueFrameOne == null) {
					continue;
				} else {
					
					context.write(new Text(codonValueFrameOne.trim()), outputFrameOne);
				}
			}

			// TODO: read, process, and write reading frame 2
			StringBuffer bufferFrameTwo = new StringBuffer(chromosomeInput).deleteCharAt(0);
			String frame2 = bufferFrameTwo.toString();

			// T CAG CCT TTT CTT TGA CCT CTT CTT TCT GTT CAT GTG TAT TTG CTG TCT
			// CTT
			// AGC CCA GA
			// does CAG exist in codon2aaMap?
			// if so, write (key, value) pair to context
			String codonValueFrameTwo = null;
			for (int i = 0; i <=frame2.length() - 3; i = i + 3) {
				codonValueFrameTwo = check(frame2.substring(i, i + 3));
				if (codonValueFrameTwo == null) {
					continue;
				} else {
					context.write(new Text(codonValueFrameTwo.trim()), outputFrameTwo);
				}
			}
			// TODO: read, process, and write reading frame 3
			String bufferFrameThree = chromosomeInput.substring(2, chromosomeInput.length());
			String frame3 = bufferFrameThree.toString();

			// TC AGC CTT TTC TTT GAC CTC TTC TTT CTG TTC ATG TGT ATT TGC TGT
			// CTC
			// TTA GCC CAG A
			// does AGC exist in codon2aaMap?
			// if so, write (key, value) pair to context
			String codonValueFrameThree = null;
			for (int i = 0; i <=frame3.length() - 3; i = i + 3) {
				codonValueFrameThree = check(frame3.substring(i, i + 3));
				if (codonValueFrameThree == null) {
					continue;
				} else {
					context.write(new Text(codonValueFrameThree.trim()), outputFrameThree);
				}
			}
		}
	}

	public String check(String codon) {

		return codon2aaMap.get(codon);

	}
}
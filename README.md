# CodonCounter
Implement a codon counter to count frequency of amino acids using Hadoop Map Reduce Environment
Amino acids are the building blocks of proteins, and nucleotides are the building blocks of amino acids.  There are 20 "standard" amino acids, and each amino is encoded by exactly 3 nucleotides (called a "codon").  There are 4 different nucleotides (A, C, T, and G) in DNA, and so there are 4^3 possible codons.  However, not all of them are used to encode the 20 "standard" amino acids (see https://en.wikipedia.org/wiki/DNA_codon_table for a list of the 20 amino acids and their corresponding codons).  There are also codons called "start" and "stop" that are used to bound a gene sequence.

Given the sequence  AGGTGACACCGCAAGCCTTATATTAGC, there are 3 possible reading frames:
AGG TGA CAC CGC AAG CCT TAT ATT AGC
A GGT GAC ACC GCA AGC CTT ATA TTA GC
AG GTG ACA CCG CAA GCC TTA TAT TAG C

A MapReduce program (including a driver, mapper, and reducer) that counts the frequency of each amino acid (and start and stop codons) in a given FASTA file foreach of the 3 reading frames is developed.

Expected output

Alanine			3381454	3215080	3211077
Arginine		3928264	3730207	3733975
Asparagine		2936220	2784133	2790388
Aspartic acid		1758262	1670500	1668606
Cysteine		2647046	2514091	2515827
Glutamic acid		2854530	2715951	2716251
Glutamine		3046292	2895104	2894122
Glycine			4166742	3954067	3954684
Histidine		2544916	2414470	2414611
Isoleucine		4338850	4120383	4119275
Leucine			8447464	8033631	8034291
Lysine			4364002	4149451	4146707
Methionine		1368056	1303051	1302118
Phenylalanine		4376879 4157966	4158808
Proline			4153642	3948281	3949687
Serine			6964239	6610067	6612036
Stop codons		4008143	3806777	3809253
Threonine		3868541	3674922	3668828
Tryptophan		1456170	1383705	1382291
Tyrosine		2337305	2221116	2223533

Usage:

yarn jar <something.jar> <driver class> test.fa </tmp/out-$USER> <codon file name>

Should place the fasta file in the HDFS
Deploy the jar in the HDFS and fire the command above.

All the dependencies are included in POM file of this Maven Project


package spring2018.lab2;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import java.io.IOException;

public class AAReducer extends Reducer<Text, Text, Text, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		// TODO: initialize integer sums for each reading frame
		int sumFrame1 = 0, sumFrame2 = 0, sumFrame3 = 0;
		String tab = "";

		// TODO: loop through Iterable values and increment sums for each
		// reading frame
		for (Text value : values) {
			String mapString = value.toString();
			if (mapString.startsWith("f"))
				sumFrame1 += Integer.valueOf(mapString.substring(1, mapString.length()));
			if (mapString.startsWith("s"))
				sumFrame2 += Integer.valueOf(mapString.substring(1, mapString.length()));
			if (mapString.startsWith("t"))
				sumFrame3 += Integer.valueOf(mapString.substring(1, mapString.length()));
		}
		// TODO: write the (key, value) pair to the context
		// TODO: consider how to use tabs to format output correctly
		if (key.getLength() <= 10)
			tab = "\t";

		context.write(key, new Text(tab + String.valueOf(sumFrame1).trim() + "\t" + String.valueOf(sumFrame2).trim()
				+ "\t" + String.valueOf(sumFrame3).trim()));

	}
}
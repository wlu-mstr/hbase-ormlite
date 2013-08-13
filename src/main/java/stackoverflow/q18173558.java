package stackoverflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.bloom.*;

public class q18173558 {
	public static CountingBloomFilter CBF = new CountingBloomFilter(10, 5, 0);

	public static void countingFilter() throws IOException {

		ArrayList<byte[]> CBF_Keys = new ArrayList<byte[]>();
		CBF_Keys.add(Bytes.toBytes("abc"));
		CBF_Keys.add(Bytes.toBytes("abc2"));
		CBF_Keys.add(Bytes.toBytes("abc3"));
		CBF_Keys.add(Bytes.toBytes("abc4"));
		CBF_Keys.add(Bytes.toBytes("abc5"));
		CBF_Keys.add(Bytes.toBytes("abc6"));
		CBF_Keys.add(Bytes.toBytes("abc7"));
		CBF_Keys.add(Bytes.toBytes("abc8"));
		CBF_Keys.add(Bytes.toBytes("abc9"));
		CBF_Keys.add(Bytes.toBytes("abc10"));
		CBF_Keys.add(Bytes.toBytes("abc11"));
		CBF_Keys.add(Bytes.toBytes("abc12"));
		CBF_Keys.add(Bytes.toBytes("abc13"));
		CBF_Keys.add(Bytes.toBytes("abc14"));
		CBF_Keys.add(Bytes.toBytes("abc15"));
		CBF_Keys.add(Bytes.toBytes("abc16"));
		CBF_Keys.add(Bytes.toBytes("abc17"));
		CBF_Keys.add(Bytes.toBytes("abc18"));
		CBF_Keys.add(Bytes.toBytes("abc21"));
		CBF_Keys.add(Bytes.toBytes("abc22"));
		CBF_Keys.add(Bytes.toBytes("abc23"));
		CBF_Keys.add(Bytes.toBytes("abc24"));
		CBF_Keys.add(Bytes.toBytes("abc31"));
		CBF_Keys.add(Bytes.toBytes("abc32"));
		CBF_Keys.add(Bytes.toBytes("abc33"));

		Iterator<byte[]> iter = CBF_Keys.iterator();
		while (iter.hasNext()) {
			byte[] temp = iter.next();

			Key hadoop_key = new Key(temp, 2.0);

			CBF.add(hadoop_key);
		}

	}

	public static void main(String args[]) throws IOException {
		countingFilter();
	}
}
package com.abel.demo.hbase.mr;

/**
 * Created by abel.chan on 17/6/17.
 */

import com.abel.demo.hbase.entity.ROW;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.io.Serializable;

/**
 * Reducer
 */
public class ScanReducer extends Reducer<Text, Text, Text, Text> implements Serializable {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, final Context context) throws IOException, InterruptedException {
        long size = 0;
        for (Text p : values) {
            size++;
            context.write(key, p);
            context.getCounter(ROW.WRITE).increment(1);
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
    }
}

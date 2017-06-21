package com.abel.demo.hbase.mr;

/**
 * Created by abel.chan on 17/6/17.
 */

import com.abel.demo.hbase.entity.ROW;
import com.abel.demo.hbase.util.Resut2MapUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Mapper
 */
public class ScanMapper extends TableMapper<Text, Text> implements Serializable{
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        Map<String, String> familyMap = Resut2MapUtil.getFamilyMap(value, "r".getBytes());
        context.write(new Text(value.getRow()), new Text(familyMap.toString()));
        context.getCounter(ROW.WRITE).increment(1);
    }
}

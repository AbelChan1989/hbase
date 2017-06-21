package com.abel.demo.hbase.runner;

import com.abel.demo.hbase.conf.HbaseConfiguration;
import com.abel.demo.hbase.mr.ScanMapper;
import com.abel.demo.hbase.mr.ScanReducer;
import com.abel.demo.hbase.util.HbaseUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.datastory.banyan.spark.ScanFlushESMR
 *
 * @author lhfcws
 * @since 16/12/6
 */

public class ScanMrYarnRunner implements Serializable {

    public int getReducerNum() {
        return 1;
    }

    public Job buildJob(String table, Scan scan, Class<?> jarClass, Class<? extends TableMapper> mapperClass, Class<? extends Reducer> reducerClass
            , String outputPath) throws IOException {
        System.out.println("[SCAN] " + table + ";  " + scan);
        Job job = Job.getInstance(HbaseConfiguration.getInstance());
        Configuration conf = job.getConfiguration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        conf.set("hbase.client.keyvalue.maxsize", "" + (50 * 1024 * 1024));
        conf.set("mapreduce.job.user.classpath.first", "true");
        conf.set("mapred.reduce.slowstart.completed.maps", "1.0");  // map跑完100% 才跑reducer
        conf.set("mapreduce.job.running.map.limit", "2");
        conf.set("mapreduce.reduce.memory.mb", "512");
        conf.set("mapreduce.map.memory.mb", "512");
//        job.setJarByClass(jarClass);
        job.setJar("/Users/abel.chan/ide/idea/github/abel-test/hbase/target/hbase-1.0.0-SNAPSHOT-jar-with-dependencies.jar");
        job.setInputFormatClass(TableInputFormat.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        job.setJobName(this.getClass().getSimpleName() + "-" + table + "-" + sdf.format(new Date()));

        Class<?> mapOutputKeyClass = Text.class;
        Class<?> mapOutputValueClass = Text.class;

        if (reducerClass == null) {
            mapOutputKeyClass = NullWritable.class;
            mapOutputValueClass = NullWritable.class;
            job.setNumReduceTasks(0);
        } else {
            job.setOutputFormatClass(TextOutputFormat.class);
            job.setReducerClass(reducerClass);
            job.setMapOutputKeyClass(mapOutputKeyClass);
            job.setMapOutputValueClass(mapOutputValueClass);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setNumReduceTasks(getReducerNum());
        }
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.setJarByClass(this.getClass());

        TableMapReduceUtil.initTableMapperJob(table, scan, mapperClass, mapOutputKeyClass, mapOutputValueClass, job);
//        conf.set("mapred.job.reuse.jvm.num.tasks", "1");
        return job;
    }

    public void run(String table, String outputPath, Class<?> jarClass) throws Exception {
        Job job = buildJob(table, HbaseUtil.buildScan(), jarClass, ScanMapper.class, ScanReducer.class, outputPath);
//        job.setJobName(table + ": " + startPostDate + "-" + endPostDate);
//        job.setJobName(table);
        job.waitForCompletion(true);
    }


}

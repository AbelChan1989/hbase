package com.abel.demo.hbase.cli;

import com.abel.demo.hbase.client.HbaseDataClient;
import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.client.Get;

import java.util.List;
import java.util.Map;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseMutilVersionCli {
    public static void main(String[] args) {
        try {
            Get get1 = new Get("123".getBytes());
            HbaseDataClient client = new HbaseDataClient();
            Map<String, Object> mutilVersions = client.getMutilVersion(get1, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.abel.demo.hbase.cli;

import com.abel.demo.hbase.client.HbaseDataClient;
import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.client.Get;

import java.util.List;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseGetPkCli {
    public static void main(String[] args) {
        try {
            List<Get> list = Lists.newArrayList();
            Get get1 = new Get("123".getBytes());
            Get get2 = new Get("124".getBytes());
            list.add(get1);
            list.add(get2);
            HbaseDataClient client = new HbaseDataClient();
            System.out.println(client.get(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

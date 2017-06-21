package com.abel.demo.hbase.cli;

import com.abel.demo.hbase.client.HbaseDataClient;
import com.abel.demo.hbase.util.HbaseUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseScanCli {
    public static void main(String[] args) {
        try {
            HbaseDataClient client = new HbaseDataClient();
            List<Map<String, String>> scan = client.scan(HbaseUtil.buildScan());
            System.out.println(scan);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.abel.demo.hbase.util;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseUtil {
    public static Scan buildScan() {
        Scan scan = new Scan();
        scan.setCaching(1000);
        scan.setCacheBlocks(false);
        return scan;
    }

    public static Scan buildScan(Filter filter) {
        Scan scan = buildScan();
        if (filter != null)
            scan.setFilter(filter);
        return scan;
    }
}

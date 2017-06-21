package com.abel.demo.hbase.cli;

import com.abel.demo.hbase.runner.ScanMrYarnRunner;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseYarnScanCli {
    public static void main(String[] args) {
        try {

            ScanMrYarnRunner runner = new ScanMrYarnRunner();
            runner.run("member2", "/tmp/hbase/test", HbaseYarnScanCli.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.abel.demo.hbase.conf;

import org.apache.hadoop.conf.Configuration;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseConfiguration extends Configuration {

    private static HbaseConfiguration config;

    private HbaseConfiguration() {
        this.addResource("core-site.xml");
        this.addResource("mapred-site.xml");
        this.addResource("hbase-site.xml");
        this.addResource("hdfs-site.xml");
        this.addResource("yarn-site.xml");
    }

    public static HbaseConfiguration getInstance() {
        if (null == config) {
            synchronized (HbaseConfiguration.class) {
                if (null == config) {
                    config = new HbaseConfiguration();
                }
            }
        }
        return config;
    }

}

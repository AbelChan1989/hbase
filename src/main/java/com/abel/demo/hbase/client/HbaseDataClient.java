package com.abel.demo.hbase.client;

import com.abel.demo.hbase.conf.HbaseConfiguration;
import com.abel.demo.hbase.util.Resut2MapUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abel.chan on 17/6/16.
 */
public class HbaseDataClient {

    private HConnection hconn = null;

    public HTableInterface getMemberTable() throws Exception {
        if (hconn != null) {
            return hconn.getTable("member");
        }
        throw new NullPointerException("hConnetion is null!");
    }

    public void closeHtable(HTableInterface hTableInterface) {
        try {
            if (hTableInterface != null) {
                hTableInterface.close();
            }
        } catch (IOException e) {
        }
    }

    public HbaseDataClient() {
        try {
            hconn = HConnectionManager.createConnection(HbaseConfiguration.getInstance());
        } catch (IOException e) {
        }
    }

    public List<Map<String, String>> get(List<Get> gets) throws Exception {
        HTableInterface memberTable = getMemberTable();
        try {
            Result[] results = memberTable.get(gets);
            return Resut2MapUtil.getFamilyMap(results, "r".getBytes());
        } finally {
            closeHtable(memberTable);
        }
    }

    public Map<String, Object> getMutilVersion(Get get, int latestCnt) throws Exception {
        if (latestCnt <= 0)
            get = get.setMaxVersions();
        else
            get = get.setMaxVersions(latestCnt);

        HTableInterface hti = getMemberTable();
        try {
            Result result = hti.get(get);
            if (result == null || result.isEmpty())
                return null;

            Map<String, Object> out = new HashMap<String, Object>();
            out.put("id", new String(result.getValue("r".getBytes(), "id".getBytes())));
            out.put("sex", new String(result.getValue("r".getBytes(), "sex".getBytes())));
            out.put("name", new String(result.getValue("r".getBytes(), "name".getBytes())));

            Map<Long, byte[]> datas = result.getMap().get("r".getBytes()).get("data".getBytes());

            for (Map.Entry<Long, byte[]> entry : datas.entrySet()) {
                System.out.println(entry.getKey() + ":::" + new String(entry.getValue()));
            }
            if (datas != null && !datas.isEmpty())
                out.put("data", datas);
            return out;
        } finally {
            closeHtable(hti);
        }
    }

    public List<Map<String, String>> scan(Scan scan) throws Exception {
        ResultScanner rs = null;
        List<Map<String, String>> res = new ArrayList<Map<String, String>>();
        HTableInterface hti = getMemberTable();
        try {
            rs = hti.getScanner(scan);
            for (Result r : rs) {
                res.add(Resut2MapUtil.getFamilyMap(r, "r".getBytes()));
            }
            return res;
        } finally {
            closeHtable(hti);
            rs.close();
        }
    }

}

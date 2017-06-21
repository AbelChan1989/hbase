package com.abel.demo.hbase.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abel.chan on 17/6/16.
 */
public class Resut2MapUtil {

    public static List<Map<String, String>> getFamilyMap(Result[] result, byte[] family) {
        List<Map<String, String>> res = Lists.newArrayList();
        for (Result val : result) {
            Map<String, String> familyMap = getFamilyMap(val, family);
            if (familyMap != null) {
                res.add(familyMap);
            } else {
                res.add(new HashedMap());
            }
        }
        return res;
    }

    public static Map<String, String> getFamilyMap(Result result, byte[] family) {
        Map<byte[], byte[]> bm = result.getFamilyMap(family);
        if (bm != null) {
            return byte2strMap(bm);
        } else
            return null;
    }

    private static Map<String, String> byte2strMap(Map<byte[], byte[]> mp) {
        Map<String, String> strParams = new HashMap<String, String>();
        for (Map.Entry<byte[], byte[]> e : mp.entrySet()) {
            strParams.put(Bytes.toString(e.getKey()), Bytes.toString(e.getValue()));
        }
        return strParams;
    }
}

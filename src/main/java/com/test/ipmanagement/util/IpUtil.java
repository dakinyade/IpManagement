package com.test.ipmanagement.util;

import com.test.ipmanagement.model.entities.IpPool;

public class IpUtil {

    public static int splitIpAndGetPosition(String input, int position) {
        String[] splitedIp = input.split("\\.");
        String lastIpRangeNo = splitedIp[position];
        return Integer.valueOf(lastIpRangeNo);
    }


    public static boolean isIpWithinPoolRange(IpPool ipPool, String ip) {

        if ((splitIpAndGetPosition(ip, 3) >= ipPool.getLowerBoundIntValue())
                &&
                (splitIpAndGetPosition(ip, 3) <= ipPool.getUppperBoundIntValue())) {
            return true;
        }
        return false;
    }

}

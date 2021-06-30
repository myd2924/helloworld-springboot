package com.myd.helloworld.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 10:53
 * @Description: 服务器原信息
 */
@Slf4j
public final class ServerMetaUtil {

    private static final String SERVER_IP;
    private static final int PID;

    public static String getLocalIP (){
        return SERVER_IP;
    }

    public static int getPid(){
        return PID;
    }

    private ServerMetaUtil(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static{
        String ip = "127.0.0.1";
        int pid = -1;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            if (null != jvmName) {
                pid = Integer.parseInt(jvmName.substring(0, jvmName.indexOf(64)));
            }
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        SERVER_IP = ip;
        PID = pid;
    }
}

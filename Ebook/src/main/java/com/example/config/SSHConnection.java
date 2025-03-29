package com.example.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Properties;

public class SSHConnection {
    private static Session session;

    public static void connect(String sshHost, int sshPort, String sshUser, String sshPassword,
                               String remoteHost, int remotePort, int localPort) throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPassword);

        // 跳过 SSH 主机密钥检查
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect(); // 连接 SSH

        // 建立端口转发（本地 -> 远程数据库）
        session.setPortForwardingL(localPort, remoteHost, remotePort);

        System.out.println("✅ SSH 连接成功，已转发端口 " + localPort + " 到 " + remoteHost + ":" + remotePort);
    }

    public static void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("❌ SSH 连接已断开");
        }
    }
}

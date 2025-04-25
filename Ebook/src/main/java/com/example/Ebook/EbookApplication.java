package com.example.Ebook;

import com.example.config.SSHConnection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbookApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(EbookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// SSH 服务器信息
		String sshHost = "lab-8da5ac15-31bf-4a08-9d6e-2d54414a9c8e.uksouth.cloudapp.azure.com";  // SSH 服务器地址
		int sshPort = 5091;                        // SSH 端口（默认 22）
		String sshUser = "student";        // SSH 用户名
		String sshPassword = "WieeinstN8";// SSH 密码, jyy个人

		// MySQL 服务器信息
		String remoteHost = "lab000001";  // 远程 MySQL 地址
		int remotePort = 3306;            // 远程 MySQL 端口
		int localPort = 3306;             // 本地端口（映射远程 MySQL）

		// 连接 SSH
		SSHConnection.connect(sshHost, sshPort, sshUser, sshPassword, remoteHost, remotePort, localPort);
	}
}

package com.lazywg.assembly.sql.config;

import com.lazywg.assembly.sql.enums.SQLType;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:10:31
 *
 */
public class SQLConfig {

	private String dbIp;

	private int dbPort;

	private String dbUser;

	private String dbPwd;
	
	private String dbName;

	private SQLType sqlType;

	private String jdbcDriver;

	private String connectUri;

	private int batchSize;

	private int poolSize;
	
	private boolean proxy;

	public SQLConfig(String user, String pwd, SQLType sqlType, int port) {
		this("localhost", port, user, pwd, "", sqlType);
	}

	public SQLConfig(String ip, int port, String user, String pwd, String dbName, SQLType sqlType) {
		this(ip, port, user, pwd, dbName, sqlType, 20, 1000,false);
	}

	public SQLConfig(String ip, int port, String user, String pwd, String dbName, SQLType sqlType, int poolSize, int batchSize, boolean proxy) {
		this.dbIp = ip;
		this.dbPort = port;
		this.dbUser = user;
		this.dbPwd = pwd;
		this.dbName = dbName;
		this.sqlType = sqlType;
		this.batchSize = batchSize;
		this.poolSize = poolSize;
		this.proxy = proxy;
	}

	public SQLConfig(String jdbcDriver, String connectUri, SQLType sqlType) {
		this(jdbcDriver, connectUri, sqlType, 20, 1000,false);
	}

	public SQLConfig(String jdbcDriver, String connectUri, SQLType sqlType, int poolSize, int batchSize, boolean proxy) {
		this.jdbcDriver = jdbcDriver;
		this.sqlType = sqlType;
		this.connectUri = connectUri;
		this.poolSize = poolSize;
		this.batchSize = batchSize;
		this.proxy = proxy;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public SQLType getSqlType() {
		return sqlType;
	}

	public void setSqlType(SQLType sqlType) {
		this.sqlType = sqlType;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getConnectUri() {
		return connectUri;
	}

	public void setConnectUri(String connectUri) {
		this.connectUri = connectUri;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public boolean isProxy() {
		return proxy;
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}
}

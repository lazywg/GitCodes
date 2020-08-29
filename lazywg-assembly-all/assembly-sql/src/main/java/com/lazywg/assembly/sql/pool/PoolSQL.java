package com.lazywg.assembly.sql.pool;

import java.util.UUID;

/**
 * author: gaowang
 *
 * createTime:2018年3月21日 下午4:33:51
 *
 */
public class PoolSQL<T extends AutoCloseable> implements AutoCloseable {

	private T t = null;
	
	private boolean free = true;
	
	private UUID uuid = null;
	
	public PoolSQL(T t){
		this.t = t;
		this.uuid = UUID.randomUUID();
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public UUID getUUID() {
		return uuid;
	}
	

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public void close() throws Exception {
		if (t!=null) {
			t.close();
		}
		t = null;
	}
}

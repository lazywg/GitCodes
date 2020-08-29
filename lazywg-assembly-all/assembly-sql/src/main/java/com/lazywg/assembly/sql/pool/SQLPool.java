package com.lazywg.assembly.sql.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author: gaowang
 *
 * createTime:2018年3月21日 下午4:33:32
 *
 */
public class SQLPool<T extends AutoCloseable> implements AutoCloseable {

	private Map<String, PoolSQL<T>> objects = null;

	private int maxNum = 10;

	private static Object _locker = new Object();
	
	private Timer timer = null;
	
	public SQLPool(int maxNum) {
		this.maxNum = maxNum;
		this.objects = new HashMap<String, PoolSQL<T>>();
		timer = new Timer();
		timer.schedule(new CloseTask(), 1000, 10000);
	}

	/**
	 * 
	 * @param obj
	 * @throws IndexOutOfBoundsException
	 *             添加对象数量超出最大限制
	 */
	public void addObject(PoolSQL<T> obj) throws IndexOutOfBoundsException {
		if (objects.size() < maxNum) {
			if (!objects.containsKey(obj.getUUID().toString())) {
				objects.put(obj.getUUID().toString(), obj);
			}
		} else {
			throw new IndexOutOfBoundsException("添加对象数量超出最大限制！");
		}
	}

	/**
	 * 借
	 * 
	 * @return
	 */
	public T borrowObject() {
		synchronized (_locker) {
			for (Entry<String, PoolSQL<T>> entry : objects.entrySet()) {
				if (entry.getValue().isFree()) {
					entry.getValue().setFree(false);
					return entry.getValue().getT();
				}
			}
		}
		return null;
	}

	/**
	 * 借
	 * 
	 * @param key
	 * @return
	 */
	public T borrowObject(String key) {
		synchronized (_locker) {
			PoolSQL<T> obj = objects.get(key);
			if (obj != null && obj.isFree()) {
				obj.setFree(false);
				return obj.getT();
			}
		}
		return null;
	}

	/**
	 * 还
	 * 
	 * @param t
	 */
	public void returnObject(T t) {
		synchronized (_locker) {
			for (Entry<String, PoolSQL<T>> entry : objects.entrySet()) {
				if (t == entry.getValue().getT()) {
					entry.getValue().setFree(true);
				}
			}
		}
	}

	/**
	 * 还
	 * 
	 * @param key
	 */
	public void returnObject(String key) {
		synchronized (_locker) {
			PoolSQL<T> obj = objects.get(key);
			if (obj != null && !obj.isFree()) {
				obj.setFree(true);
			}
		}
	}

	public void clear() {
		if (objects != null) {
			synchronized (_locker) {
				objects.clear();
			}
		}
	}

	/**
	 * 池对象数量
	 * 
	 * @return
	 */
	public int getSize() {
		return objects == null ? 0 : objects.size();
	}

	/**
	 * 自由对象数量
	 * 
	 * @return
	 */
	public int getFreeSize() {
		synchronized (_locker) {
			if (objects == null) {
				return 0;
			}
			int count = 0;
			for (Entry<String, PoolSQL<T>> entry : objects.entrySet()) {
				if (entry.getValue().isFree()) {
					count++;
				}
			}
			return count;
		}
	}

	@Override
	public void close() throws Exception {
		for (Entry<String, PoolSQL<T>> entry : objects.entrySet()) {
			entry.getValue().close();
		}
		objects.clear();
		if (timer!=null) {
			timer.cancel();
		}
	}
	
	/**
	 * 定时数据库连接关闭
	 * 
	 * @author Administrator
	 *
	 */
	class CloseTask extends TimerTask{

		@Override
		public void run() {
			synchronized (_locker) {
				for (Entry<String, PoolSQL<T>> entry : objects.entrySet()) {
					if (entry.getValue().isFree()) {
						try {
							entry.getValue().getT().close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}

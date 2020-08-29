package com.lazywg.assembly.sql.service;



import com.lazywg.assembly.sql.ISQL;
import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.SQLPager;
import com.lazywg.assembly.sql.entity.SQLParam;
import com.lazywg.assembly.sql.entity.Table;
import com.lazywg.assembly.sql.text.ISQLUtil;

import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 下午1:25:19
 *
 */
public interface IBaseSQL {

	/**
	 * 单个实体入库
	 * 
	 * @param t
	 * @return
	 */
	<T> boolean insert(T t);
	
	/**
	 * 实体批量入库
	 * 
	 * @param list
	 * @return
	 */
	<T> boolean batchInsert(List<T> list);

	/**
	 * 单个实体更新
	 * 
	 * @param t
	 * @return
	 */
	<T> boolean update(T t);
	
	/**
	 * 实体批量更新
	 * @param list
	 * @return
	 */
	<T> boolean batchUpdate(List<T> list);

	/**
	 * 条件更新
	 * 
	 * @param setParams  set 字段
	 * @param sqlWhere where 条件
	 * @param cls 实体类类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> boolean updateByWhere(List<SQLParam> setParams,String sqlWhere,Class<T> cls);
	
	/**
	 * 条件更新
	 * 
	 * @param setParams set 字段
	 * @param whereParams where 字段
	 * @param cls 实体类类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> boolean updateByWhere(List<SQLParam> setParams,List<SQLParam> whereParams,Class<T> cls);
	
	/**
	 * 条件更新
	 * 
	 * @param t 实体提供 set 内容
	 * @param sqlWhere 更新条件
	 * @return
	 */
	<T> boolean updateByWhere(T t,String sqlWhere);
	
	/**
	 * 条件更新
	 * 
	 * @param t 实体提供 set 内容
	 * @param whereParams 更新条件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> boolean updateByWhere(T t,List<SQLParam> whereParams);
	
	/**
	 * 实体在表中数据量
	 * 
	 * @param cls 实体类型
	 * 
	 * @return
	 */
	<T> long count(Class<T> cls);

	/**
	 * 条件统计实体数量
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> long countByWhere(String sqlWhere,Class<T> cls);

	/**
	 * 条件统计实体数量
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> long countByWhere(List<SQLParam> whereParams,Class<T> cls);
	
	/**
	 * 和
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> double sumByWhere(String sqlWhere,String fieldName,Class<T> cls);

	/**
	 * 和
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> double sumByWhere(List<SQLParam> whereParams,String fieldName,Class<T> cls);
	
	/**
	 * 条件判断是否有数据存储
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> boolean existByWhere(String sqlWhere,Class<T> cls);

	/**
	 * 条件判断是否有数据存储
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> boolean existByWhere(List<SQLParam> whereParams,Class<T> cls);
	
	/**
	 * 条件删除
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> boolean deleteByWhere(String sqlWhere,Class<T> cls);
	
	/**
	 * 条件删除
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> boolean deleteByWhere(List<SQLParam> whereParams,Class<T> cls);

	/**
	 * 查询表中所有数据
	 * 
	 * @param cls
	 * @return
	 */
	<T> List<T> select(Class<T> cls);
	
	/**
	 * 条件查询
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> List<T> selectByWhere(String sqlWhere,Class<T> cls);
	/**
	 * 条件查询
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> List<T> selectByWhere(List<SQLParam> whereParams, Class<T> cls);
	
	/**
	 * 条件查询
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> T selectFirst(List<SQLParam> whereParams,Class<T> cls);
	
	/**
	 * 条件查询
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> T selectFirst(String sqlWhere,Class<T> cls);

	/**
	 * 条件查询
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> List<T> selectByWhere(String sqlWhere,String orderWhere,Class<T> cls);
	
	/**
	 * 条件查询
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> List<T> selectByWhere(List<SQLParam> whereParams,String orderWhere,Class<T> cls);
	
	/**
	 * 分页查询
	 * 
	 * @param whereParams
	 * @param pager
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> List<T> selectByPager(List<SQLParam> whereParams,SQLPager pager,Class<T> cls);
	
	/**
	 * 分页查询
	 * 
	 * @param sqlWhere
	 * @param pager
	 * @param cls
	 * @return
	 */
	<T> List<T> selectByPager(String sqlWhere, SQLPager pager, Class<T> cls);
	
	/**
	 * 执行结果返回
	 * 
	 * @param sqlStr
	 * @param cls
	 * @return
	 */
	<T> List<T> executeResultSet(String sqlStr,Class<T> cls);
	
	/**
	 * 获取具体数据库代理操作类
	 * 
	 * @return
	 */
	ISQL getProxySQL();
	
	/**
	 * 获取具体数据库脚本帮助类
	 * 
	 * @return
	 */
	ISQLUtil getSQLUtil();

	/**
	 * 获取数据库配置
	 * 
	 * @return
	 */
	SQLConfig getSQLConfig();

	int executeNonQuery(String sql);
	
	/**
	 * 获取具体数据库操作类
	 * 
	 * 需要手动关闭
	 * 
	 * @return
	 */
	ISQL getSQL();
	
	/**
	 * 获取所有表
	 * 
	 * @return
	 */
	List<Table> getTables();
	
	/**
	 * 获取表的所有列
	 * 
	 * @param tableName
	 * @return
	 */
	List<Column> getColumns(String tableName);
}

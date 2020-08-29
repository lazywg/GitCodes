package com.lazywg.assembly.sql.text;



import com.lazywg.assembly.sql.entity.SQLPager;
import com.lazywg.assembly.sql.entity.SQLParam;

import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 下午8:26:54
 *
 */
public interface ISQLUtil {

	/**
	 * 获取实体数据入库sql脚本
	 * 
	 * @param t
	 * @return
	 */
	<T> String getInsertSqlText(T t);

	/**
	 * 获取实体批量入库 sql脚本
	 * 
	 * @param list
	 * @return
	 */
	<T> String getBatchInsertSqlText(List<T> list);

	/**
	 * 获取实体批量入库 sql脚本
	 * 
	 * @param list
	 * @return
	 */
	<T> List<String> getListInsertSqlText(List<T> list);

	/**
	 * 获取实体更新脚本
	 * 
	 * @param t
	 * @return
	 */
	<T> String getUpdateSqlText(T t);

	/**
	 * 获取实体批量更新脚本
	 * 
	 * @param list
	 * @return
	 */
	<T> String getBatchUpdateSqlText(List<T> list);

	/**
	 * 获取实体批量更新脚本
	 * 
	 * @param list
	 * @return
	 */
	<T> List<String> getListUpdateSqlText(List<T> subList);

	/**
	 * 获取条件更新脚本
	 * 
	 * @param t
	 * @param sqlWhere
	 * @return
	 */
	<T> String getUpdateByWhereSqlText(T t, String sqlWhere);

	/**
	 * 获取条件更新脚本
	 * 
	 * @param t
	 * @param whereParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getUpdateByWhereSqlText(T t, List<SQLParam> whereParams);

	/**
	 * 获取条件更新脚本
	 * 
	 * @param setParams
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getUpdateByWhereSqlText(List<SQLParam> setParams, String sqlWhere, Class<T> cls);

	/**
	 * 获取条件更新脚本
	 * 
	 * @param setParams
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getUpdateByWhereSqlText(List<SQLParam> setParams, List<SQLParam> whereParams, Class<T> cls);

	/**
	 * 获取条件删除脚本
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> String getDeleteByWhereSqlText(String sqlWhere, Class<T> cls);

	/**
	 * 获取条件删除脚本
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getDeleteByWhereSqlText(List<SQLParam> whereParams, Class<T> cls);

	/**
	 * 获取实体数量脚本
	 * 
	 * @param cls
	 * @return
	 */
	<T> String getCountSqlText(Class<T> cls);

	/**
	 * 条件获取实体数量脚本
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> String getCountByWhereSqlText(String sqlWhere, Class<T> cls);

	/**
	 * 条件获取实体数量脚本
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getCountByWhereSqlText(List<SQLParam> whereParams, Class<T> cls);

	/**
	 * 获取查询实体脚本
	 * 
	 * @param cls
	 * @return
	 */
	<T> String getSelectSqlText(Class<T> cls);

	/**
	 * 获取条件查询实体脚本
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	<T> String getSelectByWhereSqlText(String sqlWhere, Class<T> cls);

	/**
	 * 获取条件查询实体脚本
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getSelectByWhereSqlText(List<SQLParam> whereParams, Class<T> cls);

	/**
	 * 获取条件查询实体脚本 带排序
	 * 
	 * @param sqlWhere
	 * @param orderWhere
	 * @param cls
	 * @return
	 */
	<T> String getSelectByWhereSqlText(String sqlWhere, String orderWhere, Class<T> cls);

	/**
	 * 获取条件查询实体脚本 带排序
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getSelectByWhereSqlText(List<SQLParam> whereParams, String orderWhere, Class<T> cls);

	/**
	 * 获取分页查询脚本
	 * 
	 * @param whereParams
	 * @param pager
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> String getSelectByPagerSqlText(List<SQLParam> whereParams, SQLPager pager, Class<T> cls);

	/**
	 * 获取分页查询脚本
	 * 
	 * @param sqlWhere
	 * @param pager
	 * @param cls
	 * @return
	 */
	<T> String getSelectByPagerSqlText(String sqlWhere, SQLPager pager, Class<T> cls);

	/**
	 * 统计字段和
	 * 
	 * @param cls
	 * @param fieldName
	 * @param sqlWhere
	 * @return
	 */
	<T> String getSumByWhereSqlText(Class<T> cls, String fieldName, String sqlWhere);

	/**
	 * 统计字段和
	 * 
	 * @param cls
	 * @param fieldName
	 * @param whereParams
	 * @return
	 */
	<T> String getSumByWhereSqlText(Class<T> cls, String fieldName, List<SQLParam> whereParams);
}

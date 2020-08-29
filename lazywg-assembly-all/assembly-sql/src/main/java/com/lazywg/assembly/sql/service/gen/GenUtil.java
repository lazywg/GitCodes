package com.lazywg.assembly.sql.service.gen;


import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.Table;
import com.lazywg.assembly.sql.utils.DateFmtUtil;
import com.lazywg.assembly.sql.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gaowang
 *
 * @createTime: 2019年1月18日 上午10:01:51
 *
 */
public class GenUtil {

	private String author;

	private GenImpl genImpl;

	private boolean enableSwagger;

	public GenUtil(SQLConfig config) {
		this("", config, false);
	}

	public GenUtil(String author, SQLConfig config, boolean enableSwagger) {
		this.author = author;
		this.enableSwagger = enableSwagger;
		this.genImpl = new GenImpl(config);
	}

	/**
	 * 生成实体类
	 * 
	 * @return
	 */
	public boolean genEntity(String outDir,String packageName) {

		List<Table> tables = genImpl.getTables();
		for (Table table : tables) {
			String className = humpName(table.getTableName(), false);
			String classComment = StringUtils.isBlank(table.getTableComment()) ? className : table.getTableComment().replace("\r","").replace("\n", " ").replace("\t", " ");
			StringBuilder entityBuilder = new StringBuilder();
			entityBuilder.append(String.format("package %s;\n", packageName));
			entityBuilder.append("\n");
			entityBuilder.append("import java.util.Date;\n");
			entityBuilder.append("import java.math.BigDecimal;\n");
			entityBuilder.append("import com.seevteen.sql.annotation.SQLField;\n");
			entityBuilder.append("import com.seevteen.sql.annotation.SQLKeyField;\n");
			entityBuilder.append("import com.seevteen.sql.annotation.SQLKeyFieldType;\n");
			entityBuilder.append("import com.seevteen.sql.annotation.SQLTable;\n");
			if (enableSwagger) {
				entityBuilder.append("import io.swagger.annotations.ApiModel;\n");
				entityBuilder.append("import io.swagger.annotations.ApiModelProperty;\n");
			}
			entityBuilder.append("\n");
			entityBuilder.append("/**\n");
			entityBuilder.append(" *\n");
			entityBuilder.append(String.format(" * @author: %s\n", author));
			entityBuilder.append(" *\n");
			entityBuilder.append(String.format(" * @createTime: %s\n", DateFmtUtil.currentDateFormate()));
			entityBuilder.append(" *\n");
			entityBuilder.append(String.format(" * @comment: %s\n", classComment));
			entityBuilder.append(" *\n");
			entityBuilder.append(" */\n");
			entityBuilder.append("\n");
			if (enableSwagger) {
				entityBuilder.append(String.format("@ApiModel(description = \"%s\")\n", classComment));
			}
			entityBuilder.append(String.format("@SQLTable(\"%s\")\n", table.getTableName()));
			entityBuilder.append(String.format("public class %s {\n", className));

			List<Column> columns = genImpl.getColumns(table.getTableName());

			for (Column column : columns) {
				String javaType = getColumnJavaType(column.getColumnType());
				String fieldName = humpName(column.getColumnName(), true);
				String comment = StringUtils.isBlank(column.getColumnComment()) ? fieldName : column.getColumnComment().replace("\r", "").replace("\n", " ").replace("\t", " ");
				entityBuilder.append("\n");
				entityBuilder.append("\t/**\n");
				entityBuilder.append("\t *\n");
				entityBuilder.append(String.format("\t * %s\n", comment));
				entityBuilder.append("\t *\n");
				entityBuilder.append("\t */\n");
				if (enableSwagger) {
					entityBuilder.append(String.format("\t@ApiModelProperty(name = \"%s\", value = \"%s\")\n", fieldName, comment));
				}
				if ("PRI".equals(column.getColumnKey()) || "P".equals(column.getColumnKey()) || "1".equals(column.getColumnKey())) {
					entityBuilder.append("\t@SQLKeyField(SQLKeyFieldType.PRIMARY)\n");
				}
				entityBuilder.append(String.format("\t@SQLField(value = \"%s\"%s)\n", column.getColumnName(), fieldName.toLowerCase().contains("create") ? ",supportCmdTypes = 7" : ""));
				entityBuilder.append(String.format("\tprivate %s %s;\n", javaType, fieldName));
			}

			for (Column column : columns) {
				String javaType = getColumnJavaType(column.getColumnType());
				String fieldName = humpName(column.getColumnName(), true);
				String methodName = humpName(column.getColumnName(), false);
				String comment = StringUtils.isBlank(column.getColumnComment()) ? fieldName : column.getColumnComment().replace("\r", "").replace("\n", " ").replace("\t", " ");

				entityBuilder.append("\n");
				entityBuilder.append("\t/**\n");
				entityBuilder.append("\t *\n");
				entityBuilder.append(String.format("\t * 获取%s\n", comment));
				entityBuilder.append("\t *\n");
				entityBuilder.append("\t */\n");
				entityBuilder.append(String.format("\tpublic %s get%s(){\n", javaType, methodName));
				entityBuilder.append(String.format("\t\treturn this.%s;\n", fieldName));
				entityBuilder.append("\t}\n");

				entityBuilder.append("\n");
				entityBuilder.append("\t/**\n");
				entityBuilder.append("\t *\n");
				entityBuilder.append(String.format("\t * 设置%s\n", comment));
				entityBuilder.append("\t *\n");
				entityBuilder.append(String.format("\t * @param %s\n", fieldName));
				entityBuilder.append("\t */\n");
				entityBuilder.append(String.format("\tpublic void set%s(%s %s){\n", methodName, javaType, fieldName));
				entityBuilder.append(String.format("\t\tthis.%s = %s;\n", fieldName, fieldName));
				entityBuilder.append("\t}\n");
			}

			entityBuilder.append("}\n");

			FileUtil.writeFile(entityBuilder.toString(), FileUtil.combinPath(outDir, String.format("%s.java", className)));
		}
		return true;
	}

	/**
	 * 生成控制器
	 * 
	 * @return
	 */
	public boolean genController(String outDir,String packageName) {
		List<Table> tables = genImpl.getTables();
		for (Table table : tables) {
			String className = humpName(table.getTableName(), false);
			String classComment = StringUtils.isBlank(table.getTableComment()) ? className : table.getTableComment().replace("\r", "").replace("\n", " ").replace("\t", " ");
			StringBuilder entityBuilder = new StringBuilder();
			entityBuilder.append(String.format("package %s;\n", packageName));
			entityBuilder.append("\n");
			entityBuilder.append("import org.springframework.http.MediaType;\n");
			entityBuilder.append("import org.springframework.web.bind.annotation.RequestBody;\n");
			entityBuilder.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
			entityBuilder.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
			entityBuilder.append("import org.springframework.web.bind.annotation.RestController;\n");
			entityBuilder.append("\n");
			entityBuilder.append(String.format("import %s.entity.%s;\n", packageName, className));
			entityBuilder.append("\n");
			if (enableSwagger) {
				entityBuilder.append("import io.swagger.annotations.Api;\n");
				entityBuilder.append("import io.swagger.annotations.ApiOperation;\n");
				entityBuilder.append("import io.swagger.annotations.ApiParam;\n");
			}
			entityBuilder.append("\n");
			entityBuilder.append("/**\n");
			entityBuilder.append(" *\n");
			entityBuilder.append(String.format(" * @author: %s\n", author));
			entityBuilder.append(" *\n");
			entityBuilder.append(String.format(" * @createTime: %s\n", DateFmtUtil.currentDateFormate()));
			entityBuilder.append(" *\n");
			entityBuilder.append(String.format(" * @comment: %s控制器\n", classComment));
			entityBuilder.append(" *\n");
			entityBuilder.append(" */\n");
			if (enableSwagger) {
				entityBuilder.append(String.format("@Api(tags=\"%s接口\")\n", classComment));
			}
			entityBuilder.append("@RestController\n");
			entityBuilder.append(String.format("@RequestMapping(\"/%s\")\n", className));
			entityBuilder.append(String.format("public class %sController {\n", className));

			//增
			entityBuilder.append("\n");
			entityBuilder.append("\t/**\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * 新增 %s\n", classComment));
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * @param model %s实例\n", classComment));
			entityBuilder.append("\t * @return\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append("\t */\n");
			if (enableSwagger) {
				entityBuilder.append(String.format("\t@ApiOperation(value = \"新增 %s\")\n", classComment));
			}
			entityBuilder.append("\t@RequestMapping(value = \"/add\", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)\n");
			entityBuilder.append(String.format("\tpublic String add(@RequestBody @ApiParam(value = \"%s\", name = \"model\")%s model) {\n", classComment,className));
			entityBuilder.append("\t\t return null;\n");
			entityBuilder.append("\t}\n");
			
			//删
			entityBuilder.append("\n");
			entityBuilder.append("\t/**\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * 删除 %s\n", classComment));
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * @param id %s主键\n", classComment));
			entityBuilder.append("\t * @return\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append("\t */\n");
			if (enableSwagger) {
				entityBuilder.append(String.format("\t@ApiOperation(value = \"主键删除 %s\")\n", classComment));
			}
			entityBuilder.append("\t@RequestMapping(value = \"/delete\", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)\n");
			entityBuilder.append("\tpublic String delete(@ApiParam(value = \"主键\", name = \"id\")String id) {\n");
			entityBuilder.append("\t\t return null;\n");
			entityBuilder.append("\t}\n");
			
			//改
			entityBuilder.append("\n");
			entityBuilder.append("\t/**\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * 修改 %s\n", classComment));
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * @param model 修改后%s实例\n", classComment));
			entityBuilder.append("\t * @return\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append("\t */\n");
			if (enableSwagger) {
				entityBuilder.append(String.format("\t@ApiOperation(value = \"修改 %s\")\n", classComment));
			}
			entityBuilder.append("\t@RequestMapping(value = \"/modify\", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)\n");
			entityBuilder.append(String.format("\tpublic String modify(@RequestBody @ApiParam(value = \"%s\", name = \"model\")%s model) {\n", classComment,className));
			entityBuilder.append("\t\t return null;\n");
			entityBuilder.append("\t}\n");
			
			//查
			entityBuilder.append("\n");
			entityBuilder.append("\t/**\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * 主键查询 %s\n", classComment));
			entityBuilder.append("\t *\n");
			entityBuilder.append(String.format("\t * @param id %s主键\n", classComment));
			entityBuilder.append("\t * @return\n");
			entityBuilder.append("\t *\n");
			entityBuilder.append("\t */\n");
			if (enableSwagger) {
				entityBuilder.append(String.format("\t@ApiOperation(value = \"主键查询 %s\")\n", classComment));
			}
			entityBuilder.append("\t@RequestMapping(value = \"/query\", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)\n");
			entityBuilder.append("\tpublic String query(@ApiParam(value = \"主键\", name = \"id\")String id) {\n");
			entityBuilder.append("\t\t return null;\n");
			entityBuilder.append("\t}\n");
			
			entityBuilder.append("}\n");

			FileUtil.writeFile(entityBuilder.toString(), FileUtil.combinPath(outDir, String.format("%sController.java", className)));
		}
		return true;
	}

	/**
	 * 获取驼峰命名
	 * 
	 * @param name
	 * @param isField
	 * @return
	 */
	public static String humpName(String name, boolean isField) {
		String[] ary = name.split("_|-");
		StringBuilder nameBuilder = new StringBuilder();
		boolean isFirst = true;
		for (String item : ary) {
			if (StringUtils.isBlank(item)) {
				continue;
			}
			String firstChar = item.substring(0, 1);
			String leftChars = item.substring(1, item.length());
			if (isField && isFirst) {
				isFirst = false;
				nameBuilder.append(firstChar.toLowerCase() + (StringUtils.isAllUpperCase(leftChars) ? leftChars.toLowerCase() : leftChars));
			} else {
				nameBuilder.append(firstChar.toUpperCase() + (StringUtils.isAllUpperCase(leftChars) ? leftChars.toLowerCase() : leftChars));
			}
		}
		return nameBuilder.toString();
	}

	private static Map<String, String> columnTypeDict = new HashMap<String, String>();

	static {
		columnTypeDict.put("VARCHAR", "String");
		columnTypeDict.put("NVARCHAR", "String");
		columnTypeDict.put("VARCHAR2", "String");
		columnTypeDict.put("NVARCHAR2", "String");
		columnTypeDict.put("CHAR", "String");
		columnTypeDict.put("NCHAR", "String");
		columnTypeDict.put("TEXT", "String");
		columnTypeDict.put("NTEXT", "String");
		columnTypeDict.put("INT", "Integer");
		columnTypeDict.put("SMALLINT", "Byte");
		columnTypeDict.put("TINYINT", "Byte");
		columnTypeDict.put("INTEGER", "Integer");
		columnTypeDict.put("FLOAT", "Float");
		columnTypeDict.put("DOUBLE", "Double");
		columnTypeDict.put("DECIMAL", "BigDecimal");
		columnTypeDict.put("BIT", "Boolean");
		columnTypeDict.put("BIGINT", "Long");
		columnTypeDict.put("DATETIME", "Date");
		columnTypeDict.put("DATE", "Date");
		columnTypeDict.put("TIMESTAMP", "Date");
		columnTypeDict.put("NUMBER", "Integer");
	}

	/**
	 * 数据库类型对应Java类型
	 * 
	 * @param dbTypeName
	 * @return
	 */
	public static String getColumnJavaType(String dbTypeName) {
		if (StringUtils.isBlank(dbTypeName)) {
			throw new IllegalArgumentException("dbTypeName is null or empty.");
		}
		if (columnTypeDict.containsKey(dbTypeName.toUpperCase())) {
			return columnTypeDict.get(dbTypeName.toUpperCase());
		}
		return null;
	}
}

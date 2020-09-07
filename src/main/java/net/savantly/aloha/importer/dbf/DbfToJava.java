package net.savantly.aloha.importer.dbf;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

public class DbfToJava {
	
	/**
	 * generate a starter class file for a given dbf
	 * 
	 * @param dfFileInput
	 * @param className
	 * @return 
	 */
	public String generate(InputStream dfFileInput, String className) {
		DBFReader reader = null;
		StringBuilder sb = new StringBuilder(""
				+ "@Data\n"
				+ "@Entity\n"
				+ "@Table(indexes = {@Index(columnList=\"posKey\", unique = false)})\n"
				+ "@IdClass(CHANGEME.class)\n"
				+ "public class "+ className +" implements ImportIdentifiable {\n"
				+ "\n" + 
				"	private Long posKey;\n" + 
				"	private Long importId;\n" + 
				"	private Date importDate;\n");
		try {

			reader = new DBFReader(dfFileInput);

			int numberOfFields = reader.getFieldCount();

			for (int i = 0; i < numberOfFields; i++) {
				DBFField tableField = reader.getField(i);
				sb.append(String.format("	%s\nprivate %s %s;\n", getAnnotations(tableField), getFieldType(tableField), getFieldName(tableField)));
			}
			sb.append("\n}\n");
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (Objects.nonNull(reader)) {
				reader.close();
			}
		}
		return sb.toString();
	}

	private String getFieldName(DBFField tableField) {
		return tableField.getName().toLowerCase();
	}
	
	private String getAnnotations(DBFField tableField) {
		
		int length = tableField.getLength();
		
		switch (tableField.getType()) {
			case CHARACTER:
			case VARCHAR:
				return String.format("	@Column(length = %s)", length);
			default: return "";
		}
	}


	private String getFieldType(DBFField tableField) {
		String name = tableField.getName().toLowerCase();
		switch (tableField.getType()) {
		case AUTOINCREMENT:
			return "long";
		case BINARY:
			return "byte[]";
		case BLOB:
			return "byte[]";
		case CHARACTER: 
			return "String";
		case CURRENCY:
			return "BigDecimal";
		case DATE:
			return "Date";
		case DOUBLE:
			return "Double";
		case FLOATING_POINT:
			return "Float";
		case GENERAL_OLE:
			return "byte[]";
		case LOGICAL:
			return "byte[]";
		case LONG:
			if(tableField.isNullable()) {
				return "Long";
			} else {
				return "long";
			}
		case MEMO:
			return "byte[]";
		case NULL_FLAGS:
			return "String";
		case NUMERIC:
			if(soundsLikeDecimal(name)){
				return "BigDecimal";
			} else {
				return "Long";
			}
		case PICTURE:
			return "byte[]";
		case TIMESTAMP:
			return "Instant";
		case TIMESTAMP_DBASE7:
			return "Instant";
		case UNKNOWN:
			return "String";
		case VARBINARY:
			return "byte[]";
		case VARCHAR:
			return "String";
		default:
			return "String";
		}
	}

	List<String> likelyDecimalNames = Arrays.asList("price", "amt", "amount", "cost", "charge", "delta", "change", "chg", "money", "tax", "sale");
	private boolean soundsLikeDecimal(String name) {
		for (String string : likelyDecimalNames) {
			if(name.contains(string)) {
				return true;
			}
		}
		return false;
	}

}

package com.brimzi.app.filetransferpc;

import java.nio.charset.StandardCharsets;

public class UtilityClass {

	public static final Object FILE_ENTRY_DELIMITER = "|";
	public static final String MESSAGE_DELIMETTER = "|";

	public static String getFileEntry(String path, String metadata) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String DeserializeBytes(byte[] bytes){
		return new String(bytes,StandardCharsets.UTF_8);
	}
}

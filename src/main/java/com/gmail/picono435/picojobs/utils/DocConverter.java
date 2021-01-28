/*
 * DocConverter
 * Copyright https://github.com/assimbly/docconverter
 *
 * This product includes software developed by
 * assimbly (https://github.com/assimbly)
 *
 * This software contains code modified by Picono435 (https://github.com/Picono435)
 */

package com.gmail.picono435.picojobs.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public final class DocConverter {

	private static String yaml;
	private static String json;

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	/**
	* @param path as string (uses UTF-8 for encoding)
    * @return String
    * @throws Exception (generic exception)
	*/
    public static String convertFileToString(String path) throws Exception {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  String fileAsString = new String(encoded, StandardCharsets.UTF_8);
	  return fileAsString;
	}

	/**
	* @param json as string
    * @return yaml as string 
    * @throws Exception (generic exception)
	*/
	public static String convertJsonToYaml(String json) throws Exception {

		JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
		yaml = new YAMLMapper().writeValueAsString(jsonNodeTree);

		return yaml;
	}

	/**
	* @param yaml as string
    * @return json as string 
    * @throws Exception (generic exception)
	*/	
	public static String convertYamlToJson(String yaml) throws Exception {

		ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
		Object obj = yamlReader.readValue(yaml, Object.class);

		ObjectMapper jsonWriter = new ObjectMapper();
		json = jsonWriter.writeValueAsString(obj);

		return json;
	}	
	
}
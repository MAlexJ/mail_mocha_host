package com.malex.service;

import java.util.ResourceBundle;

/**
 * The class is intended for search of values in the property file
 *
 * @author malex
 */
public class PropertiesReaderAppService {

	/**
	 * File name: mail.property
	 */
	private final static String FILE_NAME = "mail";

	/**
	 * Get value by key from property
	 *
	 * @param propertyName the name of parameter
	 * @return the value of the specified parameter
	 */
	public String getProperty(String propertyName) {

		ResourceBundle resource = ResourceBundle.getBundle(FILE_NAME);

		return resource.getString(propertyName);
	}

}
package io;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;
/**
 * public class to adapt LocalDateTime objects in order to use with XML
 * @author carolinenilsson
 *
 */
public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal(String localDateTime) throws Exception {
		//Create a LocalDateTime out of a String
		return LocalDateTime.parse(localDateTime);
	}

	@Override
	public String marshal(LocalDateTime localDateTime) throws Exception {
		//Creates a String from a LocalDateTime
		return localDateTime.toString();
	}

	
}
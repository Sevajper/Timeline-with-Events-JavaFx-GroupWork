package io;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import functions.Timeline;

public class FileHandler {

	/**
	 * method to save a Timeline with Events to an XML-file
	 * @param timeline - Timeline to be saved
	 * @param file - Filepath
	 * @throws Exception
	 */
	public void saveTimeline(Timeline timeline, File file) throws Exception {

		JAXBContext context = JAXBContext.newInstance(Timeline.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(timeline, file);

	}
	/**
	 * method to load a timeline from file
	 * @param file - file to load Timeline and Events from
	 * @return Timeline
	 * @throws Exception
	 */
	public Timeline loadTimeline(File file) throws Exception {

		JAXBContext context = JAXBContext.newInstance(Timeline.class);
		Unmarshaller unMarshaller = context.createUnmarshaller();

		return (Timeline) unMarshaller.unmarshal(file);

	}

}
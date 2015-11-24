package framework.auto.utils;

//Util methods for handling logging
import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

public class LogUtil {
	
	public static Log getLog(Class<?> className, String fileName) {
		Log logger = LogFactory.getLog(className);
		File myFile = new File(fileName);
		DOMConfigurator.configure(myFile.getAbsolutePath());
		return logger;
	}
	/**
	 * This will create the log with the default file name log4j.xml
	 * 
	 * @param className
	 * @return
	 */
	public static Log getLog(Class<?> className) {
		return getLog(className, "log4j.xml");
	}

	/*
	 * Method for log4j formatting of the message
	 */
	public String printLogMessage(String message) {
		return "\n******************" + message + "******************\n";
	}

}


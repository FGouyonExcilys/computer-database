package fr.excilys.computer_database.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggers {

	public static Logger LOGGER = LoggerFactory.getLogger(Loggers.class);

	public static void writeLog(String titre, String message) {

		try {

			FileWriter fileWriter = new FileWriter("Logs.log", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

			printWriter.println(LocalDateTime.now().format(dateTimeFormatter));
			printWriter.println("Niveau de log: " + titre);
			printWriter.println("Message: " + message + "\n");
			printWriter.close();

		} catch (IOException e) {
			Loggers.afficherMessageError("Erreur écriture de log");
		}
	}

	public static void afficherMessageDebug(String message) {

		LOGGER.debug(message);

		writeLog("DEBUG", message);

	}

	public static void afficherMessageInfo(String message) {

		LOGGER.info(message);

		writeLog("INFO", message);

	}
	
	public static void afficherMessageWarn(String message) {

		LOGGER.warn(message);

		writeLog("WARN", message);

	}
	public static void afficherMessageError(String message) {

		LOGGER.error(message);

		writeLog("ERROR", message);

	}
}

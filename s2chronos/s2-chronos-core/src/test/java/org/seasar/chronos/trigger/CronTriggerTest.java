package org.seasar.chronos.trigger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.seasar.chronos.S2TestCaseBase;
import org.seasar.chronos.core.trigger.CronTrigger;

public class CronTriggerTest extends S2TestCaseBase {

	private static final String SERIALIZABLE_OBJECT_DAT_FILENAME = "serializableObject.dat";

	private static final String CRON_EXPRESSION = "5 0 * * *";

	private CronTrigger cronTrigger;

	public void testSerialize() {

		try {
			OutputStream outputStream = new FileOutputStream(
					SERIALIZABLE_OBJECT_DAT_FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			cronTrigger.setCronExpression(CRON_EXPRESSION);
			oos.writeObject(cronTrigger);
			oos.flush();
			oos.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void testDeserialize() {

		try {
			InputStream inputStream = new FileInputStream(
					SERIALIZABLE_OBJECT_DAT_FILENAME);
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			CronTrigger _cronTrigger = (CronTrigger) ois.readObject();
			assertEquals(CRON_EXPRESSION, _cronTrigger.getCronExpression());
			ois.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}

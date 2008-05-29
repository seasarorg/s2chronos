package org.seasar.chronos.extension.persitense;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.junit.Test;
import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.extension.task.TestTask;
import org.seasar.framework.exception.IORuntimeException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SerializebleObjectTest extends S2TestCaseBase {

	private static final long serialVersionUID = 1L;

	@Test
	public void testSerializebleObject() throws NotFoundException,
			CannotCompileException, InstantiationException,
			IllegalAccessException {

		XStream xstream = new XStream(new DomDriver());

		Class<?> clazz = SerializeFactory.createProxy(TestTask.class);
		TestTask a = (TestTask) clazz.newInstance();

		// a.setTestTask((TestTask) clazz.newInstance());
		// a.getTestTask().setName("hoge");

		String xml = xstream.toXML(a);

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			File targetFile = new File("C:\\temp\\", TestTask.class.getName());
			fos = new FileOutputStream(targetFile);
			oos = xstream.createObjectOutputStream(fos);
			oos.writeObject(a);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
		}
	}

	@Test
	public void testDeserializebleObject() {
		XStream xstream = new XStream();
		TestTask target = null;
		String className = TestTask.class.getName();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			File targetFile = new File("C:\\temp\\", className);
			if (targetFile.exists()) {
				fis = new FileInputStream(targetFile);
				ois = xstream.createObjectInputStream(fis);
				target = (TestTask) ois.readObject();
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
		}
		System.out.println(target);
		System.out.println(target.getName());
		// System.out.println(target.id);
		// System.out.println(target.testTask);
		// System.out.println(target.testTask.name);
		// System.out.println(target.testTask.id);
	}
}

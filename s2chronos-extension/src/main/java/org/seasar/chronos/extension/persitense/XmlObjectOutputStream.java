package org.seasar.chronos.extension.persitense;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlObjectOutputStream extends ObjectOutputStream {

	private DocumentBuilderFactory dbfactory = DocumentBuilderFactory
			.newInstance();

	protected XmlObjectOutputStream() throws IOException, SecurityException {
		super();
	}

	public XmlObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeObjectOverride(Object object) throws IOException {
		String xml = null;
		try {
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if (object instanceof String) {

		} else if (object instanceof Map) {

		}
		this.writeUTF(xml);
	}
}

package org.seasar.chronos.extension.persitense;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class XmlObjectOutputStream extends ObjectOutputStream {

	protected XmlObjectOutputStream() throws IOException, SecurityException {
		super();
	}

	public XmlObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeObjectOverride(Object object) throws IOException {
		String xml = null;

		this.writeUTF(xml);
		// super.writeObjectOverride(object);
	}

}

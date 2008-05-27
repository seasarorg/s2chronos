package org.seasar.chronos.extension.persitense;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class XmlObjectInputStream extends ObjectInputStream {

	public XmlObjectInputStream() throws IOException, SecurityException {
		super();
	}

	public XmlObjectInputStream(InputStream arg0) throws IOException {
		super(arg0);
	}

	@Override
	protected Object readObjectOverride() throws IOException,
			ClassNotFoundException {
		return super.readObjectOverride();
	}

}

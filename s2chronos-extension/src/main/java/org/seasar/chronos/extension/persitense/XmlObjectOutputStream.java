package org.seasar.chronos.extension.persitense;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

public class XmlObjectOutputStream extends ObjectOutputStream {

	public XmlObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected Object replaceObject(Object obj) throws IOException {
		System.out.println("replaceObject = " + obj);
		return super.replaceObject(obj);
	}

	@Override
	protected void annotateClass(Class<?> cl) throws IOException {
		System.out.println("annotateClass = " + cl);
		super.annotateClass(cl);
	}

	@Override
	public void close() throws IOException {
		System.out.println("close");
		super.close();
	}

	@Override
	public void defaultWriteObject() throws IOException {
		System.out.println("defaultWriteObject");
		super.defaultWriteObject();
	}

	@Override
	public void flush() throws IOException {
		System.out.println("flush");
		super.flush();
	}

	@Override
	public void reset() throws IOException {
		System.out.println("reset");
		super.reset();
	}

	@Override
	public void useProtocolVersion(int version) throws IOException {
		System.out.println("useProtocolVersion = " + version);
		super.useProtocolVersion(version);
	}

	@Override
	public void write(byte[] buf, int off, int len) throws IOException {
		System.out.println("write2 = " + buf);
		super.write(buf, off, len);
	}

	@Override
	public void write(byte[] buf) throws IOException {
		System.out.println("write = " + buf);
		super.write(buf);
	}

	@Override
	public void write(int val) throws IOException {
		System.out.println("write = " + val);
		super.write(val);
	}

	@Override
	protected void annotateProxyClass(Class<?> cl) throws IOException {
		System.out.println("annotateProxyClass = " + cl);
		super.annotateProxyClass(cl);
	}

	@Override
	protected void drain() throws IOException {
		System.out.println("drain");
		super.drain();
	}

	@Override
	protected boolean enableReplaceObject(boolean enable)
			throws SecurityException {
		System.out.println("enableReplaceObject = " + enable);
		return super.enableReplaceObject(enable);
	}

	@Override
	protected void writeClassDescriptor(ObjectStreamClass desc)
			throws IOException {
		System.out.println("writeClassDescriptor = " + desc);
		super.writeClassDescriptor(desc);
	}

	@Override
	protected void writeObjectOverride(Object obj) throws IOException {
		System.out.println("writeObjectOverride = " + obj);
		super.writeObjectOverride(obj);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		System.out.println("writeStreamHeader");
		super.writeStreamHeader();
	}

	@Override
	public void writeUnshared(Object obj) throws IOException {
		System.out.println("writeUnshared = " + obj);
		super.writeUnshared(obj);
	}

	@Override
	public void writeFields() throws IOException {
		System.out.println("writeFields");
		super.writeFields();
	}

	@Override
	public PutField putFields() throws IOException {
		PutField ret = super.putFields();
		System.out.println("putFields = " + ret);
		return ret;
	}

	@Override
	public void writeBoolean(boolean val) throws IOException {
		System.out.println("writeBoolean = " + val);
		super.writeBoolean(val);
	}

	@Override
	public void writeByte(int val) throws IOException {
		System.out.println("writeByte = " + val);
		super.writeByte(val);
	}

	@Override
	public void writeChar(int val) throws IOException {
		System.out.println("writeChar = " + val);
		super.writeChar(val);
	}

	@Override
	public void writeDouble(double val) throws IOException {
		System.out.println("writeDouble = " + val);
		super.writeDouble(val);
	}

	@Override
	public void writeFloat(float val) throws IOException {
		System.out.println("writeFloat = " + val);
		super.writeFloat(val);
	}

	@Override
	public void writeInt(int val) throws IOException {
		System.out.println("writeInt = " + val);
		super.writeInt(val);
	}

	@Override
	public void writeLong(long val) throws IOException {
		System.out.println("writeLong = " + val);
		super.writeLong(val);
	}

	@Override
	public void writeShort(int val) throws IOException {
		System.out.println("writeShort = " + val);
		super.writeShort(val);
	}

	@Override
	public void writeBytes(String str) throws IOException {
		System.out.println("writeBytes = " + str);
		super.writeBytes(str);
	}

	@Override
	public void writeChars(String str) throws IOException {
		System.out.println("writeChars = " + str);
		super.writeChars(str);
	}

	@Override
	public void writeUTF(String str) throws IOException {
		System.out.println("writeUTF = " + str);
		super.writeUTF(str);
	}

}

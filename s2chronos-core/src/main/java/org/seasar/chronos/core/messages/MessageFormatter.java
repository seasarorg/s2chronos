package org.seasar.chronos.core.messages;

import java.text.MessageFormat;

import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.message.MessageResourceBundleFactory;

public final class MessageFormatter {
	private static final int DIGITS = 9;

	private static final String MESSAGES = "Messages";

	private MessageFormatter() {
	}

	public static String getMessage(String messageCode, Object[] args) {
		if (messageCode == null) {
			messageCode = "";
		}
		return getFormattedMessage(messageCode, getSimpleMessage(messageCode,
				args));
	}

	public static String getFormattedMessage(String messageCode,
			String simpleMessage) {
		return "[" + messageCode + "]" + simpleMessage;
	}

	public static String getSimpleMessage(String messageCode, Object[] arguments) {

		try {
			String pattern = getPattern(messageCode);
			if (pattern != null) {
				return MessageFormat.format(pattern, arguments);
			}
		} catch (Throwable ignore) {
		}
		return getNoPatternMessage(arguments);
	}

	private static String getPattern(String messageCode) {
		MessageResourceBundle resourceBundle = getMessages(getSystemName(messageCode));
		if (resourceBundle == null) {
			return null;
		}

		int length = messageCode.length();
		if (length > 8) {
			String key = messageCode.charAt(0)
					+ messageCode.substring(length - DIGITS);
			String pattern = resourceBundle.get(key);
			if (pattern != null) {
				return pattern;
			}
		}
		return resourceBundle.get(messageCode);
	}

	private static String getSystemName(String messageCode) {
		String result = messageCode.substring(1, Math.max(1, messageCode
				.length()
				- DIGITS));
		return result;
	}

	private static MessageResourceBundle getMessages(String systemName) {
		return MessageResourceBundleFactory.getBundle(systemName + MESSAGES);
	}

	private static String getNoPatternMessage(Object[] args) {
		if (args == null || args.length == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(args[i] + ", ");
		}
		buffer.setLength(buffer.length() - 2);
		return buffer.toString();
	}
}

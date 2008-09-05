package org.seasar.chronos.sastruts.example.mai;

import org.seasar.chronos.sastruts.example.dto.MailDto;

public interface RegisterMai {

	public void sendRegisterMail(MailDto dto);

	public void sendUnregisterMail(MailDto dto);

}

package org.seasar.chronos.sastruts.example.dxo;

import java.util.List;

import org.seasar.chronos.sastruts.example.entity.Event;
import org.seasar.framework.beans.util.BeanMap;

public interface EventDxo {

	public List<BeanMap> convert(List<Event> eventList);

}

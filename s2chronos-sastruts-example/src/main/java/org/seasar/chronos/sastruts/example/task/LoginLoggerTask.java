package org.seasar.chronos.sastruts.example.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.framework.log.Logger;

@SuppressWarnings("serial")
@Task
@CronTrigger(expression = "0 */1 * * * ?")
public class LoginLoggerTask implements Serializable{
	/** Logger */
	private static final Logger log = Logger.getLogger(LoginLoggerTask.class);
	private List<Map<String,Object>>  loginInfoList = new ArrayList<Map<String,Object>>();
	public void addLoginInfo(String userName, long time){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("time", time);
		loginInfoList.add(map);
	}
	public void doExecute() {
		for(Map<String,Object> map:loginInfoList){
			log.info("logged = "+(String)map.get("userName")+","+new Date((Long)map.get("time")).toString());
		}
	}
}

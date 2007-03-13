package org.seasar.chronos.job.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.job.Transition;
import org.seasar.chronos.job.impl.JobMethodMetaData;

public class JobMethodExecuteHandlerImpl extends AbstractTaskExecuteHandler {
	private static final String METHOD_PREFIX_NAME_DO = "do";

	@Override
	public Transition handleRequest(String startTaskName)
			throws InterruptedException {
		MethodInvoker mi = this.getMethodInvoker();
		List<AsyncResult> asyncResultList = new ArrayList<AsyncResult>();
		// 連続でジョブを呼び出す
		String nextTaskName = startTaskName;
		while (true) {
			String firstChar = nextTaskName.substring(0, 1);
			String afterString = nextTaskName.substring(1);
			final String methodName = METHOD_PREFIX_NAME_DO
					+ firstChar.toUpperCase() + afterString;

			JobMethodMetaData md = new JobMethodMetaData(mi
					.getMethod(methodName));

			for (int i = 0; i < md.getCloneSize(); i++) {
				AsyncResult ar = mi.beginInvoke(methodName);
				asyncResultList.add(ar);
			}

			Object returnValue = null;
			String _nextTaskName = null;
			if (md.getJoinType() == JoinType.Wait) {
				for (AsyncResult ar : asyncResultList) {
					returnValue = mi.endInvoke(ar);
				}
				// 同期の場合で戻り値にStringでジョブ名を返した場合は遷移先を上書き
				if (returnValue instanceof String) {
					_nextTaskName = (String) returnValue;
				}
			}
			nextTaskName = _nextTaskName != null ? _nextTaskName : md
					.getNextTask();
			if (nextTaskName == null) {
				break;
			} else if (this.getMethodGroupMap().existGroup(nextTaskName)) {
				return new Transition();
			}
		}

		for (AsyncResult ar : asyncResultList) {
			mi.endInvoke(ar);
		}
		return new Transition(true);

	}

}

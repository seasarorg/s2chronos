package org.seasar.chronos.sastruts.example.task;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ExportActiveMemberTaskTest {

	private ExportActiveMemberTask exportActiveMemberTask;

	public void postBindFields() {
		exportActiveMemberTask.initialize();
		exportActiveMemberTask.start();
	}

	public void preUnbindFields() {
		exportActiveMemberTask.finish();
	}

	@Test
	public void testDoExportCsv() throws IOException {
		exportActiveMemberTask.doExportCsv();
	}

}

package org.seasar.chronos.core.task;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.chronos.core.task.TaskAnnotationReader.TriggerAnnotationHandler;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.util.tiger.ReflectionUtil;

@RunWith(Seasar2.class)
public class TaskAnnotationReaderTest  {
	
	@Task
	@NonDelayTrigger
	private static class ValidTask{
		public void initialize(){
			
		}
	}
	
	@Task
	private static class ValidTask_TaskProperty{
		public TaskTrigger getTrigger(){
			return null;
		}
		public void initialize(){
			
		}
	}
	

	@NonDelayTrigger
	private static class InValidTask_TaskAnnotation{
		public void initialize(){
			
		}
	}
	
	private static class InValidTask_TaskTriggerAnnotation{
		public void initialize(){
			
		}
	}
	
	@Task
	private static class InValidValidTask_TaskProperty{
		public void initialize(){
			
		}
	}
	
	private TaskAnnotationReader taskAnnotationReader;
	
	public void postBindFields(){
		
	}
	

	@Test
	public void testHasTaskAnnotation() {
		taskAnnotationReader.setup(ValidTask.class);
		assertTrue(taskAnnotationReader.hasTaskAnnotation());
		taskAnnotationReader.setup(InValidTask_TaskAnnotation.class);
		assertFalse(taskAnnotationReader.hasTaskAnnotation());
	}

	@Test
	public void testHasTriggerAnnotation() {
		taskAnnotationReader.setup(ValidTask.class);
		assertTrue(taskAnnotationReader.hasTriggerAnnotation());
		taskAnnotationReader.setup(InValidTask_TaskTriggerAnnotation.class);
		assertFalse(taskAnnotationReader.hasTriggerAnnotation());
	}

	@Test
	public void testHasTriggerProperty() {
		taskAnnotationReader.setup(ValidTask_TaskProperty.class);
		assertTrue(taskAnnotationReader.hasTriggerProperty());
		taskAnnotationReader.setup(InValidValidTask_TaskProperty.class);
		assertFalse(taskAnnotationReader.hasTriggerProperty());	
	}

	@Test
	public void testGetTaskAnnotation() {
		taskAnnotationReader.setup(ValidTask.class);
		Task task = taskAnnotationReader.getTaskAnnotation();
		assertNotNull(task);
	}

	@Test
	public void testGetTriggerAnnotationClass() {
		TriggerAnnotationHandler tah = new TriggerAnnotationHandler(){
			public TaskTrigger process(Annotation annotaion,
					Class<?> triggerAnnotationClass) {
				TaskTrigger taskTrigger = (TaskTrigger) ReflectionUtil
				.newInstance(triggerAnnotationClass);
				return taskTrigger;
			}};
		taskAnnotationReader.setup(ValidTask.class);
		TaskTrigger taskTrigger = taskAnnotationReader.getTriggerAnnotationClass(tah);
		assertNotNull(taskTrigger);
		taskAnnotationReader.setup(InValidTask_TaskTriggerAnnotation.class);
		taskTrigger = taskAnnotationReader.getTriggerAnnotationClass(tah);
		assertNull(taskTrigger);
	}

}

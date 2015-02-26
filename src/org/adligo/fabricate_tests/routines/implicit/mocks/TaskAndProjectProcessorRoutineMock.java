package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.AbstractRoutine;
import org.adligo.fabricate.routines.I_ProjectProcessor;
import org.adligo.fabricate.routines.I_TaskProcessor;

public class TaskAndProjectProcessorRoutineMock extends AbstractRoutine implements
  I_TaskProcessor, I_ProjectProcessor {
  private final String project_;
  private final String task_;
  
  public TaskAndProjectProcessorRoutineMock(String task, String project) {
    task_ = task;
    project_ = project;
  }

  @Override
  public String getCurrentProject() {
    return project_;
  }

  @Override
  public String getCurrentTask() {
    return task_;
  }
}

package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.AbstractRoutine;
import org.adligo.fabricate.routines.I_TaskProcessor;

public class TaskProcessorRoutineMock extends AbstractRoutine implements I_TaskProcessor {
  private String task_;
  
  public TaskProcessorRoutineMock() {}
  
  public TaskProcessorRoutineMock(String task) {
    task_ = task;
  }
  @Override
  public String getCurrentTask() {
    return task_;
  }

}

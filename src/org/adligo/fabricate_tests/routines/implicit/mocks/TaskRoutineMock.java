package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.AbstractRoutine;

public class TaskRoutineMock extends AbstractRoutine {

  public TaskRoutineMock() {}
  
  public TaskRoutineMock(String task) {
    locationInfo_.setCurrentTask(task);
  }

}

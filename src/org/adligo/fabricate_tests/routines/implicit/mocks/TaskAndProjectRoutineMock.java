package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.AbstractRoutine;

public class TaskAndProjectRoutineMock extends AbstractRoutine {

  public TaskAndProjectRoutineMock(String task, String project) {
    locationInfo_.setCurrentTask(task);
    locationInfo_.setCurrentProject(project);
  }
}

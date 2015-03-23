package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.AbstractRoutine;

public class ProjectRoutineMock extends AbstractRoutine  {

  public ProjectRoutineMock(String project) {
    locationInfo_.setCurrentProject(project);
  }

}

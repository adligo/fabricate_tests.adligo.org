package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.I_ProjectProcessor;
import org.adligo.fabricate.routines.implicit.AbstractRoutine;

public class ProjectProcessorRoutineMock extends AbstractRoutine implements I_ProjectProcessor {
  private String project_;
  
  public ProjectProcessorRoutineMock(String project) {
    project_ = project;
  }

  @Override
  public String getCurrentProject() {
    return project_;
  }
}

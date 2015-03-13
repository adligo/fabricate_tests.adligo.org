package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.DuplicateRoutineException;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=DuplicateRoutineException.class)
public class DuplicateRoutineExceptionTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethodsGetsAndSets() {
    DuplicateRoutineException dre = new DuplicateRoutineException();
    dre.setName("name");
    assertEquals("name", dre.getName());
    
    dre.setOrigin(RoutineBriefOrigin.COMMAND);
    assertSame(RoutineBriefOrigin.COMMAND, dre.getOrigin());
    
    dre.setParentName("name");
    assertEquals("name", dre.getParentName());
    
    dre.setParentOrigin(RoutineBriefOrigin.COMMAND_TASK);
    assertSame(RoutineBriefOrigin.COMMAND_TASK, dre.getParentOrigin());
  }
}

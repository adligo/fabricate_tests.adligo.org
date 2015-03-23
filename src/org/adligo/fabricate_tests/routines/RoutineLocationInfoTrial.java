package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.models.project.I_Project;
import org.adligo.fabricate.routines.I_ProjectProcessor;
import org.adligo.fabricate.routines.RoutineLocationInfo;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@SourceFileScope (sourceClass=RoutineLocationInfo.class)
public class RoutineLocationInfoTrial extends MockitoSourceFileTrial {

  
  @Test
  public void testMethodsGetsAndSets() throws Exception {
    Method getCurrentTask = RoutineLocationInfo.class.getMethod("getCurrentTask");
    assertTrue(Modifier.isSynchronized(getCurrentTask.getModifiers()));
    
    Method getCurrentProject = RoutineLocationInfo.class.getMethod("getCurrentProject");
    assertTrue(Modifier.isSynchronized(getCurrentProject.getModifiers()));
    
    Method getWaitingProject = RoutineLocationInfo.class.getMethod("getWaitingProject");
    assertTrue(Modifier.isSynchronized(getWaitingProject.getModifiers()));
    
    Method setCurrentTask = RoutineLocationInfo.class.getMethod("setCurrentTask", String.class);
    assertTrue(Modifier.isSynchronized(setCurrentTask.getModifiers()));
    
    Method setCurrentProject = RoutineLocationInfo.class.getMethod("setCurrentProject", String.class);
    assertTrue(Modifier.isSynchronized(setCurrentProject.getModifiers()));
    
    Method setWaitingProject = RoutineLocationInfo.class.getMethod("setWaitingProject", I_Project.class);
    assertTrue(Modifier.isSynchronized(setWaitingProject.getModifiers()));
    
    RoutineLocationInfo rli = new RoutineLocationInfo();
    rli.setCurrentProject("currentProject");
    assertEquals("currentProject", rli.getCurrentProject());
    rli.setCurrentProject("currentProject2");
    assertEquals("currentProject2", rli.getCurrentProject());
    
    rli.setCurrentTask("currentTask");
    assertEquals("currentTask", rli.getCurrentTask());
    rli.setCurrentTask("currentTask2");
    assertEquals("currentTask2", rli.getCurrentTask());
    
    I_Project mockProject = mock(I_Project.class);
    rli.setWaitingProject(mockProject);
    assertSame(mockProject, rli.getWaitingProject());
    
    I_Project mockProject2 = mock(I_Project.class);
    rli.setWaitingProject(mockProject2);
    assertSame(mockProject2, rli.getWaitingProject());
  }
}

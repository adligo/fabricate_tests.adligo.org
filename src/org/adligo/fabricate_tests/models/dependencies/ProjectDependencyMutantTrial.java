package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ProjectDependencyMutant.class, minCoverage=80.0)
public class ProjectDependencyMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new ProjectDependencyMutant(null);
      }
    });
  }
  
  @Test
  public void testConstructorCopy() {
    ProjectDependencyMutant pdm = new ProjectDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setProjectName("projectName");
    
    assertEquals("platform", pdm.getPlatform());
    assertEquals("projectName", pdm.getProjectName());
    
    ProjectDependencyMutant pdm2 = new ProjectDependencyMutant(pdm);
    
    assertEquals("platform", pdm2.getPlatform());
    assertEquals("projectName", pdm2.getProjectName());
    
  }
  
}

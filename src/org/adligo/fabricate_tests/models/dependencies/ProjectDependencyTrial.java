package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ProjectDependency.class, minCoverage=80.0)
public class ProjectDependencyTrial extends MockitoSourceFileTrial {

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
    
    ProjectDependency pd = new ProjectDependency(pdm);
    
    assertEquals("platform", pd.getPlatform());
    assertEquals("projectName", pd.getProjectName());
    
  }
  
}

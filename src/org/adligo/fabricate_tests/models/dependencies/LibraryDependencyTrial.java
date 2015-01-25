package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.LibraryDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependencyMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=LibraryDependency.class, minCoverage=80.0)
public class LibraryDependencyTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new LibraryDependency(null);
      }
    });
  }
  
  @Test
  public void testConstructorCopy() {
    LibraryDependencyMutant pdm = new LibraryDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setLibraryName("ln");
    
    assertEquals("platform", pdm.getPlatform());
    assertEquals("ln", pdm.getLibraryName());
    
    LibraryDependency pdm2 = new LibraryDependency(pdm);
    
    assertEquals("platform", pdm2.getPlatform());
    assertEquals("ln", pdm2.getLibraryName());
    
  }
  
}

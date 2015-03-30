package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.project.ProjectBlockKey;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ProjectBlockKey.class,minCoverage=89.0)
public class ProjectBlockKeyTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("project")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ProjectBlockKey(null, "blockingProject");
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("project")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ProjectBlockKey("\t", "blockingProject");
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("blockingProject")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ProjectBlockKey("project", null);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("blockingProject")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ProjectBlockKey("project", "\t");
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndMethodsEqualsHashCode() {
    ProjectBlockKey pbkA = new ProjectBlockKey("projectA", "blockingProjectA");
    ProjectBlockKey pbkB = new ProjectBlockKey("projectB", "blockingProjectA");
    ProjectBlockKey pbkC = new ProjectBlockKey("projectC", "blockingProjectA");
    ProjectBlockKey pbkD = new ProjectBlockKey("projectA", "blockingProjectB");
    
    assertEquals(pbkA.hashCode(), pbkA.hashCode());
    assertNotEquals(pbkA.hashCode(), pbkB.hashCode());
    assertNotEquals(pbkA.hashCode(), pbkC.hashCode());
    assertNotEquals(pbkA.hashCode(), pbkD.hashCode());
    
    assertEquals(pbkA, pbkA);
    assertNotEquals(pbkA, new Object());
    assertNotEquals(pbkA, null);
    assertNotEquals(pbkA, pbkB);
    assertNotEquals(pbkA, pbkC);
    assertNotEquals(pbkA, pbkD);
    
    assertEquals("ProjectBlockKey [project=projectA, blockingProject=blockingProjectA]", pbkA.toString());
    assertEquals("ProjectBlockKey [project=projectB, blockingProject=blockingProjectA]", pbkB.toString());
    assertEquals("ProjectBlockKey [project=projectC, blockingProject=blockingProjectA]", pbkC.toString());
    assertEquals("ProjectBlockKey [project=projectA, blockingProject=blockingProjectB]", pbkD.toString());
  }
  
  
}

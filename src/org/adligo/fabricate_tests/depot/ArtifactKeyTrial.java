package org.adligo.fabricate_tests.depot;

import org.adligo.fabricate.depot.ArtifactKey;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ArtifactKey.class, minCoverage=93.0)
public class ArtifactKeyTrial extends MockitoSourceFileTrial {
	
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("projectName")), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new ArtifactKey(null, "atA", "platA");
      }
    });
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("artifactType")), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new ArtifactKey("pnA", null, "platA");
      }
    });
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("platformName")), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new ArtifactKey("pnA", "atA", null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorEqualsHashCodeAndGets() {
    ArtifactKey aka = new ArtifactKey("pnA", "atA", "platA");
    ArtifactKey akaCopy = new ArtifactKey("pnA", "atA", "platA");
    ArtifactKey aka0 = new ArtifactKey("pnA0", "atA", "platA");
    ArtifactKey aka1 = new ArtifactKey("pnA", "atA1", "platA");
    ArtifactKey aka2 = new ArtifactKey("pnA", "atA", "platA2");
    
    assertEquals(aka.hashCode(), aka.hashCode());
    assertEquals(aka, aka);
    assertEquals("ArtifactKey [projectName=pnA, artifactType=atA, platformName=platA]", aka.toString());
    assertEquals("atA", aka.getArtifactType());
    assertEquals("platA", aka.getPlatformName());
    assertEquals("pnA", aka.getProjectName());
    
    assertEquals(akaCopy.hashCode(), akaCopy.hashCode());
    assertEquals(akaCopy, akaCopy);
    
    assertNotEquals(aka.hashCode(), aka0.hashCode());
    assertNotEquals(aka, aka0);
    assertNotEquals(aka.hashCode(), aka1.hashCode());
    assertNotEquals(aka, aka1);
    assertNotEquals(aka.hashCode(), aka2.hashCode());
    assertNotEquals(aka, aka2);
  }
}

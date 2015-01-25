package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Ide;
import org.adligo.fabricate.models.dependencies.Ide;
import org.adligo.fabricate.models.dependencies.IdeMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=DependencyMutant.class, minCoverage=80.0)
public class DependencyMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new DependencyMutant(null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifact");
    dm.setExtract(true);
    dm.setFileName("fileName");
    dm.setGroup("group");
    dm.setPlatform("platform");
    dm.setType("type");
    dm.setVersion("version");
    
    assertEquals("artifact",dm.getArtifact());
    assertTrue(dm.isExtract());
    assertEquals("fileName",dm.getFileName());
    assertEquals("group",dm.getGroup());
    assertEquals("platform",dm.getPlatform());
    assertEquals("type",dm.getType());
    assertEquals("version",dm.getVersion());
    
    IdeMutant im = new IdeMutant();
    im.setName("sn");
   
    Ide ide = new Ide(im);
    im.setName("dn");
    List<I_Ide> ides = new ArrayList<I_Ide>();
    ides.add(im);
    ides.add(ide);
    
    dm.setChildren(ides);
    assertEquals(2, dm.size());
    
    I_Ide ide0 = dm.get(0);
    assertSame(im, ide0);
    
    I_Ide ide1 = dm.get(1);
    assertNotSame(ide, ide1);
    assertEquals("sn", ide1.getName());
    
    DependencyMutant dmCopy = new DependencyMutant(dm); 
    assertEquals("artifact",dmCopy.getArtifact());
    assertTrue(dmCopy.isExtract());
    assertEquals("fileName",dmCopy.getFileName());
    assertEquals("group",dmCopy.getGroup());
    assertEquals("platform",dmCopy.getPlatform());
    assertEquals("type",dmCopy.getType());
    assertEquals("version",dmCopy.getVersion());
    
    assertEquals(2, dmCopy.size());
    
    ide0 = dmCopy.get(0);
    assertSame(im, ide0);
    
    ide1 = dmCopy.get(1);
    assertNotSame(ide, ide1);
    assertEquals("sn", ide1.getName());
  }
  
}

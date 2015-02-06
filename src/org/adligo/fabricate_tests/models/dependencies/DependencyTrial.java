package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.dependencies.I_Ide;
import org.adligo.fabricate.models.dependencies.Ide;
import org.adligo.fabricate.models.dependencies.IdeMutant;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate_tests.models.common.ParameterMutantTrial;
import org.adligo.fabricate_tests.models.common.ParameterTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=Dependency.class, minCoverage=94.0)
public class DependencyTrial extends MockitoSourceFileTrial {

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
    
    Dependency dmCopy = new Dependency(dm); 
    assertEquals("artifact",dmCopy.getArtifact());
    assertTrue(dmCopy.isExtract());
    assertEquals("fileName",dmCopy.getFileName());
    assertEquals("group",dmCopy.getGroup());
    assertEquals("platform",dmCopy.getPlatform());
    assertEquals("type",dmCopy.getType());
    assertEquals("version",dmCopy.getVersion());
    
    assertEquals(2, dmCopy.size());
    
    ide0 = dmCopy.get(0);
    assertNotSame(im, ide0);
    assertEquals("dn", ide0.getName());
    
    ide1 = dmCopy.get(1);
    assertNotSame(ide, ide1);
    assertEquals("sn", ide1.getName());
    
    
    List<I_Ide> children = dmCopy.getChildren();
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", children.getClass().getName());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyFromXml() {
    DependencyType dm = new DependencyType();
    dm.setArtifact("artifact");
    dm.setFileName("fileName");
    dm.setGroup("group");
    dm.setPlatform("platform");
    dm.setType("type");
    dm.setVersion("version");
    
    IdeType itA = new IdeType();
    itA.setName("ideA");
    
    itA.setArgs(ParameterMutantTrial.createParams());
    dm.getIde().add(itA);
    
    Dependency inst = new Dependency(dm);
    assertEquals("artifact",inst.getArtifact());
    assertFalse(inst.isExtract());
    assertEquals("fileName",inst.getFileName());
    assertEquals("group",inst.getGroup());
    assertEquals("platform",inst.getPlatform());
    assertEquals("type",inst.getType());
    assertEquals("version",inst.getVersion());
    
    List<I_Ide> ides = inst.getChildren();
    I_Ide ide =  ides.get(0);
    assertEquals("ideA", ide.getName());
    
    ParameterTrial.assertParams(ide.getChildren(), this);
    
    dm.setExtract(true);
    inst = new Dependency(dm);
    assertTrue(inst.isExtract());
    
    dm.setExtract(false);
    inst = new Dependency(dm);
    assertFalse(inst.isExtract());
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new Dependency((I_Dependency) null);
      }
    });
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new Dependency((I_Dependency) null);
      }
    });
  }
  
  
 
  @SuppressWarnings("boxing")
  @Test
  public void testMethodCovertAndCreateDepTypeListAndDepType() {
    List<DependencyType> types = DependencyMutantTrial.getDependencies();
    
    List<I_Dependency> result = Dependency.convert(types);
    I_Dependency depA = result.get(0);
    assertEquals("artifactA", depA.getArtifact());
    assertFalse(depA.isExtract());
    assertEquals("fileNameA", depA.getFileName());
    assertEquals("groupA", depA.getGroup());
    assertEquals("platformA", depA.getPlatform());
    assertEquals("typeA", depA.getType());
    assertEquals("versionA", depA.getVersion());
    assertEquals(Dependency.class.getName(), depA.getClass().getName());
    
    I_Dependency depB = result.get(1);
    assertEquals("artifactB", depB.getArtifact());
    assertTrue(depB.isExtract());
    assertEquals("fileNameB", depB.getFileName());
    assertEquals("groupB", depB.getGroup());
    assertEquals("platformB", depB.getPlatform());
    assertEquals("typeB", depB.getType());
    assertEquals("versionB", depB.getVersion());
    assertEquals(Dependency.class.getName(), depB.getClass().getName());
    
    List<I_Ide> ides =  depA.getChildren();
    I_Ide ide1 = ides.get(0);
    assertEquals("ideA", ide1.getName());
    assertEquals(Ide.class.getName(), ide1.getClass().getName());
    
    I_Ide ide2 = ides.get(1);
    assertEquals("ideB", ide2.getName());
    assertEquals(Ide.class.getName(), ide2.getClass().getName());
    assertEquals(0, ide2.size());
    
    List<I_Parameter> out = ide1.getChildren();
    ParameterTrial.assertParams(out, this);
    
    out = ParameterMutant.convert((ParamsType) null);
    assertNotNull(out);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCode() {
    Dependency dm = new Dependency(new DependencyMutant());
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifact");
    Dependency depA = new Dependency(dmA);
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifact");
    dmB.setFileName("fileName");
    Dependency depB = new Dependency(dmB);
    
    DependencyMutant dmC = new DependencyMutant();
    dmC.setArtifact("artifact");
    dmC.setFileName("fileName");
    dmC.setGroup("group");
    Dependency depC = new Dependency(dmC);
    
    DependencyMutant dmD = new DependencyMutant();
    dmD.setArtifact("artifact");
    dmD.setFileName("fileName");
    dmD.setGroup("group");
    dmD.setType("type");
    Dependency depD = new Dependency(dmD);
    
    DependencyMutant dmE = new DependencyMutant();
    dmE.setArtifact("artifact");
    dmE.setFileName("fileName");
    dmE.setGroup("group");
    dmE.setType("type");
    dmE.setVersion("version");
    Dependency depE = new Dependency(dmE);
    
    DependencyMutant dmF = new DependencyMutant();
    dmF.setArtifact("artifactA");
    dmF.setFileName("fileName");
    dmF.setGroup("group");
    dmF.setType("type");
    dmF.setVersion("version");
    Dependency depF = new Dependency(dmF);
    
    DependencyMutant dmG = new DependencyMutant();
    dmG.setArtifact("artifactA");
    dmG.setFileName("fileNameA");
    dmG.setGroup("group");
    dmG.setType("type");
    dmG.setVersion("version");
    Dependency depG = new Dependency(dmG);
    
    DependencyMutant dmH = new DependencyMutant();
    dmH.setArtifact("artifactA");
    dmH.setFileName("fileNameA");
    dmH.setGroup("groupA");
    dmH.setType("type");
    dmH.setVersion("version");
    Dependency depH = new Dependency(dmH);
    
    DependencyMutant dmI = new DependencyMutant();
    dmI.setArtifact("artifactA");
    dmI.setFileName("fileNameA");
    dmI.setGroup("groupA");
    dmI.setType("typeA");
    dmI.setVersion("version");
    Dependency depI = new Dependency(dmI);
    
    DependencyMutant dmJ = new DependencyMutant();
    dmJ.setArtifact("artifactA");
    dmJ.setFileName("fileNameA");
    dmJ.setGroup("groupA");
    dmJ.setType("typeA");
    dmJ.setVersion("versionA");
    Dependency depJ = new Dependency(dmJ);
    
    assertEquals(dm, dm);
    assertEquals(dm.hashCode(), dm.hashCode());
    
    assertNotEquals(dm, depA);
    assertNotEquals(dm.hashCode(), depA.hashCode());
    
    assertNotEquals(dm, depA);
    assertNotEquals(dm.hashCode(), depA.hashCode());
    
    assertNotEquals(dm, depB);
    assertNotEquals(dm.hashCode(), depB.hashCode());
    
    assertNotEquals(dm, depC);
    assertNotEquals(dm.hashCode(), depC.hashCode());
    
    assertNotEquals(dm, depD);
    assertNotEquals(dm.hashCode(), depD.hashCode());
    
    assertNotEquals(dm, depE);
    assertNotEquals(dm.hashCode(), depE.hashCode());
    
    //done with null compares
    assertEquals(depE, new DependencyMutant(depE));
    assertEquals(depE.hashCode(), new DependencyMutant(depE).hashCode());
    assertEquals(depE, new Dependency(depE));
    assertEquals(depE.hashCode(), new Dependency(depE).hashCode());
    
    assertNotEquals(depE, depF);
    assertNotEquals(depE.hashCode(), depF.hashCode());
    
    assertNotEquals(depE, depG);
    assertNotEquals(depE.hashCode(), depG.hashCode());
    
    assertNotEquals(depE, depH);
    assertNotEquals(depE.hashCode(), depH.hashCode());
    
    assertNotEquals(depE, depI);
    assertNotEquals(depE.hashCode(), depI.hashCode());
    
    assertNotEquals(depE, depJ);
    assertNotEquals(depE.hashCode(), depJ.hashCode());
  }
}

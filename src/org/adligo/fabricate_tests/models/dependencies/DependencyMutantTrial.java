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
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=DependencyMutant.class, minCoverage=72.0)
public class DependencyMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new DependencyMutant((I_Dependency) null);
      }
    });
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new DependencyMutant((DependencyType) null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyFromInterface() {
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
    
    DependencyMutant inst = new DependencyMutant(dm);
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
    
    ParameterMutantTrial.assertConvertedParams(ide.getChildren(), this);
    
    dm.setExtract(true);
    inst = new DependencyMutant(dm);
    assertTrue(inst.isExtract());
    
    dm.setExtract(false);
    inst = new DependencyMutant(dm);
    assertFalse(inst.isExtract());
  }
  
  @Test
  public void testMethodCovertAndCreateDepTypeListAndDepType() {
    List<DependencyType> types = getDependencies();
    
    List<I_Dependency> result = DependencyMutant.convert(types);
    List<I_Parameter> out;
    assertDependencyConversion(this, result);
    
    
    out = ParameterMutant.convert((ParamsType) null);
    assertNotNull(out);
  }

  @SuppressWarnings("boxing")
  public static void assertDependencyConversion(I_Asserts asserts, List<I_Dependency> result) {
    I_Dependency depA = result.get(0);
    asserts.assertEquals("artifactA", depA.getArtifact());
    asserts.assertFalse(depA.isExtract());
    asserts.assertEquals("fileNameA", depA.getFileName());
    asserts.assertEquals("groupA", depA.getGroup());
    asserts.assertEquals("platformA", depA.getPlatform());
    asserts.assertEquals("typeA", depA.getType());
    asserts.assertEquals("versionA", depA.getVersion());
    asserts.assertEquals(DependencyMutant.class.getName(), depA.getClass().getName());
    
    I_Dependency depB = result.get(1);
    asserts.assertEquals("artifactB", depB.getArtifact());
    asserts.assertTrue(depB.isExtract());
    asserts.assertEquals("fileNameB", depB.getFileName());
    asserts.assertEquals("groupB", depB.getGroup());
    asserts.assertEquals("platformB", depB.getPlatform());
    asserts.assertEquals("typeB", depB.getType());
    asserts.assertEquals("versionB", depB.getVersion());
    asserts.assertEquals(DependencyMutant.class.getName(), depB.getClass().getName());
    
    List<I_Ide> ides =  depA.getChildren();
    I_Ide ide1 = ides.get(0);
    asserts.assertEquals("ideA", ide1.getName());
    asserts.assertEquals(IdeMutant.class.getName(), ide1.getClass().getName());
    
    I_Ide ide2 = ides.get(1);
    asserts.assertEquals("ideB", ide2.getName());
    asserts.assertEquals(IdeMutant.class.getName(), ide2.getClass().getName());
    asserts.assertEquals(0, ide2.size());
    
    List<I_Parameter> out = ide1.getChildren();
    ParameterMutantTrial.assertConvertedParams(out, asserts);
  }

  public static void assertDependencyConversionC(I_Asserts asserts, List<I_Dependency> result) {
    I_Dependency depA = result.get(0);
    asserts.assertEquals("artifactC", depA.getArtifact());
    asserts.assertFalse(depA.isExtract());
    asserts.assertEquals("fileNameC", depA.getFileName());
    asserts.assertEquals("groupC", depA.getGroup());
    asserts.assertEquals("platformC", depA.getPlatform());
    asserts.assertEquals("typeC", depA.getType());
    asserts.assertEquals("versionC", depA.getVersion());
    asserts.assertEquals(DependencyMutant.class.getName(), depA.getClass().getName());

  }
  
  public static void assertDependencyConversionD(I_Asserts asserts, List<I_Dependency> result) {
    I_Dependency depA = result.get(0);
    asserts.assertEquals("artifactD", depA.getArtifact());
    asserts.assertFalse(depA.isExtract());
    asserts.assertEquals("fileNameD", depA.getFileName());
    asserts.assertEquals("groupD", depA.getGroup());
    asserts.assertEquals("platformD", depA.getPlatform());
    asserts.assertEquals("typeD", depA.getType());
    asserts.assertEquals("versionD", depA.getVersion());
    asserts.assertEquals(DependencyMutant.class.getName(), depA.getClass().getName());

  }
  
  @SuppressWarnings("boxing")
  public static List<DependencyType> getDependencies() {
    DependencyType type = new DependencyType();
    type.setArtifact("artifactA");
    type.setExtract(false);
    type.setFileName("fileNameA");
    type.setGroup("groupA");
    type.setPlatform("platformA");
    type.setType("typeA");
    type.setVersion("versionA");
    
    DependencyType typeB = new DependencyType();
    typeB.setArtifact("artifactB");
    typeB.setExtract(true);
    typeB.setFileName("fileNameB");
    typeB.setGroup("groupB");
    typeB.setPlatform("platformB");
    typeB.setType("typeB");
    typeB.setVersion("versionB");
    
    IdeType itA = new IdeType();
    itA.setName("ideA");
    
    
    itA.setArgs(ParameterMutantTrial.createParams());
    
    IdeType itB = new IdeType();
    itB.setName("ideB");
    
    List<IdeType> its = new ArrayList<IdeType>();
    its.add(itA);
    its.add(itB);
    type.getIde().add(itA);
    type.getIde().add(itB);
    
    List<DependencyType> types = new ArrayList<DependencyType>();
    types.add(type);
    types.add(typeB);
    return types;
  }
  
  public static List<DependencyType> getDependenciesC() {
    DependencyType type = new DependencyType();
    type.setArtifact("artifactC");
    type.setFileName("fileNameC");
    type.setGroup("groupC");
    type.setPlatform("platformC");
    type.setType("typeC");
    type.setVersion("versionC");
    
    List<DependencyType> types = new ArrayList<DependencyType>();
    types.add(type);
    return types;
  }

  public static List<DependencyType> getDependenciesD() {
    DependencyType type = new DependencyType();
    type.setArtifact("artifactD");
    type.setFileName("fileNameD");
    type.setGroup("groupD");
    type.setPlatform("platformD");
    type.setType("typeD");
    type.setVersion("versionD");
    
    List<DependencyType> types = new ArrayList<DependencyType>();
    types.add(type);
    return types;
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCode() {
    DependencyMutant dm = new DependencyMutant();
    
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifact");
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifact");
    dmB.setFileName("fileName");
    
    DependencyMutant dmC = new DependencyMutant();
    dmC.setArtifact("artifact");
    dmC.setFileName("fileName");
    dmC.setGroup("group");
    
    DependencyMutant dmD = new DependencyMutant();
    dmD.setArtifact("artifact");
    dmD.setFileName("fileName");
    dmD.setGroup("group");
    dmD.setType("type");
    
    DependencyMutant dmE = new DependencyMutant();
    dmE.setArtifact("artifact");
    dmE.setFileName("fileName");
    dmE.setGroup("group");
    dmE.setType("type");
    dmE.setVersion("version");
    
    DependencyMutant dmF = new DependencyMutant();
    dmF.setArtifact("artifactA");
    dmF.setFileName("fileName");
    dmF.setGroup("group");
    dmF.setType("type");
    dmF.setVersion("version");
    
    DependencyMutant dmG = new DependencyMutant();
    dmG.setArtifact("artifactA");
    dmG.setFileName("fileNameA");
    dmG.setGroup("group");
    dmG.setType("type");
    dmG.setVersion("version");
    
    DependencyMutant dmH = new DependencyMutant();
    dmH.setArtifact("artifactA");
    dmH.setFileName("fileNameA");
    dmH.setGroup("groupA");
    dmH.setType("type");
    dmH.setVersion("version");
    
    DependencyMutant dmI = new DependencyMutant();
    dmI.setArtifact("artifactA");
    dmI.setFileName("fileNameA");
    dmI.setGroup("groupA");
    dmI.setType("typeA");
    dmI.setVersion("version");
    
    DependencyMutant dmJ = new DependencyMutant();
    dmJ.setArtifact("artifactA");
    dmJ.setFileName("fileNameA");
    dmJ.setGroup("groupA");
    dmJ.setType("typeA");
    dmJ.setVersion("versionA");
    
    assertEquals(dm, dm);
    assertEquals(dm.hashCode(), dm.hashCode());
    
    assertNotEquals(dm, dmA);
    assertNotEquals(dm.hashCode(), dmA.hashCode());
    
    assertNotEquals(dm, dmA);
    assertNotEquals(dm.hashCode(), dmA.hashCode());
    
    assertNotEquals(dm, dmB);
    assertNotEquals(dm.hashCode(), dmB.hashCode());
    
    assertNotEquals(dm, dmC);
    assertNotEquals(dm.hashCode(), dmC.hashCode());
    
    assertNotEquals(dm, dmD);
    assertNotEquals(dm.hashCode(), dmD.hashCode());
    
    assertNotEquals(dm, dmE);
    assertNotEquals(dm.hashCode(), dmE.hashCode());
    
    //done with null compares
    assertEquals(dmE, new DependencyMutant(dmE));
    assertEquals(dmE.hashCode(), new DependencyMutant(dmE).hashCode());
    assertEquals(dmE, new Dependency(dmE));
    assertEquals(dmE.hashCode(), new Dependency(dmE).hashCode());
    
    assertNotEquals(dmE, dmF);
    assertNotEquals(dmE.hashCode(), dmF.hashCode());
    
    assertNotEquals(dmE, dmG);
    assertNotEquals(dmE.hashCode(), dmG.hashCode());
    
    assertNotEquals(dmE, dmH);
    assertNotEquals(dmE.hashCode(), dmH.hashCode());
    
    assertNotEquals(dmE, dmI);
    assertNotEquals(dmE.hashCode(), dmI.hashCode());
    
    assertNotEquals(dmE, dmJ);
    assertNotEquals(dmE.hashCode(), dmJ.hashCode());
  }
}

package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.DependencyVersionMismatchException;
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
import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=Dependency.class, minCoverage=92.0)
public class DependencyTrial extends MockitoSourceFileTrial {

  public static void assertDependencyConversion(I_Asserts asserts, List<I_Dependency> result, 
      String project) {
    assertDependencyConversion(asserts, result, project, null);
  }
  @SuppressWarnings("boxing")
  public static void assertDependencyConversion(I_Asserts asserts, List<I_Dependency> result, 
      String project, String platform) {
    I_Dependency depA = result.get(0);
    asserts.assertEquals("artifactA", depA.getArtifact());
    asserts.assertFalse(depA.isExtract());
    asserts.assertEquals("fileName_vA", depA.getFileName());
    asserts.assertEquals("groupA", depA.getGroup());
    if (platform == null) {
      asserts.assertEquals("platformA", depA.getPlatform());
    } else {
      asserts.assertEquals(platform, depA.getPlatform());
    }
    if (project == null) {
      asserts.assertNull(depA.getProject());
    } else {
      asserts.assertEquals(project, depA.getProject());
    }
    asserts.assertEquals("typeA", depA.getType());
    asserts.assertEquals("vA", depA.getVersion());
    asserts.assertEquals(Dependency.class.getName(), depA.getClass().getName());
    
    I_Dependency depB = result.get(1);
    asserts.assertEquals("artifactB", depB.getArtifact());
    asserts.assertTrue(depB.isExtract());
    asserts.assertEquals("fileName_vB", depB.getFileName());
    asserts.assertEquals("groupB", depB.getGroup());
    if (platform == null) {
      asserts.assertEquals("platformB", depB.getPlatform());
    } else {
      asserts.assertEquals(platform, depA.getPlatform());
    }
    if (project == null) {
      asserts.assertNull(depB.getProject());
    } else {
      asserts.assertEquals(project, depB.getProject());
    }
    asserts.assertEquals("typeB", depB.getType());
    asserts.assertEquals("vB", depB.getVersion());
    asserts.assertEquals(Dependency.class.getName(), depB.getClass().getName());
    
    List<I_Ide> ides =  depA.getChildren();
    I_Ide ide1 = ides.get(0);
    asserts.assertEquals("ideA", ide1.getName());
    asserts.assertEquals(Ide.class.getName(), ide1.getClass().getName());
    
    I_Ide ide2 = ides.get(1);
    asserts.assertEquals("ideB", ide2.getName());
    asserts.assertEquals(Ide.class.getName(), ide2.getClass().getName());
    asserts.assertEquals(0, ide2.size());
    
    List<I_Parameter> out = ide1.getChildren();
    ParameterTrial.assertConvertedParams(out, asserts);
  }
  
  public static void assertDependencyConversionC(I_Asserts asserts, List<I_Dependency> result, String project) {
    assertDependencyConversionC(asserts, result, project, null);
  }
  public static void assertDependencyConversionC(I_Asserts asserts, List<I_Dependency> result, String project,
      String platform) {
    I_Dependency dep = result.get(0);
    asserts.assertEquals("artifactC", dep.getArtifact());
    asserts.assertFalse(dep.isExtract());
    asserts.assertEquals("fileName_vC", dep.getFileName());
    asserts.assertEquals("groupC", dep.getGroup());
    if (platform == null) {
      asserts.assertEquals("platformC", dep.getPlatform());
    } else {
      asserts.assertEquals(platform, dep.getPlatform());
    }
    if (project == null) {
      asserts.assertNull(dep.getProject());
    } else {
      asserts.assertEquals(project, dep.getProject());
    }
    asserts.assertEquals("typeC", dep.getType());
    asserts.assertEquals("vC", dep.getVersion());
    asserts.assertEquals(Dependency.class.getName(), dep.getClass().getName());
  }
  
  public static void assertDependencyConversionD(I_Asserts asserts, List<I_Dependency> result, 
      String project) {
    assertDependencyConversionD(asserts, result, project, null);
  }
  public static void assertDependencyConversionD(I_Asserts asserts, List<I_Dependency> result, 
      String project, String platform) {
    I_Dependency dep = result.get(0);
    asserts.assertEquals("artifactD", dep.getArtifact());
    asserts.assertFalse(dep.isExtract());
    asserts.assertEquals("fileName_vD", dep.getFileName());
    asserts.assertEquals("groupD", dep.getGroup());
    if (platform == null) {
      asserts.assertEquals("platformD", dep.getPlatform());
    } else {
      asserts.assertEquals(project, dep.getProject());
    }
    if (project == null) {
      asserts.assertNull(dep.getProject());
    } else {
      asserts.assertEquals(project, dep.getProject());
    }
    asserts.assertEquals("typeD", dep.getType());
    asserts.assertEquals("vD", dep.getVersion());
    asserts.assertEquals(Dependency.class.getName(), dep.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() throws Exception {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifact");
    dm.setExtract(true);
    dm.setFileName("fileName_v9");
    dm.setGroup("group");
    dm.setPlatform("platform");
    dm.setType("type");
    dm.setVersion("v9");
    
    assertEquals("artifact",dm.getArtifact());
    assertTrue(dm.isExtract());
    assertEquals("fileName_v9",dm.getFileName());
    assertEquals("group",dm.getGroup());
    assertEquals("platform",dm.getPlatform());
    assertEquals("type",dm.getType());
    assertEquals("v9",dm.getVersion());
    
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
    assertEquals("fileName_v9",dmCopy.getFileName());
    assertEquals("group",dmCopy.getGroup());
    assertEquals("platform",dmCopy.getPlatform());
    assertEquals("type",dmCopy.getType());
    assertEquals("v9",dmCopy.getVersion());
    
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
  public void testConstructorDependencyMismatchException() throws Exception  {
    
    
    DependencyType dm = new DependencyType();
    dm.setArtifact("artifact");
    dm.setFileName("fileName_v1_0");
    dm.setGroup("group");
    dm.setPlatform("platform");
    dm.setType("type");
    dm.setVersion("ver");
    
    IdeType itA = new IdeType();
    itA.setName("ideA");
    
    itA.setArgs(ParameterMutantTrial.createParams());
    dm.getIde().add(itA);
    DependencyVersionMismatchException caught = null;
    try {
      new Dependency(dm,"projectA");
    } catch (DependencyVersionMismatchException x) {
      caught = x;
    }
    assertNotNull(caught);
    I_Dependency dep = caught.getDependency();
    assertEquals("artifact", dep.getArtifact());
    assertEquals("fileName_v1_0", dep.getFileName());
    assertEquals("group", dep.getGroup());
    assertEquals("platform", dep.getPlatform());
    assertEquals("type", dep.getType());
    assertEquals("ver", dep.getVersion());
    
    DependencyMutant dm2 = new DependencyMutant();
    dm2.setArtifact("2artifact");
    dm2.setFileName("2fileName_v1_0");
    dm2.setGroup("2group");
    dm2.setPlatform("2platform");
    dm2.setType("2type");
    dm2.setVersion("2ver");
    
    caught = null;
    try {
      new Dependency(dm2);
    } catch (DependencyVersionMismatchException x) {
      caught = x;
    }
    assertNotNull(caught);
    dep = caught.getDependency();
    assertEquals("2artifact", dep.getArtifact());
    assertEquals("2fileName_v1_0", dep.getFileName());
    assertEquals("2group", dep.getGroup());
    assertEquals("2platform", dep.getPlatform());
    assertEquals("2type", dep.getType());
    assertEquals("2ver", dep.getVersion());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyFromXml() throws Exception  {
    DependencyType dm = new DependencyType();
    dm.setArtifact("artifact");
    dm.setFileName("fileName_v1_0");
    dm.setGroup("group");
    dm.setPlatform("platform");
    dm.setType("type");
    dm.setVersion("v1_0");
    
    IdeType itA = new IdeType();
    itA.setName("ideA");
    
    itA.setArgs(ParameterMutantTrial.createParams());
    dm.getIde().add(itA);
    
    Dependency inst = new Dependency(dm,"projectA");
    assertEquals("artifact",inst.getArtifact());
    assertFalse(inst.isExtract());
    assertEquals("fileName_v1_0",inst.getFileName());
    assertEquals("group",inst.getGroup());
    assertEquals("platform",inst.getPlatform());
    assertEquals("projectA", inst.getProject());
    assertEquals("type",inst.getType());
    assertEquals("v1_0",inst.getVersion());
    
    List<I_Ide> ides = inst.getChildren();
    I_Ide ide =  ides.get(0);
    assertEquals("ideA", ide.getName());
    
    ParameterTrial.assertConvertedParams(ide.getChildren(), this);
    
    dm.setExtract(true);
    inst = new Dependency(dm, null);
    assertTrue(inst.isExtract());
    assertNull(inst.getProject());
    
    dm.setExtract(false);
    inst = new Dependency(dm, null);
    assertFalse(inst.isExtract());
    assertNull(inst.getProject());
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
  public void testMethodCovertAndCreateDepTypeListAndDepType() throws Exception  {
    List<DependencyType> types = DependencyMutantTrial.getDependencies();
    
    List<I_Dependency> result = Dependency.convert(types, "projectAbc");
    I_Dependency depA = result.get(0);
    assertEquals("artifactA", depA.getArtifact());
    assertFalse(depA.isExtract());
    assertEquals("fileName_vA", depA.getFileName());
    assertEquals("groupA", depA.getGroup());
    assertEquals("platformA", depA.getPlatform());
    assertEquals("projectAbc", depA.getProject());
    assertEquals("typeA", depA.getType());
    assertEquals("vA", depA.getVersion());
    assertEquals(Dependency.class.getName(), depA.getClass().getName());
    
    I_Dependency depB = result.get(1);
    assertEquals("artifactB", depB.getArtifact());
    assertTrue(depB.isExtract());
    assertEquals("fileName_vB", depB.getFileName());
    assertEquals("groupB", depB.getGroup());
    assertEquals("platformB", depB.getPlatform());
    assertEquals("projectAbc", depB.getProject());
    assertEquals("typeB", depB.getType());
    assertEquals("vB", depB.getVersion());
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
    ParameterTrial.assertConvertedParams(out, this);
    
    out = ParameterMutant.convert((ParamsType) null);
    assertNotNull(out);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() throws Exception  {
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
    dmE.setFileName("fileName_v1E");
    dmE.setGroup("group");
    dmE.setType("type");
    dmE.setVersion("v1E");
    Dependency depE = new Dependency(dmE);
    
    DependencyMutant dmF = new DependencyMutant();
    dmF.setArtifact("artifactA");
    dmF.setFileName("fileName_v1F");
    dmF.setGroup("group");
    dmF.setType("type");
    dmF.setVersion("v1F");
    Dependency depF = new Dependency(dmF);
    
    DependencyMutant dmG = new DependencyMutant();
    dmG.setArtifact("artifactA");
    dmG.setFileName("fileName_v1G");
    dmG.setGroup("group");
    dmG.setType("type");
    dmG.setVersion("v1G");
    Dependency depG = new Dependency(dmG);
    
    DependencyMutant dmH = new DependencyMutant();
    dmH.setArtifact("artifactA");
    dmH.setFileName("fileName_v1H");
    dmH.setGroup("groupA");
    dmH.setType("type");
    dmH.setVersion("v1H");
    Dependency depH = new Dependency(dmH);
    
    DependencyMutant dmI = new DependencyMutant();
    dmI.setArtifact("artifactA");
    dmI.setFileName("fileName_v1I");
    dmI.setGroup("groupA");
    dmI.setType("typeA");
    dmI.setVersion("v1I");
    Dependency depI = new Dependency(dmI);
    
    DependencyMutant dmJ = new DependencyMutant();
    dmJ.setArtifact("artifactA");
    dmJ.setFileName("fileName_v1J");
    dmJ.setGroup("groupA");
    dmJ.setType("typeA");
    dmJ.setVersion("v1J");
    Dependency depJ = new Dependency(dmJ);
    
    
    assertEquals(dm, dm);
    assertEquals(dm.hashCode(), dm.hashCode());
    assertEquals("Dependency [artifact=null, extract=false," + System.lineSeparator() +
        "\tfileName=null, group=null," + System.lineSeparator() +
        "\tplatform=null, type=jar,"  + System.lineSeparator() +
        "\tversion=null, ideChidren=0]", dm.toString());
    
    assertNotEquals(dm, depA);
    assertNotEquals(dm.hashCode(), depA.hashCode());
    assertEquals("Dependency [artifact=artifact, extract=false," + System.lineSeparator() +
        "\tfileName=null, group=null," + System.lineSeparator() +
        "\tplatform=null, type=jar,"  + System.lineSeparator() +
        "\tversion=null, ideChidren=0]", depA.toString());
    
    assertNotEquals(dm, depA);
    
    assertNotEquals(dm, depB);
    assertNotEquals(dm.hashCode(), depB.hashCode());
    assertEquals("Dependency [artifact=artifact, extract=false," + System.lineSeparator() +
        "\tfileName=fileName, group=null," + System.lineSeparator() +
        "\tplatform=null, type=jar,"  + System.lineSeparator() +
        "\tversion=null, ideChidren=0]", depB.toString());
    
    assertNotEquals(dm, depC);
    assertNotEquals(dm.hashCode(), depC.hashCode());
    assertEquals("Dependency [artifact=artifact, extract=false," + System.lineSeparator() +
        "\tfileName=fileName, group=group," + System.lineSeparator() +
        "\tplatform=null, type=jar,"  + System.lineSeparator() +
        "\tversion=null, ideChidren=0]", depC.toString());
    
    assertNotEquals(dm, depD);
    assertNotEquals(dm.hashCode(), depD.hashCode());
    assertEquals("Dependency [artifact=artifact, extract=false," + System.lineSeparator() +
        "\tfileName=fileName, group=group," + System.lineSeparator() +
        "\tplatform=null, type=type,"  + System.lineSeparator() +
        "\tversion=null, ideChidren=0]", depD.toString());
    
    assertNotEquals(dm, depE);
    assertNotEquals(dm.hashCode(), depE.hashCode());
    assertEquals("Dependency [artifact=artifact, extract=false," + System.lineSeparator() +
        "\tfileName=fileName_v1E, group=group," + System.lineSeparator() +
        "\tplatform=null, type=type,"  + System.lineSeparator() +
        "\tversion=v1E, ideChidren=0]", depE.toString());
    
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

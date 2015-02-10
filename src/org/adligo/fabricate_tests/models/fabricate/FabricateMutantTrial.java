package org.adligo.fabricate_tests.models.fabricate;

import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.fabricate.I_Fabricate;
import org.adligo.fabricate.models.fabricate.I_FabricateXmlDiscovery;
import org.adligo.fabricate.models.fabricate.I_JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettingsMutant;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=FabricateMutant.class, minCoverage=95.0)
public class FabricateMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDefaults() {
    FabricateMutant fm = new FabricateMutant();
    assertNull(fm.getFabricateHome());
    assertNull(fm.getFabricateRepository());
    assertNull(fm.getJavaHome());
    assertNull(fm.getJavaHome());
    assertNull(fm.getJavaSettings());
    assertNull(fm.getJavaSettings());
    assertEquals(FabricateDefaults.JAVA_THREADS, fm.getThreads());
    assertEquals(FabricateDefaults.JAVA_XMS_DEFAULT, fm.getXms());
    assertEquals(FabricateDefaults.JAVA_XMX_DEFAULT, fm.getXmx());
    List<I_Dependency> deps = fm.getDependencies();
    assertEquals(0, deps.size());
  }
  
  @SuppressWarnings({"unused"})
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new FabricateMutant(null, null);
          }
        }); 
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new FabricateMutant((I_Fabricate) null);
          }
        }); 
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopiesFromInterface() {
    FabricateMutant fm = new FabricateMutant();
    fm.setJavaHome("jh");
    fm.setFabricateHome("fh");
    fm.setFabricateRepository("fr");
    
    fm.setFabricateXmlRunDir("fabXmlDir");
    
    FabricateMutant copy = new FabricateMutant(fm);
    assertEquals("jh", copy.getJavaHome());
    assertEquals("fh", copy.getFabricateHome());
    assertEquals("fr", copy.getFabricateRepository());
    
    JavaSettingsMutant jsm = new JavaSettingsMutant();
    jsm.setThreads(1);
    jsm.setXms("120m");
    jsm.setXmx("130m");
    fm.setJavaSettings(jsm);
    
    copy = new FabricateMutant(fm);
    assertEquals("fabXmlDir", copy.getFabricateXmlRunDir());
    assertNull(copy.getFabricateProjectRunDir());
    assertNull(copy.getFabricateDevXmlDir());
    assertEquals("jh", copy.getJavaHome());
    assertEquals("fh", copy.getFabricateHome());
    assertEquals("fr", copy.getFabricateRepository());
    assertEquals(1, copy.getThreads());
    I_JavaSettings js = copy.getJavaSettings();
    assertEquals(JavaSettingsMutant.class.getName(), js.getClass().getName());
    assertEquals("120m", js.getXms());
    assertEquals("130m", js.getXmx());
    
    fm.addRemoteRepository("repoC");
    List<String> repoC = fm.getRemoteRepositories();
    assertEquals("repoC", repoC.get(0));
    assertEquals(1, repoC.size());
    
    List<String> remoteRepos = new ArrayList<String>();
    remoteRepos.add("repoA");
    remoteRepos.add("repoB");
    remoteRepos.add(null);
    fm.setRemoteRepositories(remoteRepos);
    List<String> repos = fm.getRemoteRepositories();
    assertEquals("repoA", repos.get(0));
    assertEquals("repoB", repos.get(1));
    assertEquals(2, repos.size());
    
    FabricateMutant mock = mock(FabricateMutant.class);
    copy = new FabricateMutant(mock);
    List<I_Dependency> deps = copy.getDependencies();
    assertEquals(0, deps.size());
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifactA");
    dmA.setExtract(false);
    dmA.setFileName("fileNameA");
    dmA.setGroup("groupA");
    dmA.setPlatform("platformA");
    dmA.setType("typeA");
    dmA.setVersion("versionA");
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifactB");
    dmB.setExtract(true);
    dmB.setFileName("fileNameB");
    dmB.setGroup("groupB");
    dmB.setPlatform("platformB");
    dmB.setType("typeB");
    dmB.setVersion("versionB");  
    
    List<I_Dependency> dms = new ArrayList<I_Dependency>();
    dms.add(dmA);
    dms.add(new Dependency(dmB));
    dms.add(null);
    
    fm.setDependencies(dms);
    fm.setFabricateProjectRunDir("projectRunDir");
    fm.setFabricateDevXmlDir("devXmlDir");
    copy = new FabricateMutant(fm);
    assertEquals("fabXmlDir", copy.getFabricateXmlRunDir());
    assertEquals("projectRunDir", copy.getFabricateProjectRunDir());
    assertEquals("devXmlDir", copy.getFabricateDevXmlDir());
    
    assertSame(dmA, copy.getDependencies().get(0));
    I_Dependency dtCopy = copy.getDependencies().get(1);
    assertEquals(DependencyMutant.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactB",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameB" ,dtCopy.getFileName());
    assertEquals("groupB" ,dtCopy.getGroup());
    assertEquals("platformB" ,dtCopy.getPlatform());
    assertEquals("typeB" ,dtCopy.getType());
    assertEquals("versionB" ,dtCopy.getVersion()); 
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopiesFromXml() {
    FabricateType ft = new FabricateType();
    JavaType jt = new JavaType();
    jt.setThreads(3);
    jt.setXms("12m");
    jt.setXmx("13m");
    ft.setJava(jt);
    
    I_FabricateXmlDiscovery fxml = mock(I_FabricateXmlDiscovery.class);
    
    
    FabricateMutant fm = new FabricateMutant(ft, fxml);
    assertEquals(3, fm.getThreads());
    I_JavaSettings js = fm.getJavaSettings();
    assertEquals(3, js.getThreads());
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    assertNull(fm.getFabricateDevXmlDir());
    assertNull(fm.getFabricateProjectRunDir());
    assertNull(fm.getFabricateXmlRunDir());
    
    FabricateDependencies fdeps = new FabricateDependencies();
    fdeps.getRemoteRepository().add("repoA");
    fdeps.getRemoteRepository().add("repoB");
    ft.setDependencies(fdeps);
    when(fxml.getDevXmlDir()).thenReturn("devx");
    when(fxml.getProjectXmlDir()).thenReturn("projx");
    when(fxml.getFabricateXmlDir()).thenReturn("fabx");
    
    fm = new FabricateMutant(ft, fxml);
    assertEquals("devx", fm.getFabricateDevXmlDir());
    assertEquals("projx", fm.getFabricateProjectRunDir());
    assertEquals("fabx", fm.getFabricateXmlRunDir());
    
    List<String> repos = fm.getRemoteRepositories();
    assertEquals("repoA", repos.get(0));
    assertEquals("repoB", repos.get(1));
    assertEquals(2, repos.size());
    
    List<DependencyType> dts = DependencyMutantTrial.getDependencies();
    
    fdeps.getDependency().addAll(dts);
    
    
    fm = new FabricateMutant(ft, fxml);
    assertEquals(3, fm.getThreads());
    js = fm.getJavaSettings();
    assertEquals(3, js.getThreads());
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    
    DependencyMutantTrial.assertDependencyConversion(this, fm.getDependencies());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethods() {
    FabricateMutant fm = new FabricateMutant();
    fm.setJavaHome("jh");
    assertEquals("jh", fm.getJavaHome());
    
    fm.setFabricateHome("fh");
    assertEquals("fh", fm.getFabricateHome());
    
    fm.setFabricateRepository("fr");
    assertEquals("fr", fm.getFabricateRepository());
    
    JavaSettingsMutant jsm = new JavaSettingsMutant();
    jsm.setThreads(1);
    jsm.setXms("12m");
    jsm.setXmx("13m");
    fm.setJavaSettings(jsm);
    assertSame(jsm, fm.getJavaSettings());
    
    fm.setJavaSettings(new JavaSettings(jsm));
    I_JavaSettings copy = fm.getJavaSettings();
    assertNotSame(jsm, copy);
    assertEquals(1, copy.getThreads());
    assertEquals("12m", copy.getXms());
    assertEquals("13m", copy.getXmx());
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifactA");
    dmA.setExtract(false);
    dmA.setFileName("fileNameA");
    dmA.setGroup("groupA");
    dmA.setPlatform("platformA");
    dmA.setType("typeA");
    dmA.setVersion("versionA");
    
    fm.addDependency(dmA);
    assertSame(dmA, fm.getDependencies().get(0));
    
    DependencyType dtC = new DependencyType();
    dtC.setArtifact("artifactC");
    dtC.setExtract(true);
    dtC.setFileName("fileNameC");
    dtC.setGroup("groupC");
    dtC.setPlatform("platformC");
    dtC.setType("typeC");
    dtC.setVersion("versionC");  
    
    fm.addDependency(dtC);
    I_Dependency dtCopy = fm.getDependencies().get(1);
    assertEquals(DependencyMutant.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactC",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameC" ,dtCopy.getFileName());
    assertEquals("groupC" ,dtCopy.getGroup());
    assertEquals("platformC" ,dtCopy.getPlatform());
    assertEquals("typeC" ,dtCopy.getType());
    assertEquals("versionC" ,dtCopy.getVersion());  
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifactB");
    dmB.setExtract(true);
    dmB.setFileName("fileNameB");
    dmB.setGroup("groupB");
    dmB.setPlatform("platformB");
    dmB.setType("typeB");
    dmB.setVersion("versionB");  
    
    List<DependencyMutant> dms = new ArrayList<DependencyMutant>();
    dms.add(dmA);
    dms.add(dmB);
    dms.add(null);
    
    fm.setDependencies(dms);
    assertSame(dmA, fm.getDependencies().get(0));
    assertSame(dmB, fm.getDependencies().get(1));
    
    List<I_Dependency> dms2 = new ArrayList<I_Dependency>();
    dms2.add(dmA);
    dms2.add(new Dependency(dmB));
    
    fm.setDependencies(dms);
    assertSame(dmA, fm.getDependencies().get(0));
    dtCopy = fm.getDependencies().get(1);
    assertEquals(DependencyMutant.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactB",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameB" ,dtCopy.getFileName());
    assertEquals("groupB" ,dtCopy.getGroup());
    assertEquals("platformB" ,dtCopy.getPlatform());
    assertEquals("typeB" ,dtCopy.getType());
    assertEquals("versionB" ,dtCopy.getVersion()); 
    
  }
}

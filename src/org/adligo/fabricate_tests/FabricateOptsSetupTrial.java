package org.adligo.fabricate_tests;

import org.adligo.fabricate.FabricateFactory;
import org.adligo.fabricate.FabricateOptsSetup;
import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.FabricateXmlDiscovery;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.fabricate.Fabricate;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.fabricate.JavaSettingsMutant;
import org.adligo.fabricate.repository.DefaultRepositoryPathBuilder;
import org.adligo.fabricate.repository.DependenciesNormalizer;
import org.adligo.fabricate.repository.RepositoryManager;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate_tests.common.log.ThreadLocalPrintStreamMock;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.DependencyTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@SourceFileScope (sourceClass=FabricateOptsSetup.class, minCoverage=83.0)
public class FabricateOptsSetupTrial extends MockitoSourceFileTrial {
  private FabSystem sysMock_;
  private I_FabFileIO fileMock_;
  private I_FabXmlFileIO xmlIoMock_;
  private I_FabLog logMock_;
  private ConcurrentLinkedQueue<I_Dependency> queueMock_;
  private MockMethod<Boolean> queueMockAddAll_;
  private FabricateFactory factoryMock_;
  private RepositoryManager repoManagerMock_;
  private MockMethod<Void> repoManagerAddDependencies_;
  private MockMethod<Boolean> repoManagerManageDependencies_;
  
  public void afterTests() {
    ThreadLocalPrintStreamMock.revert();
  }
  
  @SuppressWarnings("unchecked")
  public void beforeTests() {
    sysMock_ = mock(FabSystem.class);
    
    when(sysMock_.lineSeparator()).thenReturn("\n");
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.getDefaultLanguage()).thenReturn("en");
    when(sysMock_.getDefaultCountry()).thenReturn("US");
    when(sysMock_.newFabConstantsDiscovery("en", "US")).thenReturn(FabricateEnConstants.INSTANCE);
    
    fileMock_ = mock(I_FabFileIO.class);
    when(fileMock_.getNameSeparator()).thenReturn("/");
    xmlIoMock_ = mock(I_FabXmlFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(fileMock_);
    when(sysMock_.getXmlFileIO()).thenReturn(xmlIoMock_);
    
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    
    queueMock_ = mock(ConcurrentLinkedQueue.class);
    queueMockAddAll_ = new MockMethod<Boolean>();
    doAnswer(queueMockAddAll_).when(queueMock_).addAll(any());
    
    when(sysMock_.newConcurrentLinkedQueue(I_Dependency.class)).thenReturn(queueMock_);
    
    factoryMock_ = mock(FabricateFactory.class);
    repoManagerMock_ = mock(RepositoryManager.class);
    repoManagerAddDependencies_ = new MockMethod<Void>();
    doAnswer(repoManagerAddDependencies_).when(repoManagerMock_).addDependencies(any());
    repoManagerManageDependencies_ = new MockMethod<Boolean>(true, true);
    doAnswer(repoManagerManageDependencies_).when(repoManagerMock_).manageDependencies();
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorArgsFailNoFabricateXml() {
    FabricateFactory factoryMock = mock(FabricateFactory.class);
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(factoryMock.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock);
    
    assertEquals("Exception: No fabricate.xml or project.xml found.", printlnMethod.getArg(0));
    assertEquals("LASTLINE END", printlnMethod.getArg(1));
    assertEquals(2, printlnMethod.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorArgsFailNoJavaVersion() {
    FabricateFactory factoryMock = mock(FabricateFactory.class);
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "Exception: Java version parameter expected.")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock);
          }
        });
    
    assertEquals("LASTLINE END", printlnMethod.getArg(0));
    assertEquals(1, printlnMethod.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorArgsFailStart() {
    FabricateFactory factoryMock = mock(FabricateFactory.class);
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    when(sysMock_.getArgValue("java")).thenReturn("1.7.0_03");
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "Exception: No start time argument.")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock);
          }
        });
    
    assertEquals("LASTLINE END", printlnMethod.getArg(0));
    assertEquals(1, printlnMethod.count());
  }

  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorBadFabricateXmlFile() throws Exception {
    FabricateFactory factoryMock = mock(FabricateFactory.class);
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    
    when(sysMock_.getArgValue("java")).thenReturn("1.7.0_03");
    when(sysMock_.getArgValue("start")).thenReturn("123");
    String fabXml = "/somewhere/fabricate.xml";
    when(xmlDiscMock.getFabricateXmlPath()).thenReturn(fabXml);
    File fabricateXml = mock(File.class);
    when(fabricateXml.getAbsolutePath()).thenReturn(fabXml);
    when(fileMock_.instance(fabXml)).thenReturn(fabricateXml);
    
    IOException ioe = new IOException("ioe");
    FabricateType ft = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("/somewhere/fabricate.xml"))
          .thenThrow(ioe);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    MockMethod<Void> printlnTrace = new MockMethod<Void>();
    doAnswer(printlnTrace).when(logMock_).printTrace(any());
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock);
 
    assertEquals("LASTLINE END", printlnMethod.getArg(0));
    assertEquals(1, printlnMethod.count());
    
    assertSame(ioe, printlnTrace.getArg(0));
    assertEquals(1, printlnTrace.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorSimple() throws Exception {
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock_.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    
    when(sysMock_.getArgValue("java")).thenReturn("1.7.0_03");
    when(sysMock_.getArgValue("start")).thenReturn("123");
    String fabXml = "/somewhere/fabricate.xml";
    when(xmlDiscMock.getFabricateXmlPath()).thenReturn(fabXml);
    File fabricateXml = mock(File.class);
    when(fabricateXml.getAbsolutePath()).thenReturn(fabXml);
    when(fileMock_.instance(fabXml)).thenReturn(fabricateXml);
    
    FabricateType ft = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("/somewhere/fabricate.xml")).thenReturn(ft);
    
    when(sysMock_.getPathSeparator()).thenReturn(";");
    FabricateMutant fm = new FabricateMutant();
    JavaSettingsMutant settings = new JavaSettingsMutant();
    fm.setJavaSettings(settings);
    fm.setFabricateHome("/somewhere/fabHome");
    fm.setProjectsDir("someDirWithProjects");
    Fabricate fab = new Fabricate(fm);
    when(factoryMock_.create(sysMock_, ft, xmlDiscMock)).thenReturn(fab);
    when(factoryMock_.createRepositoryManager(sysMock_, fab)).thenReturn(repoManagerMock_);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock_);
    assertEquals(0, queueMockAddAll_.count());
    assertEquals(0, repoManagerAddDependencies_.count());
    assertEquals(0, repoManagerManageDependencies_.count());
    
    assertEquals("LASTLINE -Xmx64m -Xms16m -cp /somewhere/fabHome/lib/commons-logging-1.2.jar;"
        + "/somewhere/fabHome/lib/httpclient-4.3.5.jar;/somewhere/fabHome/lib/httpcore-4.3.2.jar", 
        printlnMethod.getArg(0));
  }
  
  @SuppressWarnings({"unused", "boxing", "unchecked"})
  @Test
  public void testConstructorStrenuousExtraRuntimeClasspath() throws Exception {
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock_.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    when(factoryMock_.createDependenciesNormalizer()).thenReturn(new DependenciesNormalizer());
    DefaultRepositoryPathBuilder builder = new DefaultRepositoryPathBuilder(
        "/somewhere/repo", "/");
    when(fileMock_.getNameSeparator()).thenReturn("/");
    when(factoryMock_.createRepositoryPathBuilder("/somewhere/repo","/")).thenReturn(builder);
    
    when(sysMock_.getArgValue("java")).thenReturn("1.7.0_03");
    when(sysMock_.getArgValue("start")).thenReturn("123");
    String fabXml = "/somewhere/fabricate.xml";
    when(xmlDiscMock.getFabricateXmlPath()).thenReturn(fabXml);
    File fabricateXml = mock(File.class);
    when(fabricateXml.getAbsolutePath()).thenReturn(fabXml);
    when(fileMock_.instance(fabXml)).thenReturn(fabricateXml);
    
    FabricateType ft = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("/somewhere/fabricate.xml")).thenReturn(ft);

    when(sysMock_.getPathSeparator()).thenReturn(";");
    FabricateMutant fm = new FabricateMutant();
    JavaSettingsMutant settings = new JavaSettingsMutant();
    fm.setJavaSettings(settings);
    fm.setFabricateHome("/somewhere/fabHome");
    fm.setFabricateRepository("/somewhere/repo");
    List<DependencyType> dts = DependencyMutantTrial.getDependencies();
    List<I_Dependency> deps = Dependency.convert(dts, null);
    fm.setDependencies(deps);
    fm.setProjectsDir("someDirWithProjects");
    Fabricate fab = new Fabricate(fm);
    
    when(factoryMock_.create(sysMock_, ft, xmlDiscMock)).thenReturn(fab);
    when(factoryMock_.createRepositoryManager(sysMock_, fab)).thenReturn(repoManagerMock_);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock_);
    List<I_Dependency> depsAddedToQueue = (List<I_Dependency>) queueMockAddAll_.getArg(0);
    DependencyTrial.assertDependencyConversion(this, depsAddedToQueue, null);
    assertEquals(1, queueMockAddAll_.count());
    
    assertSame(queueMock_, repoManagerAddDependencies_.getArg(0));
    assertEquals(1, repoManagerAddDependencies_.count());
    assertEquals(1, repoManagerManageDependencies_.count());
    
    assertEquals("LASTLINE -Xmx64m -Xms16m -cp /somewhere/fabHome/lib/commons-logging-1.2.jar;"
        + "/somewhere/fabHome/lib/httpclient-4.3.5.jar;/somewhere/fabHome/lib/httpcore-4.3.2.jar;"
        + "/somewhere/repogroupA/fileNameA;/somewhere/repogroupB/fileNameB", 
        printlnMethod.getArg(0));
  }
  
  @SuppressWarnings({"unused", "boxing", "unchecked"})
  @Test
  public void testConstructorStrenuousDownloadException() throws Exception {
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock_.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    when(factoryMock_.createDependenciesNormalizer()).thenReturn(new DependenciesNormalizer());
    DefaultRepositoryPathBuilder builder = new DefaultRepositoryPathBuilder(
        "/somewhere/repo", "/");
    when(fileMock_.getNameSeparator()).thenReturn("/");
    when(factoryMock_.createRepositoryPathBuilder("/somewhere/repo","/")).thenReturn(builder);
    
    when(sysMock_.getArgValue("java")).thenReturn("1.7.0_03");
    when(sysMock_.getArgValue("start")).thenReturn("123");
    String fabXml = "/somewhere/fabricate.xml";
    when(xmlDiscMock.getFabricateXmlPath()).thenReturn(fabXml);
    File fabricateXml = mock(File.class);
    when(fabricateXml.getAbsolutePath()).thenReturn(fabXml);
    when(fileMock_.instance(fabXml)).thenReturn(fabricateXml);
    
    FabricateType ft = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("/somewhere/fabricate.xml")).thenReturn(ft);

    when(sysMock_.getPathSeparator()).thenReturn(";");
    FabricateMutant fm = new FabricateMutant();
    JavaSettingsMutant settings = new JavaSettingsMutant();
    fm.setJavaSettings(settings);
    fm.setFabricateHome("/somewhere/fabHome");
    fm.setFabricateRepository("/somewhere/repo");
    List<DependencyType> dts = DependencyMutantTrial.getDependencies();
    List<I_Dependency> deps = Dependency.convert(dts, null);
    fm.setDependencies(deps);
    fm.setProjectsDir("someDirWithProjects");
    Fabricate fab = new Fabricate(fm);
    
    when(factoryMock_.create(sysMock_, ft, xmlDiscMock)).thenReturn(fab);
    when(factoryMock_.createRepositoryManager(sysMock_, fab)).thenReturn(repoManagerMock_);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    reset(repoManagerMock_);
    MockMethod<Boolean> repoManageMethod = new MockMethod<Boolean>(false, true);
    doAnswer(repoManagerAddDependencies_).when(repoManagerMock_).addDependencies(any());
    doAnswer(repoManageMethod).when(repoManagerMock_).manageDependencies();
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock_);
    List<I_Dependency> depsAddedToQueue = (List<I_Dependency>) queueMockAddAll_.getArg(0);
    DependencyTrial.assertDependencyConversion(this, depsAddedToQueue, null);
    assertEquals(1, queueMockAddAll_.count());
    
    assertSame(queueMock_, repoManagerAddDependencies_.getArg(0));
    assertEquals(1, repoManagerAddDependencies_.count());
    assertEquals(1, repoManageMethod.count());
    
    assertEquals("LASTLINE END", 
        printlnMethod.getArg(0));
  }
}

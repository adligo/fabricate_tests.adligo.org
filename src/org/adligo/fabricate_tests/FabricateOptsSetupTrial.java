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
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SourceFileScope (sourceClass=FabricateOptsSetup.class, minCoverage=85.0)
public class FabricateOptsSetupTrial extends MockitoSourceFileTrial {
  private FabSystem sysMock_;
  private I_FabFileIO fileMock_;
  private I_FabXmlFileIO xmlIoMock_;
  private I_FabLog logMock_;
  
  public void afterTests() {
    ThreadLocalPrintStreamMock.revert();
  }
  
  public void beforeTests() {
    sysMock_ = mock(FabSystem.class);
    
    when(sysMock_.lineSeperator()).thenReturn("\n");
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    fileMock_ = mock(I_FabFileIO.class);
    when(fileMock_.getNameSeparator()).thenReturn("/");
    xmlIoMock_ = mock(I_FabXmlFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(fileMock_);
    when(sysMock_.getXmlFileIO()).thenReturn(xmlIoMock_);
    
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    
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
    
    FabricateType ft = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("/somewhere/fabricate.xml")).thenReturn(ft);
    
    when(sysMock_.getPathSeparator()).thenReturn(";");
    FabricateMutant fm = new FabricateMutant();
    JavaSettingsMutant settings = new JavaSettingsMutant();
    fm.setJavaSettings(settings);
    fm.setFabricateHome("/somewhere/fabHome");
    Fabricate fab = new Fabricate(fm);
    when(factoryMock.create(sysMock_, ft, xmlDiscMock)).thenReturn(fab);
    
    RepositoryManager rm = mock(RepositoryManager.class);
    when(factoryMock.createRepositoryManager(sysMock_, fab)).thenReturn(rm);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock);
    
    assertEquals("LASTLINE -Xmx64m -Xms16m -cp /somewhere/fabHome/lib/commons-logging-1.2.jar;"
        + "/somewhere/fabHome/lib/httpclient-4.3.5.jar;/somewhere/fabHome/lib/httpcore-4.3.2.jar", 
        printlnMethod.getArg(0));
  }
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorExtraRuntimeClasspath() throws Exception {
    FabricateFactory factoryMock = mock(FabricateFactory.class);
    FabricateXmlDiscovery xmlDiscMock = mock(FabricateXmlDiscovery.class);
    when(xmlDiscMock.hasFabricateXml()).thenReturn(true);
    when(factoryMock.createDiscovery(sysMock_)).thenReturn(xmlDiscMock);
    
    when(factoryMock.createDependenciesNormalizer()).thenReturn(new DependenciesNormalizer());
    DefaultRepositoryPathBuilder builder = new DefaultRepositoryPathBuilder(
        "/somewhere/repo", "/");
    when(fileMock_.getNameSeparator()).thenReturn("/");
    when(factoryMock.createRepositoryPathBuilder("/somewhere/repo","/")).thenReturn(builder);
    
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
    List<I_Dependency> deps = Dependency.convert(dts);
    fm.setDependencies(deps);
    Fabricate fab = new Fabricate(fm);
    
    when(factoryMock.create(sysMock_, ft, xmlDiscMock)).thenReturn(fab);
    
    RepositoryManager rm = mock(RepositoryManager.class);
    when(factoryMock.createRepositoryManager(sysMock_, fab)).thenReturn(rm);
    
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock_).println(any());
    
    new FabricateOptsSetup(new String[] {}, sysMock_, factoryMock);
    
    assertEquals("LASTLINE -Xmx64m -Xms16m -cp /somewhere/fabHome/lib/commons-logging-1.2.jar;"
        + "/somewhere/fabHome/lib/httpclient-4.3.5.jar;/somewhere/fabHome/lib/httpcore-4.3.2.jar;"
        + "/somewhere/repogroupA/fileNameA;/somewhere/repogroupB/fileNameB", 
        printlnMethod.getArg(0));
  }
}

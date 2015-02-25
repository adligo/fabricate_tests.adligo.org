package org.adligo.fabricate_tests;

import org.adligo.fabricate.FabricateFactory;
import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.common.system.FabricateEnvironment;
import org.adligo.fabricate.common.system.FabricateXmlDiscovery;
import org.adligo.fabricate.managers.CommandManager;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.fabricate.Fabricate;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.fabricate.I_Fabricate;
import org.adligo.fabricate.models.fabricate.I_FabricateXmlDiscovery;
import org.adligo.fabricate.repository.DefaultRepositoryPathBuilder;
import org.adligo.fabricate.repository.DependenciesManager;
import org.adligo.fabricate.repository.DependenciesNormalizer;
import org.adligo.fabricate.repository.DependencyManager;
import org.adligo.fabricate.repository.I_DependenciesManager;
import org.adligo.fabricate.repository.I_DependenciesNormalizer;
import org.adligo.fabricate.repository.I_DependencyManager;
import org.adligo.fabricate.repository.I_LibraryResolver;
import org.adligo.fabricate.repository.I_RepositoryPathBuilder;
import org.adligo.fabricate.repository.LibraryResolver;
import org.adligo.fabricate.repository.RepositoryManager;
import org.adligo.fabricate.routines.RoutineFabricateFactory;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@SourceFileScope (sourceClass=FabricateFactory.class, minCoverage=75.0)
public class FabricateFactoryTrial extends MockitoSourceFileTrial {
  private ByteArrayOutputStream baos_;
  private FabSystem sysMock_;
  private I_FabFileIO fileMock_;
  private I_FabXmlFileIO xmlIoMock_;
  private I_FabLog mockLog_;
  
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
    
    mockLog_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(mockLog_);
    
    baos_ = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos_);
    
    ThreadLocalPrintStreamMock.set(printStream);
  }
  
  
  
  @Test
  public void testMethodCreate() throws Exception {
    FabricateFactory factory = new FabricateFactory();
    FabricateType fabType = new FabricateType();
    I_FabricateXmlDiscovery xmlDiscMock = mock(I_FabricateXmlDiscovery.class);
    when(xmlDiscMock.getDevXmlDir()).thenReturn("someDevXmlDir");
    when(xmlDiscMock.getFabricateXmlDir()).thenReturn("someFabricateXmlDir");
    when(xmlDiscMock.getProjectXmlDir()).thenReturn("someProjectXmlDir");
    
    when(sysMock_.getenv(FabricateEnvironment.JAVA_HOME)).thenReturn("javaHome");
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn("fabricateHome");
    
   
    Fabricate fab = factory.create(sysMock_, fabType, xmlDiscMock);
    assertNotNull(fab);
    assertEquals("javaHome", fab.getJavaHome());
    assertEquals("fabricateHome", fab.getFabricateHome());
    assertEquals("someDevXmlDir", fab.getFabricateDevXmlDir());
    assertEquals("someFabricateXmlDir", fab.getFabricateXmlRunDir());
    assertEquals("someProjectXmlDir", fab.getFabricateProjectRunDir());
    assertEquals(FabricateDefaults.LOCAL_REPOSITORY, fab.getFabricateRepository());
    
    FabricateDependencies fdeps = new FabricateDependencies();
    fabType.setDependencies(fdeps);
    fab = factory.create(sysMock_, fabType, xmlDiscMock);
    assertNotNull(fab);
    assertEquals("javaHome", fab.getJavaHome());
    assertEquals("fabricateHome", fab.getFabricateHome());
    assertEquals("someDevXmlDir", fab.getFabricateDevXmlDir());
    assertEquals("someFabricateXmlDir", fab.getFabricateXmlRunDir());
    assertEquals("someProjectXmlDir", fab.getFabricateProjectRunDir());
    assertEquals(FabricateDefaults.LOCAL_REPOSITORY, fab.getFabricateRepository());
  }
  
  @Test
  public void testMethodCreateSimple() throws Exception {
    FabricateFactory factory = new FabricateFactory();
    Fabricate fab = factory.create(new FabricateMutant());
    assertNotNull(fab);
  }
  
  @Test
  public void testMethodCreateCommandManager() throws Exception {
    FabricateFactory factory = new FabricateFactory();
    RoutineFabricateFactory routineFactory = new RoutineFabricateFactory(new FabricateMutant(), true);
    
    CommandManager cm = factory.createCommandManager(
        Collections.singleton("hey"), sysMock_, routineFactory);
    assertNotNull(cm);
  }
  
  @Test
  public void testMethodCreateDependenciesNormalizer() {
    FabricateFactory factory = new FabricateFactory();
    ConcurrentLinkedQueue<I_Dependency> clq = new ConcurrentLinkedQueue<I_Dependency>();
    I_DependenciesManager idm = factory.createDependenciesManager(sysMock_, clq);
    assertEquals(DependenciesManager.class.getName(), idm.getClass().getName());
    DependenciesManager dm = (DependenciesManager) idm;
    assertSame(clq, dm.getDependencyQueue());
    
    I_DependenciesNormalizer idn = factory.createDependenciesNormalizer();
    assertEquals(DependenciesNormalizer.class.getName(), idn.getClass().getName());
  }
  
  @Test
  public void testMethodCreateDependenciesManager() {
    FabricateFactory factory = new FabricateFactory();
    ConcurrentLinkedQueue<I_Dependency> clq = new ConcurrentLinkedQueue<I_Dependency>();
    I_DependenciesManager idm = factory.createDependenciesManager(sysMock_, clq);
    assertEquals(DependenciesManager.class.getName(), idm.getClass().getName());
    DependenciesManager dm = (DependenciesManager) idm;
    assertSame(clq, dm.getDependencyQueue());
    
    I_DependenciesNormalizer idn = factory.createDependenciesNormalizer();
    assertEquals(DependenciesNormalizer.class.getName(), idn.getClass().getName());
    
    I_RepositoryPathBuilder builder = mock(I_RepositoryPathBuilder.class);
    
    I_DependencyManager idepm = factory.createDependencyManager(sysMock_, Collections.singletonList("repo"), builder);
    assertEquals(DependencyManager.class.getName(), idepm.getClass().getName());

    FabricateXmlDiscovery disc = factory.createDiscovery(sysMock_);
    assertNotNull(disc);
    
  }
  
  @Test
  public void testMethodCreateDiscovery() {
    FabricateFactory factory = new FabricateFactory();
    FabricateXmlDiscovery disc = factory.createDiscovery(sysMock_);
    assertNotNull(disc);
  }
  
  @Test
  public void testMethodCreateMutant() throws Exception {
    FabricateFactory factory = new FabricateFactory();
    FabricateType fabType = new FabricateType();
    I_FabricateXmlDiscovery xmlDiscMock = mock(I_FabricateXmlDiscovery.class);
    when(xmlDiscMock.getDevXmlDir()).thenReturn("someDevXmlDir");
    when(xmlDiscMock.getFabricateXmlDir()).thenReturn("someFabricateXmlDir");
    when(xmlDiscMock.getProjectXmlDir()).thenReturn("someProjectXmlDir");
    
    when(sysMock_.getenv(FabricateEnvironment.JAVA_HOME)).thenReturn("javaHome");
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn("fabricateHome");
    
   
    Fabricate fab = factory.create(sysMock_, fabType, xmlDiscMock);
    FabricateMutant mut = factory.createMutant(fab);
    assertNotNull(mut);
  }
  
  @Test
  public void testMethodCreateRepositoryManager() {
    FabricateFactory factory = new FabricateFactory();
    ConcurrentLinkedQueue<I_Dependency> clq = new ConcurrentLinkedQueue<I_Dependency>();
    I_DependenciesManager idm = factory.createDependenciesManager(sysMock_, clq);
    assertEquals(DependenciesManager.class.getName(), idm.getClass().getName());
    DependenciesManager dm = (DependenciesManager) idm;
    assertSame(clq, dm.getDependencyQueue());
    
    FabricateMutant fabricate = new FabricateMutant();
    I_LibraryResolver lr =  factory.createLibraryResolver(sysMock_, fabricate);
    assertEquals(LibraryResolver.class.getName(), lr.getClass().getName());
    
    RepositoryManager rm = factory.createRepositoryManager(sysMock_, fabricate);
    assertNotNull(rm);
  }
  
  @Test
  public void testMethodCreateRepositoryPathBuilder() {
    FabricateFactory factory = new FabricateFactory();
    ConcurrentLinkedQueue<I_Dependency> clq = new ConcurrentLinkedQueue<I_Dependency>();
    I_DependenciesManager idm = factory.createDependenciesManager(sysMock_, clq);
    assertEquals(DependenciesManager.class.getName(), idm.getClass().getName());
    DependenciesManager dm = (DependenciesManager) idm;
    assertSame(clq, dm.getDependencyQueue());
    
    I_RepositoryPathBuilder rpb = factory.createRepositoryPathBuilder("remoteRepo");
    assertEquals(DefaultRepositoryPathBuilder.class.getName(), rpb.getClass().getName());
    DefaultRepositoryPathBuilder builder = (DefaultRepositoryPathBuilder) rpb;
    assertEquals("remoteRepo", builder.getRepository());
    assertEquals("/", builder.getSeperator());
    
    I_RepositoryPathBuilder lpb = factory.createRepositoryPathBuilder("remoteRepo","\\");
    assertEquals(DefaultRepositoryPathBuilder.class.getName(), lpb.getClass().getName());
    DefaultRepositoryPathBuilder localBuilder = (DefaultRepositoryPathBuilder) lpb;
    assertEquals("remoteRepo", localBuilder.getRepository());
    assertEquals("\\", localBuilder.getSeperator());
  }
   
  @Test
  public void testMethodCreateRoutineFabricateFactory() {
    FabricateFactory factory = new FabricateFactory();
    I_Fabricate fab = mock(I_Fabricate.class);
    RoutineFabricateFactory routineFactory =  factory.createRoutineFabricateFactory(fab, true);
   assertNotNull(routineFactory);
  }
  @Test
  public void testMethodCreateWithRuntimeDeps() throws Exception {
    FabricateFactory factory = new FabricateFactory();
    FabricateType fabType = new FabricateType();
    
    List<DependencyType> dts = DependencyMutantTrial.getDependencies();
    FabricateDependencies fdeps = new FabricateDependencies();
    fdeps.getDependency().addAll(dts);
    fabType.setDependencies(fdeps);
    
    I_FabricateXmlDiscovery xmlDiscMock = mock(I_FabricateXmlDiscovery.class);
    
    when(sysMock_.getenv(FabricateEnvironment.JAVA_HOME)).thenReturn("javaHome");
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn("fabricateHome");
    Fabricate fab = factory.create(sysMock_, fabType, xmlDiscMock);
    assertNotNull(fab);
    assertEquals("javaHome", fab.getJavaHome());
    assertEquals("fabricateHome", fab.getFabricateHome());
    assertNull(fab.getFabricateDevXmlDir());
    assertNull(fab.getFabricateXmlRunDir());
    assertNull(fab.getFabricateProjectRunDir());
    assertEquals(FabricateDefaults.LOCAL_REPOSITORY, fab.getFabricateRepository());
    
    List<I_Dependency> deps = fab.getDependencies();
    DependencyTrial.assertDependencyConversion(this, deps);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodCreateExceptions() {
    FabricateFactory factory = new FabricateFactory();
    FabricateType fabType = new FabricateType();

    
    I_FabricateXmlDiscovery xmlDiscMock = mock(I_FabricateXmlDiscovery.class);
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(mockLog_).println(any());
    
    assertThrown(new ExpectedThrowable(IllegalStateException.class),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            factory.create(sysMock_, fabType, xmlDiscMock);
          }
        });
    assertEquals("Exception: No $FABRICATE_HOME environment variable set.",
        printlnMethod.getArg(0));
    assertEquals("LASTLINE END",
        printlnMethod.getArg(1));
    assertEquals(2, printlnMethod.count());
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(mockLog_).println(any());
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn("fabricateHome");
    assertThrown(new ExpectedThrowable(IllegalStateException.class),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            factory.create(sysMock_, fabType, xmlDiscMock);
          }
        });
    assertEquals("Exception: No $JAVA_HOME environment variable set.",
        printlnMethod.getArg(0));
    assertEquals("LASTLINE END",
        printlnMethod.getArg(1));
    assertEquals(2, printlnMethod.count());
  }
}

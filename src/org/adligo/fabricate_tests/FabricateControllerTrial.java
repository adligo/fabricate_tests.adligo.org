package org.adligo.fabricate_tests;

import org.adligo.fabricate.FabricateController;
import org.adligo.fabricate.FabricateFactory;
import org.adligo.fabricate.common.en.CommandLineEnConstants;
import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.log.I_FabFileLog;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.CommandLineArgs;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.FabricateXmlDiscovery;
import org.adligo.fabricate.managers.CommandManager;
import org.adligo.fabricate.models.common.FabricationMemoryMutant;
import org.adligo.fabricate.models.fabricate.Fabricate;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.routines.implicit.RoutineFabricateFactory;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.result_v1_0.FailureType;
import org.adligo.fabricate.xml.io_v1.result_v1_0.MachineInfoType;
import org.adligo.fabricate.xml.io_v1.result_v1_0.ResultType;
import org.adligo.fabricate_tests.common.log.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;


@SourceFileScope (sourceClass=FabricateController.class, minCoverage=55.0)
public class FabricateControllerTrial extends MockitoSourceFileTrial {
  private FabSystem sysMock_;
  private I_FabFileIO fileMock_;
  private MockMethod<Void> deleteOnExitMethod_;
  private I_FabXmlFileIO xmlIoMock_;
  private I_FabLog logMock_;
  private MockMethod<Void> printlnMethod_;
  private MockMethod<Void> printTraceMethod_;
  private FabricateFactory factoryMock_;
  private FabricateXmlDiscovery xmlDiscoveryMock_;
  private FabricationMemoryMutant<Object> memory_ = new FabricationMemoryMutant<Object>();
  
  public void afterTests() {
    ThreadLocalPrintStreamMock.revert();
  }
  
  @SuppressWarnings("boxing")
  public void beforeTests() {
    sysMock_ = mock(FabSystem.class);
    
    when(sysMock_.lineSeperator()).thenReturn("\n");
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    fileMock_ = mock(I_FabFileIO.class);
    deleteOnExitMethod_ = new MockMethod<Void>();
    doAnswer(deleteOnExitMethod_).when(fileMock_).deleteOnExit(any());
    
    when(fileMock_.getNameSeparator()).thenReturn("/");
    xmlIoMock_ = mock(I_FabXmlFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(fileMock_);
    when(sysMock_.getXmlFileIO()).thenReturn(xmlIoMock_);
    
    logMock_ = mock(I_FabLog.class);
    printlnMethod_ = new MockMethod<Void>();
    doAnswer(printlnMethod_).when(logMock_).println(any());
    
    printTraceMethod_ = new MockMethod<Void>();
    doAnswer(printTraceMethod_).when(logMock_).printTrace(any());
    
    when(sysMock_.getLog()).thenReturn(logMock_);
    
    xmlDiscoveryMock_ = mock(FabricateXmlDiscovery.class);
    when(xmlDiscoveryMock_.hasFabricateXml()).thenReturn(true);
    when(xmlDiscoveryMock_.getFabricateXmlDir()).thenReturn("fabricateXmlDir/");
    factoryMock_ = mock(FabricateFactory.class);
    when(factoryMock_.createDiscovery(sysMock_)).thenReturn(xmlDiscoveryMock_);
    
    when(factoryMock_.createMemory()).thenReturn(memory_);
  }
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorCommandNoFabricateXmlOrProjectXml() throws Exception {
    when(xmlDiscoveryMock_.hasFabricateXml()).thenReturn(false);
    
    new FabricateController(sysMock_, new String[] {}, factoryMock_);
    
    assertEquals("Exception: No fabricate.xml or project.xml found.", printlnMethod_.getArg(0));
  }
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorCommandFabricateAlreadyRunning() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(true);
    
    new FabricateController(sysMock_, new String[] {}, factoryMock_);
    
    assertEquals("Fabricate appears to already be running \n" + 
        "(run.marker is in the same directory as fabricate.xml).\n" +
        "fabricateXmlDir/", printlnMethod_.getArg(0));
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandClassNotFoundFromAddCommands() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    when(fileMock_.mkdirs("fabricateXmlDir/output")).thenReturn(true);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    ClassNotFoundException x = new ClassNotFoundException("foo");
    doThrow(x).when(fmMock).addCommands(ftMock);
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    
    List<String> commands = Collections.singletonList("hey");
    when(sysMock_.getArgValues(CommandLineEnConstants.INSTANCE.getCommand())).thenReturn(
        commands);
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
   
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals("Unable to load the following class;\n" +
        "foo", printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
    
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandClassNotFoundFromAddStages() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    when(fileMock_.mkdirs("fabricateXmlDir/output")).thenReturn(true);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    ClassNotFoundException x = new ClassNotFoundException("foo");
    doThrow(x).when(fmMock).addStages(ftMock);
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
   
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals("Unable to load the following class;\n" +
        "foo", printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandClassNotFoundFromAddTraits() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = mock(FabricateType.class);
    List<RoutineParentType> traits = new ArrayList<RoutineParentType>();
    when(ftMock.getTrait()).thenReturn(traits);
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    when(fileMock_.mkdirs("fabricateXmlDir/output")).thenReturn(true);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    ClassNotFoundException x = new ClassNotFoundException("foo2");
    doThrow(x).when(fmMock).addTraits(traits);
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    
    new FabricateController(sysMock_, new String[] {"start=123","cmd=encrypt"}, factoryMock_);
   
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals("Unable to load the following class;\n" +
        "foo2", printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandDataTypeConfigurationException() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    when(fileMock_.mkdirs("fabricateXmlDir/output")).thenReturn(true);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    List<String> commands = Collections.singletonList("hey");
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    RoutineFabricateFactory routineFactoryMock = mock(RoutineFabricateFactory.class);
    when(factoryMock_.createRoutineFabricateFactory(sysMock_, fabMock, true)).thenReturn(routineFactoryMock);
    CommandManager cmMock = mock(CommandManager.class);
    when(factoryMock_.createCommandManager(commands, sysMock_, routineFactoryMock)).thenReturn(cmMock);
    
    when(sysMock_.getArgValues(CommandLineEnConstants.INSTANCE.getCommand())).thenReturn(
        commands);
    when(factoryMock_.create(fmMock)).thenReturn(fabMock);
    when(sysMock_.getArgValue(CommandLineArgs.PASSABLE_ARGS_)).thenReturn("[1,[11,cmd=encrypt]]");
    File fabricateFileMock = mock(File.class);
    when(fabricateFileMock.getName()).thenReturn("fabProject");
    when(fileMock_.instance("fabricateXmlDir/")).thenReturn(fabricateFileMock);
    when(sysMock_.getOperatingSystem()).thenReturn("other");
    when(sysMock_.getCpuInfo("other")).thenReturn(new String[] {"cpuInfo1", "cpuInfo2"});
    when(sysMock_.getArgValue("start")).thenReturn("123");
    when(sysMock_.getCurrentTime()).thenReturn(456L);
    
    DatatypeConfigurationException dataTypeX = new DatatypeConfigurationException("dce");
    when(sysMock_.newDatatypeFactory()).thenThrow(dataTypeX);
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
   
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
    assertSame(dataTypeX, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandIOExceptionFromCreateRunMarker() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    IOException x = new IOException("ipe");
    when(fileMock_.create("fabricateXmlDir/run.marker")).thenThrow(x);
    new FabricateController(sysMock_, new String[] {}, factoryMock_);
    
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
    assertEquals("There was a problem creating run.marker in the following directory;\n" +
        "fabricateXmlDir/", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandIOExceptionFromCreateRunMarkerOutputStream() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    IOException x = new IOException("ipe");
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenThrow(x);
    new FabricateController(sysMock_, new String[] {}, factoryMock_);
    
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
    assertEquals("There was a problem creating run.marker in the following directory;\n" +
        "fabricateXmlDir/", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandIOExceptionFromCreateFabLogFile() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    when(fileMock_.mkdirs("fabricateXmlDir/output")).thenReturn(true);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    List<String> commands = Collections.singletonList("hey");
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    RoutineFabricateFactory routineFactoryMock = mock(RoutineFabricateFactory.class);
    when(factoryMock_.createRoutineFabricateFactory(sysMock_, fabMock, true)).thenReturn(routineFactoryMock);
    CommandManager cmMock = mock(CommandManager.class);
    when(factoryMock_.createCommandManager(commands, sysMock_, routineFactoryMock)).thenReturn(cmMock);
    
    when(sysMock_.getArgValues(CommandLineEnConstants.INSTANCE.getCommand())).thenReturn(
        commands);
    when(factoryMock_.create(fmMock)).thenReturn(fabMock);
    when(sysMock_.getArgValue(CommandLineArgs.PASSABLE_ARGS_)).thenReturn("[1,[11,cmd=encrypt]]");
    File fabricateFileMock = mock(File.class);
    when(fabricateFileMock.getName()).thenReturn("fabProject");
    when(fileMock_.instance("fabricateXmlDir/")).thenReturn(fabricateFileMock);
    when(sysMock_.getOperatingSystem()).thenReturn("other");
    when(sysMock_.getCpuInfo("other")).thenReturn(new String[] {"cpuInfo1", "cpuInfo2"});
    when(sysMock_.getArgValue("start")).thenReturn("123");
    when(sysMock_.hasArg("-w")).thenReturn(true);
    when(sysMock_.getCurrentTime()).thenReturn(456L);
    
    IOException x = new IOException("ioe");
    when(sysMock_.newFabFileLog("fabricateXmlDir/output/fab.log")).thenThrow(x);
    assertThrown(new ExpectedThrowable(x), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
      }
    });
    
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals(0, printlnMethod_.count());
    assertEquals(0, printTraceMethod_.count());
  }
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorCommandIOExceptionFromDeleteOutputDirRecursive() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(true);
    IOException x = new IOException("ioe");
    doThrow(x).when(fileMock_).deleteRecursive("fabricateXmlDir/output");
    
    assertThrown(new ExpectedThrowable(new IOException("ioe")), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
      }
    });
   
    
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(0, printlnMethod_.count());
    assertEquals(0, printTraceMethod_.count());
    
  }
  
  @Test
  public void testConstructorCommandIOExceptionFromCreateResultXmlFile() {
    
  }
  
  @Test
  public void testConstructorCommandIOExceptionFromWriteResultXmlFile() {
    
  }

  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorCommandIOExceptionFromWriteToRunMarker() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    OutputStream outMock = mock(OutputStream.class);
    IOException x = new IOException("ipe");
    doThrow(x).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
    
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
    assertEquals("There was a problem creating run.marker in the following directory;\n" +
        "fabricateXmlDir/", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
  }
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorCommandFailureToCreateOutputDir() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    when(fileMock_.mkdirs("fabricateXmlDir/output")).thenReturn(false);
    File fileMock = mock(File.class);
    when(fileMock.getAbsolutePath()).thenReturn("absFile");
    when(fileMock_.instance("fabricateXmlDir/output")).thenReturn(fileMock);
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
   
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("There was a problem creating the following directory;\n" +
          "absFile", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
    assertEquals(0, printTraceMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandFileNotFoundExceptionFromCreateRunMarkerOutputStream() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    FileNotFoundException x = new FileNotFoundException("ipe");
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenThrow(x);
    new FabricateController(sysMock_, new String[] {}, factoryMock_);
    
    assertSame(x, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
    assertEquals("There was a problem creating run.marker in the following directory;\n" +
        "fabricateXmlDir/", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandEncrypt() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    File mockResult = mock(File.class);
    when(mockResult.getAbsolutePath()).thenReturn("fabricateXmlDir/output/result.xml");
    MockMethod<File> createFileMethod = new MockMethod<File>(mock(File.class), mockResult);
    doAnswer(createFileMethod).when(fileMock_).create(any());
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    MockMethod<Boolean> createDirsMethod = new MockMethod<Boolean>(true, true);
    when(fileMock_.mkdirs(any())).then(createDirsMethod);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    List<String> commands = Collections.singletonList("hey");
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    RoutineFabricateFactory routineFactoryMock = mock(RoutineFabricateFactory.class);
    when(factoryMock_.createRoutineFabricateFactory(sysMock_, fabMock, true)).thenReturn(routineFactoryMock);
    CommandManager cmMock = mock(CommandManager.class);
    when(factoryMock_.createCommandManager(commands, sysMock_, routineFactoryMock)).thenReturn(cmMock);
    
    when(sysMock_.getArgValues(CommandLineEnConstants.INSTANCE.getCommand())).thenReturn(
        commands);
    when(factoryMock_.create(fmMock)).thenReturn(fabMock);
    when(sysMock_.getArgValue(CommandLineArgs.PASSABLE_ARGS_)).thenReturn("[1,[11,cmd=encrypt]]");
    File fabricateFileMock = mock(File.class);
    when(fabricateFileMock.getName()).thenReturn("fabProject");
    when(fileMock_.instance("fabricateXmlDir/")).thenReturn(fabricateFileMock);
    when(sysMock_.getOperatingSystem()).thenReturn("other");
    when(sysMock_.getOperatingSystemVersion("other")).thenReturn("3.1");
    when(sysMock_.getCpuInfo("other")).thenReturn(new String[] {"cpuInfo1", "cpuInfo2"});
    when(sysMock_.getArgValue("start")).thenReturn("123");
    when(sysMock_.getCurrentTime()).thenReturn(456L);
    MockMethod<Void> writeResultMethod = new MockMethod<Void>();
    doAnswer(writeResultMethod).when(xmlIoMock_).writeResult_v1_0(any(), any());
    
    when(sysMock_.newDatatypeFactory()).thenReturn(DatatypeFactory.newInstance());
    
    
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
    assertEquals("fabricateXmlDir/run.marker", createFileMethod.getArg(0));
    assertEquals("fabricateXmlDir/output/result.xml", createFileMethod.getArg(1));
    assertEquals(2, createFileMethod.count());
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals("123", new String((byte []) writeMethod.getArg(0), "UTF-8"));
    assertEquals(1, writeMethod.count());
    assertEquals("fabricateXmlDir/output", createDirsMethod.getArg(0));
    assertEquals(1, createDirsMethod.count());
    
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals("Fabrication successful!", printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertEquals(0, printTraceMethod_.count());
    
    Object[] args = writeResultMethod.getArgs(0);
    assertEquals("fabricateXmlDir/output/result.xml", args[0]);
    ResultType rt = (ResultType) args[1];
    assertNotNull(rt);
    assertEquals("fab cmd=encrypt", rt.getCommandLine());
    assertEquals("fabProject", rt.getName());
    assertEquals("other", rt.getOs());
    assertEquals("3.1", rt.getOsVersion());
    assertEquals("P0Y0M0DT0H0M0.333S", rt.getDuration().toString());
    assertNull(rt.getFailure());
    MachineInfoType mit = rt.getMachine();
    assertNotNull(mit);
    assertEquals("cpuInfo1", mit.getCpuName());
    assertEquals("cpuInfo2", mit.getCpuSpeed());
    assertNull(rt.getTests());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandEncryptWithFabLogFile() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    File mockResult = mock(File.class);
    when(mockResult.getAbsolutePath()).thenReturn("fabricateXmlDir/output/result.xml");
    MockMethod<File> createFileMethod = new MockMethod<File>(mock(File.class), mockResult);
    doAnswer(createFileMethod).when(fileMock_).create(any());
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    MockMethod<Boolean> createDirsMethod = new MockMethod<Boolean>(true, true);
    when(fileMock_.mkdirs(any())).then(createDirsMethod);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    List<String> commands = Collections.singletonList("hey");
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    RoutineFabricateFactory routineFactoryMock = mock(RoutineFabricateFactory.class);
    when(factoryMock_.createRoutineFabricateFactory(sysMock_, fabMock, true)).thenReturn(routineFactoryMock);
    CommandManager cmMock = mock(CommandManager.class);
    when(factoryMock_.createCommandManager(commands, sysMock_, routineFactoryMock)).thenReturn(cmMock);
    
    when(sysMock_.getArgValues(CommandLineEnConstants.INSTANCE.getCommand())).thenReturn(
        commands);
    when(factoryMock_.create(fmMock)).thenReturn(fabMock);
    when(sysMock_.getArgValue(CommandLineArgs.PASSABLE_ARGS_)).thenReturn("[1,[11,cmd=encrypt]]");
    File fabricateFileMock = mock(File.class);
    when(fabricateFileMock.getName()).thenReturn("fabProject");
    when(fileMock_.instance("fabricateXmlDir/")).thenReturn(fabricateFileMock);
    when(sysMock_.getOperatingSystem()).thenReturn("other");
    when(sysMock_.getOperatingSystemVersion("other")).thenReturn("3.1");
    when(sysMock_.getCpuInfo("other")).thenReturn(new String[] {"cpuInfo1", "cpuInfo2"});
    when(sysMock_.getArgValue("start")).thenReturn("123");
    when(sysMock_.getCurrentTime()).thenReturn(456L);
    MockMethod<Void> writeResultMethod = new MockMethod<Void>();
    doAnswer(writeResultMethod).when(xmlIoMock_).writeResult_v1_0(any(), any());
    when(sysMock_.hasArg("-w")).thenReturn(true);
    when(sysMock_.newDatatypeFactory()).thenReturn(DatatypeFactory.newInstance());
    
    I_FabFileLog logMock = mock(I_FabFileLog.class);
    when(sysMock_.newFabFileLog("fabricateXmlDir/output/fab.log")).thenReturn(logMock);
    MockMethod<Void> closeLogMethod = new MockMethod<Void>();
    doAnswer(closeLogMethod).when(logMock).close();
    
    MockMethod<Void> setLogFileMethod = new MockMethod<Void>();
    doAnswer(setLogFileMethod).when(sysMock_).setLogFile(any());
    
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
    assertEquals("fabricateXmlDir/run.marker", createFileMethod.getArg(0));
    assertEquals("fabricateXmlDir/output/result.xml", createFileMethod.getArg(1));
    assertEquals(2, createFileMethod.count());
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals("123", new String((byte []) writeMethod.getArg(0), "UTF-8"));
    assertEquals(1, writeMethod.count());
    assertEquals("fabricateXmlDir/output", createDirsMethod.getArg(0));
    assertEquals(1, createDirsMethod.count());
    
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals("Fabrication successful!", printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertEquals(0, printTraceMethod_.count());
    
    Object[] args = writeResultMethod.getArgs(0);
    assertEquals("fabricateXmlDir/output/result.xml", args[0]);
    ResultType rt = (ResultType) args[1];
    assertNotNull(rt);
    assertEquals("fab cmd=encrypt", rt.getCommandLine());
    assertEquals("fabProject", rt.getName());
    assertEquals("other", rt.getOs());
    assertEquals("3.1", rt.getOsVersion());
    assertEquals("P0Y0M0DT0H0M0.333S", rt.getDuration().toString());
    assertNull(rt.getFailure());
    MachineInfoType mit = rt.getMachine();
    assertNotNull(mit);
    assertEquals("cpuInfo1", mit.getCpuName());
    assertEquals("cpuInfo2", mit.getCpuSpeed());
    assertNull(rt.getTests());
    assertEquals(1, closeLogMethod.count());
    assertSame(logMock, setLogFileMethod.getArg(0));
    assertEquals(1, setLogFileMethod.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorCommandMockEncryptFailure() throws Exception {
    when(fileMock_.exists("fabricateXmlDir/run.marker")).thenReturn(false);
    when(xmlDiscoveryMock_.getFabricateXmlPath()).thenReturn("fabricateDir/fabricate.xml");
    FabricateType ftMock = new FabricateType();
    when(xmlIoMock_.parseFabricate_v1_0("fabricateDir/fabricate.xml")).thenReturn(ftMock);
    
    File mockResult = mock(File.class);
    when(mockResult.getAbsolutePath()).thenReturn("fabricateXmlDir/output/result.xml");
    MockMethod<File> createFileMethod = new MockMethod<File>(mock(File.class), mockResult);
    doAnswer(createFileMethod).when(fileMock_).create(any());
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write("123".getBytes("UTF-8"));
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    when(fileMock_.newFileOutputStream("fabricateXmlDir/run.marker")).thenReturn(outMock);
    when(fileMock_.exists("fabricateXmlDir/output")).thenReturn(false);
    MockMethod<Boolean> createDirsMethod = new MockMethod<Boolean>(true, true);
    when(fileMock_.mkdirs(any())).then(createDirsMethod);
    
    Fabricate fabMock = mock(Fabricate.class);
    when(factoryMock_.create(sysMock_, ftMock, xmlDiscoveryMock_)).thenReturn(fabMock);
    FabricateMutant fmMock = mock(FabricateMutant.class);
    
    List<String> commands = Collections.singletonList("hey");
    when(factoryMock_.createMutant(fabMock)).thenReturn(fmMock);
    RoutineFabricateFactory routineFactoryMock = mock(RoutineFabricateFactory.class);
    when(factoryMock_.createRoutineFabricateFactory(sysMock_, fabMock, true)).thenReturn(routineFactoryMock);
    CommandManager cmMock = mock(CommandManager.class);
    FailureType ft = new FailureType();
    ft.setCommand("encrypt");
    ft.setDetail("detail");
    FabricationMemoryMutant memory = new FabricationMemoryMutant();
    when(factoryMock_.createMemory()).thenReturn(memory);
    when(cmMock.processCommands(memory)).thenReturn(ft);
    when(factoryMock_.createCommandManager(commands, sysMock_, routineFactoryMock)).thenReturn(cmMock);
    
    when(sysMock_.getArgValues(CommandLineEnConstants.INSTANCE.getCommand())).thenReturn(
        commands);
    when(factoryMock_.create(fmMock)).thenReturn(fabMock);
    when(sysMock_.getArgValue(CommandLineArgs.PASSABLE_ARGS_)).thenReturn("[1,[11,cmd=encrypt]]");
    File fabricateFileMock = mock(File.class);
    when(fabricateFileMock.getName()).thenReturn("fabProject");
    when(fileMock_.instance("fabricateXmlDir/")).thenReturn(fabricateFileMock);
    when(sysMock_.getOperatingSystem()).thenReturn("other");
    when(sysMock_.getOperatingSystemVersion("other")).thenReturn("3.1");
    when(sysMock_.getCpuInfo("other")).thenReturn(new String[] {"cpuInfo1", "cpuInfo2"});
    when(sysMock_.getArgValue("start")).thenReturn("123");
    when(sysMock_.getCurrentTime()).thenReturn(456L);
    MockMethod<Void> writeResultMethod = new MockMethod<Void>();
    doAnswer(writeResultMethod).when(xmlIoMock_).writeResult_v1_0(any(), any());
    
    when(sysMock_.newDatatypeFactory()).thenReturn(DatatypeFactory.newInstance());
    
    new FabricateController(sysMock_, new String[] {"start=123"}, factoryMock_);
    assertEquals("fabricateXmlDir/run.marker", createFileMethod.getArg(0));
    assertEquals("fabricateXmlDir/output/result.xml", createFileMethod.getArg(1));
    assertEquals(2, createFileMethod.count());
    assertEquals("fabricateXmlDir/run.marker", deleteOnExitMethod_.getArg(0));
    assertEquals("123", new String((byte []) writeMethod.getArg(0), "UTF-8"));
    assertEquals(1, writeMethod.count());
    assertEquals("fabricateXmlDir/output", createDirsMethod.getArg(0));
    assertEquals(1, createDirsMethod.count());
    
    assertEquals(1, deleteOnExitMethod_.count());
    assertEquals("\nFabricating...\n", printlnMethod_.getArg(0));
    assertEquals("detail", printlnMethod_.getArg(1));
    assertEquals("Fabrication failed!", printlnMethod_.getArg(2));
    assertEquals(3, printlnMethod_.count());
    assertEquals(0, printTraceMethod_.count());
    
    Object[] args = writeResultMethod.getArgs(0);
    assertEquals("fabricateXmlDir/output/result.xml", args[0]);
    ResultType rt = (ResultType) args[1];
    assertNotNull(rt);
    assertEquals("fab cmd=encrypt", rt.getCommandLine());
    assertEquals("fabProject", rt.getName());
    assertEquals("other", rt.getOs());
    assertEquals("3.1", rt.getOsVersion());
    assertEquals("P0Y0M0DT0H0M0.333S", rt.getDuration().toString());
    FailureType resultFailure = rt.getFailure();
    assertSame(ft, resultFailure);
    MachineInfoType mit = rt.getMachine();
    assertNotNull(mit);
    assertEquals("cpuInfo1", mit.getCpuName());
    assertEquals("cpuInfo2", mit.getCpuSpeed());
    assertNull(rt.getTests());
  }
  
  
}

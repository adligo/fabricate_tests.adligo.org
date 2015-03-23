package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.system.BufferedInputStream;
import org.adligo.fabricate.common.system.Executor;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.I_ExecutingProcess;
import org.adligo.fabricate.common.system.I_ExecutionResult;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.I_ProcessBuilderWrapper;
import org.adligo.fabricate.common.system.ProcessBuilderWrapper;
import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.fabricate.models.common.I_ExecutionEnvironment;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@SourceFileScope (sourceClass=Executor.class,minCoverage=80.0)
public class ExecutorTrial extends MockitoSourceFileTrial {
  private I_ExecutionEnvironment envMock_;
  private MockMethod<Void> addAllToMethod_;
  private I_ProcessBuilderWrapper pbMock_;
  
  private MockMethod<I_ProcessBuilderWrapper> redirectErrorStreamMethod_;
  private MockMethod<I_ProcessBuilderWrapper> directoryMethod_;
  private Process procMock_;
  private Map<String,String> procEnv_;
  
  @SuppressWarnings("unchecked")
  @Override
  public void beforeTests() {
    try {
      envMock_ = mock(I_ExecutionEnvironment.class);
      addAllToMethod_ = new MockMethod<Void>();
      
      
      pbMock_ = mock(ProcessBuilderWrapper.class);
      redirectErrorStreamMethod_ = 
          new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
      directoryMethod_ = 
          new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
      procMock_ = mock(Process.class);
      when(pbMock_.redirectErrorStream(true)).then(redirectErrorStreamMethod_);
      
      when(pbMock_.directory(any())).then(directoryMethod_);
      
      when(pbMock_.start()).thenReturn(procMock_);
      procEnv_ = mock(Map.class);
      when(pbMock_.environment()).thenReturn(procEnv_);
      doAnswer(addAllToMethod_).when(envMock_).addAllTo(procEnv_);
      
    } catch (Exception x) {
      throw new RuntimeException(x);
    }
  }
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessWhichThrowsAInterruptedException() throws Exception {
   
    when(procMock_.waitFor()).thenThrow(new InterruptedException("ie"));
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<I_ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    Thread threadMock = mock(Thread.class);
    
    MockMethod<Void> interrupMethod = new MockMethod<Void>(new I_ReturnFactory<Void>() {
      @Override
      public Void create(Object[] keys)  {
        throw new RuntimeException("oi");
      }
    });
    doAnswer(interrupMethod).when(threadMock).interrupt();
    
    I_FabFileIO fileMock = mock(I_FabFileIO.class);
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("absPath");
    when(fileMock.instance(".")).thenReturn(dirMock);
    when(sysMock.getFileIO()).thenReturn(fileMock);
    
    File dirMock2 = mock(File.class);
    when(fileMock.instance("absPath")).thenReturn(dirMock2);
    
    when(sysMock.currentThread()).thenReturn(threadMock);
    ExecutorMock exe = new ExecutorMock(sysMock);
    assertThrown(new ExpectedThrowable(new RuntimeException("oi")), 
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            exe.executeProcess(envMock_, 
                ".", "echo","foo");
          }
        });
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod_.count());
    assertEquals(1, addAllToMethod_.count());
    assertTrue((Boolean) redirectErrorStreamMethod_.getArg(0));
    assertEquals(1, directoryMethod_.count());
    assertSame(dirMock2, (File) directoryMethod_.getArg(0));
    assertEquals(1, interrupMethod.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessWhichThrowsAIOException() throws Exception {
   
    when(pbMock_.start()).thenReturn(procMock_);
    MockMethod<Integer> waitForMethod = new MockMethod<Integer>(1);
    when(procMock_.waitFor()).then(waitForMethod);
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<I_ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    
    InputStream in = mock(InputStream.class);
    when(procMock_.getInputStream()).thenReturn(in);
    
    BufferedInputStream bisMock = mock(BufferedInputStream.class);
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(bisMock).close();
    
    when(bisMock.readLine()).thenThrow(new IOException("xyz"));
    when(sysMock.newBufferedInputStream(in)).thenReturn(bisMock);
    
    I_FabFileIO fileMock = mock(I_FabFileIO.class);
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("absPath");
    when(fileMock.instance(".")).thenReturn(dirMock);
    when(sysMock.getFileIO()).thenReturn(fileMock);
    
    File dirMock2 = mock(File.class);
    when(fileMock.instance("absPath")).thenReturn(dirMock2);
    
    ExecutorMock exe = new ExecutorMock(sysMock);
    assertThrown(new ExpectedThrowable(new IOException("xyz")), 
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            exe.executeProcess(envMock_, 
                ".", "echo","foo");
          }
        });
    
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod_.count());
    assertEquals(1, addAllToMethod_.count());
    assertTrue((Boolean) redirectErrorStreamMethod_.getArg(0));
    assertEquals(1, waitForMethod.count());
    assertEquals(1, directoryMethod_.count());
    assertSame(dirMock2,(File) directoryMethod_.getArg(0));
    assertEquals(1, closeMethod.count());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessMockIO() throws Exception {

    when(pbMock_.start()).thenReturn(procMock_);
    MockMethod<Integer> waitForMethod = new MockMethod<Integer>(1);
    when(procMock_.waitFor()).then(waitForMethod);
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<I_ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    
    InputStream in = mock(InputStream.class);
    when(procMock_.getInputStream()).thenReturn(in);
    
    when(sysMock.lineSeparator()).thenReturn(System.lineSeparator());
    String output = "hey" + System.lineSeparator() +
        "you " + System.lineSeparator() + 
        "guys";
    ByteArrayInputStream baos = new ByteArrayInputStream(
        new String(output).getBytes());
    BufferedInputStream bir = new BufferedInputStream(baos);
    when(sysMock.newBufferedInputStream(in)).thenReturn(bir);
    
    I_FabFileIO fileMock = mock(I_FabFileIO.class);
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("absPath");
    when(fileMock.instance(".")).thenReturn(dirMock);
    
    File dirMock2 = mock(File.class);
    when(fileMock.instance("absPath")).thenReturn(dirMock2);
    
    when(sysMock.getFileIO()).thenReturn(fileMock);
    
    ExecutorMock exe = new ExecutorMock(sysMock);
    I_ExecutionResult er = exe.executeProcess(envMock_, 
        ".", "echo","foo");
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod_.count());
    assertEquals(1, addAllToMethod_.count());
    assertTrue((Boolean) redirectErrorStreamMethod_.getArg(0));
    assertEquals(1, waitForMethod.count());
    assertEquals(1, directoryMethod_.count());
    assertSame(dirMock2,  (File) directoryMethod_.getArg(0));
    
    assertEquals(output + System.lineSeparator(), er.getOutput());
    assertEquals(0, er.getExitCode());
    assertTrue(bir.isClosedDelegate());
    
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessRealIO() throws Exception {
    FabSystem sys = new FabSystem();
    ExecutorMock exe = new ExecutorMock(sys);
    I_ExecutionResult er = exe.executeProcess(FabricationMemoryConstants.EMPTY_ENV, 
        ".", "echo","foo");
    
    String out = er.getOutput();
    assertTrue(out.contains("foo"));
    assertEquals(0, er.getExitCode());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodStartProcessMockIO() throws Exception {

    when(pbMock_.start()).thenReturn(procMock_);
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<I_ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    
    InputStream in = mock(InputStream.class);
    when(procMock_.getInputStream()).thenReturn(in);
    
    when(sysMock.lineSeparator()).thenReturn(System.lineSeparator());
    String output = "hey" + System.lineSeparator() +
        "you " + System.lineSeparator() + 
        "guys";
    ByteArrayInputStream baos = new ByteArrayInputStream(
        new String(output).getBytes());
    BufferedInputStream bir = new BufferedInputStream(baos);
    when(sysMock.newBufferedInputStream(in)).thenReturn(bir);
    
    I_FabFileIO fileMock = mock(I_FabFileIO.class);
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("absPath");
    when(fileMock.instance(".")).thenReturn(dirMock);
    
    File dirMock2 = mock(File.class);
    when(fileMock.instance("absPath")).thenReturn(dirMock2);
    
    when(sysMock.getFileIO()).thenReturn(fileMock);
    
    I_ExecutingProcess epMock = mock(I_ExecutingProcess.class);
    when(sysMock.newExecutingProcess(procMock_)).thenReturn(epMock);
    
    ExecutorService mockService = mock(ExecutorService.class);
    MockMethod<Void> executeMethod = new MockMethod<Void>();
    doAnswer(executeMethod).when(mockService).execute(any());
    
    ExecutorMock exe = new ExecutorMock(sysMock);
    I_ExecutingProcess ep = exe.startProcess(envMock_, mockService, 
        ".", "echo","foo");
    assertSame(epMock, ep);
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod_.count());
    assertEquals(1, addAllToMethod_.count());
    assertTrue((Boolean) redirectErrorStreamMethod_.getArg(0));
    assertEquals(1, directoryMethod_.count());
    assertSame(dirMock2,  (File) directoryMethod_.getArg(0));
    assertSame(epMock, executeMethod.getArg(0));
    assertEquals(1, executeMethod.count());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodStartProcessMockIOEmptyDir() throws Exception {

    when(pbMock_.start()).thenReturn(procMock_);
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<I_ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<I_ProcessBuilderWrapper>(pbMock_);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    
    InputStream in = mock(InputStream.class);
    when(procMock_.getInputStream()).thenReturn(in);
    
    when(sysMock.lineSeparator()).thenReturn(System.lineSeparator());
    String output = "hey" + System.lineSeparator() +
        "you " + System.lineSeparator() + 
        "guys";
    ByteArrayInputStream baos = new ByteArrayInputStream(
        new String(output).getBytes());
    BufferedInputStream bir = new BufferedInputStream(baos);
    when(sysMock.newBufferedInputStream(in)).thenReturn(bir);
    
    I_FabFileIO fileMock = mock(I_FabFileIO.class);
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("absPath");
    when(fileMock.instance(".")).thenReturn(dirMock);
    
    File dirMock2 = mock(File.class);
    when(fileMock.instance("absPath")).thenReturn(dirMock2);
    
    when(sysMock.getFileIO()).thenReturn(fileMock);
    
    I_ExecutingProcess epMock = mock(I_ExecutingProcess.class);
    when(sysMock.newExecutingProcess(procMock_)).thenReturn(epMock);
    
    ExecutorService mockService = mock(ExecutorService.class);
    MockMethod<Void> executeMethod = new MockMethod<Void>();
    doAnswer(executeMethod).when(mockService).execute(any());
    
    ExecutorMock exe = new ExecutorMock(sysMock);
    I_ExecutingProcess ep = exe.startProcess(envMock_, mockService, 
        "", "echo","foo");
    assertSame(epMock, ep);
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod_.count());
    assertEquals(1, addAllToMethod_.count());
    assertTrue((Boolean) redirectErrorStreamMethod_.getArg(0));
    assertEquals(1, directoryMethod_.count());
    assertSame(dirMock,  (File) directoryMethod_.getArg(0));
    assertSame(epMock, executeMethod.getArg(0));
    assertEquals(1, executeMethod.count());
    
  }
}

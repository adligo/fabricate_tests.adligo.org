package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.system.BufferedInputStream;
import org.adligo.fabricate.common.system.Executor;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.I_ExecutionResult;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.ProcessBuilderWrapper;
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

@SourceFileScope (sourceClass=Executor.class,minCoverage=73.0)
public class ExecutorTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessWhichThrowsAInterruptedException() throws Exception {
    ProcessBuilderWrapper pbMock = mock(ProcessBuilderWrapper.class);
    
    MockMethod<ProcessBuilderWrapper> redirectErrorStreamMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(pbMock.redirectErrorStream(true)).then(redirectErrorStreamMethod);
    MockMethod<ProcessBuilderWrapper> directoryMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(pbMock.directory(any())).then(directoryMethod);
    
    
    Process procMock = mock(Process.class);
    when(pbMock.start()).thenReturn(procMock);
    when(procMock.waitFor()).thenThrow(new InterruptedException("ie"));
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
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
            exe.executeProcess(".", "echo","foo");
          }
        });
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod.count());
    assertTrue((Boolean) redirectErrorStreamMethod.getArg(0));
    assertEquals(1, directoryMethod.count());
    assertSame(dirMock2, (File) directoryMethod.getArg(0));
    assertEquals(1, interrupMethod.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessWhichThrowsAIOException() throws Exception {
    ProcessBuilderWrapper pbMock = mock(ProcessBuilderWrapper.class);
    
    MockMethod<ProcessBuilderWrapper> redirectErrorStreamMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(pbMock.redirectErrorStream(true)).then(redirectErrorStreamMethod);
    MockMethod<ProcessBuilderWrapper> directoryMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(pbMock.directory(any())).then(directoryMethod);
    
    
    Process procMock = mock(Process.class);
    when(pbMock.start()).thenReturn(procMock);
    MockMethod<Integer> waitForMethod = new MockMethod<Integer>(1);
    when(procMock.waitFor()).then(waitForMethod);
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    
    InputStream in = mock(InputStream.class);
    when(procMock.getInputStream()).thenReturn(in);
    
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
            exe.executeProcess(".", "echo","foo");
          }
        });
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod.count());
    assertTrue((Boolean) redirectErrorStreamMethod.getArg(0));
    assertEquals(1, waitForMethod.count());
    assertEquals(1, directoryMethod.count());
    assertSame(dirMock2,(File) directoryMethod.getArg(0));
    assertEquals(1, closeMethod.count());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessMockIO() throws Exception {
    ProcessBuilderWrapper pbMock = mock(ProcessBuilderWrapper.class);
    
    MockMethod<ProcessBuilderWrapper> redirectErrorStreamMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(pbMock.redirectErrorStream(true)).then(redirectErrorStreamMethod);
    MockMethod<ProcessBuilderWrapper> directoryMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(pbMock.directory(any())).then(directoryMethod);
    
    Process procMock = mock(Process.class);
    when(pbMock.start()).thenReturn(procMock);
    MockMethod<Integer> waitForMethod = new MockMethod<Integer>(1);
    when(procMock.waitFor()).then(waitForMethod);
    
    I_FabSystem sysMock = mock(I_FabSystem.class);
    MockMethod<ProcessBuilderWrapper> newProcessBuilderMethod = 
        new MockMethod<ProcessBuilderWrapper>(pbMock);
    when(sysMock.newProcessBuilder(any())).then(newProcessBuilderMethod);
    
    InputStream in = mock(InputStream.class);
    when(procMock.getInputStream()).thenReturn(in);
    
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
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
    I_ExecutionResult er = exe.executeProcess(".", "echo","foo");
    
    assertEquals(1, newProcessBuilderMethod.count());
    assertEquals(1, redirectErrorStreamMethod.count());
    assertTrue((Boolean) redirectErrorStreamMethod.getArg(0));
    assertEquals(1, waitForMethod.count());
    assertEquals(1, directoryMethod.count());
    assertSame(dirMock2,  (File) directoryMethod.getArg(0));
    
    assertEquals(output + System.lineSeparator(), er.getOutput());
    assertEquals(0, er.getExitCode());
    assertTrue(bir.isClosedDelegate());
    
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodExecuteProcessRealIO() throws Exception {
    FabSystem sys = new FabSystem();
    ExecutorMock exe = new ExecutorMock(sys);
    I_ExecutionResult er = exe.executeProcess(".", "echo","foo");
    
    String out = er.getOutput();
    assertTrue(out.contains("foo"));
    assertEquals(0, er.getExitCode());
  }
}

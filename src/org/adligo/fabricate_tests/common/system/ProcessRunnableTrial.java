package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.I_Method;
import org.adligo.fabricate.common.system.ProcessOutputData;
import org.adligo.fabricate.common.system.ProcessRunnable;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@SourceFileScope (sourceClass=ProcessRunnable.class, minCoverage=81.00)
public class ProcessRunnableTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  private I_FabFileIO filesMock_;
  private MockMethod<Void> printlnMethod = new MockMethod<Void>();
  private Process processMock_;
  private ConcurrentLinkedQueue<String> processLinesOut_;
  private ConcurrentLinkedQueue<ProcessOutputData> processLinesIn_;
  private InputStream processOutput_;
  private OutputStream processInput_;
  
  private ExecutorService serviceMock_;
  private I_Method mockMethod_;
  
  @SuppressWarnings("unchecked")
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.lineSeparator()).thenReturn("\n");
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    logMock_ = mock(I_FabLog.class);
    printlnMethod.clear();
    
    filesMock_ = mock(I_FabFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(filesMock_);
    when(filesMock_.getNameSeparator()).thenReturn("/");
    doAnswer(printlnMethod).when(logMock_).println(any());
    serviceMock_ = mock(ExecutorService.class);
    when(sysMock_.newFixedThreadPool(1)).thenReturn(serviceMock_);
    processMock_ = mock(Process.class);
    
    processLinesOut_ = mock(ConcurrentLinkedQueue.class);
    processLinesIn_ = mock(ConcurrentLinkedQueue.class);    
    
    when(sysMock_.newConcurrentLinkedQueue(String.class)).thenReturn(processLinesOut_);
    when(sysMock_.newConcurrentLinkedQueue(ProcessOutputData.class)).thenReturn(processLinesIn_);
    

    processOutput_ = mock(InputStream.class);
    processInput_ = mock(OutputStream.class);
    when(processMock_.getInputStream()).thenReturn(processOutput_);
    when(processMock_.getOutputStream()).thenReturn(processInput_);
    
    mockMethod_ = mock(I_Method.class);
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodsRunOutputOnly() throws Exception {
    
    MockMethod<Integer> availableMethod = new MockMethod<Integer>(1,1,1,0);
    doAnswer(availableMethod).when(processOutput_).available();
    addWriteAbc();

    MockMethod<Boolean> addMethod = new MockMethod<Boolean>(true, true);
    doAnswer(addMethod).when(processLinesOut_).add(any());
    
    MockMethod<Integer> exitCodeMethod = new MockMethod<Integer>(0, true);
    doAnswer(exitCodeMethod).when(processMock_).exitValue();
    
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(processOutput_).close();
    
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(processInput_).close();
    
    ProcessRunnable pr = new ProcessRunnable(sysMock_, processMock_, null);
    
    pr.run();
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals("abc", addMethod.getArg(0));
    assertEquals(1,  addMethod.count());
    assertEquals(0, pr.getExitCode());
    
    MockMethod<String> pollMethod = new MockMethod<String>("abc", false);
    when(processLinesOut_.poll()).then(pollMethod);
    
    List<String> out = pr.getOutput();
    assertContains(out, "abc");
    assertEquals(1, out.size());
    
    closeInMethod.clear();
    closeOutMethod.clear();
    addMethod.clear();
    reset(processOutput_);
    doAnswer(closeOutMethod).when(processOutput_).close();
    addWriteAbcN123RAbcd();
    pr.run();

    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals("abc", addMethod.getArg(0));
    assertEquals("123", addMethod.getArg(1));
    assertEquals("abcd", addMethod.getArg(2));
    assertEquals(3,  addMethod.count());
    assertEquals(0, pr.getExitCode());
  }
  
  @SuppressWarnings("boxing")
  public void addWriteAbc() throws UnsupportedEncodingException, IOException {
    MockMethod<Integer> writeMethod = new MockMethod<Integer>(new I_ReturnFactory<Integer>() {
      int index = 0;
      byte [] abc_ = "abc".getBytes("ASCII");
      
      @Override
      public Integer create(Object[] keys) {
        byte [] bytes = (byte []) keys[0];
        if (index < 3) {
          bytes[0] = abc_[index++];
          return 1;
        } else {
          return 0;
        }
      }
      
    }, false);
    doAnswer(writeMethod).when(processOutput_).read(any(byte[].class));
  }
  
  @SuppressWarnings("boxing")
  public void addWriteAbcN123RAbcd() throws UnsupportedEncodingException, IOException {
    MockMethod<Integer> writeMethod = new MockMethod<Integer>(new I_ReturnFactory<Integer>() {
      int index = 0;
      byte [] abc_ = "abc\n123\r\nabcd".getBytes("ASCII");
      
      @Override
      public Integer create(Object[] keys) {
        byte [] bytes = (byte []) keys[0];
        if (index < 13) {
          bytes[0] = abc_[index++];
          return 1;
        } else {
          return 0;
        }
      }
      
    }, false);
    doAnswer(writeMethod).when(processOutput_).read(any(byte[].class));
    
    MockMethod<Integer> availableMethod = new MockMethod<Integer>(new I_ReturnFactory<Integer>() {
      int index = 0;
      
      @Override
      public Integer create(Object[] keys) {
        while(index++ < 13) {
          return 1;
        }
        return 0;
      }
      
    }, false);
    doAnswer(availableMethod).when(processOutput_).available();
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsRunAvailableAndReadIoExceptions() throws Exception {
    
    MockMethod<Boolean> addMethod = new MockMethod<Boolean>(true, true);
    doAnswer(addMethod).when(processLinesOut_).add(any());
    
    MockMethod<Integer> exitCodeMethod = new MockMethod<Integer>(0, true);
    doAnswer(exitCodeMethod).when(processMock_).exitValue();
    
    IOException x = new IOException("x");
    doThrow(x).when(processOutput_).available();
    
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(processOutput_).close();
    
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(processInput_).close();
    
    ProcessRunnable pr = new ProcessRunnable(sysMock_, processMock_, null);
    assertThrown(new ExpectedThrowable(new RuntimeException(new IOException("x"))),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pr.run();
          }
        });
    
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals(0,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
    
    reset(processOutput_);
    closeOutMethod.clear();
    closeInMethod.clear();
    doAnswer(closeOutMethod).when(processOutput_).close();
    
    MockMethod<Integer> availableMethod = new MockMethod<Integer>(1,1,1,0);
    doAnswer(availableMethod).when(processOutput_).available();
    
    IOException x1 = new IOException("x1");
    doThrow(x1).when(processOutput_).read(any(byte[].class));
    
    assertThrown(new ExpectedThrowable(new RuntimeException(new IOException("x1"))),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pr.run();
          }
        });
    
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals(0,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
  }
  
  
  @SuppressWarnings("boxing")
  public void testMethodsRunWaitForIllegalAccessArgumenAndInvocationTargetExceptions() throws Exception {
    
    MockMethod<Boolean> addMethod = new MockMethod<Boolean>(true, true);
    doAnswer(addMethod).when(processLinesOut_).add(any());
    
    MockMethod<Integer> exitCodeMethod = new MockMethod<Integer>(0, true);
    doAnswer(exitCodeMethod).when(processMock_).exitValue();
    addWriteAbc();
    
    IOException x = new IOException("x");
    doThrow(x).when(processOutput_).available();
    
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(processOutput_).close();
    
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(processInput_).close();
    
    IllegalAccessException iae = new IllegalAccessException("iae");
    doThrow(iae).when(mockMethod_).invoke(processMock_, 1000L, TimeUnit.MILLISECONDS);
    
    ProcessRunnable pr = new ProcessRunnable(sysMock_, processMock_, null);
    pr.run();
    
    assertEquals("abc", addMethod.getArg(0));
    assertEquals(1, addMethod.count());
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals(0,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
    
    IllegalArgumentException iae2 = new IllegalArgumentException("iae2");
    doThrow(iae2).when(mockMethod_).invoke(processMock_, 1000L, TimeUnit.MILLISECONDS);
    
    pr.run();
    
    assertEquals("abc", addMethod.getArg(0));
    assertEquals(1, addMethod.count());
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals(0,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
    
    InvocationTargetException ite = mock(InvocationTargetException.class);
    doThrow(ite).when(mockMethod_).invoke(processMock_, 1000L, TimeUnit.MILLISECONDS);
    
    pr.run();
    
    assertEquals("abc", addMethod.getArg(0));
    assertEquals(1, addMethod.count());
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals(0,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsRunAndWrite() throws Exception {
    
    MockMethod<Integer> availableMethod = new MockMethod<Integer>(1,1,1,0);
    doAnswer(availableMethod).when(processOutput_).available();
    addWriteAbc();

    MockMethod<Boolean> addMethod = new MockMethod<Boolean>(true, true);
    doAnswer(addMethod).when(processLinesOut_).add(any());
    
    MockMethod<Integer> exitCodeMethod = new MockMethod<Integer>(0, true);
    doAnswer(exitCodeMethod).when(processMock_).exitValue();
    
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(processOutput_).close();
    
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(processInput_).close();
    
    ProcessRunnable pr = new ProcessRunnable(sysMock_, processMock_, null);
    
    pr.writeInputToProcess("123", "UTF-8");
    pr.run();

    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals("abc", addMethod.getArg(0));
    assertEquals(1,  addMethod.count());
    assertEquals(0, pr.getExitCode());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsRunAndWriteFlushIoExceptions() throws Exception {
    
    MockMethod<Boolean> addMethod = new MockMethod<Boolean>(true, true);
    doAnswer(addMethod).when(processLinesOut_).add(any());
    
    MockMethod<Integer> exitCodeMethod = new MockMethod<Integer>(0, true);
    doAnswer(exitCodeMethod).when(processMock_).exitValue();
    
    MockMethod<Integer> availableMethod = new MockMethod<Integer>(1,1,1,0);
    doAnswer(availableMethod).when(processOutput_).available();
    addWriteAbc();
    
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(processOutput_).close();
    
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(processInput_).close();
    
    ProcessRunnable pr = new ProcessRunnable(sysMock_, processMock_, null);
    MockMethod<ProcessOutputData> pollMethod = new MockMethod<ProcessOutputData>(
        new ProcessOutputData("haha","UTF-8"), false);
    doAnswer(pollMethod).when(processLinesIn_).poll();

    IOException x = new IOException("x");
    doThrow(x).when(processInput_).write(any(byte[].class));
    
    assertThrown(new ExpectedThrowable(new RuntimeException(new IOException("x"))),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pr.run();
          }
        });
    
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals("abc",  addMethod.getArg(0));
    assertEquals(1,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
    
    reset(processInput_);
    doAnswer(closeInMethod).when(processInput_).close();
    IOException x1 = new IOException("x1");
    doThrow(x1).when(processInput_).flush();
    
    pollMethod = new MockMethod<ProcessOutputData>(
        new ProcessOutputData("haha","UTF-8"), false);
    doAnswer(pollMethod).when(processLinesIn_).poll();
    
    closeInMethod.clear();
    closeOutMethod.clear();
    addMethod.clear();
    assertThrown(new ExpectedThrowable(new RuntimeException(new IOException("x1"))),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pr.run();
          }
        });
    
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    assertEquals(0,  addMethod.count());
    assertEquals(-1, pr.getExitCode());
    
  }
}

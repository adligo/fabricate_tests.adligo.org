package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.AntHelper;
import org.adligo.fabricate.common.system.I_Executor;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.I_GitCalls;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * TODO
 * @author scott
 *
 */
@SourceFileScope (sourceClass=AntHelper.class, minCoverage=84.0)
public class AntHelperTrial extends MockitoSourceFileTrial {

  private I_FabSystem sysMock_;
  private I_FabFileIO filesMock_;
  private I_FabLog logMock_;
  private MockMethod<Void> printlnMethod_;
  private MockMethod<Void> printTraceMethod_;
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.lineSeparator()).thenReturn("\n");
    
    filesMock_ = mock(I_FabFileIO.class);
    doReturn(filesMock_).when(sysMock_).getFileIO();
    when(filesMock_.getNameSeparator()).thenReturn("/");
    
    when(sysMock_.newArrayBlockingQueue(Boolean.class, 1))
        .thenReturn(new ArrayBlockingQueue<Boolean>(1));
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeparator()).thenReturn(System.lineSeparator());
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    printlnMethod_ = new MockMethod<Void>();
    doAnswer(printlnMethod_).when(logMock_).println(any());
    printTraceMethod_ = new MockMethod<Void>();
    doAnswer(printTraceMethod_).when(logMock_).printTrace(any());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructor() throws Exception {
    I_GitCalls gitCallsMock = mock(I_GitCalls.class);
    when(sysMock_.newGitCalls()).thenReturn(gitCallsMock);
    
    I_Executor executorMock = mock(I_Executor.class);
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    MockMethod<Boolean> checkMethod = new MockMethod<Boolean>(true, true);
    when(gitCallsMock.check(executorMock)).then(checkMethod);
    
    MockMethod<String> describeMethod = new MockMethod<String>("gitDesc", true);
    when(gitCallsMock.describe()).then(describeMethod);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write((byte[]) any());
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    MockMethod<OutputStream> newFileOutputStreamMethod = new MockMethod<OutputStream>(outMock, true);
    when(filesMock_.newFileOutputStream("dir/version.properties")).then(newFileOutputStreamMethod);
    
    new AntHelper(sysMock_, new String[] {"dir"});
    assertEquals(executorMock, checkMethod.getArg(0));
    assertEquals(1, checkMethod.count());
    
    assertEquals("dir/version.properties", newFileOutputStreamMethod.getArg(0));
    assertEquals(1, newFileOutputStreamMethod.count());
    
    assertEquals("fabricate_name=fabricate_gitDesc\n", new String((byte[]) writeMethod.getArg(0), "UTF-8"));
    assertEquals("fabricate_version=gitDesc\n", new String((byte[]) writeMethod.getArg(1), "UTF-8"));
    assertEquals(2, writeMethod.count());
    
    assertEquals(1, closeMethod.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorNoDirectoryArg() {
    new AntHelper(sysMock_, null);
    assertEquals("AntHelper requires a directory argument.", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
    new AntHelper(sysMock_, new String[] {});
    assertEquals("AntHelper requires a directory argument.", printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorGitCheckIOException() throws Exception {
    I_GitCalls gitCallsMock = mock(I_GitCalls.class);
    when(sysMock_.newGitCalls()).thenReturn(gitCallsMock);
    
    I_Executor executorMock = mock(I_Executor.class);
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    IOException checkIo = new IOException("checkIo");
    doThrow(checkIo).when(gitCallsMock).check(executorMock);
    
    MockMethod<String> describeMethod = new MockMethod<String>("gitDesc", true);
    when(gitCallsMock.describe()).then(describeMethod);
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write((byte[]) any());
    
    
    MockMethod<OutputStream> newFileOutputStreamMethod = new MockMethod<OutputStream>(outMock, true);
    when(filesMock_.newFileOutputStream("dir/version.properties")).then(newFileOutputStreamMethod);
    
    new AntHelper(sysMock_, new String[] {"dir"});
    
    assertEquals(0, newFileOutputStreamMethod.count());
    assertEquals(0, writeMethod.count());
    assertEquals(checkIo, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
  }
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorGitDescribeIOException() throws Exception {
    I_GitCalls gitCallsMock = mock(I_GitCalls.class);
    when(sysMock_.newGitCalls()).thenReturn(gitCallsMock);
    
    I_Executor executorMock = mock(I_Executor.class);
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    doReturn(true).when(gitCallsMock).check(executorMock);
    
    MockMethod<String> describeMethod = new MockMethod<String>("gitDesc", true);

    IOException descIo = new IOException("descIo");
    doThrow(descIo).when(gitCallsMock).describe();
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write((byte[]) any());
    
    
    MockMethod<OutputStream> newFileOutputStreamMethod = new MockMethod<OutputStream>(outMock, true);
    when(filesMock_.newFileOutputStream("dir/version.properties")).then(newFileOutputStreamMethod);
    
    new AntHelper(sysMock_, new String[] {"dir"});
    
    assertEquals(0, newFileOutputStreamMethod.count());
    assertEquals(0, writeMethod.count());
    assertEquals(descIo, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
  }
  
  
  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorGitNotInstalled() throws Exception {
    I_GitCalls gitCallsMock = mock(I_GitCalls.class);
    when(sysMock_.newGitCalls()).thenReturn(gitCallsMock);
    
    I_Executor executorMock = mock(I_Executor.class);
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    doReturn(false).when(gitCallsMock).check(executorMock);
    
    MockMethod<String> describeMethod = new MockMethod<String>("gitDesc", true);

    doReturn("desc").when(gitCallsMock).describe();
    
    OutputStream outMock = mock(OutputStream.class);
    MockMethod<Void> writeMethod = new MockMethod<Void>();
    doAnswer(writeMethod).when(outMock).write((byte[]) any());
    
    
    MockMethod<OutputStream> newFileOutputStreamMethod = new MockMethod<OutputStream>(outMock, true);
    when(filesMock_.newFileOutputStream("dir/version.properties")).then(newFileOutputStreamMethod);
    
    new AntHelper(sysMock_, new String[] {"dir"});
    
    assertEquals(0, newFileOutputStreamMethod.count());
    assertEquals(0, writeMethod.count());
    assertEquals("Git does not appear to be installed please install it.", printlnMethod_.getArg(0));
    assertEquals(1, printlnMethod_.count());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorWriteIOException() throws Exception {
    I_GitCalls gitCallsMock = mock(I_GitCalls.class);
    when(sysMock_.newGitCalls()).thenReturn(gitCallsMock);
    
    I_Executor executorMock = mock(I_Executor.class);
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    MockMethod<Boolean> checkMethod = new MockMethod<Boolean>(true, true);
    when(gitCallsMock.check(executorMock)).then(checkMethod);
    
    MockMethod<String> describeMethod = new MockMethod<String>("gitDesc", true);
    when(gitCallsMock.describe()).then(describeMethod);
    
    OutputStream outMock = mock(OutputStream.class);
    IOException writeIo = new IOException("writeIo");
    doThrow(writeIo).when(outMock).write((byte[]) any());
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(outMock).close();
    
    MockMethod<OutputStream> newFileOutputStreamMethod = new MockMethod<OutputStream>(outMock, true);
    when(filesMock_.newFileOutputStream("dir/version.properties")).then(newFileOutputStreamMethod);
    
    new AntHelper(sysMock_, new String[] {"dir"});
    assertEquals(executorMock, checkMethod.getArg(0));
    assertEquals(1, checkMethod.count());
    
    assertEquals("dir/version.properties", newFileOutputStreamMethod.getArg(0));
    assertEquals(1, newFileOutputStreamMethod.count());
    
    assertEquals(writeIo, printTraceMethod_.getArg(0));
    assertEquals(1, printTraceMethod_.count());
    
    assertEquals(1, closeMethod.count());
  }
  
  @Test
  public void testMainPassthroughNoDirectoryArg() {
    AntHelper.main(null);
    AntHelper.main(new String[] {});
  }
}

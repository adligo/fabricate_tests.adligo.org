package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.GitCalls;
import org.adligo.fabricate.common.system.I_ExecutingProcess;
import org.adligo.fabricate.common.system.I_ExecutionResult;
import org.adligo.fabricate.common.system.I_Executor;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.fabricate.models.common.I_ExecutionEnvironment;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.BeforeTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * TODO
 * @author scott
 *
 */
@SourceFileScope (sourceClass=GitCalls.class, minCoverage=97.0)
public class GitCallsTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  private I_FabFileIO filesMock_;
  private MockMethod<Void> printlnMethod = new MockMethod<Void>();
  private ExecutorService serviceMock_;
  
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
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorSetsAndGets() {
    
    
    GitCalls gc = new GitCalls(sysMock_);
    gc.setHostname("hostname");
    assertEquals("hostname", gc.getHostname());
    
    gc.setPort(11);
    assertEquals(11, gc.getPort());
    
    gc.setProtocol("protocol");
    assertEquals("protocol", gc.getProtocol());
    
    gc.setRemotePath("remotePath");
    assertEquals("remotePath", gc.getRemotePath());
    
    gc.setUser("user"); 
    assertEquals("user", gc.getUser());
  }
  
  @Test
  public void testMethodCheck() throws IOException {
    I_Executor mockExecutor = mock(I_Executor.class);
    I_ExecutionResult result = mock(I_ExecutionResult.class);
    
    GitCalls gc = new GitCalls(sysMock_);
    when(mockExecutor.executeProcess(FabricationMemoryConstants.EMPTY_ENV,
        ".", "git", "--version")).thenReturn(result);
    
    assertThrown(new ExpectedThrowable(new IOException(), MatchType.NULL),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            gc.check(mockExecutor);
          }
        });
    when(result.getOutput()).thenReturn("\t");
    assertThrown(new ExpectedThrowable(new IOException("\t")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            gc.check(mockExecutor);
          }
        });
    
    when(result.getOutput()).thenReturn("git version 1.9.2 (Apple Git-50)");
    assertThrown(new ExpectedThrowable(new IOException(
        "This version of Fabricate requires Git 1.9.3 or greater, and 'git --version' returned the following;" +
        "\ngit version 1.9.2 (Apple Git-50)")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            gc.check(mockExecutor);
          }
        });
    when(result.getOutput()).thenReturn("error:");
    assertThrown(new ExpectedThrowable(new IOException(
        "error:")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            gc.check(mockExecutor);
          }
        });
    
    when(result.getOutput()).thenReturn("git version 1.9.3 (Apple Git-50)");
    gc.check(mockExecutor);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodClone() throws Exception {
    I_Executor executorMock = mock(I_Executor.class);
    I_ExecutingProcess proc = mock(I_ExecutingProcess.class);
    I_ExecutionEnvironment ee = mock(I_ExecutionEnvironment.class);
    MockMethod<Void> addAllToMethod = new MockMethod<Void>();
    doAnswer(addAllToMethod).when(ee).addAllTo(any());
    
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    MockMethod<I_ExecutingProcess> startProcessMethod = new MockMethod<I_ExecutingProcess>(proc, true);
    doAnswer(startProcessMethod).when(executorMock).startProcess(any(), any(), any(), anyVararg());
    
    //when
    GitCalls gc = new GitCalls(sysMock_);
    gc.setHostname("hostname");
    gc.setPort(11);
    gc.setProtocol("protocol");
    gc.setRemotePath("remotePath");
    
    I_ExecutingProcess ep = gc.clone(ee, "project", "localProjectDir");
    assertSame(proc, ep);
    Object [] startProcArgs = startProcessMethod.getArgs(0);
    assertSame(ee, startProcArgs[0]);
    assertSame(serviceMock_, startProcArgs[1]);
    assertEquals("localProjectDir", startProcArgs[2]);
    assertEquals("git", startProcArgs[3]);
    assertEquals("clone", startProcArgs[4]);
    assertEquals("protocol://hostname:11remotePathproject", startProcArgs[5]);
    assertEquals(6, startProcArgs.length);
    
    gc.setUser("user");
    startProcessMethod.clear();
    ep = gc.clone(ee, "project", "localProjectDir");
    assertSame(proc, ep);
    startProcArgs = startProcessMethod.getArgs(0);
    assertSame(ee, startProcArgs[0]);
    assertSame(serviceMock_, startProcArgs[1]);
    assertEquals("localProjectDir", startProcArgs[2]);
    assertEquals("git", startProcArgs[3]);
    assertEquals("clone", startProcArgs[4]);
    assertEquals("protocol://user@hostname:11remotePathproject", startProcArgs[5]);
    assertEquals(6, startProcArgs.length);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodCheckout() throws Exception {
    I_Executor executorMock = mock(I_Executor.class);
    I_ExecutionResult result = mock(I_ExecutionResult.class);
    
    MockMethod<I_ExecutionResult> executeProcessMethod = new MockMethod<I_ExecutionResult>(result, true);
    doAnswer(executeProcessMethod).when(executorMock).executeProcess(any(), any(),  anyVararg());
    
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    GitCalls gc = new GitCalls(sysMock_);
    assertThrown(new ExpectedThrowable(new IOException(), MatchType.NULL), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            gc.checkout("project", "localProjectDir", "version");
          }
        });
    Object [] executeArgs = executeProcessMethod.getArgs(0);
    assertSame(FabricationMemoryConstants.EMPTY_ENV, executeArgs[0]);
    assertEquals("localProjectDir/project", executeArgs[1]);
    assertEquals("git", executeArgs[2]);
    assertEquals("checkout", executeArgs[3]);
    assertEquals("version", executeArgs[4]);
    assertEquals(5, executeArgs.length);
    assertEquals(1, executeProcessMethod.count());
    
    executeProcessMethod.clear();
    
    when(result.getOutput()).thenReturn("error:");
    assertThrown(new ExpectedThrowable(new IOException("error:")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            gc.checkout("project", "localProjectDir", "version");
          }
        });
    
    executeProcessMethod.clear();
    when(result.getOutput()).thenReturn("Previous HEAD position was f1be130... " +
        "This commit was manufactured by cvs2svn to create tag 'v1_2final'.\n" +
        "Switched to branch 'trunk'\n" +
        "Your branch is up-to-date with 'origin/trunk'.\n");
    gc.checkout("project", "localProjectDir", "version");
    assertEquals("localProjectDir/project", executeArgs[1]);
    assertEquals("git", executeArgs[2]);
    assertEquals("checkout", executeArgs[3]);
    assertEquals("version", executeArgs[4]);
    assertEquals(5, executeArgs.length);
    assertEquals(1, executeProcessMethod.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodDescribe() throws Exception {
    I_Executor executorMock = mock(I_Executor.class);
    I_ExecutionResult result = mock(I_ExecutionResult.class);
    
    MockMethod<I_ExecutionResult> executeProcessMethod = new MockMethod<I_ExecutionResult>(result, true);
    doAnswer(executeProcessMethod).when(executorMock).executeProcess(any(), any(),  anyVararg());
    
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    
    GitCalls gc = new GitCalls(sysMock_);
    String version = gc.describe("localProjectDir/project");
    assertEquals("snapshot", version);
    Object [] executeArgs = executeProcessMethod.getArgs(0);
    assertSame(FabricationMemoryConstants.EMPTY_ENV, executeArgs[0]);
    assertEquals("localProjectDir/project", executeArgs[1]);
    assertEquals("git", executeArgs[2]);
    assertEquals("describe", executeArgs[3]);
    assertEquals(4, executeArgs.length);
    assertEquals(1, executeProcessMethod.count());
    
    when(result.getOutput()).thenReturn("error:");
    executeProcessMethod.clear();
    version = gc.describe("localProjectDir/project");
    assertEquals("snapshot", version);
    executeArgs = executeProcessMethod.getArgs(0);
    assertSame(FabricationMemoryConstants.EMPTY_ENV, executeArgs[0]);
    assertEquals("localProjectDir/project", executeArgs[1]);
    assertEquals("git", executeArgs[2]);
    assertEquals("describe", executeArgs[3]);
    assertEquals(4, executeArgs.length);
    assertEquals(1, executeProcessMethod.count());
    
    
    when(result.getOutput()).thenReturn("123");
    executeProcessMethod.clear();
    version = gc.describe("localProjectDir/project");
    assertEquals("123", version);
    executeArgs = executeProcessMethod.getArgs(0);
    assertSame(FabricationMemoryConstants.EMPTY_ENV, executeArgs[0]);
    assertEquals("localProjectDir/project", executeArgs[1]);
    assertEquals("git", executeArgs[2]);
    assertEquals("describe", executeArgs[3]);
    assertEquals(4, executeArgs.length);
    assertEquals(1, executeProcessMethod.count());
    
    when(result.getOutput()).thenReturn("123");
    executeProcessMethod.clear();
    version = gc.describe(".");
    assertEquals("123", version);
    executeArgs = executeProcessMethod.getArgs(0);
    assertSame(FabricationMemoryConstants.EMPTY_ENV, executeArgs[0]);
    assertEquals(".", executeArgs[1]);
    assertEquals("git", executeArgs[2]);
    assertEquals("describe", executeArgs[3]);
    assertEquals(4, executeArgs.length);
    assertEquals(1, executeProcessMethod.count());
  }
  
  @Test
  public void testMethodIsSuccess() {
    GitCalls gc = new GitCalls(sysMock_);
    assertFalse(gc.isSuccess(null));
    assertFalse(gc.isSuccess("\t"));
    assertFalse(gc.isSuccess("error:"));
    assertFalse(gc.isSuccess("fatal:"));
    assertTrue(gc.isSuccess("123"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodPull() throws Exception {
    I_Executor executorMock = mock(I_Executor.class);
    I_ExecutingProcess proc = mock(I_ExecutingProcess.class);
    I_ExecutionEnvironment ee = mock(I_ExecutionEnvironment.class);
    MockMethod<Void> addAllToMethod = new MockMethod<Void>();
    doAnswer(addAllToMethod).when(ee).addAllTo(any());
    
    when(sysMock_.getExecutor()).thenReturn(executorMock);
    MockMethod<I_ExecutingProcess> startProcessMethod = new MockMethod<I_ExecutingProcess>(proc, true);
    doAnswer(startProcessMethod).when(executorMock).startProcess(any(), any(), any(), anyVararg());
    
    //when
    GitCalls gc = new GitCalls(sysMock_);
    gc.setHostname("hostname");
    gc.setPort(11);
    gc.setProtocol("protocol");
    gc.setRemotePath("remotePath");
    
    I_ExecutingProcess ep = gc.pull(ee, "project", "localProjectDir");
    assertSame(proc, ep);
    Object [] startProcArgs = startProcessMethod.getArgs(0);
    assertSame(ee, startProcArgs[0]);
    assertSame(serviceMock_, startProcArgs[1]);
    assertEquals("localProjectDir/project", startProcArgs[2]);
    assertEquals("git", startProcArgs[3]);
    assertEquals("pull", startProcArgs[4]);
    assertEquals(5, startProcArgs.length);
    
    gc.setUser("user");
    startProcessMethod.clear();
    ep = gc.pull(ee, "project", "localProjectDir");
    assertSame(proc, ep);
    startProcArgs = startProcessMethod.getArgs(0);
    assertSame(ee, startProcArgs[0]);
    assertSame(serviceMock_, startProcArgs[1]);
    assertEquals("localProjectDir/project", startProcArgs[2]);
    assertEquals("git", startProcArgs[3]);
    assertEquals("pull", startProcArgs[4]);
    assertEquals(5, startProcArgs.length);
  }
}

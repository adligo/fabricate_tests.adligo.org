package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.ExecutingProcess;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.ProcessRunnable;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@SourceFileScope (sourceClass=ExecutingProcess.class, minCoverage=54.00, 
    allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class ExecutingProcessTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabFileIO filesMock_;
  private I_FabLog logMock_;
  private Process processMock_;
  private ProcessRunnable processRunnableMock_;
  private MockMethod<Void> runMethod_;
  private MockMethod<Void> destroyMethod_;
  private ArrayBlockingQueue<Boolean> queueMock_;
  private MockMethod<Boolean> pollMethod_;
  
  @SuppressWarnings("unchecked")
  @Override
  public void beforeTests() {
    try {
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
      
      
      processMock_ = mock(Process.class);
      processRunnableMock_ = mock(ProcessRunnable.class);
      runMethod_ = new MockMethod<Void>();
      doAnswer(runMethod_).when(processRunnableMock_).run();
      destroyMethod_ = new MockMethod<Void>();
      doAnswer(destroyMethod_).when(processRunnableMock_).destroy();
      when(sysMock_.newProcessRunnable(processMock_)).thenReturn(processRunnableMock_);
      
      queueMock_ = mock(ArrayBlockingQueue.class);
      pollMethod_ = new MockMethod<Boolean>();
      
      
   
      when(sysMock_.newArrayBlockingQueue(Boolean.class, 1)).thenReturn(queueMock_);
      
      
    
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndRun() {
    ExecutingProcess ep = new ExecutingProcess(sysMock_, processMock_);
    when(processRunnableMock_.getExitCode()).thenReturn(0);
    ep.run();
    assertEquals(1, runMethod_.count());
    assertTrue(ep.isFinished());
    assertFalse(ep.hasFailure());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndRunWaitDestroy() throws Exception {
    ExecutingProcess ep = new ExecutingProcess(sysMock_, processMock_);
    //don't actually call run here since this is a simulation
    assertEquals(0, runMethod_.count());
    when(queueMock_.poll(1000, TimeUnit.MILLISECONDS)).then(pollMethod_);
    ep.waitUntilFinished(1000);
    assertEquals(1, pollMethod_.count());
    assertFalse(ep.isFinished());
    ep.destroy();
    assertEquals(1, destroyMethod_.count());
  }
  
  @Test
  public void testConstructorAndRunWithThrown() {
    ExecutingProcess ep = new ExecutingProcess(sysMock_, processMock_);
    
    IllegalStateException x = new IllegalStateException("x");
    doThrow(x).when(processRunnableMock_).run();
    ep.run();
    assertTrue(ep.isFinished());
    assertSame(x, ep.getCaught());
    assertTrue(ep.hasFailure());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndRunExitCodeFailure() {
    ExecutingProcess ep = new ExecutingProcess(sysMock_, processMock_);
    when(processRunnableMock_.getExitCode()).thenReturn(2);
    
    ep.run();
    assertTrue(ep.isFinished());
    assertEquals(1, runMethod_.count());
    assertTrue(ep.hasFailure());
    assertEquals(2, ep.getExitCode());
  }
}

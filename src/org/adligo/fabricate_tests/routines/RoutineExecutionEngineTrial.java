package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.I_LocatableRunable;
import org.adligo.fabricate.common.system.I_RunMonitor;
import org.adligo.fabricate.common.system.RunMonitor;
import org.adligo.fabricate.models.common.I_FabricationMemory;
import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.fabricate.routines.I_RoutineBuilder;
import org.adligo.fabricate.routines.RoutineExecutionEngine;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.fabricate_tests.routines.implicit.mocks.SimpleConcurrentRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.SimpleRoutineMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@SourceFileScope (sourceClass=RoutineExecutionEngine.class, minCoverage=98.0)
public class RoutineExecutionEngineTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  private MockMethod<Void> printlnMethod_;
  private DecryptTrait encrypt_ = new DecryptTrait();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.newArrayBlockingQueue(Boolean.class, 1))
        .thenReturn(new ArrayBlockingQueue<Boolean>(1));
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeperator()).thenReturn(System.lineSeparator());
    encrypt_.setSystem(sysMock_);
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    printlnMethod_ = new MockMethod<Void>();
    doAnswer(printlnMethod_).when(logMock_).println(any());
    
  }
  
  @Test
  public void testMethodRunRoutinesNullRoutine() throws Exception {
    I_RoutineBuilder builderMock = mock(I_RoutineBuilder.class);
    
    RoutineExecutionEngine e = new RoutineExecutionEngine(sysMock_, builderMock, 0);
    e.runRoutines();
    //test no exception
  }
  
  @Test
  public void testMethodRunRoutineBasicExectuion() throws Exception {
    I_RoutineBuilder builderMock = mock(I_RoutineBuilder.class);
    SimpleRoutineMock srm = new SimpleRoutineMock();
    MockMethod<I_FabricationRoutine> buildMethod = new MockMethod<I_FabricationRoutine>(srm, true);
    when(builderMock.build(any())).then(buildMethod);
    
    doReturn(new RunMonitor(sysMock_, srm, 1)).when(sysMock_).newRunMonitor(srm, 1);
    
    RoutineExecutionEngine e = new RoutineExecutionEngine(sysMock_, builderMock, 0);
    e.runRoutines();
    assertTrue(srm.isRan());
    assertNull(srm.getLastMemoryMutant());
    assertNull(srm.getLastMemory());
    assertNotNull(buildMethod.getArg(0));
  }
  
  @Test
  public void testMethodRunRoutineConcurrentBasicExectuion() throws Exception {
    I_RoutineBuilder builderMock = mock(I_RoutineBuilder.class);
    SimpleConcurrentRoutineMock srm = new SimpleConcurrentRoutineMock();
    MockMethod<I_FabricationRoutine> buildMethod = new MockMethod<I_FabricationRoutine>(srm, true);
    when(builderMock.build(any())).then(buildMethod);
    
    doReturn(new RunMonitor(sysMock_, srm, 1)).when(sysMock_).newRunMonitor(srm, 1);
    
    RoutineExecutionEngine e = new RoutineExecutionEngine(sysMock_, builderMock, 0);
    e.runRoutines();
    assertTrue(srm.isRan());
    assertNull(srm.getLastMemoryMutant());
    assertNull(srm.getLastMemory());
    assertNotNull(buildMethod.getArg(0));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodRunRoutineConcurrentBasicExectuionFailure() throws Exception {
    I_RoutineBuilder builderMock = mock(I_RoutineBuilder.class);
    SimpleConcurrentRoutineMock srm = mock(SimpleConcurrentRoutineMock.class);
    MockMethod<I_FabricationRoutine> buildMethod = new MockMethod<I_FabricationRoutine>(srm, true);
    when(builderMock.build(any())).then(buildMethod);
    
    I_RunMonitor runMonitor = mock(I_RunMonitor.class);
    when(runMonitor.hasFailure()).thenReturn(true);
    when(runMonitor.getCaught()).thenReturn(new IllegalStateException("isExp"));
    when(runMonitor.getDelegate()).thenReturn(srm);
    MockMethod<Void> runMethod = new MockMethod<Void>();
    doAnswer(runMethod).when(runMonitor).run();
    
    when(sysMock_.newRunMonitor(any(), anyInt())).thenReturn(runMonitor);
    
    RoutineExecutionEngine e = new RoutineExecutionEngine(sysMock_, builderMock, 0);
    e.runRoutines();
    assertFalse(srm.isRan());
    assertEquals(1, runMethod.count());
    assertNull(srm.getLastMemoryMutant());
    assertNull(srm.getLastMemory());
    assertNotNull(buildMethod.getArg(0));
    
    assertTrue(e.hadFailure());
    Throwable t = e.getFailure();
    assertEquals(IllegalStateException.class.getName(), t.getClass().getName());
    assertEquals("isExp", t.getMessage());
    
    assertSame(srm, e.getRoutineThatFailed());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodRunRoutinesConcurrentExectuion() throws Exception {
    I_RoutineBuilder builderMock = mock(I_RoutineBuilder.class);
    SimpleConcurrentRoutineMock srm = new SimpleConcurrentRoutineMock();
    MockMethod<I_FabricationRoutine> buildMethod = new MockMethod<I_FabricationRoutine>(srm, true);
    when(builderMock.build(any())).then(buildMethod);
    when(builderMock.build((I_FabricationMemory) any())).then(buildMethod);
    
    ExecutorService exeServ  = mock(ExecutorService.class);
    MockMethod<Future<?>> submitMethod = new MockMethod<Future<?>>();
    doAnswer(submitMethod).when(exeServ).submit(any());
    MockMethod<ExecutorService> newFixedThreadPoolMethod = new MockMethod<ExecutorService>(exeServ, true);
    doAnswer(newFixedThreadPoolMethod).when(sysMock_).newFixedThreadPool(anyInt());
    
    I_RunMonitor monitorOne = mock(I_RunMonitor.class);
    when(monitorOne.getSequence()).thenReturn(1);
    MockMethod<Void> waitMethodOne = new MockMethod<Void>();
    doAnswer(waitMethodOne).when(monitorOne).waitUntilFinished(anyLong());
    I_LocatableRunable locateableRunnableOne = mock(I_LocatableRunable.class);
    when(locateableRunnableOne.getCurrentLocation()).thenReturn("locOne");
    when(monitorOne.getDelegate()).thenReturn(locateableRunnableOne);
    MockMethod<Boolean> finishedMethodOne = new MockMethod<Boolean>(
        false, false, false, true);
    when(monitorOne.isFinished()).then(finishedMethodOne);
    
    I_RunMonitor monitorTwo = mock(I_RunMonitor.class);
    MockMethod<Void> waitMethodTwo = new MockMethod<Void>();
    doAnswer(waitMethodTwo).when(monitorTwo).waitUntilFinished(anyLong());
    I_LocatableRunable locateableRunnableTwo = mock(I_LocatableRunable.class);
    when(locateableRunnableTwo.getCurrentLocation()).thenReturn("locTwo");
    when(monitorTwo.getDelegate()).thenReturn(locateableRunnableTwo);
    MockMethod<Boolean> finishedMethodTwo = new MockMethod<Boolean>(
        false, false, false, true);
    when(monitorTwo.isFinished()).then(finishedMethodTwo);
    
    I_RunMonitor monitorThree = mock(I_RunMonitor.class);
    MockMethod<Void> waitMethodThree = new MockMethod<Void>();
    doAnswer(waitMethodThree).when(monitorThree).waitUntilFinished(anyLong());
    I_LocatableRunable locateableRunnableThree = mock(I_LocatableRunable.class);
    when(locateableRunnableThree.getCurrentLocation()).thenReturn("locThree");
    when(monitorThree.getDelegate()).thenReturn(locateableRunnableThree);
    MockMethod<Boolean> finishedMethodThree = new MockMethod<Boolean>(
        false, false, false, true);
    when(monitorThree.isFinished()).then(finishedMethodThree);
    
    MockMethod<I_RunMonitor> newRunMonitorMethod = new MockMethod<I_RunMonitor>(
        monitorOne, monitorTwo, monitorThree);
    when(sysMock_.newRunMonitor(any(), anyInt())).then(newRunMonitorMethod);
    
    RoutineExecutionEngine e = new RoutineExecutionEngine(sysMock_, builderMock, 3);
    e.runRoutines();
    assertFalse(srm.isRan());
    assertEquals(3, newFixedThreadPoolMethod.getArg(0));
    assertEquals(4, buildMethod.count());
    
    assertSame(monitorOne, submitMethod.getArg(0));
    assertSame(monitorTwo, submitMethod.getArg(1));
    assertSame(monitorThree, submitMethod.getArg(2));
    
    assertEquals(4, finishedMethodOne.count());
    assertEquals(4, finishedMethodTwo.count());
    assertEquals(4, finishedMethodThree.count());
    
    assertEquals(3, waitMethodOne.count());
    assertEquals(3, waitMethodTwo.count());
    assertEquals(3, waitMethodThree.count());
    assertEquals("locOne", printlnMethod_.getArg(0));
    assertEquals("locTwo", printlnMethod_.getArg(1));
    assertEquals("locThree", printlnMethod_.getArg(2));
    
    assertFalse(e.hadFailure());
    assertNull(e.getFailure());
    assertNull(e.getRoutineThatFailed());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodRunRoutinesConcurrentExectuionWaitInterrupted() throws Exception {
    I_RoutineBuilder builderMock = mock(I_RoutineBuilder.class);
    SimpleConcurrentRoutineMock srm = new SimpleConcurrentRoutineMock();
    MockMethod<I_FabricationRoutine> buildMethod = new MockMethod<I_FabricationRoutine>(srm, true);
    when(builderMock.build(any())).then(buildMethod);
    when(builderMock.build((I_FabricationMemory) any())).then(buildMethod);
    
    ExecutorService exeServ  = mock(ExecutorService.class);
    MockMethod<Future<?>> submitMethod = new MockMethod<Future<?>>();
    doAnswer(submitMethod).when(exeServ).submit(any());
    MockMethod<ExecutorService> newFixedThreadPoolMethod = new MockMethod<ExecutorService>(exeServ, true);
    doAnswer(newFixedThreadPoolMethod).when(sysMock_).newFixedThreadPool(anyInt());
    
    I_RunMonitor monitorOne = mock(I_RunMonitor.class);
    when(monitorOne.getSequence()).thenReturn(1);
    doThrow(new InterruptedException("interrupted")).when(monitorOne).waitUntilFinished(anyLong());
    
    I_LocatableRunable locateableRunnableOne = mock(I_LocatableRunable.class);
    when(locateableRunnableOne.getCurrentLocation()).thenReturn("locOne");
    when(monitorOne.getDelegate()).thenReturn(locateableRunnableOne);
    MockMethod<Boolean> finishedMethodOne = new MockMethod<Boolean>(
        false, false, false, true);
    when(monitorOne.isFinished()).then(finishedMethodOne);
    
    I_RunMonitor monitorTwo = mock(I_RunMonitor.class);
    MockMethod<Void> waitMethodTwo = new MockMethod<Void>();
    doAnswer(waitMethodTwo).when(monitorTwo).waitUntilFinished(anyLong());
    I_LocatableRunable locateableRunnableTwo = mock(I_LocatableRunable.class);
    when(locateableRunnableTwo.getCurrentLocation()).thenReturn("locTwo");
    when(monitorTwo.getDelegate()).thenReturn(locateableRunnableTwo);
    MockMethod<Boolean> finishedMethodTwo = new MockMethod<Boolean>(
        false, false, false, true);
    when(monitorTwo.isFinished()).then(finishedMethodTwo);
    
    I_RunMonitor monitorThree = mock(I_RunMonitor.class);
    MockMethod<Void> waitMethodThree = new MockMethod<Void>();
    doAnswer(waitMethodThree).when(monitorThree).waitUntilFinished(anyLong());
    I_LocatableRunable locateableRunnableThree = mock(I_LocatableRunable.class);
    when(locateableRunnableThree.getCurrentLocation()).thenReturn("locThree");
    when(monitorThree.getDelegate()).thenReturn(locateableRunnableThree);
    MockMethod<Boolean> finishedMethodThree = new MockMethod<Boolean>(
        false, false, false, true);
    when(monitorThree.isFinished()).then(finishedMethodThree);
    
    MockMethod<I_RunMonitor> newRunMonitorMethod = new MockMethod<I_RunMonitor>(
        monitorOne, monitorTwo, monitorThree);
    when(sysMock_.newRunMonitor(any(), anyInt())).then(newRunMonitorMethod);
    
    Thread threadMock = mock(Thread.class);
    MockMethod<Void> interruptMethod = new MockMethod<Void>(new I_ReturnFactory<Void>() {

      @Override
      public Void create(Object[] keys) {
        throw new IllegalStateException("ise");
      }
    });
    doAnswer(interruptMethod).when(threadMock).interrupt();
    
    when(sysMock_.currentThread()).thenReturn(threadMock);
    RoutineExecutionEngine e = new RoutineExecutionEngine(sysMock_, builderMock, 3);
    assertThrown(new ExpectedThrowable(new IllegalStateException("ise")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            e.runRoutines();
          }
        });
    
    assertFalse(srm.isRan());
    assertEquals(3, newFixedThreadPoolMethod.getArg(0));
    assertEquals(4, buildMethod.count());
    
    assertSame(monitorOne, submitMethod.getArg(0));
    assertSame(monitorTwo, submitMethod.getArg(1));
    assertSame(monitorThree, submitMethod.getArg(2));
    
    assertEquals(1, finishedMethodOne.count());
    assertEquals(0, finishedMethodTwo.count());
    assertEquals(0, finishedMethodThree.count());
    
    assertEquals(1, interruptMethod.count());
    
    assertFalse(e.hadFailure());
  }
  
}

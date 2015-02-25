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
import org.adligo.fabricate.routines.RoutineFabricateFactory;
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

@SourceFileScope (sourceClass=RoutineFabricateFactory.class, minCoverage=0.0)
public class RoutineFactoryTrial extends MockitoSourceFileTrial {
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
  public void testConstructorCopy() throws Exception {
  //TODO
  }

  @Test
  public void testMethodsAddAndGetSimple() throws Exception {
  //TODO
  }

  @Test
  public void testMethodsAddAndGetOverlay() throws Exception {
  //TODO
  }
  
  @Test
  public void testMethodCreateSimple() throws Exception {
  //TODO
  }
  
  @Test
  public void testMethodCreateSimpleFailureExpectedInterfacesDoNOTMatch() throws Exception {
  //TODO
  }

  @Test
  public void testMethodCreateMultipleExpectedInterfaces() throws Exception {
  //TODO
  }
  
  @Test
  public void testMethodCreateMultipleExpectedInterfacesFailureExpectedInterfacesDoNOTMatch() throws Exception {
    //TODO
  }
}

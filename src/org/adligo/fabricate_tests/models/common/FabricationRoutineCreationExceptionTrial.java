package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.models.common.FabricationRoutineCreationException;
import org.adligo.fabricate.routines.I_InputAware;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricationRoutineCreationException.class)
public class FabricationRoutineCreationExceptionTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() {
    FabricationRoutineCreationException e = new FabricationRoutineCreationException();
    e.setActualGenericType(String.class);
    assertEquals(String.class.getName(), e.getActualGenericType().getName());
    
    e.setExpectedGenericType(Long.class);
    assertEquals(Long.class.getName(), e.getExpectedGenericType().getName());
    
    e.setExpectedInterface(Integer.class);
    assertEquals(Integer.class.getName(), e.getExpectedInterface().getName());
    
    e.setRoutine(EncryptTrait.class);
    assertEquals(EncryptTrait.class.getName(), e.getRoutine().getName());
    
    e.setWhichGenericType(1);
    assertEquals(1, e.getWhichGenericType());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodInitCause() {
    FabricationRoutineCreationException e = new FabricationRoutineCreationException();
    e.setActualGenericType(String.class);
    e.setExpectedGenericType(Long.class);
    e.setExpectedInterface(Integer.class);
    e.setRoutine(EncryptTrait.class);
    e.setWhichGenericType(1);
    
    FabricationRoutineCreationException e1 = new FabricationRoutineCreationException();
    e1.initCause(e);
    
    assertEquals(String.class.getName(), e1.getActualGenericType().getName());
    assertEquals(Long.class.getName(), e1.getExpectedGenericType().getName());
    assertEquals(Integer.class.getName(), e1.getExpectedInterface().getName());
    assertEquals(EncryptTrait.class.getName(), e1.getRoutine().getName());
    assertEquals(1, e1.getWhichGenericType());
    
    e1 = new FabricationRoutineCreationException();
    e1.initCause(new IllegalArgumentException());
    
    assertNull( e1.getActualGenericType());
    assertNull(e1.getExpectedGenericType());
    assertNull(e1.getExpectedInterface());
    assertNull(e1.getRoutine());
    assertEquals(0, e1.getWhichGenericType());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodLog() {
    I_FabLog logMock = mock(I_FabLog.class);
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    MockMethod<Void> printTraceMethod = new MockMethod<Void>();
    doAnswer(printTraceMethod).when(logMock).printTrace(any());
    
    
    FabricationRoutineCreationException e = new FabricationRoutineCreationException();
    FabricationRoutineCreationException.log(logMock, SystemEnMessages.INSTANCE, e);
    assertEquals("There was a problem creating the following routine;", printlnMethod.getArg(0));
    assertEquals("null", printlnMethod.getArg(1));
    assertEquals(2, printlnMethod.count());
    assertEquals(e, printTraceMethod.getArg(0));
    assertEquals(1, printTraceMethod.count());
    
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    printTraceMethod = new MockMethod<Void>();
    doAnswer(printTraceMethod).when(logMock).printTrace(any());
    
    e.setRoutine(EncryptTrait.class);
    FabricationRoutineCreationException.log(logMock, SystemEnMessages.INSTANCE, e);
 
    assertEquals("There was a problem creating the following routine;", printlnMethod.getArg(0));
    assertEquals(EncryptTrait.class.getName(), printlnMethod.getArg(1));
    assertEquals(2, printlnMethod.count());
    assertEquals(e, printTraceMethod.getArg(0));
    assertEquals(1, printTraceMethod.count());
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    printTraceMethod = new MockMethod<Void>();
    doAnswer(printTraceMethod).when(logMock).printTrace(any());
    
    e.setExpectedInterface(I_InputAware.class);
    FabricationRoutineCreationException.log(logMock, SystemEnMessages.INSTANCE, e);
 
    assertEquals("There was a problem creating the following routine;", printlnMethod.getArg(0));
    assertEquals(EncryptTrait.class.getName(), printlnMethod.getArg(1));
    assertEquals("It was expected to implement the following interface;", printlnMethod.getArg(2));
    assertEquals(I_InputAware.class.getName(), printlnMethod.getArg(3));
    assertEquals(4, printlnMethod.count());
    assertEquals(e, printTraceMethod.getArg(0));
    assertEquals(1, printTraceMethod.count());
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    printTraceMethod = new MockMethod<Void>();
    doAnswer(printTraceMethod).when(logMock).printTrace(any());
    
    e.setExpectedGenericType(Long.class);
    FabricationRoutineCreationException.log(logMock, SystemEnMessages.INSTANCE, e);
 
    assertEquals("There was a problem creating the following routine;", printlnMethod.getArg(0));
    assertEquals(EncryptTrait.class.getName(), printlnMethod.getArg(1));
    assertEquals("It was expected to implement the following interface;", printlnMethod.getArg(2));
    assertEquals(I_InputAware.class.getName(), printlnMethod.getArg(3));
    assertEquals("With the following generic type 0;", printlnMethod.getArg(4));
    assertEquals(Long.class.getName(), printlnMethod.getArg(5));
    assertEquals(6, printlnMethod.count());
    assertEquals(e, printTraceMethod.getArg(0));
    assertEquals(1, printTraceMethod.count());
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    printTraceMethod = new MockMethod<Void>();
    doAnswer(printTraceMethod).when(logMock).printTrace(any());
    
    e.setActualGenericType(String.class);
    FabricationRoutineCreationException.log(logMock, SystemEnMessages.INSTANCE, e);
 
    assertEquals("There was a problem creating the following routine;", printlnMethod.getArg(0));
    assertEquals(EncryptTrait.class.getName(), printlnMethod.getArg(1));
    assertEquals("It was expected to implement the following interface;", printlnMethod.getArg(2));
    assertEquals(I_InputAware.class.getName(), printlnMethod.getArg(3));
    assertEquals("With the following generic type 0;", printlnMethod.getArg(4));
    assertEquals(Long.class.getName(), printlnMethod.getArg(5));
    assertEquals("Instead of the following actual generic type;", printlnMethod.getArg(6));
    assertEquals(String.class.getName(), printlnMethod.getArg(7));
    assertEquals(8, printlnMethod.count());
    assertEquals(e, printTraceMethod.getArg(0));
    assertEquals(1, printTraceMethod.count());
  }
  
}

package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricationRoutineCreationException;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
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
}

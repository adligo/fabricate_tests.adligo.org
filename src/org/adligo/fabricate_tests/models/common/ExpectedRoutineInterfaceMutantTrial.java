package org.adligo.fabricate_tests.models.common;

import com.sun.org.apache.xpath.internal.operations.String;

import org.adligo.fabricate.models.common.ExpectedRoutineInterface;
import org.adligo.fabricate.models.common.ExpectedRoutineInterfaceMutant;
import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.List;

/**
 * @author scott
 *
 */
@SourceFileScope (sourceClass=ExpectedRoutineInterfaceMutant.class,minCoverage=87.0)
public class ExpectedRoutineInterfaceMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    ExpectedRoutineInterfaceMutant mut = new ExpectedRoutineInterfaceMutant();
    mut.setInterfaceClass(String.class);
    
    
    ExpectedRoutineInterfaceMutant copy = new ExpectedRoutineInterfaceMutant(mut);
    
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    List<Class<?>> gts =  copy.getGenericTypes();
    assertEquals(0, gts.size());
    assertEquals("java.util.Collections$EmptyList", gts.getClass().getName());
    
    mut.setGenericTypes(Collections.singletonList(Long.class));
    copy = new ExpectedRoutineInterfaceMutant(mut);
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    gts =  copy.getGenericTypes();
    assertEquals(Long.class.getName(), gts.get(0).getName());
    assertEquals(1, gts.size());
    assertEquals("java.util.ArrayList", gts.getClass().getName());
  }
  
  @Test
  public void testMethodsGetsAndSets() {
    ExpectedRoutineInterfaceMutant mut = new ExpectedRoutineInterfaceMutant();
    mut.setInterfaceClass(String.class);
    assertEquals(String.class.getName(), mut.getInterfaceClass().getName());
    
    mut.setGenericTypes(Collections.singletonList(Long.class));
    List<Class<?>> gts =  mut.getGenericTypes();
    assertEquals(Long.class.getName(), gts.get(0).getName());
    assertEquals("java.util.ArrayList", gts.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCode() {
    ExpectedRoutineInterfaceMutant a = new ExpectedRoutineInterfaceMutant();
    ExpectedRoutineInterfaceMutant a2 = new ExpectedRoutineInterfaceMutant();
    ExpectedRoutineInterfaceMutant b = new ExpectedRoutineInterfaceMutant();
    b.setInterfaceClass(I_FabricationRoutine.class);
    ExpectedRoutineInterfaceMutant c = new ExpectedRoutineInterfaceMutant();
    c.setInterfaceClass(I_FabricationRoutine.class);
    c.setGenericTypes(Collections.singletonList(String.class));
    
    ExpectedRoutineInterface b1 = new ExpectedRoutineInterface(b);
    ExpectedRoutineInterface c1 = new ExpectedRoutineInterface(c);
    
    //hash codes
    assertEquals(a.hashCode(), a.hashCode());
    assertEquals(a2.hashCode(), a2.hashCode());
    assertNotEquals(a.hashCode(), b1.hashCode());
    assertNotEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a.hashCode(), c1.hashCode());
    assertNotEquals(a.hashCode(), c.hashCode());
    assertNotEquals(a2.hashCode(), b1.hashCode());
    assertNotEquals(a2.hashCode(), b.hashCode());
    assertNotEquals(a2.hashCode(), c1.hashCode());
    assertNotEquals(a2.hashCode(), c.hashCode());
    
    assertEquals(b1.hashCode(), b1.hashCode());
    assertEquals(b1.hashCode(), b.hashCode());
    assertNotEquals(b1.hashCode(), a2.hashCode());
    assertNotEquals(b1.hashCode(), a.hashCode());
    assertNotEquals(b1.hashCode(), c1.hashCode());
    assertNotEquals(b1.hashCode(), c.hashCode());
    
    assertEquals(c1.hashCode(), c1.hashCode());
    assertEquals(c1.hashCode(), c.hashCode());
    assertNotEquals(c1.hashCode(), a2.hashCode());
    assertNotEquals(c1.hashCode(), a.hashCode());
    assertNotEquals(c1.hashCode(), b1.hashCode());
    assertNotEquals(c1.hashCode(), b.hashCode());
    
    //equals
    assertTrue(a.equals(a));
    assertTrue(a.equals(a2));
    assertTrue(a2.equals(a2));
    assertTrue(a2.equals(a));
    assertFalse(a.equals(b1));
    assertFalse(a.equals(b));
    assertFalse(a.equals(c1));
    assertFalse(a.equals(c));
    assertFalse(a2.equals(b1));
    assertFalse(a2.equals(b));
    assertFalse(a2.equals(c1));
    assertFalse(a2.equals(c));
    
    assertTrue(b1.equals(b1));
    assertTrue(b1.equals(b));
    assertFalse(b1.equals(a2));
    assertFalse(b1.equals(a));
    assertFalse(b1.equals(c1));
    assertFalse(b1.equals(c));
    
    assertTrue(c1.equals(c1));
    assertTrue(c1.equals(c));
    assertFalse(c1.equals(a2));
    assertFalse(c1.equals(a));
    assertFalse(c1.equals(b1));
    assertFalse(c1.equals(b));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodsEqualsHashCode() {
    ExpectedRoutineInterfaceMutant a = new ExpectedRoutineInterfaceMutant();
    ExpectedRoutineInterfaceMutant b = new ExpectedRoutineInterfaceMutant();
    b.setInterfaceClass(I_FabricationRoutine.class);
    ExpectedRoutineInterfaceMutant c = new ExpectedRoutineInterfaceMutant();
    c.setInterfaceClass(I_FabricationRoutine.class);
    c.setGenericTypes(Collections.singletonList(String.class));
    
    assertEquals(ExpectedRoutineInterfaceMutant.hashCode(a), 
        ExpectedRoutineInterfaceMutant.hashCode(a));
    assertTrue(
        ExpectedRoutineInterfaceMutant.equals(a, a));
    
    assertNotEquals(ExpectedRoutineInterfaceMutant.hashCode(a), 
        ExpectedRoutineInterfaceMutant.hashCode(b));
    assertFalse(ExpectedRoutineInterfaceMutant.equals(a, b));
    
    assertNotEquals(ExpectedRoutineInterfaceMutant.hashCode(a), 
        ExpectedRoutineInterfaceMutant.hashCode(c));
    assertFalse(ExpectedRoutineInterfaceMutant.equals(a, c));
    
    assertEquals(ExpectedRoutineInterfaceMutant.hashCode(b), 
        ExpectedRoutineInterfaceMutant.hashCode(b));
    assertTrue(ExpectedRoutineInterfaceMutant.equals(b, b));
    assertFalse(ExpectedRoutineInterfaceMutant.equals(b, a));
    assertFalse(ExpectedRoutineInterfaceMutant.equals(b, c));
    
    assertEquals(ExpectedRoutineInterfaceMutant.hashCode(c), 
        ExpectedRoutineInterfaceMutant.hashCode(c));
    assertTrue(ExpectedRoutineInterfaceMutant.equals(c, c));
    assertFalse(ExpectedRoutineInterfaceMutant.equals(c, a));
    assertFalse(ExpectedRoutineInterfaceMutant.equals(c, b));
  }

}

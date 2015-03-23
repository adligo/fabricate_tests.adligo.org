package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.ExpectedRoutineInterface;
import org.adligo.fabricate.models.common.ExpectedRoutineInterfaceMutant;
import org.adligo.fabricate.models.common.I_ExpectedRoutineInterface;
import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.List;

/**
 * @author scott
 *
 */
@SourceFileScope (sourceClass=ExpectedRoutineInterface.class, minCoverage=97.0)
public class ExpectedRoutineInterfaceTrial extends MockitoSourceFileTrial {


  @SuppressWarnings("boxing")
  @Test
  public void testConstructor() {
    Class<?>[] clazzes = null;
    ExpectedRoutineInterface copy = new ExpectedRoutineInterface(String.class,
        clazzes);
    
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    List<Class<?>> gts =  copy.getGenericTypes();
    assertEquals(0, gts.size());
    assertEquals("java.util.Collections$EmptyList", gts.getClass().getName());
    
    
    copy = new ExpectedRoutineInterface(String.class, Long.class, Integer.class);
    
    
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    gts =  copy.getGenericTypes();
    assertEquals(Long.class.getName(), gts.get(0).getName());
    assertEquals(Integer.class.getName(), gts.get(1).getName());
    assertEquals(2, gts.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", gts.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    ExpectedRoutineInterfaceMutant mut = new ExpectedRoutineInterfaceMutant();
    mut.setInterfaceClass(String.class);
    
    
    
    ExpectedRoutineInterface copy = new ExpectedRoutineInterface(mut);
    
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    List<Class<?>> gts =  copy.getGenericTypes();
    assertEquals(0, gts.size());
    assertEquals("java.util.Collections$EmptyList", gts.getClass().getName());
    
    I_ExpectedRoutineInterface mock = mock(I_ExpectedRoutineInterface.class);
    doReturn(String.class).when(mock).getInterfaceClass();
    when(mock.getGenericTypes()).thenReturn(null);
    
    copy = new ExpectedRoutineInterface(mock);
    
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    gts =  copy.getGenericTypes();
    assertEquals(0, gts.size());
    assertEquals("java.util.Collections$EmptyList", gts.getClass().getName());
    
    mut.setGenericTypes(Collections.singletonList(Long.class));
    copy = new ExpectedRoutineInterface(mut);
    assertEquals(String.class.getName(), copy.getInterfaceClass().getName());
    gts =  copy.getGenericTypes();
    assertEquals(Long.class.getName(), gts.get(0).getName());
    assertEquals(1, gts.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", gts.getClass().getName());
  }
  
  @Test
  public void testConstructorExceptions() {
    ExpectedRoutineInterfaceMutant mut = new ExpectedRoutineInterfaceMutant();
   
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new ExpectedRoutineInterface(mut);
          }
        });
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new ExpectedRoutineInterface(null, String.class);
          }
        });
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
    a.setInterfaceClass(String.class);
    ExpectedRoutineInterfaceMutant b = new ExpectedRoutineInterfaceMutant();
    b.setInterfaceClass(I_FabricationRoutine.class);
    ExpectedRoutineInterfaceMutant c = new ExpectedRoutineInterfaceMutant();
    c.setInterfaceClass(I_FabricationRoutine.class);
    c.setGenericTypes(Collections.singletonList(String.class));
    
    ExpectedRoutineInterface a1 = new ExpectedRoutineInterface(a);
    ExpectedRoutineInterface b1 = new ExpectedRoutineInterface(b);
    ExpectedRoutineInterface c1 = new ExpectedRoutineInterface(c);
    
    //hash codes
    assertEquals(a1.hashCode(), a1.hashCode());
    assertEquals(a1.hashCode(), a.hashCode());
    assertNotEquals(a1.hashCode(), b1.hashCode());
    assertNotEquals(a1.hashCode(), b.hashCode());
    assertNotEquals(a1.hashCode(), c1.hashCode());
    assertNotEquals(a1.hashCode(), c.hashCode());
    
    assertEquals(b1.hashCode(), b1.hashCode());
    assertEquals(b1.hashCode(), b.hashCode());
    assertNotEquals(b1.hashCode(), a1.hashCode());
    assertNotEquals(b1.hashCode(), a.hashCode());
    assertNotEquals(b1.hashCode(), c1.hashCode());
    assertNotEquals(b1.hashCode(), c.hashCode());
    
    assertEquals(c1.hashCode(), c1.hashCode());
    assertEquals(c1.hashCode(), c.hashCode());
    assertNotEquals(c1.hashCode(), a1.hashCode());
    assertNotEquals(c1.hashCode(), a.hashCode());
    assertNotEquals(c1.hashCode(), b1.hashCode());
    assertNotEquals(c1.hashCode(), b.hashCode());
    
    //equals
    assertTrue(a1.equals(a1));
    assertTrue(a1.equals(a));
    assertFalse(a1.equals(b1));
    assertFalse(a1.equals(b));
    assertFalse(a1.equals(c1));
    assertFalse(a1.equals(c));
    
    assertTrue(b1.equals(b1));
    assertTrue(b1.equals(b));
    assertFalse(b1.equals(a1));
    assertFalse(b1.equals(a));
    assertFalse(b1.equals(c1));
    assertFalse(b1.equals(c));
    
    assertTrue(c1.equals(c1));
    assertTrue(c1.equals(c));
    assertFalse(c1.equals(a1));
    assertFalse(c1.equals(a));
    assertFalse(c1.equals(b1));
    assertFalse(c1.equals(b));
  }
}

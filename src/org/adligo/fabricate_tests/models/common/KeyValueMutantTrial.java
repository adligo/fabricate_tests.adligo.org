package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.I_KeyValue;
import org.adligo.fabricate.models.common.KeyValue;
import org.adligo.fabricate.models.common.KeyValueMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=KeyValueMutant.class, minCoverage=80.0)
public class KeyValueMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new KeyValueMutant(null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    
    KeyValueMutant kvm = new KeyValueMutant();
    kvm.setKey("keyA");
    kvm.setValue("value1");
   
    
    kvm.setChildren(null);
    assertEquals(0, kvm.size());
    
    KeyValueMutant kvmB = new KeyValueMutant();
    kvmB.setKey("keyB");
    kvmB.setValue("value2");
    
    KeyValueMutant kvmC = new KeyValueMutant();
    kvmC.setKey("keyC");
    kvmC.setValue("value2");
    KeyValue kvC = new KeyValue(kvmC);
    
    List<I_KeyValue> kvs = new ArrayList<I_KeyValue>();
    kvs.add(kvmB);
    kvs.add(kvC);
    kvm.setChildren(kvs);
    
    KeyValueMutant kvm2 = new KeyValueMutant(kvm);
    assertEquals("keyA", kvm2.getKey());
    assertEquals("value1", kvm2.getValue());
    
    assertEquals(2, kvm2.size());
    I_KeyValue kvmB1 = kvm2.get(0);
    assertSame(kvmB, kvmB1);
    I_KeyValue kvmC1 = kvm2.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value2", kvmC1.getValue());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() {
    KeyValueMutant kvm = new KeyValueMutant();
    kvm.setKey("keyA");
    assertEquals("keyA", kvm.getKey());
    kvm.setValue("value1");
    assertEquals("value1", kvm.getValue());
    
    kvm.setChildren(null);
    assertEquals(0, kvm.size());
    
    KeyValueMutant kvmB = new KeyValueMutant();
    kvmB.setKey("keyB");
    kvmB.setValue("value2");
    
    KeyValueMutant kvmC = new KeyValueMutant();
    kvmC.setKey("keyC");
    kvmC.setValue("value2");
    KeyValue kvC = new KeyValue(kvmC);
    
    List<I_KeyValue> kvs = new ArrayList<I_KeyValue>();
    kvs.add(kvmB);
    kvs.add(kvC);
    kvm.setChildren(kvs);
    
    assertEquals(2, kvm.size());
    I_KeyValue kvmB1 = kvm.get(0);
    assertSame(kvmB, kvmB1);
    I_KeyValue kvmC1 = kvm.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value2", kvmC1.getValue());
    
    List<I_KeyValue> children =  kvm.getChildren();
    assertEquals(2, children.size());
    kvmB1 = children.get(0);
    assertSame(kvmB, kvmB1);
    kvmC1 = children.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value2", kvmC1.getValue());
    //test external mutation
    children.clear();
    assertEquals(2, kvm.size());
  }
}

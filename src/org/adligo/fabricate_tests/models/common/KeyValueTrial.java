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

@SourceFileScope (sourceClass=KeyValue.class, minCoverage=80.0)
public class KeyValueTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new KeyValue(null);
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
    kvmC.setValue("value3");
    KeyValue kvC = new KeyValue(kvmC);
    
    List<I_KeyValue> kvs = new ArrayList<I_KeyValue>();
    kvs.add(kvmB);
    kvs.add(kvC);
    kvm.setChildren(kvs);
    
    KeyValue kv2 = new KeyValue(kvm);
    assertEquals("keyA", kv2.getKey());
    assertEquals("value1", kv2.getValue());
    
    assertEquals(2, kv2.size());
    I_KeyValue kvmB1 = kv2.get(0);
    assertNotSame(kvmB, kvmB1);
    assertEquals("keyB", kvmB1.getKey());
    assertEquals("value2", kvmB1.getValue());
    I_KeyValue kvmC1 = kv2.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value3", kvmC1.getValue());
    
    List<I_KeyValue> kv = kv2.getChildren();
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", kv.getClass().getName());
  }
  
}

package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.common.I_KeyValue;
import org.adligo.fabricate.models.common.KeyValue;
import org.adligo.fabricate.models.common.KeyValueMutant;
import org.adligo.fabricate.models.dependencies.Ide;
import org.adligo.fabricate.models.dependencies.IdeMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=Ide.class, minCoverage=80.0)
public class IdeTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new Ide(null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    IdeMutant im = new IdeMutant();
    im.setName("sn");
    assertEquals("sn", im.getName());
    
    KeyValueMutant kvm = new KeyValueMutant();
    kvm.setKey("keyA");
    kvm.setValue("value1");
    
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
    kvs.add(kvm);
    im.setChildren(kvs);
    assertEquals(3, im.size());
    
    
  
    Ide ide = new Ide(im);
    assertEquals("sn", ide.getName());
    assertEquals(3, ide.size());
    
    I_KeyValue kvmFrom = ide.get(0);
    assertNotSame(kvmB, kvmFrom);
    assertEquals("keyB", kvmFrom.getKey());
    assertEquals("value2", kvmFrom.getValue());
    
    I_KeyValue kvm1From = ide.get(1);
    assertNotSame(kvC, kvm1From);
    assertEquals("keyC", kvm1From.getKey());
    assertEquals("value3", kvm1From.getValue());
    
    I_KeyValue kvm2From = ide.get(2);
    assertNotSame(kvm, kvm2From);
    assertEquals("keyA", kvm2From.getKey());
    assertEquals("value1", kvm2From.getValue());
    
    List<I_KeyValue> children = ide.getChildren();
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList",
        children.getClass().getName());
  }
  
}

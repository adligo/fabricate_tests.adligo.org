package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.dependencies.I_Ide;
import org.adligo.fabricate.models.dependencies.IdeMutant;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate_tests.models.common.ParameterMutantTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=IdeMutant.class, minCoverage=80.0)
public class IdeMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new IdeMutant((I_Ide) null);
      }
    });
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new IdeMutant((IdeType) null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    IdeMutant im = new IdeMutant();
    im.setName("sn");
    assertEquals("sn", im.getName());
    
    ParameterMutant kvm = new ParameterMutant();
    kvm.setKey("keyA");
    kvm.setValue("value1");
    
    ParameterMutant kvmB = new ParameterMutant();
    kvmB.setKey("keyB");
    kvmB.setValue("value2");
    
    ParameterMutant kvmC = new ParameterMutant();
    kvmC.setKey("keyC");
    kvmC.setValue("value3");
    Parameter kvC = new Parameter(kvmC);
    
    List<I_Parameter> kvs = new ArrayList<I_Parameter>();
    kvs.add(kvmB);
    kvs.add(kvC);
    kvs.add(kvm);
    im.setChildren(kvs);
    assertEquals(3, im.size());
    
    //enforce encapsulation of list
    List<I_Parameter> children = im.getChildren();
    assertEquals(3, children.size());
    children.clear();
    assertEquals(3, im.size());
    
    I_Parameter kvmFrom = im.get(0);
    assertSame(kvmB, kvmFrom);
    
    I_Parameter kvm1From = im.get(1);
    assertNotSame(kvC, kvm1From);
    assertEquals("keyC", kvm1From.getKey());
    assertEquals("value3", kvm1From.getValue());
    
    I_Parameter kvm2From = im.get(2);
    assertSame(kvm, kvm2From);
    
    IdeMutant im2 = new IdeMutant(im);
    assertEquals("sn", im2.getName());
    
    kvmFrom = im2.get(0);
    assertSame(kvmB, kvmFrom);
    
    kvm1From = im.get(1);
    assertNotSame(kvC, kvm1From);
    assertEquals("keyC", kvm1From.getKey());
    assertEquals("value3", kvm1From.getValue());
    
    kvm2From = im.get(2);
    assertSame(kvm, kvm2From);
    
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodCovertAndCreateIdeTypeListAndIdeType() {
    IdeType itA = new IdeType();
    itA.setName("ideA");
    
    itA.setArgs(ParameterMutantTrial.createParams());
    
    IdeType itB = new IdeType();
    itB.setName("ideB");
    
    List<IdeType> its = new ArrayList<IdeType>();
    its.add(itA);
    its.add(itB);
    
    List<I_Ide> ides =  IdeMutant.convert(its);
    I_Ide ide1 = ides.get(0);
    assertEquals("ideA", ide1.getName());
    assertEquals(IdeMutant.class.getName(), ide1.getClass().getName());
    
    I_Ide ide2 = ides.get(1);
    assertEquals("ideB", ide2.getName());
    assertEquals(IdeMutant.class.getName(), ide2.getClass().getName());
    assertEquals(0, ide2.size());
    
    ParameterMutantTrial.assertConvertedParams(ide1.getChildren(), this);
    
    List<I_Ide> out = IdeMutant.convert((List<IdeType>) null);
    assertNotNull(out);
    
  }
}

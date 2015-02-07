package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=Parameter.class, minCoverage=80.0)
public class ParameterTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new Parameter((I_Parameter) null);
      }
    });
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new Parameter((ParamType) null);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    
    ParameterMutant kvm = new ParameterMutant();
    kvm.setKey("keyA");
    kvm.setValue("value1");
   
    
    kvm.setChildren(null);
    assertEquals(0, kvm.size());
    
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
    kvm.setChildren(kvs);
    
    Parameter kv2 = new Parameter(kvm);
    assertEquals("keyA", kv2.getKey());
    assertEquals("value1", kv2.getValue());
    
    assertEquals(2, kv2.size());
    I_Parameter kvmB1 = kv2.get(0);
    assertNotSame(kvmB, kvmB1);
    assertEquals("keyB", kvmB1.getKey());
    assertEquals("value2", kvmB1.getValue());
    I_Parameter kvmC1 = kv2.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value3", kvmC1.getValue());
    
    List<I_Parameter> kv = kv2.getChildren();
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", kv.getClass().getName());
  }
  
  @Test
  public void testMethodsCovertAndCreateParamsTypeAndParamType() {
    
    
    List<I_Parameter> out = Parameter.convert(ParameterMutantTrial.createParams());
    assertConvertedParams(out, this);
    
    out = ParameterMutant.convert((List<ParamType>) null);
    assertNotNull(out);
    
    out = ParameterMutant.convert((ParamsType) null);
    assertNotNull(out);
  }

  @SuppressWarnings("boxing")
  public static void assertConvertedParams(List<I_Parameter> out, I_Asserts asserts) {
    I_Parameter p1 = out.get(0);
    asserts.assertEquals("a", p1.getKey());
    asserts.assertEquals("1", p1.getValue());
    asserts.assertEquals(Parameter.class.getName(), p1.getClass().getName());
    asserts.assertEquals(0, p1.size());
    
    I_Parameter p2 = out.get(1);
    asserts.assertEquals("b", p2.getKey());
    asserts.assertEquals("2", p2.getValue());
    asserts.assertEquals(Parameter.class.getName(), p2.getClass().getName());
    
    I_Parameter p3 = p2.get(0);
    asserts.assertEquals("c", p3.getKey());
    asserts.assertEquals("3", p3.getValue());
    asserts.assertEquals(Parameter.class.getName(), p3.getClass().getName());
    asserts.assertEquals(1, p2.size());
    
    asserts.assertEquals(2, out.size());
  }
}

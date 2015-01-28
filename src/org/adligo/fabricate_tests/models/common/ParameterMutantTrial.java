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

@SourceFileScope (sourceClass=ParameterMutant.class, minCoverage=70.0)
public class ParameterMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new ParameterMutant((I_Parameter) null);
      }
    });
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new ParameterMutant((ParamType) null);
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
    kvmC.setValue("value2");
    Parameter kvC = new Parameter(kvmC);
    
    List<I_Parameter> kvs = new ArrayList<I_Parameter>();
    kvs.add(kvmB);
    kvs.add(kvC);
    kvm.setChildren(kvs);
    
    ParameterMutant kvm2 = new ParameterMutant(kvm);
    assertEquals("keyA", kvm2.getKey());
    assertEquals("value1", kvm2.getValue());
    
    assertEquals(2, kvm2.size());
    I_Parameter kvmB1 = kvm2.get(0);
    assertSame(kvmB, kvmB1);
    I_Parameter kvmC1 = kvm2.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value2", kvmC1.getValue());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() {
    ParameterMutant kvm = new ParameterMutant();
    kvm.setKey("keyA");
    assertEquals("keyA", kvm.getKey());
    kvm.setValue("value1");
    assertEquals("value1", kvm.getValue());
    
    kvm.setChildren(null);
    assertEquals(0, kvm.size());
    
    ParameterMutant kvmB = new ParameterMutant();
    kvmB.setKey("keyB");
    kvmB.setValue("value2");
    
    ParameterMutant kvmC = new ParameterMutant();
    kvmC.setKey("keyC");
    kvmC.setValue("value2");
    Parameter kvC = new Parameter(kvmC);
    
    List<I_Parameter> kvs = new ArrayList<I_Parameter>();
    kvs.add(kvmB);
    kvs.add(kvC);
    kvm.setChildren(kvs);
    
    assertEquals(2, kvm.size());
    I_Parameter kvmB1 = kvm.get(0);
    assertSame(kvmB, kvmB1);
    I_Parameter kvmC1 = kvm.get(1);
    assertNotSame(kvC, kvmC1);
    assertNotSame(kvmC, kvmC1);
    assertEquals("keyC", kvmC1.getKey());
    assertEquals("value2", kvmC1.getValue());
    
    List<I_Parameter> children =  kvm.getChildren();
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
  
  @Test
  public void testMethodsCovertAndCreateParamsTypeAndParamType() {
    ParamsType params = createParams();
    
    List<I_Parameter> out = ParameterMutant.convert(params);
    assertConvertedParams(out, this);
    
    out = ParameterMutant.convert((List<ParamType>) null);
    assertNotNull(out);
    
    out = ParameterMutant.convert((ParamsType) null);
    assertNotNull(out);
  }

  public static ParamsType createParams() {
    ParamType pa = new ParamType();
    pa.setKey("a");
    pa.setValue("1");
    pa.getParam().add(null);
    
    ParamType pb = new ParamType();
    pb.setKey("b");
    pb.setValue("2");
    
    ParamType pc = new ParamType();
    pc.setKey("c");
    pc.setValue("3");
    pb.getParam().add(pc);
    
    ParamsType params = new ParamsType();
    List<ParamType> ps = params.getParam();
    ps.add(pa);
    ps.add(pb);
    ps.add(null);
    return params;
  }

  public static void assertConvertedParams(List<I_Parameter> out, I_Asserts asserts) {
    I_Parameter p1 = out.get(0);
    asserts.assertEquals("a", p1.getKey());
    asserts.assertEquals("1", p1.getValue());
    asserts.assertEquals(0, p1.size());
    asserts.assertEquals(ParameterMutant.class.getName(), p1.getClass().getName());
    
    I_Parameter p2 = out.get(1);
    asserts.assertEquals("b", p2.getKey());
    asserts.assertEquals("2", p2.getValue());
    asserts.assertEquals(ParameterMutant.class.getName(), p2.getClass().getName());
    
    I_Parameter p3 = p2.get(0);
    asserts.assertEquals("c", p3.getKey());
    asserts.assertEquals("3", p3.getValue());
    asserts.assertEquals(ParameterMutant.class.getName(), p3.getClass().getName());
    asserts.assertEquals(1, p2.size());
    
    asserts.assertEquals(2, out.size());

  }
}

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
import java.util.Collections;
import java.util.List;

@SourceFileScope (sourceClass=Parameter.class, minCoverage=97.0)
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
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    ParameterMutant a1 = new ParameterMutant();
    ParameterMutant b1 = new ParameterMutant();
    b1.setKey("key");
    ParameterMutant c1 = new ParameterMutant();
    c1.setKey("key2");
    ParameterMutant d1 = new ParameterMutant();
    d1.setKey("key");
    d1.setValue("value");
    ParameterMutant e1 = new ParameterMutant();
    e1.setKey("key");
    e1.setValue("value2");
    ParameterMutant f1 = new ParameterMutant();
    f1.setKey("keyF");
    ParameterMutant g1 = new ParameterMutant();
    g1.setKey("keyG");
    g1.setChildren(Collections.singletonList(f1));
    ParameterMutant h1 = new ParameterMutant();
    h1.setKey("keyH");
    h1.setChildren(Collections.singletonList(g1));
    
    Parameter a = new Parameter(a1);
    Parameter b = new Parameter(b1);
    Parameter c = new Parameter(c1);
    Parameter d = new Parameter(d1);
    Parameter e = new Parameter(e1);
    Parameter f = new Parameter(f1);
    Parameter g = new Parameter(g1);
    Parameter h = new Parameter(h1);
    
    assertEquals(a.hashCode(), a.hashCode());
    assertNotEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a.hashCode(), c.hashCode());
    assertNotEquals(a.hashCode(), d.hashCode());
    assertNotEquals(a.hashCode(), e.hashCode());
    assertNotEquals(a.hashCode(), f.hashCode());
    assertNotEquals(a.hashCode(), g.hashCode());
    assertNotEquals(a.hashCode(), h.hashCode());
    assertEquals(a, a);
    assertNotEquals(a, b);
    assertNotEquals(a, c);
    assertNotEquals(a, d);
    assertNotEquals(a, e);
    assertNotEquals(a, f);
    assertNotEquals(a, g);
    assertNotEquals(a, h);
    assertEquals("Parameter [key=null, value=null]", a.toString());
    
    assertEquals(b.hashCode(), b.hashCode());
    assertNotEquals(b.hashCode(), a.hashCode());
    assertNotEquals(b.hashCode(), c.hashCode());
    assertNotEquals(b.hashCode(), d.hashCode());
    assertNotEquals(b.hashCode(), e.hashCode());
    assertNotEquals(b.hashCode(), f.hashCode());
    assertNotEquals(b.hashCode(), g.hashCode());
    assertNotEquals(b.hashCode(), h.hashCode());
    assertEquals(b, b);
    assertNotEquals(b, a);
    assertNotEquals(b, c);
    assertNotEquals(b, d);
    assertNotEquals(b, e);
    assertNotEquals(b, f);
    assertNotEquals(b, g);
    assertNotEquals(b, h);
    assertEquals("Parameter [key=key, value=null]", b.toString());
    
    assertEquals(c.hashCode(), c.hashCode());
    assertNotEquals(c.hashCode(), a.hashCode());
    assertNotEquals(c.hashCode(), b.hashCode());
    assertNotEquals(c.hashCode(), d.hashCode());
    assertNotEquals(c.hashCode(), e.hashCode());
    assertNotEquals(c.hashCode(), f.hashCode());
    assertNotEquals(c.hashCode(), g.hashCode());
    assertNotEquals(c.hashCode(), h.hashCode());
    assertEquals(c, c);
    assertNotEquals(c, a);
    assertNotEquals(c, b);
    assertNotEquals(c, d);
    assertNotEquals(c, e);
    assertNotEquals(c, f);
    assertNotEquals(c, g);
    assertNotEquals(c, h);
    assertEquals("Parameter [key=key2, value=null]", c.toString());
    
    assertEquals(d.hashCode(), d.hashCode());
    assertNotEquals(d.hashCode(), a.hashCode());
    assertNotEquals(d.hashCode(), b.hashCode());
    assertNotEquals(d.hashCode(), c.hashCode());
    assertNotEquals(d.hashCode(), e.hashCode());
    assertNotEquals(d.hashCode(), f.hashCode());
    assertNotEquals(d.hashCode(), g.hashCode());
    assertNotEquals(d.hashCode(), h.hashCode());
    assertEquals(d, d);
    assertNotEquals(d, a);
    assertNotEquals(d, b);
    assertNotEquals(d, c);
    assertNotEquals(d, e);
    assertNotEquals(d, f);
    assertNotEquals(d, g);
    assertNotEquals(d, h);
    assertEquals("Parameter [key=key, value=value]", d.toString());
    
    assertEquals(e.hashCode(), e.hashCode());
    assertNotEquals(e.hashCode(), a.hashCode());
    assertNotEquals(e.hashCode(), b.hashCode());
    assertNotEquals(e.hashCode(), c.hashCode());
    assertNotEquals(e.hashCode(), d.hashCode());
    assertNotEquals(e.hashCode(), f.hashCode());
    assertNotEquals(e.hashCode(), g.hashCode());
    assertNotEquals(e.hashCode(), h.hashCode());
    assertEquals(e, e);
    assertNotEquals(e, a);
    assertNotEquals(e, b);
    assertNotEquals(e, c);
    assertNotEquals(e, d);
    assertNotEquals(e, f);
    assertNotEquals(e, g);
    assertNotEquals(e, h);
    assertEquals("Parameter [key=key, value=value2]", e.toString());
    
    assertEquals(f.hashCode(), f.hashCode());
    assertNotEquals(f.hashCode(), a.hashCode());
    assertNotEquals(f.hashCode(), b.hashCode());
    assertNotEquals(f.hashCode(), c.hashCode());
    assertNotEquals(f.hashCode(), d.hashCode());
    assertNotEquals(f.hashCode(), e.hashCode());
    assertNotEquals(f.hashCode(), g.hashCode());
    assertNotEquals(f.hashCode(), h.hashCode());
    assertEquals(f, f);
    assertNotEquals(f, a);
    assertNotEquals(f, b);
    assertNotEquals(f, c);
    assertNotEquals(f, d);
    assertNotEquals(f, e);
    assertNotEquals(f, g);
    assertNotEquals(f, h);
    assertEquals("Parameter [key=keyF, value=null]", f.toString());
    
    assertEquals(g.hashCode(), g.hashCode());
    assertNotEquals(g.hashCode(), a.hashCode());
    assertNotEquals(g.hashCode(), b.hashCode());
    assertNotEquals(g.hashCode(), c.hashCode());
    assertNotEquals(g.hashCode(), d.hashCode());
    assertNotEquals(g.hashCode(), e.hashCode());
    assertNotEquals(g.hashCode(), f.hashCode());
    assertNotEquals(g.hashCode(), h.hashCode());
    assertEquals(g, g);
    assertNotEquals(g, a);
    assertNotEquals(g, b);
    assertNotEquals(g, c);
    assertNotEquals(g, d);
    assertNotEquals(g, e);
    assertNotEquals(g, f);
    assertNotEquals(g, h);
    assertEquals("Parameter [key=keyG, value=null" + System.lineSeparator() +
        "\tParameter [key=keyF, value=null]" + System.lineSeparator() +
        "]", g.toString());
    
    assertEquals(h.hashCode(), h.hashCode());
    assertNotEquals(h.hashCode(), a.hashCode());
    assertNotEquals(h.hashCode(), b.hashCode());
    assertNotEquals(h.hashCode(), c.hashCode());
    assertNotEquals(h.hashCode(), d.hashCode());
    assertNotEquals(h.hashCode(), e.hashCode());
    assertNotEquals(h.hashCode(), f.hashCode());
    assertEquals(h, h);
    assertNotEquals(h, a);
    assertNotEquals(h, b);
    assertNotEquals(h, c);
    assertNotEquals(h, d);
    assertNotEquals(h, e);
    assertNotEquals(h, f);
    assertEquals("Parameter [key=keyH, value=null" + System.lineSeparator() +
        "\tParameter [key=keyG, value=null" + System.lineSeparator() +
        "\t\tParameter [key=keyF, value=null]" + System.lineSeparator() +
        "\t]" + System.lineSeparator() +
        "]", h.toString());
  }
}

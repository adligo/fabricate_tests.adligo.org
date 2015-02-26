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

@SourceFileScope (sourceClass=ParameterMutant.class, minCoverage=75.0)
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
  public void testMethodsEqualsHashCodeAndToString() {
    ParameterMutant a = new ParameterMutant();
    ParameterMutant b = new ParameterMutant();
    b.setKey("key");
    ParameterMutant c = new ParameterMutant();
    c.setKey("key2");
    ParameterMutant d = new ParameterMutant();
    d.setKey("key");
    d.setValue("value");
    ParameterMutant e = new ParameterMutant();
    e.setKey("key");
    e.setValue("value2");
    ParameterMutant f = new ParameterMutant();
    f.setKey("keyF");
    ParameterMutant g = new ParameterMutant();
    g.setKey("keyG");
    g.setChildren(Collections.singletonList(f));
    ParameterMutant h = new ParameterMutant();
    h.setKey("keyH");
    h.setChildren(Collections.singletonList(g));
    
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
    assertEquals("ParameterMutant [key=null, value=null]", a.toString());
    
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
    assertEquals("ParameterMutant [key=key, value=null]", b.toString());
    
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
    assertEquals("ParameterMutant [key=key2, value=null]", c.toString());
    
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
    assertEquals("ParameterMutant [key=key, value=value]", d.toString());
    
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
    assertEquals("ParameterMutant [key=key, value=value2]", e.toString());
    
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
    assertEquals("ParameterMutant [key=keyF, value=null]", f.toString());
    
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
    assertEquals("ParameterMutant [key=keyG, value=null" + System.lineSeparator() +
        "\tParameterMutant [key=keyF, value=null]" + System.lineSeparator() +
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
    assertEquals("ParameterMutant [key=keyH, value=null" + System.lineSeparator() +
        "\tParameterMutant [key=keyG, value=null" + System.lineSeparator() +
        "\t\tParameterMutant [key=keyF, value=null]" + System.lineSeparator() +
        "\t]" + System.lineSeparator() +
        "]", h.toString());
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

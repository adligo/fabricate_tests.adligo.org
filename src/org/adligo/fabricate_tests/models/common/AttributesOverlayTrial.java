package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.AttributesOverlay;
import org.adligo.fabricate.models.common.I_AttributesContainer;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=AttributesOverlay.class, minCoverage=89.0)
public class AttributesOverlayTrial extends MockitoSourceFileTrial {


  
  @SuppressWarnings("boxing")
  @Test 
  public void testConstructorWithComplex_AwBCv1_AwBv1() {
    I_AttributesContainer project = mock(I_AttributesContainer.class);
    I_AttributesContainer fabricate = mock(I_AttributesContainer.class);
    
    ParameterMutant a = getA();
    a.addChild(getB());
    a.addChild(getCv1());
    when(fabricate.getAttributes()).thenReturn(newList(a));
    
    ParameterMutant a2 = getA();
    a2.addChild(getBv1());
    when(project.getAttributes()).thenReturn(newList(a2));
    
    AttributesOverlay ao = new AttributesOverlay(fabricate, project);
    List<I_Parameter> result = ao.getAttributes();
    I_Parameter p0 = result.get(0);
    assertEquals("A", p0.getKey());
    assertNull(p0.getValue());
    
    List<I_Parameter> children = p0.getChildren();
    I_Parameter p00 = children.get(0);
    assertEquals("B", p00.getKey());
    assertEquals("1", p00.getValue());
    
    I_Parameter p01 = children.get(1);
    assertEquals("C", p01.getKey());
    assertEquals("1", p01.getValue());
    
    assertEquals(2, children.size());
    assertEquals(1, result.size());
  }
  
  @SuppressWarnings({"boxing", "unused"})
  @Test 
  public void testConstructorWithComplexRecursion_AwBwCv1_AwBv1wDv4() {
    I_AttributesContainer project = mock(I_AttributesContainer.class);
    I_AttributesContainer fabricate = mock(I_AttributesContainer.class);
    
    ParameterMutant a = getA();
    ParameterMutant b = getB();
    a.addChild(b);
    b.addChild(getCv1());
    when(fabricate.getAttributes()).thenReturn(newList(a));
    
    ParameterMutant a2 = getA();
    ParameterMutant b2 = getBv1();
    ParameterMutant d2 = getDv4();
    a2.addChild(b2);
    b2.addChild(d2);
    
    when(project.getAttributes()).thenReturn(newList(a2));
    
    AttributesOverlay ao = new AttributesOverlay(fabricate, project);
    List<I_Parameter> result = ao.getAttributes();
    I_Parameter p0 = result.get(0);
    assertEquals("A", p0.getKey());
    assertNull(p0.getValue());
    
    List<I_Parameter> children = p0.getChildren();
    I_Parameter p00 = children.get(0);
    assertEquals("B", p00.getKey());
    assertEquals("1", p00.getValue());
    assertEquals(1, children.size());
    
    children = p00.getChildren();
    I_Parameter p000 = children.get(0);
    assertEquals("C", p000.getKey());
    assertEquals("1", p000.getValue());
    
    I_Parameter p001 = children.get(1);
    assertEquals("D", p001.getKey());
    assertEquals("4", p001.getValue());
    assertEquals(2, children.size());
    
    assertEquals(1, result.size());
  }
  
  @SuppressWarnings("boxing")
  @Test 
  public void testConstructorWithComplexRecursion_AwBwBv1_AwBv1wBv2() {
    I_AttributesContainer project = mock(I_AttributesContainer.class);
    I_AttributesContainer fabricate = mock(I_AttributesContainer.class);
    
    ParameterMutant a = getA();
    ParameterMutant b = getB();
    a.addChild(b);
    b.addChild(getBv1());
    when(fabricate.getAttributes()).thenReturn(newList(a));
    
    ParameterMutant a2 = getA();
    ParameterMutant b2 = getBv1();
    a2.addChild(b2);
    b2.addChild(getBv2());
    
    when(project.getAttributes()).thenReturn(newList(a2));
    
    AttributesOverlay ao = new AttributesOverlay(fabricate, project);
    List<I_Parameter> result = ao.getAttributes();
    I_Parameter p0 = result.get(0);
    assertEquals("A", p0.getKey());
    assertNull(p0.getValue());
    
    List<I_Parameter> children = p0.getChildren();
    I_Parameter p00 = children.get(0);
    assertEquals("B", p00.getKey());
    assertEquals("1", p00.getValue());
    assertEquals(1, children.size());
    
    children = p00.getChildren();
    I_Parameter p000 = children.get(0);
    assertEquals("B", p000.getKey());
    assertEquals("2", p000.getValue());
    assertEquals(1, children.size());
    
    assertEquals(1, result.size());
  }
  
  @SuppressWarnings("boxing")
  @Test 
  public void testConstructorWithSimpleOverlay() {
    I_AttributesContainer project = mock(I_AttributesContainer.class);
    I_AttributesContainer fabricate = mock(I_AttributesContainer.class);
    
    when(project.getAttributes()).thenReturn(newList(getBv1()));
    when(fabricate.getAttributes()).thenReturn(newList(getB()));
    
    AttributesOverlay ao = new AttributesOverlay(fabricate, project);
    assertEquals("1", ao.getAttributeValue("B"));
    assertEquals(getBv1(), ao.getAttribute("B"));
    reset(project, fabricate);
    
    List<I_Parameter> b1s = ao.getAttributes("B", "1");
    assertContains(b1s, getBv1());
    assertEquals(1, b1s.size());
    
    List<I_Parameter> all = ao.getAllAttributes();
    assertEquals(getBv1(), all.get(0));
    assertEquals(getB(), all.get(1));
    assertEquals(2, all.size());
    
    assertEquals(getBv1(), ao.getAnyAttribute("B"));
    assertEquals("1", ao.getAnyAttributeValue("B"));
    
    List<String> atribVals = ao.getAllAttributeValues("B");
    assertContains(atribVals, "1");
    assertEquals(1, atribVals.size());
    
    when(project.getAttributes()).thenReturn(newList(getBv2()));
    when(fabricate.getAttributes()).thenReturn(newList(getBv1()));
    
    ao = new AttributesOverlay(fabricate, project);
    assertEquals("2", ao.getAttributeValue("B"));
    assertEquals(getBv2(), ao.getAttribute("B"));
    reset(project, fabricate);
    
    when(project.getAttributes()).thenReturn(newList(getB()));
    when(fabricate.getAttributes()).thenReturn(newList(getBv1()));
    
    ao = new AttributesOverlay(fabricate, project);
    assertNull(ao.getAttributeValue("B"));
    assertEquals(getB(), ao.getAttribute("B"));
  }
 
  @SuppressWarnings("boxing")
  @Test 
  public void testConstructorWithSimple_ABC_B1D() {
    I_AttributesContainer project = mock(I_AttributesContainer.class);
    I_AttributesContainer fabricate = mock(I_AttributesContainer.class);
    
    List<I_Parameter> projs = new ArrayList<I_Parameter>();
    projs.add(getBv1());
    projs.add(getDv4());
    when(project.getAttributes()).thenReturn(projs);
    when(fabricate.getAttributes()).thenReturn(getA_B_Cv1());
    
    AttributesOverlay ao = new AttributesOverlay(fabricate, project);
    List<I_Parameter> result = ao.getAttributes();
    I_Parameter p0 = result.get(0);
    assertEquals("A", p0.getKey());
    assertNull(p0.getValue());
    assertSame(p0, ao.getAttribute("A"));
    assertNull(ao.getAttributeValue("A"));
    List<I_Parameter> attribs = ao.getAttributes("A");
    assertContains(attribs, p0);
    assertEquals(1, attribs.size());
    
    List<String> vals = ao.getAttributeValues("A");
    assertEquals(0, vals.size());
    
    I_Parameter p1 = result.get(1);
    assertEquals("B", p1.getKey());
    assertEquals("1",p1.getValue());
    assertSame(p1, ao.getAttribute("B"));
    assertEquals("1", ao.getAttributeValue("B"));
    List<I_Parameter> bAttribs = ao.getAttributes("B");
    assertContains(bAttribs, p1);
    assertEquals(1, bAttribs.size());
    
    List<String> bVals = ao.getAttributeValues("B");
    assertContains(bVals, "1");
    assertEquals(1, bVals.size());
    
    I_Parameter p2 = result.get(2);
    assertEquals("C", p2.getKey());
    assertEquals("1",p2.getValue());
    
    I_Parameter p3 = result.get(3);
    assertEquals("D", p3.getKey());
    assertEquals("4",p3.getValue());
    assertEquals(4, result.size());
  }
  
  @SuppressWarnings("boxing")
  @Test 
  public void testConstructorWithSimple_BBv1_Bv2B() {
    I_AttributesContainer project = mock(I_AttributesContainer.class);
    I_AttributesContainer fabricate = mock(I_AttributesContainer.class);
    
    List<I_Parameter> fabs = new ArrayList<I_Parameter>();
    fabs.add(getB());
    fabs.add(getBv1());
    when(fabricate.getAttributes()).thenReturn(fabs);
    
    List<I_Parameter> projs = new ArrayList<I_Parameter>();
    projs.add(getBv2());
    projs.add(getB());
    when(project.getAttributes()).thenReturn(projs);
    
    AttributesOverlay ao = new AttributesOverlay(fabricate, project);
    List<I_Parameter> result = ao.getAttributes();
    I_Parameter p0 = result.get(0);
    assertEquals("B", p0.getKey());
    assertEquals("2", p0.getValue());
    
    I_Parameter p1 = result.get(1);
    assertEquals("B", p1.getKey());
    assertNull(p1.getValue());
    
    assertEquals(2, result.size());
  }
  
  @Test
  public void testStaticMethodGetAttribute() {
    List<I_Parameter> abc1 = getA_B_Cv1();
    assertThrown(new ExpectedThrowable(NullPointerException.class), 
      new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        AttributesOverlay.getAttributeValue(null, abc1);
      }
    });
    assertNull(AttributesOverlay.getAttribute("null", abc1));
    assertEquals(getA(), AttributesOverlay.getAttribute("A", abc1));
    assertEquals(getCv1(), AttributesOverlay.getAttribute("C", abc1));
    
    List<I_Parameter> nullAbc1 = new ArrayList<I_Parameter>();
    nullAbc1.add(null);
    nullAbc1.addAll(abc1);
    nullAbc1.add(getBv1());
    assertEquals(getB(), AttributesOverlay.getAttribute("B", nullAbc1));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodGetAttributes() {
    List<I_Parameter> abc1 = getA_B_Cv1();
    assertThrown(new ExpectedThrowable(NullPointerException.class), 
      new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        AttributesOverlay.getAttributeValue(null, abc1);
      }
    });
    List<I_Parameter> result = AttributesOverlay.getAttributes("null", abc1);
    assertEquals(0, result.size());
    
    result = AttributesOverlay.getAttributes("A", abc1);
    assertContains(result, getA());
    assertEquals(1, result.size());
    
    result = AttributesOverlay.getAttributes("C", abc1);
    assertContains(result, getCv1());
    assertEquals(1, result.size());
    
    List<I_Parameter> nullAbc1 = new ArrayList<I_Parameter>();
    nullAbc1.add(null);
    nullAbc1.addAll(abc1);
    nullAbc1.add(getBv1());
    nullAbc1.add(getBv2());
    
    result = AttributesOverlay.getAttributes("B", nullAbc1);
    assertContains(result, getB());
    assertContains(result, getBv1());
    assertContains(result, getBv2());
    assertEquals(3, result.size());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodGetAttributesByKeyValue() {
    List<I_Parameter> abc1 = getA_B_Cv1();
    assertThrown(new ExpectedThrowable(NullPointerException.class), 
      new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        AttributesOverlay.getAttributeValue(null, abc1);
      }
    });
    List<I_Parameter> result = AttributesOverlay.getAttributes("null", abc1);
    assertEquals(0, result.size());
    
    result = AttributesOverlay.getAttributes("A", null, abc1);
    assertContains(result, getA());
    assertEquals(1, result.size());
    
    result = AttributesOverlay.getAttributes("C","1", abc1);
    assertContains(result, getCv1());
    assertEquals(1, result.size());
    
  }
  
  @Test
  public void testStaticMethodGetAttributeValue() {
    List<I_Parameter> abc1 = getA_B_Cv1();
    assertThrown(new ExpectedThrowable(NullPointerException.class), 
      new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        AttributesOverlay.getAttributeValue(null, abc1);
      }
    });
    assertNull(AttributesOverlay.getAttributeValue("null", abc1));
    assertNull(AttributesOverlay.getAttributeValue("A", abc1));
    assertEquals("1", AttributesOverlay.getAttributeValue("C", abc1));
    
    List<I_Parameter> nullAbc1 = new ArrayList<I_Parameter>();
    nullAbc1.add(null);
    nullAbc1.addAll(abc1);
    assertEquals("1", AttributesOverlay.getAttributeValue("C", nullAbc1));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodGetAttributeValues() {
    List<I_Parameter> abc1 = getA_B_Cv1();
    assertThrown(new ExpectedThrowable(NullPointerException.class), 
      new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        AttributesOverlay.getAttributeValue(null, abc1);
      }
    });
    List<String> result = AttributesOverlay.getAttributeValues("null", abc1);
    assertEquals(0, result.size());
    
    result = AttributesOverlay.getAttributeValues("A", abc1);
    assertEquals(0, result.size());
    
    result = AttributesOverlay.getAttributeValues("C", abc1);
    assertContains(result, "1");
    assertEquals(1, result.size());
    
    List<I_Parameter> nullAbc1 = new ArrayList<I_Parameter>();
    nullAbc1.add(null);
    nullAbc1.addAll(abc1);
    nullAbc1.add(getBv1());
    nullAbc1.add(getBv2());
    
    result = AttributesOverlay.getAttributeValues("B", nullAbc1);
    assertContains(result, "1");
    assertContains(result, "2");
    assertEquals(2, result.size());
  }
  
  private ParameterMutant getA() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("A");
    return pm;
  }

  private ParameterMutant getB() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("B");
    return pm;
  }
  
  private ParameterMutant getBv1() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("B");
    pm.setValue("1");
    return pm;
  }

  private ParameterMutant getBv2() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("B");
    pm.setValue("2");
    return pm;
  }
  
  private ParameterMutant getCv1() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("C");
    pm.setValue("1");
    return pm;
  }
  
  private ParameterMutant getDv4() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("D");
    pm.setValue("4");
    return pm;
  }
  
  private ParameterMutant getEv5() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("E");
    pm.setValue("5");
    return pm;
  }
  
  private ParameterMutant getFv6() {
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("F");
    pm.setValue("6");
    return pm;
  }
  
  private List<I_Parameter> getA_B_Cv1() {
    List<I_Parameter> ret = new ArrayList<I_Parameter>();
    ret.add(getA());
    ret.add(getB());
    ret.add(getCv1());
    return ret;
  }
  
  private List<I_Parameter> newList(I_Parameter p) {
    List<I_Parameter> ret = new ArrayList<I_Parameter>();
    ret.add(p);
    return ret;
  }
}

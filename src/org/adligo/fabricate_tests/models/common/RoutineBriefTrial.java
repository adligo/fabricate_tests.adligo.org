package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.DuplicateRoutineException;
import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBrief;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.routines.DependenciesQueueRoutine;
import org.adligo.fabricate.routines.ProjectBriefQueueRoutine;
import org.adligo.fabricate.routines.ProjectQueueRoutine;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StageType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SourceFileScope (sourceClass=RoutineBrief.class, minCoverage=86.0)
public class RoutineBriefTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyInterfaceSimple() {
    I_RoutineBrief brief = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethod = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              ProjectBriefQueueRoutine.class, true);
    doAnswer(getClassMethod).when(brief).getClazz();
    when(brief.getName()).thenReturn("go");
    when(brief.getOrigin()).thenReturn(RoutineBriefOrigin.COMMAND);
    when(brief.getNestedRoutines()).thenReturn(null);
    when(brief.getParameters()).thenReturn(null);
    
    RoutineBrief copy  = new RoutineBrief(brief);
    assertEquals(ProjectBriefQueueRoutine.class, copy.getClazz());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertSame(RoutineBriefOrigin.COMMAND, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertNull(copy.getNestedRoutine(""));
    assertSame(Collections.emptyList(), copy.getParameters());
    assertNull(copy.getParameter("l"));
    assertEquals(0, copy.getParameters("").size());
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testConstructorCopyInterfaceStageTasksAndParams() {
    I_RoutineBrief brief = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethod = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              ProjectBriefQueueRoutine.class, true);
    doAnswer(getClassMethod).when(brief).getClazz();
    when(brief.getName()).thenReturn("go");
    when(brief.getNestedRoutines()).thenReturn(null);
    List<I_Parameter> params = ParameterMutant.convert(ParameterMutantTrial.createParams());
    when(brief.getParameters()).thenReturn(params);
    when(brief.isOptional()).thenReturn(true);
    
    I_RoutineBrief briefA = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethodA = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              ProjectQueueRoutine.class, true);
    doAnswer(getClassMethodA).when(briefA).getClazz();
    when(briefA.getName()).thenReturn("goA");
    when(briefA.getNestedRoutines()).thenReturn(null);
    when(briefA.getParameters()).thenReturn(params);
    when(briefA.isOptional()).thenReturn(true);
    
    I_RoutineBrief briefB = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethodB = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              DependenciesQueueRoutine.class, true);
    doAnswer(getClassMethodB).when(briefB).getClazz();
    when(briefB.getName()).thenReturn("goB");
    when(briefB.getNestedRoutines()).thenReturn(null);
    when(briefB.getParameters()).thenReturn(params);
    
    List<I_RoutineBrief> nested = new ArrayList<I_RoutineBrief>();
    nested.add(briefA);
    nested.add(briefB);
    when(brief.getNestedRoutines()).thenReturn(nested);

    when(brief.getOrigin()).thenReturn(RoutineBriefOrigin.STAGE);
    when(briefA.getOrigin()).thenReturn(RoutineBriefOrigin.STAGE_TASK);
    when(briefB.getOrigin()).thenReturn(RoutineBriefOrigin.STAGE_TASK);
    
    RoutineBrief copy  = new RoutineBrief(brief);
    assertEquals(ProjectBriefQueueRoutine.class, copy.getClazz());
    assertEquals("go", copy.getName());
    assertTrue(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE, copy.getOrigin());
    ParameterTrial.assertConvertedParams(copy.getParameters(), this);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", copy.getParameters().getClass().getName());
    
    List<I_RoutineBrief> routines =  copy.getNestedRoutines();
    I_RoutineBrief nest0  = (RoutineBrief) routines.get(0);
    assertEquals(ProjectQueueRoutine.class, nest0.getClazz());
    assertEquals("goA", nest0.getName());
    assertTrue(nest0.isOptional());
    assertSame(RoutineBriefOrigin.STAGE_TASK, nest0.getOrigin());
    ParameterTrial.assertConvertedParams(nest0.getParameters(), this);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", nest0.getParameters().getClass().getName());
    assertSame(nest0, copy.getNestedRoutine("goA"));
    
    I_RoutineBrief nest1  = (RoutineBrief) routines.get(1);
    assertEquals(DependenciesQueueRoutine.class, nest1.getClazz());
    assertEquals("goB", nest1.getName());
    assertFalse(nest1.isOptional());
    assertSame(RoutineBriefOrigin.STAGE_TASK, nest1.getOrigin());
    ParameterTrial.assertConvertedParams(nest1.getParameters(), this);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", nest1.getParameters().getClass().getName());
    assertSame(nest1, copy.getNestedRoutine("goB"));
  }

  @SuppressWarnings({"unused", "boxing"})
  @Test
  public void testConstructorDuplicateNestedException() {
    I_RoutineBrief brief = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethod = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              ProjectBriefQueueRoutine.class, true);
    doAnswer(getClassMethod).when(brief).getClazz();
    when(brief.getName()).thenReturn("go");
    when(brief.getNestedRoutines()).thenReturn(null);
    List<I_Parameter> params = ParameterMutant.convert(ParameterMutantTrial.createParams());
    when(brief.getParameters()).thenReturn(params);
    when(brief.isOptional()).thenReturn(true);
    
    I_RoutineBrief briefA = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethodA = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              ProjectQueueRoutine.class, true);
    doAnswer(getClassMethodA).when(briefA).getClazz();
    when(briefA.getName()).thenReturn("goA");
    when(briefA.getNestedRoutines()).thenReturn(null);
    when(briefA.getParameters()).thenReturn(params);
    when(briefA.isOptional()).thenReturn(true);
    
    List<I_RoutineBrief> nested = new ArrayList<I_RoutineBrief>();
    nested.add(briefA);
    nested.add(briefA);
    when(brief.getNestedRoutines()).thenReturn(nested);

    when(brief.getOrigin()).thenReturn(RoutineBriefOrigin.STAGE);
    when(briefA.getOrigin()).thenReturn(RoutineBriefOrigin.STAGE_TASK);
    
    DuplicateRoutineException caught = null;
    try {
      new RoutineBrief(brief);
    } catch (DuplicateRoutineException x){
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("goA", caught.getName());
    assertSame(RoutineBriefOrigin.STAGE_TASK, caught.getOrigin());
    assertEquals("go", caught.getParentName());
    assertSame(RoutineBriefOrigin.STAGE, caught.getParentOrigin());
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorSimpleExceptions() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBrief(new RoutineBriefMutant());
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("origin")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            RoutineBriefMutant rbm = new RoutineBriefMutant();
            rbm.setName("dn");
            rbm.setClazz(DependenciesQueueRoutine.class);
            new RoutineBrief(rbm);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("clazz_ != null")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            RoutineBriefMutant rbm = new RoutineBriefMutant();
            rbm.setName("dn");
            rbm.setClazz(DependenciesQueueRoutine.class);
            rbm.setOrigin(RoutineBriefOrigin.PROJECT_COMMAND);
            new RoutineBrief(rbm);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("clazz_ != null")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            RoutineBriefMutant rbm = new RoutineBriefMutant();
            rbm.setName("dn");
            rbm.setClazz(DependenciesQueueRoutine.class);
            rbm.setOrigin(RoutineBriefOrigin.PROJECT_STAGE);
            new RoutineBrief(rbm);
          }
        });
  }
  
  @Test
  public void testConstructorDefaults() {
    RoutineBriefMutant copy  = new RoutineBriefMutant();
    assertNull(copy.getClazz());
    assertNull(copy.getName());
    assertFalse(copy.isOptional());
    assertNull(copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
  }
  
  
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetNestedParameter() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName("name");
    rbm.setOrigin(RoutineBriefOrigin.COMMAND);
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("nr");
    pm.setValue("nr");
    rbm.setParameters(Collections.singletonList(pm));
    
    RoutineBrief rb = new RoutineBrief(rbm);
    assertTrue(rb.hasParameter("nr"));
    assertFalse(rb.hasParameter("key"));
    List<String> params = rb.getParameters("nr");
    assertContains(params, "nr");
    assertEquals(1, params.size());
    ParameterMutant nest = new ParameterMutant();
    nest.setKey("nr");
    rbm.setParameters(Collections.singletonList(nest));
    rb = new RoutineBrief(rbm);
    
    assertNull(rb.getNestedRoutine("nr"));
    nest.setValue("nrVal");
    rb = new RoutineBrief(rbm);
    
    assertEquals("nrVal", rb.getParameter("nr"));
    assertNull(rb.getParameter("k"));
    params = rb.getParameters("nr");
    assertContains(params, "nrVal");
    assertEquals(1, params.size());
    
    rbm.setParameters(Collections.singletonList(new Parameter(nest)));
    rb = new RoutineBrief(rbm);
    
    assertEquals("nrVal", rb.getParameter("nr"));
    assertNull(rb.getParameter("k"));
    params = rb.getParameters("nr");
    assertContains(params, "nrVal");
    assertEquals(1, params.size());
    
    pm = new ParameterMutant();
    pm.setKey("nr");
    pm.setValue("nrVal2");
    rbm.addParameter(pm);
    rb = new RoutineBrief(rbm);
    
    assertEquals("nrVal", rb.getParameter("nr"));
    assertNull(rb.getParameter("k"));
    params = rb.getParameters("nr");
    assertContains(params, "nrVal");
    assertContains(params, "nrVal2");
    assertEquals(2, params.size());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    RoutineBriefMutant a1 = new RoutineBriefMutant();
    a1.setName("a");
    a1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    RoutineBriefMutant b1 = new RoutineBriefMutant();
    b1.setName("b");
    b1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    RoutineBriefMutant c1 = new RoutineBriefMutant();
    c1.setName("c");
    c1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    RoutineBriefMutant d1 = new RoutineBriefMutant();
    d1.setName("d");
    d1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    d1.setClazz(EncryptTrait.class);
    RoutineBriefMutant e1 = new RoutineBriefMutant();
    e1.setName("e");
    e1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    e1.setClazz(DecryptTrait.class);
    RoutineBriefMutant f1 = new RoutineBriefMutant();
    f1.setName("f");
    f1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    f1.setOptional(true);
    RoutineBriefMutant g1 = new RoutineBriefMutant();
    g1.setName("g");
    g1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    g1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    RoutineBriefMutant h1 = new RoutineBriefMutant();
    h1.setName("h");
    h1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    h1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE_TASK);
    RoutineBriefMutant i1 = new RoutineBriefMutant();
    i1.setName("i");
    i1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    ParameterMutant pmI1 = new ParameterMutant();
    i1.addParameter(pmI1);
    RoutineBriefMutant j1 = new RoutineBriefMutant();
    j1.setName("j");
    j1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    ParameterMutant pmJ1 = new ParameterMutant();
    pmJ1.setKey("keyJ");
    j1.addParameter(pmJ1);
    
    RoutineBriefMutant k1 = new RoutineBriefMutant();
    k1.setName("k");
    k1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    k1.addNestedRoutine(a1);
    RoutineBriefMutant l1 = new RoutineBriefMutant();
    l1.setName("l");
    l1.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    l1.addNestedRoutine(b1);
    
    RoutineBrief a = new RoutineBrief(a1);
    RoutineBrief aa = new RoutineBrief(a1);
    RoutineBrief b = new RoutineBrief(b1);
    RoutineBrief c = new RoutineBrief(c1);
    RoutineBrief d = new RoutineBrief(d1);
    RoutineBrief e = new RoutineBrief(e1);
    RoutineBrief f = new RoutineBrief(f1);
    RoutineBrief g = new RoutineBrief(g1);
    RoutineBrief h = new RoutineBrief(h1);
    RoutineBrief i = new RoutineBrief(i1);
    RoutineBrief j = new RoutineBrief(j1);
    RoutineBrief k = new RoutineBrief(k1);
    RoutineBrief l = new RoutineBrief(l1);
    
    assertEquals(a.hashCode(), a.hashCode());
    assertEquals(aa.hashCode(), a.hashCode());
    assertEquals(a.hashCode(), aa.hashCode());
    assertEquals(a.hashCode(), a1.hashCode());
    assertNotEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a.hashCode(), c.hashCode());
    assertNotEquals(a.hashCode(), d.hashCode());
    assertNotEquals(a.hashCode(), e.hashCode());
    assertNotEquals(a.hashCode(), f.hashCode());
    assertNotEquals(a.hashCode(), g.hashCode());
    assertNotEquals(a.hashCode(), h.hashCode());
    assertNotEquals(a.hashCode(), i.hashCode());
    assertNotEquals(a.hashCode(), j.hashCode());
    assertNotEquals(a.hashCode(), k.hashCode());
    assertNotEquals(a.hashCode(), l.hashCode());
    assertEquals(a, a);
    assertEquals(a, a1);
    assertEquals(a, aa);
    assertEquals(aa, a);
    assertNotEquals(a, b);
    assertNotEquals(a, c);
    assertNotEquals(a, d);
    assertNotEquals(a, e);
    assertNotEquals(a, f);
    assertNotEquals(a, g);
    assertNotEquals(a, h);
    assertNotEquals(a, i);
    assertNotEquals(a, j);
    assertNotEquals(a, k);
    assertNotEquals(a, l);
    assertEquals("RoutineBrief [name=a, class=null]", a.toString());
    
    assertEquals(b.hashCode(), b.hashCode());
    assertNotEquals(b.hashCode(), a.hashCode());
    assertNotEquals(b.hashCode(), c.hashCode());
    assertNotEquals(b.hashCode(), d.hashCode());
    assertNotEquals(b.hashCode(), e.hashCode());
    assertNotEquals(b.hashCode(), f.hashCode());
    assertNotEquals(b.hashCode(), g.hashCode());
    assertNotEquals(b.hashCode(), h.hashCode());
    assertNotEquals(b.hashCode(), i.hashCode());
    assertNotEquals(b.hashCode(), j.hashCode());
    assertNotEquals(b.hashCode(), k.hashCode());
    assertNotEquals(b.hashCode(), l.hashCode());
    assertEquals(b, b);
    assertNotEquals(b, a);
    assertNotEquals(b, c);
    assertNotEquals(b, d);
    assertNotEquals(b, e);
    assertNotEquals(b, f);
    assertNotEquals(b, g);
    assertNotEquals(b, h);
    assertNotEquals(b, i);
    assertNotEquals(b, j);
    assertNotEquals(b, k);
    assertNotEquals(b, l);
    assertEquals("RoutineBrief [name=b, class=null]", b.toString());
    
    assertEquals(c.hashCode(), c.hashCode());
    assertNotEquals(c.hashCode(), a.hashCode());
    assertNotEquals(c.hashCode(), b.hashCode());
    assertNotEquals(c.hashCode(), d.hashCode());
    assertNotEquals(c.hashCode(), e.hashCode());
    assertNotEquals(c.hashCode(), f.hashCode());
    assertNotEquals(c.hashCode(), g.hashCode());
    assertNotEquals(c.hashCode(), h.hashCode());
    assertNotEquals(c.hashCode(), i.hashCode());
    assertNotEquals(c.hashCode(), j.hashCode());
    assertNotEquals(c.hashCode(), k.hashCode());
    assertNotEquals(c.hashCode(), l.hashCode());
    assertEquals(c, c);
    assertNotEquals(c, a);
    assertNotEquals(c, b);
    assertNotEquals(c, d);
    assertNotEquals(c, e);
    assertNotEquals(c, f);
    assertNotEquals(c, g);
    assertNotEquals(c, h);
    assertNotEquals(c, i);
    assertNotEquals(c, j);
    assertNotEquals(c, k);
    assertNotEquals(c, l);
    assertEquals("RoutineBrief [name=c, class=null]", c.toString());
    
    assertEquals(d.hashCode(), d.hashCode());
    assertNotEquals(d.hashCode(), a.hashCode());
    assertNotEquals(d.hashCode(), b.hashCode());
    assertNotEquals(d.hashCode(), c.hashCode());
    assertNotEquals(d.hashCode(), e.hashCode());
    assertNotEquals(d.hashCode(), f.hashCode());
    assertNotEquals(d.hashCode(), g.hashCode());
    assertNotEquals(d.hashCode(), h.hashCode());
    assertNotEquals(d.hashCode(), i.hashCode());
    assertNotEquals(d.hashCode(), j.hashCode());
    assertNotEquals(d.hashCode(), k.hashCode());
    assertNotEquals(d.hashCode(), l.hashCode());
    assertEquals(d, d);
    assertNotEquals(d, a);
    assertNotEquals(d, b);
    assertNotEquals(d, c);
    assertNotEquals(d, e);
    assertNotEquals(d, f);
    assertNotEquals(d, g);
    assertNotEquals(d, h);
    assertNotEquals(d, i);
    assertNotEquals(d, j);
    assertNotEquals(d, k);
    assertNotEquals(d, l);
    assertEquals("RoutineBrief [name=d, class=org.adligo.fabricate.routines.implicit.EncryptTrait]", d.toString());
  
    assertEquals(e.hashCode(), e.hashCode());
    assertNotEquals(e.hashCode(), a.hashCode());
    assertNotEquals(e.hashCode(), b.hashCode());
    assertNotEquals(e.hashCode(), c.hashCode());
    assertNotEquals(e.hashCode(), d.hashCode());
    assertNotEquals(e.hashCode(), f.hashCode());
    assertNotEquals(e.hashCode(), g.hashCode());
    assertNotEquals(e.hashCode(), h.hashCode());
    assertNotEquals(e.hashCode(), i.hashCode());
    assertNotEquals(e.hashCode(), j.hashCode());
    assertNotEquals(e.hashCode(), k.hashCode());
    assertNotEquals(e.hashCode(), l.hashCode());
    assertEquals(e, e);
    assertNotEquals(e, a);
    assertNotEquals(e, b);
    assertNotEquals(e, c);
    assertNotEquals(e, d);
    assertNotEquals(e, f);
    assertNotEquals(e, g);
    assertNotEquals(e, h);
    assertNotEquals(e, i);
    assertNotEquals(e, j);
    assertNotEquals(e, k);
    assertNotEquals(e, l);
    assertEquals("RoutineBrief [name=e, class=org.adligo.fabricate.routines.implicit.DecryptTrait]", e.toString());
  
    assertEquals(f.hashCode(), f.hashCode());
    assertNotEquals(f.hashCode(), a.hashCode());
    assertNotEquals(f.hashCode(), b.hashCode());
    assertNotEquals(f.hashCode(), c.hashCode());
    assertNotEquals(f.hashCode(), d.hashCode());
    assertNotEquals(f.hashCode(), e.hashCode());
    assertNotEquals(f.hashCode(), g.hashCode());
    assertNotEquals(f.hashCode(), h.hashCode());
    assertNotEquals(f.hashCode(), i.hashCode());
    assertNotEquals(f.hashCode(), j.hashCode());
    assertNotEquals(f.hashCode(), k.hashCode());
    assertNotEquals(f.hashCode(), l.hashCode());
    assertEquals(f, f);
    assertNotEquals(f, a);
    assertNotEquals(f, b);
    assertNotEquals(f, c);
    assertNotEquals(f, d);
    assertNotEquals(f, e);
    assertNotEquals(f, g);
    assertNotEquals(f, h);
    assertNotEquals(f, i);
    assertNotEquals(f, j);
    assertNotEquals(f, k);
    assertNotEquals(f, l);
    assertEquals("RoutineBrief [name=f, class=null]", f.toString());
    
    assertEquals(g.hashCode(), g.hashCode());
    assertNotEquals(g.hashCode(), a.hashCode());
    assertNotEquals(g.hashCode(), b.hashCode());
    assertNotEquals(g.hashCode(), c.hashCode());
    assertNotEquals(g.hashCode(), d.hashCode());
    assertNotEquals(g.hashCode(), e.hashCode());
    assertNotEquals(g.hashCode(), f.hashCode());
    assertNotEquals(g.hashCode(), h.hashCode());
    assertNotEquals(g.hashCode(), i.hashCode());
    assertNotEquals(g.hashCode(), j.hashCode());
    assertNotEquals(g.hashCode(), k.hashCode());
    assertNotEquals(g.hashCode(), l.hashCode());
    assertEquals(g, g);
    assertNotEquals(g, a);
    assertNotEquals(g, b);
    assertNotEquals(g, c);
    assertNotEquals(g, d);
    assertNotEquals(g, e);
    assertNotEquals(g, f);
    assertNotEquals(g, h);
    assertNotEquals(g, i);
    assertNotEquals(g, j);
    assertNotEquals(g, k);
    assertNotEquals(g, l);
    assertEquals("RoutineBrief [name=g, class=null]", g.toString());
    
    assertEquals(h.hashCode(), h.hashCode());
    assertNotEquals(h.hashCode(), a.hashCode());
    assertNotEquals(h.hashCode(), b.hashCode());
    assertNotEquals(h.hashCode(), c.hashCode());
    assertNotEquals(h.hashCode(), d.hashCode());
    assertNotEquals(h.hashCode(), e.hashCode());
    assertNotEquals(h.hashCode(), f.hashCode());
    assertNotEquals(h.hashCode(), g.hashCode());
    assertNotEquals(h.hashCode(), i.hashCode());
    assertNotEquals(h.hashCode(), j.hashCode());
    assertNotEquals(h.hashCode(), k.hashCode());
    assertNotEquals(h.hashCode(), l.hashCode());
    assertEquals(h, h);
    assertNotEquals(h, a);
    assertNotEquals(h, b);
    assertNotEquals(h, c);
    assertNotEquals(h, d);
    assertNotEquals(h, e);
    assertNotEquals(h, f);
    assertNotEquals(h, g);
    assertNotEquals(h, i);
    assertNotEquals(h, j);
    assertNotEquals(h, k);
    assertNotEquals(h, l);
    assertEquals("RoutineBrief [name=h, class=null]", h.toString());
  
    assertEquals(i.hashCode(), i.hashCode());
    assertNotEquals(i.hashCode(), a.hashCode());
    assertNotEquals(i.hashCode(), b.hashCode());
    assertNotEquals(i.hashCode(), c.hashCode());
    assertNotEquals(i.hashCode(), d.hashCode());
    assertNotEquals(i.hashCode(), e.hashCode());
    assertNotEquals(i.hashCode(), f.hashCode());
    assertNotEquals(i.hashCode(), g.hashCode());
    assertNotEquals(i.hashCode(), h.hashCode());
    assertNotEquals(i.hashCode(), j.hashCode());
    assertNotEquals(i.hashCode(), k.hashCode());
    assertNotEquals(i.hashCode(), l.hashCode());
    assertEquals(i, i);
    assertNotEquals(i, a);
    assertNotEquals(i, b);
    assertNotEquals(i, c);
    assertNotEquals(i, d);
    assertNotEquals(i, e);
    assertNotEquals(i, f);
    assertNotEquals(i, g);
    assertNotEquals(i, h);
    assertNotEquals(i, j);
    assertNotEquals(i, k);
    assertNotEquals(i, l);
    assertEquals("RoutineBrief [name=i, class=null," + System.lineSeparator() +
        "\tParameter [key=null, value=null]" + System.lineSeparator() +
        "]", i.toString());
  
    assertEquals(j.hashCode(), j.hashCode());
    assertNotEquals(j.hashCode(), a.hashCode());
    assertNotEquals(j.hashCode(), b.hashCode());
    assertNotEquals(j.hashCode(), c.hashCode());
    assertNotEquals(j.hashCode(), d.hashCode());
    assertNotEquals(j.hashCode(), e.hashCode());
    assertNotEquals(j.hashCode(), f.hashCode());
    assertNotEquals(j.hashCode(), g.hashCode());
    assertNotEquals(j.hashCode(), h.hashCode());
    assertNotEquals(j.hashCode(), i.hashCode());
    assertNotEquals(j.hashCode(), k.hashCode());
    assertNotEquals(j.hashCode(), l.hashCode());
    assertEquals(j, j);
    assertNotEquals(j, a);
    assertNotEquals(j, b);
    assertNotEquals(j, c);
    assertNotEquals(j, d);
    assertNotEquals(j, e);
    assertNotEquals(j, f);
    assertNotEquals(j, g);
    assertNotEquals(j, h);
    assertNotEquals(j, i);
    assertNotEquals(j, k);
    assertNotEquals(j, l);
    assertEquals("RoutineBrief [name=j, class=null," + System.lineSeparator() +
          "\tParameter [key=keyJ, value=null]" + System.lineSeparator() +
          "]", j.toString());
    
    assertEquals(k.hashCode(), k.hashCode());
    assertNotEquals(k.hashCode(), a.hashCode());
    assertNotEquals(k.hashCode(), b.hashCode());
    assertNotEquals(k.hashCode(), c.hashCode());
    assertNotEquals(k.hashCode(), d.hashCode());
    assertNotEquals(k.hashCode(), e.hashCode());
    assertNotEquals(k.hashCode(), f.hashCode());
    assertNotEquals(k.hashCode(), g.hashCode());
    assertNotEquals(k.hashCode(), h.hashCode());
    assertNotEquals(k.hashCode(), i.hashCode());
    assertNotEquals(k.hashCode(), j.hashCode());
    assertNotEquals(k.hashCode(), l.hashCode());
    assertEquals(k, k);
    assertNotEquals(k, a);
    assertNotEquals(k, b);
    assertNotEquals(k, c);
    assertNotEquals(k, d);
    assertNotEquals(k, e);
    assertNotEquals(k, f);
    assertNotEquals(k, g);
    assertNotEquals(k, h);
    assertNotEquals(k, i);
    assertNotEquals(k, j);
    assertNotEquals(k, l);
    assertEquals("RoutineBrief [name=k, class=null," + System.lineSeparator() +
        "\tRoutineBrief [name=a, class=null]" + System.lineSeparator() +
        "]", k.toString());
  
    assertEquals(l.hashCode(), l.hashCode());
    assertNotEquals(l.hashCode(), a.hashCode());
    assertNotEquals(l.hashCode(), b.hashCode());
    assertNotEquals(l.hashCode(), c.hashCode());
    assertNotEquals(l.hashCode(), d.hashCode());
    assertNotEquals(l.hashCode(), e.hashCode());
    assertNotEquals(l.hashCode(), f.hashCode());
    assertNotEquals(l.hashCode(), g.hashCode());
    assertNotEquals(l.hashCode(), h.hashCode());
    assertNotEquals(l.hashCode(), i.hashCode());
    assertNotEquals(l.hashCode(), j.hashCode());
    assertNotEquals(l.hashCode(), k.hashCode());
    assertEquals(l, l);
    assertNotEquals(l, a);
    assertNotEquals(l, b);
    assertNotEquals(l, c);
    assertNotEquals(l, d);
    assertNotEquals(l, e);
    assertNotEquals(l, f);
    assertNotEquals(l, g);
    assertNotEquals(l, h);
    assertNotEquals(l, i);
    assertNotEquals(l, j);
    assertNotEquals(l, k);
    assertEquals("RoutineBrief [name=l, class=null," + System.lineSeparator() +
        "\tRoutineBrief [name=b, class=null]" + System.lineSeparator() +
        "]", l.toString());
  }
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodConvert() throws Exception {
    Map<String, I_RoutineBrief> routinesFromNull = RoutineBrief.convert(null, null);
    assertEquals(0, routinesFromNull.size());
    
    List<RoutineParentType> list = new ArrayList<RoutineParentType>();
    RoutineParentType cmd = new RoutineParentType();
    cmd.setName("eclipse");
    cmd.setClazz(EncryptTrait.class.getName());
    list.add(cmd);
    
    RoutineParentType cmd2 = new RoutineParentType();
    cmd2.setName("build");
    list.add(cmd2);
    
    RoutineParentType cmd3 = new RoutineParentType();
    cmd3.setName("vouch");
    list.add(cmd3);
    
    Map<String, I_RoutineBrief> routines = 
        RoutineBrief.convert(list, RoutineBriefOrigin.COMMAND);
    I_RoutineBrief rb = routines.get("eclipse");
    assertEquals("eclipse", rb.getName());
    assertEquals(EncryptTrait.class.getName(), rb.getClazz().getName());
    assertEquals(RoutineBrief.class.getName(), rb.getClass().getName());
    
    I_RoutineBrief rb1 = routines.get("build");
    assertEquals("build", rb1.getName());
    assertNull(rb1.getClazz());
    assertEquals(RoutineBrief.class.getName(), rb1.getClass().getName());
    
    I_RoutineBrief rb2 = routines.get("vouch");
    assertEquals("vouch", rb2.getName());
    assertNull(rb2.getClazz());
    assertEquals(RoutineBrief.class.getName(), rb2.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  public void testStaticMethodConvertStages() throws Exception {
    Map<String, I_RoutineBrief> routinesFromNull = RoutineBrief.convertStages(null, 
        RoutineBriefOrigin.FABRICATE_STAGE);
    assertEquals(0, routinesFromNull.size());
    
    List<StageType> list = new ArrayList<StageType>();
    
    StageType cmd = new StageType();
    cmd.setName("eclipse");
    cmd.setClazz(EncryptTrait.class.getName());
    list.add(cmd);
    
    StageType cmd2 = new StageType();
    cmd2.setName("build");
    list.add(cmd2);
    
    StageType cmd3 = new StageType();
    cmd3.setName("vouch");
    list.add(cmd3);
    
    Map<String, I_RoutineBrief> routines = RoutineBrief.convertStages(list, RoutineBriefOrigin.FABRICATE_STAGE);
    I_RoutineBrief rb = routines.get("eclipse");
    assertEquals("eclipse", rb.getName());
    assertEquals(EncryptTrait.class.getName(), rb.getClazz().getName());
    assertEquals(RoutineBrief.class.getName(), rb.getClass().getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE, rb.getOrigin());
    
    I_RoutineBrief rb1 = routines.get("build");
    assertEquals("build", rb1.getName());
    assertNull(rb1.getClazz());
    assertEquals(RoutineBrief.class.getName(), rb1.getClass().getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE, rb1.getOrigin());
    
    I_RoutineBrief rb2 = routines.get("vouch");
    assertEquals("vouch", rb2.getName());
    assertNull(rb2.getClazz());
    assertEquals(RoutineBrief.class.getName(), rb2.getClass().getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE, rb2.getOrigin());
  }
  
}

package org.adligo.fabricate_tests.models.common;

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
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineType;
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

@SourceFileScope (sourceClass=RoutineBriefMutant.class, minCoverage=81.0)
public class RoutineBriefMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorCopyCommandXml() throws Exception {
    RoutineParentType brief = mock(RoutineParentType.class);
    when(brief.getClazz()).thenReturn(ProjectBriefQueueRoutine.class.getName());
    when(brief.getName()).thenReturn("go");
    when(brief.getTask()).thenReturn(null);
    when(brief.getParams()).thenReturn(null);
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief, RoutineBriefOrigin.COMMAND);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.COMMAND, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    assertNull(copy.getParameter("hey"));
  }
  
  @Test
  public void testConstructorCopyCommandXmlTasksAndParams() throws Exception {
    RoutineParentType brief = new RoutineParentType();
    brief.setClazz(ProjectBriefQueueRoutine.class.getName());
    brief.setName("go");
    
    RoutineType briefTaskA = new RoutineType();
    briefTaskA.setClazz(ProjectQueueRoutine.class.getName());
    briefTaskA.setName("goA");
    ParamsType params = ParameterMutantTrial.createParams();
    briefTaskA.setParams(params);
    
    RoutineType briefTaskB = new RoutineType();
    briefTaskB.setClazz(DependenciesQueueRoutine.class.getName());
    briefTaskB.setName("goB");
    params = ParameterMutantTrial.createParams();
    briefTaskB.setParams(params);
    
    brief.getTask().add(briefTaskA);
    brief.getTask().add(briefTaskB);
    
    params = ParameterMutantTrial.createParams();
    brief.setParams(params);
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief, RoutineBriefOrigin.COMMAND);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.COMMAND, copy.getOrigin());
    
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
    List<I_RoutineBrief>  briefs = copy.getNestedRoutines();
    
    RoutineBriefMutant copySubA  = (RoutineBriefMutant) briefs.get(0);
    assertEquals(ProjectQueueRoutine.class.getName(), copySubA.getClazz().getName());
    assertEquals("goA", copySubA.getName());
    assertFalse(copySubA.isOptional());
    assertEquals(RoutineBriefOrigin.COMMAND_TASK, copySubA.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copySubA.getParameters(), this);
    
    RoutineBriefMutant copySubB  = (RoutineBriefMutant) briefs.get(1);
    assertEquals(DependenciesQueueRoutine.class.getName(), copySubB.getClazz().getName());
    assertEquals("goB", copySubB.getName());
    assertFalse(copySubB.isOptional());
    assertEquals(RoutineBriefOrigin.COMMAND_TASK, copySubB.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copySubB.getParameters(), this);
  }
  
  @Test
  public void testConstructorCopyInterfaceSimple() {
    I_RoutineBrief brief = mock(I_RoutineBrief.class);
    
    MockMethod<Class<? extends I_FabricationRoutine>> getClassMethod = 
          new MockMethod<Class<? extends I_FabricationRoutine>>(
              ProjectBriefQueueRoutine.class, true);
    doAnswer(getClassMethod).when(brief).getClazz();
    when(brief.getName()).thenReturn("go");
    when(brief.getNestedRoutines()).thenReturn(null);
    when(brief.getParameters()).thenReturn(null);
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class, copy.getClazz());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertNull(copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    
    when(brief.getOrigin()).thenReturn(RoutineBriefOrigin.COMMAND);
    
    copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class, copy.getClazz());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertSame(RoutineBriefOrigin.COMMAND, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
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
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class, copy.getClazz());
    assertEquals("go", copy.getName());
    assertTrue(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE, copy.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
    
    List<I_RoutineBrief> routines =  copy.getNestedRoutines();
    copy  = (RoutineBriefMutant) routines.get(0);
    assertEquals(ProjectQueueRoutine.class, copy.getClazz());
    assertEquals("goA", copy.getName());
    assertTrue(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE_TASK, copy.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
    
    copy  = (RoutineBriefMutant) routines.get(1);
    assertEquals(DependenciesQueueRoutine.class, copy.getClazz());
    assertEquals("goB", copy.getName());
    assertFalse(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE_TASK, copy.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
    
  }

  @Test
  public void testConstructorCopyStageXml() throws Exception {
    StageType brief = mock(StageType.class);
    
    when(brief.getClazz()).thenReturn(ProjectBriefQueueRoutine.class.getName());
    when(brief.getName()).thenReturn("go");
    when(brief.getTask()).thenReturn(null);
    when(brief.getParams()).thenReturn(null);
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief, RoutineBriefOrigin.FABRICATE_STAGE);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    
    when(brief.getParams()).thenReturn(new ParamsType());
    copy  = new RoutineBriefMutant(brief, RoutineBriefOrigin.FABRICATE_STAGE);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    
    when(brief.getTask()).thenReturn(Collections.emptyList());
    ParamsType params = ParameterMutantTrial.createParams();
    when(brief.getParams()).thenReturn(params);
    
    copy  = new RoutineBriefMutant(brief, RoutineBriefOrigin.FABRICATE_STAGE);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertSame(RoutineBriefOrigin.FABRICATE_STAGE, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyStageParamsAndTaskXml() throws Exception {
    StageType brief = mock(StageType.class);
    
    when(brief.getClazz()).thenReturn(ProjectBriefQueueRoutine.class.getName());
    when(brief.getName()).thenReturn("go");
    
    List<RoutineType> routines = new ArrayList<RoutineType>();
    when(brief.getTask()).thenReturn(routines);
    when(brief.getParams()).thenReturn(ParameterMutantTrial.createParams());
    when(brief.isOptional()).thenReturn(true);
    
    RoutineType briefTaskA = mock(RoutineType.class);
    
    when(briefTaskA.getClazz()).thenReturn(ProjectQueueRoutine.class.getName());
    when(briefTaskA.getName()).thenReturn("goA");
    when(briefTaskA.getParams()).thenReturn(ParameterMutantTrial.createParams());
    
    RoutineType briefTaskB = mock(RoutineType.class);
    
    when(briefTaskB.getClazz()).thenReturn(DependenciesQueueRoutine.class.getName());
    when(briefTaskB.getName()).thenReturn("goB");
    when(briefTaskB.getParams()).thenReturn(ParameterMutantTrial.createParams());
    
    routines.add(briefTaskA);
    routines.add(briefTaskB);
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief, RoutineBriefOrigin.FABRICATE_STAGE);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE, copy.getOrigin());
    assertTrue(copy.isOptional());
    
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
    List<I_RoutineBrief>  briefs = copy.getNestedRoutines();
    
    RoutineBriefMutant copySubA  = (RoutineBriefMutant) briefs.get(0);
    assertEquals(ProjectQueueRoutine.class.getName(), copySubA.getClazz().getName());
    assertEquals("goA", copySubA.getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE_TASK, copySubA.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copySubA.getParameters(), this);
    assertFalse(copySubA.isOptional());
    
    RoutineBriefMutant copySubB  = (RoutineBriefMutant) briefs.get(1);
    assertEquals(DependenciesQueueRoutine.class.getName(), copySubB.getClazz().getName());
    assertEquals("goB", copySubB.getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_STAGE_TASK, copySubB.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copySubB.getParameters(), this);
    assertFalse(copySubB.isOptional());
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() throws Exception {

    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant(null, null, RoutineBriefOrigin.COMMAND);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("origin")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("dn", "", null);
          }
        });
    assertThrown(new ExpectedThrowable(new ClassNotFoundException("cn")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("dn", "cn", RoutineBriefOrigin.COMMAND);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("origin")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("dn", DependenciesQueueRoutine.class.getName(), null);
          }
        });
    //passthrough exceptions
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant(new RoutineParentType(),
                RoutineBriefOrigin.PROJECT_COMMAND);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant(new RoutineType(), RoutineBriefOrigin.COMMAND_TASK);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant(new StageType(), null);
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
  
  @Test
  public void testMethodGetNestedRoutine() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    
    assertNull(rbm.getNestedRoutine("nr"));
    RoutineBriefMutant nest = new RoutineBriefMutant();
    nest.setName("nr");
    nest.setOrigin(RoutineBriefOrigin.COMMAND);
    rbm.setNestedRoutines(Collections.singletonList(nest));
    
    assertSame(nest, rbm.getNestedRoutine("nr"));
    
    rbm.setNestedRoutines(Collections.singletonList(new RoutineBrief(nest)));
    I_RoutineBrief nestActual = rbm.getNestedRoutine("nr");
    assertEquals("nr", nestActual.getName());
    assertEquals(RoutineBriefOrigin.COMMAND, nestActual.getOrigin());
    assertEquals(RoutineBriefMutant.class.getName(), nest.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetNestedParameter() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("nr");
    pm.setValue("nr");
    rbm.setParameters(Collections.singletonList(pm));
    
    assertTrue(rbm.hasParameter("nr"));
    assertFalse(rbm.hasParameter("key"));
    assertEquals("nr", rbm.getParameter("nr"));
    assertNull(rbm.getParameter("key"));
    List<String> params = rbm.getParameters("nr");
    assertContains(params, "nr");
    assertEquals(1, params.size());
    ParameterMutant nest = new ParameterMutant();
    nest.setKey("nr");
    rbm.setParameters(Collections.singletonList(nest));
    
    assertNull(rbm.getNestedRoutine("nr"));
    nest.setValue("nrVal");
    params = rbm.getParameters("nr");
    assertContains(params, "nrVal");
    assertEquals(1, params.size());
    
    rbm.setParameters(Collections.singletonList(new Parameter(nest)));
    params = rbm.getParameters("nr");
    assertContains(params, "nrVal");
    assertEquals(1, params.size());
    
    pm = new ParameterMutant();
    pm.setKey("nr");
    pm.setValue("nrVal2");
    rbm.addParameter(pm);
    
    params = rbm.getParameters("nr");
    assertContains(params, "nrVal");
    assertContains(params, "nrVal2");
    assertEquals(2, params.size());
  }
  
  
  @Test
  public void testMethodRemoveNestedRoutine() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    
    assertFalse(rbm.removeNestedRoutine("nr"));
    RoutineBriefMutant nest = new RoutineBriefMutant();
    nest.setName("nr");
    nest.setOrigin(RoutineBriefOrigin.COMMAND);
    rbm.setNestedRoutines(Collections.singletonList(nest));
    
    assertFalse(rbm.removeNestedRoutine("nr1"));
    assertTrue(rbm.removeNestedRoutine("nr"));
    
  }

  @SuppressWarnings("boxing")
  @Test
  public void testMethodRemoveParameter() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    ParameterMutant pm = new ParameterMutant();
    pm.setKey("nr");
    pm.setValue("nrVal");
    rbm.setParameters(Collections.singletonList(pm));
    
    pm = new ParameterMutant();
    pm.setKey("nr");
    pm.setValue("nrVal2");
    rbm.addParameter(pm);
    
    List<String> params = rbm.getParameters("nr");
    assertContains(params, "nrVal");
    assertContains(params, "nrVal2");
    assertEquals(2, params.size());
    
    rbm.removeParameters("nr");
    assertFalse(rbm.hasParameter("nr"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodConvert() throws Exception {
    Map<String, I_RoutineBrief>  routinesFromNull = RoutineBriefMutant.convert(null, null);
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
        RoutineBriefMutant.convert(list, RoutineBriefOrigin.COMMAND);
    I_RoutineBrief rb = routines.get("eclipse");
    assertEquals("eclipse", rb.getName());
    assertEquals(EncryptTrait.class.getName(), rb.getClazz().getName());
    assertEquals(RoutineBriefMutant.class.getName(), rb.getClass().getName());
    
    I_RoutineBrief rb1 = routines.get("build");
    assertEquals("build", rb1.getName());
    assertNull(rb1.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), rb1.getClass().getName());
    
    I_RoutineBrief rb2 = routines.get("vouch");
    assertEquals("vouch", rb2.getName());
    assertNull(rb2.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), rb2.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    RoutineBriefMutant a = new RoutineBriefMutant();
    RoutineBriefMutant b = new RoutineBriefMutant();
    b.setName("b");
    RoutineBriefMutant c = new RoutineBriefMutant();
    c.setName("c");
    RoutineBriefMutant d = new RoutineBriefMutant();
    d.setClazz(EncryptTrait.class);
    RoutineBriefMutant e = new RoutineBriefMutant();
    e.setClazz(DecryptTrait.class);
    RoutineBriefMutant f = new RoutineBriefMutant();
    f.setOptional(true);
    RoutineBriefMutant g = new RoutineBriefMutant();
    g.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE);
    RoutineBriefMutant h = new RoutineBriefMutant();
    h.setOrigin(RoutineBriefOrigin.ARCHIVE_STAGE_TASK);
    RoutineBriefMutant i = new RoutineBriefMutant();
    ParameterMutant pmI = new ParameterMutant();
    i.addParameter(pmI);
    RoutineBriefMutant j = new RoutineBriefMutant();
    ParameterMutant pmJ = new ParameterMutant();
    pmJ.setKey("keyJ");
    j.addParameter(pmJ);
    
    RoutineBriefMutant k = new RoutineBriefMutant();
    k.addNestedRoutine(a);
    RoutineBriefMutant l = new RoutineBriefMutant();
    l.addNestedRoutine(b);
    
    assertEquals(a.hashCode(), a.hashCode());
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
    assertEquals("RoutineBriefMutant [name=null, class=null]", a.toString());
    
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
    assertEquals("RoutineBriefMutant [name=b, class=null]", b.toString());
    
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
    assertEquals("RoutineBriefMutant [name=c, class=null]", c.toString());
    
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
    assertEquals("RoutineBriefMutant [name=null, class=org.adligo.fabricate.routines.implicit.EncryptTrait]", d.toString());
  
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
    assertEquals("RoutineBriefMutant [name=null, class=org.adligo.fabricate.routines.implicit.DecryptTrait]", e.toString());
  
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
    assertEquals("RoutineBriefMutant [name=null, class=null]", f.toString());
    
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
    assertEquals("RoutineBriefMutant [name=null, class=null]", g.toString());
    
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
    assertEquals("RoutineBriefMutant [name=null, class=null]", h.toString());
  
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
    assertEquals("RoutineBriefMutant [name=null, class=null," + System.lineSeparator() +
        "\tParameterMutant [key=null, value=null]" + System.lineSeparator() +
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
    assertEquals("RoutineBriefMutant [name=null, class=null," + System.lineSeparator() +
          "\tParameterMutant [key=keyJ, value=null]" + System.lineSeparator() +
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
    assertEquals("RoutineBriefMutant [name=null, class=null," + System.lineSeparator() +
        "\tRoutineBriefMutant [name=null, class=null]" + System.lineSeparator() +
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
    assertEquals("RoutineBriefMutant [name=null, class=null," + System.lineSeparator() +
        "\tRoutineBriefMutant [name=b, class=null]" + System.lineSeparator() +
        "]", l.toString());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodConvertStages() throws Exception {
    Map<String, I_RoutineBrief> routinesFromNull = RoutineBriefMutant.convertStages(null, null);
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
    
    Map<String, I_RoutineBrief>  routines = 
        RoutineBriefMutant.convertStages(list, RoutineBriefOrigin.FABRICATE_STAGE);
    I_RoutineBrief rb = routines.get("eclipse");
    assertEquals("eclipse", rb.getName());
    assertEquals(EncryptTrait.class.getName(), rb.getClazz().getName());
    assertEquals(RoutineBriefMutant.class.getName(), rb.getClass().getName());
    
    I_RoutineBrief rb1 = routines.get("build");
    assertEquals("build", rb1.getName());
    assertNull(rb1.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), rb1.getClass().getName());
    
    I_RoutineBrief rb2 = routines.get("vouch");
    assertEquals("vouch", rb2.getName());
    assertNull(rb2.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), rb2.getClass().getName());
  }
}

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

@SourceFileScope (sourceClass=RoutineBriefMutant.class, minCoverage=67.0)
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
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.STAGE, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    
    when(brief.getParams()).thenReturn(new ParamsType());
    copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.STAGE, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    
    when(brief.getTask()).thenReturn(Collections.emptyList());
    ParamsType params = ParameterMutantTrial.createParams();
    when(brief.getParams()).thenReturn(params);
    
    copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE, copy.getOrigin());
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
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertEquals(RoutineBriefOrigin.STAGE, copy.getOrigin());
    assertTrue(copy.isOptional());
    
    ParameterMutantTrial.assertConvertedParams(copy.getParameters(), this);
    List<I_RoutineBrief>  briefs = copy.getNestedRoutines();
    
    RoutineBriefMutant copySubA  = (RoutineBriefMutant) briefs.get(0);
    assertEquals(ProjectQueueRoutine.class.getName(), copySubA.getClazz().getName());
    assertEquals("goA", copySubA.getName());
    assertEquals(RoutineBriefOrigin.STAGE_TASK, copySubA.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copySubA.getParameters(), this);
    assertFalse(copySubA.isOptional());
    
    RoutineBriefMutant copySubB  = (RoutineBriefMutant) briefs.get(1);
    assertEquals(DependenciesQueueRoutine.class.getName(), copySubB.getClazz().getName());
    assertEquals("goB", copySubB.getName());
    assertEquals(RoutineBriefOrigin.STAGE_TASK, copySubB.getOrigin());
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
            new RoutineBriefMutant(new StageType());
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
  
  @Test
  public void testMethodGetNestedParameter() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    
    assertNull(rbm.getParameter("nr"));
    ParameterMutant nest = new ParameterMutant();
    nest.setKey("nr");
    rbm.setParameters(Collections.singletonList(nest));
    
    assertNull(rbm.getNestedRoutine("nr"));
    nest.setValue("nrVal");
    assertEquals("nrVal", rbm.getParameter("nr"));
    
    rbm.setParameters(Collections.singletonList(new Parameter(nest)));
    assertEquals("nrVal", rbm.getParameter("nr"));
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
  public void testStaticMethodConvertStages() throws Exception {
    Map<String, I_RoutineBrief> routinesFromNull = RoutineBriefMutant.convert(null);
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
        RoutineBriefMutant.convert(list);
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

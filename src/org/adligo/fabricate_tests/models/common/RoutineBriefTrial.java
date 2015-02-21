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

@SourceFileScope (sourceClass=RoutineBrief.class, minCoverage=81.0)
public class RoutineBriefTrial extends MockitoSourceFileTrial {

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
    assertNull(copy.getParameter(""));
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

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
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
  public void testMethodGetNestedParameter() throws Exception {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName("name");
    rbm.setOrigin(RoutineBriefOrigin.COMMAND);
    
    assertNull(rbm.getParameter("nr"));
    ParameterMutant nest = new ParameterMutant();
    nest.setKey("nr");
    rbm.setParameters(Collections.singletonList(nest));
    
    assertNull(new RoutineBrief(rbm).getNestedRoutine("nr"));
    nest.setValue("nrVal");
    assertEquals("nrVal", new RoutineBrief(rbm).getParameter("nr"));
    
    rbm.setParameters(Collections.singletonList(new Parameter(nest)));
    assertEquals("nrVal", new RoutineBrief(rbm).getParameter("nr"));
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

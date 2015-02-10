package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBrief;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.routines.DependenciesQueueRoutine;
import org.adligo.fabricate.routines.ProjectBriefQueueRoutine;
import org.adligo.fabricate.routines.ProjectQueueRoutine;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.CommandType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.RoutineType;
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

@SourceFileScope (sourceClass=RoutineBrief.class, minCoverage=88.0)
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
    
    RoutineBrief copy  = new RoutineBrief(brief);
    assertEquals(ProjectBriefQueueRoutine.class, copy.getClazz());
    assertEquals("go", copy.getName());
    assertTrue(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE, copy.getOrigin());
    ParameterTrial.assertConvertedParams(copy.getParameters(), this);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", copy.getParameters().getClass().getName());
    
    List<I_RoutineBrief> routines =  copy.getNestedRoutines();
    copy  = (RoutineBrief) routines.get(0);
    assertEquals(ProjectQueueRoutine.class, copy.getClazz());
    assertEquals("goA", copy.getName());
    assertTrue(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE_TASK, copy.getOrigin());
    ParameterTrial.assertConvertedParams(copy.getParameters(), this);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", copy.getParameters().getClass().getName());
    
    copy  = (RoutineBrief) routines.get(1);
    assertEquals(DependenciesQueueRoutine.class, copy.getClazz());
    assertEquals("goB", copy.getName());
    assertFalse(copy.isOptional());
    assertSame(RoutineBriefOrigin.STAGE_TASK, copy.getOrigin());
    ParameterTrial.assertConvertedParams(copy.getParameters(), this);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", copy.getParameters().getClass().getName());
    
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
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("clazz")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            RoutineBriefMutant rbm = new RoutineBriefMutant();
            rbm.setName("dn");
            new RoutineBrief(rbm);
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
  
}

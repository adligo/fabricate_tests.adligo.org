package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.routines.DependenciesQueueRoutine;
import org.adligo.fabricate.routines.ProjectBriefQueueRoutine;
import org.adligo.fabricate.routines.ProjectQueueRoutine;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.CommandType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.OptionalRoutineType;
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

@SourceFileScope (sourceClass=RoutineBriefMutant.class, minCoverage=88.0)
public class RoutineBriefMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorCopyCommand() {
    CommandType brief = mock(CommandType.class);
    when(brief.getClazz()).thenReturn(ProjectBriefQueueRoutine.class.getName());
    when(brief.getName()).thenReturn("go");
    when(brief.getTask()).thenReturn(null);
    when(brief.getParams()).thenReturn(null);
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief);
    assertEquals(ProjectBriefQueueRoutine.class.getName(), copy.getClazz().getName());
    assertEquals("go", copy.getName());
    assertFalse(copy.isOptional());
    assertEquals(RoutineBriefOrigin.COMMAND, copy.getOrigin());
    assertSame(Collections.emptyList(), copy.getNestedRoutines());
    assertSame(Collections.emptyList(), copy.getParameters());
    
  }
  
  @Test
  public void testConstructorCopyCommandTasksAndParams() {
    CommandType brief = new CommandType();
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
    
    RoutineBriefMutant copy  = new RoutineBriefMutant(brief);
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
  public void testConstructorCopyStage() {
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
  public void testConstructorCopyStageParamsAndTask() {
    StageType brief = mock(StageType.class);
    
    when(brief.getClazz()).thenReturn(ProjectBriefQueueRoutine.class.getName());
    when(brief.getName()).thenReturn("go");
    
    List<OptionalRoutineType> routines = new ArrayList<OptionalRoutineType>();
    when(brief.getTask()).thenReturn(routines);
    when(brief.getParams()).thenReturn(ParameterMutantTrial.createParams());
    when(brief.isOptional()).thenReturn(true);
    
    OptionalRoutineType briefTaskA = mock(OptionalRoutineType.class);
    
    when(briefTaskA.getClazz()).thenReturn(ProjectQueueRoutine.class.getName());
    when(briefTaskA.getName()).thenReturn("goA");
    when(briefTaskA.getParams()).thenReturn(ParameterMutantTrial.createParams());
    when(briefTaskA.isOptional()).thenReturn(true);
    
    OptionalRoutineType briefTaskB = mock(OptionalRoutineType.class);
    
    when(briefTaskB.getClazz()).thenReturn(DependenciesQueueRoutine.class.getName());
    when(briefTaskB.getName()).thenReturn("goB");
    when(briefTaskB.getParams()).thenReturn(ParameterMutantTrial.createParams());
    when(briefTaskB.isOptional()).thenReturn(true);
    
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
    assertTrue(copySubA.isOptional());
    
    RoutineBriefMutant copySubB  = (RoutineBriefMutant) briefs.get(1);
    assertEquals(DependenciesQueueRoutine.class.getName(), copySubB.getClazz().getName());
    assertEquals("goB", copySubB.getName());
    assertEquals(RoutineBriefOrigin.STAGE_TASK, copySubB.getOrigin());
    ParameterMutantTrial.assertConvertedParams(copySubB.getParameters(), this);
    assertTrue(copySubB.isOptional());
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant(null, null, null);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("", null, null);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("className")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("dn", null, null);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("className")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("dn", "", null);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("cn")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new RoutineBriefMutant("dn", "cn", null);
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
            new RoutineBriefMutant(new CommandType());
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
  
}

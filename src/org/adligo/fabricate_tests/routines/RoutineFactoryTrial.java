package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.common.ExpectedRoutineInterfaceMutant;
import org.adligo.fabricate.models.common.FabricationRoutineCreationException;
import org.adligo.fabricate.models.common.I_ExpectedRoutineInterface;
import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.I_RoutineFactory;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBrief;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.routines.I_GenericTypeAware;
import org.adligo.fabricate.routines.I_InputAware;
import org.adligo.fabricate.routines.I_ProjectBriefsAware;
import org.adligo.fabricate.routines.I_TaskProcessor;
import org.adligo.fabricate.routines.RoutineFactory;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.fabricate_tests.routines.implicit.mocks.BadInputRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.TaskProcessorRoutineMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@SourceFileScope (sourceClass=RoutineFactory.class, minCoverage=97.0)
public class RoutineFactoryTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  private MockMethod<Void> printlnMethod_;
  private DecryptTrait encrypt_ = new DecryptTrait();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.newArrayBlockingQueue(Boolean.class, 1))
        .thenReturn(new ArrayBlockingQueue<Boolean>(1));
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeparator()).thenReturn(System.lineSeparator());
    encrypt_.setSystem(sysMock_);
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    printlnMethod_ = new MockMethod<Void>();
    doAnswer(printlnMethod_).when(logMock_).println(any());
   
    when(sysMock_.currentThreadName()).thenReturn("main");
  }
  
  @Test
  public void testConstructorCopy() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(a);
   
    RoutineBriefMutant b = new RoutineBriefMutant();
    b.setName("b");
    b.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(b);
   
    RoutineBriefMutant c = new RoutineBriefMutant();
    c.setName("c");
    c.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(c);
    RoutineFactory copy = new RoutineFactory(sysMock_, factory);
    
    List<I_RoutineBrief> values = copy.getValues();
    I_RoutineBrief a1 = values.get(0);
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    assertSame(a1, copy.get("a"));
    
    I_RoutineBrief b1 = values.get(1);
    assertEquals(b, b1);
    assertEquals(RoutineBrief.class.getName(), b1.getClass().getName());
    assertSame(b1, copy.get("b"));
    
    I_RoutineBrief c1 = values.get(2);
    assertEquals(c, c1);
    assertEquals(RoutineBrief.class.getName(), c1.getClass().getName());
    assertSame(c1, copy.get("c"));
  }

  @Test
  public void testMethodsAddAndGetSimple() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(a);
    I_RoutineBrief a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    
    RoutineBriefMutant b = new RoutineBriefMutant();
    b.setName("b");
    b.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(b);
    I_RoutineBrief b1 = factory.get("b");
    assertEquals(b, b1);
    assertEquals(RoutineBrief.class.getName(), b1.getClass().getName());
    
    RoutineBriefMutant c = new RoutineBriefMutant();
    c.setName("c");
    c.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(c);
    I_RoutineBrief c1 = factory.get("c");
    assertEquals(c, c1);
    assertEquals(RoutineBrief.class.getName(), c1.getClass().getName());
    
    List<I_RoutineBrief> values = factory.getValues();
    a1 = values.get(0);
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    
    b1 = values.get(1);
    assertEquals(b, b1);
    assertEquals(RoutineBrief.class.getName(), b1.getClass().getName());
    
    c1 = values.get(2);
    assertEquals(c, c1);
    assertEquals(RoutineBrief.class.getName(), c1.getClass().getName());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsAddAndGetOverlay() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(a);
    
    I_RoutineBrief a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    
    a.setClazz(EncryptTrait.class);
    factory.add(a);
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    
    ParameterMutant pm1 = new ParameterMutant();
    pm1.setKey("key1");
    pm1.setValue("value1");
    a.addParameter(pm1);
    factory.add(a);
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    List<String> values = a1.getParameterValues("key1");
    assertContains(values, "value1");
    assertEquals(1, values.size());
    
    a.removeParameters("key1");
    ParameterMutant pm2 = new ParameterMutant();
    pm2.setKey("key1");
    pm2.setValue("value2");
    a.addParameter(pm2);
    factory.add(a);
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    values = a1.getParameterValues("key1");
    assertContains(values, "value2");
    assertEquals(1, values.size());
    
    a.addParameter(pm1);
    factory.add(a);
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    values = a1.getParameterValues("key1");
    assertContains(values, "value2");
    assertContains(values, "value1");
    assertEquals(2, values.size());
    
    RoutineBriefMutant b = new RoutineBriefMutant();
    b.setName("b");
    b.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    a.addNestedRoutine(b);
    factory.add(a);
    
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    I_RoutineBrief b1 = a1.getNestedRoutine("b");
    assertEquals(b, b1);
    assertEquals(RoutineBrief.class.getName(), b1.getClass().getName());
    
    b.setClazz(DecryptTrait.class);
    factory.add(a);
    
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    b1 = a1.getNestedRoutine("b");
    assertEquals(DecryptTrait.class.getName(), b1.getClazz().getName());
    assertEquals(b, b1);
    assertEquals(RoutineBrief.class.getName(), b1.getClass().getName());
    
    b.setClazz(EncryptTrait.class);
    factory.add(a);
    
    a1 = factory.get("a");
    assertEquals(a, a1);
    assertEquals(RoutineBrief.class.getName(), a1.getClass().getName());
    b1 = a1.getNestedRoutine("b");
    assertEquals(EncryptTrait.class.getName(), b1.getClazz().getName());
    assertEquals(b, b1);
    assertEquals(RoutineBrief.class.getName(), b1.getClass().getName());
  }
  
  @Test
  public void testMethodCreateFactory() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    a.setClazz(EncryptTrait.class);
    
    RoutineBriefMutant b = new RoutineBriefMutant();
    b.setName("b");
    b.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    b.setClazz(DecryptTrait.class);
    a.addNestedRoutine(b);
    factory.add(a);
    
    I_RoutineFactory taskFactory =  factory.createTaskFactory("a");
    I_FabricationRoutine rout =  taskFactory.createRoutine("b", Collections.emptySet());
    assertNotNull(rout);
    assertEquals(DecryptTrait.class.getName(), rout.getClass().getName());
  }
  
  @Test
  public void testMethodCreateSimple() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    a.setClazz(EncryptTrait.class);
    factory.add(a);
    
    I_FabricationRoutine encrypt = factory.createRoutine("a", Collections.emptySet());
    assertNotNull(encrypt);
    
    encrypt = factory.createRoutine("a", EncryptTrait.IMPLEMENTED_INTERFACES);
    assertNotNull(encrypt);
  }
  
  @Test
  public void testMethodCreateSimpleExceptions() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    factory.add(a);
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("No routine found with name 'b'.")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            factory.createRoutine("b", Collections.emptySet());
          }
        });
    //no class
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("a")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            factory.createRoutine("a", Collections.emptySet());
          }
        });
    
    a.setClazz(EncryptTrait.class);
    factory.add(a);
    when(sysMock_.currentThreadName()).thenReturn("blah");
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "This method must be called from the main thread (try moving the call to setup?).")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            factory.createRoutine("a", Collections.emptySet());
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodCreateSimpleFailureExpectedInterfacesDoNOTMatch() throws Exception {
    RoutineFactory factory = new RoutineFactory(sysMock_);
    RoutineBriefMutant a = new RoutineBriefMutant();
    a.setName("a");
    a.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    a.setClazz(EncryptTrait.class);
    factory.add(a);
    
    Set<I_ExpectedRoutineInterface> interfaces = new HashSet<I_ExpectedRoutineInterface>();
    ExpectedRoutineInterfaceMutant erim = new ExpectedRoutineInterfaceMutant();
    erim.setInterfaceClass(I_ProjectBriefsAware.class);
    interfaces.add(erim);
    
    FabricationRoutineCreationException caught = null;
    try {
      factory.createRoutine("a", interfaces);
    } catch (FabricationRoutineCreationException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals(I_ProjectBriefsAware.class.getName(), caught.getExpectedInterface().getName());
    assertEquals(EncryptTrait.class.getName(), caught.getRoutine().getName());
    
    interfaces = new HashSet<I_ExpectedRoutineInterface>();
    erim = new ExpectedRoutineInterfaceMutant();
    erim.setInterfaceClass(I_InputAware.class);
    erim.setGenericTypes(Collections.singletonList(Long.class));
    interfaces.add(erim);
    
    
    caught = null;
    try {
      factory.createRoutine("a", interfaces);
    } catch (FabricationRoutineCreationException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals(I_InputAware.class.getName(), caught.getExpectedInterface().getName());
    assertEquals(Long.class.getName(), caught.getExpectedGenericType().getName());
    assertEquals(String.class.getName(), caught.getActualGenericType().getName());
    assertEquals(0, caught.getWhichGenericType());
    assertEquals(EncryptTrait.class.getName(), caught.getRoutine().getName());
    
    a.setClazz(TaskProcessorRoutineMock.class);
    factory.add(a);
    
    interfaces = new HashSet<I_ExpectedRoutineInterface>();
    erim = new ExpectedRoutineInterfaceMutant();
    erim.setInterfaceClass(I_TaskProcessor.class);
    erim.setGenericTypes(Collections.singletonList(Long.class));
    interfaces.add(erim);
    
    caught = null;
    try {
      factory.createRoutine("a", interfaces);
    } catch (FabricationRoutineCreationException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals(I_GenericTypeAware.class.getName(), caught.getExpectedInterface().getName());
    assertEquals(0, caught.getWhichGenericType());
    assertEquals(TaskProcessorRoutineMock.class.getName(), caught.getRoutine().getName());
    
    a.setClazz(BadInputRoutineMock.class);
    factory.add(a);
    
    interfaces = new HashSet<I_ExpectedRoutineInterface>();
    erim = new ExpectedRoutineInterfaceMutant();
    erim.setInterfaceClass(I_InputAware.class);
    erim.setGenericTypes(Collections.singletonList(String.class));
    interfaces.add(erim);
    
    caught = null;
    try {
      factory.createRoutine("a", interfaces);
    } catch (FabricationRoutineCreationException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals(I_InputAware.class.getName(), caught.getExpectedInterface().getName());
    assertEquals(String.class.getName(), caught.getExpectedGenericType().getName());
    assertEquals(0, caught.getWhichGenericType());
    assertEquals(BadInputRoutineMock.class.getName(), caught.getRoutine().getName());
  }
}

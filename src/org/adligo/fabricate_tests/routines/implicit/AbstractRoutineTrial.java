package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.common.I_FabricationMemory;
import org.adligo.fabricate.models.common.I_FabricationMemoryMutant;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.I_RoutineFactory;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.fabricate.I_FabricateXmlDiscovery;
import org.adligo.fabricate.routines.implicit.AbstractRoutine;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.fabricate_tests.routines.implicit.mocks.ProjectProcessorRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.SimpleRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.TaskAndProjectProcessorRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.TaskProcessorRoutineMock;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=AbstractRoutine.class, minCoverage=93.0)
public class AbstractRoutineTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private DecryptTrait encrypt_ = new DecryptTrait();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeperator()).thenReturn(System.lineSeparator());
    encrypt_.setSystem(sysMock_);
  }
  
  public void testMethodsGetsAndSets() {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    I_RoutineBrief routine = mock(I_RoutineBrief.class);
    ar.setBrief(routine);
    assertSame(routine, ar.getBrief());
    
    I_FabricateXmlDiscovery locations = mock(I_FabricateXmlDiscovery.class);
    ar.setLocations(locations);
    assertSame(locations, ar.getLocations());
    
    ar.setSystem(sysMock_);
    assertSame(sysMock_, ar.getSystem());
    
    I_RoutineFactory taskFactory = mock(I_RoutineFactory.class);
    ar.setTaskFactory(taskFactory);
    assertSame(taskFactory, ar.getTaskFactory());
    
    I_RoutineFactory traitFactory = mock(I_RoutineFactory.class);
    ar.setTaskFactory(traitFactory);
    assertSame(traitFactory, ar.getTraitFactory());
  }
  
  @Test
  public void testMethodGetCurrentLocationDefault() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE_TASK);
    
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    ar.run();
    
    assertEquals("org.adligo.fabricate.routines.implicit.AbstractRoutine.getCurrentLocation() "
        + "unknown location for ARCHIVE_STAGE_TASK",
        ar.getCurrentLocation());
    
  }
  
  @Test
  public void testMethodGetCurrentLocationSimpleArchives() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE);
    
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    assertEquals("Archive stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE);
    I_FabricationMemoryMutant memoryMutant = mock(I_FabricationMemoryMutant.class);
    ar.setup(memoryMutant);
    assertEquals("Archive stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Archive stage routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @Test
  public void testMethodGetCurrentLocationSimpleCommands() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.COMMAND);
    
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    assertEquals("Command routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.COMMAND);
    I_FabricationMemoryMutant memoryMutant = mock(I_FabricationMemoryMutant.class);
    ar.setup(memoryMutant);
    assertEquals("Command routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Command routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @Test
  public void testMethodGetCurrentLocationSimpleStages() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.STAGE);
    
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    assertEquals("Build stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.FABRICATE_STAGE);
    I_FabricationMemoryMutant memoryMutant = mock(I_FabricationMemoryMutant.class);
    ar.setup(memoryMutant);
    assertEquals("Build stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Build stage routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @Test
  public void testMethodGetCurrentLocationTaskProjectArchives() throws Exception {
    TaskProcessorRoutineMock ar = new TaskProcessorRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE);
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    ar.run();
    assertEquals("Archive stage routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectProcessorRoutineMock ar2 = new ProjectProcessorRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.ARCHIVE_STAGE);
    ar2.setup(memory);
    ar2.run();
    assertEquals("Archive stage routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectProcessorRoutineMock ar3 = new TaskAndProjectProcessorRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.FABRICATE_ARCHIVE_STAGE);
    ar3.setup(memory);
    ar3.run();
    assertEquals("Archive stage routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }
  
  @Test
  public void testMethodGetCurrentLocationTaskProjectCommands() throws Exception {
    TaskProcessorRoutineMock ar = new TaskProcessorRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.COMMAND);
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    ar.run();
    assertEquals("Command routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectProcessorRoutineMock ar2 = new ProjectProcessorRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.FABRICATE_COMMAND);
    ar2.setup(memory);
    ar2.run();
    assertEquals("Command routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectProcessorRoutineMock ar3 = new TaskAndProjectProcessorRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.PROJECT_COMMAND);
    ar3.setup(memory);
    ar3.run();
    assertEquals("Command routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }

  @Test
  public void testMethodGetCurrentLocationTaskProjectStages() throws Exception {
    TaskProcessorRoutineMock ar = new TaskProcessorRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.STAGE);
    I_FabricationMemory memory = mock(I_FabricationMemory.class);
    ar.setup(memory);
    ar.run();
    assertEquals("Build stage routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectProcessorRoutineMock ar2 = new ProjectProcessorRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.FABRICATE_STAGE);
    ar2.setup(memory);
    ar2.run();
    assertEquals("Build stage routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectProcessorRoutineMock ar3 = new TaskAndProjectProcessorRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.PROJECT_STAGE);
    ar3.setup(memory);
    ar3.run();
    assertEquals("Build stage routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }
  
  public void addAndAssertBrief(AbstractRoutine ar, RoutineBriefOrigin type) {
    I_RoutineBrief routine = mock(I_RoutineBrief.class);
    when(routine.getOrigin()).thenReturn(type);
    when(routine.getName()).thenReturn("routineName");
    ar.setBrief(routine);
    assertSame(routine, ar.getBrief());
  }
}

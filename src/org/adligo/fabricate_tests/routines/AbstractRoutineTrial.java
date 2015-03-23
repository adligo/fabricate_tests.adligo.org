package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.common.I_FabricationMemory;
import org.adligo.fabricate.models.common.I_FabricationMemoryMutant;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.I_RoutineFactory;
import org.adligo.fabricate.models.common.I_RoutineMemory;
import org.adligo.fabricate.models.common.I_RoutineMemoryMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.fabricate.I_FabricateXmlDiscovery;
import org.adligo.fabricate.routines.AbstractRoutine;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.fabricate_tests.routines.implicit.mocks.ProjectRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.SimpleRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.TaskAndProjectRoutineMock;
import org.adligo.fabricate_tests.routines.implicit.mocks.TaskRoutineMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=AbstractRoutine.class, minCoverage=90.0)
public class AbstractRoutineTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  private I_FabFileIO filesMock_;
  private DecryptTrait encrypt_ = new DecryptTrait();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeparator()).thenReturn(System.lineSeparator());
    encrypt_.setSystem(sysMock_);
    
    filesMock_ = mock(I_FabFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(filesMock_);
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
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationDefault() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE_TASK);
    
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    ar.run();
    
    assertEquals("org.adligo.fabricate.routines.AbstractRoutine.getCurrentLocation() "
        + "unknown location for ARCHIVE_STAGE_TASK",
        ar.getCurrentLocation());
    
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationSimpleArchives() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE);
    
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    assertEquals("Archive stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE);
    I_FabricationMemoryMutant<Object> memoryMutant = mock(I_FabricationMemoryMutant.class);
    I_RoutineMemoryMutant<Object> routineMemoryMutant = mock(I_RoutineMemoryMutant.class);
    ar.setup(memoryMutant, routineMemoryMutant);
    assertEquals("Archive stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Archive stage routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationSimpleCommands() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.COMMAND);
    
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    assertEquals("Command routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.COMMAND);
    I_FabricationMemoryMutant<Object> memoryMutant = mock(I_FabricationMemoryMutant.class);
    I_RoutineMemoryMutant<Object> routineMemoryMutant = mock(I_RoutineMemoryMutant.class);
    ar.setup(memoryMutant, routineMemoryMutant);
    assertEquals("Command routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Command routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationSimpleFacets() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.FACET);
    
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    assertEquals("Facet routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.FABRICATE_FACET);
    I_FabricationMemoryMutant<Object> memoryMutant = mock(I_FabricationMemoryMutant.class);
    I_RoutineMemoryMutant<Object> routineMemoryMutant = mock(I_RoutineMemoryMutant.class);
    ar.setup(memoryMutant, routineMemoryMutant);
    assertEquals("Facet routineName is still setting up.", ar.getCurrentLocation());
    
    addAndAssertBrief(ar, RoutineBriefOrigin.IMPLICIT_FACET);
    memoryMutant = mock(I_FabricationMemoryMutant.class);
    routineMemoryMutant = mock(I_RoutineMemoryMutant.class);
    ar.setup(memoryMutant, routineMemoryMutant);
    assertEquals("Facet routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Facet routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationSimpleStages() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.STAGE);
    
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    assertEquals("Build stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.FABRICATE_STAGE);
    I_FabricationMemoryMutant<Object> memoryMutant = mock(I_FabricationMemoryMutant.class);
    I_RoutineMemoryMutant<Object> routineMemoryMutant = mock(I_RoutineMemoryMutant.class);
    ar.setup(memoryMutant, routineMemoryMutant);
    assertEquals("Build stage routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Build stage routineName is still running.", ar.getCurrentLocation());
    
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationSimpleTraits() throws Exception {
    SimpleRoutineMock ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
   
    addAndAssertBrief(ar, RoutineBriefOrigin.TRAIT);
    
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    assertEquals("Trait routineName is still setting up.", ar.getCurrentLocation());
    
    ar = new SimpleRoutineMock();
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.FABRICATE_TRAIT);
    I_FabricationMemoryMutant<Object> memoryMutant = mock(I_FabricationMemoryMutant.class);
    I_RoutineMemoryMutant<Object> routineMemoryMutant = mock(I_RoutineMemoryMutant.class);
    ar.setup(memoryMutant, routineMemoryMutant);
    assertEquals("Trait routineName is still setting up.", ar.getCurrentLocation());
    
    ar.run();
    assertEquals("Trait routineName is still running.", ar.getCurrentLocation());
    
  }
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationTaskProjectArchives() throws Exception {
    TaskRoutineMock ar = new TaskRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.ARCHIVE_STAGE);
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    ar.run();
    assertEquals("Archive stage routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectRoutineMock ar2 = new ProjectRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.ARCHIVE_STAGE);
    ar2.setup(memory, routineMemory);
    ar2.run();
    assertEquals("Archive stage routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectRoutineMock ar3 = new TaskAndProjectRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.FABRICATE_ARCHIVE_STAGE);
    ar3.setup(memory, routineMemory);
    ar3.run();
    assertEquals("Archive stage routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationTaskProjectCommands() throws Exception {
    TaskRoutineMock ar = new TaskRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.COMMAND);
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    ar.run();
    assertEquals("Command routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectRoutineMock ar2 = new ProjectRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.FABRICATE_COMMAND);
    ar2.setup(memory, routineMemory);
    ar2.run();
    assertEquals("Command routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectRoutineMock ar3 = new TaskAndProjectRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.PROJECT_COMMAND);
    ar3.setup(memory, routineMemory);
    ar3.run();
    assertEquals("Command routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationTaskProjectFacets() throws Exception {
    TaskRoutineMock ar = new TaskRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.FACET);
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    ar.run();
    assertEquals("Facet routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectRoutineMock ar2 = new ProjectRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.FABRICATE_FACET);
    ar2.setup(memory, routineMemory);
    ar2.run();
    assertEquals("Facet routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectRoutineMock ar3 = new TaskAndProjectRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.IMPLICIT_FACET);
    ar3.setup(memory, routineMemory);
    ar3.run();
    assertEquals("Facet routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationTaskProjectStages() throws Exception {
    TaskRoutineMock ar = new TaskRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.STAGE);
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    ar.run();
    assertEquals("Build stage routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectRoutineMock ar2 = new ProjectRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.FABRICATE_STAGE);
    ar2.setup(memory, routineMemory);
    ar2.run();
    assertEquals("Build stage routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectRoutineMock ar3 = new TaskAndProjectRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.PROJECT_STAGE);
    ar3.setup(memory, routineMemory);
    ar3.run();
    assertEquals("Build stage routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testMethodGetCurrentLocationTaskProjectTraits() throws Exception {
    TaskRoutineMock ar = new TaskRoutineMock("taskName");
    ar.setSystem(sysMock_);
    addAndAssertBrief(ar, RoutineBriefOrigin.TRAIT);
    I_FabricationMemory<Object> memory = mock(I_FabricationMemory.class);
    I_RoutineMemory<Object> routineMemory = mock(I_RoutineMemory.class);
    ar.setup(memory, routineMemory);
    ar.run();
    assertEquals("Trait routineName, task taskName is still running.", ar.getCurrentLocation());
    
    ProjectRoutineMock ar2 = new ProjectRoutineMock("projectName");
    ar2.setSystem(sysMock_);
    addAndAssertBrief(ar2, RoutineBriefOrigin.FABRICATE_TRAIT);
    ar2.setup(memory, routineMemory);
    ar2.run();
    assertEquals("Trait routineName is still running on project projectName.", ar2.getCurrentLocation());
    
    TaskAndProjectRoutineMock ar3 = new TaskAndProjectRoutineMock("taskName","projectName");
    ar3.setSystem(sysMock_);
    addAndAssertBrief(ar3, RoutineBriefOrigin.IMPLICIT_TRAIT);
    ar3.setup(memory, routineMemory);
    ar3.run();
    assertEquals("Trait routineName, task taskName is still running on project projectName.", ar3.getCurrentLocation());
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodMakeDir() throws Exception {
    TaskRoutineMock ar = new TaskRoutineMock("taskName");
    ar.setSystem(sysMock_);
    
    assertThrown(new ExpectedThrowable(new RuntimeException("There was a problem creating the following directory;\n" +
        "filePath")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            ar.makeDir("filePath");
            
          }
        });
    
    MockMethod<Boolean> mkdirsMethod = new MockMethod<Boolean>(true, true);
    when(filesMock_.mkdirs("filePath")).then(mkdirsMethod);
    ar.makeDir("filePath");
  }
  
  public void addAndAssertBrief(AbstractRoutine ar, RoutineBriefOrigin type) {
    I_RoutineBrief routine = mock(I_RoutineBrief.class);
    when(routine.getOrigin()).thenReturn(type);
    when(routine.getName()).thenReturn("routineName");
    ar.setBrief(routine);
    assertSame(routine, ar.getBrief());
  }
}

package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.models.common.I_FabricationMemory;
import org.adligo.fabricate.models.common.I_FabricationMemoryMutant;
import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.fabricate.models.common.I_RoutineMemory;
import org.adligo.fabricate.models.common.I_RoutineMemoryMutant;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.project.I_Project;
import org.adligo.fabricate.models.project.ProjectMutant;
import org.adligo.fabricate.repository.I_RepositoryFactory;
import org.adligo.fabricate.repository.I_RepositoryManager;
import org.adligo.fabricate.routines.I_CommandAware;
import org.adligo.fabricate.routines.I_ProjectsAware;
import org.adligo.fabricate.routines.I_RepositoryFactoryAware;
import org.adligo.fabricate.routines.I_RepositoryManagerAware;
import org.adligo.fabricate.routines.I_RoutineFabricateFactory;
import org.adligo.fabricate.routines.RoutineBuilder;
import org.adligo.fabricate.routines.RoutineFactory;
import org.adligo.fabricate.routines.implicit.FabricateAwareRoutine;
import org.adligo.fabricate_tests.common.log.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SourceFileScope (sourceClass=RoutineBuilder.class,minCoverage=92.0)
public class RoutineBuilderTrial extends MockitoSourceFileTrial {
  private FabSystem sysMock_;
  private I_FabLog logMock_;
  private MockMethod<Void> printlnMethod_;
  private MockMethod<Void> printTraceMethod_;
  private I_RoutineFabricateFactory routineFactory_;
  private I_FabricationMemory<Object> memory_;
  private I_RoutineMemory<Object> routineMemory_;
  private I_FabricationMemoryMutant<Object> memoryMutant_;
  private I_RoutineMemoryMutant<Object> routineMemoryMutant_;
  private I_RepositoryFactory repositoryFactory_;
  private I_RepositoryManager repositoryManager_;
  
  private FabricateMutant fabricate_;
  
  public void afterTests() {
    ThreadLocalPrintStreamMock.revert();
  }
  
  @SuppressWarnings({"boxing", "unchecked"})
  public void beforeTests() {
    sysMock_ = mock(FabSystem.class);
    
    when(sysMock_.lineSeparator()).thenReturn("\n");
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.getDefaultLanguage()).thenReturn("en");
    when(sysMock_.getDefaultCountry()).thenReturn("US");
    when(sysMock_.newFabConstantsDiscovery("en", "US")).thenReturn(FabricateEnConstants.INSTANCE);
   
    
    logMock_ = mock(I_FabLog.class);
    printlnMethod_ = new MockMethod<Void>();
    doAnswer(printlnMethod_).when(logMock_).println(any());
    
    printTraceMethod_ = new MockMethod<Void>();
    doAnswer(printTraceMethod_).when(logMock_).printTrace(any());
    
    when(sysMock_.getLog()).thenReturn(logMock_);
    
    routineFactory_ = mock(I_RoutineFabricateFactory.class);
    
    List<String> fabricateJarList = new ArrayList<String>();
    fabricateJarList.add("somewhere/fabricate_snapshot.jar");
    
    memory_ = mock(I_FabricationMemory.class);
    routineMemory_ = mock(I_RoutineMemory.class);
    fabricate_ = new FabricateMutant();
    
    when(routineFactory_.getFabricate()).thenReturn(fabricate_);
    
    memoryMutant_ = mock(I_FabricationMemoryMutant.class);
    routineMemoryMutant_ = mock(I_RoutineMemoryMutant.class);
    
    repositoryFactory_ = mock(I_RepositoryFactory.class);
    repositoryManager_ = mock(I_RepositoryManager.class);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddOptionalI_CommandAware() throws Exception {
    I_CommandAware routine = mock(I_CommandAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setCommand = new MockMethod<Void>();
    doAnswer(setCommand).when(routine).setCommandFactory(any());
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    builder.addOptional(routine);
    
    assertSame(cmdFactory, setCommand.getArg(0));
    assertSame(1, setCommand.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddOptionalI_RepositoryFactoryAware() throws Exception {
    I_RepositoryFactoryAware routine = mock(I_RepositoryFactoryAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setRepositoryFactory = new MockMethod<Void>();
    doAnswer(setRepositoryFactory).when(routine).setRepositoryFactory(any());
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following routine implements org.adligo.fabricate.routines.I_RepositoryFactoryAware "
        + "but the RoutineBuilder's value is null.\n" +
            "org.adligo.fabricate.routines.I_RepositoryFactoryAware"), MatchType.CONTAINS),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            builder.addOptional(routine);
          }
        });
    builder.setRepositoryFactory(repositoryFactory_);
    assertSame(repositoryFactory_, builder.getRepositoryFactory());
    builder.addOptional(routine);
    
    assertSame(repositoryFactory_, setRepositoryFactory.getArg(0));
    assertSame(1, setRepositoryFactory.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddOptionalI_RepositoryManagerAware() throws Exception {
    I_RepositoryManagerAware routine = mock(I_RepositoryManagerAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setRepositoryFactory = new MockMethod<Void>();
    doAnswer(setRepositoryFactory).when(routine).setRepositoryManager(any());
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following routine implements org.adligo.fabricate.routines.I_RepositoryManagerAware "
        + "but the RoutineBuilder's value is null.\n" +
            "org.adligo.fabricate.routines.I_RepositoryManagerAware"), MatchType.CONTAINS),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            builder.addOptional(routine);
          }
        });
    builder.setRepositoryManager(repositoryManager_);
    assertSame(repositoryManager_, builder.getRepositoryManager());
    builder.addOptional(routine);
    
    assertSame(repositoryManager_, setRepositoryFactory.getArg(0));
    assertSame(1, setRepositoryFactory.count());
  }
  
  @SuppressWarnings({"boxing", "unchecked"})
  @Test
  public void testMethodAddOptionalI_ProjectsAware() throws Exception {
    I_ProjectsAware routine = mock(I_ProjectsAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setProjects = new MockMethod<Void>();
    doAnswer(setProjects).when(routine).setProjects(any());
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following routine implements org.adligo.fabricate.routines.I_ProjectsAware "
        + "but the RoutineBuilder's value is null.\n" +
            "org.adligo.fabricate.routines.I_ProjectsAware"), MatchType.CONTAINS),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            builder.addOptional(routine);
          }
        });
    List<I_Project> projects = new ArrayList<I_Project>();
    ProjectMutant pm = new ProjectMutant();
    projects.add(pm);
    builder.setProjects(projects);
    List<I_Project> pOut = builder.getProjects();
    assertSame(pm, pOut.get(0));
    assertSame(1, pOut.size());
    builder.addOptional(routine);
    
    pOut = (List<I_Project>) setProjects.getArg(0);
    assertSame(pm, pOut.get(0));
    assertSame(1, pOut.size());
    assertSame(1, setProjects.count());
  }
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildInitialSimpleArchiveStage() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "archiveStage";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addArchiveStage(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setupInitial(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getArchiveStages()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateArchive = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateArchive).when(routineFactory_).createArchiveStage(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.buildInitial(memoryMutant_, routineMemoryMutant_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateArchive.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateArchive.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateArchive.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memoryMutant_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemoryMutant_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildInitialSimpleCommand() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "command";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addCommand(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setupInitial(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateCommand = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateCommand).when(routineFactory_).createCommand(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.COMMAND, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.buildInitial(memoryMutant_, routineMemoryMutant_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateCommand.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateCommand.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateCommand.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memoryMutant_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemoryMutant_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildInitialSimpleFacet() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "facet";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addFacet(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setupInitial(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getFacets()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateFacet = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateFacet).when(routineFactory_).createFacet(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.FACET, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.buildInitial(memoryMutant_, routineMemoryMutant_);
    assertSame(routine, result);
    
    
    assertSame(name, routineFactoryCreateFacet.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateFacet.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateFacet.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memoryMutant_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemoryMutant_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildInitialSimplePassThroughToAddOptional() throws Exception {
    FabricateAwareRoutine routine = mock(FabricateAwareRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "archiveStage";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addArchiveStage(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetFabricate = new MockMethod<Void>();
    doAnswer(routineSetFabricate).when(routine).setFabricate(any());
    
    MockMethod<Void> routineSetRepositoryFactory = new MockMethod<Void>();
    doAnswer(routineSetRepositoryFactory).when(routine).setRepositoryFactory(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setupInitial(any(), any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getArchiveStages()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateArchive = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateArchive).when(routineFactory_).createArchiveStage(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    builder.setRepositoryFactory(repositoryFactory_);
    I_FabricationRoutine result = builder.buildInitial(memoryMutant_, routineMemoryMutant_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateArchive.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateArchive.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateArchive.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(fabricate_, routineSetFabricate.getArg(0));
    assertEquals(1, routineSetFabricate.count());
    
    assertSame(repositoryFactory_, routineSetRepositoryFactory.getArg(0));
    assertEquals(1, routineSetRepositoryFactory.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memoryMutant_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemoryMutant_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildInitialSimpleStage() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "stage";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addStage(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setupInitial(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getStages()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateStage = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateStage).when(routineFactory_).createStage(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.STAGE, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.buildInitial(memoryMutant_, routineMemoryMutant_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateStage.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateStage.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateStage.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memoryMutant_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemoryMutant_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildInitialSimpleTrait() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "trait";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addTrait(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setupInitial(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(traitFacory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateStage = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateStage).when(routineFactory_).createTrait(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.TRAIT, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.buildInitial(memoryMutant_, routineMemoryMutant_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateStage.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateStage.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateStage.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memoryMutant_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemoryMutant_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  

  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildSimpleArchiveStage() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "archiveStage";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addArchiveStage(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Void> routineSetup = new MockMethod<Void>();
    doAnswer(routineSetup).when(routine).setup(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getArchiveStages()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateArchive = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateArchive).when(routineFactory_).createArchiveStage(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.build(memory_, routineMemory_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateArchive.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateArchive.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateArchive.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memory_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemory_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildSimpleCommand() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "command";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addCommand(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Void> routineSetup = new MockMethod<Void>();
    doAnswer(routineSetup).when(routine).setup(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateCommand = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateCommand).when(routineFactory_).createCommand(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.COMMAND, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.build(memory_, routineMemory_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateCommand.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateCommand.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateCommand.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memory_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemory_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildSimpleFacet() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "facet";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addFacet(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Void> routineSetup = new MockMethod<Void>();
    doAnswer(routineSetup).when(routine).setup(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getFacets()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateFacet = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateFacet).when(routineFactory_).createFacet(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.FACET, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.build(memory_, routineMemory_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateFacet.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateFacet.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateFacet.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memory_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemory_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildSimplePassThroughToAddOptional() throws Exception {
    FabricateAwareRoutine routine = mock(FabricateAwareRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "archiveStage";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addArchiveStage(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetFabricate = new MockMethod<Void>();
    doAnswer(routineSetFabricate).when(routine).setFabricate(any());
    
    MockMethod<Void> routineSetRepositoryFactory = new MockMethod<Void>();
    doAnswer(routineSetRepositoryFactory).when(routine).setRepositoryFactory(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Boolean> routineSetup = new MockMethod<Boolean>(true, true);
    doAnswer(routineSetup).when(routine).setup(any(), any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getArchiveStages()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateArchive = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateArchive).when(routineFactory_).createArchiveStage(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.ARCHIVE_STAGE, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    builder.setRepositoryFactory(repositoryFactory_);
    I_FabricationRoutine result = builder.build(memory_, routineMemory_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateArchive.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateArchive.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateArchive.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(fabricate_, routineSetFabricate.getArg(0));
    assertEquals(1, routineSetFabricate.count());
    
    assertSame(repositoryFactory_, routineSetRepositoryFactory.getArg(0));
    assertEquals(1, routineSetRepositoryFactory.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memory_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemory_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildSimpleStage() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "stage";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addStage(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Void> routineSetup = new MockMethod<Void>();
    doAnswer(routineSetup).when(routine).setup(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory routineFactory = mock(RoutineFactory.class);
    when(routineFactory_.getStages()).thenReturn(routineFactory);
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(routineFactory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateStage = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateStage).when(routineFactory_).createStage(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.STAGE, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.build(memory_, routineMemory_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateStage.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateStage.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateStage.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memory_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemory_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testMethodBuildSimpleTrait() throws Exception {
    I_FabricationRoutine routine = mock(I_FabricationRoutine.class);
    MockMethod<Void> routineSetTasks = new MockMethod<Void>();
    doAnswer(routineSetTasks).when(routine).setTaskFactory(any());
    String name = "trait";
    
    RoutineBriefMutant brief = new RoutineBriefMutant();
    brief.setName(name);
    fabricate_.addTrait(brief);
    
    MockMethod<Void> routineSetBrief = new MockMethod<Void>();
    doAnswer(routineSetBrief).when(routine).setBrief(any());
    
    MockMethod<Void> routineSetSystem = new MockMethod<Void>();
    doAnswer(routineSetSystem).when(routine).setSystem(any());
    
    MockMethod<Void> routineSetTraitFactory = new MockMethod<Void>();
    doAnswer(routineSetTraitFactory).when(routine).setTraitFactory(any());
    
    MockMethod<Void> routineSetup = new MockMethod<Void>();
    doAnswer(routineSetup).when(routine).setup(any(), any());
    
    RoutineFactory traitFacory = mock(RoutineFactory.class);
    when(routineFactory_.getTraits()).thenReturn(traitFacory);
    
    RoutineFactory taskFactory = mock(RoutineFactory.class);
    when(traitFacory.createTaskFactory(name)).thenReturn(taskFactory);
    
    MockMethod<I_FabricationRoutine> routineFactoryCreateStage = new MockMethod<I_FabricationRoutine>(routine, true);
    doAnswer(routineFactoryCreateStage).when(routineFactory_).createTrait(any(), any());
    
    RoutineBuilder builder = new RoutineBuilder(sysMock_, RoutineBriefOrigin.TRAIT, routineFactory_);
    
    builder.setNextRoutineName(name);
    assertSame(name, builder.getNextRoutineName());
    I_FabricationRoutine result = builder.build(memory_, routineMemory_);
    assertSame(routine, result);
    
    assertSame(name, routineFactoryCreateStage.getArgs(0)[0]);
    assertSame(0, ((Collection<?>) routineFactoryCreateStage.getArgs(0)[1]).size());
    assertEquals(1, routineFactoryCreateStage.count());

    assertSame(brief, routineSetBrief.getArg(0));
    assertEquals(1, routineSetBrief.count());
    
    assertSame(sysMock_, routineSetSystem.getArg(0));
    assertEquals(1, routineSetSystem.count());
    
    assertSame(taskFactory, routineSetTasks.getArg(0));
    assertEquals(1, routineSetTasks.count());
    
    assertSame(traitFacory, routineSetTraitFactory.getArg(0));
    assertEquals(1, routineSetTraitFactory.count());
    
    assertSame(memory_, routineSetup.getArgs(0)[0]);
    assertSame(routineMemory_, routineSetup.getArgs(0)[1]);
    assertEquals(1, routineSetup.count());
  }
}

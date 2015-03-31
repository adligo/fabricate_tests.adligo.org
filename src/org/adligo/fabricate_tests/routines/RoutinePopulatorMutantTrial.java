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
import org.adligo.fabricate.routines.I_RoutineFabricateProcessorFactory;
import org.adligo.fabricate.routines.RoutineBuilder;
import org.adligo.fabricate.routines.RoutineFactory;
import org.adligo.fabricate.routines.RoutinePopulatorMutant;
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

@SourceFileScope (sourceClass=RoutinePopulatorMutant.class,minCoverage=40.0)
public class RoutinePopulatorMutantTrial extends MockitoSourceFileTrial {
  private FabSystem sysMock_;
  private I_FabLog logMock_;
  private MockMethod<Void> printlnMethod_;
  private MockMethod<Void> printTraceMethod_;
  private I_RoutineFabricateProcessorFactory routineFactory_;
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
    
    routineFactory_ = mock(I_RoutineFabricateProcessorFactory.class);
    
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
  public void testMethodPopulatelI_CommandAware() throws Exception {
    I_CommandAware routine = mock(I_CommandAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setCommand = new MockMethod<Void>();
    doAnswer(setCommand).when(routine).setCommandFactory(any());
    RoutinePopulatorMutant pop = new RoutinePopulatorMutant(sysMock_, routineFactory_);
    
    pop.populate(routine);
    
    assertSame(cmdFactory, setCommand.getArg(0));
    assertSame(1, setCommand.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodPopulateI_RepositoryFactoryAware() throws Exception {
    I_RepositoryFactoryAware routine = mock(I_RepositoryFactoryAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setRepositoryFactory = new MockMethod<Void>();
    doAnswer(setRepositoryFactory).when(routine).setRepositoryFactory(any());
    RoutinePopulatorMutant pop = new RoutinePopulatorMutant(sysMock_, routineFactory_);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following routine implements org.adligo.fabricate.routines.I_RepositoryFactoryAware "
        + "but the RoutinePopulator's value is null.\n" +
            "org.adligo.fabricate.routines.I_RepositoryFactoryAware"), MatchType.CONTAINS),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pop.populate(routine);
          }
        });
    pop.setRepositoryFactory(repositoryFactory_);
    assertSame(repositoryFactory_, pop.getRepositoryFactory());
    pop.populate(routine);
    
    assertSame(repositoryFactory_, setRepositoryFactory.getArg(0));
    assertSame(1, setRepositoryFactory.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodPopulateI_RepositoryManagerAware() throws Exception {
    I_RepositoryManagerAware routine = mock(I_RepositoryManagerAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setRepositoryFactory = new MockMethod<Void>();
    doAnswer(setRepositoryFactory).when(routine).setRepositoryManager(any());
    RoutinePopulatorMutant pop = new RoutinePopulatorMutant(sysMock_, routineFactory_);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following routine implements org.adligo.fabricate.routines.I_RepositoryManagerAware "
        + "but the RoutinePopulator's value is null.\n" +
            "org.adligo.fabricate.routines.I_RepositoryManagerAware"), MatchType.CONTAINS),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pop.populate(routine);
          }
        });
    pop.setRepositoryManager(repositoryManager_);
    assertSame(repositoryManager_, pop.getRepositoryManager());
    pop.populate(routine);
    
    assertSame(repositoryManager_, setRepositoryFactory.getArg(0));
    assertSame(1, setRepositoryFactory.count());
  }
  
  @SuppressWarnings({"boxing", "unchecked"})
  @Test
  public void testMethodPopulateI_ProjectsAware() throws Exception {
    I_ProjectsAware routine = mock(I_ProjectsAware.class);
    RoutineFactory cmdFactory = mock(RoutineFactory.class);
    when(routineFactory_.getCommands()).thenReturn(cmdFactory);
    MockMethod<Void> setProjects = new MockMethod<Void>();
    doAnswer(setProjects).when(routine).setProjects(any());
    RoutinePopulatorMutant pop = new RoutinePopulatorMutant(sysMock_, routineFactory_);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following routine implements org.adligo.fabricate.routines.I_ProjectsAware "
        + "but the RoutinePopulator's value is null.\n" +
            "org.adligo.fabricate.routines.I_ProjectsAware"), MatchType.CONTAINS),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            pop.populate(routine);
          }
        });
    List<I_Project> projects = new ArrayList<I_Project>();
    ProjectMutant pm = new ProjectMutant();
    projects.add(pm);
    pop.setProjects(projects);
    List<I_Project> pOut = pop.getProjects();
    assertSame(pm, pOut.get(0));
    assertSame(1, pOut.size());
    pop.populate(routine);
    
    pOut = (List<I_Project>) setProjects.getArg(0);
    assertSame(pm, pOut.get(0));
    assertSame(1, pOut.size());
    assertSame(1, setProjects.count());
  }
  
}

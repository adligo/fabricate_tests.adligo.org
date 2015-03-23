package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.Parameter;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBrief;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.dependencies.I_LibraryDependency;
import org.adligo.fabricate.models.dependencies.I_ProjectDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependencyMutant;
import org.adligo.fabricate.models.dependencies.ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.fabricate.models.project.I_Project;
import org.adligo.fabricate.models.project.Project;
import org.adligo.fabricate.models.project.ProjectMutant;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SourceFileScope (sourceClass=Project.class,minCoverage=82.0)
public class ProjectTrial extends MockitoSourceFileTrial {

  public DependencyMutant getDependencyA() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactA");
    dm.setExtract(true);
    dm.setFileName("fileNameA");
    dm.setGroup("groupA");
    dm.setPlatform("platformA");
    dm.setType("typeA");
    dm.setVersion("versionA");
    return dm;
  }
  
  public DependencyMutant getDependencyB() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactB");
    dm.setExtract(true);
    dm.setFileName("fileNameB");
    dm.setGroup("groupB");
    dm.setPlatform("platformB");
    dm.setType("typeB");
    dm.setVersion("versionB");
    return dm;
  }
  
  public LibraryDependencyMutant getLibraryDependencyA() {
    LibraryDependencyMutant dm = new LibraryDependencyMutant();
    dm.setLibraryName("libraryNameA");
    dm.setPlatform("platformA");
    return dm;
  }

  public LibraryDependencyMutant getLibraryDependencyB() {
    LibraryDependencyMutant dm = new LibraryDependencyMutant();
    dm.setLibraryName("libraryNameB");
    dm.setPlatform("platformB");
    return dm;
  }
  

  public ProjectDependencyMutant getProjectDependencyA() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("projectA");
    dm.setPlatform("platformA");
    return dm;
  }

  public ProjectDependencyMutant getProjectDependencyB() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("projectB");
    dm.setPlatform("platformB");
    return dm;
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    ProjectMutant pm = new ProjectMutant();
    pm.setDir("dir");
    pm.setName("name");
    pm.setVersion("version");
    
    Project copy = new Project(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    assertEquals("java.util.Collections$EmptyList", copy.getAttributes().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getCommands().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getLibraryDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getNormalizedDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getProjectDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getStages().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getTraits().getClass().getName());
    
    DependencyMutant dmA = getDependencyA();
    pm.addDependency(dmA);
    pm.addDependency(dmA);
    LibraryDependencyMutant ldmA = getLibraryDependencyA();
    pm.addLibraryDependency(ldmA);
    pm.addLibraryDependency(ldmA);
    ProjectDependencyMutant pdmA = getProjectDependencyA();
    pm.addProjectDependency(pdmA);
    pm.addProjectDependency(pdmA);
    copy = new Project(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    List<I_Dependency> dependencies = copy.getDependencies();
    I_Dependency zero = dependencies.get(0);
    assertEquals(dmA, zero);
    assertEquals(Dependency.class.getName(), zero.getClass().getName());
    assertEquals(1, dependencies.size());
    
    List<I_LibraryDependency> libDeps = copy.getLibraryDependencies();
    I_LibraryDependency lz = libDeps.get(0);
    assertEquals(ldmA, lz);
    assertEquals(LibraryDependency.class.getName(), lz.getClass().getName());
    assertEquals(1, libDeps.size());
    
    List<I_ProjectDependency> projDeps = copy.getProjectDependencies();
    I_ProjectDependency pd = projDeps.get(0);
    assertEquals(pdmA, pd);
    assertEquals(ProjectDependency.class.getName(), pd.getClass().getName());
    assertEquals(1, projDeps.size());
   
    Project copyCopy = new Project(copy);
    assertEquals("dir", copyCopy.getDir());
    assertEquals("name", copyCopy.getName());
    assertEquals("version", copyCopy.getVersion());
    
    assertEquals("java.util.Collections$EmptyList", copy.getAttributes().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getCommands().getClass().getName());
    
    dependencies = copyCopy.getDependencies();
    zero = dependencies.get(0);
    assertEquals(dmA, zero);
    assertEquals(Dependency.class.getName(), zero.getClass().getName());
    assertEquals(1, dependencies.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", dependencies.getClass().getName());
    
    libDeps = copyCopy.getLibraryDependencies();
    lz = libDeps.get(0);
    assertEquals(ldmA, lz);
    assertEquals(LibraryDependency.class.getName(), lz.getClass().getName());
    assertEquals(1, libDeps.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", libDeps.getClass().getName());
    
    projDeps = copyCopy.getProjectDependencies();
    pd = projDeps.get(0);
    assertEquals(pdmA, pd);
    assertEquals(ProjectDependency.class.getName(), pd.getClass().getName());
    assertEquals(1, projDeps.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", projDeps.getClass().getName());
    
    assertEquals("java.util.Collections$EmptyMap", copy.getStages().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getTraits().getClass().getName());

  }
  
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyMutableImmutableChildParams() {
    I_Project project = mock(I_Project.class);
    
    ParameterMutant attributeA = new ParameterMutant();
    attributeA.setKey("keyA");
    attributeA.setValue("valueA");
    
    ParameterMutant attributeB = new ParameterMutant();
    attributeB.setKey("keyB");
    attributeB.setValue("valueB");
    
    Parameter attributeB1 = new Parameter(attributeB);
    List<I_Parameter> params = new ArrayList<I_Parameter>();
    params.add(attributeA);
    params.add(attributeB1);
    when(project.getAttributes()).thenReturn(params);
    
    RoutineBriefMutant cmdA = new RoutineBriefMutant();
    cmdA.setName("cmdA");
    cmdA.setOrigin(RoutineBriefOrigin.PROJECT_COMMAND);
    
    RoutineBriefMutant cmdB = new RoutineBriefMutant();
    cmdB.setName("cmdB");
    cmdB.setOrigin(RoutineBriefOrigin.PROJECT_COMMAND);
    RoutineBrief cmdB1 = new RoutineBrief(cmdB);//immutable
    Map<String, I_RoutineBrief> cmds = new HashMap<String, I_RoutineBrief>();
    cmds.put("cmdA",cmdA);
    cmds.put("cmdB",cmdB1);
    when(project.getCommands()).thenReturn(cmds);
    
    DependencyMutant dependencyA = ProjectMutantTrial.getDependencyLetterPrime("DepA");
    Dependency dependencyB = new Dependency(ProjectMutantTrial.getDependencyLetterPrime("DepB"));
   
    List<I_Dependency> depsList = new ArrayList<I_Dependency>();
    depsList.add(dependencyA);
    depsList.add(dependencyB);
    when(project.getDependencies()).thenReturn(depsList);
    
    LibraryDependencyMutant libDepA = new LibraryDependencyMutant();
    libDepA.setLibraryName("libraryA");
    libDepA.setPlatform("platformA");
    LibraryDependency libDepAA = new LibraryDependency(libDepA);
    
    LibraryDependencyMutant libDepB = new LibraryDependencyMutant();
    libDepB.setLibraryName("libraryB");
    libDepB.setPlatform("platformB");
    
    List<I_LibraryDependency> libList = new ArrayList<I_LibraryDependency>();
    libList.add(libDepAA);
    libList.add(libDepB);
    when(project.getLibraryDependencies()).thenReturn(libList);
    
    DependencyMutant normA = ProjectMutantTrial.getDependencyLetterPrime("NormDepA");
    Dependency normB = new Dependency(ProjectMutantTrial.getDependencyLetterPrime("NormDepB"));

    List<I_Dependency> normsList = new ArrayList<I_Dependency>();
    normsList.add(normA);
    normsList.add(normB);
    when(project.getNormalizedDependencies()).thenReturn(normsList);

    ProjectDependencyMutant projDepA = new ProjectDependencyMutant();
    projDepA.setProjectName("projectA");
    projDepA.setPlatform("platformA");
    ProjectDependency projDepA1 = new ProjectDependency(projDepA);//immutable to mutable
    
    ProjectDependencyMutant projDepB = new ProjectDependencyMutant();
    projDepB.setProjectName("projectB");
    projDepB.setPlatform("platformB");
    List<I_ProjectDependency> projList = new ArrayList<I_ProjectDependency>();
    projList.add(projDepA1);
    projList.add(projDepB);
    when(project.getProjectDependencies()).thenReturn(projList);

    RoutineBriefMutant stageA = new RoutineBriefMutant();
    stageA.setName("stageA");
    stageA.setOrigin(RoutineBriefOrigin.PROJECT_STAGE);
    RoutineBrief stageA1 = new RoutineBrief(stageA);
    
    RoutineBriefMutant stageB = new RoutineBriefMutant();
    stageB.setName("stageB");
    stageB.setOrigin(RoutineBriefOrigin.PROJECT_STAGE);
    Map<String, I_RoutineBrief> stages = new HashMap<String, I_RoutineBrief>();
    stages.put("stageA", stageA1);
    stages.put("stageB", stageB);
    when(project.getStages()).thenReturn(stages);
    
    RoutineBriefMutant traitA = new RoutineBriefMutant();
    traitA.setName("traitA");
    traitA.setClazz(EncryptTrait.class);
    traitA.setOrigin(RoutineBriefOrigin.PROJECT_TRAIT);
    
    RoutineBriefMutant traitB = new RoutineBriefMutant();
    traitB.setName("traitB");
    traitB.setClazz(EncryptTrait.class);
    traitB.setOrigin(RoutineBriefOrigin.PROJECT_TRAIT);
    RoutineBrief traitB1 = new RoutineBrief(traitB);
    Map<String, I_RoutineBrief> traits = new HashMap<String, I_RoutineBrief>();
    traits.put("traitA", traitA);
    traits.put("traitB", traitB1);
    when(project.getTraits()).thenReturn(traits);
    
    Project copy3 = new Project(project);
    
    List<I_Parameter> attribs = copy3.getAttributes();
    assertTwoFromMemoryAttributes(attributeA, attributeB1, attribs);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", attribs.getClass().getName());
    assertEquals(2, attribs.size());
    
    attribs = copy3.getAttributes("keyA");
    assertEquals(attributeA, attribs.get(0));
    assertNotSame(attributeA, attribs.get(0));
    assertEquals(Parameter.class.getName(), attribs.get(0).getClass().getName());
    assertEquals("java.util.ArrayList", attribs.getClass().getName());
    assertEquals(1, attribs.size());
    attribs.clear();
    attribs = copy3.getAttributes("keyA");
    assertEquals(attributeA, attribs.get(0));
    assertNotSame(attributeA, attribs.get(0));
    assertEquals(Parameter.class.getName(), attribs.get(0).getClass().getName());
    assertEquals("java.util.ArrayList", attribs.getClass().getName());
    assertEquals(1, attribs.size());
    
    I_Parameter paramA = copy3.getAttribute("keyA");
    assertEquals(attributeA, paramA);
    assertNotSame(attributeA, paramA);
    
    Map<String,I_RoutineBrief> commands = copy3.getCommands();
    assertTwoFromMemoryCommands(commands, cmdA, cmdB1, copy3);
    assertEquals("java.util.Collections$UnmodifiableMap", commands.getClass().getName());
    assertEquals(2, copy3.getCommands().size());
    
    List<I_Dependency> dependencies = copy3.getDependencies();
    I_Dependency depA = dependencies.get(0);
    assertEquals(dependencyA, depA);
    assertEquals(Dependency.class.getName(), depA.getClass().getName());
    I_Dependency depB = dependencies.get(1);
    assertSame(dependencyB, depB);
    assertEquals(Dependency.class.getName(), depB.getClass().getName());
    assertEquals(2, dependencies.size());
    
    List<I_LibraryDependency> libDeps = copy3.getLibraryDependencies();
    I_LibraryDependency libDepA1 = libDeps.get(0);
    assertSame(libDepAA, libDepA1);
    I_LibraryDependency libDepB1 = libDeps.get(1);
    assertEquals(libDepB, libDepB1);
    assertEquals(LibraryDependency.class.getName(), libDepB1.getClass().getName());
    assertEquals(2, libDeps.size());
    
    List<I_ProjectDependency> projDeps = copy3.getProjectDependencies();
    I_ProjectDependency projDepAA = projDeps.get(0);
    assertSame(projDepA1, projDepAA);
    assertEquals(ProjectDependency.class.getName(), projDepAA.getClass().getName());
    I_ProjectDependency projDepB1 = projDeps.get(1);
    assertEquals(projDepB, projDepB1);
    assertEquals(ProjectDependency.class.getName(), projDepB1.getClass().getName());
    assertEquals(2, projDeps.size());
    
    List<I_Dependency> normDeps = copy3.getNormalizedDependencies();
    assertTwoNormDeps(normA, normB, normDeps);
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", normDeps.getClass().getName());
    
    Map<String,I_RoutineBrief> stages3 = copy3.getStages();
    assertTwoFromMemoryStages(stages3, stageA1, stageB, copy3);
    assertEquals("java.util.Collections$UnmodifiableMap", stages3.getClass().getName());
    
    //triats
    Map<String,I_RoutineBrief> traits3 = copy3.getTraits();
    assertTwoFromMemoryTraits(traits3, traitA, traitB1, copy3);
    assertEquals("java.util.Collections$UnmodifiableMap", traits3.getClass().getName());
  }
  
  @Test
  public void testConstructorCopyNullLists() {
    I_Project pm = mock(I_Project.class);
    
    when(pm.getAttributes()).thenReturn(null);
    when(pm.getCommands()).thenReturn(null);
    when(pm.getDependencies()).thenReturn(null);
    when(pm.getLibraryDependencies()).thenReturn(null);
    when(pm.getNormalizedDependencies()).thenReturn(null);
    when(pm.getProjectDependencies()).thenReturn(null);
    when(pm.getStages()).thenReturn(null);
    when(pm.getTraits()).thenReturn(null);
    
    Project copy = new Project(pm);
    
    assertEquals("java.util.Collections$EmptyList", copy.getAttributes().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getCommands().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getLibraryDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getNormalizedDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getProjectDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getStages().getClass().getName());
    assertEquals("java.util.Collections$EmptyMap", copy.getTraits().getClass().getName());
  }
  
  @Test
  public void testConstructorCopyDupelicateRoutineExcepions() {
    ProjectMutant pm = new ProjectMutant();
    pm.setDir("dir");
    pm.setName("name");
    pm.setVersion("version");
    
    Project copy = new Project(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
  }
  
  private void assertDependencyLetterPrime(String letter, I_Dependency dep) {
    assertEquals("artifact" + letter + "1", dep.getArtifact());
    assertEquals("fileName" + letter + "1", dep.getFileName());
    assertEquals("group" + letter + "1",  dep.getGroup());
    assertEquals("platform" + letter + "1", dep.getPlatform());
    assertEquals("type" + letter + "1", dep.getType());
    assertEquals("version" + letter + "1", dep.getVersion());
  }
  
  private void assertTwoFromMemoryAttributes(I_Parameter attributeA,
      I_Parameter attributeB, List<I_Parameter> attribs) {
    assertEquals(attributeA, attribs.get(0));
    assertNotSame(attributeA, attribs.get(0));
    assertEquals(Parameter.class.getName(), attribs.get(0).getClass().getName());
    
    assertEquals(attributeB, attribs.get(1));
    assertSame(attributeB, attribs.get(1));
    assertEquals(Parameter.class.getName(), attribs.get(1).getClass().getName());
  }

  
  @SuppressWarnings("boxing")
  private void assertTwoFromMemoryCommands(Map<String, I_RoutineBrief> cmdsOut, 
      I_RoutineBrief a, I_RoutineBrief b, Project project) {
    I_RoutineBrief cmdOA = cmdsOut.get("cmdA");
    assertEquals(RoutineBriefOrigin.PROJECT_COMMAND, cmdOA.getOrigin());
    assertEquals(a, cmdOA);
    assertEquals(RoutineBrief.class.getName(), cmdOA.getClass().getName());
    assertSame(cmdOA, project.getCommand("cmdA"));
    
    I_RoutineBrief cmdOB = cmdsOut.get("cmdB");
    assertSame(b, cmdOB);
    assertEquals(RoutineBriefOrigin.PROJECT_COMMAND, cmdOB.getOrigin());
    assertEquals(RoutineBrief.class.getName(), cmdOB.getClass().getName());
    assertEquals(2, cmdsOut.size());
    assertSame(cmdOB, project.getCommand("cmdB"));
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoFromMemoryStages(Map<String, I_RoutineBrief> stagesOut, 
      I_RoutineBrief a, I_RoutineBrief b, Project project) {
    
    I_RoutineBrief stageOA = stagesOut.get("stageA");
    assertSame(a, stageOA);
    assertEquals(RoutineBriefOrigin.PROJECT_STAGE ,stageOA.getOrigin());
    assertNull(stageOA.getClazz());
    assertEquals(RoutineBrief.class.getName(), stageOA.getClass().getName());
    assertSame(stageOA, project.getStage("stageA"));
    
    I_RoutineBrief stageOB = stagesOut.get("stageB");
    assertEquals(b, stageOB);
    assertEquals(RoutineBriefOrigin.PROJECT_STAGE ,stageOB.getOrigin());
    assertNull(stageOB.getClazz());
    assertEquals(RoutineBrief.class.getName(), stageOB.getClass().getName());
    
    assertEquals(2, stagesOut.size());
    assertSame(stageOB, project.getStage("stageB"));
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoFromMemoryTraits(Map<String, I_RoutineBrief> traitsOut, 
      I_RoutineBrief a, I_RoutineBrief b, Project project) {
    I_RoutineBrief traitOA = traitsOut.get("traitA");
    assertEquals(a, traitOA);
    assertEquals(EncryptTrait.class, traitOA.getClazz());
    assertEquals(RoutineBrief.class.getName(), traitOA.getClass().getName());
    assertSame(traitOA, project.getTrait("traitA"));
    
    I_RoutineBrief traitOB = traitsOut.get("traitB");
    assertSame(b, traitOB);
    assertEquals(EncryptTrait.class, traitOB.getClazz());
    assertEquals(RoutineBrief.class.getName(), traitOB.getClass().getName());
    assertEquals(2, traitsOut.size());
    assertSame(traitOB, project.getTrait("traitB"));
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoNormDeps(I_Dependency normA, I_Dependency normB,
      List<I_Dependency> normDeps) {
    I_Dependency nA = normDeps.get(0);
    assertDependencyLetterPrime("NormDepA", nA);
    assertEquals(normA, nA);
    
    I_Dependency nB = normDeps.get(1);
    assertDependencyLetterPrime("NormDepB", nB);
    assertSame(normB, nB);
    assertEquals(Dependency.class.getName(), nB.getClass().getName());
    assertEquals(2, normDeps.size());
  }
}

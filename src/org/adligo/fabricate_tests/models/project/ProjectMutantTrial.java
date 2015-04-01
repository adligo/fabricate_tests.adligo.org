package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.common.DuplicateRoutineException;
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
import org.adligo.fabricate.models.project.ProjectMutant;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.FabricateProjectType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectDependenciesType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectRoutineType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectStagesType;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.LibraryDependencyMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.ProjectDependencyMutantTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SourceFileScope (sourceClass=ProjectMutant.class,minCoverage=93.0)
public class ProjectMutantTrial extends MockitoSourceFileTrial {

  
  public static DependencyMutant getDependencyLetterPrime(String letter) {
    DependencyMutant depB = new DependencyMutant();
    depB.setArtifact("artifact" + letter + "1");
    depB.setFileName("fileName_v" + letter + "1");
    depB.setGroup("group" + letter + "1");
    depB.setPlatform("platform" + letter + "1");
    depB.setType("type" + letter + "1");
    depB.setVersion("v" + letter + "1");
    return depB;
  }
  
  public DependencyMutant getDependencyA() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactA");
    dm.setExtract(true);
    dm.setFileName("fileName_vA");
    dm.setGroup("groupA");
    dm.setPlatform("platformA");
    dm.setType("typeA");
    dm.setVersion("vA");
    return dm;
  }
  
  public DependencyMutant getDependencyB() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactB");
    dm.setExtract(true);
    dm.setFileName("fileName_vB");
    dm.setGroup("groupB");
    dm.setPlatform("platformB");
    dm.setType("typeB");
    dm.setVersion("vB");
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
  public void testConstructorCopy() throws Exception {
    ProjectMutant pm = new ProjectMutant();
    pm.setDir("dir");
    pm.setName("name");
    pm.setVersion("version");
    
    ProjectMutant copy = new ProjectMutant(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    assertEquals(0, copy.getAttributes().size());
    assertEquals(0, copy.getCommands().size());
    assertEquals(0, copy.getDependencies().size());
    assertEquals(0, copy.getLibraryDependencies().size());
    assertEquals(0, copy.getProjectDependencies().size());
    assertEquals(0, copy.getStages().size());
    assertEquals(0, copy.getTraits().size());
    
    DependencyMutant dmA = getDependencyA();
    pm.addDependency(dmA);
    pm.addDependency(dmA);
    
    LibraryDependencyMutant ldmA = getLibraryDependencyA();
    pm.addLibraryDependency(ldmA);
    pm.addLibraryDependency(ldmA);
    
    ProjectDependencyMutant pdmA = getProjectDependencyA();
    pm.addProjectDependency(pdmA);
    pm.addProjectDependency(pdmA);
    
    copy = new ProjectMutant(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    assertEquals(0, copy.getAttributes().size());
    assertEquals(0, copy.getCommands().size());
    
    List<I_Dependency> dependencies = copy.getDependencies();
    assertSame(dmA, dependencies.get(0));
    assertEquals(1, dependencies.size());
    
    List<I_LibraryDependency> libDeps = copy.getLibraryDependencies();
    assertSame(ldmA, libDeps.get(0));
    assertEquals(1, libDeps.size());
    
    List<I_ProjectDependency> projDeps = copy.getProjectDependencies();
    assertSame(pdmA, projDeps.get(0));
    assertEquals(1, projDeps.size());
    
    assertEquals(0, copy.getStages().size());
    assertEquals(0, copy.getTraits().size());
    
    ParameterMutant attributeA = new ParameterMutant();
    attributeA.setKey("keyA");
    attributeA.setValue("valueA");
    pm.addAttribute(attributeA);
    
    ParameterMutant attributeB = new ParameterMutant();
    attributeB.setKey("keyB");
    attributeB.setValue("valueB");
    
    Parameter attributeB1 = new Parameter(attributeB);
    pm.addAttribute(attributeB1);
    
    RoutineBriefMutant cmdA = new RoutineBriefMutant();
    cmdA.setName("cmdA");
    cmdA.setOrigin(RoutineBriefOrigin.PROJECT_COMMAND);
    pm.addCommand(cmdA);
    
    RoutineBriefMutant cmdB = new RoutineBriefMutant();
    cmdB.setName("cmdB");
    cmdB.setOrigin(RoutineBriefOrigin.PROJECT_COMMAND);
    pm.addCommand(new RoutineBrief(cmdB));//immutable
    
    Dependency dependencyB =new Dependency(getDependencyLetterPrime("DepB"));
    pm.addDependency(dependencyB);//immutable to mutable
    
    LibraryDependencyMutant libDepB = new LibraryDependencyMutant();
    libDepB.setLibraryName("libraryB");
    libDepB.setPlatform("platformB");
    pm.addLibraryDependency(new LibraryDependency(libDepB));//immutable to mutable

    DependencyMutant normA = getDependencyLetterPrime("NormDepA");
    pm.addNormalizedDependency(normA);
    Dependency normB = new Dependency(getDependencyLetterPrime("NormDepB"));
    pm.addNormalizedDependency(normB);//immutable to mutable
    
    ProjectDependencyMutant projDepB = new ProjectDependencyMutant();
    projDepB.setProjectName("projectB");
    projDepB.setPlatform("platformB");
    pm.addProjectDependency(new ProjectDependency(projDepB));//immutable to mutable

    RoutineBriefMutant stageA = new RoutineBriefMutant();
    stageA.setName("stageA");
    stageA.setOrigin(RoutineBriefOrigin.PROJECT_STAGE);
    pm.addStage(stageA);
    
    RoutineBriefMutant stageB = new RoutineBriefMutant();
    stageB.setName("stageB");
    stageB.setOrigin(RoutineBriefOrigin.PROJECT_STAGE);
    pm.addStage(new RoutineBrief(stageB));//immutable
    
    RoutineBriefMutant traitA = new RoutineBriefMutant();
    traitA.setName("traitA");
    traitA.setClazz(EncryptTrait.class);
    traitA.setOrigin(RoutineBriefOrigin.PROJECT_TRAIT);
    pm.addTrait(traitA);
    
    RoutineBriefMutant traitB = new RoutineBriefMutant();
    traitB.setName("traitB");
    traitB.setClazz(EncryptTrait.class);
    traitB.setOrigin(RoutineBriefOrigin.PROJECT_TRAIT);
    pm.addTrait(new RoutineBrief(traitB));//immutable
    
    copy = new ProjectMutant(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    List<I_Parameter> attribs = copy.getAttributes();
    assertTwoFromMemoryAttributes(attributeA, attributeB, attribs);
    //check encapsulation
    attribs.clear();
    attribs = copy.getAttributes();
    assertTwoFromMemoryAttributes(attributeA, attributeB, attribs);
    assertEquals(2, copy.getAttributes().size());

    Map<String,I_RoutineBrief> commands = copy.getCommands();
    assertTwoFromMemoryCommands(commands, cmdA, cmdB, copy);
    //check encapsulation
    commands.clear();
    commands = copy.getCommands();
    assertTwoFromMemoryCommands(commands, cmdA, cmdB, copy);
    assertEquals(2, copy.getCommands().size());
    
    dependencies = copy.getDependencies();
    I_Dependency depA = dependencies.get(0);
    assertSame(dmA, depA);
    I_Dependency depB = dependencies.get(1);
    assertEquals(dependencyB, depB);
    assertEquals(DependencyMutant.class.getName(), depB.getClass().getName());
    assertEquals(2, dependencies.size());
    
    libDeps = copy.getLibraryDependencies();
    I_LibraryDependency libDepA1 = libDeps.get(0);
    assertSame(ldmA, libDepA1);
    I_LibraryDependency libDepB1 = libDeps.get(1);
    assertEquals(libDepB, libDepB1);
    assertEquals(LibraryDependencyMutant.class.getName(), libDepB1.getClass().getName());
    assertEquals(2, libDeps.size());
    
    projDeps = copy.getProjectDependencies();
    I_ProjectDependency projDepA1 = projDeps.get(0);
    assertSame(pdmA, projDepA1);
    I_ProjectDependency projDepB1 = projDeps.get(1);
    assertEquals(projDepB, projDepB1);
    assertEquals(ProjectDependencyMutant.class.getName(), projDepB1.getClass().getName());
    assertEquals(2, projDeps.size());
    
    List<I_Dependency> normDeps = copy.getNormalizedDependencies();
    assertTwoNormDeps(normA, normB, normDeps);
    normDeps.clear();
    //check encapsulation
    normDeps = copy.getNormalizedDependencies();
    assertTwoNormDeps(normA, normB, normDeps);
    
    Map<String,I_RoutineBrief> stages = copy.getStages();
    assertTwoFromMemoryStages(stages, stageA, stageB, copy);
    //check encapsulation
    stages.clear();
    stages = copy.getStages();
    assertTwoFromMemoryStages(stages, stageA, stageB, copy);
    assertEquals(2, stages.size());
    
    //triats
    Map<String,I_RoutineBrief> traits = copy.getTraits();
    assertTwoFromMemoryTraits(traits, traitA, traitB, copy);
    //check encapsulation
    traits.clear();
    traits = copy.getTraits();
    assertTwoFromMemoryTraits(traits, traitA, traitB, copy);
    assertEquals(2, traits.size());
  }

  @SuppressWarnings("boxing")
  private void assertTwoNormDeps(DependencyMutant normA, Dependency normB,
      List<I_Dependency> normDeps) {
    I_Dependency nA = normDeps.get(0);
    assertDependencyLetterPrime("NormDepA", nA);
    assertSame(normA, nA);
    
    I_Dependency nB = normDeps.get(1);
    assertDependencyLetterPrime("NormDepB", nB);
    assertEquals(normB, nB);
    assertEquals(DependencyMutant.class.getName(), nB.getClass().getName());
    assertEquals(2, normDeps.size());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyFromXml() throws Exception {
    I_Project pt = mock(I_Project.class);
    when(pt.getName()).thenReturn("projectName");
    when(pt.getVersion()).thenReturn("projectVersion");
    
    FabricateProjectType fpt = new FabricateProjectType();
    fpt.setDependencies(null);
    ProjectMutant copy = new ProjectMutant("dir2", pt, fpt);
    
    assertEquals("dir2", copy.getDir());
    assertEquals("projectName", copy.getName());
    assertEquals("projectVersion", copy.getVersion());
    
    assertEquals(0, copy.getAttributes().size());
    assertEquals(0, copy.getCommands().size());
    assertEquals(0, copy.getDependencies().size());
    assertEquals(0, copy.getLibraryDependencies().size());
    assertEquals(0, copy.getNormalizedDependencies().size());
    assertEquals(0, copy.getProjectDependencies().size());
    assertEquals(0, copy.getStages().size());
    assertEquals(0, copy.getTraits().size());
    
    ProjectDependenciesType pdt = new ProjectDependenciesType();
    pdt.getDependency().addAll(DependencyMutantTrial.getDependencies());
    pdt.getLibrary().addAll(LibraryDependencyMutantTrial.getListAB());
    pdt.getProject().addAll(ProjectDependencyMutantTrial.getListAB());
    fpt.setDependencies(pdt);
    
    ProjectMutant copy2 = new ProjectMutant("dir2", pt, fpt);
    
    assertEquals("dir2", copy2.getDir());
    assertEquals("projectName", copy2.getName());
    assertEquals("projectVersion", copy2.getVersion());
    
    assertEquals(0, copy.getAttributes().size());
    assertEquals(0, copy.getCommands().size());
    DependencyMutantTrial.assertDependencyConversion(this, copy2.getDependencies(), "projectName");
    LibraryDependencyMutantTrial.assertAB(this, copy2.getLibraryDependencies());
    assertEquals(0, copy.getNormalizedDependencies().size());
    ProjectDependencyMutantTrial.assertAB(this, copy2.getProjectDependencies());
    assertEquals(0, copy.getStages().size());
    assertEquals(0, copy.getTraits().size());
    
    ParamsType params = new ParamsType();
    ParamType param = new ParamType();
    param.setKey("key");
    param.setValue("value");
    params.getParam().add(param);
    
    fpt.setAttributes(params);
    copy2 = new ProjectMutant("dir2", pt, fpt);
    
    assertEquals("dir2", copy2.getDir());
    assertEquals("projectName", copy2.getName());
    assertEquals("projectVersion", copy2.getVersion());
    
    List<I_Parameter> attributes = copy2.getAttributes();
    I_Parameter paramA = attributes.get(0);
    assertEquals("key", paramA.getKey());
    assertEquals("value", paramA.getValue());
    assertEquals(1, attributes.size());
    
    paramA = copy2.getAttribute("key");
    assertEquals("key", paramA.getKey());
    assertEquals("value", paramA.getValue());
    
    assertEquals(0, copy2.getCommands().size());
    
    DependencyMutantTrial.assertDependencyConversion(this, copy2.getDependencies(), "projectName");
    LibraryDependencyMutantTrial.assertAB(this, copy2.getLibraryDependencies());
    assertEquals(0, copy.getNormalizedDependencies().size());
    ProjectDependencyMutantTrial.assertAB(this, copy2.getProjectDependencies());
    assertEquals(0, copy.getStages().size());
    assertEquals(0, copy.getTraits().size());
    
    ProjectRoutineType cmd = new ProjectRoutineType();
    cmd.setName("command");
    fpt.getCommand().add(cmd);
    
    ProjectRoutineType stage = new ProjectRoutineType();
    stage.setName("stage");
    ProjectStagesType stages = new ProjectStagesType();
    fpt.setStages(stages);
    stages.getStage().add(stage);
    
    RoutineParentType trait = new RoutineParentType();
    trait.setName("trait");
    fpt.getTrait().add(trait);
    
    copy2 = new ProjectMutant("dir2", pt, fpt);
    
    assertEquals("dir2", copy2.getDir());
    assertEquals("projectName", copy2.getName());
    assertEquals("projectVersion", copy2.getVersion());
    
    attributes = copy2.getAttributes();
    paramA = attributes.get(0);
    assertEquals("key", paramA.getKey());
    assertEquals("value", paramA.getValue());
    assertEquals(1, attributes.size());
    
    Map<String, I_RoutineBrief> cmds = copy2.getCommands();
    assertEquals("command", cmds.get("command").getName());
    assertEquals(1, cmds.size());
    
    DependencyMutantTrial.assertDependencyConversion(this, copy2.getDependencies(), "projectName");
    LibraryDependencyMutantTrial.assertAB(this, copy2.getLibraryDependencies());
    assertEquals(0, copy.getNormalizedDependencies().size());
    ProjectDependencyMutantTrial.assertAB(this, copy2.getProjectDependencies());
    
    Map<String, I_RoutineBrief> stages2 = copy2.getStages();
    assertEquals("stage", stages2.get("stage").getName());
    assertEquals(1, stages2.size());
    
    Map<String, I_RoutineBrief> trait2 = copy2.getTraits();
    assertEquals("trait", trait2.get("trait").getName());
    assertEquals(1, trait2.size());
  }
  
  @Test
  public void testMethodsEqualsHashCode() {
    ProjectMutant pmA = new ProjectMutant();
    ProjectMutant pmB = new ProjectMutant();
    
    assertEquals(pmA, pmA);
    assertEquals(pmA, pmB);
    pmA.setName("a");
    assertNotEquals(pmA, pmB);
    assertNotEquals(pmB, pmA);
    
    pmB.setName("b");
    assertNotEquals(pmA, pmB);
    assertNotEquals(pmB, pmA);
  }
  
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodsGetsAndSets() {
    ProjectMutant pm = new ProjectMutant();
    pm.setDir("dir");
    assertEquals("dir", pm.getDir());
    
    pm.setName("name.adigo.org");
    assertEquals("name.adigo.org", pm.getName());
    assertEquals("name", pm.getShortName());
    assertEquals("adigo.org", pm.getDomainName());
    
    pm.setVersion("version");
    assertEquals("version", pm.getVersion());
    
    List<I_Parameter> attribsIn = new ArrayList<I_Parameter>();
    ParameterMutant attrib = new ParameterMutant();
    attrib.setKey("aKey");
    attrib.setValue("aVal");
    
    Parameter attribCopy = new Parameter(attrib);
    attribsIn.add(attrib);
    attribsIn.add(attribCopy);
    pm.setAttributes(attribsIn);
    
    List<I_Parameter> attribsOut = pm.getAttributes();
    assertTwoAttributes(attribsOut);
    assertTwoAttributes(pm.getAttributes("aKey", "aVal"));
    attribsOut.clear();
    attribsOut = pm.getAttributes("aKey");
    assertTwoAttributes(attribsOut);
    
    List<I_RoutineBrief> cmdsIn = new ArrayList<I_RoutineBrief>();
    RoutineBriefMutant cmdBriefA = new RoutineBriefMutant();
    cmdBriefA.setName("cmd");
    cmdBriefA.setOrigin(RoutineBriefOrigin.COMMAND);
    cmdBriefA.setClazz(EncryptTrait.class);
    
    RoutineBrief cmdBriefB = new RoutineBrief(cmdBriefA);
    cmdBriefA.setName("cmdA");
    cmdsIn.add(cmdBriefA);
    cmdsIn.add(cmdBriefB);
    pm.setCommands(cmdsIn);
    
    Map<String, I_RoutineBrief> cmdsOut = pm.getCommands();
    assertTwoCommands(cmdsOut, pm);
    cmdsOut.clear();
    cmdsOut = pm.getCommands();
    assertTwoCommands(cmdsOut, pm);
    
    DependencyMutant dmA = getDependencyA();
    pm.addDependency(dmA);
    List<I_Dependency> dependencies = pm.getDependencies();
    assertSame(dmA, dependencies.get(0));
    assertEquals(1, dependencies.size());
    
    DependencyMutant dmB = getDependencyB();
    List<DependencyMutant> dms = new ArrayList<DependencyMutant>();
    dms.add(dmA);
    dms.add(dmB);
    pm.setDependencies(dms);
    
    dependencies = pm.getDependencies();
    assertSame(dmA, dependencies.get(0));
    assertSame(dmB, dependencies.get(1));
    assertEquals(2, dependencies.size());
    
    LibraryDependencyMutant ldmA = getLibraryDependencyA();
    pm.addLibraryDependency(ldmA);
    List<I_LibraryDependency> libDeps = pm.getLibraryDependencies();
    assertSame(ldmA, libDeps.get(0));
    assertEquals(1, libDeps.size());
    
    LibraryDependencyMutant ldmB = getLibraryDependencyB();
    List<LibraryDependencyMutant> ldms = new ArrayList<LibraryDependencyMutant>();
    ldms.add(ldmA);
    ldms.add(ldmB);
    pm.setLibraryDependencies(ldms);
    
    libDeps = pm.getLibraryDependencies();
    assertSame(ldmA, libDeps.get(0));
    assertSame(ldmB, libDeps.get(1));
    assertEquals(2, libDeps.size());
    
    ProjectDependencyMutant pdmA = getProjectDependencyA();
    pm.addProjectDependency(pdmA);
    List<I_ProjectDependency> projDeps = pm.getProjectDependencies();
    assertSame(pdmA, projDeps.get(0));
    assertEquals(1, projDeps.size());
    
    ProjectDependencyMutant pdmB = getProjectDependencyB();
    List<ProjectDependencyMutant> pdms = new ArrayList<ProjectDependencyMutant>();
    pdms.add(pdmA);
    pdms.add(pdmB);
    pm.setProjectDependencies(pdms);
    
    projDeps = pm.getProjectDependencies();
    assertSame(pdmA, projDeps.get(0));
    assertSame(pdmB, projDeps.get(1));
    assertEquals(2, libDeps.size());
    
    List<I_RoutineBrief> stagesIn = new ArrayList<I_RoutineBrief>();
    RoutineBriefMutant stageBriefA = new RoutineBriefMutant();
    stageBriefA.setName("stage");
    stageBriefA.setOrigin(RoutineBriefOrigin.STAGE);
    stageBriefA.setClazz(EncryptTrait.class);
    
    RoutineBrief stageBriefB = new RoutineBrief(stageBriefA);
    stageBriefA.setName("stageA");
    stagesIn.add(stageBriefA);
    stagesIn.add(stageBriefB);
    pm.setStages(stagesIn);
    
    Map<String, I_RoutineBrief> stagesOut = pm.getStages();
    assertTwoStages(stagesOut, pm);
    stagesOut.clear();
    stagesOut = pm.getStages();
    assertTwoStages(stagesOut, pm);
    
    List<I_RoutineBrief> traitsIn = new ArrayList<I_RoutineBrief>();
    RoutineBriefMutant traitBriefA = new RoutineBriefMutant();
    traitBriefA.setName("trait");
    traitBriefA.setOrigin(RoutineBriefOrigin.TRAIT);
    traitBriefA.setClazz(EncryptTrait.class);
    
    RoutineBrief traitBriefB = new RoutineBrief(traitBriefA);
    traitBriefA.setName("traitA");
    traitsIn.add(traitBriefA);
    traitsIn.add(traitBriefB);
    pm.setTraits(traitsIn);
    
    Map<String, I_RoutineBrief> traitsOut = pm.getTraits();
    assertTwoTraits(traitsOut, pm);
    traitsOut.clear();
    traitsOut = pm.getTraits();
    assertTwoTraits(traitsOut, pm);
    
    
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodsAddAndSetImmutables() throws Exception  {
    ProjectMutant pm = new ProjectMutant();
    
    Dependency dmA = new Dependency(getDependencyA());
    pm.addDependency(dmA);
    List<I_Dependency> dependencies = pm.getDependencies();
    assertEquals(dmA, dependencies.get(0));
    assertEquals(1, dependencies.size());
    
    Dependency dmB = new Dependency(getDependencyB());
    List<Dependency> dms = new ArrayList<Dependency>();
    dms.add(dmA);
    dms.add(dmB);
    pm.setDependencies(dms);
    
    dependencies = pm.getDependencies();
    assertEquals(dmA, dependencies.get(0));
    assertEquals(dmB, dependencies.get(1));
    assertEquals(2, dependencies.size());
    
    LibraryDependency ldmA = new LibraryDependency(getLibraryDependencyA());
    pm.addLibraryDependency(ldmA);
    List<I_LibraryDependency> libDeps = pm.getLibraryDependencies();
    assertEquals(ldmA, libDeps.get(0));
    assertEquals(1, libDeps.size());
    
    LibraryDependency ldmB =  new LibraryDependency(getLibraryDependencyB());
    List<LibraryDependency> ldms = new ArrayList<LibraryDependency>();
    ldms.add(ldmA);
    ldms.add(ldmB);
    pm.setLibraryDependencies(ldms);
    
    libDeps = pm.getLibraryDependencies();
    assertEquals(ldmA, libDeps.get(0));
    assertEquals(ldmB, libDeps.get(1));
    assertEquals(2, libDeps.size());
    
    ProjectDependency pdmA = new ProjectDependency(getProjectDependencyA());
    pm.addProjectDependency(pdmA);
    List<I_ProjectDependency> projDeps = pm.getProjectDependencies();
    assertEquals(pdmA, projDeps.get(0));
    assertEquals(1, projDeps.size());
    
    ProjectDependency pdmB = new ProjectDependency(getProjectDependencyB());
    List<ProjectDependency> pdms = new ArrayList<ProjectDependency>();
    pdms.add(pdmA);
    pdms.add(pdmB);
    pm.setProjectDependencies(pdms);
    
    projDeps = pm.getProjectDependencies();
    assertEquals(pdmA, projDeps.get(0));
    assertEquals(pdmB, projDeps.get(1));
    assertEquals(2, libDeps.size());
  }
  
  @Test
  public void testMethodsAddExceptions() {
    ProjectMutant pm = new ProjectMutant();
    pm.setName("projectName");
    
    RoutineBriefMutant routine = new RoutineBriefMutant();
    routine.setName("routineName");
    routine.setOrigin(RoutineBriefOrigin.PROJECT_COMMAND);
    
    pm.addCommand(routine);
    DuplicateRoutineException caught = null;
    try {
      pm.addCommand(routine);
    } catch (DuplicateRoutineException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("routineName", caught.getName());
    assertEquals(RoutineBriefOrigin.PROJECT_COMMAND, caught.getOrigin());
    assertEquals("projectName", caught.getParentName());
    
    routine.setOrigin(RoutineBriefOrigin.PROJECT_STAGE);
    pm.addStage(routine);
    caught = null;
    try {
      pm.addStage(routine);
    } catch (DuplicateRoutineException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("routineName", caught.getName());
    assertEquals(RoutineBriefOrigin.PROJECT_STAGE, caught.getOrigin());
    assertEquals("projectName", caught.getParentName());
    
    routine.setOrigin(RoutineBriefOrigin.PROJECT_TRAIT);
    pm.addTrait(routine);
    caught = null;
    try {
      pm.addTrait(routine);
    } catch (DuplicateRoutineException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("routineName", caught.getName());
    assertEquals(RoutineBriefOrigin.PROJECT_TRAIT, caught.getOrigin());
    assertEquals("projectName", caught.getParentName());
  }
  
  @Test
  public void testMethodsToString() {
    ProjectMutant pm = mock(ProjectMutant.class);
    
    when(pm.getAttributes()).thenReturn(null);
    when(pm.getCommands()).thenReturn(null);
    when(pm.getDependencies()).thenReturn(null);
    when(pm.getLibraryDependencies()).thenReturn(null);
    when(pm.getNormalizedDependencies()).thenReturn(null);
    when(pm.getProjectDependencies()).thenReturn(null);
    when(pm.getStages()).thenReturn(null);
    when(pm.getTraits()).thenReturn(null);
    String result = ProjectMutant.toString(pm);
    int firstSquare = result.indexOf("[");
    result = result.substring(firstSquare, result.length());
    assertEquals("[name=null, dir=null]", result);
    
    pm = new ProjectMutant();
    pm.setDir("dir");
    
    pm.setName("name.adigo.org");
    assertEquals("ProjectMutant [name=name.adigo.org, dir=dir, attributes=0, commands=0, dependencies=0, "
        + "libraryDependencies=0, normalizedDependencies=0, projectDependencies=0, stages=0, traits=0]", ProjectMutant.toString(pm));
    
  }
  
  private void assertDependencyLetterPrime(String letter, I_Dependency dep) {
    assertEquals("artifact" + letter + "1", dep.getArtifact());
    assertEquals("fileName_v" + letter + "1", dep.getFileName());
    assertEquals("group" + letter + "1",  dep.getGroup());
    assertEquals("platform" + letter + "1", dep.getPlatform());
    assertEquals("type" + letter + "1", dep.getType());
    assertEquals("v" + letter + "1", dep.getVersion());
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoAttributes(List<I_Parameter> attribsOut) {
    I_Parameter attribZero = attribsOut.get(0);
    assertEquals("aKey", attribZero.getKey());
    assertEquals("aVal", attribZero.getValue());
    assertEquals(ParameterMutant.class.getName(), attribZero.getClass().getName());
    
    I_Parameter attribOne = attribsOut.get(1);
    assertEquals("aKey", attribOne.getKey());
    assertEquals("aVal", attribOne.getValue());
    assertEquals(ParameterMutant.class.getName(), attribOne.getClass().getName());
    assertEquals(2, attribsOut.size());
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoCommands(Map<String, I_RoutineBrief> cmdsOut, ProjectMutant project) {
    I_RoutineBrief cmdOA = cmdsOut.get("cmdA");
    assertEquals(EncryptTrait.class, cmdOA.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), cmdOA.getClass().getName());
    assertSame(cmdOA, project.getCommand("cmdA"));
    
    I_RoutineBrief cmdOB = cmdsOut.get("cmd");
    assertEquals(EncryptTrait.class, cmdOB.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), cmdOB.getClass().getName());
    assertEquals(2, cmdsOut.size());
    assertSame(cmdOB, project.getCommand("cmd"));
  }

  private void assertTwoFromMemoryAttributes(ParameterMutant attributeA,
      ParameterMutant attributeB, List<I_Parameter> attribs) {
    assertSame(attributeA, attribs.get(0));
    assertEquals(ParameterMutant.class.getName(), attribs.get(0).getClass().getName());
    
    assertEquals(attributeB, attribs.get(1));
    assertNotSame(attributeB, attribs.get(1));
    assertEquals(ParameterMutant.class.getName(), attribs.get(1).getClass().getName());
  }

  
  @SuppressWarnings("boxing")
  private void assertTwoFromMemoryCommands(Map<String, I_RoutineBrief> cmdsOut, 
      RoutineBriefMutant a, RoutineBriefMutant b, ProjectMutant project) {
    I_RoutineBrief cmdOA = cmdsOut.get("cmdA");
    assertEquals(RoutineBriefOrigin.PROJECT_COMMAND, cmdOA.getOrigin());
    assertSame(a, cmdOA);
    assertEquals(RoutineBriefMutant.class.getName(), cmdOA.getClass().getName());
    assertSame(cmdOA, project.getCommand("cmdA"));
    
    I_RoutineBrief cmdOB = cmdsOut.get("cmdB");
    assertEquals(b, cmdOB);
    assertEquals(RoutineBriefOrigin.PROJECT_COMMAND, cmdOB.getOrigin());
    assertEquals(RoutineBriefMutant.class.getName(), cmdOB.getClass().getName());
    assertEquals(2, cmdsOut.size());
    assertSame(cmdOB, project.getCommand("cmdB"));
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoFromMemoryStages(Map<String, I_RoutineBrief> stagesOut, 
      RoutineBriefMutant a, RoutineBriefMutant b, 
      ProjectMutant project) {
    
    I_RoutineBrief stageOA = stagesOut.get("stageA");
    assertSame(a, stageOA);
    assertEquals(RoutineBriefOrigin.PROJECT_STAGE ,stageOA.getOrigin());
    assertNull(stageOA.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), stageOA.getClass().getName());
    assertSame(stageOA, project.getStage("stageA"));
    
    I_RoutineBrief stageOB = stagesOut.get("stageB");
    assertEquals(b, stageOB);
    assertEquals(RoutineBriefOrigin.PROJECT_STAGE ,stageOB.getOrigin());
    assertNull(stageOB.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), stageOB.getClass().getName());
    
    assertEquals(2, stagesOut.size());
    assertSame(stageOB, project.getStage("stageB"));
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoFromMemoryTraits(Map<String, I_RoutineBrief> traitsOut, 
      RoutineBriefMutant a, RoutineBriefMutant b, 
      ProjectMutant project) {
    I_RoutineBrief traitOA = traitsOut.get("traitA");
    assertSame(a, traitOA);
    assertEquals(EncryptTrait.class, traitOA.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), traitOA.getClass().getName());
    assertSame(traitOA, project.getTrait("traitA"));
    
    I_RoutineBrief traitOB = traitsOut.get("traitB");
    assertEquals(b, traitOB);
    assertEquals(EncryptTrait.class, traitOB.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), traitOB.getClass().getName());
    assertEquals(2, traitsOut.size());
    assertSame(traitOB, project.getTrait("traitB"));
  }
  
  @SuppressWarnings("boxing")
  private void assertTwoStages(Map<String, I_RoutineBrief> stagesOut, ProjectMutant project) {
    I_RoutineBrief stageOA = stagesOut.get("stageA");
    assertEquals(EncryptTrait.class, stageOA.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), stageOA.getClass().getName());
    assertSame(stageOA, project.getStage("stageA"));
    
    I_RoutineBrief stageOB = stagesOut.get("stage");
    assertEquals(EncryptTrait.class, stageOB.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), stageOB.getClass().getName());
    assertEquals(2, stagesOut.size());
    assertSame(stageOB, project.getStage("stage"));
  }
  
  
  @SuppressWarnings("boxing")
  private void assertTwoTraits(Map<String, I_RoutineBrief> traitsOut, ProjectMutant project) {
    I_RoutineBrief traitOA = traitsOut.get("traitA");
    assertEquals(EncryptTrait.class, traitOA.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), traitOA.getClass().getName());
    assertSame(traitOA, project.getTrait("traitA"));
    
    I_RoutineBrief traitOB = traitsOut.get("trait");
    assertEquals(EncryptTrait.class, traitOB.getClazz());
    assertEquals(RoutineBriefMutant.class.getName(), traitOB.getClass().getName());
    assertEquals(2, traitsOut.size());
    assertSame(traitOB, project.getTrait("trait"));
  }

}

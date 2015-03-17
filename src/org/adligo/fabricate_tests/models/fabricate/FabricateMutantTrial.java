package org.adligo.fabricate_tests.models.fabricate;


import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.fabricate.I_Fabricate;
import org.adligo.fabricate.models.fabricate.I_FabricateXmlDiscovery;
import org.adligo.fabricate.models.fabricate.I_JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettingsMutant;
import org.adligo.fabricate.models.project.I_ProjectBrief;
import org.adligo.fabricate.models.project.ProjectBrief;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StageType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesAndProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SourceFileScope (sourceClass=FabricateMutant.class, minCoverage=80.0)
public class FabricateMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDefaults() {
    FabricateMutant fm = new FabricateMutant();
    assertNull(fm.getFabricateHome());
    assertNull(fm.getFabricateRepository());
    assertNull(fm.getJavaHome());
    assertNull(fm.getJavaHome());
    assertNull(fm.getJavaSettings());
    assertNull(fm.getJavaSettings());
    assertEquals(FabricateDefaults.JAVA_THREADS, fm.getThreads());
    assertEquals(FabricateDefaults.JAVA_XMS_DEFAULT, fm.getXms());
    assertEquals(FabricateDefaults.JAVA_XMX_DEFAULT, fm.getXmx());
    List<I_Dependency> deps = fm.getDependencies();
    assertEquals(0, deps.size());
  }
  
  @SuppressWarnings({"unused"})
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new FabricateMutant(null, null);
          }
        }); 
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new FabricateMutant((I_Fabricate) null);
          }
        }); 
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopyFromInterface() {
    FabricateMutant fm = new FabricateMutant();
    fm.setJavaHome("jh");
    fm.setFabricateHome("fh");
    fm.setFabricateRepository("fr");
    fm.setProjectsDir("pjDir");
    fm.setFabricateXmlRunDir("fabXmlDir");
    
    FabricateMutant copy = new FabricateMutant(fm);
    assertEquals("jh", copy.getJavaHome());
    assertEquals("fh", copy.getFabricateHome());
    assertEquals("fr", copy.getFabricateRepository());
    assertEquals("pjDir", copy.getProjectsDir());
    
    JavaSettingsMutant jsm = new JavaSettingsMutant();
    jsm.setThreads(1);
    jsm.setXms("120m");
    jsm.setXmx("130m");
    fm.setJavaSettings(jsm);
    
    copy = new FabricateMutant(fm);
    assertEquals("fabXmlDir", copy.getFabricateXmlRunDir());
    assertNull(copy.getFabricateProjectRunDir());
    assertNull(copy.getFabricateDevXmlDir());
    assertEquals("jh", copy.getJavaHome());
    assertEquals("fh", copy.getFabricateHome());
    assertEquals("fr", copy.getFabricateRepository());
    assertEquals(1, copy.getThreads());
    I_JavaSettings js = copy.getJavaSettings();
    assertEquals(JavaSettingsMutant.class.getName(), js.getClass().getName());
    assertEquals("120m", js.getXms());
    assertEquals("130m", js.getXmx());
    
    fm.addRemoteRepository("repoC");
    List<String> repoC = fm.getRemoteRepositories();
    assertEquals("repoC", repoC.get(0));
    assertEquals(1, repoC.size());
    
    List<String> remoteRepos = new ArrayList<String>();
    remoteRepos.add("repoA");
    remoteRepos.add("repoB");
    remoteRepos.add(null);
    fm.setRemoteRepositories(remoteRepos);
    List<String> repos = fm.getRemoteRepositories();
    assertEquals("repoA", repos.get(0));
    assertEquals("repoB", repos.get(1));
    assertEquals(2, repos.size());
    
    FabricateMutant mock = mock(FabricateMutant.class);
    copy = new FabricateMutant(mock);
    List<I_Dependency> deps = copy.getDependencies();
    assertEquals(0, deps.size());
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifactA");
    dmA.setExtract(false);
    dmA.setFileName("fileNameA");
    dmA.setGroup("groupA");
    dmA.setPlatform("platformA");
    dmA.setType("typeA");
    dmA.setVersion("versionA");
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifactB");
    dmB.setExtract(true);
    dmB.setFileName("fileNameB");
    dmB.setGroup("groupB");
    dmB.setPlatform("platformB");
    dmB.setType("typeB");
    dmB.setVersion("versionB");  
    
    List<I_Dependency> dms = new ArrayList<I_Dependency>();
    dms.add(dmA);
    dms.add(new Dependency(dmB));
    dms.add(null);
    
    fm.setDependencies(dms);
    fm.setCommands(getRoutines("command", RoutineBriefOrigin.COMMAND));
    fm.setFacets(getRoutines("facet", RoutineBriefOrigin.FACET));
    fm.setStages(getRoutines("stage", RoutineBriefOrigin.STAGE));
    fm.setTraits(getRoutines("trait", RoutineBriefOrigin.TRAIT));
    fm.setFabricateProjectRunDir("projectRunDir");
    fm.setFabricateDevXmlDir("devXmlDir");
    copy = new FabricateMutant(fm);
    assertEquals("fabXmlDir", copy.getFabricateXmlRunDir());
    assertEquals("projectRunDir", copy.getFabricateProjectRunDir());
    assertEquals("devXmlDir", copy.getFabricateDevXmlDir());
    
    assertSame(dmA, copy.getDependencies().get(0));
    I_Dependency dtCopy = copy.getDependencies().get(1);
    assertEquals(DependencyMutant.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactB",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameB" ,dtCopy.getFileName());
    assertEquals("groupB" ,dtCopy.getGroup());
    assertEquals("platformB" ,dtCopy.getPlatform());
    assertEquals("typeB" ,dtCopy.getType());
    assertEquals("versionB" ,dtCopy.getVersion()); 
    
    I_RoutineBrief cmd = assertRoutines(copy.getCommands(), "command", RoutineBriefOrigin.COMMAND);
    assertSame(cmd, copy.getCommand("command"));
    I_RoutineBrief facet = assertRoutines(copy.getFacets(), "facet", RoutineBriefOrigin.FACET);
    assertSame(facet, copy.getFacet("facet"));
    I_RoutineBrief stage = assertRoutines(copy.getStages(), "stage", RoutineBriefOrigin.STAGE);
    assertSame(stage, copy.getStage("stage"));
    I_RoutineBrief trait = assertRoutines(copy.getTraits(), "trait", RoutineBriefOrigin.TRAIT);
    assertSame(trait, copy.getTrait("trait"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopiesFromXml() throws Exception {
    FabricateType ft = new FabricateType();
    JavaType jt = new JavaType();
    jt.setThreads(3);
    jt.setXms("12m");
    jt.setXmx("13m");
    ft.setJava(jt);
    
    I_FabricateXmlDiscovery fxml = mock(I_FabricateXmlDiscovery.class);
    
    RoutineParentType rtp = new RoutineParentType();
    rtp.setName("command");
    rtp.setClazz(EncryptTrait.class.getName());
    ParamsType params = new ParamsType();
    ParamType param = new ParamType();
    param.setKey("commandKey");
    param.setValue("commandValue");
    params.getParam().add(param);
    rtp.setParams(params);
    ft.getCommand().add(rtp);

    StageType stage = new StageType();
    stage.setName("stage");
    stage.setClazz(EncryptTrait.class.getName());
    ParamsType stageParams = new ParamsType();
    ParamType stageParam = new ParamType();
    stageParam.setKey("stageKey");
    stageParam.setValue("stageValue");
    stageParams.getParam().add(stageParam);
    stage.setParams(stageParams);
    
    StagesAndProjectsType spt = new StagesAndProjectsType();
    StagesType st = new StagesType();
    st.getStage().add(stage);
    spt.setStages(st);
    ft.setProjectGroup(spt);
   
    RoutineParentType trait = new RoutineParentType();
    trait.setName("trait");
    trait.setClazz(EncryptTrait.class.getName());
    ParamsType traitParams = new ParamsType();
    ParamType traitParam = new ParamType();
    traitParam.setKey("traitKey");
    traitParam.setValue("traitValue");
    traitParams.getParam().add(traitParam);
    trait.setParams(traitParams);
    ft.getTrait().add(trait);
    
    
    FabricateMutant fm = new FabricateMutant(ft, fxml);
    assertEquals(3, fm.getThreads());
    I_JavaSettings js = fm.getJavaSettings();
    assertEquals(3, js.getThreads());
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    assertNull(fm.getFabricateDevXmlDir());
    assertNull(fm.getFabricateProjectRunDir());
    assertNull(fm.getFabricateXmlRunDir());
    
    FabricateDependencies fdeps = new FabricateDependencies();
    fdeps.getRemoteRepository().add("repoA");
    fdeps.getRemoteRepository().add("repoB");
    ft.setDependencies(fdeps);
    when(fxml.getDevXmlDir()).thenReturn("devx");
    when(fxml.getProjectXmlDir()).thenReturn("projx");
    when(fxml.getFabricateXmlDir()).thenReturn("fabx");
    
    fm = new FabricateMutant(ft, fxml);
    assertEquals("devx", fm.getFabricateDevXmlDir());
    assertEquals("projx", fm.getFabricateProjectRunDir());
    assertEquals("fabx", fm.getFabricateXmlRunDir());
    
    List<String> repos = fm.getRemoteRepositories();
    assertEquals("repoA", repos.get(0));
    assertEquals("repoB", repos.get(1));
    assertEquals(2, repos.size());
    
    Map<String, I_RoutineBrief> commands = fm.getCommands();
    assertEquals(0, commands.size());
    
    Map<String, I_RoutineBrief> stages = fm.getStages();
    assertEquals(0, stages.size());
    
    Map<String, I_RoutineBrief> traits = fm.getTraits();
    assertEquals(0, traits.size());
    
    
    List<DependencyType> dts = DependencyMutantTrial.getDependencies();
    
    fdeps.getDependency().addAll(dts);
    
    
    fm = new FabricateMutant(ft, fxml);
    assertEquals(3, fm.getThreads());
    js = fm.getJavaSettings();
    assertEquals(3, js.getThreads());
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    

    
    DependencyMutantTrial.assertDependencyConversion(this, fm.getDependencies());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethods() {
    FabricateMutant fm = new FabricateMutant();
    fm.setJavaHome("jh");
    assertEquals("jh", fm.getJavaHome());
    
    fm.setFabricateHome("fh");
    assertEquals("fh", fm.getFabricateHome());
    
    fm.setFabricateRepository("fr");
    assertEquals("fr", fm.getFabricateRepository());
    
    JavaSettingsMutant jsm = new JavaSettingsMutant();
    jsm.setThreads(1);
    jsm.setXms("12m");
    jsm.setXmx("13m");
    fm.setJavaSettings(jsm);
    assertSame(jsm, fm.getJavaSettings());
    
    fm.setJavaSettings(new JavaSettings(jsm));
    I_JavaSettings copy = fm.getJavaSettings();
    assertNotSame(jsm, copy);
    assertEquals(1, copy.getThreads());
    assertEquals("12m", copy.getXms());
    assertEquals("13m", copy.getXmx());
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifactA");
    dmA.setExtract(false);
    dmA.setFileName("fileNameA");
    dmA.setGroup("groupA");
    dmA.setPlatform("platformA");
    dmA.setType("typeA");
    dmA.setVersion("versionA");
    
    fm.addDependency(dmA);
    assertSame(dmA, fm.getDependencies().get(0));
    
    DependencyType dtC = new DependencyType();
    dtC.setArtifact("artifactC");
    dtC.setExtract(true);
    dtC.setFileName("fileNameC");
    dtC.setGroup("groupC");
    dtC.setPlatform("platformC");
    dtC.setType("typeC");
    dtC.setVersion("versionC");  
    
    fm.addDependency(dtC);
    I_Dependency dtCopy = fm.getDependencies().get(1);
    assertEquals(DependencyMutant.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactC",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameC" ,dtCopy.getFileName());
    assertEquals("groupC" ,dtCopy.getGroup());
    assertEquals("platformC" ,dtCopy.getPlatform());
    assertEquals("typeC" ,dtCopy.getType());
    assertEquals("versionC" ,dtCopy.getVersion());  
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifactB");
    dmB.setExtract(true);
    dmB.setFileName("fileNameB");
    dmB.setGroup("groupB");
    dmB.setPlatform("platformB");
    dmB.setType("typeB");
    dmB.setVersion("versionB");  
    
    List<DependencyMutant> dms = new ArrayList<DependencyMutant>();
    dms.add(dmA);
    dms.add(dmB);
    dms.add(null);
    
    fm.setDependencies(dms);
    assertSame(dmA, fm.getDependencies().get(0));
    assertSame(dmB, fm.getDependencies().get(1));
    
    List<I_Dependency> dms2 = new ArrayList<I_Dependency>();
    dms2.add(dmA);
    dms2.add(new Dependency(dmB));
    
    fm.setDependencies(dms);
    assertSame(dmA, fm.getDependencies().get(0));
    dtCopy = fm.getDependencies().get(1);
    assertEquals(DependencyMutant.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactB",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameB" ,dtCopy.getFileName());
    assertEquals("groupB" ,dtCopy.getGroup());
    assertEquals("platformB" ,dtCopy.getPlatform());
    assertEquals("typeB" ,dtCopy.getType());
    assertEquals("versionB" ,dtCopy.getVersion()); 
    
    fm.setCommands(getRoutines("command", RoutineBriefOrigin.COMMAND));
    I_RoutineBrief cmd = assertRoutines(fm.getCommands(), "command", RoutineBriefOrigin.COMMAND);
    assertSame(cmd, fm.getCommand("command"));
    // check encapsulation
    fm.getCommands().clear();
    cmd = assertRoutines(fm.getCommands(), "command", RoutineBriefOrigin.COMMAND);
    assertSame(cmd, fm.getCommand("command"));
    
    fm.setFacets(getRoutines("facet", RoutineBriefOrigin.FACET));
    I_RoutineBrief facet = assertRoutines(fm.getFacets(), "facet", RoutineBriefOrigin.FACET);
    assertSame(facet, fm.getFacet("facet"));
    // check encapsulation
    fm.getFacets().clear();
    cmd = assertRoutines(fm.getFacets(), "facet", RoutineBriefOrigin.FACET);
    assertSame(cmd, fm.getFacet("facet"));
    
    fm.setStages(getRoutines("stage", RoutineBriefOrigin.STAGE));
    I_RoutineBrief stage = assertRoutines(fm.getStages(), "stage", RoutineBriefOrigin.STAGE);
    assertSame(stage, fm.getStage("stage"));
    // check encapsulation
    fm.getStages().clear();
    stage = assertRoutines(fm.getStages(), "stage", RoutineBriefOrigin.STAGE);
    assertSame(stage, fm.getStage("stage"));
    
    fm.setTraits(getRoutines("trait", RoutineBriefOrigin.TRAIT));
    I_RoutineBrief trait = assertRoutines(fm.getTraits(), "trait", RoutineBriefOrigin.TRAIT);
    assertSame(trait, fm.getTrait("trait"));
    // check encapsulation
    fm.getTraits().clear();
    trait = assertRoutines(fm.getTraits(), "trait", RoutineBriefOrigin.TRAIT);
    assertSame(trait, fm.getTrait("trait"));
    
    fm.setProjectsDir("pjDir");
    assertEquals("pjDir", fm.getProjectsDir());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsAddFromXmlTypes() throws Exception {
    
    FabricateType ft = new FabricateType();
    JavaType jt = new JavaType();
    jt.setThreads(3);
    jt.setXms("12m");
    jt.setXmx("13m");
    ft.setJava(jt);
    
    RoutineParentType rtp = new RoutineParentType();
    rtp.setName("command");
    rtp.setClazz(EncryptTrait.class.getName());
    ParamsType params = new ParamsType();
    ParamType param = new ParamType();
    param.setKey("commandKey");
    param.setValue("commandValue");
    params.getParam().add(param);
    rtp.setParams(params);
    ft.getCommand().add(rtp);

    RoutineParentType facetType = new RoutineParentType();
    facetType.setName("facet");
    facetType.setClazz(EncryptTrait.class.getName());
    ParamsType fParams = new ParamsType();
    ParamType fParam = new ParamType();
    fParam.setKey("facetKey");
    fParam.setValue("facetValue");
    fParams.getParam().add(fParam);
    facetType.setParams(fParams);
    ft.getFacet().add(facetType);
    
    StageType stage = new StageType();
    stage.setName("stage");
    stage.setClazz(EncryptTrait.class.getName());
    ParamsType stageParams = new ParamsType();
    ParamType stageParam = new ParamType();
    stageParam.setKey("stageKey");
    stageParam.setValue("stageValue");
    stageParams.getParam().add(stageParam);
    stage.setParams(stageParams);
    
    StagesAndProjectsType spt = new StagesAndProjectsType();
    StagesType st = new StagesType();
    st.getStage().add(stage);
    spt.setStages(st);
    ft.setProjectGroup(spt);
   
    RoutineParentType trait = new RoutineParentType();
    trait.setName("trait");
    trait.setClazz(EncryptTrait.class.getName());
    ParamsType traitParams = new ParamsType();
    ParamType traitParam = new ParamType();
    traitParam.setKey("traitKey");
    traitParam.setValue("traitValue");
    traitParams.getParam().add(traitParam);
    trait.setParams(traitParams);
    ft.getTrait().add(trait);
    
    FabricateMutant fm = new FabricateMutant();
    fm.addCommands(ft);
    assertRoutinesFromXml(fm.getCommands(), "command", RoutineBriefOrigin.FABRICATE_COMMAND);
    //check encapsulation
    fm.getCommands().clear();
    assertRoutinesFromXml(fm.getCommands(), "command", RoutineBriefOrigin.FABRICATE_COMMAND);

    fm.addFacets(ft.getFacet());
    assertRoutinesFromXml(fm.getFacets(), "facet", RoutineBriefOrigin.FABRICATE_FACET);
    //check encapsulation
    fm.getFacets().clear();
    assertRoutinesFromXml(fm.getFacets(), "facet", RoutineBriefOrigin.FABRICATE_FACET);
    
    fm.addStages(ft);
    assertRoutinesFromXml(fm.getStages(), "stage", RoutineBriefOrigin.FABRICATE_STAGE);
    //check encapsulation
    fm.getStages().clear();
    assertRoutinesFromXml(fm.getStages(), "stage", RoutineBriefOrigin.FABRICATE_STAGE);
    
    fm.addTraits(ft.getTrait());
    assertRoutinesFromXml(fm.getTraits(), "trait", RoutineBriefOrigin.FABRICATE_TRAIT);
    //check encapsulation
    fm.getTraits().clear();
    assertRoutinesFromXml(fm.getTraits(), "trait", RoutineBriefOrigin.FABRICATE_TRAIT);
    
    ProjectsType projects = new ProjectsType();
    RoutineType scm = new RoutineType();
    scm.setName("Git");
    projects.setScm(scm);
    
    ProjectType projA = new ProjectType();
    projA.setName("a.example.com");
    projA.setVersion("");
    
    ProjectType projB = new ProjectType();
    projB.setName("b.example.com");
    projB.setVersion("1");
    
    projects.getProject().add(projA);
    projects.getProject().add(projB);
    spt.setProjects(projects);
    
    fm.addScmAndProjects(ft);
    
    I_RoutineBrief rb =  fm.getScm();
    assertNotNull(rb);
    assertEquals("Git", rb.getName());
    assertEquals(RoutineBriefMutant.class.getName(), rb.getClass().getName());
    
    List<I_ProjectBrief> projs = fm.getProjects();
    I_ProjectBrief prjA = projs.get(0);
    assertNotNull(prjA);
    assertEquals("a.example.com", prjA.getName());
    assertEquals("", prjA.getVersion());
    assertEquals(ProjectBrief.class.getName(), prjA.getClass().getName());
    
    I_ProjectBrief prjB = projs.get(1);
    assertNotNull(prjB);
    assertEquals("b.example.com", prjB.getName());
    assertEquals("1", prjB.getVersion());
    assertEquals(ProjectBrief.class.getName(), prjB.getClass().getName());
    assertEquals(2, projs.size());
  }
  
  @SuppressWarnings("boxing")
  private void assertRoutinesFromXml(Map<String,I_RoutineBrief> routines, String name, 
      RoutineBriefOrigin origin) {
    I_RoutineBrief route = routines.get(name);
    assertNotNull(route);
    
    assertEquals(name, route.getName());
    assertSame(origin, route.getOrigin());
    assertEquals(EncryptTrait.class.getName(), route.getClazz().getName());
    assertEquals(RoutineBriefMutant.class.getName(), route.getClass().getName());
    assertEquals("java.util.HashMap", routines.getClass().getName());
    
    List<I_Parameter> params = route.getParameters();
    I_Parameter param = params.get(0);
    assertEquals(name + "Key", param.getKey());
    assertEquals(name + "Value", param.getValue());
    
    assertEquals(1, params.size());
    
    routines.clear();
    assertEquals(0, routines.size());
  }
  
  @SuppressWarnings("boxing")
  private I_RoutineBrief assertRoutines(Map<String,I_RoutineBrief> routines, String name, 
      RoutineBriefOrigin origin) {
    I_RoutineBrief route = routines.get(name);
    assertNotNull(route);
    
    assertEquals(name, route.getName());
    assertSame(origin, route.getOrigin());
    assertEquals(RoutineBriefMutant.class.getName(), route.getClass().getName());
    assertEquals("java.util.HashMap", routines.getClass().getName());
    
    routines.clear();
    assertEquals(0, routines.size());
    return route;
  }
  
  private Map<String, I_RoutineBrief> getRoutines(String name, RoutineBriefOrigin origin) {
    Map<String, I_RoutineBrief> toRet = new HashMap<String, I_RoutineBrief>();
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName(name);
    rbm.setOrigin(origin);
    toRet.put(name, rbm);
    return toRet;
    
  }
}

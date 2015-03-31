package org.adligo.fabricate_tests.models.fabricate;


import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.models.common.DuplicateRoutineException;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBrief;
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
import org.adligo.fabricate_tests.models.common.ParameterMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SourceFileScope (sourceClass=FabricateMutant.class, minCoverage=87.0)
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
    
    fm.setAttributes(ParameterMutant.convert(ParameterMutantTrial.createParams()));
    
    
    FabricateMutant copy = new FabricateMutant(fm);
    ParameterMutantTrial.assertConvertedParams(copy.getAttributes(), this);
    
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
    fm.setArchiveStages(Collections.singletonList("aStage"), getRoutines("aStage", RoutineBriefOrigin.ARCHIVE_STAGE));
    fm.setCommands(getRoutines("command", RoutineBriefOrigin.COMMAND));
    fm.setFacets(getRoutines("facet", RoutineBriefOrigin.FACET));
    fm.setStages(Collections.singletonList("stage"), getRoutines("stage", RoutineBriefOrigin.STAGE));
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
    
    I_RoutineBrief aStage = assertRoutines(copy.getArchiveStages(), "aStage", RoutineBriefOrigin.ARCHIVE_STAGE);
    assertSame(aStage, copy.getArchiveStage("aStage"));
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
    
    ft.setAttributes(ParameterMutantTrial.createParams());
    
    I_FabricateXmlDiscovery fxml = mock(I_FabricateXmlDiscovery.class);
    
    RoutineParentType rtp = getXmlCommand();
    ft.getCommand().add(rtp);

    RoutineParentType facet = getXmlCommand();
    ft.getFacet().add(facet);
    
    StagesAndProjectsType spt = getXmlStagesAndProjectsType();
    ft.setProjectGroup(spt);

    RoutineParentType trait = getXmlTrait();
    ft.getTrait().add(trait);
    
    FabricateMutant fm = new FabricateMutant(ft, fxml);
    assertEquals(0, fm.getArchiveStageOrder().size());
    assertEquals(0, fm.getArchiveStages().size());
    ParameterMutantTrial.assertConvertedParams(fm.getAttributes(), this);
    assertEquals(2, fm.getAttributes().size());
    
    I_Parameter a = fm.getAttribute("a");
    assertNotNull(a);
    assertEquals(1, fm.getAttributes("a").size());
    assertEquals("1", fm.getAttributeValue("a"));
    assertEquals(1, fm.getAttributeValues("a").size());
    List<I_Parameter> attribs = fm.getAttributes("a", "1");
    assertContains(attribs, a);
    assertEquals(1, attribs.size());
    
    //the constructor shouldn't copy these values,
    // only the add methods
    assertEquals(0, fm.getCommands().size());
    assertEquals(0, fm.getFacets().size());
    
    assertEquals(3, fm.getThreads());
    I_JavaSettings js = fm.getJavaSettings();
    assertEquals(3, js.getThreads());
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    assertNull(fm.getFabricateDevXmlDir());
    assertNull(fm.getFabricateProjectRunDir());
    assertNull(fm.getFabricateXmlRunDir());
    
    //the constructor shouldn't copy these values,
    // only the add methods
    assertEquals(0, fm.getStageOrder().size());
    assertEquals(0, fm.getStages().size());
    assertEquals(0, fm.getTraits().size());
    
    //setup
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
    
    DependencyMutantTrial.assertDependencyConversion(this, fm.getDependencies(), null);
  }

  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddStageAndProjectsArchiveStageDuplicate() {
    FabricateMutant fm = new FabricateMutant();
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName("1");
    fm.addStage(rbm);
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("Duplicate stage name 1")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fm.addStage(rbm);
          }
        });
    
    RoutineBriefMutant rbm2 = new RoutineBriefMutant();
    rbm2.setName("2");
    rbm2.setOrigin(RoutineBriefOrigin.FABRICATE_ARCHIVE_STAGE);
    fm.addStage(new RoutineBrief(rbm2));
    
    List<String> order = fm.getStageOrder();
    assertEquals("1", order.get(0));
    assertEquals("2", order.get(1));
    assertEquals(2, order.size());
  
    assertSame(rbm, fm.getStage("1"));
    assertEquals(rbm2, fm.getStage("2"));
    assertNotSame(rbm2, fm.getStage("2"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddStageDuplicate() {
    FabricateMutant fm = new FabricateMutant();
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName("1");
    fm.addStage(rbm);
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("Duplicate stage name 1")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fm.addStage(rbm);
          }
        });
    
    RoutineBriefMutant rbm2 = new RoutineBriefMutant();
    rbm2.setName("2");
    rbm2.setOrigin(RoutineBriefOrigin.FABRICATE_STAGE);
    fm.addStage(new RoutineBrief(rbm2));
    
    List<String> order = fm.getStageOrder();
    assertEquals("1", order.get(0));
    assertEquals("2", order.get(1));
    assertEquals(2, order.size());
  
    assertSame(rbm, fm.getStage("1"));
    assertEquals(rbm2, fm.getStage("2"));
    assertNotSame(rbm2, fm.getStage("2"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddTrait() throws Exception {
    
    FabricateType ft = new FabricateType();
    RoutineParentType rtp = getXmlTrait();
    ft.getTrait().add(rtp);

    
    FabricateMutant fm = new FabricateMutant();
    fm.addTraits(null);
    assertEquals(0, fm.getTraits().size());
    
    fm.addTraits(Collections.emptyList());
    assertEquals(0, fm.getTraits().size());
    
    fm.addTraits(ft.getTrait());
    assertRoutinesFromXml(fm.getTraits(), "trait", RoutineBriefOrigin.FABRICATE_TRAIT);
    //check encapsulation
    fm.getTraits().clear();
    assertRoutinesFromXml(fm.getTraits(), "trait", RoutineBriefOrigin.FABRICATE_TRAIT);

  }
  
  @Test
  public void testMethodAddTraitDuplicate() throws Exception {
    
    FabricateType ft = new FabricateType();
    RoutineParentType rtp = getXmlTrait();
    ft.getTrait().add(rtp);

    
    FabricateMutant fm = new FabricateMutant();
    fm.addTraits(ft.getTrait());
    DuplicateRoutineException caught = null;
    try {
      fm.addTraits(ft.getTrait());
    } catch (DuplicateRoutineException x ) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("trait", caught.getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_TRAIT, caught.getOrigin());
    assertNull(caught.getMessage());
    assertNull(caught.getParentName());
    assertNull(caught.getParentOrigin());
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
    
    fm.setStages(Collections.singletonList("stage"), getRoutines("stage", RoutineBriefOrigin.STAGE));
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
  public void testMethodAddArchiveStageDuplicate() {
    FabricateMutant fm = new FabricateMutant();
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName("1");
    fm.addArchiveStage(rbm);
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("Duplicate archive stage name 1")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fm.addArchiveStage(rbm);
          }
        });
    
    RoutineBriefMutant rbm2 = new RoutineBriefMutant();
    rbm2.setName("2");
    rbm2.setOrigin(RoutineBriefOrigin.FABRICATE_ARCHIVE_STAGE);
    fm.addArchiveStage(new RoutineBrief(rbm2));
    
    List<String> order = fm.getArchiveStageOrder();
    assertEquals("1", order.get(0));
    assertEquals("2", order.get(1));
    assertEquals(2, order.size());
  
    assertSame(rbm, fm.getArchiveStage("1"));
    assertEquals(rbm2, fm.getArchiveStage("2"));
    assertNotSame(rbm2, fm.getArchiveStage("2"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddCommand() throws Exception {
    
    FabricateType ft = new FabricateType();
    RoutineParentType rtp = getXmlCommand();
    ft.getCommand().add(rtp);

    FabricateMutant fm = new FabricateMutant();
    fm.addCommands(null);
    assertEquals(0, fm.getCommands().size());
    
    fm.addCommands(Collections.emptyList());
    assertEquals(0, fm.getCommands().size());
    
    fm.addCommands(ft.getCommand());
    assertRoutinesFromXml(fm.getCommands(), "command", RoutineBriefOrigin.FABRICATE_COMMAND);
    //check encapsulation
    fm.getCommands().clear();
    assertRoutinesFromXml(fm.getCommands(), "command", RoutineBriefOrigin.FABRICATE_COMMAND);
  }
  
  @Test
  public void testMethodAddCommandDuplicate() throws Exception {
    
    FabricateType ft = new FabricateType();
    RoutineParentType rtp = getXmlCommand();
    ft.getCommand().add(rtp);

    
    FabricateMutant fm = new FabricateMutant();
    fm.addCommands(ft.getCommand());
    DuplicateRoutineException caught = null;
    try {
       fm.addCommands(ft.getCommand());
    } catch (DuplicateRoutineException x ) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("command", caught.getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_COMMAND, caught.getOrigin());
    assertNull(caught.getMessage());
    assertNull(caught.getParentName());
    assertNull(caught.getParentOrigin());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddFacet() throws Exception {
    
    FabricateType ft = new FabricateType();
    RoutineParentType rtp = getXmlFacet();
    ft.getCommand().add(rtp);

    RoutineParentType facet = getXmlFacet();
    ft.getFacet().add(facet);
    
    FabricateMutant fm = new FabricateMutant();
    fm.addFacets(null);
    assertEquals(0, fm.getFacets().size());
    
    fm.addFacets(Collections.emptyList());
    assertEquals(0, fm.getFacets().size());
    
    fm.addFacets(ft.getFacet());
    assertRoutinesFromXml(fm.getFacets(), "facet", RoutineBriefOrigin.FABRICATE_FACET);
    //check encapsulation
    fm.getFacets().clear();
    assertRoutinesFromXml(fm.getFacets(), "facet", RoutineBriefOrigin.FABRICATE_FACET);

  }
  
  @Test
  public void testMethodAddFacetDuplicate() throws Exception {
    
    FabricateType ft = new FabricateType();
    RoutineParentType rtp = getXmlFacet();
    ft.getFacet().add(rtp);

    
    FabricateMutant fm = new FabricateMutant();
    fm.addFacets(ft.getFacet());
    DuplicateRoutineException caught = null;
    try {
      fm.addFacets(ft.getFacet());
    } catch (DuplicateRoutineException x ) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("facet", caught.getName());
    assertEquals(RoutineBriefOrigin.FABRICATE_FACET, caught.getOrigin());
    assertNull(caught.getMessage());
    assertNull(caught.getParentName());
    assertNull(caught.getParentOrigin());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodAddStageAndProjectsXmlTypes() throws Exception {
    
    FabricateType ft = new FabricateType();

    StagesAndProjectsType spt = getXmlStagesAndProjectsType();
    ft.setProjectGroup(spt);
   
    FabricateMutant fm = new FabricateMutant();

    fm.addStagesAndProjects(ft);
    assertRoutinesFromXml(fm.getStages(), "stage", RoutineBriefOrigin.FABRICATE_STAGE);
    List<String> so = fm.getStageOrder();
    assertEquals("stage", so.get(0));
    assertEquals(1, so.size());
    //check encapsulation
    fm.getStages().clear();
    so.clear();
    assertRoutinesFromXml(fm.getStages(), "stage", RoutineBriefOrigin.FABRICATE_STAGE);
    so = fm.getStageOrder();
    assertEquals("stage", so.get(0));
    assertEquals(1, so.size());
    
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
    
    fm = new FabricateMutant();
    fm.addStagesAndProjects(ft);
    
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
  

  private Map<String, I_RoutineBrief> getRoutines(String name, RoutineBriefOrigin origin) {
    Map<String, I_RoutineBrief> toRet = new HashMap<String, I_RoutineBrief>();
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.setName(name);
    rbm.setOrigin(origin);
    toRet.put(name, rbm);
    return toRet;
    
  }

  private RoutineParentType getXmlTrait() {
    RoutineParentType rtp = new RoutineParentType();
    rtp.setName("trait");
    rtp.setClazz(EncryptTrait.class.getName());
    ParamsType params = new ParamsType();
    ParamType param = new ParamType();
    param.setKey("traitKey");
    param.setValue("traitValue");
    params.getParam().add(param);
    rtp.setParams(params);
    return rtp;
  }
  
  private StageType getXmlArchiveStage() {
    StageType aStage = new StageType();
    aStage.setName("aStage");
    aStage.setClazz(EncryptTrait.class.getName());
    ParamsType aStageParams = new ParamsType();
    ParamType aStageParam = new ParamType();
    aStageParam.setKey("aStageKey");
    aStageParam.setValue("aStageValue");
    aStageParams.getParam().add(aStageParam);
    aStage.setParams(aStageParams);
    return aStage;
  }
  
  private RoutineParentType getXmlCommand() {
    RoutineParentType rtp = new RoutineParentType();
    rtp.setName("command");
    rtp.setClazz(EncryptTrait.class.getName());
    ParamsType params = new ParamsType();
    ParamType param = new ParamType();
    param.setKey("commandKey");
    param.setValue("commandValue");
    params.getParam().add(param);
    rtp.setParams(params);
    return rtp;
  }

  private RoutineParentType getXmlFacet() {
    RoutineParentType rtp = new RoutineParentType();
    rtp.setName("facet");
    rtp.setClazz(EncryptTrait.class.getName());
    ParamsType params = new ParamsType();
    ParamType param = new ParamType();
    param.setKey("facetKey");
    param.setValue("facetValue");
    params.getParam().add(param);
    rtp.setParams(params);
    return rtp;
  }
  
  private StageType getXmlStage() {
    StageType stage = new StageType();
    stage.setName("stage");
    stage.setClazz(EncryptTrait.class.getName());
    ParamsType stageParams = new ParamsType();
    ParamType stageParam = new ParamType();
    stageParam.setKey("stageKey");
    stageParam.setValue("stageValue");
    stageParams.getParam().add(stageParam);
    stage.setParams(stageParams);
    return stage;
  }
  
  private StagesAndProjectsType getXmlStagesAndProjectsType() {
    StageType stage = getXmlStage();
    
    StageType aStage = getXmlArchiveStage();
    
    StagesAndProjectsType spt = new StagesAndProjectsType();
    StagesType st = new StagesType();
    st.getStage().add(stage);
    st.getArchiveStage().add(aStage);
    spt.setStages(st);
    return spt;
  }

}

package org.adligo.fabricate_tests.models.fabricate;

import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.models.common.I_Parameter;
import org.adligo.fabricate.models.common.I_RoutineBrief;
import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBrief;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.fabricate.Fabricate;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.fabricate.I_JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettingsMutant;
import org.adligo.fabricate.models.project.I_ProjectBrief;
import org.adligo.fabricate.models.project.ProjectBrief;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate_tests.models.common.ParameterMutantTrial;
import org.adligo.fabricate_tests.models.common.ParameterTrial;
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

@SourceFileScope (sourceClass=Fabricate.class, minCoverage=90.0)
public class FabricateTrial extends MockitoSourceFileTrial {

  
  @SuppressWarnings({"unused"})
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new Fabricate( null);
          }
        }); 
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("projectsDir")),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new Fabricate( new FabricateMutant());
          }
        }); 
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopiesFromInterface() {
    FabricateMutant fm = new FabricateMutant();
    fm.setAttributes(ParameterMutant.convert(ParameterMutantTrial.createParams()));
    fm.setJavaHome("jh");
    fm.setFabricateHome("fh");
    fm.setFabricateRepository("fr");
    fm.setFabricateXmlRunDir("fabXmlDir");
    fm.setProjectsDir("pjDir");
    
    Fabricate copy = new Fabricate(fm);
    ParameterTrial.assertConvertedParams(copy.getAttributes(), this);
    assertEquals(2, copy.getAttributes().size());
    
    I_Parameter a = fm.getAttribute("a");
    assertNotNull(a);
    assertEquals(1, copy.getAttributes("a").size());
    assertNotNull(copy.getAttributeValue("a"));
    assertEquals(1, copy.getAttributeValues("a").size());
    List<I_Parameter> attribs = fm.getAttributes("a", "1");
    assertContains(attribs, a);
    assertEquals(1, attribs.size());
    
    assertEquals(FabricateDefaults.JAVA_THREADS, copy.getThreads());
    assertEquals(FabricateDefaults.JAVA_XMS_DEFAULT, copy.getXms());
    assertEquals(FabricateDefaults.JAVA_XMX_DEFAULT, copy.getXmx());
    assertEquals("pjDir", copy.getProjectsDir());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", copy.getProjects().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getStageOrder().getClass().getName());
    assertNull(copy.getScm());
    
    JavaSettingsMutant jsm = new JavaSettingsMutant();
    jsm.setThreads(1);
    jsm.setXms("120m");
    jsm.setXmx("130m");
    fm.setJavaSettings(jsm);
    
    copy = new Fabricate(fm);
    assertEquals("fabXmlDir", copy.getFabricateXmlRunDir());
    assertNull(copy.getFabricateProjectRunDir());
    assertNull(copy.getFabricateDevXmlDir());
    assertEquals(0, copy.getRemoteRepositories().size());
    
    fm.addRemoteRepository("repoA");
    fm.addRemoteRepository("repoB");
    fm.addRemoteRepository("repoA");
    
    fm.setFabricateProjectRunDir("projectRunDir");
    fm.setFabricateDevXmlDir("devXmlDir");
    copy = new Fabricate(fm);
    assertEquals("fabXmlDir", copy.getFabricateXmlRunDir());
    assertEquals("projectRunDir", copy.getFabricateProjectRunDir());
    assertEquals("devXmlDir", copy.getFabricateDevXmlDir());
    
    List<String> repos = copy.getRemoteRepositories();
    assertEquals("repoA", repos.get(0));
    assertEquals("repoB", repos.get(1));
    assertEquals(2, repos.size());
    
    assertEquals("jh", copy.getJavaHome());
    assertEquals("fh", copy.getFabricateHome());
    assertEquals("fr", copy.getFabricateRepository());
    
    copy = new Fabricate(fm);
    assertEquals("jh", copy.getJavaHome());
    assertEquals("fh", copy.getFabricateHome());
    assertEquals("fr", copy.getFabricateRepository());
    assertEquals(1, copy.getThreads());
    assertSame(Collections.emptyMap(), copy.getCommands());
    assertSame(Collections.emptyMap(), copy.getStages());
    assertSame(Collections.emptyMap(), copy.getTraits());
    
    repos = copy.getRemoteRepositories();
    assertEquals("repoA", repos.get(0));
    assertEquals("repoB", repos.get(1));
    assertEquals(2, repos.size());
    
    I_JavaSettings js = copy.getJavaSettings();
    assertEquals(JavaSettings.class.getName(), js.getClass().getName());
    assertEquals("120m", js.getXms());
    assertEquals("130m", js.getXmx());
    
    FabricateMutant mock = mock(FabricateMutant.class);
    when(mock.getJavaSettings()).thenReturn(js);
    when(mock.getProjectsDir()).thenReturn("someDirWithProjects");
    copy = new Fabricate(mock);
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
    Dependency dB = new Dependency(dmB);
    dms.add(dB);
    dms.add(null);
    
    fm.setDependencies(dms);
    fm.setCommands(getRoutines("command", RoutineBriefOrigin.COMMAND));
    fm.setFacets(getRoutines("facet", RoutineBriefOrigin.FACET));
    fm.setStages(getRoutines("stage", RoutineBriefOrigin.STAGE));
    fm.setTraits(getRoutines("trait", RoutineBriefOrigin.TRAIT));
    
    copy = new Fabricate(fm);
    assertDependencies(copy); 
    I_RoutineBrief cmd = assertRoutines(copy.getCommands(), "command", RoutineBriefOrigin.COMMAND);
    assertSame(cmd, copy.getCommand("command"));
    I_RoutineBrief facet = assertRoutines(copy.getFacets(), "facet", RoutineBriefOrigin.FACET);
    assertSame(facet, copy.getFacet("facet"));
    I_RoutineBrief stage = assertRoutines(copy.getStages(), "stage", RoutineBriefOrigin.STAGE);
    assertSame(stage, copy.getStage("stage"));
    List<String> stageOrder = copy.getStageOrder();
    assertEquals("stage",stageOrder.get(0));
    assertEquals(1,stageOrder.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", stageOrder.getClass().getName());
    
    I_RoutineBrief trait = assertRoutines(copy.getTraits(), "trait", RoutineBriefOrigin.TRAIT);
    assertSame(trait, copy.getTrait("trait"));
    
    //hit the immutable pointer copy
    copy = new Fabricate(copy);
    assertDependencies(copy); 
    assertRoutines(copy.getCommands(), "command", RoutineBriefOrigin.COMMAND);
    assertRoutines(copy.getFacets(), "facet", RoutineBriefOrigin.FACET);
    assertRoutines(copy.getStages(), "stage", RoutineBriefOrigin.STAGE);
    assertRoutines(copy.getTraits(), "trait", RoutineBriefOrigin.TRAIT);
    
    RoutineBriefMutant scm = new RoutineBriefMutant();
    scm.setName("Git");
    scm.setOrigin(RoutineBriefOrigin.FABRICATE_SCM);
    fm.setScm(scm);
    
    ProjectType pA = new ProjectType();
    pA.setName("a.example.com");
    pA.setVersion("");
    ProjectBrief pbA = new ProjectBrief(pA);
    
    ProjectType pB = new ProjectType();
    pB.setName("b.example.com");
    pB.setVersion("1");
    ProjectBrief pbB = new ProjectBrief(pB);
    
    List<I_ProjectBrief> briefs = new ArrayList<I_ProjectBrief>();
    briefs.add(pbA);
    briefs.add(pbB);
    
    fm.setProjects(briefs);
    copy = new Fabricate(fm);
    
    I_RoutineBrief rb =  copy.getScm();
    assertNotNull(rb);
    assertEquals("Git", rb.getName());
    assertEquals(RoutineBrief.class.getName(), rb.getClass().getName());
    
    List<I_ProjectBrief> projs = copy.getProjects();
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
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", projs.getClass().getName());
  }

  private void assertDependencies(Fabricate copy) {
    I_Dependency dtCopy = copy.getDependencies().get(0);
    assertEquals(Dependency.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactA",dtCopy.getArtifact());
    assertFalse(dtCopy.isExtract());
    assertEquals("fileNameA" ,dtCopy.getFileName());
    assertEquals("groupA" ,dtCopy.getGroup());
    assertEquals("platformA" ,dtCopy.getPlatform());
    assertEquals("typeA" ,dtCopy.getType());
    assertEquals("versionA" ,dtCopy.getVersion()); 
    
    dtCopy = copy.getDependencies().get(1);
    assertEquals(Dependency.class.getName(), dtCopy.getClass().getName());
    assertEquals("artifactB",dtCopy.getArtifact());
    assertTrue(dtCopy.isExtract());
    assertEquals("fileNameB" ,dtCopy.getFileName());
    assertEquals("groupB" ,dtCopy.getGroup());
    assertEquals("platformB" ,dtCopy.getPlatform());
    assertEquals("typeB" ,dtCopy.getType());
    assertEquals("versionB" ,dtCopy.getVersion());
  }
  
  private I_RoutineBrief assertRoutines(Map<String,I_RoutineBrief> routines, String name, 
      RoutineBriefOrigin origin) {
    I_RoutineBrief route = routines.get(name);
    assertNotNull(route);
    
    assertEquals(name, route.getName());
    assertSame(origin, route.getOrigin());
    assertEquals(RoutineBrief.class.getName(), route.getClass().getName());
    assertEquals("java.util.Collections$UnmodifiableMap", routines.getClass().getName());
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

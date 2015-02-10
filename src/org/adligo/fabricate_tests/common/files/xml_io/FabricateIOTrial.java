package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.files.xml_io.FabricateIO;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.CommandType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.GitServerType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.OptionalRoutineType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ScmType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StageType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesAndProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.TraitType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryReferenceType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.UnmarshalException;

@SourceFileScope (sourceClass=FabricateIO.class, minCoverage=89.0)
public class FabricateIOTrial extends MockitoSourceFileTrial {


  @SuppressWarnings("boxing")
  @Test
  public void testMethod_parse_v1_0() throws Exception {
    FabricateType fab = new FabXmlFileIO().parseFabricate_v1_0("test_data/xml_io_trials/fabricate.xml");
    JavaType java =fab.getJava();
    assertEquals("256m", java.getXms());
    assertEquals("1g", java.getXmx());
    assertEquals(8, java.getThreads());
    
    FabricateDependencies deps = fab.getDependencies();
    List<String> remotes = deps.getRemoteRepository();
    assertContains(remotes,"http://127.0.0.1/");
    assertEquals(1, remotes.size());
    
    List<LibraryReferenceType> libs = deps.getLibrary();
    LibraryReferenceType lib = libs.get(0);
    assertNotNull(lib);
    assertEquals("lib_foo", lib.getValue());
    
    List<DependencyType> depList = deps.getDependency();
    DependencyType dep = depList.get(0);
    assertDependency(dep);
    assertEquals(1, depList.size());
    
    List<TraitType> traits =  fab.getTrait();
    TraitType trait = traits.get(0);
    assertEquals("prepare", trait.getName());
    assertEquals("com.example.DefaultPrepare", trait.getClazz());
    
    ParamsType params = trait.getParams();
    List<ParamType> paramsList = params.getParam();
    ParamType param = paramsList.get(0);
    assertEquals(1, paramsList.size());
    
    assertEquals("prepareParam", param.getKey());
    assertEquals("prepareParamValue", param.getValue());
    paramsList = param.getParam();
    param = paramsList.get(0);
    assertEquals("nestedPrepareParam", param.getKey());
    assertEquals("nestedPrepareParamValue", param.getValue());
    assertEquals(1, paramsList.size());
    paramsList = param.getParam();
    assertEquals(0, paramsList.size());
    
    assertEquals(1, traits.size());
    
    List<CommandType> commands = fab.getCommand();
    CommandType command = commands.get(0);
    assertNotNull(command);
    assertEquals("com.example.Classpath2Eclipse",command.getClazz());
    assertEquals("classpath2eclipse",command.getName());
    
    ParamsType cmdParams = command.getParams();
    paramsList = cmdParams.getParam();
    param = paramsList.get(0);
    assertNotNull(param);
    assertEquals("c2eKey",param.getKey());
    assertEquals("c2eVal",param.getValue());
    assertEquals(1, paramsList.size());
    
    paramsList = param.getParam();
    param = paramsList.get(0);
    assertNotNull(param);
    assertEquals("c2eKeyNested",param.getKey());
    assertEquals("c2eValNested",param.getValue());
    assertEquals(1, paramsList.size());
    
    assertEquals(1, commands.size());
    
    StagesAndProjectsType stagesAndProjs = fab.getProjectGroup();
    assertNotNull(stagesAndProjs);
    StagesType stages = stagesAndProjs.getStages();
    List<StageType> stageList = stages.getStage();
    StageType stage = stageList.get(0);
    assertNotNull(stage);
    assertEquals("build", stage.getName());
    assertEquals("com.example.ExampleBuild", stage.getClazz());
    assertTrue(stage.isOptional());
    
    ParamsType paramsType = stage.getParams();
    paramsList =  paramsType.getParam();
    param = paramsList.get(0);
    assertEquals("buildParam", param.getKey());
    assertEquals("buildParamValue", param.getValue());
    assertEquals(1, paramsList.size());
    
    paramsList = param.getParam();
    param = paramsList.get(0);
    assertEquals("nestedBuildParam", param.getKey()); 
    assertEquals("nestedBuildParamValue", param.getValue());
    assertEquals(1, paramsList.size());
    
    List<OptionalRoutineType> tasks = stage.getTask();
    OptionalRoutineType task = tasks.get(0);
    assertEquals("buildTask" ,task.getName());
    assertEquals("foo", task.getClazz());
    assertTrue(task.isOptional());
    
    ParamsType taskParams = task.getParams();
    paramsList = taskParams.getParam();
    param = paramsList.get(0);
    assertEquals("Default-Vendor", param.getKey());
    assertEquals("Adligo Inc", param.getValue());
    assertEquals(1, paramsList.size());
    
    paramsList = param.getParam();
    param = paramsList.get(0);
    assertEquals("nestKey", param.getKey()); 
    assertEquals("nestVal", param.getValue());
    assertEquals(1, paramsList.size());
    assertEquals(1, tasks.size());
    
    ProjectsType projects = stagesAndProjs.getProjects();
    ScmType scm = projects.getScm();
    GitServerType server = scm.getGit();
    assertEquals("git",server.getHostname());
    assertEquals("/opt/git/",server.getPath());
    assertEquals("JimDoe",server.getUser());
    
    List<ProjectType> projectsList = projects.getProject();
    ProjectType project = projectsList.get(0);
    assertEquals("widgets.example.com", project.getName());
    assertEquals("1", project.getVersion());
  }

  private void assertDependency(DependencyType dep) {
    assertNotNull(dep);
    assertEquals("cldc", dep.getArtifact());
    assertEquals("cldc_1.1.jar", dep.getFileName());
    assertEquals("com.sun.jme", dep.getGroup());
    assertEquals("JME", dep.getPlatform());
    assertEquals("jar", dep.getType());
    assertEquals("1.1", dep.getVersion());
    List<IdeType> ides = dep.getIde();
    IdeType ide = ides.get(0);
    assertNotNull(ide);
    assertEquals("eclipse", ide.getName());
    ParamsType args = ide.getArgs();
    List<ParamType> argList = args.getParam();
    ParamType arg = argList.get(0);
    assertEquals("ideKey", arg.getKey());
    assertEquals("ideVal", arg.getValue());
    argList = arg.getParam();
    arg = argList.get(0);
    assertEquals("nestedIdeKey", arg.getKey());
    assertEquals("nestedIdeVal", arg.getValue());
  }
  
  @Test
  public void testMethod_parse_v1_0Groups() throws Exception {
    FabricateType fab = new FabXmlFileIO().parseFabricate_v1_0(
        "test_data/xml_io_trials/fabricateGroups.xml");
    ProjectGroupsType groups = fab.getGroups();
    List<ProjectGroupType> pgts = groups.getProjectGroup();
    ProjectGroupType pgt = pgts.get(0);
    assertNotNull(pgt);
    ScmType scm = pgt.getScm();
    assertNotNull(scm);
    GitServerType gst = scm.getGit();
    assertEquals("git",gst.getHostname());
    assertEquals("/opt/git/",gst.getPath());
    assertEquals("JohnDoe",gst.getUser());
    assertEquals("https", gst.getProtocol());
    
    ProjectType proj = pgt.getProject();
    assertEquals("project_group.example.com",proj.getName());
    assertEquals("1",proj.getVersion());
    
    pgt = pgts.get(1);
    assertNotNull(pgt);
    scm = pgt.getScm();
    assertNotNull(scm);
    gst = scm.getGit();
    assertEquals("git",gst.getHostname());
    assertEquals("/opt/git/",gst.getPath());
    assertEquals("JaneDoe",gst.getUser());
    
    proj = pgt.getProject();
    assertEquals("other_project_group.example.com",proj.getName());
    assertEquals("2",proj.getVersion());
    
    
  }
  
  @Test
  public void testMethod_parse_v1_0_bad_content() {
    assertThrown(new ExpectedThrowable(IOException.class, MatchType.ANY), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseFabricate_v1_0("test_data/xml_io_trials/dev.xml");
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_bad_file() {
    String osName = System.getProperty("os.name");
    String badFile = "X:/foo.xml";
    if (osName.startsWith("Windows")) {
      badFile = "/dev/nul";
    } 
    final String badFileName = badFile;
    assertThrown(new ExpectedThrowable(IOException.class, MatchType.ANY,
        new ExpectedThrowable(UnmarshalException.class, MatchType.ANY,
        new ExpectedThrowable(FileNotFoundException.class, MatchType.ANY))), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseFabricate_v1_0(badFileName);
      }
    });
  }
  
}

package org.adligo.fabricate_tests.files.xml_io;

import org.adligo.fabricate.files.xml_io.FabXmlFiles;
import org.adligo.fabricate.files.xml_io.FabricateIO;
import org.adligo.fabricate.xml.io_v1.common_v1_0.CommandType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.TaskType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.GitServerType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ScmType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StageType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesAndProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeArgumentType;
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
    FabricateType fab = FabXmlFiles.INSTANCE.parseFabricate_v1_0("test_data/xml_io_trials/fabricate.xml");
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
    
    List<CommandType> commands = fab.getCommand();
    CommandType command = commands.get(0);
    assertNotNull(command);
    assertEquals("com.example.Classpath2Eclipse",command.getClazz());
    assertEquals("classpath2eclipse",command.getName());
    
    List<ParamType> params = command.getParam();
    ParamType param = params.get(0);
    assertNotNull(param);
    assertEquals("c2eKey",param.getKey());
    assertEquals("c2eVal",param.getValue());
    assertEquals(1, params.size());
    
    params = param.getParam();
    param = params.get(0);
    assertNotNull(param);
    assertEquals("c2eKeyNested",param.getKey());
    assertEquals("c2eValNested",param.getValue());
    assertEquals(1, params.size());
    
    assertEquals(1, commands.size());
    
    StagesAndProjectsType stagesAndProjs = fab.getProjectGroup();
    assertNotNull(stagesAndProjs);
    StagesType stages = stagesAndProjs.getStages();
    List<StageType> stageList = stages.getStage();
    StageType stage = stageList.get(0);
    assertNotNull(stage);
    assertEquals("setup", stage.getName());
    assertEquals("com.example.ExampleSetup", stage.getClazz());
    assertTrue(stage.isOptional());
    
    ParamsType paramsType = stage.getParams();
    params =  paramsType.getParam();
    param = params.get(0);
    assertEquals("setupParam", param.getKey());
    assertEquals("setupParamValue", param.getValue());
    assertEquals(1, params.size());
    
    params = param.getParam();
    param = params.get(0);
    assertEquals("nestedSetupParam", param.getKey()); 
    assertEquals("nestedSetupParamValue", param.getValue());
    assertEquals(1, params.size());
    
    List<TaskType> tasks = stage.getTask();
    TaskType task = tasks.get(0);
    assertEquals("setupTask" ,task.getName());
    
    params = task.getParam();
    param = params.get(0);
    assertEquals("Default-Vendor", param.getKey());
    assertEquals("Adligo Inc", param.getValue());
    assertEquals(1, params.size());
    
    params = param.getParam();
    param = params.get(0);
    assertEquals("nestKey", param.getKey()); 
    assertEquals("nestVal", param.getValue());
    assertEquals(1, params.size());
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
    List<IdeArgumentType> args = ide.getArg();
    IdeArgumentType arg = args.get(0);
    assertEquals("ideKey", arg.getKey());
    assertEquals("ideVal", arg.getValue());
    args = arg.getArg();
    arg = args.get(0);
    assertEquals("nestedIdeKey", arg.getKey());
    assertEquals("nestedIdeVal", arg.getValue());
  }
  
  @Test
  public void testMethod_parse_v1_0Groups() throws Exception {
    FabricateType fab = FabXmlFiles.INSTANCE.parseFabricate_v1_0("test_data/xml_io_trials/fabricateGroups.xml");
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
        FabXmlFiles.INSTANCE.parseFabricate_v1_0("test_data/xml_io_trials/dev.xml");
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
        FabXmlFiles.INSTANCE.parseFabricate_v1_0(badFileName);
      }
    });
  }
  
}

package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.files.xml_io.FabricateIO;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StageType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesAndProjectsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.StagesType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryReferenceType;
import org.adligo.tests4j.run.common.FileUtils;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
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
    
    ParamsType attribs = fab.getAttributes();
    List<ParamType> attribList = attribs.getParam();
    ParamType attrib = attribList.get(0);
    assertEquals("gitDefaultBranch", attrib.getKey());
    assertEquals("trunk", attrib.getValue());
    
    ParamType attrib2 = attribList.get(1);
    assertEquals("k2", attrib2.getKey());
    assertNull(attrib2.getValue());
    assertEquals(2, attribList.size());
    
    attribList = attrib.getParam();
    attrib = attribList.get(0);
    assertEquals("c2eKeyNestedGit", attrib.getKey());
    assertEquals("c2eValNestedGit", attrib.getValue());
    assertEquals(1, attribList.size());
    attribList = attrib.getParam();
    assertEquals(0, attribList.size());
    
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
    
    List<RoutineParentType> traits =  fab.getTrait();
    RoutineParentType trait = traits.get(0);
    assertEquals("prepare", trait.getName());
    assertEquals("com.example.DefaultPrepare", trait.getClazz());
    
    ParamsType paramsType = trait.getParams();
    List<ParamType> params = paramsType.getParam();
    ParamType param = params.get(0);
    assertEquals(1, params.size());
    
    assertEquals("prepareParam", param.getKey());
    assertEquals("prepareParamValue", param.getValue());
    params = param.getParam();
    param = params.get(0);
    assertEquals("nestedPrepareParam", param.getKey());
    assertEquals("nestedPrepareParamValue", param.getValue());
    assertEquals(1, params.size());
    params = param.getParam();
    assertEquals(0, params.size());
    
    assertEquals(1, traits.size());
    
    List<RoutineParentType> commands = fab.getCommand();
    RoutineParentType command = commands.get(0);
    assertNotNull(command);
    assertEquals("com.example.Classpath2Eclipse",command.getClazz());
    assertEquals("classpath2eclipse",command.getName());
    
    ParamsType cmdParams = command.getParams();
    params = cmdParams.getParam();
    param = params.get(0);
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
    assertEquals("build", stage.getName());
    assertEquals("com.example.ExampleBuild", stage.getClazz());
    assertTrue(stage.isOptional());
    
    paramsType = stage.getParams();
    params =  paramsType.getParam();
    param = params.get(0);
    assertEquals("buildParam", param.getKey());
    assertEquals("buildParamValue", param.getValue());
    assertEquals(1, params.size());
    
    params = param.getParam();
    param = params.get(0);
    assertEquals("nestedBuildParam", param.getKey()); 
    assertEquals("nestedBuildParamValue", param.getValue());
    assertEquals(1, params.size());
    
    List<RoutineType> tasks = stage.getTask();
    RoutineType task = tasks.get(0);
    assertEquals("buildTask" ,task.getName());
    assertEquals("foo", task.getClazz());
    
    ParamsType taskParams = task.getParams();
    params = taskParams.getParam();
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
    RoutineType scm = projects.getScm();
    assertNotNull(scm);
    assertEquals("Git", scm.getName());
    paramsType = scm.getParams();
    params =  paramsType.getParam();
    ParamType zero = params.get(0);
    assertEquals("hostname",zero.getKey());
    assertEquals("github.com",zero.getValue());
    assertEquals(0, zero.getParam().size());
    
    ParamType one = params.get(1);
    assertEquals("path",one.getKey());
    assertEquals("/opt/git/",one.getValue());
    assertEquals(0, one.getParam().size());
    
    ParamType two = params.get(2);
    assertEquals("user",two.getKey());
    assertEquals("JimDoe",two.getValue());
    
    List<ParamType> twoPs = two.getParam();
    ParamType twoZero = twoPs.get(0);
    assertEquals("nestKey",twoZero.getKey());
    assertEquals("nestVal",twoZero.getValue());
    assertEquals(0, twoZero.getParam().size());
    assertEquals(1, twoPs.size());
    
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
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethod_parse_v1_0Groups() throws Exception {
    FabricateType fab = new FabXmlFileIO().parseFabricate_v1_0(
        "test_data/xml_io_trials/fabricateGroups.xml");
    ProjectGroupsType groups = fab.getGroups();
    List<ProjectGroupType> pgts = groups.getProjectGroup();
    ProjectGroupType pgt = pgts.get(0);
    assertNotNull(pgt);
    RoutineType scm = pgt.getScm();
    assertNotNull(scm);
    assertEquals("Git", scm.getName());
    ParamsType paramsType = scm.getParams();
    List<ParamType> params =  paramsType.getParam();
    ParamType zero = params.get(0);
    assertEquals("hostname",zero.getKey());
    assertEquals("github.com",zero.getValue());
    assertEquals(0, zero.getParam().size());
    
    ParamType one = params.get(1);
    assertEquals("path",one.getKey());
    assertEquals("/opt/git/",one.getValue());
    assertEquals(0, one.getParam().size());
    
    ParamType two = params.get(2);
    assertEquals("user",two.getKey());
    assertEquals("JimDoe",two.getValue());
    
    List<ParamType> twoPs = two.getParam();
    ParamType twoZero = twoPs.get(0);
    assertEquals("nestKey",twoZero.getKey());
    assertEquals("nestVal",twoZero.getValue());
    assertEquals(0, twoZero.getParam().size());
    assertEquals(1, twoPs.size());
    
    ProjectType proj = pgt.getProject();
    assertEquals("project_group.example.com",proj.getName());
    assertEquals("1",proj.getVersion());
    
    pgt = pgts.get(1);
    assertNotNull(pgt);
    scm = pgt.getScm();
    assertNotNull(scm);
    assertEquals("Git", scm.getName());
    paramsType = scm.getParams();
    params =  paramsType.getParam();
    zero = params.get(0);
    assertEquals("hostname",zero.getKey());
    assertEquals("github.com",zero.getValue());
    assertEquals(0, zero.getParam().size());
    
    one = params.get(1);
    assertEquals("path",one.getKey());
    assertEquals("/opt/git/",one.getValue());
    assertEquals(0, one.getParam().size());
    
    two = params.get(2);
    assertEquals("user",two.getKey());
    assertEquals("JimDoe",two.getValue());
    
    twoPs = two.getParam();
    twoZero = twoPs.get(0);
    assertEquals("nestKey",twoZero.getKey());
    assertEquals("nestVal",twoZero.getValue());
    assertEquals(0, twoZero.getParam().size());
    assertEquals(1, twoPs.size());
    
    proj = pgt.getProject();
    assertEquals("other_project_group.example.com",proj.getName());
    assertEquals("2",proj.getVersion());
    
    
  }
  
  @Test
  public void testMethod_parse_v1_0_attribute_blank_key() {
    File file = new File("test_data/xml_io_trials/fabricate/fabricateAttributeEmptyKey.xml");
    assertThrown(new ExpectedThrowable(new IOException(
          "fabricateAttributeEmptyKey.xml; lineNumber: 8; columnNumber: 27; cvc-minLength-valid: " +
          "Value '' with length = '0' is not facet-valid with respect to " +
          "minLength '1' for type '#AnonType_keyparam_type'.]"), 
          MatchType.CONTAINS), 
          new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseFabricate_v1_0(file.getAbsolutePath());
      }
    });
  }
  
  
  @Test
  public void testMethod_parse_v1_0_attribute_no_key() {
    File file = new File("test_data/xml_io_trials/fabricate/fabricateAttributeNoKey.xml");
    assertThrown(new ExpectedThrowable(new IOException(
          "fabricateAttributeNoKey.xml; lineNumber: 7; columnNumber: 19; cvc-complex-type.4: " +
          "Attribute 'key' must appear on element 'cns:param'.]"), 
          MatchType.CONTAINS), 
          new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseFabricate_v1_0(file.getAbsolutePath());
      }
    });
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
  public void testMethod_parse_v1_0_bad_file_path() {
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
  
  @Test
  public void testMethod_parse_v1_0_bad_file_missing_trait_name() {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "malformed_fabricate" + File.separator;
    final FabXmlFileIO io = new FabXmlFileIO();
    String message = "fabricateMissingTraitName.xml; lineNumber: 25; columnNumber: 52; "
        + "cvc-complex-type.4: Attribute 'name' must appear on element 'fns:trait'.";
    assertThrown(new ExpectedThrowable(new IOException(message), MatchType.CONTAINS,
        new ExpectedThrowable(UnmarshalException.class, MatchType.ANY)), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        String file = fileName + "fabricateMissingTraitName.xml";
        io.parseFabricate_v1_0(file);
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_command_class() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "fabricate" + File.separator +
        "fabricateMissingCommandClass.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    FabricateType ft = io.parseFabricate_v1_0(fileName);
    List<RoutineParentType> commands = ft.getCommand();
    RoutineParentType command =  commands.get(0);
    assertNull(command.getClazz());
  }
  
  @Test
  public void testMethod_parse_v1_0_empty_remote_repository() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "malformed_fabricate" + File.separator +
        "fabricateEmptyRemoteRepository.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    String message = "fabricateEmptyRemoteRepository.xml; lineNumber: 7; columnNumber: 60; "
        + "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to "
        + "minLength '3' for type '#AnonType_remote_repositoryfabricate_dependencies'.";
    assertThrown(new ExpectedThrowable(new IOException(message), MatchType.CONTAINS,
        new ExpectedThrowable(UnmarshalException.class, MatchType.ANY)), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        io.parseFabricate_v1_0(fileName);
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_command_task_class() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "fabricate" + File.separator +
        "fabricateMissingCommandTaskClass.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    FabricateType ft = io.parseFabricate_v1_0(fileName);
    List<RoutineParentType> commands = ft.getCommand();
    RoutineParentType command =  commands.get(0);
    assertNull(command.getClazz());
    
    List<RoutineType> tasks = command.getTask();
    RoutineType task = tasks.get(0);
    assertNull(task.getClazz());
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_dependencies() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "malformed_fabricate" + File.separator +
        "fabricateMissingDependencies.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    String message = "fabricateMissingDependencies.xml; lineNumber: 7; columnNumber: 24; "
        + "cvc-complex-type.2.4.a: Invalid content was found starting with element "
        + "'fns:project_group'. One of '{"
        + "\"http://www.adligo.org/fabricate/xml/io_v1/fabricate_v1_0.xsd\":java, "
        + "\"http://www.adligo.org/fabricate/xml/io_v1/fabricate_v1_0.xsd\":logs, "
        + "\"http://www.adligo.org/fabricate/xml/io_v1/fabricate_v1_0.xsd\":attributes, "
        + "\"http://www.adligo.org/fabricate/xml/io_v1/fabricate_v1_0.xsd\":dependencies}' "
        + "is expected.";
    assertThrown(new ExpectedThrowable(new IOException(message), MatchType.CONTAINS,
        new ExpectedThrowable(UnmarshalException.class, MatchType.ANY)), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        io.parseFabricate_v1_0(fileName);
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_remote_repositories() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "malformed_fabricate" + File.separator +
        "fabricateMissingRemoteRepository.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    String message = "fabricateMissingRemoteRepository.xml; lineNumber: 7; columnNumber: 24; "
        + "cvc-complex-type.2.4.b: The content of element 'fns:dependencies' is not complete. "
        + "One of '{"
        + "\"http://www.adligo.org/fabricate/xml/io_v1/fabricate_v1_0.xsd\":remote_repository}' "
        + "is expected.";
    assertThrown(new ExpectedThrowable(new IOException(message), MatchType.CONTAINS,
        new ExpectedThrowable(UnmarshalException.class, MatchType.ANY)), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        io.parseFabricate_v1_0(fileName);
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_stage_class() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "fabricate" + File.separator +
        "fabricateMissingStageClass.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    FabricateType ft = io.parseFabricate_v1_0(fileName);
    StagesAndProjectsType stages = ft.getProjectGroup();
    StagesType stagesType = stages.getStages();
    List<StageType> list = stagesType.getStage();
    
    StageType stage =  list.get(0);
    assertNull(stage.getClazz());
    
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_trait_stage_class() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "fabricate" + File.separator +
        "fabricateMissingStageTaskClass.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    FabricateType ft = io.parseFabricate_v1_0(fileName);
    StagesAndProjectsType stages = ft.getProjectGroup();
    StagesType stagesType = stages.getStages();
    List<StageType> list = stagesType.getStage();
    
    StageType stage =  list.get(0);
    assertNull(stage.getClazz());
    
    List<RoutineType> tasks = stage.getTask();
    RoutineType task = tasks.get(0);
    assertNull(task.getClazz());
  }
  
  
  @Test
  public void testMethod_parse_v1_0_missing_trait_class() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "fabricate" + File.separator +
        "fabricateMissingTraitClass.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    FabricateType ft = io.parseFabricate_v1_0(fileName);
    List<RoutineParentType> traits = ft.getTrait();
    RoutineParentType trait =  traits.get(0);
    assertNull(trait.getClazz());
    
  }
  
  @Test
  public void testMethod_parse_v1_0_missing_trait_task_class() throws Exception {
    final String fileName = FileUtils.getRunDir() + "test_data" +
        File.separator + "xml_io_trials" + 
        File.separator + "fabricate" + File.separator +
        "fabricateMissingTraitTaskClass.xml";
    final FabXmlFileIO io = new FabXmlFileIO();
    FabricateType ft = io.parseFabricate_v1_0(fileName);
    List<RoutineParentType> traits = ft.getTrait();
    RoutineParentType trait =  traits.get(0);
    assertNull(trait.getClazz());
    
    List<RoutineType> tasks = trait.getTask();
    RoutineType task = tasks.get(0);
    assertNull(task.getClazz());
  }
}

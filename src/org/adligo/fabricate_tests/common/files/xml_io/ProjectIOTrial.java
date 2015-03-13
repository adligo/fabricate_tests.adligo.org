package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.files.xml_io.ProjectIO;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.FabricateProjectType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectDependenciesType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectRoutineType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectStagesType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.UnmarshalException;

@SourceFileScope (sourceClass=ProjectIO.class, minCoverage=90.0)
public class ProjectIOTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethod_parse_v1_0() throws Exception {
    FabricateProjectType project = new FabXmlFileIO().parseProject_v1_0("test_data/xml_io_trials/project.xml");
    assertNotNull(project);
    
    ParamsType attributes = project.getAttributes();
    List<ParamType> attributesList = attributes.getParam();
    ParamType attribute = attributesList.get(0);
    assertEquals("srcDirs",attribute.getKey());
    assertEquals("src,src2", attribute.getValue());
    assertEquals(1, attributesList.size());
    
    attributesList = attribute.getParam();
    attribute = attributesList.get(0);
    assertEquals("c2eKeyNested",attribute.getKey());
    assertEquals("c2eValNested", attribute.getValue());
    assertEquals(1, attributesList.size());

    List<RoutineParentType> traits = project.getTrait();
    RoutineParentType trait = traits.get(0);
    assertNotNull(trait);
    assertEquals("traitXyz", trait.getName());
    assertEquals("xyz", trait.getClazz());
    
    ParamsType traitParams = trait.getParams();
    List<ParamType> paramList = traitParams.getParam();
    ParamType param = paramList.get(0);
    assertEquals("c2eKey", param.getKey());
    assertEquals("c2eVal", param.getValue());
    assertEquals(1, paramList.size());
    
    paramList = param.getParam();
    param = paramList.get(0);
    assertEquals("c2eKeyNested", param.getKey());
    assertEquals("c2eValNested", param.getValue());
    assertEquals(1, paramList.size());
    assertEquals(1, traits.size());
    
    List<ProjectRoutineType> commands = project.getCommand();
    ProjectRoutineType command = commands.get(0);
    assertNotNull(command);
    assertEquals("classpath2eclipse",command.getName());
    
    ParamsType paramsType = command.getParams();
    
    List<ParamType> params = paramsType.getParam();
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
    
    ProjectStagesType stages = project.getStages();
    List<ProjectRoutineType> stageList = stages.getStage();
    ProjectRoutineType stage = stageList.get(0);
    
    assertNotNull(stage);
    assertEquals("setup", stage.getName());
    
    paramsType = stage.getParams();
    params = paramsType.getParam();
    param = params.get(0);
    assertEquals("setupParam", param.getKey());
    assertEquals("setupParamValue", param.getValue());
    assertEquals(1, params.size());
    
    params = param.getParam();
    param = params.get(0);
    assertEquals("nestedSetupParam", param.getKey()); 
    assertEquals("nestedSetupParamValue", param.getValue());
    assertEquals(1, params.size());
    
    List<RoutineType> tasks = stage.getTask();
    RoutineType task = tasks.get(0);
    assertEquals("setupTask" ,task.getName());
    
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
    assertEquals(1, stageList.size());
    
    ProjectDependenciesType deps = project.getDependencies();
    assertNotNull(deps);
    List<DependencyType> depsList =  deps.getDependency();
    DependencyType dep = depsList.get(0);
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
  public void testMethod_parse_v1_0_bad_content() {
    assertThrown(new ExpectedThrowable(IOException.class, MatchType.ANY), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseProject_v1_0("test_data/xml_io_trials/dev.xml");
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
        new FabXmlFileIO().parseProject_v1_0(badFileName);
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_class_on_command_not_allowed() {
    File file = new File("test_data/xml_io_trials/malformed_projects/projectClassOnCommandNotAllowed.xml");
    assertThrown(new ExpectedThrowable(new IOException(file.getAbsolutePath()), 
        new ExpectedThrowable(UnmarshalException.class, new ExpectedThrowable(new SAXParseException(
            "Attribute 'class' is not allowed to appear in element 'pns:command'.", mock(Locator.class)), MatchType.CONTAINS))), 
          new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseProject_v1_0(file.getAbsolutePath());
      }
    });
  }
  

  @Test
  public void testMethod_parse_v1_0_class_on_stage_not_allowed() {
    File file = new File("test_data/xml_io_trials/malformed_projects/projectClassOnStageNotAllowed.xml");
    assertThrown(new ExpectedThrowable(new IOException(file.getAbsolutePath()), 
        new ExpectedThrowable(UnmarshalException.class, new ExpectedThrowable(new SAXParseException(
            "Attribute 'class' is not allowed to appear in element 'pns:stage'.", mock(Locator.class)), MatchType.CONTAINS))), 
          new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabXmlFileIO().parseProject_v1_0(file.getAbsolutePath());
      }
    });
  }
}

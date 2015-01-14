package org.adligo.fabricate_tests.files.xml_io;

import org.adligo.fabricate.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.files.xml_io.ProjectIO;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.ParamsType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.TaskType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependenciesType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeArgumentType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.FabricateProjectType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectCommandType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectStageType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectStagesType;
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

@SourceFileScope (sourceClass=ProjectIO.class, minCoverage=90.0)
public class ProjectIOTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethod_parse_v1_0() throws Exception {
    FabricateProjectType project = FabXmlFileIO.INSTANCE.parseProject_v1_0("test_data/xml_io_trials/project.xml");
    assertNotNull(project);
    List<ProjectCommandType> commands = project.getCommand();
    ProjectCommandType command = commands.get(0);
    assertNotNull(command);
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
    
    ProjectStagesType stages = project.getStages();
    List<ProjectStageType> stageList = stages.getStage();
    ProjectStageType stage = stageList.get(0);
    
    assertNotNull(stage);
    assertEquals("setup", stage.getName());
    
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
    assertEquals(1, stageList.size());
    
    DependenciesType deps = project.getDependencies();
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
  public void testMethod_parse_v1_0_bad_content() {
    assertThrown(new ExpectedThrowable(IOException.class, MatchType.ANY), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFileIO.INSTANCE.parseProject_v1_0("test_data/xml_io_trials/dev.xml");
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
        FabXmlFileIO.INSTANCE.parseProject_v1_0(badFileName);
      }
    });
  }
  
}

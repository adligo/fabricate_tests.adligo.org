package org.adligo.fabricate_tests.files.xml_io;

import org.adligo.fabricate.files.xml_io.FabXmlFiles;
import org.adligo.fabricate.files.xml_io.FabricateIO;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.GitServerType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectGroupsType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ScmType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrownData;
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
    //deps.get
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
    assertThrown(new ExpectedThrownData(IOException.class, MatchType.ANY), new I_Thrower() {
      
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
    assertThrown(new ExpectedThrownData(IOException.class, MatchType.ANY,
        new ExpectedThrownData(UnmarshalException.class, MatchType.ANY,
        new ExpectedThrownData(FileNotFoundException.class, MatchType.ANY))), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFiles.INSTANCE.parseFabricate_v1_0(badFileName);
      }
    });
  }
  
}

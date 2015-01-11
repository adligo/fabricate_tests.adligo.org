package org.adligo.fabricate_tests.files.xml_io;

import org.adligo.fabricate.files.xml_io.DevIO;
import org.adligo.fabricate.files.xml_io.FabXmlFiles;
import org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrownData;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.UnmarshalException;

@SourceFileScope (sourceClass=DevIO.class, minCoverage=48.0)
public class DevIOTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethod_parse_v1_0() throws Exception {
    FabricateDevType dev = FabXmlFiles.INSTANCE.parseDev_v1_0("test_data/xml_io_trials/dev.xml");
    assertNotNull(dev);
    assertEquals("some_project_group.example.com", dev.getProjectGroup());
  }
  
  @Test
  public void testMethod_parse_v1_0_bad_content() {
    assertThrown(new ExpectedThrownData(IOException.class, MatchType.ANY), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFiles.INSTANCE.parseDev_v1_0("test_data/xml_io_trials/lib.xml");
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
        FabXmlFiles.INSTANCE.parseDev_v1_0(badFileName);
      }
    });
  }
  
  @Test
  public void testMethod_write_v1_0() {
    
  }
  
  @Test
  public void testMethod_write_v1_0_null_file_name() {
    
  }
  
  @Test
  public void testMethod_write_v1_0_bad_file() {
    
  }
}

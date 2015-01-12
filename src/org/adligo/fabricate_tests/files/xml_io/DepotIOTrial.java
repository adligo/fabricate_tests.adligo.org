package org.adligo.fabricate_tests.files.xml_io;

import org.adligo.fabricate.files.xml_io.DepotIO;
import org.adligo.fabricate.files.xml_io.FabXmlFiles;
import org.adligo.fabricate.xml.io_v1.depot_v1_0.ArtifactType;
import org.adligo.fabricate.xml.io_v1.depot_v1_0.DepotType;
import org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrownData;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.BeforeTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

@SourceFileScope (sourceClass=DepotIO.class, minCoverage=95.0)
public class DepotIOTrial extends MockitoSourceFileTrial {

  @BeforeTrial
  public static void beforeTrial(Map<String,Object> params) throws Exception {
    new File("test_data/xml_io_trials/depot_io_trial_temp").mkdirs();
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethod_parse_v1_0() throws Exception {
    DepotType depot = FabXmlFiles.INSTANCE.parseDepot_v1_0("test_data/xml_io_trials/depot.xml");
    assertNotNull(depot);
    List<ArtifactType> artifacts = depot.getArtifact();
    ArtifactType artifact = artifacts.get(0);
    assertNotNull(artifact);
    assertEquals("jars/hibernate_db_snapshot.jar", artifact.getFilename());
    assertEquals("hibernate_db.adligo.org", artifact.getProject());
    assertEquals("jar", artifact.getType());
    assertEquals(1, artifacts.size());
  }
  
  @Test
  public void testMethod_parse_v1_0_bad_content() {
    assertThrown(new ExpectedThrownData(IOException.class, MatchType.ANY), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFiles.INSTANCE.parseDepot_v1_0("test_data/xml_io_trials/lib.xml");
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
        FabXmlFiles.INSTANCE.parseDepot_v1_0(badFileName);
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethod_write_v1_0() throws Exception {
    DepotType depot = new DepotType();
    List<ArtifactType> artifacts = depot.getArtifact();
    ArtifactType art = new ArtifactType();
    art.setFilename("filename");
    art.setProject("project");
    art.setType("type");
    artifacts.add(art);
    FabXmlFiles.INSTANCE.writeDepot_v1_0("test_data/xml_io_trials/depot_io_trial_temp/depot.xml",depot);
    depot = FabXmlFiles.INSTANCE.parseDepot_v1_0("test_data/xml_io_trials/depot_io_trial_temp/depot.xml");
    assertNotNull(depot);
    artifacts = depot.getArtifact();
    art = artifacts.get(0);
    assertEquals("filename", art.getFilename());
    assertEquals("project", art.getProject());
    assertEquals("type", art.getType());
    assertEquals(1, artifacts.size());
  }
  
  @Test
  public void testMethod_write_v1_0_null_file_name() {
    final DepotType depot = new DepotType();
    assertThrown(new ExpectedThrownData(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFiles.INSTANCE.writeDepot_v1_0(null,depot);
      }
    });
    
  }
  
  @Test
  public void testMethod_write_v1_0_bad_file() {
    final DepotType depot = new DepotType();

    assertThrown(new ExpectedThrownData(IOException.class,
        new ExpectedThrownData(JAXBException.class)), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFiles.INSTANCE.writeDepot_v1_0("test_data/xml_io_trials/temp/na/dev.xml",depot);
      }
    });
  }
  
  @AfterTrial
  public static void afterTrial() throws Exception {
    Files.deleteIfExists(new File("test_data/xml_io_trials/depot_io_trial_temp/depot.xml").toPath());
    Files.deleteIfExists(new File("test_data/xml_io_trials/depot_io_trial_temp").toPath());
  }
}

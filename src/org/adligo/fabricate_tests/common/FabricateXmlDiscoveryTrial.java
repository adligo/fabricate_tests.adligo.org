package org.adligo.fabricate_tests.common;

import org.adligo.fabricate.common.FabricateXmlDiscovery;
import org.adligo.fabricate.files.I_FabFiles;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricateXmlDiscovery.class)
public class FabricateXmlDiscoveryTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorDirWithFabricateXml() {
    I_FabFiles files = mock(I_FabFiles.class);
    when(files.exists("fabricate.xml")).thenReturn(true);
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(files, false);
    assertEquals("fabricate.xml", disc.getFabricateXmlPath());
  }
  
  @Test
  public void testConstructorDirWithProjectXml() {
    
  }
  
  @Test
  public void testConstructorDirWithProjectAndDevXml() {
    
  }
  
  @Test
  public void testConstructorDirWithProjectAndDevXml_DevParseIOException() {
    
  }
}

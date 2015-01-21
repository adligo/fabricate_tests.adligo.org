package org.adligo.fabricate_tests;

import org.adligo.fabricate.FabricateSetup;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricateSetup.class, minCoverage=4.0)
public class FabricateSetupTrial extends MockitoSourceFileTrial {

  @Test
  public void test() {
    FabSystem sys = mock(FabSystem.class);
    I_FabFileIO mock = mock(I_FabFileIO.class);
    I_FabXmlFileIO xmlIoMock = mock(I_FabXmlFileIO.class);
    String [] nada = new String[]{};
    new FabricateSetup(nada,sys);
  }
}

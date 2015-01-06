package org.adligo.fabricate_tests;

import org.adligo.fabricate.FabricateSetup;
import org.adligo.fabricate.files.I_FabFiles;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricateSetup.class, minCoverage=4.0)
public class FabricateSetupTrial extends MockitoSourceFileTrial {

  @Test
  public void test() {
    I_FabFiles mock = mock(I_FabFiles.class);
    String [] nada = new String[]{};
    new FabricateSetup(nada, mock);
  }
}

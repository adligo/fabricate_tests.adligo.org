package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.ProjectEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricateEnConstants.class)
public class FabricateEnConstantsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    FabricateEnConstants constants = new FabricateEnConstants();
    assertEquals(ProjectEnMessages.class.getName(), constants.getProjectMessages().getClass().getName());
  }
}

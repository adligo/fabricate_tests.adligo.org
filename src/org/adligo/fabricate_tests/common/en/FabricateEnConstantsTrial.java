package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.en.GitEnMessages;
import org.adligo.fabricate.common.en.ProjectEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricateEnConstants.class)
public class FabricateEnConstantsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    FabricateEnConstants constants = FabricateEnConstants.INSTANCE;
    assertEquals(System.lineSeparator() ,constants.getLineSeperator());
    assertEquals(GitEnMessages.class.getName(), constants.getGitMessages().getClass().getName());
    assertEquals(FileEnMessages.class.getName(), constants.getFileMessages().getClass().getName());
    assertEquals(ProjectEnMessages.class.getName(), constants.getProjectMessages().getClass().getName());
    
    assertTrue(constants.isLeftToRight());
  }
}

package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricationMemoryConstants.class)
public class FabricationMemoryConstantsTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstants() {
    
    assertEquals("gitKeystorePassword", FabricationMemoryConstants.GIT_KEYSTORE_PASSWORD);
  }
}

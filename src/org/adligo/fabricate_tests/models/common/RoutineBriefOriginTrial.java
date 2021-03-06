package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=RoutineBriefOrigin.class, minCoverage=64.0)
public class RoutineBriefOriginTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testEnum() {
    assertEquals(39,  RoutineBriefOrigin.values().length);
  }
}

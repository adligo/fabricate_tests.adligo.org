package org.adligo.fabricate_tests.common.util;

import org.adligo.fabricate.common.util.StringUtils;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;


@SourceFileScope (sourceClass=StringUtils.class, minCoverage=100.0)
public class StringUtilsTrial extends MockitoSourceFileTrial {
	
  @Test
  public void testMethodIsEmpty() {
    
    assertTrue(StringUtils.isEmpty(null));
    assertTrue(StringUtils.isEmpty(""));
    assertTrue(StringUtils.isEmpty(" "));
    assertTrue(StringUtils.isEmpty("\t"));
    assertTrue(StringUtils.isEmpty("\n"));
    assertTrue(StringUtils.isEmpty("\r"));
    assertFalse(StringUtils.isEmpty("a"));
  }
}

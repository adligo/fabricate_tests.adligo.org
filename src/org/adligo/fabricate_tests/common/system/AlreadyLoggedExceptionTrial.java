package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.AlreadyLoggedException;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=AlreadyLoggedException.class)
public class AlreadyLoggedExceptionTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructor() {
    IllegalArgumentException iae = new IllegalArgumentException("failure");
    AlreadyLoggedException ale = new AlreadyLoggedException(iae);
    assertEquals(iae, ale.getCause());
  }
  
}

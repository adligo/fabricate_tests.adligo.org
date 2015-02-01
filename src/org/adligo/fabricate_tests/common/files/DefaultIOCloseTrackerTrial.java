package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.files.DefaultIOCloseTracker;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.IOException;

@SourceFileScope (sourceClass=DefaultIOCloseTracker.class)
public class DefaultIOCloseTrackerTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethodOnException() {
    //this method is intentionally a do nothing stub
    DefaultIOCloseTracker.INSTANCE.onCloseException(new IOException("x"));
  }
}

package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.files.I_IOCloseTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOCloseTrackerStub implements I_IOCloseTracker {
  private List<IOException> exes = new ArrayList<IOException>();

  @Override
  public void onCloseException(IOException x) {
    exes.add(x);
  }
  
  public int size() {
    return exes.size();
  }
  
  public IOException get(int i) {
    return exes.get(i);
  }
}

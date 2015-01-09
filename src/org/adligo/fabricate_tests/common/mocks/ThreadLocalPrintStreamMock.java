package org.adligo.fabricate_tests.common.mocks;

import org.adligo.fabricate.common.ThreadLocalPrintStream;

import java.io.PrintStream;

public class ThreadLocalPrintStreamMock extends ThreadLocalPrintStream {

  public static void set(PrintStream threadPrintStream) {
    setProtected(threadPrintStream);
  }
  
  public static PrintStream get() {
    return getProtected();
  }
  /**
   * for tests only
   */
  public static void revert() {
    revertProtected();
  }
}

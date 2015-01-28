package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.log.ThreadLocalPrintStream;

import java.io.PrintStream;

public class ThreadLocalPrintStreamMock extends ThreadLocalPrintStream {

  /**
   * @param threadPrintStream Note for some reason this doesn't work with a 
   * Mockito mock, so use a real PrintStream perhaps wrapping a ByteArrayOuptutStream.
   */
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
  
  public static void printlnP(String p) {
    ThreadLocalPrintStream.println(p);
  }
  
  public static void printTraceP(Throwable t) {
    ThreadLocalPrintStream.printTrace(t);
  }
}

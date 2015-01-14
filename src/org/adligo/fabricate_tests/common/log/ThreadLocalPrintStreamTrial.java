package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.log.ThreadLocalPrintStream;
import org.adligo.fabricate_tests.common.mocks.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SourceFileScope (sourceClass=ThreadLocalPrintStream.class, minCoverage=95.0)
public class ThreadLocalPrintStreamTrial extends MockitoSourceFileTrial {
  
  public void afterTests() {
    ThreadLocalPrintStreamMock.revert();
  }
  
  @Test
  public void testMethodsOnPrintStream() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    
    ThreadLocalPrintStreamMock.set(printStream);
    ThreadLocalPrintStream.println("hey");
    String result = new String( baos.toByteArray());
    assertEquals("hey", result.substring(0, 3));
    baos.reset();
    
    Exception x = new Exception("hmm");
    StackTraceElement e = new StackTraceElement("hmm", "method", "file", 1);
    x.setStackTrace(new StackTraceElement[] {e});
    ThreadLocalPrintStream.printTrace(x);
    result = new String( baos.toByteArray());
    assertEquals("java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator(), result.substring(0, result.length()));
    
    Exception x1 = new Exception("hmm1");
    StackTraceElement e1 = new StackTraceElement("hmm1", "method1", "file1", 2);
    x1.setStackTrace(new StackTraceElement[] {e1});
    x1.initCause(x);
    baos.reset();
    ThreadLocalPrintStream.printTrace(x1);
    result = new String( baos.toByteArray());
    
    assertEquals("java.lang.Exception: hmm1" + System.lineSeparator() +
       "\tat hmm1.method1(file1:2)" + System.lineSeparator() +
       "Caused by: java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator() +
       "java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator(), result.substring(0, result.length()));
    
    ThreadLocalPrintStreamMock.set(null);
    ThreadLocalPrintStreamMock.get();
  }
}

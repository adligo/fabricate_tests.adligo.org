package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.log.ThreadLocalPrintStream;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

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
    ThreadLocalPrintStreamMock.printlnP("hey");
    String result = new String( baos.toByteArray());
    assertEquals("hey", result.substring(0, 3));
    baos.reset();
    
    Exception x = new Exception("hmm");
    StackTraceElement e = new StackTraceElement("hmm", "method", "file", 1);
    x.setStackTrace(new StackTraceElement[] {e});
    ThreadLocalPrintStreamMock.printTraceP(x);
    result = new String( baos.toByteArray());
    assertEquals("java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator(), result.substring(0, result.length()));
    
    Exception x1 = new Exception("hmm1");
    StackTraceElement e1 = new StackTraceElement("hmm1", "method1", "file1", 2);
    x1.setStackTrace(new StackTraceElement[] {e1});
    x1.initCause(x);
    baos.reset();
    ThreadLocalPrintStreamMock.printTraceP(x1);
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
  
  @Test
  public void testMethodsOnPrintStreamToFile() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    
    ThreadLocalFabFileLog fileLog = new ThreadLocalFabFileLog();
    
    FabSystem sys = new FabSystem();
    sys.setLogFile(fileLog);
    ThreadLocalPrintStreamMock.set(printStream);
    ThreadLocalPrintStreamMock.printlnP("hey");
    String result = new String( baos.toByteArray());
    assertEquals("hey" + System.lineSeparator(), result);
    List<String> lines = fileLog.getLines();
    String fileResult = lines.get(0);
    assertEquals("hey", fileResult);
    baos.reset();
    lines.clear();
    
    Exception x = new Exception("hmm");
    StackTraceElement e = new StackTraceElement("hmm", "method", "file", 1);
    x.setStackTrace(new StackTraceElement[] {e});
    ThreadLocalPrintStreamMock.printTraceP(x);
    result = new String( baos.toByteArray());
    assertEquals("java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator(), result);
    
    List<Throwable> throwables = fileLog.getThrowables();
    assertSame(x, throwables.get(0));
    throwables.clear();
    
    Exception x1 = new Exception("hmm1");
    StackTraceElement e1 = new StackTraceElement("hmm1", "method1", "file1", 2);
    x1.setStackTrace(new StackTraceElement[] {e1});
    x1.initCause(x);
    baos.reset();
    ThreadLocalPrintStreamMock.printTraceP(x1);
    result = new String( baos.toByteArray());
    
    assertEquals("java.lang.Exception: hmm1" + System.lineSeparator() +
       "\tat hmm1.method1(file1:2)" + System.lineSeparator() +
       "Caused by: java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator() +
       "java.lang.Exception: hmm" + System.lineSeparator() +
       "\tat hmm.method(file:1)" + System.lineSeparator(), result);
    
    throwables = fileLog.getThrowables();
    assertSame(x1, throwables.get(0));
    throwables.clear();
    
    sys.setLogFile(null);
    
    ThreadLocalPrintStreamMock.set(null);
    ThreadLocalPrintStreamMock.get();
  }
}

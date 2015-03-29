package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.log.FabLog;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SourceFileScope (sourceClass=FabLog.class, minCoverage=66.0)
public class FabLogTrial extends MockitoSourceFileTrial {
  private ByteArrayOutputStream baos_;
  @Override
  public void beforeTests() {
    baos_ = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos_);
    
    ThreadLocalPrintStreamMock.set(printStream);
  }
  
  @AfterTrial
  public static void afterTrial() {
    ThreadLocalPrintStreamMock.revert();
  }

  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorLogSettingsAndConstants() {
    FabLog log = new FabLog(null, false);
    assertFalse(log.isLogEnabled(FabLogTrial.class));
    
    log = new FabLog(Collections.singletonMap(
        FabLogTrial.class.getName(), true), false);
    assertTrue(log.isLogEnabled(FabLogTrial.class));
  }
  
  @Test
  public void testMethodOrderLine() {
    String message = FabLog.orderLine(true, "a", " " , "b");
    assertEquals("a b", message);
    message = FabLog.orderLine(true, "b", " " , "a");
    assertEquals("b a", message);
  }
  
  @Test
  public void testMethodPrintLn() {
    FabLog log = new FabLog(null, false);
    log.println("line");
    assertEquals("line\n", baos_.toString());
  }
  
  @Test
  public void testMethodPrintTrace() {
    FabLog log = new FabLog(null, false);
    Exception e = new Exception("exc");
    e.setStackTrace(new StackTraceElement[] {});
    
    log.printTrace(e);
    assertEquals("java.lang.Exception: exc\n", baos_.toString());
    baos_.reset();
    
    Exception x = new Exception("xtc");
    x.setStackTrace(new StackTraceElement[] {});
    x.initCause(e);
    
    log.printTrace(x);
    assertEquals("java.lang.Exception: xtc\n" +
        "Caused by: java.lang.Exception: exc\n" + 
        "java.lang.Exception: exc\n", baos_.toString());
    
  }
  
  @Test
  public void testMethodPrintTraceFabLogOn() {
    Map<String,Boolean> logsOn = new HashMap<String,Boolean>();
    logsOn.put(FabLog.class.getName(), true);
    FabLog log = new FabLog(logsOn, false);
    Exception e = new Exception("exc");
    e.setStackTrace(new StackTraceElement[] {});
    
    log.printTrace(e);
    
    String result = baos_.toString();
    assertTrue(result.indexOf("java.lang.RuntimeException: logging trace from;") == 0);
    assertTrue(result.indexOf("\tat org.adligo.fabricate.common.log.FabLog.printTrace(FabLog.java:92)") >= 1);
    assertTrue(result.indexOf("java.lang.Exception: exc") >= 2);
    
  }
}

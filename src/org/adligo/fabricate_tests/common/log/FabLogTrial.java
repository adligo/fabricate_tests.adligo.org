package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.log.FabLog;
import org.adligo.fabricate.common.log.ThreadLocalPrintStream;
import org.adligo.fabricate_tests.common.mocks.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

@SourceFileScope (sourceClass=ThreadLocalPrintStream.class, minCoverage=78.0)
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
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        new FabLog(null, null);
      }
    });
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorLogSettingsAndConstants() {
    FabLog log = new FabLog(FabricateEnConstants.INSTANCE, null);
    assertFalse(log.isLogEnabled(FabLogTrial.class));
    assertSame(FabricateEnConstants.INSTANCE, log.getConstants());
    
    log = new FabLog(FabricateEnConstants.INSTANCE, Collections.singletonMap(
        FabLogTrial.class.getName(), true));
    assertTrue(log.isLogEnabled(FabLogTrial.class));
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testMethodOrderLine() {
    String message = FabLog.orderLine(true, "a", " " , "b");
    assertEquals("a b", message);
    message = FabLog.orderLine(true, "b", " " , "a");
    assertEquals("b a", message);
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testMethodPrintLn() {
    FabLog log = new FabLog(FabricateEnConstants.INSTANCE, null);
    log.println("line");
    assertEquals("line\n", baos_.toString());
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testMethodPrintTrace() {
    FabLog log = new FabLog(FabricateEnConstants.INSTANCE, null);
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
}

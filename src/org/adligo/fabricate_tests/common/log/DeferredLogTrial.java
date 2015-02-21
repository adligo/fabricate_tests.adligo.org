package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.log.DeferredLog;
import org.adligo.fabricate.common.log.FabLog;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

@SourceFileScope (sourceClass=DeferredLog.class, minCoverage=78.0)
public class DeferredLogTrial extends MockitoSourceFileTrial {
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

  
  @Test
  public void testConstructorLogSettingsAndConstants() {
    
    DeferredLog log = new DeferredLog();
    assertNull(log.getDelegate());
   
    assertFalse(log.isLogEnabled(DeferredLog.class));
    assertFalse(log.hasAllLogsEnabled());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsLogSettings() {
    I_FabLog del = mock(I_FabLog.class);
    
    DeferredLog log = new DeferredLog();
    assertNull(log.getDelegate());
    log.setDelegate(del);
    
    when(del.isLogEnabled(I_FabLog.class)).thenReturn(true);
    assertTrue(log.isLogEnabled(I_FabLog.class));
    
    when(del.hasAllLogsEnabled()).thenReturn(true);
    assertTrue(log.hasAllLogsEnabled());
    
  }
  
  @Test
  public void testMethodsPrints() {
    I_FabLog del = mock(I_FabLog.class);
    
    DeferredLog log = new DeferredLog();
    assertNull(log.getDelegate());
    log.println("line");
    assertEquals("line" + System.lineSeparator(), baos_.toString());
    
    baos_.reset();
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
    
    log.setDelegate(del);
    assertTrue(log.getDelegate().getClass().getName().contains(
        "org.adligo.fabricate.common.log.I_FabLog"));
    
    MockMethod<Void> println = new MockMethod<Void>();
    doAnswer(println).when(del).println(any());
    
    MockMethod<Void> printTrace = new MockMethod<Void>();
    doAnswer(printTrace).when(del).printTrace(any());
    
    log.println("line");
    assertEquals("line", println.getArg(0));
    
    IllegalArgumentException iae = new IllegalArgumentException("xyz");
    log.printTrace(iae);
    assertSame(iae, printTrace.getArg(0));
  }
  
  @Test
  public void testMethodsSetDelegateBlocked() {
    I_FabLog del = mock(I_FabLog.class);
    
    DeferredLog log = new DeferredLog();
    assertNull(log.getDelegate());
    log.setDelegate(del);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "class org.adligo.fabricate.common.log.DeferredLog.setDelegate" + System.lineSeparator() +
        "[org.adligo.fabricate.common.system.FabSystem]")), 
        new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        log.setDelegate(del);
      }
    });
  }
}

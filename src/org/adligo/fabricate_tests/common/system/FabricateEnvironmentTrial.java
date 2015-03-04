package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.common.system.FabricateEnvironment;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FabricateEnvironment.class,minCoverage=100.0)
public class FabricateEnvironmentTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  MockMethod<Void> printlnMethod_ = new MockMethod<Void>();
  MockMethod<Void> printtraceMethod_ = new MockMethod<Void>();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    when(sysMock_.lineSeparator()).thenReturn(System.lineSeparator());
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    
    printlnMethod_ = new MockMethod<Void>();
    printtraceMethod_ = new MockMethod<Void>();
    
    doAnswer(printlnMethod_).when(logMock_).println(any());
    doAnswer(printtraceMethod_).when(logMock_).printTrace(any());
  }
  @SuppressWarnings("boxing")
  @Test
  public void testConstants() {
     
    assertEquals("FABRICATE_HOME", FabricateEnvironment.FABRICATE_HOME);
    assertEquals("FABRICATE_REPOSITORY", FabricateEnvironment.FABRICATE_REPOSITORY);
    assertEquals("JAVA_HOME", FabricateEnvironment.JAVA_HOME);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetJavaHome() {

     when(sysMock_.getenv(FabricateEnvironment.JAVA_HOME)).thenReturn("jh");
     
     assertEquals("jh", FabricateEnvironment.INSTANCE.getJavaHome(sysMock_));
     
     assertEquals(0, printlnMethod_.count());
     assertEquals(0, printtraceMethod_.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetJavaHomeEmptyValue() {
    when(sysMock_.getenv(FabricateEnvironment.JAVA_HOME)).thenReturn(null);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(), MatchType.NULL),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            FabricateEnvironment.INSTANCE.getJavaHome(sysMock_);
          }
        });
    assertEquals("Exception: No $JAVA_HOME environment variable set.", 
        printlnMethod_.getArg(0));
    assertEquals("LASTLINE END", 
        printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
    
    beforeTests();
    when(sysMock_.getenv(FabricateEnvironment.JAVA_HOME)).thenReturn("");
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(), MatchType.NULL),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            FabricateEnvironment.INSTANCE.getJavaHome(sysMock_);
          }
        });
    assertEquals("Exception: No $JAVA_HOME environment variable set.", 
        printlnMethod_.getArg(0));
    assertEquals("LASTLINE END", 
        printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetFabricateHome() {
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn("fh");
    
    assertEquals("fh", FabricateEnvironment.INSTANCE.getFabricateHome(sysMock_));
    
    assertEquals(0, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetFabricateHomeEmptyValue() {
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn(null);
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(), MatchType.NULL),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            FabricateEnvironment.INSTANCE.getFabricateHome(sysMock_);
          }
        });
    assertEquals("Exception: No $FABRICATE_HOME environment variable set.", 
        printlnMethod_.getArg(0));
    assertEquals("LASTLINE END", 
        printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
    
    beforeTests();
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_HOME)).thenReturn("");
    
    assertThrown(new ExpectedThrowable(new IllegalStateException(), MatchType.NULL),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            FabricateEnvironment.INSTANCE.getFabricateHome(sysMock_);
          }
        });
    assertEquals("Exception: No $FABRICATE_HOME environment variable set.", 
        printlnMethod_.getArg(0));
    assertEquals("LASTLINE END", 
        printlnMethod_.getArg(1));
    assertEquals(2, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetFabricateRepository() {
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_REPOSITORY)).thenReturn("fr");
    
    assertEquals("fr", FabricateEnvironment.INSTANCE.getFabricateRepository(sysMock_));
    
    assertEquals(0, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetFabricateHomeDefaultValue() {
    when(sysMock_.getenv(FabricateEnvironment.FABRICATE_REPOSITORY)).thenReturn(null);
    
    assertEquals(FabricateDefaults.LOCAL_REPOSITORY, 
        FabricateEnvironment.INSTANCE.getFabricateRepository(sysMock_));
    
    assertEquals(0, printlnMethod_.count());
    assertEquals(0, printtraceMethod_.count());
  }
}

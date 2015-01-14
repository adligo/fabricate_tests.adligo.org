package org.adligo.fabricate_tests.files;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.files.I_FileMatcher;
import org.adligo.fabricate.files.PatternFileMatcher;
import org.adligo.fabricate_tests.common.mocks.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

@SourceFileScope (sourceClass=I_FileMatcher.class)
public class PatternFileMatcherTrial extends MockitoSourceFileTrial {
  private I_FabLog logMock;
  private MockMethod<Void> printlnMock = new MockMethod<Void>();
  
  private I_FabricateConstants constantsMock;
  
  @Override
  public void beforeTests() {
    logMock = mock(I_FabLog.class);
    doAnswer(printlnMock).when(logMock).println(any());
    
    constantsMock = mock(I_FabricateConstants.class);
    when(constantsMock.getFileMessages()).thenReturn(FileEnMessages.INSTANCE);
    
    when(constantsMock.getLineSeperator()).thenReturn("\n");
    when(logMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    
  }
  
  @AfterTrial
  public static void afterTrial() {
    ThreadLocalPrintStreamMock.revert();
  }
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getTheWildCardCharacterIsNotAllowedInMiddle())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(logMock, "b*ar", false);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getTheWildCardCharacterIsNotAllowedInMiddle())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(logMock, "foo/b*ar", false);
          }
        });
  }
  
  @Test
  public void testConstructor() {
    PatternFileMatcher matcher = new PatternFileMatcher(logMock, "", false);
    assertTrue(matcher.isMatch("bar.txt"));
    
  }
  
  @Test
  public void testConstructorWithIncludes() {
    PatternFileMatcher matcher = new PatternFileMatcher(logMock, "", true);
    assertFalse(matcher.isMatch("bar.txt"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorLogMatch() {
    when(logMock.isLogEnabled(PatternFileMatcher.class)).thenReturn(true);
    
    PatternFileMatcher matcher = new PatternFileMatcher(logMock, "", true);
    assertFalse(matcher.isMatch("bar.txt"));
    File file = new File(".");
    String absPath = file.getAbsolutePath();
    int idx = absPath.lastIndexOf(".");
    if (idx == absPath.length() -1 ) {
      absPath = absPath.substring(0, absPath.length() -1);
    }
    assertEquals(FileEnMessages.INSTANCE.getTheFollowingFile() + "\n" +
        absPath + "bar.txt\n" + 
        FileEnMessages.INSTANCE.getDidNotMatchedTheFollowingPattren() + "\n"
        , printlnMock.getArg(0));
  }
  
  @Test
  public void testConstructorLogNoMatch() {
    PatternFileMatcher matcher = new PatternFileMatcher(logMock, "", true);
    assertFalse(matcher.isMatch("bar.txt"));
  }
}

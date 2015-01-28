package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.PatternFileMatcher;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate_tests.common.log.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;

@SourceFileScope (sourceClass=PatternFileMatcher.class,minCoverage=87.0)
public class PatternFileMatcherTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabFileIO filesMock;
  private I_FabLog log_;
  private MockMethod<Void> printlnMock;
  private MockMethod<File> instanceMethod;
  
  private I_FabricateConstants constantsMock;
  
  @Override
  public void beforeTests() {
    printlnMock = new MockMethod<Void>();
    sysMock_ = mock(I_FabSystem.class);
    log_ = mock(I_FabLog.class);
    doAnswer(printlnMock).when(log_).println(any());
    when(sysMock_.getLog()).thenReturn(log_);
    
    filesMock = mock(I_FabFileIO.class);
    setupPaths("/");
    
    when(filesMock.instance(any())).then(instanceMethod);
    
    constantsMock = mock(I_FabricateConstants.class);
    when(constantsMock.getFileMessages()).thenReturn(FileEnMessages.INSTANCE);
    
    when(constantsMock.getLineSeperator()).thenReturn("\n");
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    
  }

  public void setupPaths(String nameSeparator) {
    when(filesMock.getNameSeparator()).thenReturn(nameSeparator);
    instanceMethod = new MockMethod<File>(new I_ReturnFactory<File>() {

      @Override
      public File create(Object[] keys) {
        //making nameSeparator final didn't help
        String nameSep = filesMock.getNameSeparator();
        File fileMock = mock(File.class);
        String absolutePath = nameSep + "somewhere" + nameSep + keys[0];
        int lastSlash = absolutePath.lastIndexOf(nameSep);
        when(fileMock.getAbsolutePath()).thenReturn(absolutePath);
        String fileName = absolutePath.substring(lastSlash + 1, absolutePath.length());
        when(fileMock.getName()).thenReturn(fileName);
        return fileMock;
      }
    });
  }
  
  @AfterTrial
  public static void afterTrial() {
    ThreadLocalPrintStreamMock.revert();
  }
  @Test
  public void testConstructorExceptions() {

    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getFileMatchingPatternsMayNotBeEmpty())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(filesMock, sysMock_, null, false);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getFileMatchingPatternsMayNotBeEmpty())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(filesMock, sysMock_, "\t", false);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getTheWildCardCharacterIsNotAllowedInMiddleFileName())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(filesMock, sysMock_, "b*ar.txt", false);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getTheWildCardCharacterIsNotAllowedInMiddleFileName())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(filesMock, sysMock_, "foo/b*ar.txt", false);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        FileEnMessages.INSTANCE.getTheWildCardCharacterIsNotAllowedAtTheLeftOrMiddleDirectoryPath())), new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new PatternFileMatcher(filesMock, sysMock_, "foo/*/bar/bar.txt", false);
          }
        });
  }
  
  @Test
  public void testConstructorWithExcludes() {
    PatternFileMatcher matcher = new PatternFileMatcher(filesMock, sysMock_, "bar1.txt", false);
   

    assertTrue(matcher.isMatch("bar1.txt"));
    assertFalse(matcher.isMatch("bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*.txt", false);
    assertTrue(matcher.isMatch("bar.txt"));
    assertFalse(matcher.isMatch("foo/bar.txt"));
    assertFalse(matcher.isMatch("foo/bar/bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*/bar.txt", false);
    assertTrue(matcher.isMatch("bar.txt"));
    assertTrue(matcher.isMatch("foo/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar/bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "bar*/bar.txt", false);
    assertTrue(matcher.isMatch("bar/bar.txt"));
    assertTrue(matcher.isMatch("bar3/bar.txt"));
    assertFalse(matcher.isMatch("foo/bar.txt"));
    assertFalse(matcher.isMatch("foo/bar/bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "foo/*/bar*", false);
    assertFalse(matcher.isMatch("bar/bar.txt"));
    assertFalse(matcher.isMatch("bar3/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar/goo/bar.boo"));
  }
  
  @Test
  public void testConstructorWithExcludesOnWindows() {
    setupPaths("\\");
    PatternFileMatcher matcher = new PatternFileMatcher(filesMock, sysMock_, "bar1.txt", false);
   
    assertTrue(matcher.isMatch("bar1.txt"));
    assertFalse(matcher.isMatch("bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*.txt", false);
    assertTrue(matcher.isMatch("bar.txt"));
    assertFalse(matcher.isMatch("foo\\bar.txt"));
    assertFalse(matcher.isMatch("foo\\bar\\bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*/bar.txt", false);
    assertTrue(matcher.isMatch("bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar\\bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "bar*/bar.txt", false);
    assertTrue(matcher.isMatch("bar\\bar.txt"));
    assertTrue(matcher.isMatch("bar3\\bar.txt"));
    assertFalse(matcher.isMatch("foo\\bar.txt"));
    assertFalse(matcher.isMatch("foo\\bar\\bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "foo/*/bar*", false);
    assertFalse(matcher.isMatch("bar\\bar.txt"));
    assertFalse(matcher.isMatch("bar3\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar\\goo\\bar.boo"));
  }
  
  @Test
  public void testConstructorWithIncludes() {
    PatternFileMatcher matcher = new PatternFileMatcher(filesMock, sysMock_, "bar.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
    assertFalse(matcher.isMatch("bar1.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
    //not included because file is in a sub directory
    assertFalse(matcher.isMatch("foo/bar.txt"));
    assertFalse(matcher.isMatch("foo/bar/bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*/bar.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
    assertTrue(matcher.isMatch("foo/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar/bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "bar*/bar.txt", true);
    assertTrue(matcher.isMatch("bar/bar.txt"));
    assertTrue(matcher.isMatch("bar3/bar.txt"));
    //not included becaus of directory
    assertFalse(matcher.isMatch("foo/bar.txt"));
    assertFalse(matcher.isMatch("foo/bar/bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "foo/*/bar*", true);
    assertFalse(matcher.isMatch("bar/bar.txt"));
    assertFalse(matcher.isMatch("bar3/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar.txt"));
    assertTrue(matcher.isMatch("foo/bar/bar.bar"));
  }
  
  
  @Test
  public void testConstructorWithIncludesOnWindows() {
    setupPaths("\\");
    PatternFileMatcher matcher = new PatternFileMatcher(filesMock, sysMock_, "bar.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
    assertFalse(matcher.isMatch("bar1.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
    //not included because file is in a sub directory
    assertFalse(matcher.isMatch("foo\\bar.txt"));
    assertFalse(matcher.isMatch("foo\\bar\\bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "*/bar.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar\\bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "bar*/bar.txt", true);
    assertTrue(matcher.isMatch("bar\\bar.txt"));
    assertTrue(matcher.isMatch("bar3\\bar.txt"));
    //not included becaus of directory
    assertFalse(matcher.isMatch("foo\\bar.txt"));
    assertFalse(matcher.isMatch("foo\\bar\\bar.txt"));
    
    matcher = new PatternFileMatcher(filesMock, sysMock_, "foo/*/bar*", true);
    assertFalse(matcher.isMatch("bar\\bar.txt"));
    assertFalse(matcher.isMatch("bar3\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar.txt"));
    assertTrue(matcher.isMatch("foo\\bar\\bar.bar"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorLogMatch() {
    when(log_.isLogEnabled(PatternFileMatcher.class)).thenReturn(true);
    
    PatternFileMatcher matcher = new PatternFileMatcher(filesMock,sysMock_, "bar.txt", true);
    assertTrue(matcher.isMatch("bar.txt"));
   
    FileEnMessages messages = FileEnMessages.INSTANCE;
    assertEquals(messages.getTheFollowingFile() + "\n" +
        "/somewhere/bar.txt\n" + 
        messages.getMatchedTheFollowingPattern() + "\n" + 
        messages.getIncludes() + "bar.txt", printlnMock.getArg(0));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorLogNoMatch() {
    when(log_.isLogEnabled(PatternFileMatcher.class)).thenReturn(true);
    
    PatternFileMatcher matcher = new PatternFileMatcher(filesMock,sysMock_, "bar.txt", false);
    assertFalse(matcher.isMatch("bar2.txt"));
    
    FileEnMessages messages = FileEnMessages.INSTANCE;
    assertEquals(messages.getTheFollowingFile() + "\n" +
        "/somewhere/bar2.txt\n" + 
        messages.getDidNotMatchedTheFollowingPattren() + "\n" + 
        messages.getExcludes() +"bar.txt", printlnMock.getArg(0));
  }
  
 
}

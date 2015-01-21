package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.I_FileMatcher;
import org.adligo.fabricate.common.files.IncludesExcludesFileMatcher;
import org.adligo.fabricate.common.files.PatternFileMatcher;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

@SourceFileScope (sourceClass=I_FileMatcher.class)
public class IncludesExcludesFileMatcherTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  
  private I_FabLog logMock_;
  private I_FabFileIO filesMock;
  private MockMethod<Void> printlnMock;
  private MockMethod<File> instanceMethod;
  
  private I_FabricateConstants constantsMock;
  
  @Override
  public void beforeTests() {
    printlnMock = new MockMethod<Void>();
    sysMock_ = mock(I_FabSystem.class);
    logMock_ = mock(I_FabLog.class);
    doAnswer(printlnMock).when(logMock_).println(any());
    when(sysMock_.getLog()).thenReturn(logMock_);
    
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
  
  @Test
  public void testConstructor() {
    IncludesExcludesFileMatcher matcher = new IncludesExcludesFileMatcher(null, null);
    assertFalse(matcher.isMatch("hey.txt"));
    I_FileMatcher excludeFile = new PatternFileMatcher(filesMock, sysMock_, "foo.txt", false);
    matcher = new IncludesExcludesFileMatcher(null, Collections.singletonList(excludeFile));
    assertFalse(matcher.isMatch("foo.txt"));
    
    I_FileMatcher includesFile = new PatternFileMatcher(filesMock, sysMock_, "*.txt", true);
    matcher = new IncludesExcludesFileMatcher(Collections.singletonList(includesFile), 
        Collections.singletonList(excludeFile));
    assertFalse(matcher.isMatch("foo.txt"));
    
    includesFile = new PatternFileMatcher(filesMock, sysMock_, "*foo.txt", true);
    matcher = new IncludesExcludesFileMatcher(Collections.singletonList(includesFile), 
        Collections.singletonList(excludeFile));
    assertFalse(matcher.isMatch("foo.txt"));
    assertTrue(matcher.isMatch("long_foo.txt"));
    assertFalse(matcher.isMatch("bar.txt"));
  }
  
}

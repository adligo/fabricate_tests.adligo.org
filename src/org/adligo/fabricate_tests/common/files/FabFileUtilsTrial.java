package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.FabFileUtils;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;

@SourceFileScope (sourceClass=FabFileUtils.class,
  minCoverage=82.0,allowedCircularDependencies=CircularDependencies.AllowInnerOuterClasses)
public class FabFileUtilsTrial extends MockitoSourceFileTrial {

  @Test
  public void testGetAbsoluteDirCurrentOs() {
    if (File.separator == "/") {
      testGetAbsoluteDirUnix();
    } else {
      testGetAbsoluteDirWindows();
    }
  }
  
  @Test
  public void testGetAbsoluteDirUnix() {
    String path = "/foo/bar/ext.xml";
    I_FabSystem sysMock = mock(I_FabSystem.class);
    FabFileIO files = new FabFileIO(sysMock);
    
    assertEquals("/foo/bar/", FabFileUtils.getAbsoluteDir(path, '/'));
    assertEquals("/foo/", FabFileUtils.getAbsoluteDir("/foo/bar.txt", '/'));
  }
  
  @Test
  public void testGetAbsoluteDirWindows() {
    String path = "C:\\foo\\bar\\ext.xml";
    I_FabSystem sysMock = mock(I_FabSystem.class);
    FabFileIO files = new FabFileIO(sysMock);
    
    assertEquals("C:\\foo\\bar\\", FabFileUtils.getAbsoluteDir(path, '\\'));
    assertEquals("C:\\foo\\", FabFileUtils.getAbsoluteDir("C:\\foo\\bar.txt", '\\'));
  }
}

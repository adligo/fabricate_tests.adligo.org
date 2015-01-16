package org.adligo.fabricate_tests.files;

import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.files.FabFileIO;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;

@SourceFileScope (sourceClass=FabFileIO.class,
  minCoverage=72.0,allowedCircularDependencies=CircularDependencies.AllowInnerOuterClasses)
public class FabFilesTrial extends MockitoSourceFileTrial {

  @Test
  public void testIO() throws Exception {
    FabFileIO fabFiles = FabFileIO.INSTANCE;
    String separator = File.separator;
    
    if (fabFiles.exists("test_data" + separator + "file_trials" + separator + "fab_files_trial")) {
      fabFiles.removeRecursive("test_data" + separator + "file_trials" + separator + "fab_files_trial");
    }
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial"));
    assertTrue(fabFiles.mkdirs("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial" + separator + "temp"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial" + separator + "temp"));
    File file = fabFiles.create("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial" + separator + "temp" + separator + "createdFile.txt");
    assertNotNull(file);
    assertEquals("createdFile.txt", file.getName());
    assertTrue(fabFiles.exists(file.getAbsolutePath()));
    fabFiles.deleteOnExit(file.getAbsolutePath());
    assertEquals(file.getAbsolutePath(), fabFiles.getAbsolutePath("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp" + separator + "createdFile.txt"));
    assertEquals(separator, fabFiles.getNameSeparator());
    
    assertEquals("test_data/file_trials/" + 
        "fab_files_trial/temp/createdFile.txt" ,fabFiles.getSlashPath("test_data\\file_trials\\" + 
        "fab_files_trial\\temp\\createdFile.txt"));
    File instanceFile = fabFiles.instance(file.getAbsolutePath());
    assertEquals("createdFile.txt", instanceFile.getName());
    
    assertTrue(fabFiles.mkdirs("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    assertTrue(fabFiles.mkdirs("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
  
    fabFiles.removeRecursive("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2");
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    
    I_FabLog log = mock(I_FabLog.class);
    fabFiles.setLog(log);
    assertSame(log, fabFiles.getLog());
  }
}

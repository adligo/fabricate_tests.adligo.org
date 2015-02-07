package org.adligo.fabricate_tests.repository;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.repository.DefaultRepositoryPathBuilder;
import org.adligo.fabricate.repository.DependencyManager;
import org.adligo.fabricate.repository.I_DependencyManager;
import org.adligo.fabricate.repository.I_RepositoryFactory;
import org.adligo.fabricate.repository.I_RepositoryPathBuilder;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SourceFileScope (sourceClass=DependencyManager.class, minCoverage=38.0)
public class DependencyManagerTrial extends MockitoSourceFileTrial {
  DefaultRepositoryPathBuilder builder_ = new DefaultRepositoryPathBuilder("repo/","/");
  private I_FabSystem sysMock_;
  private I_FabFileIO filesMock_;
  private I_FabLog logMock_;
  private I_RepositoryFactory pathBuilderFactoryMock_;
  private MockMethod<Void> printlnMethod_;
  private MockMethod<Void> printTraceMethod_;
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeperator()).thenReturn(System.lineSeparator());
    
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
    
    filesMock_ = mock(I_FabFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(filesMock_);
    
    pathBuilderFactoryMock_ = mock(I_RepositoryFactory.class);
    when(pathBuilderFactoryMock_.createRepositoryPathBuilder(any())).then(
        new MockMethod<I_RepositoryPathBuilder>(
        new I_ReturnFactory<I_RepositoryPathBuilder>() {

          @Override
          public I_RepositoryPathBuilder create(Object[] keys) {
            return new DefaultRepositoryPathBuilder (
                (String) keys[0], "/");
          }
        }));
    printlnMethod_ = new MockMethod<Void>();
    printTraceMethod_ = new MockMethod<Void>();
    doAnswer(printlnMethod_).when(logMock_).println(any());
    doAnswer(printTraceMethod_).when(logMock_).printTrace(any());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodManageSimple()  throws IOException {
   
    
    I_DependencyManager dm = new DependencyManager(sysMock_, 
        Collections.singletonList("http://example.com/"), 
        new DefaultRepositoryPathBuilder("C:\\foo\\", "\\"));
    dm.setFactory(pathBuilderFactoryMock_);
    
    DependencyMutant dep = new DependencyMutant();
    dep.setGroup("group");
    dep.setArtifact("artifact");
    dep.setVersion("321");
    dep.setType("jar");
    
    MockMethod<Void> downloadFileMethod = new MockMethod<Void>();
    doAnswer(downloadFileMethod).when(filesMock_).downloadFile(any(), any());
    
    String md5 = "abcd";
    MockMethod<String> calcMd5Method = new MockMethod<String>(md5, true);
    doAnswer(calcMd5Method).when(filesMock_).calculateMd5(any());
    MockMethod<String> readFileMethod = new MockMethod<String>(md5, true);
    doAnswer(readFileMethod).when(filesMock_).readFile(any());
    
    MockMethod<Boolean> existsMethod = new MockMethod<Boolean>(
        new I_ReturnFactory<Boolean>() {
          private Set<String> checked = new HashSet<String>();
          
          @SuppressWarnings("boxing")
          @Override
          public Boolean create(Object[] keys) {
            String file = (String) keys[0];
            if ("C:\\foo\\group\\artifact-321.jar.md5".equals(file)) {
              return true;
            }
            if (checked.contains(file)) {
              return true;
            } else {
              checked.add(file);
            }
            return false;
          }
        }, false);
    when(filesMock_.exists(any())).then(existsMethod);
    assertTrue(dm.manange(dep));
    
    assertEquals("Starting download from the following url;" + System.lineSeparator() +
        "http://example.com/group/artifact/321/artifact-321.jar.md5" + System.lineSeparator() +
        "to the following folder;" + System.lineSeparator() + 
        "C:\\foo\\group\\artifact-321.jar.md5",printlnMethod_.getArg(0));
    assertEquals("http://example.com/group/artifact/321/artifact-321.jar.md5", downloadFileMethod.getArgs(0)[0]);
    assertEquals("C:\\foo\\group\\artifact-321.jar.md5", 
        downloadFileMethod.getArgs(0)[1]);
    
    assertEquals("C:\\foo\\group\\artifact-321.jar", 
        existsMethod.getArg(0));
    assertEquals("C:\\foo\\group", 
        existsMethod.getArg(1));
    assertEquals("C:\\foo\\group", 
        existsMethod.getArg(2));
    assertEquals("C:\\foo\\group\\artifact-321.jar.md5", 
        existsMethod.getArg(3));
    
    assertEquals("The download from the following url;" + System.lineSeparator() +
        "http://example.com/group/artifact/321/artifact-321.jar.md5" + System.lineSeparator() +
        "finished.",printlnMethod_.getArg(1));
    
    assertEquals("Starting download from the following url;" + System.lineSeparator() +
        "http://example.com/group/artifact/321/artifact-321.jar" + System.lineSeparator() +
        "to the following folder;" + System.lineSeparator() + 
        "C:\\foo\\group\\artifact-321.jar",printlnMethod_.getArg(2));
    
    assertEquals("http://example.com/group/artifact/321/artifact-321.jar", downloadFileMethod.getArgs(1)[0]);
    assertEquals("C:\\foo\\group\\artifact-321.jar", downloadFileMethod.getArgs(1)[1]);
    assertEquals(2, downloadFileMethod.count());
    
    assertEquals("C:\\foo\\group", 
        existsMethod.getArg(2));
    assertEquals("C:\\foo\\group\\artifact-321.jar.md5", 
        existsMethod.getArg(3));
    assertEquals("C:\\foo\\group\\artifact-321.jar", 
        existsMethod.getArg(4));
    assertEquals(5, existsMethod.count());
    
    assertEquals("The download from the following url;" + System.lineSeparator() +
        "http://example.com/group/artifact/321/artifact-321.jar" + System.lineSeparator() +
        "finished.",printlnMethod_.getArg(3));
    
    assertEquals("C:\\foo\\group\\artifact-321.jar", calcMd5Method.getArg(0));
    assertEquals(1, calcMd5Method.count());
    assertEquals("C:\\foo\\group\\artifact-321.jar.md5", readFileMethod.getArg(0));
    assertEquals(1, readFileMethod.count());
    
    assertEquals("The following artifact;" + System.lineSeparator() +
        "C:\\foo\\group\\artifact-321.jar" + System.lineSeparator() + 
        "passed the md5 check.",printlnMethod_.getArg(4));
    
  }
  
  @Test
  public void testMethodManageSimpleExtract() {
    
  }
  
  @Test
  public void testMethodManageArtifactDownloadIOExceptions() {
    
  }

  @Test
  public void testMethodManageCreateGroupFolder() {
    
  }
  

  
  @Test
  public void testMethodManageExtractIOExcepions() {
    
  } 

  @Test
  public void testMethodManageExtractNotValid() {
    
  } 
  
  @Test
  public void testMethodManageGroupFolderCreationOnAnotherThread() {
    
  }
  
  @Test
  public void testMethodManageMd5DownloadIOExceptions() {
    
  }
  
  @Test
  public void testMethodManageMd5MismatchRetrySuccess() {
    
  }  
  
}

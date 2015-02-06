package org.adligo.fabricate_tests.repository;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.repository.DefaultRepositoryPathBuilder;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=DefaultRepositoryPathBuilder.class, minCoverage=95.0)
public class DefaultRepositoryPathBuilderTrial extends MockitoSourceFileTrial {
  DefaultRepositoryPathBuilder builder = new DefaultRepositoryPathBuilder("repo/","/");

  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(IllegalArgumentException.class),
      new I_Thrower() {
        
        @Override
        public void run() throws Throwable {
          new DefaultRepositoryPathBuilder(null,null);
        }
      });
    assertThrown(new ExpectedThrowable(IllegalArgumentException.class),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new DefaultRepositoryPathBuilder("","/");
          }
        });
    assertThrown(new ExpectedThrowable(IllegalArgumentException.class),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new DefaultRepositoryPathBuilder("r","");
          }
        });
  }
  
  @Test
  public void testConstructor() {
    DefaultRepositoryPathBuilder builder = new DefaultRepositoryPathBuilder("repo2","\\");
    assertEquals("repo2", builder.getRepository());
    assertEquals("\\", builder.getSeperator());
  }
  
  @Test
  public void testMethodGetArtifactFileName() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("artifactName-123.txt", builder.getArtifactFileName(dm));
    
    dm.setFileName("fileName.txt");
    assertEquals("fileName.txt", builder.getArtifactFileName(dm));
    
  }
  
  @Test
  public void testMethodGetArtifactPath() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group/artifactName-123.txt", builder.getArtifactPath(dm));
    
    dm.setFileName("fileName.txt");
    assertEquals("repo/group/fileName.txt", builder.getArtifactPath(dm));
    
  }
  
  @Test
  public void testMethodGetArtifactUrl() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group/artifactName/123/artifactName-123.txt", builder.getArtifactUrl(dm));
    
    dm.setFileName("fileName.txt");
    assertEquals("repo/group/artifactName/123/fileName.txt", builder.getArtifactUrl(dm));
    
  }

  @Test
  public void testMethodExtractPath() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group/artifactName-123.txt-extract", builder.getExtractPath(dm, 
        FabricateEnConstants.INSTANCE));
  }
  
  @Test
  public void testMethodGetFolderPath() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group", builder.getFolderPath(dm));
  }

  @Test
  public void testMethodGetFolderUrl() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group/artifactName/123", builder.getFolderUrl(dm));
  }
  
  @Test
  public void testMethodGetMd5FileName() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("artifactName-123.txt.md5", builder.getMd5FileName(dm));
    
    dm.setFileName("fileName.txt");
    assertEquals("fileName.txt.md5", builder.getMd5FileName(dm));
    
  }
  
  @Test
  public void testMethodGetMd5PathName() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group/artifactName-123.txt.md5", builder.getMd5Path(dm));
    
    dm.setFileName("fileName.txt");
    assertEquals("repo/group/fileName.txt.md5", builder.getMd5Path(dm));
    
  }
  
  @Test
  public void testMethodGetMd5Url() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactName");
    dm.setType("txt");
    dm.setGroup("group");
    dm.setVersion("123");
    assertEquals("repo/group/artifactName/123/artifactName-123.txt.md5", builder.getMd5Url(dm));
    
    dm.setFileName("fileName.txt");
    assertEquals("repo/group/artifactName/123/fileName.txt.md5", builder.getMd5Url(dm));
    
  }
}

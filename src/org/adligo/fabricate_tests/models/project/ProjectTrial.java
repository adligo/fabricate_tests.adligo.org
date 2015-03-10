package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.dependencies.Dependency;
import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.dependencies.I_LibraryDependency;
import org.adligo.fabricate.models.dependencies.I_ProjectDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependencyMutant;
import org.adligo.fabricate.models.dependencies.ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.fabricate.models.project.Project;
import org.adligo.fabricate.models.project.ProjectMutant;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.List;

@SourceFileScope (sourceClass=Project.class,minCoverage=82.0)
public class ProjectTrial extends MockitoSourceFileTrial {

  public DependencyMutant getDependencyA() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactA");
    dm.setExtract(true);
    dm.setFileName("fileNameA");
    dm.setGroup("groupA");
    dm.setPlatform("platformA");
    dm.setType("typeA");
    dm.setVersion("versionA");
    return dm;
  }
  
  public DependencyMutant getDependencyB() {
    DependencyMutant dm = new DependencyMutant();
    dm.setArtifact("artifactB");
    dm.setExtract(true);
    dm.setFileName("fileNameB");
    dm.setGroup("groupB");
    dm.setPlatform("platformB");
    dm.setType("typeB");
    dm.setVersion("versionB");
    return dm;
  }
  
  public LibraryDependencyMutant getLibraryDependencyA() {
    LibraryDependencyMutant dm = new LibraryDependencyMutant();
    dm.setLibraryName("libraryNameA");
    dm.setPlatform("platformA");
    return dm;
  }

  public LibraryDependencyMutant getLibraryDependencyB() {
    LibraryDependencyMutant dm = new LibraryDependencyMutant();
    dm.setLibraryName("libraryNameB");
    dm.setPlatform("platformB");
    return dm;
  }
  

  public ProjectDependencyMutant getProjectDependencyA() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("projectA");
    dm.setPlatform("platformA");
    return dm;
  }

  public ProjectDependencyMutant getProjectDependencyB() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("projectB");
    dm.setPlatform("platformB");
    return dm;
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopy() {
    ProjectMutant pm = new ProjectMutant();
    pm.setDir("dir");
    pm.setName("name");
    pm.setVersion("version");
    
    Project copy = new Project(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    assertEquals("java.util.Collections$EmptyList", copy.getDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getDependencies().getClass().getName());
    assertEquals("java.util.Collections$EmptyList", copy.getDependencies().getClass().getName());
    
    DependencyMutant dmA = getDependencyA();
    pm.addDependency(dmA);
    pm.addDependency(dmA);
    LibraryDependencyMutant ldmA = getLibraryDependencyA();
    pm.addLibraryDependency(ldmA);
    pm.addLibraryDependency(ldmA);
    ProjectDependencyMutant pdmA = getProjectDependencyA();
    pm.addProjectDependency(pdmA);
    pm.addProjectDependency(pdmA);
    copy = new Project(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    List<I_Dependency> dependencies = copy.getDependencies();
    I_Dependency zero = dependencies.get(0);
    assertEquals(dmA, zero);
    assertEquals(Dependency.class.getName(), zero.getClass().getName());
    assertEquals(1, dependencies.size());
    
    List<I_LibraryDependency> libDeps = copy.getLibraryDependencies();
    I_LibraryDependency lz = libDeps.get(0);
    assertEquals(ldmA, lz);
    assertEquals(LibraryDependency.class.getName(), lz.getClass().getName());
    assertEquals(1, libDeps.size());
    
    List<I_ProjectDependency> projDeps = copy.getProjectDependencies();
    I_ProjectDependency pd = projDeps.get(0);
    assertEquals(pdmA, pd);
    assertEquals(ProjectDependency.class.getName(), pd.getClass().getName());
    assertEquals(1, projDeps.size());
   
    Project copyCopy = new Project(copy);
    assertEquals("dir", copyCopy.getDir());
    assertEquals("name", copyCopy.getName());
    assertEquals("version", copyCopy.getVersion());
    
    dependencies = copyCopy.getDependencies();
    zero = dependencies.get(0);
    assertEquals(dmA, zero);
    assertEquals(Dependency.class.getName(), zero.getClass().getName());
    assertEquals(1, dependencies.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", dependencies.getClass().getName());
    
    libDeps = copyCopy.getLibraryDependencies();
    lz = libDeps.get(0);
    assertEquals(ldmA, lz);
    assertEquals(LibraryDependency.class.getName(), lz.getClass().getName());
    assertEquals(1, libDeps.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", libDeps.getClass().getName());
    
    projDeps = copyCopy.getProjectDependencies();
    pd = projDeps.get(0);
    assertEquals(pdmA, pd);
    assertEquals(ProjectDependency.class.getName(), pd.getClass().getName());
    assertEquals(1, projDeps.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", projDeps.getClass().getName());
  }
}

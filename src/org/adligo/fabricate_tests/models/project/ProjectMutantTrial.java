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
import org.adligo.fabricate.models.project.ProjectMutant;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.FabricateProjectType;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectDependenciesType;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.LibraryDependencyMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.ProjectDependencyMutantTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=ProjectMutant.class,minCoverage=95.0)
public class ProjectMutantTrial extends MockitoSourceFileTrial {

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
    
    DependencyMutant dmA = getDependencyA();
    pm.addDependency(dmA);
    pm.addDependency(dmA);
    
    LibraryDependencyMutant ldmA = getLibraryDependencyA();
    pm.addLibraryDependency(ldmA);
    pm.addLibraryDependency(ldmA);
    
    ProjectDependencyMutant pdmA = getProjectDependencyA();
    pm.addProjectDependency(pdmA);
    pm.addProjectDependency(pdmA);
    
    ProjectMutant copy = new ProjectMutant(pm);
    
    assertEquals("dir", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    List<I_Dependency> dependencies = copy.getDependencies();
    assertSame(dmA, dependencies.get(0));
    assertEquals(1, dependencies.size());
    
    List<I_LibraryDependency> libDeps = copy.getLibraryDependencies();
    assertSame(ldmA, libDeps.get(0));
    assertEquals(1, libDeps.size());
    
    List<I_ProjectDependency> projDeps = copy.getProjectDependencies();
    assertSame(pdmA, projDeps.get(0));
    assertEquals(1, projDeps.size());
    
  }
  
  @Test
  public void testConstructorCopyFromXml() {
    ProjectType pt = new ProjectType();
    pt.setName("name");
    pt.setVersion("version");
    
    FabricateProjectType fpt = new FabricateProjectType();
    fpt.setDependencies(null);
    ProjectMutant copy = new ProjectMutant("dir2", pt, fpt);
    
    assertEquals("dir2", copy.getDir());
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
    
    
    ProjectDependenciesType pdt = new ProjectDependenciesType();
    pdt.getDependency().addAll(DependencyMutantTrial.getDependencies());
    pdt.getLibrary().addAll(LibraryDependencyMutantTrial.getListAB());
    pdt.getProject().addAll(ProjectDependencyMutantTrial.getListAB());
    fpt.setDependencies(pdt);
    
    ProjectMutant copy2 = new ProjectMutant("dir2", pt, fpt);
    
    assertEquals("dir2", copy2.getDir());
    assertEquals("name", copy2.getName());
    assertEquals("version", copy2.getVersion());
    
    DependencyMutantTrial.assertDependencyConversion(this, copy2.getDependencies());
    LibraryDependencyMutantTrial.assertAB(this, copy2.getLibraryDependencies());
    ProjectDependencyMutantTrial.assertAB(this, copy2.getProjectDependencies());
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodsGetsAndSets() {
    ProjectMutant pm = new ProjectMutant();
    pm.setDir("dir");
    assertEquals("dir", pm.getDir());
    
    pm.setName("name.adigo.org");
    assertEquals("name.adigo.org", pm.getName());
    assertEquals("name", pm.getShortName());
    assertEquals("adigo.org", pm.getDomainName());
    
    pm.setVersion("version");
    assertEquals("version", pm.getVersion());
    
    DependencyMutant dmA = getDependencyA();
    pm.addDependency(dmA);
    List<I_Dependency> dependencies = pm.getDependencies();
    assertSame(dmA, dependencies.get(0));
    assertEquals(1, dependencies.size());
    
    DependencyMutant dmB = getDependencyB();
    List<DependencyMutant> dms = new ArrayList<DependencyMutant>();
    dms.add(dmA);
    dms.add(dmB);
    pm.setDependencies(dms);
    
    dependencies = pm.getDependencies();
    assertSame(dmA, dependencies.get(0));
    assertSame(dmB, dependencies.get(1));
    assertEquals(2, dependencies.size());
    
    LibraryDependencyMutant ldmA = getLibraryDependencyA();
    pm.addLibraryDependency(ldmA);
    List<I_LibraryDependency> libDeps = pm.getLibraryDependencies();
    assertSame(ldmA, libDeps.get(0));
    assertEquals(1, libDeps.size());
    
    LibraryDependencyMutant ldmB = getLibraryDependencyB();
    List<LibraryDependencyMutant> ldms = new ArrayList<LibraryDependencyMutant>();
    ldms.add(ldmA);
    ldms.add(ldmB);
    pm.setLibraryDependencies(ldms);
    
    libDeps = pm.getLibraryDependencies();
    assertSame(ldmA, libDeps.get(0));
    assertSame(ldmB, libDeps.get(1));
    assertEquals(2, libDeps.size());
    
    ProjectDependencyMutant pdmA = getProjectDependencyA();
    pm.addProjectDependency(pdmA);
    List<I_ProjectDependency> projDeps = pm.getProjectDependencies();
    assertSame(pdmA, projDeps.get(0));
    assertEquals(1, projDeps.size());
    
    ProjectDependencyMutant pdmB = getProjectDependencyB();
    List<ProjectDependencyMutant> pdms = new ArrayList<ProjectDependencyMutant>();
    pdms.add(pdmA);
    pdms.add(pdmB);
    pm.setProjectDependencies(pdms);
    
    projDeps = pm.getProjectDependencies();
    assertSame(pdmA, projDeps.get(0));
    assertSame(pdmB, projDeps.get(1));
    assertEquals(2, libDeps.size());
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodsAddAndSetImmutables() {
    ProjectMutant pm = new ProjectMutant();
    
    Dependency dmA = new Dependency(getDependencyA());
    pm.addDependency(dmA);
    List<I_Dependency> dependencies = pm.getDependencies();
    assertEquals(dmA, dependencies.get(0));
    assertEquals(1, dependencies.size());
    
    Dependency dmB = new Dependency(getDependencyB());
    List<Dependency> dms = new ArrayList<Dependency>();
    dms.add(dmA);
    dms.add(dmB);
    pm.setDependencies(dms);
    
    dependencies = pm.getDependencies();
    assertEquals(dmA, dependencies.get(0));
    assertEquals(dmB, dependencies.get(1));
    assertEquals(2, dependencies.size());
    
    LibraryDependency ldmA = new LibraryDependency(getLibraryDependencyA());
    pm.addLibraryDependency(ldmA);
    List<I_LibraryDependency> libDeps = pm.getLibraryDependencies();
    assertEquals(ldmA, libDeps.get(0));
    assertEquals(1, libDeps.size());
    
    LibraryDependency ldmB =  new LibraryDependency(getLibraryDependencyB());
    List<LibraryDependency> ldms = new ArrayList<LibraryDependency>();
    ldms.add(ldmA);
    ldms.add(ldmB);
    pm.setLibraryDependencies(ldms);
    
    libDeps = pm.getLibraryDependencies();
    assertEquals(ldmA, libDeps.get(0));
    assertEquals(ldmB, libDeps.get(1));
    assertEquals(2, libDeps.size());
    
    ProjectDependency pdmA = new ProjectDependency(getProjectDependencyA());
    pm.addProjectDependency(pdmA);
    List<I_ProjectDependency> projDeps = pm.getProjectDependencies();
    assertEquals(pdmA, projDeps.get(0));
    assertEquals(1, projDeps.size());
    
    ProjectDependency pdmB = new ProjectDependency(getProjectDependencyB());
    List<ProjectDependency> pdms = new ArrayList<ProjectDependency>();
    pdms.add(pdmA);
    pdms.add(pdmB);
    pm.setProjectDependencies(pdms);
    
    projDeps = pm.getProjectDependencies();
    assertEquals(pdmA, projDeps.get(0));
    assertEquals(pdmB, projDeps.get(1));
    assertEquals(2, libDeps.size());
  }
}

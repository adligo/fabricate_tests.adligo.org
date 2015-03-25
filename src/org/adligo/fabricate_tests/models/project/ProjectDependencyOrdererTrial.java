package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.dependencies.I_ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.fabricate.models.project.I_Project;
import org.adligo.fabricate.models.project.ProjectDependencyOrderer;
import org.adligo.fabricate.models.project.ProjectMutant;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=ProjectDependencyOrderer.class,minCoverage=50.0)
public class ProjectDependencyOrdererTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private I_FabLog logMock_;
  
  @Override
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    logMock_ = mock(I_FabLog.class);
    when(sysMock_.getLog()).thenReturn(logMock_);
  }
  
  @Test
  public void testConstructorSimpleOrderABC() {
    List<I_Project> projects = new ArrayList<I_Project>();
    projects.add(getProjectC());
    projects.add(getProjectB());
    projects.add(getProjectA());
    ProjectDependencyOrderer pdo = new ProjectDependencyOrderer(projects, sysMock_);
    
    List<String> names = pdo.getNames();
    assertEquals("projectA", names.get(0));
    assertEquals("projectB", names.get(1));
    assertEquals("projectC", names.get(2));
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", names.getClass().getName());
    
    List<I_Project> projectList = pdo.getProjects();
    assertEquals(getProjectA(), projectList.get(0));
    assertEquals(getProjectB(), projectList.get(1));
    assertEquals(getProjectC(), projectList.get(2));
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", projectList.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorTrunkWithBranchingRoots() {
    List<I_Project> projects = new ArrayList<I_Project>();
    projects.add(getProjectRootBranching());
    projects.add(getProjectLeft2());
    projects.add(getProject("leftLeft"));
    projects.add(getProject("leftRight"));
    projects.add(getProjectRight2());
    projects.add(getProject("rightLeft"));
    projects.add(getProject("rightRight"));
    ProjectDependencyOrderer pdo = new ProjectDependencyOrderer(projects, sysMock_);
    
    List<String> names = pdo.getNames();
    assertEquals("leftLeft", names.get(0));
    assertEquals("leftRight", names.get(1));
    assertEquals("rightLeft", names.get(2));
    assertEquals("rightRight", names.get(3));
    assertEquals("left", names.get(4));
    assertEquals("right", names.get(5));
    assertEquals("root", names.get(6));
    assertEquals(7, names.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", names.getClass().getName());
    
    List<I_Project> projectList = pdo.getProjects();
    assertEquals(getProject("leftLeft"), projectList.get(0));
    assertEquals(getProject("leftRight"), projectList.get(1));
    assertEquals(getProject("rightLeft"), projectList.get(2));
    assertEquals(getProject("rightRight"), projectList.get(3));
    assertEquals(getProjectLeft2(), projectList.get(4));
    assertEquals(getProjectRight2(), projectList.get(5));
    assertEquals(getProjectRootBranching(), projectList.get(6));
    assertEquals(7, projectList.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", projectList.getClass().getName());    
  }
  
  @Test
  public void testConstructorTrunkWithTwoRoots() {
    List<I_Project> projects = new ArrayList<I_Project>();
    projects.add(getProjectRootWithRightLeft());
    projects.add(getProjectLeft());
    projects.add(getProjectRight());
    
    ProjectDependencyOrderer pdo = new ProjectDependencyOrderer(projects, sysMock_);
    
    List<String> names = pdo.getNames();
    assertEquals("left", names.get(0));
    assertEquals("right", names.get(1));
    assertEquals("root", names.get(2));
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", names.getClass().getName());
    
    List<I_Project> projectList = pdo.getProjects();
    assertEquals(getProjectLeft(), projectList.get(0));
    assertEquals(getProjectRight(), projectList.get(1));
    assertEquals(getProjectRootWithRightLeft(), projectList.get(2));
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", projectList.getClass().getName());
  }
  
  private ProjectMutant getProject(String name) {
    ProjectMutant dm = new ProjectMutant();
    dm.setName(name);
    return dm;
  }
  
  private ProjectMutant getProjectA() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("projectA");
    return dm;
  }
  
  private ProjectMutant getProjectB() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("projectB");
    List<I_ProjectDependency> deps = new ArrayList<I_ProjectDependency>();
    deps.add(getProjectDependencyA());
    dm.setProjectDependencies(deps);
    return dm;
  }
  
  private ProjectMutant getProjectC() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("projectC");
    List<I_ProjectDependency> deps = new ArrayList<I_ProjectDependency>();
    deps.add(getProjectDependencyB());
    dm.setProjectDependencies(deps);
    return dm;
  }
  
  private ProjectMutant getProjectRootWithRightLeft() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("root");
    List<I_ProjectDependency> deps = new ArrayList<I_ProjectDependency>();
    deps.add(getProjectDependencyLeft());
    deps.add(getProjectDependencyRight());
    dm.setProjectDependencies(deps);
    return dm;
  }
  
  private ProjectMutant getProjectRootBranching() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("root");
    List<I_ProjectDependency> deps = new ArrayList<I_ProjectDependency>();
    deps.add(getProjectDependencyLeft());
    deps.add(getProjectDependencyRight());
    dm.setProjectDependencies(deps);
    return dm;
  }
  
  private ProjectMutant getProjectLeft() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("left");
    return dm;
  }
  
  private ProjectMutant getProjectLeft2() {
    ProjectMutant dm = new ProjectMutant();
    List<I_ProjectDependency> deps = new ArrayList<I_ProjectDependency>();
    deps.add(getProjectDependencyLeftLeft());
    deps.add(getProjectDependencyLeftRight());
    dm.setProjectDependencies(deps);
    dm.setName("left");
    return dm;
  }
  
  private ProjectMutant getProjectRight() {
    ProjectMutant dm = new ProjectMutant();
    dm.setName("right");
    return dm;
  }
  
  private ProjectMutant getProjectRight2() {
    ProjectMutant dm = new ProjectMutant();
    List<I_ProjectDependency> deps = new ArrayList<I_ProjectDependency>();
    deps.add(getProjectDependencyRightLeft());
    deps.add(getProjectDependencyRightRight());
    dm.setProjectDependencies(deps);
    dm.setName("right");
    return dm;
  }
  
  private ProjectDependencyMutant getProjectDependencyA() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("projectA");
    return dm;
  }

  private ProjectDependencyMutant getProjectDependencyB() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("projectB");
    return dm;
  }
  
  private ProjectDependencyMutant getProjectDependencyLeft() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("left");
    return dm;
  }

  private ProjectDependencyMutant getProjectDependencyRight() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("right");
    return dm;
  }
  
  private ProjectDependencyMutant getProjectDependencyLeftLeft() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("leftLeft");
    return dm;
  }

  private ProjectDependencyMutant getProjectDependencyLeftRight() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("leftRight");
    return dm;
  }
  
  private ProjectDependencyMutant getProjectDependencyRightLeft() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("rightLeft");
    return dm;
  }
  
  private ProjectDependencyMutant getProjectDependencyRightRight() {
    ProjectDependencyMutant dm = new ProjectDependencyMutant();
    dm.setProjectName("rightRight");
    return dm;
  }


}

package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.I_ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectDependencyType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=ProjectDependency.class, minCoverage=80.0)
public class ProjectDependencyTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new ProjectDependencyMutant((I_ProjectDependency) null);
      }
    });
  }
  
  @Test
  public void testConstructorCopy() {
    ProjectDependencyMutant pdm = new ProjectDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setProjectName("projectName");
    
    ProjectDependency pd = new ProjectDependency(pdm);
    
    assertEquals("platform", pd.getPlatform());
    assertEquals("projectName", pd.getProjectName());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    ProjectDependencyMutant pdm = new ProjectDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setProjectName("ln");
    ProjectDependency pd = new ProjectDependency(pdm);
    
    ProjectDependencyMutant pdmA = new ProjectDependencyMutant();
    pdmA.setPlatform("platform");
    pdmA.setProjectName("ln");
    ProjectDependency pdA = new ProjectDependency(pdmA);
    
    ProjectDependencyMutant pdm2 = new ProjectDependencyMutant();
    pdm2.setPlatform("platform");
    pdm2.setProjectName("ln2");
    ProjectDependency pd2 = new ProjectDependency(pdm2);
    
    ProjectDependencyMutant pdm3 = new ProjectDependencyMutant();
    pdm3.setPlatform("platform3");
    pdm3.setProjectName("ln");    
    ProjectDependency pd3 = new ProjectDependency(pdm3);
    
    ProjectDependencyMutant pdm4 = new ProjectDependencyMutant();
    pdm4.setProjectName("ln");  
    ProjectDependency pd4 = new ProjectDependency(pdm4);
    
    ProjectDependencyMutant pdm5 = new ProjectDependencyMutant();
    pdm5.setPlatform("platform");  
    ProjectDependency pd5 = new ProjectDependency(pdm5);
    
    assertEquals(pd, pd);
    assertEquals(pd.hashCode(), pd.hashCode());
    assertEquals("ProjectDependency [name=ln,platform=platform]", pd.toString());
    
    assertEquals(pd, pdA);
    assertEquals(pd.hashCode(), pdA.hashCode());   
    
    assertNotEquals(pd, pd2);
    assertNotEquals(pd.hashCode(), pd2.hashCode());   
    assertNotEquals(pd, pd3);
    assertNotEquals(pd.hashCode(), pd3.hashCode());   
    assertNotEquals(pd, pd4);
    assertNotEquals(pd.hashCode(), pd4.hashCode()); 
    assertNotEquals(pd, pd5);
    assertNotEquals(pd.hashCode(), pd5.hashCode()); 
    assertEquals("ProjectDependency [name=ln2,platform=platform]", pd2.toString());
    assertEquals("ProjectDependency [name=ln,platform=platform3]", pd3.toString());
    
    assertNotEquals(pdA, pd2);
    assertNotEquals(pdA.hashCode(), pd2.hashCode());   
    assertNotEquals(pdA, pd3);
    assertNotEquals(pdA.hashCode(), pd3.hashCode());   
    assertNotEquals(pdA, pd4);
    assertNotEquals(pdA.hashCode(), pd4.hashCode()); 
    assertNotEquals(pdA, pd5);
    assertNotEquals(pdA.hashCode(), pd5.hashCode()); 
    assertEquals("ProjectDependency [name=ln]", pd4.toString());
    
    assertNotEquals(pd3, pdA);
    assertNotEquals(pd3.hashCode(), pdA.hashCode());   
    assertNotEquals(pd3, pd2);
    assertNotEquals(pd3.hashCode(), pd2.hashCode());   
    assertNotEquals(pd3, pd4);
    assertNotEquals(pd3.hashCode(), pd4.hashCode());  
    assertNotEquals(pd3, pd5);
    assertNotEquals(pd3.hashCode(), pd5.hashCode());  
    assertEquals("ProjectDependency [name=null,platform=platform]", pd5.toString());
    
  }
  

  @Test
  public void testStaticMethodsConvert() {
    ProjectDependencyType lrt = new ProjectDependencyType();
    lrt.setPlatform("platform");
    lrt.setValue("projectA");
    
    ProjectDependency ldm = ProjectDependency.convert(lrt);
    assertEquals("platform", ldm.getPlatform());
    assertEquals("projectA", ldm.getProjectName());
    
    ProjectDependencyType lrtB = new ProjectDependencyType();
    lrtB.setPlatform("platformB");
    lrtB.setValue("projectB");
    
    ProjectDependency ldmB = ProjectDependency.convert(lrtB);
    assertEquals("platformB", ldmB.getPlatform());
    assertEquals("projectB", ldmB.getProjectName());
    
    List<ProjectDependencyType> asList = new ArrayList<ProjectDependencyType>();
    asList.add(lrt);
    asList.add(lrtB);
    
    List<ProjectDependency> convertedList = ProjectDependency.convert(asList);
    ldm = convertedList.get(0);
    assertEquals("platform", ldm.getPlatform());
    assertEquals("projectA", ldm.getProjectName());
    
    ldmB = convertedList.get(1);
    assertEquals("platformB", ldmB.getPlatform());
    assertEquals("projectB", ldmB.getProjectName());
  }
}

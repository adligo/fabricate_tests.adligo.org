package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.I_ProjectDependency;
import org.adligo.fabricate.models.dependencies.ProjectDependencyMutant;
import org.adligo.fabricate.xml.io_v1.project_v1_0.ProjectDependencyType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=ProjectDependencyMutant.class, minCoverage=80.0)
public class ProjectDependencyMutantTrial extends MockitoSourceFileTrial {

  public static void assertAB(I_Asserts asserts,  List<? extends I_ProjectDependency> convertedList) {
    I_ProjectDependency ldm = convertedList.get(0);
    asserts.assertEquals("platform", ldm.getPlatform());
    asserts.assertEquals("projectA", ldm.getProjectName());
    
    I_ProjectDependency ldmB = convertedList.get(1);
    asserts.assertEquals("platformB", ldmB.getPlatform());
    asserts.assertEquals("projectB", ldmB.getProjectName());
  }
  
  public static List<ProjectDependencyType> getListAB() {
    ProjectDependencyType lrt = getA();
    ProjectDependencyType lrtB = getB();
    
    List<ProjectDependencyType> asList = new ArrayList<ProjectDependencyType>();
    asList.add(lrt);
    asList.add(lrtB);
    return asList;
  }

  public static ProjectDependencyType getB() {
    ProjectDependencyType lrtB = new ProjectDependencyType();
    lrtB.setPlatform("platformB");
    lrtB.setValue("projectB");
    return lrtB;
  }

  public static ProjectDependencyType getA() {
    ProjectDependencyType lrt = new ProjectDependencyType();
    lrt.setPlatform("platform");
    lrt.setValue("projectA");
    return lrt;
  }
  
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new ProjectDependencyMutant(null);
      }
    });
  }
  
  @Test
  public void testConstructorCopy() {
    ProjectDependencyMutant pdm = new ProjectDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setProjectName("projectName");
    
    assertEquals("platform", pdm.getPlatform());
    assertEquals("projectName", pdm.getProjectName());
    
    ProjectDependencyMutant pdm2 = new ProjectDependencyMutant(pdm);
    
    assertEquals("platform", pdm2.getPlatform());
    assertEquals("projectName", pdm2.getProjectName());
    
  }
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    ProjectDependencyMutant pdm = new ProjectDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setProjectName("ln");
    
    ProjectDependencyMutant pdmA = new ProjectDependencyMutant();
    pdmA.setPlatform("platform");
    pdmA.setProjectName("ln");
    
    ProjectDependencyMutant pdm2 = new ProjectDependencyMutant();
    pdm2.setPlatform("platform");
    pdm2.setProjectName("ln2");
    
    ProjectDependencyMutant pdm3 = new ProjectDependencyMutant();
    pdm3.setPlatform("platform3");
    pdm3.setProjectName("ln");    
    
    ProjectDependencyMutant pdm4 = new ProjectDependencyMutant();
    pdm4.setProjectName("ln");  
    
    ProjectDependencyMutant pdm5 = new ProjectDependencyMutant();
    pdm5.setPlatform("platform");  
    
    assertEquals(pdm, pdm);
    assertEquals(pdm.hashCode(), pdm.hashCode());
    assertEquals("ProjectDependencyMutant [name=ln,platform=platform]", pdm.toString());
    
    assertEquals(pdm, pdmA);
    assertEquals(pdm.hashCode(), pdmA.hashCode());   
    
    assertNotEquals(pdm, pdm2);
    assertNotEquals(pdm.hashCode(), pdm2.hashCode());   
    assertNotEquals(pdm, pdm3);
    assertNotEquals(pdm.hashCode(), pdm3.hashCode());   
    assertNotEquals(pdm, pdm4);
    assertNotEquals(pdm.hashCode(), pdm4.hashCode()); 
    assertNotEquals(pdm, pdm5);
    assertNotEquals(pdm.hashCode(), pdm5.hashCode()); 
    assertEquals("ProjectDependencyMutant [name=ln2,platform=platform]", pdm2.toString());
    assertEquals("ProjectDependencyMutant [name=ln,platform=platform3]", pdm3.toString());
    
    assertNotEquals(pdmA, pdm2);
    assertNotEquals(pdmA.hashCode(), pdm2.hashCode());   
    assertNotEquals(pdmA, pdm3);
    assertNotEquals(pdmA.hashCode(), pdm3.hashCode());   
    assertNotEquals(pdmA, pdm4);
    assertNotEquals(pdmA.hashCode(), pdm4.hashCode()); 
    assertNotEquals(pdmA, pdm5);
    assertNotEquals(pdmA.hashCode(), pdm5.hashCode()); 
    assertEquals("ProjectDependencyMutant [name=ln]", pdm4.toString());
    
    assertNotEquals(pdm3, pdmA);
    assertNotEquals(pdm3.hashCode(), pdmA.hashCode());   
    assertNotEquals(pdm3, pdm2);
    assertNotEquals(pdm3.hashCode(), pdm2.hashCode());   
    assertNotEquals(pdm3, pdm4);
    assertNotEquals(pdm3.hashCode(), pdm4.hashCode());  
    assertNotEquals(pdm3, pdm5);
    assertNotEquals(pdm3.hashCode(), pdm5.hashCode());  
    assertEquals("ProjectDependencyMutant [name=null,platform=platform]", pdm5.toString());
  }
  
  @Test
  public void testStaticMethodsConvert() {
    ProjectDependencyType lrt = getA();
    
    ProjectDependencyMutant ldm = ProjectDependencyMutant.convert(lrt);
    assertEquals("platform", ldm.getPlatform());
    assertEquals("projectA", ldm.getProjectName());
    
    ProjectDependencyType lrtB = getB();
    
    ProjectDependencyMutant ldmB = ProjectDependencyMutant.convert(lrtB);
    assertEquals("platformB", ldmB.getPlatform());
    assertEquals("projectB", ldmB.getProjectName());
    
    List<ProjectDependencyType> asList = getListAB();
    
    List<ProjectDependencyMutant> convertedList = ProjectDependencyMutant.convert(asList);
    assertAB(this, convertedList);
  }
}

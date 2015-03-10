package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.LibraryDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependencyMutant;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryReferenceType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=LibraryDependency.class, minCoverage=80.0)
public class LibraryDependencyTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new LibraryDependency(null);
      }
    });
  }
  
  @Test
  public void testConstructorCopy() {
    LibraryDependencyMutant pdm = new LibraryDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setLibraryName("ln");
    
    assertEquals("platform", pdm.getPlatform());
    assertEquals("ln", pdm.getLibraryName());
    
    LibraryDependency pdm2 = new LibraryDependency(pdm);
    
    assertEquals("platform", pdm2.getPlatform());
    assertEquals("ln", pdm2.getLibraryName());
    
  }
  
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    LibraryDependencyMutant pdm = new LibraryDependencyMutant();
    pdm.setPlatform("platform");
    pdm.setLibraryName("ln");
    LibraryDependency pd = new LibraryDependency(pdm);
    
    LibraryDependencyMutant pdmA = new LibraryDependencyMutant();
    pdmA.setPlatform("platform");
    pdmA.setLibraryName("ln");
    LibraryDependency pdA = new LibraryDependency(pdmA);
    
    LibraryDependencyMutant pdm2 = new LibraryDependencyMutant();
    pdm2.setPlatform("platform");
    pdm2.setLibraryName("ln2");
    LibraryDependency pd2 = new LibraryDependency(pdm2);
    
    LibraryDependencyMutant pdm3 = new LibraryDependencyMutant();
    pdm3.setPlatform("platform3");
    pdm3.setLibraryName("ln");    
    LibraryDependency pd3 = new LibraryDependency(pdm3);
    
    LibraryDependencyMutant pdm4 = new LibraryDependencyMutant();
    pdm4.setLibraryName("ln");  
    LibraryDependency pd4 = new LibraryDependency(pdm4);
    
    LibraryDependencyMutant pdm5 = new LibraryDependencyMutant();
    pdm5.setPlatform("platform");  
    LibraryDependency pd5 = new LibraryDependency(pdm5);
    
    //LibraryDependency ld = new LibraryDependency()
    assertEquals(pd, pd);
    assertEquals(pd.hashCode(), pd.hashCode());
    assertEquals("LibraryDependency [name=ln,platform=platform]", pd.toString());
    
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
    assertEquals("LibraryDependency [name=ln2,platform=platform]", pd2.toString());
    assertEquals("LibraryDependency [name=ln,platform=platform3]", pd3.toString());
    
    assertNotEquals(pdA, pd2);
    assertNotEquals(pdA.hashCode(), pd2.hashCode());   
    assertNotEquals(pdA, pd3);
    assertNotEquals(pdA.hashCode(), pd3.hashCode());   
    assertNotEquals(pdA, pd4);
    assertNotEquals(pdA.hashCode(), pd4.hashCode()); 
    assertNotEquals(pdA, pd5);
    assertNotEquals(pdA.hashCode(), pd5.hashCode()); 
    assertEquals("LibraryDependency [name=ln]", pd4.toString());
    
    assertNotEquals(pd3, pdA);
    assertNotEquals(pd3.hashCode(), pdA.hashCode());   
    assertNotEquals(pd3, pd2);
    assertNotEquals(pd3.hashCode(), pd2.hashCode());   
    assertNotEquals(pd3, pd4);
    assertNotEquals(pd3.hashCode(), pd4.hashCode());  
    assertNotEquals(pd3, pd5);
    assertNotEquals(pd3.hashCode(), pd5.hashCode());  
    assertEquals("LibraryDependency [name=null,platform=platform]", pd5.toString());
    
  }
  
  @Test
  public void testStaticMethodsConvert() {
    LibraryReferenceType lrt = new LibraryReferenceType();
    lrt.setPlatform("platform");
    lrt.setValue("libA");
    
    LibraryDependency ldm = LibraryDependency.convert(lrt);
    assertEquals("platform", ldm.getPlatform());
    assertEquals("libA", ldm.getLibraryName());
    
    LibraryReferenceType lrtB = new LibraryReferenceType();
    lrtB.setPlatform("platformB");
    lrtB.setValue("libB");
    
    LibraryDependency ldmB = LibraryDependency.convert(lrtB);
    assertEquals("platformB", ldmB.getPlatform());
    assertEquals("libB", ldmB.getLibraryName());
    
    List<LibraryReferenceType> asList = new ArrayList<LibraryReferenceType>();
    asList.add(lrt);
    asList.add(lrtB);
    
    List<LibraryDependency> convertedList = LibraryDependency.convert(asList);
    ldm = convertedList.get(0);
    assertEquals("platform", ldm.getPlatform());
    assertEquals("libA", ldm.getLibraryName());
    
    ldmB = convertedList.get(1);
    assertEquals("platformB", ldmB.getPlatform());
    assertEquals("libB", ldmB.getLibraryName());
  }
}

package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate.models.dependencies.I_LibraryDependency;
import org.adligo.fabricate.models.dependencies.LibraryDependencyMutant;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryReferenceType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=LibraryDependencyMutant.class, minCoverage=80.0)
public class LibraryDependencyMutantTrial extends MockitoSourceFileTrial {

  public static void assertAB(I_Asserts asserts, List<? extends I_LibraryDependency> convertedList) {
    I_LibraryDependency ldm = convertedList.get(0);
    asserts.assertEquals("platform", ldm.getPlatform());
    asserts.assertEquals("libA", ldm.getLibraryName());
    
    I_LibraryDependency ldmB = convertedList.get(1);
    asserts.assertEquals("platformB", ldmB.getPlatform());
    asserts.assertEquals("libB", ldmB.getLibraryName());
  }

  public static List<LibraryReferenceType> getListAB() {
    LibraryReferenceType lrtA = getLibA();
    LibraryReferenceType lrtB = getLibB();
    
    List<LibraryReferenceType> asList = new ArrayList<LibraryReferenceType>();
    asList.add(lrtA);
    asList.add(lrtB);
    return asList;
  }

  public static LibraryReferenceType getLibB() {
    LibraryReferenceType lrtB = new LibraryReferenceType();
    lrtB.setPlatform("platformB");
    lrtB.setValue("libB");
    return lrtB;
  }

  public static LibraryReferenceType getLibA() {
    LibraryReferenceType lrt = new LibraryReferenceType();
    lrt.setPlatform("platform");
    lrt.setValue("libA");
    return lrt;
  }
  
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class), new I_Thrower() {
      
      @SuppressWarnings("unused")
      @Override
      public void run() throws Throwable {
        new LibraryDependencyMutant(null);
      }
    });
  }
  
  @Test
  public void testConstructorCopy() {
    LibraryDependencyMutant ldm = new LibraryDependencyMutant();
    ldm.setPlatform("platform");
    ldm.setLibraryName("ln");
    
    assertEquals("platform", ldm.getPlatform());
    assertEquals("ln", ldm.getLibraryName());
    
    LibraryDependencyMutant ldm2 = new LibraryDependencyMutant(ldm);
    
    assertEquals("platform", ldm2.getPlatform());
    assertEquals("ln", ldm2.getLibraryName());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    LibraryDependencyMutant ldm = new LibraryDependencyMutant();
    ldm.setPlatform("platform");
    ldm.setLibraryName("ln");
    
    LibraryDependencyMutant ldmA = new LibraryDependencyMutant();
    ldmA.setPlatform("platform");
    ldmA.setLibraryName("ln");
    
    LibraryDependencyMutant ldm2 = new LibraryDependencyMutant();
    ldm2.setPlatform("platform");
    ldm2.setLibraryName("ln2");
    
    LibraryDependencyMutant ldm3 = new LibraryDependencyMutant();
    ldm3.setPlatform("platform3");
    ldm3.setLibraryName("ln");    
    
    LibraryDependencyMutant ldm4 = new LibraryDependencyMutant();
    ldm4.setLibraryName("ln");  
    
    LibraryDependencyMutant ldm5 = new LibraryDependencyMutant();
    ldm5.setPlatform("platform");  
    
    assertEquals(ldm, ldm);
    assertEquals(ldm.hashCode(), ldm.hashCode());
    assertEquals("LibraryDependencyMutant [name=ln,platform=platform]", ldm.toString());
    
    assertEquals(ldm, ldmA);
    assertEquals(ldm.hashCode(), ldmA.hashCode());   
    
    assertNotEquals(ldm, ldm2);
    assertNotEquals(ldm.hashCode(), ldm2.hashCode());   
    assertNotEquals(ldm, ldm3);
    assertNotEquals(ldm.hashCode(), ldm3.hashCode());   
    assertNotEquals(ldm, ldm4);
    assertNotEquals(ldm.hashCode(), ldm4.hashCode()); 
    assertNotEquals(ldm, ldm5);
    assertNotEquals(ldm.hashCode(), ldm5.hashCode()); 
    assertEquals("LibraryDependencyMutant [name=ln2,platform=platform]", ldm2.toString());
    assertEquals("LibraryDependencyMutant [name=ln,platform=platform3]", ldm3.toString());
    
    assertNotEquals(ldmA, ldm2);
    assertNotEquals(ldmA.hashCode(), ldm2.hashCode());   
    assertNotEquals(ldmA, ldm3);
    assertNotEquals(ldmA.hashCode(), ldm3.hashCode());   
    assertNotEquals(ldmA, ldm4);
    assertNotEquals(ldmA.hashCode(), ldm4.hashCode()); 
    assertNotEquals(ldmA, ldm5);
    assertNotEquals(ldmA.hashCode(), ldm5.hashCode()); 
    assertEquals("LibraryDependencyMutant [name=ln]", ldm4.toString());
    
    assertNotEquals(ldm3, ldmA);
    assertNotEquals(ldm3.hashCode(), ldmA.hashCode());   
    assertNotEquals(ldm3, ldm2);
    assertNotEquals(ldm3.hashCode(), ldm2.hashCode());   
    assertNotEquals(ldm3, ldm4);
    assertNotEquals(ldm3.hashCode(), ldm4.hashCode());  
    assertNotEquals(ldm3, ldm5);
    assertNotEquals(ldm3.hashCode(), ldm5.hashCode());  
    assertEquals("LibraryDependencyMutant [name=null,platform=platform]", ldm5.toString());
    
  }
  
  @Test
  public void testStaticMethodsConvert() {
    LibraryReferenceType lrt = getLibA();
    
    LibraryDependencyMutant ldm = LibraryDependencyMutant.convert(lrt);
    assertEquals("platform", ldm.getPlatform());
    assertEquals("libA", ldm.getLibraryName());
    
    LibraryReferenceType lrtB = getLibB();
    
    LibraryDependencyMutant ldmB = LibraryDependencyMutant.convert(lrtB);
    assertEquals("platformB", ldmB.getPlatform());
    assertEquals("libB", ldmB.getLibraryName());
    
    List<LibraryReferenceType> asList = getListAB();
    
    List<LibraryDependencyMutant> convertedList = LibraryDependencyMutant.convert(asList);
    assertAB(this, convertedList);
  }


}

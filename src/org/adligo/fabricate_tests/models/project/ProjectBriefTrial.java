package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.project.I_ProjectBrief;
import org.adligo.fabricate.models.project.ProjectBrief;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.ProjectType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ProjectBrief.class, minCoverage=86.0)
public class ProjectBriefTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorCopy() {
    ProjectBrief brief = new ProjectBrief("name", "version");
    ProjectBrief copy = new ProjectBrief(brief);
    assertEquals("name", copy.getName());
    assertEquals("version", copy.getVersion());
  }
  
  @Test
  public void testConstructorCopyXml() {
    ProjectType type = new ProjectType();
    type.setName("name2");
    type.setVersion("version2");
    
    ProjectBrief copy = new ProjectBrief(type);
    assertEquals("name2", copy.getName());
    assertEquals("version2", copy.getVersion());
  }
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
      
          @Override
          public void run() throws Throwable {
            new ProjectBrief(null, null);
          }
        });
    
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            ProjectType pt = null;
            new ProjectBrief(pt);
          }
        });
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            I_ProjectBrief pb = null;
            new ProjectBrief(pb);
          }
        });
  }
  
  @Test
  public void testConstructorNameVersion() {
    ProjectBrief brief = new ProjectBrief("name", "version");
    assertEquals("name", brief.getName());
    assertEquals("version", brief.getVersion());
    assertEquals("", brief.getDomainName());
    assertEquals("name", brief.getShortName());
    
    ProjectBrief brief2 = new ProjectBrief("name.adligo.org", "version");
    assertEquals("name.adligo.org", brief2.getName());
    assertEquals("version", brief2.getVersion());
    assertEquals("adligo.org", brief2.getDomainName());
    assertEquals("name", brief2.getShortName());
    
    ProjectBrief brief3 = new ProjectBrief("name.adligo.org", null);
    assertEquals("name.adligo.org", brief3.getName());
    assertEquals("", brief3.getVersion());
    assertEquals("adligo.org", brief3.getDomainName());
    assertEquals("name", brief3.getShortName());
  }
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsEqualsHashCodeAndToString() {
    ProjectBrief brief = new ProjectBrief("name", "version");
    ProjectBrief briefA = new ProjectBrief("name", "version");
    ProjectBrief brief2 = new ProjectBrief("name2", "version");
    ProjectBrief brief3 = new ProjectBrief("name", "version3");
    
    assertEquals(brief,brief);
    assertNotEquals(brief,null);
    assertEquals(brief.hashCode(), brief.hashCode());
    assertEquals("ProjectBrief [name=name, version=version]",brief.toString());
    assertEquals(brief,briefA);
    assertEquals(brief.hashCode(), briefA.hashCode());
    assertEquals(briefA, brief);
    assertEquals(briefA.hashCode(), brief.hashCode());
    assertEquals("ProjectBrief [name=name, version=version]",briefA.toString());
    
    assertNotEquals(brief,brief2);
    assertNotEquals(brief.hashCode(), brief2.hashCode());
    assertNotEquals(brief,brief3);
    assertNotEquals(brief.hashCode(), brief3.hashCode());
    
    assertNotEquals(brief2, brief);
    assertNotEquals(brief2.hashCode(), brief.hashCode());
    assertEquals("ProjectBrief [name=name2, version=version]", brief2.toString());
    assertNotEquals(brief3, brief);
    assertNotEquals(brief3.hashCode(), brief.hashCode());
    assertEquals("ProjectBrief [name=name, version=version3]", brief3.toString());
  }
}

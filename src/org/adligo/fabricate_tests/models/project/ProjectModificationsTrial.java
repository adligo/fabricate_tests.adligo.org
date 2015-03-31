package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.project.I_ProjectModifications;
import org.adligo.fabricate.models.project.ProjectModifications;
import org.adligo.fabricate.models.project.ProjectModificationsMutant;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=ProjectModifications.class,minCoverage=100.00)
public class ProjectModificationsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("name")),
        new I_Thrower() {
          
          @SuppressWarnings("unused")
          @Override
          public void run() throws Throwable {
            new ProjectModifications(new ProjectModificationsMutant());
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorNullLists() {
    I_ProjectModifications projModsMock = mock(I_ProjectModifications.class);
    when(projModsMock.getAdditions()).thenReturn(null);
    when(projModsMock.getDeletions()).thenReturn(null);
    when(projModsMock.getModifications()).thenReturn(null);
    when(projModsMock.getName()).thenReturn("projectName");
    
    ProjectModifications projMods = new ProjectModifications(projModsMock);
    List<String> adds = projMods.getAdditions();
    assertEquals(0, adds.size());
    assertEquals("java.util.Collections$EmptyList", adds.getClass().getName());
    
    List<String> dels = projMods.getDeletions();
    assertEquals(0, dels.size());
    assertEquals("java.util.Collections$EmptyList", dels.getClass().getName());
    
    List<String> mods = projMods.getModifications();
    assertEquals(0, mods.size());
    assertEquals("java.util.Collections$EmptyList", mods.getClass().getName());
    
    assertEquals("projectName", projMods.getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorEmptyLists() {
    ProjectModificationsMutant projModsMut = new ProjectModificationsMutant();
    projModsMut.setName("projectName");
    
    ProjectModifications projMods = new ProjectModifications(projModsMut);
    List<String> adds = projMods.getAdditions();
    assertEquals(0, adds.size());
    assertEquals("java.util.Collections$EmptyList", adds.getClass().getName());
    
    List<String> dels = projMods.getDeletions();
    assertEquals(0, dels.size());
    assertEquals("java.util.Collections$EmptyList", dels.getClass().getName());
    
    List<String> mods = projMods.getModifications();
    assertEquals(0, mods.size());
    assertEquals("java.util.Collections$EmptyList", mods.getClass().getName());
    
    assertEquals("projectName", projMods.getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorPopulatedLists() {
    List<String> addsIn = new ArrayList<String>();
    addsIn.add(null);
    addsIn.add("a1");
    addsIn.add("a2");
    addsIn.add("a1");
    
    List<String> delsIn = new ArrayList<String>();
    delsIn.add(null);
    delsIn.add("d1");
    delsIn.add("d2");
    delsIn.add("d1");
    
    List<String> modsIn = new ArrayList<String>();
    modsIn.add(null);
    modsIn.add("m1");
    modsIn.add("m2");
    modsIn.add("m1");
    
    I_ProjectModifications projModsMock = mock(I_ProjectModifications.class);
    when(projModsMock.getAdditions()).thenReturn(addsIn);
    when(projModsMock.getDeletions()).thenReturn(delsIn);
    when(projModsMock.getModifications()).thenReturn(modsIn);
    when(projModsMock.getName()).thenReturn("projectName");
    
    ProjectModifications projMods = new ProjectModifications(projModsMock);
    List<String> adds = projMods.getAdditions();
    assertEquals("a1", adds.get(0));
    assertEquals("a2", adds.get(1));
    assertEquals(2, adds.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", adds.getClass().getName());
    
    List<String> dels = projMods.getDeletions();
    assertEquals("d1", dels.get(0));
    assertEquals("d2", dels.get(1));
    assertEquals(2, dels.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", dels.getClass().getName());
    
    List<String> mods = projMods.getModifications();
    assertEquals("m1", mods.get(0));
    assertEquals("m2", mods.get(1));
    assertEquals(2, mods.size());
    assertEquals("java.util.Collections$UnmodifiableRandomAccessList", mods.getClass().getName());
    
    assertEquals("projectName", projMods.getName());
  }
}

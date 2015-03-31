package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.project.ProjectModificationsMutant;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SourceFileScope (sourceClass=ProjectModificationsMutant.class,minCoverage=100.00)
public class ProjectModificationsMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethodsGetsAndAdds() {
    ProjectModificationsMutant mutant = new ProjectModificationsMutant();
    mutant.addAddition(null);
    mutant.addAddition("a1");
    mutant.addAddition("a2");
    mutant.addAddition("a1");
    
    mutant.addDeletion(null);
    mutant.addDeletion("d1");
    mutant.addDeletion("d2");
    mutant.addDeletion("d1");
    
    mutant.addModification(null);
    mutant.addModification("m1");
    mutant.addModification("m2");
    mutant.addModification("m1");
    
    assertAdditions(mutant);
    assertDeletions(mutant);
    assertModifications(mutant);
  }

  
  @Test
  public void testMethodsGetsAndSets() {
    ProjectModificationsMutant mutant = new ProjectModificationsMutant();
    List<String> adds = new ArrayList<String>();
    adds.add(null);
    adds.add("a1");
    adds.add("a2");
    adds.add("a1");
    mutant.setAdditions(null);
    mutant.setAdditions(Collections.emptyList());
    mutant.setAdditions(adds);
    
    List<String> dels = new ArrayList<String>();
    dels.add(null);
    dels.add("d1");
    dels.add("d2");
    dels.add("d1");
    mutant.setDeletions(null);
    mutant.setDeletions(Collections.emptyList());
    mutant.setDeletions(dels);
    
    List<String> mods = new ArrayList<String>();
    mods.add(null);
    mods.add("m1");
    mods.add("m2");
    mods.add("m1");
    mutant.setModifications(null);
    mutant.setModifications(Collections.emptyList());
    mutant.setModifications(mods);
    
    mutant.setName("projectName");
    
    assertAdditions(mutant);
    assertDeletions(mutant);
    assertModifications(mutant);
    assertEquals("projectName", mutant.getName());
  }
  
  @SuppressWarnings("boxing")
  private void assertAdditions(ProjectModificationsMutant mutant) {
    List<String> adds = mutant.getAdditions();
    assertEquals("a1", adds.get(0));
    assertEquals("a2", adds.get(1));
    assertEquals(2, adds.size());
    //check encapsulation
    adds.clear();
    adds = mutant.getAdditions();
    assertEquals("a1", adds.get(0));
    assertEquals("a2", adds.get(1));
    assertEquals(2, adds.size());
  }
  
  @SuppressWarnings("boxing")
  private void assertDeletions(ProjectModificationsMutant mutant) {
    List<String> dels = mutant.getDeletions();
    assertEquals("d1", dels.get(0));
    assertEquals("d2", dels.get(1));
    assertEquals(2, dels.size());
    //check encapsulation
    dels.clear();
    dels = mutant.getDeletions();
    assertEquals("d1", dels.get(0));
    assertEquals("d2", dels.get(1));
    assertEquals(2, dels.size());
  }


  @SuppressWarnings("boxing")
  private void assertModifications(ProjectModificationsMutant mutant) {
    List<String> mods = mutant.getModifications();
    assertEquals("m1", mods.get(0));
    assertEquals("m2", mods.get(1));
    assertEquals(2, mods.size());
    //check encapsulation
    mods.clear();
    mods = mutant.getModifications();
    assertEquals("m1", mods.get(0));
    assertEquals("m2", mods.get(1));
    assertEquals(2, mods.size());
  }
}

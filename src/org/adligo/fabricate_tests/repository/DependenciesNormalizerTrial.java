package org.adligo.fabricate_tests.repository;

import org.adligo.fabricate.models.dependencies.DependencyMutant;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.repository.DependenciesNormalizer;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@SourceFileScope (sourceClass=DependenciesNormalizer.class, minCoverage=31.0)
public class DependenciesNormalizerTrial extends MockitoSourceFileTrial {
  
  @SuppressWarnings("boxing")
  @Test
  public void testAddDupes() {
    DependenciesNormalizer dn = new DependenciesNormalizer();
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifact");
    dmA.setFileName("fileName");
    dmA.setGroup("group");
    dmA.setType("jar");
    dmA.setVersion("version");
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifactB");
    dmB.setFileName("fileNameB");
    dmB.setGroup("groupB");
    dmB.setType("jarB");
    dmB.setVersion("versionB");
    
    List<I_Dependency> deps = new ArrayList<I_Dependency>();
    deps.add(dmA);
    deps.add(dmB);
    deps.add(dmA);
    deps.add(dmB);
    
    dn.add(deps);

    ConcurrentLinkedQueue<I_Dependency> depsQueue = dn.get();
    assertEquals(2, depsQueue.size());
    assertSame(dmB, depsQueue.poll());
    assertSame(dmA, depsQueue.poll());
    
  }  

  @SuppressWarnings("boxing")
  @Test
  public void testAddNoExtractExtract() {
    
    DependenciesNormalizer dn = new DependenciesNormalizer();
    
    DependencyMutant dmA = new DependencyMutant();
    dmA.setArtifact("artifact");
    dmA.setFileName("fileName");
    dmA.setGroup("group");
    dmA.setType("jar");
    dmA.setVersion("version");
    
    DependencyMutant dmB = new DependencyMutant();
    dmB.setArtifact("artifactB");
    dmB.setFileName("fileNameB");
    dmB.setGroup("groupB");
    dmB.setType("jarB");
    dmB.setVersion("versionB");
    
    List<I_Dependency> deps = new ArrayList<I_Dependency>();
    deps.add(dmA);
    deps.add(dmB);
    DependencyMutant dmC = new DependencyMutant(dmA);
    dmC.setExtract(true);
    deps.add(dmC);
    DependencyMutant dmD = new DependencyMutant(dmB);
    dmD.setExtract(true);
    deps.add(dmD);
    
    dn.add(deps);

    ConcurrentLinkedQueue<I_Dependency> depsQueue = dn.get();
    assertEquals(2, depsQueue.size());
    assertSame(dmD, depsQueue.poll());
    assertSame(dmC, depsQueue.poll());
    
  }  
}

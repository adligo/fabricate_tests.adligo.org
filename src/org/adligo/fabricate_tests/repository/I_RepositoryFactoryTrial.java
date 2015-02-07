package org.adligo.fabricate_tests.repository;

import org.adligo.fabricate.repository.I_RepositoryFactory;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=I_RepositoryFactory.class,
  minCoverage=100.0, 
  allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class I_RepositoryFactoryTrial extends MockitoSourceFileTrial {

}

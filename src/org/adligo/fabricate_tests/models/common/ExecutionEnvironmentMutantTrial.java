package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.ExecutionEnvironmentMutant;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ExecutionEnvironmentMutant.class, minCoverage=0.0,
allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class ExecutionEnvironmentMutantTrial extends MockitoSourceFileTrial {

}

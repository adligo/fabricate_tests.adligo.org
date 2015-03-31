package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.routines.I_RoutineBuilder;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=I_RoutineBuilder.class,minCoverage=100.0,
allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class I_RoutineBuilderTrial extends MockitoSourceFileTrial {

}

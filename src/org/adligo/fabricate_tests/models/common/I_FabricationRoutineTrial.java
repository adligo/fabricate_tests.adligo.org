package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.I_FabricationRoutine;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=I_FabricationRoutine.class,minCoverage=100.00,
    allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class I_FabricationRoutineTrial extends MockitoSourceFileTrial {

}

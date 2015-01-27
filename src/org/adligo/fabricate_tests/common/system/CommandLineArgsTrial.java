package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.CommandLineArgs;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Map;
import java.util.Set;

@SourceFileScope (sourceClass=CommandLineArgs.class, minCoverage=71.0)
public class CommandLineArgsTrial extends MockitoSourceFileTrial {

  @Test
  public void testIO() throws Exception {
   Map<String,String> args =  CommandLineArgs.parseArgs(new String[] {"-vd"});
   Set<String> keys =  args.keySet();
   assertContains(keys,"-v");
   assertContains(keys,"-d");
  }
}

package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.CommandLineEnConstants;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=CommandLineEnConstants.class)
public class CommandLineEnConstantsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    CommandLineEnConstants messages = CommandLineEnConstants.INSTANCE;
    assertEquals("cmd", messages.getCommand());
    assertEquals("-a", messages.getArchive(true));
    assertEquals("--archive", messages.getArchive(false));
    assertEquals("-c", messages.getConfirmRepositoryIntegrity(true));
    assertEquals("--confirm-repository-integrity", messages.getConfirmRepositoryIntegrity(false));
    assertEquals("-d", messages.getDevelopment(true));
    assertEquals("--development", messages.getDevelopment(false));
    assertEquals("-g", messages.getGrowBranches(true));
    assertEquals("--grow-branches", messages.getGrowBranches(false));
    
    assertEquals("-l", messages.getLog(true));
    assertEquals("--log-verbosely", messages.getLog(false));
    assertEquals("-m", messages.getMarkVersions(true));
    assertEquals("--mark-versions", messages.getMarkVersions(false));
    assertEquals("-r", messages.getRebuildDependents(true));
    assertEquals("--rebuild-dependents", messages.getRebuildDependents(false));
    assertEquals("-s", messages.getShare(true));
    assertEquals("--share", messages.getShare(false));
    assertEquals("skip", messages.getSkip());
    assertEquals("stages", messages.getStages());
    assertEquals("-t", messages.getTest(true));
    assertEquals("--test", messages.getTest(false));
    assertEquals("-u", messages.getUpdate(true));
    assertEquals("--update", messages.getUpdate(false));
    assertEquals("-v", messages.getVersion(true));
    assertEquals("--version", messages.getVersion(false));
    assertEquals("-w", messages.getWriteLog(true));
    assertEquals("--write-log", messages.getWriteLog(false));
    
    assertEquals("-a", messages.getAlias("--archive"));
    assertEquals("-c", messages.getAlias("--confirm-repository-integrity"));
    assertEquals("-d", messages.getAlias("--development"));
    assertEquals("-g", messages.getAlias("--grow-branches"));
    
    assertEquals("-l", messages.getAlias("--log-verbosely"));
    assertEquals("-m", messages.getAlias("--mark-versions"));
    assertEquals("-r", messages.getAlias("--rebuild-dependents"));
    assertEquals("-s", messages.getAlias("--share"));
    assertEquals("-t", messages.getAlias("--test"));
    
    assertEquals("-u", messages.getAlias("--update"));
    assertEquals("-v", messages.getAlias("--version"));
    assertEquals("-w", messages.getAlias("--write-log"));
  }
  
}

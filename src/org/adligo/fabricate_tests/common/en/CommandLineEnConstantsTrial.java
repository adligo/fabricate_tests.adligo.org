package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.CommandLineEnConstants;
import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=CommandLineEnConstants.class)
public class CommandLineEnConstantsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    CommandLineEnConstants messages = CommandLineEnConstants.INSTANCE;
    assertEquals("cmd", messages.getCommand());
    assertEquals("-c", messages.getConfirmRepositoryIntegrity(true));
    assertEquals("--confirm-repository-integrity", messages.getConfirmRepositoryIntegrity(false));
    assertEquals("-d", messages.getDevelopment(true));
    assertEquals("--development", messages.getDevelopment(false));
    assertEquals("-l", messages.getLog(true));
    assertEquals("--log-verbosely", messages.getLog(false));
    assertEquals("-r", messages.getRebuildDependents(true));
    assertEquals("--rebuild-dependents", messages.getRebuildDependents(false));
    assertEquals("-u", messages.getUpdate(true));
    assertEquals("--update", messages.getUpdate(false));
    assertEquals("-v", messages.getVersion(true));
    assertEquals("--version", messages.getVersion(false));
    
    assertEquals("-c", messages.getAlias("--confirm-repository-integrity"));
    assertEquals("-d", messages.getAlias("--development"));
    assertEquals("-l", messages.getAlias("--log-verbosely"));
    assertEquals("-r", messages.getAlias("--rebuild-dependents"));
    assertEquals("-u", messages.getAlias("--update"));
    assertEquals("-v", messages.getAlias("--version"));
  }
  
}

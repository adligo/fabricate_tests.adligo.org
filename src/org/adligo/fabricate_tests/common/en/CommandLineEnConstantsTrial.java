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
    assertEquals("-a", messages.getArchive(true));
    assertEquals("--archive", messages.getArchive(false));
    assertEquals("cmd", messages.getCommand());
    assertEquals("-c", messages.getConfirmRepositoryIntegrity(true));
    assertEquals("--confirm-repository-integrity", messages.getConfirmRepositoryIntegrity(false));
    assertEquals("-d", messages.getDevelopment(true));
    assertEquals("--development", messages.getDevelopment(false));
    
    assertEquals("-l", messages.getLog(true));
    assertEquals("--log-verbosely", messages.getLog(false));
    assertEquals("-n", messages.getNoSshKeystorePassPhrase(true));
    assertEquals("--no-ssh-keystore-passphrase", messages.getNoSshKeystorePassPhrase(false));
    assertEquals("-p", messages.getPurge(true));
    assertEquals("--purge", messages.getPurge(false));
    
    assertEquals("-r", messages.getRebuildDependents(true));
    assertEquals("--rebuild-dependents", messages.getRebuildDependents(false));

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
    
    assertEquals("-l", messages.getAlias("--log-verbosely"));
    assertEquals("-n", messages.getAlias("--no-ssh-keystore-passphrase"));
    assertEquals("-p", messages.getAlias("--purge"));
    assertEquals("-r", messages.getAlias("--rebuild-dependents"));
    assertEquals("-t", messages.getAlias("--test"));
    
    assertEquals("-u", messages.getAlias("--update"));
    assertEquals("-v", messages.getAlias("--version"));
    assertEquals("-w", messages.getAlias("--write-log"));
  }
  
}

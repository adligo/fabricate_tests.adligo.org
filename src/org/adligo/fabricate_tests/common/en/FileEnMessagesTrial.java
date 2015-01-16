package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.en.ProjectEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

@SourceFileScope (sourceClass=FileEnMessages.class)
public class FileEnMessagesTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    FileEnMessages messages = FileEnMessages.INSTANCE;
    asserter.assertConstant("did NOT match the following pattern;", 
        messages.getDidNotMatchedTheFollowingPattren());
    asserter.assertConstant("Excludes: ", 
        messages.getExcludes());
    asserter.assertConstant("Includes: ", 
        messages.getIncludes());
    asserter.assertConstant("File matching patterns may not be empty.", 
        messages.getFileMatchingPatternsMayNotBeEmpty());
    
    asserter.assertConstant("matched the following pattern;", 
        messages.getMatchedTheFollowingPattern());
    
    asserter.assertConstant("The following file;", 
        messages.getTheFollowingFile());
    asserter.assertConstant("The wildcard character (*) is not allowed in the middle of a file matching pattern file name.", 
        messages.getTheWildCardCharacterIsNotAllowedInMiddleFileName());
    asserter.assertConstant("The wildcard character (*) is not allowed at the left or middle of a file matching pattern directory path.", 
        messages.getTheWildCardCharacterIsNotAllowedAtTheLeftOrMiddleDirectoryPath());
    asserter.assertConstantsMatchMethods(FileEnMessages.class);
  }
}

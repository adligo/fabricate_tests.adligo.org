package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.GitEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

@SourceFileScope (sourceClass=GitEnMessages.class)
public class GitEnMessagesTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    
    GitEnMessages messages = GitEnMessages.INSTANCE;
    
    
    asserter.assertConstant("Cancel", 
        messages.getCancel());
    asserter.assertConstant("Default", 
        messages.getDefault());
    
    asserter.assertConstant("Discovered <X/> projects.", 
        messages.getDiscoveredXProjects());
    asserter.assertConstant("Finished git checkout for the following project;", 
        messages.getFinishedGitCheckoutForTheFollowingProject());
    asserter.assertConstant("Finished git clone for the following project;", 
        messages.getFinishedGitCloneForTheFollowingProject());
    asserter.assertConstant("Finished git commit for the following project;", 
        messages.getFinishedGitCommitForTheFollowingProject());
    asserter.assertConstant("Finished git pull for the following project;", 
        messages.getFinishedGitPullForTheFollowingProject());
    asserter.assertConstant("Finished git push for the following project;", 
        messages.getFinishedGitPushForTheFollowingProject());
    asserter.assertConstant("Finished git stage.", 
        messages.getFinishedGitStage());
    
    
    asserter.assertConstant("Ok", 
        messages.getOk());
    
    asserter.assertConstant("Please enter your commit message for project <X/>.", 
        messages.getPleaseEnterYourCommitMessageForProjectX());
    asserter.assertConstant("Please enter your default commit message.", 
        messages.getPleaseEnterYourDefaultCommitMessage());
    
    asserter.assertConstant("The following project has the version '<X/>' in fabricate.xml but "
        + "is not checked out to that version aborting fabrication;", 
        messages.getTheFollowingProjectHasAVersionXInFabricatXmlButIsNotCheckedOutToThatVersionAborting());
    asserter.assertConstant("The project directory is as follows;", 
        messages.getTheProjectDirectoryIs());
    
    asserter.assertConstant("Select All", 
        messages.getSelectAll());
    asserter.assertConstant("Select None", 
        messages.getSelectNone());
    
    asserter.assertConstant("Started git checkout for the following project;", 
        messages.getStartedGitCheckoutForTheFollowingProject());
    asserter.assertConstant("Started git clone for the following project;", 
        messages.getStartedGitCloneForTheFollowingProject());
    asserter.assertConstant("Started git commit for the following project;", 
        messages.getStartedGitCommitForTheFollowingProject());
    asserter.assertConstant("Started git pull for the following project;", 
        messages.getStartedGitPullForTheFollowingProject());
    asserter.assertConstant("Started git push for the following project;", 
        messages.getStartedGitPushForTheFollowingProject());
    
    asserter.assertConstantsMatchMethods(GitEnMessages.class);
  }
}

package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

@SourceFileScope (sourceClass=SystemEnMessages.class)
public class SystemEnMessagesTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    SystemEnMessages messages = SystemEnMessages.INSTANCE;
    asserter.assertConstant("AntHelper requires a directory argument.", 
        messages.getAntHelperRequiresADirectoryArgument());
    asserter.assertConstant("Archive stage <X/> is still running.", 
        messages.getArchiveStageXIsStillRunning());
    asserter.assertConstant("Archive stage <X/> is still running on project <Z/>.", 
        messages.getArchiveStageXIsStillRunningOnProjectZ());
    asserter.assertConstant("Archive stage <X/>, task <Y/> is still running.", 
        messages.getArchiveStageXTaskYIsStillRunning());
    asserter.assertConstant("Archive stage <X/>, task <Y/> is still running on project <Z/>.", 
        messages.getArchiveStageXTaskYIsStillRunningOnProjectZ());
    asserter.assertConstant("Archive stage <X/> is still setting up.", 
        messages.getArchiveStageXIsStillSettingUp());

    asserter.assertConstant("Build stage <X/> is still running.", 
        messages.getBuildStageXIsStillRunning());
    asserter.assertConstant("Build stage <X/> is still running on project <Z/>.", 
        messages.getBuildStageXIsStillRunningOnProjectZ());
    asserter.assertConstant("Build stage <X/>, task <Y/> is still running.", 
        messages.getBuildStageXTaskYIsStillRunning());
    asserter.assertConstant("Build stage <X/>, task <Y/> is still running on project <Z/>.", 
        messages.getBuildStageXTaskYIsStillRunningOnProjectZ());
    asserter.assertConstant("Build stage <X/> is still setting up.", 
        messages.getBuildStageXIsStillSettingUp());
    
    asserter.assertConstant("Building Fabricate runtime class path.", 
        messages.getBuildingFabricateRuntimeClassPath());
    
    asserter.assertConstant("Command <X/> is still running.", 
        messages.getCommandXIsStillRunning());
    asserter.assertConstant("Command <X/> is still running on project <Z/>.", 
        messages.getCommandXIsStillRunningOnProjectZ());
    asserter.assertConstant("Command <X/>, task <Y/> is still running.", 
        messages.getCommandXTaskYIsStillRunning());
    asserter.assertConstant("Command <X/>, task <Y/> is still running on project <Z/>.", 
        messages.getCommandXTaskYIsStillRunningOnProjectZ());
    asserter.assertConstant("Command <X/> is still setting up.", 
        messages.getCommandXIsStillSettingUp());
    
    asserter.assertConstant("Checking Fabricate runtime dependencies.", 
        messages.getCheckingFabricateRuntimeDependencies());
    asserter.assertConstant("Compiled on <X/>.", 
        messages.getCompiledOnX());
    
    asserter.assertConstant("did not pass the extract check.", 
        messages.getDidNotPassTheExtractCheck());
    asserter.assertConstant("did not pass the md5 check.", 
        messages.getDidNotPassTheMd5Check());
    
    asserter.assertConstant("Duration was <X/> milliseconds.", 
        messages.getDurationWasXMilliseconds());
    asserter.assertConstant("Duration was <X/> minutes.", 
        messages.getDurationWasXMinutes());
    asserter.assertConstant("Duration was <X/> seconds.", 
        messages.getDurationWasXSeconds());
    
    asserter.assertConstant("Exception: There was a problem executing java with the following $JAVA_HOME;", 
        messages.getExceptionExecutingJavaWithTheFollowingJavaHome());
    asserter.assertConstant("Exception: Fabricate requires Java 1.7 or greater.", 
        messages.getExceptionFabricateRequiresJava1_7OrGreater());
    asserter.assertConstant("Exception: Java version parameter expected.", 
        messages.getExceptionJavaVersionParameterExpected());
    asserter.assertConstant("Exception: No $FABRICATE_HOME environment variable set.", 
        messages.getExceptionNoFabricateHomeSet());
    asserter.assertConstant("Exception: No fabricate_*.jar in $FABRICATE_HOME/lib for the following $FABRICATE_HOME;", 
        messages.getExceptionNoFabricateJarInFabricateHomeLib());
    asserter.assertConstant("Exception: No fabricate.xml or project.xml found.", 
        messages.getExceptionNoFabricateXmlOrProjectXmlFound());
    asserter.assertConstant("Exception: No $JAVA_HOME environment variable set.", 
        messages.getExceptionNoJavaHomeSet());
    asserter.assertConstant("Exception: No start time argument.", 
        messages.getExceptionNoStartTimeArg());
    asserter.assertConstant("Exception: Unable to find SSH_AGENT_PID when parsing output of ssh-agent.", 
        messages.getExceptionUnableToFindSSH_AGENT_PIDWhenParsingOutputOfSshAgent());
    asserter.assertConstant("Exception: Unable to find SSH_AUTH_SOCK when parsing output of ssh-agent.", 
        messages.getExceptionUnableToFindSSH_AUTH_SOCKWhenParsingOutputOfSshAgent());
    
    asserter.assertConstant("Extracting the following artifact;", 
        messages.getExtractingTheFollowingArtifact());
    asserter.assertConstant("Extraction of the following artifact;", 
        messages.getExtractionOfTheFollowingArtifact());
    
    asserter.assertConstant("Fabricate by Adligo.", 
        messages.getFabricateByAdligo());
    asserter.assertConstant("Fabricating...", 
        messages.getFabricating());
    asserter.assertConstant("Fabricate appears to already be running ", 
        messages.getFabricateAppearsToBeAlreadyRunning());
    asserter.assertConstant("(run.marker is in the same directory as fabricate.xml).", 
        messages.getFabricateAppearsToBeAlreadyRunningPartTwo());
    asserter.assertConstant("Fabrication failed!", 
        messages.getFabricationFailed());
    asserter.assertConstant("Fabrication successful!", 
        messages.getFabricationSuccessful());
    
    asserter.assertConstant("Facet <X/> is still running.", 
        messages.getFacetXIsStillRunning());
    asserter.assertConstant("Facet <X/> is still running on project <Z/>.", 
        messages.getFacetXIsStillRunningOnProjectZ());
    asserter.assertConstant("Facet <X/>, task <Y/> is still running.", 
        messages.getFacetXTaskYIsStillRunning());
    asserter.assertConstant("Facet <X/>, task <Y/> is still running on project <Z/>.", 
        messages.getFacetXTaskYIsStillRunningOnProjectZ());
    asserter.assertConstant("Facet <X/> is still setting up.", 
        messages.getFacetXIsStillSettingUp());
    
    asserter.assertConstant("failed!", 
        messages.getFailed());
    asserter.assertConstant("finished.", 
        messages.getFinished());
    asserter.assertConstant("Finished git clone on project <X/>.", 
        messages.getFinishedGetCloneOnProjectX());
    asserter.assertConstant("Finished git pull on project <X/>.", 
        messages.getFinishedGetPullOnProjectX());
    asserter.assertConstant("Git does not appear to be installed please install it.", 
        messages.getGitDoesNotAppearToBeInstalledPleaseInstallIt());
    
    asserter.assertConstant("Instead of the following actual generic type;", 
        messages.getInsteadOfTheFollowingActualGenericType());
    asserter.assertConstant("It was expected to implement the following interface;", 
        messages.getItWasExpectedToImplementTheFollowingInterface());
    
    asserter.assertConstant("Locks may only be set on or under the caller's package.", 
        messages.getLocksMayOnlyBeSetOnOrUnderTheCallersPackage());
    asserter.assertConstant("Locks must contain at least one allowed caller.", 
        messages.getLocksMustContainAtLeastOneAllowedCaller());
    
    asserter.assertConstant("Managing the following local repository;", 
        messages.getManagingTheFollowingLocalRepository());
    asserter.assertConstant("Managing Fabricate runtime class path dependencies.", 
        messages.getManagingFabricateRuntimeClassPathDependencies());
    asserter.assertConstant("No remote repositories could be reached.", 
        messages.getNoRemoteRepositoriesCouldBeReached());
    asserter.assertConstant("passed the extract check.", 
        messages.getPassedTheExtractCheck());
    asserter.assertConstant("passed the md5 check.", 
        messages.getPassedTheMd5Check());
    asserter.assertConstant("Projects are located in the following directory;", 
        messages.getProjectsAreLocatedInTheFollowingDirectory());
    
    asserter.assertConstant("Sending opts to script.", 
        messages.getSendingOptsToScript());
    asserter.assertConstant("Starting download from the following url;", 
        messages.getStartingDownloadFromTheFollowingUrl());
    asserter.assertConstant("Starting git clone on project <X/>.", 
        messages.getStartingGetCloneOnProjectX());
    asserter.assertConstant("Starting git pull on project <X/>.", 
        messages.getStartingGetPullOnProjectX());
    
    asserter.assertConstant("The download from the following url;", 
        messages.getTheDownloadFromTheFollowingUrl());

    asserter.assertConstant("The following artifact;", 
        messages.getTheFollowingArtifact());
    asserter.assertConstant("The following Fabricate Home should have only these jars;", 
        messages.getTheFollowingFabricateHomeLibShouldHaveOnlyTheseJars());
    asserter.assertConstant("The following Fabricate library can NOT be found;", 
        messages.getTheFollowingFabricateLibraryCanNotBeFound());
    asserter.assertConstant("The following list of Fabricate libraries contains a circular reference;", 
        messages.getTheFollowingListOfFabricateLibrariesContainsACircularReference());
    asserter.assertConstant("The following local repository is locked by another process;", 
        messages.getTheFollowingLocalRepositoryIsLockedByAnotherProcess());
    asserter.assertConstant("The following command line program exited abnormally with exit code <X/>;", 
        messages.getTheFollowingCommandLineProgramExitedAbnormallyWithExitCodeX());
    asserter.assertConstant("The following remote repository appears to be down;", 
        messages.getTheFollowingRemoteRepositoryAppearsToBeDown());
    asserter.assertConstant("The memory key '<X/>' has been locked by the following classes;", 
        messages.getTheMemoryKeyXHasBeenLockedByTheFollowingClasses());
    
    asserter.assertConstant("There was a problem creating the following directory;", 
        messages.getThereWasAProblemCreatingTheFollowingDirectory());
    asserter.assertConstant("There was a problem creating the following routine;", 
        messages.getThereWasAProblemCreatingTheFollowingRoutine());
    asserter.assertConstant("There was a problem creating run.marker in the following directory;", 
        messages.getThereWasAProblemCreatingRunMarkerInTheFollowingDirectory());
    asserter.assertConstant("There was a problem deleting the following directory;", 
        messages.getThereWasAProblemDeletingTheFollowingDirectory());
    asserter.assertConstant("This method must be called from the main thread (try moving the call to setup?).", 
        messages.getThisMethodMustBeCalledFromTheMainThread());
    asserter.assertConstant("This version of Fabricate requires Git <X/> or greater, and 'git --version' "
        + "returned the following;", 
        messages.getThisVersionOfFabricateRequiresGitXOrGreater());
    
    asserter.assertConstant("to the following folder;", 
        messages.getToTheFollowingFolder());
    asserter.assertConstant("Trait <X/> is still running.", 
        messages.getTraitXIsStillRunning());
    asserter.assertConstant("Trait <X/> is still running on project <Z/>.", 
        messages.getTraitXIsStillRunningOnProjectZ());
    asserter.assertConstant("Trait <X/>, task <Y/> is still running.", 
        messages.getTraitXTaskYIsStillRunning());
    asserter.assertConstant("Trait <X/>, task <Y/> is still running on project <Z/>.", 
        messages.getTraitXTaskYIsStillRunningOnProjectZ());
    asserter.assertConstant("Trait <X/> is still setting up.", 
        messages.getTraitXIsStillSettingUp());
    
    asserter.assertConstant("Unable to load the following class;", 
        messages.getUnableToLoadTheFollowingClass());
    asserter.assertConstant("Using the following remote repositories;", 
        messages.getUsingTheFollowingRemoteRepositories());
    
    asserter.assertConstant("Unknown", 
        messages.getUnknown());
    asserter.assertConstant("Version <X/>.", 
        messages.getVersionX());
    asserter.assertConstant("With the following generic type <X/>;", 
        messages.getWithTheFollowingGenericTypeX());
    asserter.assertConstantsMatchMethods(SystemEnMessages.class);
  }
  
}

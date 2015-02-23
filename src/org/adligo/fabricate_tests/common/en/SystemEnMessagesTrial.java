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
    asserter.assertConstant("Building Fabricate runtime class path.", 
        messages.getBuildingFabricateRuntimeClassPath());
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
    asserter.assertConstant("failed!", 
        messages.getFailed());
    asserter.assertConstant("finished.", 
        messages.getFinished());
    
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

    asserter.assertConstant("Sending opts to script.", 
        messages.getSendingOptsToScript());
    asserter.assertConstant("Starting download from the following url;", 
        messages.getStartingDownloadFromTheFollowingUrl());
    
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
    asserter.assertConstant("The following remote repository appears to be down;", 
        messages.getTheFollowingRemoteRepositoryAppearsToBeDown());
    
    asserter.assertConstant("There was a problem creating run.marker in the following directory;", 
        messages.getThereWasAProblemCreatingRunMarkerInTheFollowingDirectory());
    
    asserter.assertConstant("to the following folder;", 
        messages.getToTheFollowingFolder());
    asserter.assertConstant("Unable to load the following class;", 
        messages.getUnableToLoadTheFollowingClass());
    asserter.assertConstant("Using the following remote repositories;", 
        messages.getUsingTheFollowingRemoteRepositories());
    
    asserter.assertConstant("Unknown", 
        messages.getUnknown());
    asserter.assertConstant("Version <X/>.", 
        messages.getVersionX());
    asserter.assertConstantsMatchMethods(SystemEnMessages.class);
  }
  
}

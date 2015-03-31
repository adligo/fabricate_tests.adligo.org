package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate.routines.implicit.GitStageTask;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=GitStageTask.class,minCoverage=0.00)
public class GitStageTaskTrial extends MockitoSourceFileTrial {
  public static final String EXAMPLE_OUTPUT_ADD_DEL_MOD = 
      "On branch master\n" +
      "Your branch is up-to-date with 'origin/master'.\n" +
      "\n" +
      "Changes not staged for commit:\n" +
      "  (use \"git add/rm <file>...\" to update what will be committed)\n" +
      "  (use \"git checkout -- <file>...\" to discard changes in working directory)\n" +
      "\n" +
      "\tmodified:   .classpath\n" +
      "\tdeleted:    project.xml\n" +
      "\n" +
      "Untracked files:\n" +
      "  (use \"git add <file>...\" to include in what will be committed)\n" +
      "\n" +
      "\tfoo.txt\n" +
      "\n" +
      "no changes added to commit (use \"git add\" and/or \"git commit -a\")";
}

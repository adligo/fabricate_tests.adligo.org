package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.CommandLineEnConstants;
import org.adligo.fabricate.common.system.CommandLineArgs;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@SourceFileScope (sourceClass=CommandLineArgs.class, minCoverage=71.0)
public class CommandLineArgsTrial extends MockitoSourceFileTrial {

  @Test
  public void testAppend() throws Exception {
   Map<String,String> args =  new TreeMap<String,String>();
   args.put(CommandLineEnConstants.INSTANCE.getDevelopment(true), null);
   args.put(CommandLineEnConstants.INSTANCE.getRebuildDependents(true), null);
   args.put(CommandLineEnConstants.INSTANCE.getUpdate(true), null);
   args.put(CommandLineArgs.LOCALE, "fr_CA");
   
   StringBuilder sb = new StringBuilder();
   CommandLineArgs.appendArgs(sb, args);
   assertEquals(" --=fr_CA -d -r -u", sb.toString());
  }
  
  @Test
  public void testNormalize() throws Exception {
   Map<String,String> args =  new HashMap<String,String>();
   args.put(CommandLineEnConstants.INSTANCE.getDevelopment(false), null);
   args.put(CommandLineEnConstants.INSTANCE.getLog(false), null);
   args.put(CommandLineEnConstants.INSTANCE.getRebuildDependents(false), null);
   args.put(CommandLineEnConstants.INSTANCE.getUpdate(false), null);
   args.put(CommandLineEnConstants.INSTANCE.getVersion(false), null);
   args.put(CommandLineArgs.LOCALE, "fr_CA");
   
   args =  CommandLineArgs.normalizeArgs(args, CommandLineEnConstants.INSTANCE);
   Set<String> keys = args.keySet();
   assertContains(keys,"-d");
   assertContains(keys,"-l");
   assertContains(keys,"-r");
   assertContains(keys,"-u");
   assertContains(keys,"-v");
   assertEquals("fr_CA", args.get(CommandLineArgs.LOCALE));
  }
  

  @Test
  public void testParse() throws Exception {
   Map<String,String> args =  CommandLineArgs.parseArgs(new String[] {"-vd"});
   Set<String> keys =  args.keySet();
   assertContains(keys,"-v");
   assertContains(keys,"-d");
   
   args =  CommandLineArgs.parseArgs(new String[] {"-v","--debug"});
   keys =  args.keySet();
   assertContains(keys,"-v");
   assertContains(keys,"--debug");
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testToFromPassaable() throws Exception {
    String passable =  CommandLineArgs.toPassableString(new String[] {"-vd"});
    assertEquals("\"[1,[3,-vd]]\"", passable);
    String [] passed = CommandLineArgs.fromPassableString(passable);
    assertEquals("-vd", passed[0]);
    assertEquals(1, passed.length);
   
    passable =  CommandLineArgs.toPassableString(new String[] {"-v","--debug"});
    assertEquals("\"[2,[2,-v][7,--debug]]\"", passable);
    passed = CommandLineArgs.fromPassableString(passable);
    assertEquals("-v", passed[0]);
    assertEquals("--debug", passed[1]);
    assertEquals(2, passed.length);
  }
}

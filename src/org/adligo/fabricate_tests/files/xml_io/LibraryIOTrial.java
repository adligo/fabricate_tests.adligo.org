package org.adligo.fabricate_tests.files.xml_io;

import org.adligo.fabricate.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.files.xml_io.LibraryIO;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependenciesType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeArgumentType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.IdeType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.UnmarshalException;

@SourceFileScope (sourceClass=LibraryIO.class, minCoverage=90.0)
public class LibraryIOTrial extends MockitoSourceFileTrial {


  @Test
  public void testMethod_parse_v1_0() throws Exception {
    LibraryType lib = FabXmlFileIO.INSTANCE.parseLibrary_v1_0("test_data/xml_io_trials/lib.xml");
    assertNotNull(lib);
    DependenciesType deps = lib.getDependencies();
    assertNotNull(deps);
    List<DependencyType> depsList =  deps.getDependency();
    DependencyType dep = depsList.get(0);
    assertNotNull(dep);
    assertEquals("cldc", dep.getArtifact());
    assertEquals("cldc_1.1.jar", dep.getFileName());
    assertEquals("com.sun.jme", dep.getGroup());
    assertEquals("JME", dep.getPlatform());
    assertEquals("jar", dep.getType());
    assertEquals("1.1", dep.getVersion());
    List<IdeType> ides = dep.getIde();
    IdeType ide = ides.get(0);
    assertNotNull(ide);
    assertEquals("eclipse", ide.getName());
    List<IdeArgumentType> args = ide.getArg();
    IdeArgumentType arg = args.get(0);
    assertEquals("ideKey", arg.getKey());
    assertEquals("ideVal", arg.getValue());
    args = arg.getArg();
    arg = args.get(0);
    assertEquals("nestedIdeKey", arg.getKey());
    assertEquals("nestedIdeVal", arg.getValue());
  }
  
  @Test
  public void testMethod_parse_v1_0_bad_content() {
    assertThrown(new ExpectedThrowable(IOException.class, MatchType.ANY), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFileIO.INSTANCE.parseLibrary_v1_0("test_data/xml_io_trials/dev.xml");
      }
    });
  }
  
  @Test
  public void testMethod_parse_v1_0_bad_file() {
    String osName = System.getProperty("os.name");
    String badFile = "X:/foo.xml";
    if (osName.startsWith("Windows")) {
      badFile = "/dev/nul";
    } 
    final String badFileName = badFile;
    assertThrown(new ExpectedThrowable(IOException.class, MatchType.ANY,
        new ExpectedThrowable(UnmarshalException.class, MatchType.ANY,
        new ExpectedThrowable(FileNotFoundException.class, MatchType.ANY))), new I_Thrower() {
      
      @Override
      public void run() throws Throwable {
        FabXmlFileIO.INSTANCE.parseLibrary_v1_0(badFileName);
      }
    });
  }
  
}

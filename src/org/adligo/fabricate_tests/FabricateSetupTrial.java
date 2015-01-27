package org.adligo.fabricate_tests;

import org.adligo.fabricate.FabricateSetup;
import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.external.JavaCalls;
import org.adligo.fabricate.external.ManifestParser;
import org.adligo.fabricate_tests.common.mocks.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.shared.asserts.line_text.TextLines;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@SourceFileScope (sourceClass=FabricateSetup.class, minCoverage=4.0)
public class FabricateSetupTrial extends MockitoSourceFileTrial {
  private ByteArrayOutputStream baos_;
  private FabSystem sysMock_;
  private I_FabFileIO fileMock_;
  private I_FabXmlFileIO xmlIoMock_;
  private JavaCalls javaCallsMock_;
  private ManifestParser manifestParserMock_;
  
  public void afterTests() {
    ThreadLocalPrintStreamMock.revert();
  }
  
  public void beforeTests() {
    sysMock_ = mock(FabSystem.class);
    when(sysMock_.lineSeperator()).thenReturn("\n");
    fileMock_ = mock(I_FabFileIO.class);
    when(fileMock_.getNameSeparator()).thenReturn("/");
    xmlIoMock_ = mock(I_FabXmlFileIO.class);
    when(sysMock_.getFileIO()).thenReturn(fileMock_);
    when(sysMock_.getXmlFileIO()).thenReturn(xmlIoMock_);
    javaCallsMock_ = mock(JavaCalls.class);
    FabricateSetup.setJAVA_CALLS(javaCallsMock_);
    assertSame(javaCallsMock_, FabricateSetup.getJAVA_CALLS());
    
    manifestParserMock_ = mock(ManifestParser.class);
    
    baos_ = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos_);
    
    ThreadLocalPrintStreamMock.set(printStream);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsFailNoJavaHome() {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenThrow(new IllegalStateException());
    
    String [] nada = new String[]{"args"};
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    assertEquals("Message" + System.lineSeparator() +
        "Exception: No $JAVA_HOME environment variable set." + 
        System.lineSeparator(), result);
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsProblemExecutingJavaWithJavaHome() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenThrow(
        new IOException());
    String [] nada = new String[]{"args"};
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("Exception: There was a problem executing java with the following $JAVA_HOME;",
        lines.getLine(1));
    assertEquals("someHome", lines.getLine(2));
    assertEquals("java.io.IOException", lines.getLine(3));
    assertEquals("\tat org.adligo.fabricate.FabricateSetup.doArgs(FabricateSetup.java:126)", 
        lines.getLine(4));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadJavaVersion() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.6.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.6.0_03")).thenReturn(1.6);
    String [] nada = new String[]{"args"};
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("Exception: Fabricate requires Java 1.7 or greater.",
        lines.getLine(1));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadFabriateHomeWrongFilesList() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args"};
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("The following Fabricate Home should have only these jars;",
        lines.getLine(1));
    assertEquals("fabHome", lines.getLine(2));
    assertEquals("commons-logging-1.2.jar", lines.getLine(3));
    assertEquals("fabricate_*.jar", lines.getLine(4));
    assertEquals("httpclient-4.3.5.jar", lines.getLine(5));
    assertEquals("httpcore-4.3.2.jar", lines.getLine(6));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadFabriateHomeListFilesException() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args"};
    
    when(fileMock_.list(any(), any())).thenThrow(new IOException());
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_,manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("The following Fabricate Home should have only these jars;",
        lines.getLine(1));
    assertEquals("fabHome", lines.getLine(2));
    assertEquals("commons-logging-1.2.jar", lines.getLine(3));
    assertEquals("fabricate_*.jar", lines.getLine(4));
    assertEquals("httpclient-4.3.5.jar", lines.getLine(5));
    assertEquals("httpcore-4.3.2.jar", lines.getLine(6));
    assertEquals("java.io.IOException", lines.getLine(7));
    assertEquals("\tat org.adligo.fabricate.FabricateSetup.locateFabricateJarAndVerifyFabricateHomeJars(FabricateSetup.java:208)", lines.getLine(8));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadFabriateHomeNoFabriateJar() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args"};
    
    List<String> names = new ArrayList<String>();
    names.add("hun.jar");
    names.add("huh.jar");
    names.add("hun1.jar");
    names.add("huh1.jar");
    when(fileMock_.list(any(), any())).thenReturn(names);
    
    MockMethod<File> fileInstanceMock = new MockMethod<File>(new I_ReturnFactory<File>() {
      @Override
      public File create(Object[] keys) {
        return new File((String) keys[0]);
      }
    });
    when(fileMock_.instance(any())).then(fileInstanceMock);
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("Exception: No fabricate_*.jar in $FABRICATE_HOME/lib for the following $FABRICATE_HOME;",
        lines.getLine(1));
    assertEquals("fabHome", lines.getLine(2));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsNoFabricateHome() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn(null);
    String [] nada = new String[]{"args"};
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("Exception: No $FABRICATE_HOME environment variable set.",
        lines.getLine(1));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadFabriateHomeNoCommonsLogging() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args"};
    
    List<String> names = new ArrayList<String>();
    names.add("fabricate_snapshot.jar");
    names.add("huh.jar");
    names.add("httpclient-4.3.5.jar");
    names.add("httpcore-4.3.2.jar");
    when(fileMock_.list(any(), any())).thenReturn(names);
    
    MockMethod<File> fileInstanceMock = new MockMethod<File>(new I_ReturnFactory<File>() {
      @Override
      public File create(Object[] keys) {
        return new File((String) keys[0]);
      }
    });
    when(fileMock_.instance(any())).then(fileInstanceMock);
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("The following Fabricate Home should have only these jars;",
        lines.getLine(1));
    assertEquals("fabHome", lines.getLine(2));
    assertEquals("commons-logging-1.2.jar", lines.getLine(3));
    assertEquals("fabricate_*.jar", lines.getLine(4));
    assertEquals("httpclient-4.3.5.jar", lines.getLine(5));
    assertEquals("httpcore-4.3.2.jar", lines.getLine(6));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadFabriateHomeNoHttpCore() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args"};
    
    List<String> names = new ArrayList<String>();
    names.add("fabricate_snapshot.jar");
    names.add("huh.jar");
    names.add("httpclient-4.3.5.jar");
    names.add("commons-logging-1.2.jar");
    when(fileMock_.list(any(), any())).thenReturn(names);
    
    MockMethod<File> fileInstanceMock = new MockMethod<File>(new I_ReturnFactory<File>() {
      @Override
      public File create(Object[] keys) {
        return new File((String) keys[0]);
      }
    });
    when(fileMock_.instance(any())).then(fileInstanceMock);
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("The following Fabricate Home should have only these jars;",
        lines.getLine(1));
    assertEquals("fabHome", lines.getLine(2));
    assertEquals("commons-logging-1.2.jar", lines.getLine(3));
    assertEquals("fabricate_*.jar", lines.getLine(4));
    assertEquals("httpclient-4.3.5.jar", lines.getLine(5));
    assertEquals("httpcore-4.3.2.jar", lines.getLine(6));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsBadFabriateHomeNoHttpClient() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args"};
    
    List<String> names = new ArrayList<String>();
    names.add("fabricate_snapshot.jar");
    names.add("huh.jar");
    names.add("httpcore-4.3.2.jar");
    names.add("commons-logging-1.2.jar");
    when(fileMock_.list(any(), any())).thenReturn(names);
    
    MockMethod<File> fileInstanceMock = new MockMethod<File>(new I_ReturnFactory<File>() {
      @Override
      public File create(Object[] keys) {
        return new File((String) keys[0]);
      }
    });
    when(fileMock_.instance(any())).then(fileInstanceMock);
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("The following Fabricate Home should have only these jars;",
        lines.getLine(1));
    assertEquals("fabHome", lines.getLine(2));
    assertEquals("commons-logging-1.2.jar", lines.getLine(3));
    assertEquals("fabricate_*.jar", lines.getLine(4));
    assertEquals("httpclient-4.3.5.jar", lines.getLine(5));
    assertEquals("httpcore-4.3.2.jar", lines.getLine(6));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsDisplayVersion() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args","-vrd"};
    
    List<String> names = new ArrayList<String>();
    names.add("fabricate_snapshot.jar");
    names.add("commons-logging-1.2.jar");
    names.add("httpcore-4.3.2.jar");
    names.add("httpclient-4.3.5.jar");
    when(fileMock_.list(any(), any())).thenReturn(names);
    
    MockMethod<File> fileInstanceMock = new MockMethod<File>(new I_ReturnFactory<File>() {
      @Override
      public File create(Object[] keys) {
        File toRet = mock(File.class);
        String name = (String) keys[0];
        
        if (name.indexOf("fabHome") == -1) {
          when(toRet.getName()).thenReturn(name);
          when(toRet.getAbsolutePath()).thenReturn("fabHome/lib/" + (String) keys[0]);
        } else {
          String newName = name.substring(12, name.length());
          when(toRet.getName()).thenReturn(newName);
          when(toRet.getAbsolutePath()).thenReturn(name);
        }
        return toRet;
      }
    });
    when(fileMock_.instance(any())).then(fileInstanceMock);
    
    MockMethod<Void> manifestReader = new MockMethod<Void>();
    doAnswer(manifestReader).when(manifestParserMock_).readManifest(any());
    
    when(manifestParserMock_.get(ManifestParser.SPECIFICATION_VERSION)
        ).thenReturn("snapshot");
    when(manifestParserMock_.get(ManifestParser.IMPLEMENTATION_VERSION)
        ).thenReturn("1/1/1001");
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    assertEquals(1, manifestReader.count());
    assertEquals("fabHome/lib/fabricate_snapshot.jar", manifestReader.getArg(0));
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("Message", lines.getLine(0));
    assertEquals("Version snapshot.", lines.getLine(1));
    assertEquals("Compiled on 1/1/1001.", lines.getLine(2));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorArgsPrintOpts() throws Exception {
    MockMethod<Void> setDebugMock_ = new MockMethod<Void>();
    doAnswer(setDebugMock_).when(sysMock_).setDebug(anyBoolean());
    
    when(javaCallsMock_.getJavaHome()).thenReturn("someHome");
    when(javaCallsMock_.getJavaVersion(eq("someHome"), any())).thenReturn("1.7.0_03");
    when(javaCallsMock_.getJavaMajorVersion("1.7.0_03")).thenReturn(1.7);
    when(sysMock_.getenv("FABRICATE_HOME")).thenReturn("fabHome");
    String [] nada = new String[]{"args","-rd"};
    
    List<String> names = new ArrayList<String>();
    names.add("fabricate_snapshot.jar");
    names.add("commons-logging-1.2.jar");
    names.add("httpcore-4.3.2.jar");
    names.add("httpclient-4.3.5.jar");
    when(fileMock_.list(any(), any())).thenReturn(names);
    
    MockMethod<File> fileInstanceMock = new MockMethod<File>(new I_ReturnFactory<File>() {
      @Override
      public File create(Object[] keys) {
        return new File((String) keys[0]);
      }
    });
    when(fileMock_.instance(any())).then(fileInstanceMock);
    
    when(manifestParserMock_.get(ManifestParser.SPECIFICATION_VERSION)
        ).thenReturn("snapshot");
    when(manifestParserMock_.get(ManifestParser.IMPLEMENTATION_VERSION)
        ).thenReturn("1/1/1001");
    
    FabricateSetup setup = new FabricateSetup(nada,sysMock_, manifestParserMock_);
    assertEquals(1, setDebugMock_.count());
    assertFalse((Boolean) setDebugMock_.getArg(0));
    assertSame(FabricateEnConstants.INSTANCE, setup.getConstants());
    
    String result = baos_.toString();
    TextLines lines = new TextLines(result);
    assertEquals("start=0 java=1.7.0_03", lines.getLine(0));
    assertEquals(1, lines.getLines());
  }
}

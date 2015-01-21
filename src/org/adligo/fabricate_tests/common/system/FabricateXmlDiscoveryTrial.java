package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.system.CommandLineArgs;
import org.adligo.fabricate.common.system.FabricateXmlDiscovery;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType;
import org.adligo.fabricate_tests.common.mocks.ThreadLocalPrintStreamMock;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

@SourceFileScope (sourceClass=FabricateXmlDiscovery.class,minCoverage=92.0)
public class FabricateXmlDiscoveryTrial extends MockitoSourceFileTrial {
  private ByteArrayOutputStream baos_;
  private I_FabSystem sysMock_;
  private I_FabFileIO filesMock_;
  private I_FabXmlFileIO xmlFilesMock_;
  
  private MockMethod<File> instanceMethod_;
  private MockMethod<FabricateDevType> parseDev_v1_0Method_;
  
  private I_FabricateConstants constantsMock_;
  private boolean windows_ = false;
  
  @Override
  public void beforeTests()  {
    windows_ = false;
    baos_ = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos_);
    ThreadLocalPrintStreamMock.set(printStream);
    filesMock_ = mock(I_FabFileIO.class);
    setupPaths("/");
    
    
    when(filesMock_.instance(any())).then(instanceMethod_);
    
    constantsMock_ = mock(I_FabricateConstants.class);
    when(constantsMock_.getFileMessages()).thenReturn(FileEnMessages.INSTANCE);
    
    when(constantsMock_.getLineSeperator()).thenReturn("\n");
    
    xmlFilesMock_ = mock(I_FabXmlFileIO.class);
    parseDev_v1_0Method_ = new MockMethod<FabricateDevType>(new I_ReturnFactory<FabricateDevType>() {

      @Override
      public FabricateDevType create(Object[] keys) {
        FabricateDevType toRet = mock(FabricateDevType.class);
        return toRet;
      }
    });
    try {
      when(xmlFilesMock_.parseDev_v1_0(any())).then(parseDev_v1_0Method_);
    } catch (IOException x) {
      throw new RuntimeException(x);
    }
    
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.getFileIO()).thenReturn(filesMock_);
    when(sysMock_.getXmlFileIO()).thenReturn(xmlFilesMock_);
  }

  public void setupPaths(String nameSeparator) {
    when(filesMock_.getNameSeparator()).thenReturn(nameSeparator);
    instanceMethod_ = new MockMethod<File>(new I_ReturnFactory<File>() {

      @Override
      public File create(Object[] keys) {
        String name = (String) keys[0];
        //making nameSeparator final didn't help
        String nameSep = filesMock_.getNameSeparator();
        File fileMock = mock(File.class);
        String absolutePath = null;
        String fileName = name;
        int lastNameSep = name.lastIndexOf(nameSep);
        if (name.indexOf(nameSep) == -1 && name.indexOf(":") <= 3) {
          //C:\
          if (windows_) {
            absolutePath = "C:" + nameSep + "somewhere" + nameSep + "projects" + nameSep + "projectName.example.com" + nameSep + name;
          } else {
            absolutePath = nameSep + "somewhere" + nameSep + "projects" + nameSep + "projectName.example.com" + nameSep + name;
          }
       } else {
          absolutePath = name;
        }
        fileName = name.substring(lastNameSep + 1, name.length());
        when(fileMock.getAbsolutePath()).thenReturn(absolutePath);
        when(fileMock.getName()).thenReturn(fileName);
        
        return fileMock;
      }
    });
  }

  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithFabricateXml() {
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(true);
    File mockFabFile = filesMock_.instance("fabricate.xml");
    when(mockFabFile.exists()).thenReturn(true);
    
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.hasFabricateXml());
    assertEquals("/somewhere/projects/projectName.example.com/fabricate.xml", disc.getFabricateXmlPath());
    assertFalse(disc.hasProjectXml());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithFabricateXmlWindows() {
    windows_ = true;
    setupPaths("\\");
    when(filesMock_.exists("fabricate.xml")).thenReturn(true);
    File mockFabFile = filesMock_.instance("fabricate.xml");
    when(mockFabFile.exists()).thenReturn(true);
    
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.hasFabricateXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\fabricate.xml", disc.getFabricateXmlPath());
    assertFalse(disc.hasProjectXml());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectXmlNullParent() {
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("/somewhere/projects/projectName.example.com");
    
    File projectFileMock = filesMock_.instance("/somewhere/projects/projectName.example.com/project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
    
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertFalse(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectXmlNullParentWindows() {
    windows_ = true;
    setupPaths("\\");
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects\\projectName.example.com");
    
    File projectFileMock = filesMock_.instance("C:\\somewhere\\projects\\projectName.example.com\\project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
    
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertFalse(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectXmlNullGrandparent() {
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("/somewhere/projects/projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("/somewhere/projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File projectFileMock = filesMock_.instance("/somewhere/projects/projectName.example.com/project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
    
    when(projectFileMock.exists()).thenReturn(true);
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertTrue(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectXmlNullGrandparentWindows() {
    windows_ = true;
    setupPaths("\\");
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects\\projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("/somewhere/projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File projectFileMock = filesMock_.instance("C:\\somewhere\\projects\\projectName.example.com\\project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
    
    when(projectFileMock.exists()).thenReturn(true);
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertTrue(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectXml() {
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("/somewhere/projects/projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("/somewhere/projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File grandParentDirMock = mock(File.class);
    when(grandParentDirMock.getAbsolutePath()).thenReturn("/somewhere");
    when(parentDirMock.getParentFile()).thenReturn(grandParentDirMock);
    
    File projectFileMock = filesMock_.instance("/somewhere/projects/projectName.example.com/project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
    
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertEquals("/somewhere/fabricate.xml", disc.getFabricateXmlPath());
    assertFalse(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
    
    when(projectFileMock.exists()).thenReturn(true);
    disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertEquals("/somewhere/fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
    
    File fabFile = filesMock_.instance("/somewhere/fabricate.xml");
    when(fabFile.exists()).thenReturn(true);
    disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.hasFabricateXml());
    assertEquals("/somewhere/fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectXmlWindows() {
    windows_ = true;
    setupPaths("\\");
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects\\projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File grandParentDirMock = mock(File.class);
    when(grandParentDirMock.getAbsolutePath()).thenReturn("C:\\somewhere");
    when(parentDirMock.getParentFile()).thenReturn(grandParentDirMock);
    
    File projectFileMock = filesMock_.instance("C:\\somewhere\\projects\\projectName.example.com\\project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
    
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertEquals("C:\\somewhere\\fabricate.xml", disc.getFabricateXmlPath());
    assertFalse(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
    
    when(projectFileMock.exists()).thenReturn(true);
    disc = new FabricateXmlDiscovery(sysMock_);
    assertFalse(disc.hasFabricateXml());
    assertEquals("C:\\somewhere\\fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
    
    File fabFile = filesMock_.instance("C:\\somewhere\\fabricate.xml");
    when(fabFile.exists()).thenReturn(true);
    disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.hasFabricateXml());
    assertEquals("C:\\somewhere\\fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
    
  }
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectAndDevXml() throws Exception {
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("/somewhere/projects/projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("/somewhere/projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File grandParentDirMock = mock(File.class);
    when(grandParentDirMock.getAbsolutePath()).thenReturn("/somewhere");
    when(parentDirMock.getParentFile()).thenReturn(grandParentDirMock);
    
    File projectFileMock = filesMock_.instance("/somewhere/projects/projectName.example.com/project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
   
    when(filesMock_.exists("/somewhere/projects/dev.xml")).thenReturn(true);
    FabricateDevType dev = new FabricateDevType();
    dev.setProjectGroup("projectGroup.example.com");
    when(xmlFilesMock_.parseDev_v1_0("/somewhere/projects/dev.xml")).thenReturn(dev);
    
    
    when(projectFileMock.exists()).thenReturn(true);
    //run
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    //assert
    assertFalse(disc.hasFabricateXml());
    assertEquals("/somewhere/projects/projectGroup.example.com/fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
    
    //setup
    File fabFile = filesMock_.instance("/somewhere/projects/projectGroup.example.com/fabricate.xml");
    when(fabFile.exists()).thenReturn(true);
    //run
    disc = new FabricateXmlDiscovery(sysMock_);
    //assert
    assertTrue(disc.hasFabricateXml());
    assertEquals("/somewhere/projects/projectGroup.example.com/fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("/somewhere/projects/projectName.example.com/project.xml", disc.getProjectXml());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithProjectAndDevXmlWindows() throws Exception {
    windows_ = true;
    setupPaths("\\");
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects\\projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File grandParentDirMock = mock(File.class);
    when(grandParentDirMock.getAbsolutePath()).thenReturn("C:\\somewhere");
    when(parentDirMock.getParentFile()).thenReturn(grandParentDirMock);
    
    File projectFileMock = filesMock_.instance("C:\\somewhere\\projects\\projectName.example.com\\project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
   
    when(filesMock_.exists("C:\\somewhere\\projects\\dev.xml")).thenReturn(true);
    FabricateDevType dev = new FabricateDevType();
    dev.setProjectGroup("projectGroup.example.com");
    when(xmlFilesMock_.parseDev_v1_0("C:\\somewhere\\projects\\dev.xml")).thenReturn(dev);
    
    
    when(projectFileMock.exists()).thenReturn(true);
    //run
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    //assert
    assertFalse(disc.hasFabricateXml());
    assertEquals("C:\\somewhere\\projects\\projectGroup.example.com\\fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
    
    //setup
    File fabFile = filesMock_.instance("C:\\somewhere\\projects\\projectGroup.example.com\\fabricate.xml");
    when(fabFile.exists()).thenReturn(true);
    //run
    disc = new FabricateXmlDiscovery(sysMock_);
    //assert
    assertTrue(disc.hasFabricateXml());
    assertEquals("C:\\somewhere\\projects\\projectGroup.example.com\\fabricate.xml", disc.getFabricateXmlPath());
    assertTrue(disc.hasProjectXml());
    assertEquals("C:\\somewhere\\projects\\projectName.example.com\\project.xml", disc.getProjectXml());
  }
  @SuppressWarnings({"boxing"})
  @Test
  public void testConstructorDirWithProjectAndDevXml_DevParseIOException() throws IOException {
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("/somewhere/projects/projectName.example.com");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("/somewhere/projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File grandParentDirMock = mock(File.class);
    when(grandParentDirMock.getAbsolutePath()).thenReturn("/somewhere");
    when(parentDirMock.getParentFile()).thenReturn(grandParentDirMock);
    
    File projectFileMock = filesMock_.instance("/somewhere/projects/projectName.example.com/project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
   
    when(filesMock_.exists("/somewhere/projects/dev.xml")).thenReturn(true);
    when(xmlFilesMock_.parseDev_v1_0("/somewhere/projects/dev.xml")).thenThrow(new IOException("catchup"));
    
    when(projectFileMock.exists()).thenReturn(true);
    
    //run
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.isDevParseException());
    assertEquals("",baos_.toString());
    
    when(sysMock_.isDebug()).thenReturn(true);
    disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.isDevParseException());
    String output = baos_.toString();
    assertTrue(output.indexOf(CommandLineArgs.MESSAGE) == 0);
    assertTrue(output.indexOf("java.io.IOException") >= 7);
    assertTrue(output.indexOf("catchup") >= 17);
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testConstructorDirWithProjectAndDevXml_DevParseIOExceptionWindows() throws IOException {
    windows_ = true;
    setupPaths("\\");
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(false);
    when(filesMock_.exists("project.xml")).thenReturn(true);
    
    File dirMock = mock(File.class);
    when(dirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects\\projectName.example.com\\");
    File parentDirMock = mock(File.class);
    when(parentDirMock.getAbsolutePath()).thenReturn("C:\\somewhere\\projects");
    when(dirMock.getParentFile()).thenReturn(parentDirMock);
    
    File grandParentDirMock = mock(File.class);
    when(grandParentDirMock.getAbsolutePath()).thenReturn("C:\\somewhere");
    when(parentDirMock.getParentFile()).thenReturn(grandParentDirMock);
    
    File projectFileMock = filesMock_.instance("C:\\somewhere\\projects\\projectName.example.com\\project.xml");
    when(projectFileMock.getParentFile()).thenReturn(dirMock);
   
    when(filesMock_.exists("C:\\somewhere\\projects\\dev.xml")).thenReturn(true);
    when(xmlFilesMock_.parseDev_v1_0("C:\\somewhere\\projects\\dev.xml")).thenThrow(new IOException("catchup"));
    
    when(projectFileMock.exists()).thenReturn(true);
    
    //run
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.isDevParseException());
    assertEquals("",baos_.toString());
    
    when(sysMock_.isDebug()).thenReturn(true);
    disc = new FabricateXmlDiscovery(sysMock_);
    assertTrue(disc.isDevParseException());
    String output = baos_.toString();
    assertTrue(output.indexOf(CommandLineArgs.MESSAGE) == 0);
    assertTrue(output.indexOf("java.io.IOException") >= 7);
    assertTrue(output.indexOf("catchup") >= 17);
  }
}

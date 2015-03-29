package org.adligo.fabricate_tests.repository;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.dependencies.I_Dependency;
import org.adligo.fabricate.models.fabricate.I_Fabricate;
import org.adligo.fabricate.repository.LibraryResolver;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependenciesType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryReferenceType;
import org.adligo.fabricate.xml.io_v1.library_v1_0.LibraryType;
import org.adligo.fabricate_tests.models.dependencies.DependencyMutantTrial;
import org.adligo.fabricate_tests.models.dependencies.DependencyTrial;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SourceFileScope (sourceClass=LibraryResolver.class, minCoverage=5.0)
public class LibraryResolverTrial extends MockitoSourceFileTrial {
  private static final String FAB_HOME = "${fabricate_home}/";
  private I_FabSystem sysMock_;
  private I_FabFileIO filesMock_;
  private I_FabXmlFileIO filesXmlMock_;
  private I_Fabricate fabMock_;
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeparator()).thenReturn(System.lineSeparator());
    
    
    filesMock_ = mock(I_FabFileIO.class);
    when(filesMock_.getNameSeparator()).thenReturn(File.separator);
    when(sysMock_.getFileIO()).thenReturn(filesMock_);
    
    filesXmlMock_ = mock(I_FabXmlFileIO.class);
    when(sysMock_.getXmlFileIO()).thenReturn(filesXmlMock_);
    
    fabMock_ = mock(I_Fabricate.class);
    when(fabMock_.getFabricateXmlRunDir()).thenReturn(FAB_HOME);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesSimpleLeaf() throws Exception {
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    lt.setDependencies(depsTypeIn);
    
    String path = FAB_HOME + "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(path)).thenReturn(lt);
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);
    List<I_Dependency> deps = lr.getDependencies("sleaf", null);
    
    DependencyTrial.assertDependencyConversion(this, deps, null);
    assertEquals(2, deps.size());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesBranchWithLeaf() throws Exception {
    
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    lt.setDependencies(depsTypeIn);
    
    LibraryType ltBranch = new LibraryType();
    DependenciesType depsTypeInBranch = new DependenciesType();
    List<DependencyType> depsInBranch = DependencyMutantTrial.getDependenciesC();
    depsTypeInBranch.getDependency().addAll(depsInBranch);
    
    LibraryReferenceType lrt = new LibraryReferenceType();
    lrt.setValue("sleaf");
    depsTypeInBranch.getLibrary().add(lrt);
    ltBranch.setDependencies(depsTypeInBranch);
    
    String path = FAB_HOME +  "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(path)).thenReturn(lt);
    
    String pathBranch = FAB_HOME + "lib" + 
        File.separator + "branch.xml";
    when(filesMock_.exists(pathBranch)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathBranch)).thenReturn(ltBranch);
    
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);
    List<I_Dependency> deps = lr.getDependencies("branch", "projectA");
    
    I_Dependency depZero = deps.get(0);
    DependencyTrial.assertDependencyConversionC(
        this, Collections.singletonList(depZero), "projectA");
    deps.remove(0);
    DependencyTrial.assertDependencyConversion(this, deps, "projectA");
    assertEquals(2, deps.size());
    
  }
  
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesExceptionLibNotFound() {
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    lt.setDependencies(depsTypeIn);
    
    String path = FAB_HOME +  "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(false);
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following Fabricate library can NOT be found;" + System.lineSeparator() +
        "${fabricate_home}/lib/sleaf.xml")), new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            lr.getDependencies("sleaf", "projectA");
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesExceptionCircularLibs() throws Exception {
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    LibraryReferenceType lrtTrunk = new LibraryReferenceType();
    lrtTrunk.setValue("trunk");
    depsTypeIn.getLibrary().add(lrtTrunk);
    
    lt.setDependencies(depsTypeIn);
    
    LibraryType ltBranch = new LibraryType();
    DependenciesType depsTypeInBranch = new DependenciesType();
    List<DependencyType> depsInBranch = DependencyMutantTrial.getDependenciesC();
    depsTypeInBranch.getDependency().addAll(depsInBranch);
    
    LibraryType ltTrunk = new LibraryType();
    DependenciesType depsTypeInTrunk = new DependenciesType();
    List<DependencyType> depsInTrunk = DependencyMutantTrial.getDependenciesD();
    depsTypeInTrunk.getDependency().addAll(depsInTrunk);
    
    LibraryReferenceType lrt = new LibraryReferenceType();
    lrt.setValue("sleaf");
    depsTypeInBranch.getLibrary().add(lrt);
    ltBranch.setDependencies(depsTypeInBranch);
    
    LibraryReferenceType lrtBranch = new LibraryReferenceType();
    lrtBranch.setValue("branch");
    depsTypeInTrunk.getLibrary().add(lrtBranch);
    ltTrunk.setDependencies(depsTypeInTrunk);
    

    
    String path = FAB_HOME +  "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(path)).thenReturn(lt);
    
    String pathBranch = FAB_HOME + "lib" + 
        File.separator + "branch.xml";
    when(filesMock_.exists(pathBranch)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathBranch)).thenReturn(ltBranch);
    
    String pathTrunk = FAB_HOME + "lib" + 
        File.separator + "trunk.xml";
    when(filesMock_.exists(pathTrunk)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathTrunk)).thenReturn(ltTrunk);
    
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);

    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The following list of Fabricate libraries contains a circular reference;" + System.lineSeparator() +
        "[sleaf, trunk, branch]")), new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            lr.getDependencies("sleaf", "projectA");
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesIoExceptionFromXmlRead() throws Exception {
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    lt.setDependencies(depsTypeIn);
    
    String path = FAB_HOME + "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(path)).thenThrow(new IOException("iox"));
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);
    assertThrown(new ExpectedThrowable(new IllegalStateException("iox"), 
        new ExpectedThrowable(new IOException(
        "iox"))), new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            lr.getDependencies("sleaf", "projectA");
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesTrunkWithBranch() throws Exception {
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    lt.setDependencies(depsTypeIn);
    
    LibraryType ltBranch = new LibraryType();
    DependenciesType depsTypeInBranch = new DependenciesType();
    List<DependencyType> depsInBranch = DependencyMutantTrial.getDependenciesC();
    depsTypeInBranch.getDependency().addAll(depsInBranch);
    
    LibraryType ltTrunk = new LibraryType();
    DependenciesType depsTypeInTrunk = new DependenciesType();
    List<DependencyType> depsInTrunk = DependencyMutantTrial.getDependenciesD();
    depsTypeInTrunk.getDependency().addAll(depsInTrunk);
    
    LibraryReferenceType lrt = new LibraryReferenceType();
    lrt.setValue("sleaf");
    depsTypeInBranch.getLibrary().add(lrt);
    ltBranch.setDependencies(depsTypeInBranch);
    
    LibraryReferenceType lrtBranch = new LibraryReferenceType();
    lrtBranch.setValue("branch");
    depsTypeInTrunk.getLibrary().add(lrtBranch);
    ltTrunk.setDependencies(depsTypeInTrunk);
    
    String path = FAB_HOME + "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(path)).thenReturn(lt);
    
    String pathBranch = FAB_HOME + "lib" + 
        File.separator + "branch.xml";
    when(filesMock_.exists(pathBranch)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathBranch)).thenReturn(ltBranch);
    
    String pathTrunk = FAB_HOME + "lib" + 
        File.separator + "trunk.xml";
    when(filesMock_.exists(pathTrunk)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathTrunk)).thenReturn(ltTrunk);
    
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);
    List<I_Dependency> deps = lr.getDependencies("trunk", "projectA");
    
    I_Dependency depZero = deps.get(0);
    DependencyTrial.assertDependencyConversionD(
        this, Collections.singletonList(depZero), "projectA");
    deps.remove(0);
    
    I_Dependency depOne = deps.get(0);
    DependencyTrial.assertDependencyConversionC(
        this, Collections.singletonList(depOne), "projectA");
    deps.remove(0);
    
    DependencyTrial.assertDependencyConversion(this, deps, "projectA");
    assertEquals(2, deps.size());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodGetDependenciesTrunks() throws Exception {
    LibraryType lt = new LibraryType();
    DependenciesType depsTypeIn = new DependenciesType();
    List<DependencyType> depsIn = DependencyMutantTrial.getDependencies();
    depsTypeIn.getDependency().addAll(depsIn);
    
    lt.setDependencies(depsTypeIn);
    
    LibraryType ltBranch = new LibraryType();
    DependenciesType depsTypeInBranch = new DependenciesType();
    List<DependencyType> depsInBranch = DependencyMutantTrial.getDependenciesC();
    depsTypeInBranch.getDependency().addAll(depsInBranch);
    
    LibraryType ltTrunk = new LibraryType();
    DependenciesType depsTypeInTrunk = new DependenciesType();
    List<DependencyType> depsInTrunk = DependencyMutantTrial.getDependenciesD();
    depsTypeInTrunk.getDependency().addAll(depsInTrunk);
    
    LibraryReferenceType lrt = new LibraryReferenceType();
    lrt.setValue("sleaf");
    depsTypeInBranch.getLibrary().add(lrt);
    ltBranch.setDependencies(depsTypeInBranch);
    
    LibraryReferenceType lrtBranch = new LibraryReferenceType();
    lrtBranch.setValue("branch");
    depsTypeInTrunk.getLibrary().add(lrtBranch);
    ltTrunk.setDependencies(depsTypeInTrunk);
    
    String path = FAB_HOME + "lib" + 
        File.separator + "sleaf.xml";
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesMock_.exists(path)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(path)).thenReturn(lt);
    
    String pathBranch = FAB_HOME +  "lib" + 
        File.separator + "branch.xml";
    when(filesMock_.exists(pathBranch)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathBranch)).thenReturn(ltBranch);
    
    String pathTrunk = FAB_HOME + "lib" + 
        File.separator + "trunk.xml";
    when(filesMock_.exists(pathTrunk)).thenReturn(true);
    when(filesXmlMock_.parseLibrary_v1_0(pathTrunk)).thenReturn(ltTrunk);
    
    LibraryResolver lr = new LibraryResolver(sysMock_, fabMock_);
    List<LibraryReferenceType> lrts = new ArrayList<LibraryReferenceType>();
    LibraryReferenceType libRef = new LibraryReferenceType();
    libRef.setValue("trunk");
    lrts.add(libRef);
    
    LibraryReferenceType libRefBranch = new LibraryReferenceType();
    libRefBranch.setValue("branch");
    lrts.add(libRefBranch);
    
    LibraryReferenceType libRefLeaf = new LibraryReferenceType();
    libRefLeaf.setValue("sleaf");
    lrts.add(libRefLeaf);
    
    List<I_Dependency> deps = lr.getDependencies(lrts, null);
    
    I_Dependency depZero = deps.get(0);
    DependencyTrial.assertDependencyConversionD(
        this, Collections.singletonList(depZero), null);
    deps.remove(0);
    
    I_Dependency depOne = deps.get(0);
    DependencyTrial.assertDependencyConversionC(
        this, Collections.singletonList(depOne), null);
    deps.remove(0);
    
    List<I_Dependency> depsTwo = new ArrayList<I_Dependency>();
    depsTwo.add(deps.get(0));
    depsTwo.add(deps.get(1));
    DependencyTrial.assertDependencyConversion(this, depsTwo, null);
    deps.remove(0);
    deps.remove(0);
    
    I_Dependency depFive = deps.get(0);
    DependencyTrial.assertDependencyConversionC(
        this, Collections.singletonList(depFive), null);
    deps.remove(0);
    
    List<I_Dependency> depsSixSeven = new ArrayList<I_Dependency>();
    depsSixSeven.add(deps.get(0));
    depsSixSeven.add(deps.get(1));
    DependencyTrial.assertDependencyConversion(this, depsSixSeven, null);
    deps.remove(0);
    deps.remove(0);
    
    DependencyTrial.assertDependencyConversion(this, deps, null);
    assertEquals(2, deps.size());
  }
}

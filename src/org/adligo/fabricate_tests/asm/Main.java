package org.adligo.fabricate_tests.asm;

import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

  @SuppressWarnings("unused")
  public static void main(String [] args) {
    //InputStream is = new FileInputStream(new File("classes_e/org/adligo/fabricate_tests/asm/Foo.class"));
    try {
      InputStream is = new FileInputStream(new File("../fabricate.adligo.org/classes_e/org/adligo/fabricate/models/common/ExpectedRoutineInterface.class"));
      
      ClassReader classReader=new ClassReader(is);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
}

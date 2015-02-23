package org.adligo.fabricate_tests.common.util;

import org.adligo.fabricate.common.util.MethodBlocker;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;


@SourceFileScope (sourceClass=MethodBlocker.class, minCoverage=98.0)
public class MethodBlockerTrial extends MockitoSourceFileTrial {

      /**
       * note this test must run first to 
       * trick the class loader
       * 
       */
      @Test
      public void testNullStackTraceException() {
        final MockWithMethodBlocker mockWithMethodBlocker = new MockWithMethodBlocker();
        //first call allows from anywhere
        mockWithMethodBlocker.doF();
        final Runnable recurseMethodCallFailureRunnable = new Runnable() {
          
          @Override
          public void run() {
            mockWithMethodBlocker.doF();
          }
        };
        assertThrown(new ExpectedThrowable(new IllegalStateException(
            "class org.adligo.fabricate_tests.common.util.MockWithMethodBlocker.doD" + System.lineSeparator() +
            "[org.adligo.fabricate_tests.common.util.MethodBlockerTrial, org.adligo.fabricate_tests.common.util.MockWithMethodBlocker]" )), 
          new I_Thrower() {
          
          @Override
          public void run() {
            recurseMethodCallFailureRunnable.run();
          }
        });
      }
      
      
      @SuppressWarnings("unused")
      @Test
      public void testConstructorException() {
        
        assertThrown(new ExpectedThrowable(IllegalArgumentException.class), 
          new I_Thrower() {
          
          @Override
          public void run() {
            new MethodBlocker(null, 
                "run", 
                Collections.<String> emptyList());
            
          }
        });
        
        assertThrown(new ExpectedThrowable(IllegalArgumentException.class), 
          new I_Thrower() {
          
          @Override
          public void run() {
            new MethodBlocker(null, 
                "run", 
                Collections.<String> emptyList());
            
          }
        });
        assertThrown(new ExpectedThrowable(IllegalArgumentException.class), 
          new I_Thrower() {
          
          @Override
          public void run() {
            new MethodBlocker(this.getClass(), 
                null, 
                Collections.<String> emptyList());
            
          }
        });
        
        assertThrown(new ExpectedThrowable(IllegalArgumentException.class), 
          new I_Thrower() {
          
          @Override
          public void run() {
            new MethodBlocker(this.getClass(), 
                "run", 
                Collections.<String> emptyList());
            
          }
        });
        assertThrown(new ExpectedThrowable(IllegalArgumentException.class), 
          new I_Thrower() {
          
          @Override
          public void run() {
            new MethodBlocker(this.getClass(), 
                "run", null);
            
          }
        });
      }
      
      @Test
      public void testMethodWithBlockThrowsException() {
        
        String message = 
            "class org.adligo.fabricate_tests.common.util.MockWithMethodBlocker.doA" + System.lineSeparator() +
            "[org.adligo.fabricate_tests.common.util.MethodBlockerTrial]";
        final MockWithMethodBlocker mockWithMethodBlocker = new MockWithMethodBlocker();
        //first call allows anyone to call
        mockWithMethodBlocker.doA();
        assertThrown(new ExpectedThrowable(new IllegalStateException(message)), 
          new I_Thrower() {
          
          @Override
          public void run() {
            //note this fails from here as the class name is something like
            // MethodBlockerTrial$1, which is intended
            //this test is a bit confusing
            mockWithMethodBlocker.doA();
          }
        });
        // note this passes here because the class name is MethodBlockerTrial
        // which is intended
        // this test is a bit confusing
        mockWithMethodBlocker.doA();
        
        message = 
            "class org.adligo.fabricate_tests.common.util.MockWithMethodBlocker.doB" + System.lineSeparator() +
            "[org.adligo.fabricate_tests.common.util.MockWithMethodBlocker]";
        
        //first call allows anyone to call
        mockWithMethodBlocker.doB();
        assertThrown(new ExpectedThrowable(new IllegalStateException(message)), 
          new I_Thrower() {
            
            @Override
            public void run() {
              //note this fails from here as the class name is something like
              // MethodBlockerTrial$1, which is intended
              //this test is a bit confusing
              mockWithMethodBlocker.doB();
            }
        });
        
        // note this passes here because the class name is MockWithMethodBlock
        // which is intended
        // this test is a bit confusing
        mockWithMethodBlocker.doC();
        // note this passes here because the class name is MethodBlockerTrial
        // which is intended
        // this test is a bit confusing
        mockWithMethodBlocker.doD();
        
        // note this passes here because the class name is MockWithMethodBlock
        // which is intended
        // this test is a bit confusing
        mockWithMethodBlocker.doE();
        
        //note it is doB here 
        // and not doF, because doB is the method with the MethodBlocker
        assertThrown(new ExpectedThrowable(new IllegalStateException(
            "class org.adligo.fabricate_tests.common.util.MockWithMethodBlocker.doD" + System.lineSeparator() +
            "[org.adligo.fabricate_tests.common.util.MethodBlockerTrial, org.adligo.fabricate_tests.common.util.MockWithMethodBlocker]" )), 
            new I_Thrower() {
              
              @Override
              public void run() {
                //note this fails from here as the class name is something like
                // MockWithMethodBlocker$1, which is intended
                //this test is a bit confusing
                mockWithMethodBlocker.doF();
              }
            });
        
      }
}

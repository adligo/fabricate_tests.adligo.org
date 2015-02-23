package org.adligo.fabricate_tests.common.util;

import org.adligo.fabricate.common.util.MethodBlocker;

import java.util.ArrayList;
import java.util.List;


public class MockWithMethodBlocker {
	private MethodBlocker aBlock = getABlock();
	private MethodBlocker bBlock = getBBlock();
	private MethodBlocker dBlock = getDBlock();
	
	public void doA() {
		aBlock.checkAllowed();
	}

	private MethodBlocker getABlock() {
		List<String> allowedCallersClassNames = new ArrayList<String>();
		allowedCallersClassNames.add("org.adligo.fabricate_tests.common.util.MethodBlockerTrial");
		MethodBlocker mb = new MethodBlocker(MockWithMethodBlocker.class, "doA", allowedCallersClassNames);
		return mb;
	}
	
	public void doB() {
		bBlock.checkAllowed();
	}

	private MethodBlocker getBBlock() {
		List<String> allowedCallersClassNames = new ArrayList<String>();
		allowedCallersClassNames.add("org.adligo.fabricate_tests.common.util.MockWithMethodBlocker");
		MethodBlocker mb = new MethodBlocker(MockWithMethodBlocker.class, "doB", allowedCallersClassNames);
		return mb;
	}
	
	public void doC() {
		doB();
	}
	
	public void doD() {
		dBlock.checkAllowed();
	}

	private MethodBlocker getDBlock() {
		List<String> allowedCallersClassNames = new ArrayList<String>();
		allowedCallersClassNames.add("org.adligo.fabricate_tests.common.util.MethodBlockerTrial");
		allowedCallersClassNames.add("org.adligo.fabricate_tests.common.util.MockWithMethodBlocker");
		MethodBlocker mb = new MethodBlocker(MockWithMethodBlocker.class, "doD", allowedCallersClassNames);
		return mb;
	}
	
	public void doE() {
		doD();
	}
	
	/**
	 * This always throw the method block exception
	 * for method b as this method calls doD
	 * from something like MockWithMethodBlocker$1 which is 
	 * not a MockWithMethodBlocker
	 */
	public void doF() {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				doD();
			}
		};
		r.run();
	}
	
}

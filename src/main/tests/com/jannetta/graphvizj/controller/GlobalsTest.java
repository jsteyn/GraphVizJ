/**
 * 
 */
package com.jannetta.graphvizj.controller;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author jannetta
 *
 */
public class GlobalsTest {

	@Test
	public void test() {
		Globals.loadProperties();
		Globals.setProperty("test", "testvalue");
		assertSame("testvalue", Globals.getProperty("test"));
	}

}

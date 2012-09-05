package com.photon.phresco.testcases;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Test;

import com.photon.phresco.HelloWorld;

public class SampleHelloWorld extends FestSwingJUnitTestCase {

	private FrameFixture phresco;

	protected void onSetUp() {
		phresco = new FrameFixture(robot(), createNewEditor());
		phresco.show();
		phresco.maximize();
		System.out.println("**************Executed onsetup**************");
	}

	@RunsInEDT
	private static HelloWorld createNewEditor() {
		return execute(new GuiQuery<HelloWorld>() {
			protected HelloWorld executeInEDT() throws Throwable {
				return new HelloWorld();
			}
		});
	}

	@Test
	public void testHelloWorld() throws InterruptedException {
		Thread.sleep(5000);
		assertThat(phresco.label().text()).contains("Hello World");

	}
}
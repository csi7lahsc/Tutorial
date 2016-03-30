package leaveApplicationSys.test.testI;

import static org.junit.Assert.*;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import leaveApplicationSys.ui.Util;

import org.junit.Before;
import org.junit.Test;

/**
 * 工具类测试
 */
public class UtilTest {
	private JPanel panel;

	@Before
	public void setUp(){
		panel = new JPanel();
	}
	
	@Test
	public void testNotFound(){
		assertNull(Util.getComponent(panel, "abc"));
	}
	@Test
	public void testDirectlyEmbeddedComponent(){
		final String name = "a";
		Component component = new JLabel("x");
		component.setName(name);
		panel.add(component);
		assertEquals(component, Util.getComponent(panel, name));
	}
	@Test
	public void testSubComponent(){
		final String name = "a";
		Component component = new JLabel("x");
		component.setName(name);
		JPanel subPanel = new JPanel();
		subPanel.add(component);
		panel.add(subPanel);
		assertEquals(component, Util.getComponent(panel, name));
		
	}
}

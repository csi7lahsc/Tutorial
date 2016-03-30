package leaveApplicationSys.ui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

/**
 * 提取出getComponent方法，根据组件名称判断是否存在
 */
public class Util {
	public static Component getComponent(Container container, String name) {
		for(Component component: container.getComponents()){
			if(name.equals(component.getName())){
				return component;
			}else if(component instanceof Container){
				Container subContainer = (Container)component;
				Component subComponent = getComponent(subContainer, name);
				if(subComponent != null)
					return subComponent;
			}
		}
		return null;
	}
	
	public static Component getComponent(JFrame frame, String name) {
		Container container = frame.getContentPane();
		return getComponent(container, name);
	}
}

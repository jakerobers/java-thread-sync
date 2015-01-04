package robersj;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JCFAccessApp {

	private static List<Integer> alist;
	
	public static void main(String args[]) {
		alist = new LinkedList<Integer>();
		
		new JCFAccessApp();
	}
	
	public JCFAccessApp() {
		Thread second_thread = new SecondThread();
		second_thread.start();
		this.addElements(1000);
		try {
			second_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("####################");
		System.out.println("##### Size: "+ alist.size() +" #####");
		System.out.println("####################");
		System.out.println();
		System.out.println();
		System.out.println("####################");
		System.out.println("###### Order  ######");
		System.out.println("####################");
		for (int i = 0; i < alist.size(); i++) {
			System.out.println(alist.get(i));
		}
	}
	
	private void pause(long t) {
		try {
			this.wait(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void addElements(int count) {
		for ( int i = 0; i < count; i++ ) {
			synchronized (alist) {
				JCFAccessApp.alist.add(i);
				alist.notifyAll();
				
				try {
					if (i > count -1) {
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class SecondThread extends Thread {
		
		public void run() {
			addElements(1000);
		}
	}
}

package ind.ck.test;

public class ThreadTest2{
	
	
	
	Object obj1 = new Object();
	
	Object obj2 = new Object();


	Runnable run1 = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub

				synchronized (obj2) {
					synchronized (obj1) {
						System.out.println("obj1 visited");
						//obj1.notify();
					}
					try {
						System.out.println("obj2 wait");
						obj2.wait();
						System.out.println("obj2 never wait");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	};
	
	Runnable run2 = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("?");
			synchronized (obj1) {
				
				synchronized (obj2) {
					System.out.println("obj2 notify£¨£©");
					obj2.notify();
				}
			}

		}
	};
	
	public static void main(String[] args) throws InterruptedException {
//		ThreadTest2 tt2 = new ThreadTest2();
//		
//		Thread thr1 = new Thread(tt2.run1);
//		Thread thr2 = new Thread(tt2.run2);
//		thr1.start();
//		Thread.sleep(100);
//		thr2.start();
//		Thread.sleep(100);
		Test.openIt();
		
	}
	
	
	
}

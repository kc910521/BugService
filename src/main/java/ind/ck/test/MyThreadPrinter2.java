package ind.ck.test;

import java.util.UUID;

public class MyThreadPrinter2 implements Runnable {   
	  
    private String name;   
    private Object prev;   
    private Object self;   
  
    private MyThreadPrinter2(String name, Object prev, Object self) {   
        this.name = name;   
        this.prev = prev;   
        this.self = self;   
    }   
    

  
    @Override  
    public void run() {   
        int count = 10;   
        while (count > 0) {   
        	System.out.println();
            synchronized (prev) {   
                synchronized (self) {   
                    System.out.print(count+":"+name);   
                    count--;  
                    try{
                    	Thread.sleep(1);
                    }
                    catch (InterruptedException e){
                    	e.printStackTrace();
                    }
                    
                    self.notify();   
                }   
                try {
                    prev.wait();  //
                    System.out.println("("+name+".wait() over)");
                } catch (InterruptedException e) {   
                    e.printStackTrace();   
                }   
            }   
  
        }   
    }   
  
    public static void main(String[] args) throws Exception {   
        Object a = new Object();   
        Object b = new Object();   
        Object c = new Object();   
        /**
         * 具体：
         * 给本线程对应对象解锁，
         * （这时下一进程因为上一对象的锁解锁）
         * 通过上一线程对应对象给本线程上锁。
         * 
         * 当前线程中对象解锁，会从唤醒下一个线程从wait中
         * 因为下一个对象是因为当前线程中对象儿上锁
         * 
         * 
         * 线程初始顺序调整好后
         * 
         * pa开始了，
         * 先通知a解除wait（当前a并非wait状态）
         * 将本线程通过c阻塞（wait），并释放c的锁
         * 
         * pb开始，
         * 通知b解除wait（b也无wait状态）
         * 将本线程通过a阻塞wait，并释放a的锁
         * 
         * pc开始，
         * 通知c解除wait（！）
         * 因为pa在c的阻塞中，所以pa线程解除冻结，并完成
         * 将本线程通过b阻塞wait，并释放a的锁
         * 
         * 目前只有pa不在阻塞状态，且abc锁不存在
         * 
         * 状态初始化完成
         * 
         * 
         * 
         */
        
        MyThreadPrinter2 pa = new MyThreadPrinter2("A", c, a);
        MyThreadPrinter2 pb = new MyThreadPrinter2("B", a, b);
        MyThreadPrinter2 pc = new MyThreadPrinter2("C", b, c);
           
           
        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }   
}  

 
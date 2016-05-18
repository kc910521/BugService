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
         * ���壺
         * �����̶߳�Ӧ���������
         * ����ʱ��һ������Ϊ��һ�������������
         * ͨ����һ�̶߳�Ӧ��������߳�������
         * 
         * ��ǰ�߳��ж����������ӻ�����һ���̴߳�wait��
         * ��Ϊ��һ����������Ϊ��ǰ�߳��ж��������
         * 
         * 
         * �̳߳�ʼ˳������ú�
         * 
         * pa��ʼ�ˣ�
         * ��֪ͨa���wait����ǰa����wait״̬��
         * �����߳�ͨ��c������wait�������ͷ�c����
         * 
         * pb��ʼ��
         * ֪ͨb���wait��bҲ��wait״̬��
         * �����߳�ͨ��a����wait�����ͷ�a����
         * 
         * pc��ʼ��
         * ֪ͨc���wait������
         * ��Ϊpa��c�������У�����pa�߳̽�����ᣬ�����
         * �����߳�ͨ��b����wait�����ͷ�a����
         * 
         * Ŀǰֻ��pa��������״̬����abc��������
         * 
         * ״̬��ʼ�����
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

 
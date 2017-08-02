package let.algorithm;

/**
 * 单利的几种模式
 * @author ertaoL
 */
public class Singleton {
	/**
	 * 懒汉式单利模式（线程不安全）
	 * 		这种写法lazy loading很明显，但是致命的是在多线程不能正常工作。
	 */
//	private static Singleton instance;
//	private Singleton(){};
//	public static Singleton getInstance(){
//		if(instance==null){
//			instance = new Singleton();
//		}
//		return instance;
//	}
	
	/**
	 * 懒汉式单利模式（线程安全）
	 * 		这种写法能够在多线程中很好的工作，
	 * 		而且看起来它也具备很好的lazy loading，
	 * 		但是，遗憾的是，效率很低，99%情况下不需要同步。	
	 */
//	private static Singleton instance;
//	private Singleton(){};
//	public static synchronized  Singleton getInstance(){
//		if(instance==null){
//			instance = new Singleton();
//		}
//		return instance;
//	}
	
	
	/**
	 * 饿汉式单利模式 这种方式基于classloder机制避免了多线程的同步问题，
	 * 		不过，instance在类装载时就实例化，虽然导致类装载的原因有很多种，
	 * 		在单例模式中大多数都是调用getInstance方法， 但是也不能确定有其他的方式
	 * 		（或者其他的静态方法）导致类装载，这时候初始化instance显然没有达到lazy loading的效果。
	 */
//	private static Singleton instance = new Singleton();
//	private Singleton(){};
//	public static Singleton getInstance(){
//		return instance;
//	}
	
	/**
	 * 双重校验锁单利模式
	 *  在JDK1.5之后，双重检查锁定才能够正常达到单例效果
	 * 
	 * Volatile修饰的成员变量在每次被线程访问时，
	 * 		都强迫从共享内存中重读该成员变量的值。而且，
	 * 		当成员变量发生变化时，强迫线程将变化值回写到共享内存。
	 * 		这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。 
	 */
	private volatile static Singleton singleton;
	private Singleton(){}
	public static Singleton getInstance(){
		if(singleton==null){
			synchronized(Singleton.class) {
				if(singleton==null){
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}

import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

public class FlowTest {
	public static int main_cnt = 1;
	
	public static void main(String[] args) throws InterruptedException {
		
		// 1.创建自定义创建发布者
		MyPublisher<MyBean> publiser = new MyPublisher<MyBean>();

		// 2.创建自定义的处理器。
		MyProcessor processor = new MyProcessor();

		// 3.创建自定义的订阅者
		MySubscriber<MyBean> subscriber = new MySubscriber<MyBean>();

		// 4.将订阅者注册到处理器上，再将处理器注册到发布者上。
		// 处理器相当于一个中间人。当然订阅者可以直接注册到发布者上。无需处理器。
		processor.subscribe(subscriber);
		publiser.subscribe(processor);

		// 5. 生产数据, 并发布
		String[] input = { "A", "B", "C", "x", "y", "z" };
		ArrayList<MyBean> beanList = new ArrayList<MyBean>();

		for (String i : input) {
			MyBean bean = new MyBean();
			bean.setName(i);
			beanList.add(bean);
			publiser.submit(bean);
		}

		// 主线程延迟等待, 否则集合内的数据可能没有被处理完就被输出
//		Thread.currentThread().join(1000);
//		
//		// 6. 再次发布数据
//		for (MyBean bean : beanList) {
//			bean.setName(bean.getName().concat(" *"));
//			publiser.submit(bean);
//		}
	
		// 7. 结束后 关闭发布者
		publiser.close();

		// 主线程延迟等待, 否则集合内的数据可能没有被处理完就被输出
		Thread.currentThread().join(1000);
		System.out.println("集合内的数据是：");
		for (MyBean bean : beanList) {
			System.out.println(bean.getName());
		}
	}
}

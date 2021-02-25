import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class MyProcessor extends SubmissionPublisher<MyBean> implements Processor<MyBean, MyBean> {
	private Subscription subscription;
	
	
	@Override
	public void subscribe(Subscriber subscriber) {
		// 注册订阅者
		System.out.println("处理器注册新的订阅者");
		super.subscribe(subscriber);
	}	

	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("处理器被注册到发布者上");
		// 保存订阅关系, 需要用它来给发布者响应
		this.subscription = subscription;

		// 请求一个数据
		this.subscription.request(1);
	}

	@Override
	public void onNext(MyBean bean) {
		// 接受到一个数据, 处理
		String input = bean.getName();
		//System.out.println("处理器接受到数据: " + input + " - 这是此数据第" + ++bean.process_cnt + "次被处理");
		System.out.println("处理器接受到数据: " + input);
		
		// 大小写转换
		String res = input.toUpperCase();
		if (input == res) {
			res = input.toLowerCase();
		}
		
		bean.setName("(" + FlowTest.main_cnt++ +")"+ res);
		
		// 自己作为发布者，继续发布数据
		this.submit(bean);

		// 处理完调用request再请求一个数据
		this.subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		// 出现了异常(例如处理数据的时候产生了异常)
		throwable.printStackTrace();

		// 我们可以告诉发布者, 后面不接受数据了
		this.subscription.cancel();
	}

	@Override
	public void onComplete() {
		// 全部数据处理完了(发布者关闭了)
		System.out.println("处理器处理完了");
        // 关闭发布者
        this.close();
	}
}

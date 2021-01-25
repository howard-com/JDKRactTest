import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscriber<T> implements Subscriber<MyBean> {
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("订阅者被注册到发布者上");

		// 保存订阅关系, 需要用它来给发布者响应
		this.subscription = subscription;

		// 请求一个数据
		this.subscription.request(1);
	}

	@Override
	public void onNext(MyBean bean) {
		// 接受到一个数据, 处理
		String input = bean.getName();
		System.out.println("处理器接受到数据: " + input);

		// 大小写转换
		String res = (String) new StringBuffer("[").append(input).append("]").toString();
		System.out.println("订阅者处理数据: " + res);
		bean.setName(res);
		
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
		System.out.println("订阅者处理完了");
	}
}

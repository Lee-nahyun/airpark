package kr.airport.parking.reduction.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableAsync
@ComponentScan(basePackages={"kr.airport.parking.reduction"}, excludeFilters={@Filter(Configuration.class)})
public class RightShareAsyncConfig implements AsyncConfigurer {

	/** 기본 Thread 수 */
	private static int TASK_CORE_POOL_SIZE = 10;
	/** 최대 Thread 수 */
	private static int TASK_MAX_POOL_SIZE = 20;
	/** QUEUE 수 */
	private static int TASK_QUEUE_CAPACITY = 100;
	private static String EXECUTOR_BEAN_NAME = "rightShareAsyncExecutor";

		
	@Override
	public Executor getAsyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setBeanName(EXECUTOR_BEAN_NAME);
        executor.setKeepAliveSeconds(20);
        executor.setAwaitTerminationSeconds(20);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;

	}
	

}
 
package com.demo.token.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public Queue Myqueue()     // Define a queue
	{
		return new Queue("myQueue", false);
	}
	
	@Bean
	public TopicExchange myExchage() // Define a topic exchange
	{
		return new TopicExchange("myExchage");
		
	}
	
	@Bean  // Bind the queue to the exchange with a routing key
	public Binding binding(Queue Myqueue,TopicExchange myExchage)
	{
		return BindingBuilder.bind(Myqueue).to(myExchage).with("my.routing.key");
		
	}
}

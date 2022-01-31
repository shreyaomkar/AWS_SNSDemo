package com.amazonaws.samples;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SNSTopic 
{
	private String topic;
	
	public SNSTopic() {}

	public SNSTopic(String topic) 
	{
		super();
		this.topic = topic;
	}

	public String getTopic() 
	{
		return topic;
	}

	public void setTopic(String topic) 
	{
		this.topic = topic;
	}
	
	public String createTopic(AmazonSNS sns, String topicName)
	{
		return sns.createTopic(new CreateTopicRequest(topicName)).getTopicArn();
	}
	
	public PublishResult publishTopic(AmazonSNS sns,String topicArn,String msg)
	{
		final PublishRequest publishRequest = new PublishRequest(topicArn, msg);
		return sns.publish(publishRequest);
	}
}

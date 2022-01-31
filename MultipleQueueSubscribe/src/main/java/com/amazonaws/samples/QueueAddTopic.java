package com.amazonaws.samples;

import java.util.List;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.SetSubscriptionAttributesRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class QueueAddTopic 
{	
	String topicName;
	String queueName;
	
	public QueueAddTopic(){}
	
	public QueueAddTopic(String topicName,String queueName)
	{
		this.topicName=topicName;
		this.queueName=queueName;
	}
	
	public String createTopic(AmazonSNS sns, String topicName)
	{
		return sns.createTopic(new CreateTopicRequest(topicName)).getTopicArn();
	}
	
	public String createQueue(AmazonSQS sqs,String queueName)
	{
		return sqs.createQueue(new CreateQueueRequest(queueName)).getQueueUrl();
	}
	
	public List<Message> getMessagesFromQueue(AmazonSQS sqs,String queueUrl)
	{
		return sqs.receiveMessage(new ReceiveMessageRequest(queueUrl)).getMessages();
	}
		
	public void applyFilter(AmazonSNS sns,String subscribeValue,String filterPolicy)
	{
	     SetSubscriptionAttributesRequest request = new SetSubscriptionAttributesRequest(subscribeValue, "FilterPolicy", filterPolicy);
	     sns.setSubscriptionAttributes(request);
	}
}

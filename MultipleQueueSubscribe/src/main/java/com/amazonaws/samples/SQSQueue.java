package com.amazonaws.samples;

import java.util.List;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSQueue 
{
	private String queue;
	
	public SQSQueue() {}

	public SQSQueue(String queueName) {
		super();
		this.queue = queueName;
	}

	public String getQueueName() 
	{
		return queue;
	}

	public void setQueueName(String queueName) 
	{
		this.queue = queueName;
	}
	
	public String createQueue(AmazonSQS sqs,String queueName)
	{
		return sqs.createQueue(new CreateQueueRequest(queueName)).getQueueUrl();
	}
	
	public String getQueueUrl(AmazonSQS sqs,String queueName)
	{
        GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
        return sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
    }
	
	public ListQueuesResult listQueues(AmazonSQS sqs)
	{
	    return sqs.listQueues();
	}
	 
	public void sendMessageToQueue(AmazonSQS sqs,String queueUrl, String message)
	{
	    sqs.sendMessage(new SendMessageRequest(queueUrl, message));
	}
	 
	public List<Message> getMessagesFromQueue(AmazonSQS sqs,String queueUrl)
	{
	     ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
	     List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
	     return messages;
	}
	 
	public void deleteMessageFromQueue(AmazonSQS sqs,String queueUrl, Message message)
	{
         String messageRecieptHandle = message.getReceiptHandle();
         sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messageRecieptHandle));
    }
	 
}

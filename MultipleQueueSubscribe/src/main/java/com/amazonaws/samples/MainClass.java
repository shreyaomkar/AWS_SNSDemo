package com.amazonaws.samples;

import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;


public class MainClass
{
	public static void main(String args[])
	{
		
			QueueAddTopic obj=new QueueAddTopic();
			final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
	        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
	        
	        final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

	        String myTopicArn = obj.createTopic(sns, "SampleTopic");
	        String myQueueUrl = obj.createQueue(sqs, "SampleQueue");
	        
	        String bucketName = "storagebigbucket";
	        
	        MessageAttributeValue msgValue=new MessageAttributeValue();
			msgValue.setDataType("String");
			msgValue.setStringValue("Sample");
			PublishRequest pubRequest = new PublishRequest();
			pubRequest.addMessageAttributesEntry("key", msgValue);
			pubRequest.setTopicArn(myTopicArn);
			pubRequest.setSubject("Subject");
			pubRequest.setMessage("Hello SNS World");
	        
	        String value=Topics.subscribeQueue(sns, sqs, myTopicArn, myQueueUrl);
	        System.out.println("Subscription:"+value);
	        
	        sns.publish(pubRequest);    

	        List<Message> messages = sqs.receiveMessage(new ReceiveMessageRequest(myQueueUrl)).getMessages();
	        for (Message message:messages) 
	        {
	            System.out.println("Attribute"+message);
	            s3Client.putObject(bucketName, "MessageFromSQS", String.valueOf(message));
	        }
	        
	        String filterPolicyString = "{\"store\":[\"example_corp\"],\"event\":[\"order_placed\"]}";
	        
	        obj.applyFilter(sns,value, filterPolicyString);
				
	        System.out.println("Topic attributes:"+sns.getTopicAttributes(myTopicArn));
	        
	}
}

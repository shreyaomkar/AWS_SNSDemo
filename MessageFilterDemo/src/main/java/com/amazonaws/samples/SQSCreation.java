package com.amazonaws.samples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSCreation 
{
	public static void main(String args[])
	{
		 final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

	        System.out.println("===============================================");
	        System.out.println("Getting Started with Amazon SQS Standard Queues");
	        System.out.println("===============================================\n");

	        try {
	            // Create a queue.
	            /*System.out.println("Creating a new SQS queue called MyQueue.\n");
	            final CreateQueueRequest createQueueRequest =
	                    new CreateQueueRequest("MyQueue");
	            final String myQueueUrl = sqs.createQueue(createQueueRequest)
	                    .getQueueUrl();*/
	        	
	        	//Get the url of existing queue
	        	String queue_url = sqs.getQueueUrl("MyQueue").getQueueUrl();

	            // List all queues.
	            System.out.println("Listing all queues in your account.\n");
	            for (final String queueUrl : sqs.listQueues().getQueueUrls()) 
	            {
	                System.out.println("  QueueUrl: " + queueUrl);
	            }
	            
	            //Create Message attributes in queue
	           /* final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
	            messageAttributes.put("Name", new MessageAttributeValue()
	                    .withDataType("String")
	                    .withStringValue("Shreya"));*/
	            
	            final MessageAttributeValue messageAttributeValue = new MessageAttributeValue()
	                    .withDataType("String")
	                    .withStringValue("life");
	            
	            // Send a message with an attribute
	            /*final SendMessageRequest sendMessageRequest = new SendMessageRequest();
	            sendMessageRequest.withMessageBody("This is my message text.");
	            sendMessageRequest.withQueueUrl(queue_url);
	            sendMessageRequest.withMessageAttributes(messageAttributes);
	            sqs.sendMessage(sendMessageRequest);*/
	            
	            //Find a queue using prefix
	            String name_prefix = "Consume";
	            ListQueuesResult lq_result = sqs.listQueues();
	            lq_result = sqs.listQueues(new ListQueuesRequest(name_prefix));
	            System.out.println("Queue URLs with prefix: " + name_prefix);
	            for (String url : lq_result.getQueueUrls()) {
	                System.out.println(url);
	            }
	            
	            //Recive Messages from queue
	           /* List<Message> messages = sqs.receiveMessage(queue_url).getMessages();
	            for (Message url : messages)
	            {
	                System.out.println("Messages:"+url);
	                for (final Entry<String, String> entry : url.getAttributes().entrySet()) 
	                {
	                    System.out.println("Attribute");
	                    System.out.println("  Name:  " + entry.getKey());
	                    System.out.println("  Value: " + entry.getValue());
	                }
	            }*/
	            
	            
	            final List<Message> messages = sqs.receiveMessage(queue_url).getMessages();
	            for (final Message message : messages) {
	                System.out.println("Message");
	                System.out.println("  MessageId:     " + message.getMessageId());
	                System.out.println("  ReceiptHandle: " + message.getReceiptHandle());
	                System.out.println("  MD5OfBody:     " + message.getMD5OfBody());
	                System.out.println("  Body:          " + message.getBody());
	                for (final Entry<String, String> entry : message.getAttributes().entrySet()) {
	                    System.out.println("Attribute");
	                    System.out.println("  Name:  " + entry.getKey());
	                    System.out.println("  Value: " + entry.getValue());
	                }
	            }
	            System.out.println();
	            
	            
	        }
	        catch (final AmazonServiceException ase) 
	        {
		         System.out.println("Caught an AmazonServiceException, which means " +
		                 "your request made it to Amazon SQS, but was " +
		                 "rejected with an error response for some reason.");
		         System.out.println("Error Message:    " + ase.getMessage());
		         System.out.println("HTTP Status Code: " + ase.getStatusCode());
		         System.out.println("AWS Error Code:   " + ase.getErrorCode());
		         System.out.println("Error Type:       " + ase.getErrorType());
		         System.out.println("Request ID:       " + ase.getRequestId());
		     } 
	        catch (final AmazonClientException ace) 
	        {
		         System.out.println("Caught an AmazonClientException, which means " +
		                 "the client encountered a serious internal problem while " +
		                 "trying to communicate with Amazon SQS, such as not " +
		                 "being able to access the network.");
		         System.out.println("Error Message: " + ace.getMessage());
		     }
	        
	}
}

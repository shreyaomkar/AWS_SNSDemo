package com.amazonaws.samples;

import java.util.List;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SetSubscriptionAttributesRequest;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class QueueAddTopic 
{
	public static void main(String args[])
	{
        final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        String myTopicArn = sns.createTopic(new CreateTopicRequest("topicName")).getTopicArn();
        String myQueueUrl = sqs.createQueue(new CreateQueueRequest("queueName")).getQueueUrl();
        
        String value=Topics.subscribeQueue(sns, sqs, myTopicArn, myQueueUrl);
        System.out.println("Subscription:"+value);
        
        sns.publish(new PublishRequest(myTopicArn, "Hello SNS World").withSubject("Subject"));

        List<Message> messages = sqs.receiveMessage(new ReceiveMessageRequest(myQueueUrl)).getMessages();
        for (Message message:messages) 
        {
            System.out.println("Attribute"+message);
        }
        
        String filterPolicyString = "{\"store\":[\"example_corp\"],\"event\":[\"order_placed\"]}";
        SetSubscriptionAttributesRequest request = new SetSubscriptionAttributesRequest(value, "FilterPolicy", filterPolicyString);
        //request.setSubscriptionArn(myQueueUrl);
        sns.setSubscriptionAttributes(request);
        
	}
}

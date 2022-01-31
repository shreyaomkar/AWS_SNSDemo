package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SNSApplication 
{
	public static void main(String args[])
	{
		/*String ACCESS_KEY = "AKIARXBJSPVLQJESECGS";
        String SECRET_KEY = "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";
		String msg = "If you receive this message, publishing a message to an Amazon SNS topic works.";
		PublishRequest publishRequest = new PublishRequest("arn:aws:sns:us-east-1:118198795607:Sample", msg);
		AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
		PublishResult result = snsClient.publish(new PublishRequest()
				  .withTopicArn("arn:aws:sns:us-east-1:118198795607:Sample")
				  .withMessage(msg));
				System.out.println("Msg ID:"+result.getMessageId());*/

	
		AmazonSNSClient snsClient = new AmazonSNSClient();
        String message = "My SMS message";
        String phoneNumber = "shreyapk2@gmail.com";
        Map<String, MessageAttributeValue> smsAttributes = 
                new HashMap<String, MessageAttributeValue>();
        //<set SMS attributes>
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
	}

	public static void sendSMSMessage(AmazonSNSClient snsClient, String message, 
		String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) 
	{
        PublishResult result = snsClient.publish(new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber)
                        .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
	}
}


public static void main(String[] args)
{
	String ACCESS_KEY = "AKIARXBJSPVLQJESECGS";
    String SECRET_KEY = "BZCKVq0Xm9whMt1Ij+yD2RaaVhSy52fE2f33dzFR";
    String topicName = "Sample";
    String message = "Hello from SNS";
    // Populate the list of phoneNumbers
    List<String> phoneNumbers = new ArrayList<String>(); 
    phoneNumbers.add("7709618839");// Ex: +919384374XX
    AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
    // Create SMS Topic
    String topicArn = createSNSTopic(snsClient, topicName);
    System.out.println("Create topic Name:"+topicArn);
    // Subcribe Phone Numbers to Topic
    subscribeToTopic(snsClient, topicArn, "sms", phoneNumbers);
    // Publish Message to Topic
    sendSMSMessageToTopic(snsClient, topicArn, message);

}

public static void sendSMSMessage(AmazonSNSClient snsClient,String message, String phoneNumber) 
{
	PublishResult result = snsClient.publish(new PublishRequest()
	  .withMessage(message)
	  .withPhoneNumber(phoneNumber));
	System.out.println("Result:"+result); // Prints the message ID.
}

public static String createSNSTopic(AmazonSNSClient snsClient, String topicName) 
{
	CreateTopicRequest createTopic = new  
	           CreateTopicRequest(topicName);
	CreateTopicResult result =
	   snsClient.createTopic(createTopic);
	return result.getTopicArn();
}

public static void subscribeToTopic(AmazonSNSClient snsClient, String topicArn,String protocol, List<String> phoneNumbers) 
{
	for (String phoneNumber : phoneNumbers) 
	{
		SubscribeRequest subscribe = new SubscribeRequest(topicArn, protocol, phoneNumber);
		snsClient.subscribe(subscribe);
	}
}
public static String sendSMSMessageToTopic(AmazonSNSClient snsClient, String topicArn, String message) 
{
	PublishResult result = snsClient.publish(new PublishRequest()
	  .withTopicArn(topicArn)
	  .withMessage(message));
	System.out.println("Msg ID:"+result.getMessageId());
	return result.getMessageId();
}




Create topic Name:arn:aws:sns:us-east-1:118198795607:Sample
Msg ID:97648e3c-cddb-5b1b-b04c-d81410513ec8


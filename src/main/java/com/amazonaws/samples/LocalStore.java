package com.amazonaws.samples;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;

public class LocalStore 
{

	public static void main(String[] args) throws IOException
	{
		String clientRegion = "ap-south-1";
        String bucketName = "storagebigbucket";
        String key = "C#.txt";
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        try 
        {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();
            
//            S3Object s3object = s3Client.getObject(bucketName, key);
//            S3ObjectInputStream inputStream = s3object.getObjectContent();
//            FileUtils.copyInputStreamToFile(inputStream, new File("/Users/user/Desktop/hello.txt"));
            
            ObjectListing objects = s3Client.listObjects(bucketName);
            do 
            {
                    for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) 
                    {
                            System.out.println(objectSummary.getKey() + "\t" +
                                    objectSummary.getSize() + "\t" +
                                    StringUtils.fromDate(objectSummary.getLastModified()));
                    }
                    objects = s3Client.listNextBatchOfObjects(objects);
            } while (objects.isTruncated());
            
            System.out.println("Download Object");
            
            
            //s3Client.getObject(new GetobjectRequest(bucketName), new File("C://Users//shreya.patil//Documents/hello.txt"));
            
            //s3Client.getObject( new GetObjectRequest(bucket.getName(), "perl_poetry.pdf"), new File("/home/larry/documents/perl_poetry.pdf"))
            //);
            
        }
        catch(AmazonServiceException e) 
        {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) 
        {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

	}

}

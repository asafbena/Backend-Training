package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerProperties{

	@JsonProperty("key.deserializer")
	private String keyDeserializer;

	@JsonProperty("value.deserializer")
	private String valueDeserializer;

	@JsonProperty("pollingTimeoutInMilliSeconds")
	private int pollingTimeoutInMilliSeconds;

	@JsonProperty("groupId")
	private String groupId;

	@JsonProperty("specific.avro.reader")
	private boolean specificAvroReader;

	public String getKeyDeserializer(){
		return keyDeserializer;
	}

	public String getValueDeserializer(){
		return valueDeserializer;
	}

	public int getPollingTimeoutInMilliSeconds(){
		return pollingTimeoutInMilliSeconds;
	}

	public String getGroupId(){
		return groupId;
	}

	public boolean isSpecificAvroReader(){
		return specificAvroReader;
	}

	@Override
 	public String toString(){
		return 
			"ConsumerProperties{" + 
			"key.deserializer = '" + keyDeserializer + '\'' + 
			",value.deserializer = '" + valueDeserializer + '\'' + 
			",pollingTimeoutInMilliSeconds = '" + pollingTimeoutInMilliSeconds + '\'' + 
			",groupId = '" + groupId + '\'' + 
			",specific.avro.reader = '" + specificAvroReader + '\'' + 
			"}";
		}
}
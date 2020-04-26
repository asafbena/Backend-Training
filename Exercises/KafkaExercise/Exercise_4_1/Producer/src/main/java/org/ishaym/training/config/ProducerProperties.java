package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProducerProperties{

	@JsonProperty("value.serializer")
	private String valueSerializer;

	@JsonProperty("key.serializer")
	private String keySerializer;

	public String getValueSerializer(){
		return valueSerializer;
	}

	public String getKeySerializer(){
		return keySerializer;
	}

	@Override
 	public String toString(){
		return 
			"ProducerProperties{" + 
			"value.serializer = '" + valueSerializer + '\'' + 
			",key.serializer = '" + keySerializer + '\'' + 
			"}";
		}
}
package com.upiconsumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.upiconsumer.dto.Product;
@RestController
@RequestMapping("api/v1/consumers")
public class UpiConsumerController {
	@Autowired
	DiscoveryClient ds; //eureka injected
	@GetMapping
	public List<Product> getAll(){
		List<ServiceInstance> ll= ds.getInstances("SYNECHRONEPUNEPRODUCER");
		ServiceInstance ss = ll.get(0);
		String url = ss.getUri().toString();
		RestTemplate rTemplate = new RestTemplate();
		List<Product> ll1 = rTemplate.getForObject(url+"/api/v1", List.class);
		return ll1;
	}
	
	@PostMapping
	public String createPost(@RequestBody Product ee) {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject("http://localhost:9092/api/v1", ee, String.class);
		return response;
	}

	@PutMapping("/{id}")
	public String updatePost(@RequestBody Product ee, @PathVariable String id) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put("http://localhost:9092/api/v1" + id, ee);
		return "record updated";
	}

	@DeleteMapping("/{id}")
	public String deletePost(@PathVariable String id) {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete("http://localhost:9092/api/v1" + id);

		return "deleted";
	}
}

package com.kafka.producer.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.producer.domain.Employee;

@RestController
@RequestMapping("kafka")
public class EmployeeController {

	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaIdTemplate;
 
	private static final String TOPIC = "employee_sample_topic";
	
	private static final String ID_TOPIC = "employee_id_sample_topic";

	
	@PostMapping("/publish/{id}")
	public ResponseEntity<Object> postEmployeeData(@PathVariable("id") final String id) {

		kafkaIdTemplate.send(ID_TOPIC, id);

		return ResponseEntity.ok("Succusfully Published");
	}
	
	@PostMapping("/publish")
	public ResponseEntity<Object> postEmployeeData(@RequestBody Employee employee) {

		kafkaTemplate.send(TOPIC, employee);

		return ResponseEntity.ok("Succusfully Published");
	}

}
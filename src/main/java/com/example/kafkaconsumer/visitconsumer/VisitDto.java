package com.example.kafkaconsumer.visitconsumer;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitDto {
	private Integer patientId;
	private Long visitId;
	private String visitNo;
	private Long admittedBy;
	private Timestamp admissionDateTime;
	private String patientClass;
	private String queueNo;
}

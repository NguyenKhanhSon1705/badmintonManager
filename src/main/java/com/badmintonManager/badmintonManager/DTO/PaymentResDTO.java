package com.badmintonManager.badmintonManager.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResDTO implements Serializable {
	private String status;
	private String massage;
	private String URL;
}

package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.LoginDTO;
import service.AuthService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController 
{
	@Autowired
	private AuthService service;
	
	@PostMapping(value ="/login")
	public ResponseEntity<Void> login(@RequestBody LoginDTO dto)
	{	
		if(service.Authenticate(dto))
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
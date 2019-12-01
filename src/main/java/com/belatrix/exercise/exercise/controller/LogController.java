package com.belatrix.exercise.exercise.controller;

import com.belatrix.exercise.exercise.model.LogRequest;
import com.belatrix.exercise.exercise.service.LogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Log Operations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController {


	@Autowired
	private LogService logService;

	@PostMapping("/register")
	@ResponseBody
	@ApiOperation(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "Creacion de log", notes = "Creación de log demo.", code = 200)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request, datos incompletos"),
			@ApiResponse(code = 200, message = "Creación exitosa")})
	public ResponseEntity<?> create(@RequestBody LogRequest log) throws Exception{
		return ResponseEntity.ok().body(logService.registerLog(log));
	}


//	@GetMapping("/list")
//	@ResponseBody
//	@ApiOperation(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
//			value = "Lista de logs de bd", notes = "Lista los log registrados en la BD.",
//			responseContainer = "Array", response = Client.class, code = 200)
//	public ResponseEntity<?> getListItems() {
//		return ResponseEntity.ok().body(clientService.listClient());
//
//
//	}



}

package springData.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.UserDto;
import springData.model.User;
import springData.security.config.JwtTokenUtil;
import springData.security.model.JwtRequest;
import springData.security.model.JwtResponse;
import springData.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springData.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		try {
			if (userService.findUserByUserName(user.getUserName()) == null && user.getUserName()!=null && user.getPassword()!=null)
				return ResponseEntity.ok(userDetailsService.save(user));
			throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
		}catch (Exception e){
			throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
		}
	}
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable(name = "id") Integer id, @RequestBody UserDto user, HttpServletRequest request) throws Exception{
			if (userService.findById(id) == null)
				throw  new ResponseStatusException(HttpStatus.resolve(404), "Not found");
			String token = request.getHeader("Authorization").substring(7);
			if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(userService.findById(id).getUserName()))
				throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
			if (user.getUserName() == null || user.getPassword() == null)
				throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
			User user1 = userService.convertDtoToUserUpdate(user, id);
			if (user1!=null)
				return ResponseEntity.ok(userDetailsService.put(user, id));
			throw  new ResponseStatusException(HttpStatus.resolve(400), "Not found");
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
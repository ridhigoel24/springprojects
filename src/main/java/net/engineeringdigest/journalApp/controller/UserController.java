package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
private UserService userService;
@GetMapping
public List<User> getAllUsers(){
    return userService.getAll();
}
@PostMapping
public void createUser(@RequestBody User user){
    userService.saveEntry(user);
}
@PutMapping
 public ResponseEntity<?> updateUser(@RequestBody User user){
    User userInDb = userService.findByUserName(user.getUserName());
    if (userInDb!=null){
        userInDb.setPassword((user.getPassword()));
        userInDb.setUserName(user.getUserName());
        userService.saveEntry(userInDb);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}

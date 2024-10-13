package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
@Autowired
private JournalEntryService journalEntryService;
    @GetMapping
    public ResponseEntity<?> getAll(){
       List<JournalEntry> all = journalEntryService.getAll();
if (all!=null && !all.isEmpty()){
    return new ResponseEntity<>(all, HttpStatus.OK);
}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry  =  journalEntryService.findById(myId);
    if (journalEntry.isPresent()){
        return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public  ResponseEntity<?>  updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        JournalEntry old = new JournalEntryService().findById(id).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() :  old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?>  deleteById(@PathVariable ObjectId id){
        journalEntryService.deleteId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

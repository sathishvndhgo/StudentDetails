package com.example.demo;



import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class ItemController {
	
	  private final ItemService itemService;
	    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	    @Autowired
	    public ItemController(ItemService itemService) {
	        this.itemService = itemService;
	    }
	    
	    
	    @PostMapping
	    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
	        LOGGER.info("Request received to save item: {}", item);
	        itemService.saveItem(item);
	        return ResponseEntity.created(URI.create("/items/" + item.getId())).body(item);
	    }
	    
	    

	    @GetMapping
	    public List<Item> getAllItems() {
	        LOGGER.info("Request received to retrieve all items");
	        return itemService.getAllItems();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Item> getItemById(@PathVariable String id) {
	        LOGGER.info("Request received to retrieve item with ID {}", id);
	        Optional<Item> item = itemService.getItemById(id);
	        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Item> updateItem(@PathVariable String id , @RequestBody Item newItem) {
	        LOGGER.info("Request received to update item with ID {}", id);
	        itemService.updateItem(id, newItem);
	        return ResponseEntity.ok(newItem);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteItemById(@PathVariable String id) {
	        LOGGER.info("Request received to delete item with ID {}", id);
	        Optional<Item> item = itemService.getItemById(id);
	        if (item.isPresent()) {
	            itemService.deleteItemById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.warn("Item with ID {} not found in the database", id);
	            return ResponseEntity.notFound().build();
	        }
	    }




}

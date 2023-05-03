package com.example.demo;




import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    
    
    public void saveItem(Item item) {
        itemRepository.save(item);
        LOGGER.info("Item saved to the database: {}", item);
    }
     
    

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        LOGGER.info("Retrieved all items from the database");
        return items;
    }

    public Optional<Item> getItemById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()) {
            LOGGER.info("Retrieved item with ID {} from the database", id);
        } else {
            LOGGER.warn("Item with ID {} not found in the database", id);
        }
        return item;
    }

   
     
    public void updateItem(String id, Item newItem) {
        LOGGER.info("Request received to update item with ID {}", id);
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            newItem.setId(id);
            itemRepository.save(newItem);
            LOGGER.info("Item with ID {} updated successfully", id);
        } else {
            LOGGER.warn("Item with ID {} not found in the database", id);
            
        }
    }
    
    
    public void deleteItemById(String id) {
        itemRepository.deleteById(id);
        LOGGER.info("Item with ID {} deleted from the database", id);
    }
}


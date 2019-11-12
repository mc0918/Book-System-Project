package com.trilogyed.bookservice.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(name = "note-service")
public interface NoteLookupClient {
    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    public List<Map<String, String>> getNotesByBook(@PathVariable int book_id );
}
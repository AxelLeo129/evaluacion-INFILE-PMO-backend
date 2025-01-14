package com.example.controller;

import com.example.model.NewsResponse;
import com.example.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * Endpoint para obtener noticias de la API externa.
     *
     * @param accessKey La clave de acceso para la API.
     * @return La respuesta JSON mapeada.
     */
    @GetMapping
    public ResponseEntity<NewsResponse> getNews(@RequestParam String accessKey, @RequestParam("") String limit) {
        NewsResponse newsResponse = newsService.getNews(accessKey, limit);
        return ResponseEntity.ok(newsResponse);
    }

}

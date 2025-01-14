package com.example.service;

import com.example.model.NewsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NewsService {

    private final WebClient webClient;

    public NewsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.mediastack.com/v1").build();
    }

    /**
     * Consume la API de noticias y retorna la respuesta mapeada.
     *
     * @param accessKey La clave de acceso para la API.
     * @return Un objeto NewsResponse con los datos obtenidos de la API.
     */
    public NewsResponse getNews(String accessKey, String limit) {
        String endpoint = "/news?access_key=" + accessKey;
        if(!limit.isEmpty()) endpoint += "&limit=8";

        Mono<NewsResponse> responseMono = webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(NewsResponse.class);

        return responseMono.block();
    }

}

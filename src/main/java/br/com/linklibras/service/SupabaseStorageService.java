package br.com.linklibras.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    private final RestClient restClient = RestClient.create();



    private static final String BUCKET = "fotos-interpretes";

    public String uploadFoto(MultipartFile foto) throws IOException {

        if (foto == null || foto.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma foto foi enviada.");
        }

        String tipo = foto.getContentType();

        if (tipo == null ||
                !(tipo.equals("image/jpeg") ||
                        tipo.equals("image/png") ||
                        tipo.equals("image/webp"))) {
            throw new IllegalArgumentException(
                    "Formato de imagem não permitido."
            );
        }

        String extensao;

        switch (tipo) {
            case "image/jpeg" -> extensao = ".jpg";
            case "image/png" -> extensao = ".png";
            case "image/webp" -> extensao = ".webp";
            default -> throw new IllegalArgumentException(
                    "Formato inválido."
            );
        }

        String nomeArquivo = UUID.randomUUID() + extensao;

        String caminho = "interpretes/" + nomeArquivo;

        restClient.post()
                .uri(supabaseUrl + "/storage/v1/object/"
                        + BUCKET + "/" + caminho)
                .header("apikey", supabaseKey)
                .contentType(MediaType.parseMediaType(tipo))
                .body(foto.getBytes())
                .retrieve()
                .toBodilessEntity();

        return caminho;
    }

    public String gerarUrlAssinada(String caminho) {

        if (caminho == null || caminho.isBlank()) {
            return null;
        }

        String baseUrl = supabaseUrl.replaceAll("/+$", "");

        Map<?, ?> resposta = restClient.post()
                .uri(baseUrl + "/storage/v1/object/sign/"
                        + BUCKET + "/" + caminho)
                .header("apikey", supabaseKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("expiresIn", 86400))
                .retrieve()
                .body(Map.class);

        if (resposta == null || resposta.get("signedURL") == null) {
            throw new RuntimeException("Não foi possível gerar a URL da foto do intérprete.");
        }

        String urlAssinada = resposta.get("signedURL").toString();

        return supabaseUrl + "/storage/v1" + urlAssinada;
    }
}

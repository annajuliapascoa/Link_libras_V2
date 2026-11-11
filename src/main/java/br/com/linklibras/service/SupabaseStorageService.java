package br.com.linklibras.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}

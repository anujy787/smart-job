package edu.neu.csye7374.smartjob.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendWelcomeEmail(String toEmail, String firstName, boolean isEmployer) {
        String templatePath = isEmployer ? "/templates/welcome_employer.html" : "/templates/welcome_jobseeker.html";
        String emailContent = loadTemplate(templatePath);
        emailContent = emailContent.replace("{{firstName}}", firstName);

        String fromEmail = String.format("welcome@%s", domain);
        String body = String.format("from=%s&to=%s&subject=%s&html=%s",
            URLEncoder.encode(fromEmail, StandardCharsets.UTF_8),
            URLEncoder.encode(toEmail, StandardCharsets.UTF_8),
            URLEncoder.encode("Welcome to Smart-Job", StandardCharsets.UTF_8),
            URLEncoder.encode(emailContent, StandardCharsets.UTF_8));

        String url = String.format("https://api.mailgun.net/v3/%s/messages", domain);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("api", apiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to send email");
        }
    }

    public String loadTemplate(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getResource(path).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }

    public void sendCustomEmail(String toEmail, String subject, String htmlContent) {
        String fromEmail = String.format("no-reply@%s", domain);
        String body = String.format("from=%s&to=%s&subject=%s&html=%s",
            URLEncoder.encode(fromEmail, java.nio.charset.StandardCharsets.UTF_8),
            URLEncoder.encode(toEmail, java.nio.charset.StandardCharsets.UTF_8),
            URLEncoder.encode(subject, java.nio.charset.StandardCharsets.UTF_8),
            URLEncoder.encode(htmlContent, java.nio.charset.StandardCharsets.UTF_8));

        String url = String.format("https://api.mailgun.net/v3/%s/messages", domain);

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBasicAuth("api", apiKey);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(body, headers);

        org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.POST, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to send email");
        }
    }
}
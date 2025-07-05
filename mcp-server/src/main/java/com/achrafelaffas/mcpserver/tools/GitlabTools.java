package com.achrafelaffas.mcpserver.tools;


import com.achrafelaffas.mcpserver.entity.GitLabProject;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitlabTools {

    @Value("${gitlab.api.token}")
    private String gitlabToken;
    @Value("${gitlab.api.url}")
    private String gitlabUrl;

    @Tool(description = "This function list all the project inside gitlab account")
    public List<GitLabProject> getOwnedProjects() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitlabToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitLabProject[]> response = restTemplate.exchange(
                "https://gitlab.com/api/v4/projects?owned=true",
                HttpMethod.GET,
                entity,
                GitLabProject[].class
        );

        assert response.getBody() != null;
        return Arrays.asList(response.getBody());
    }

}

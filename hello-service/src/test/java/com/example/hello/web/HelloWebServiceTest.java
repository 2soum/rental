package com.example.hello.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests for HelloWebService REST Controller
 * Tests the simple Hello endpoint using Spring MockMVC
 */
@WebMvcTest(HelloWebService.class)
class HelloWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test successful GET /hello request
     */
    @Test
    void testHelloEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Hello"));
    }

    /**
     * Test GET /hello returns correct message
     */
    @Test
    void testHelloEndpointMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }

    /**
     * Test GET /hello with different content types
     */
    @Test
    void testHelloEndpointWithJsonContentType() throws Exception {
        mockMvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"));
    }

    /**
     * Test GET /hello returns non-empty response
     */
    @Test
    void testHelloEndpointReturnsNonEmpty() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }

    /**
     * Test GET /hello response length
     */
    @Test
    void testHelloEndpointResponseLength() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hasLength(5)));
    }

    /**
     * Test GET /hello matches exact string
     */
    @Test
    void testHelloEndpointExactMatch() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"));
    }

    /**
     * Test multiple sequential requests to /hello
     */
    @Test
    void testMultipleHelloRequests() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/hello"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Hello"));
        }
    }

    /**
     * Test GET /hello with Accept header
     */
    @Test
    void testHelloEndpointWithAcceptHeader() throws Exception {
        mockMvc.perform(get("/hello")
                .header("Accept", "text/plain"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"));
    }

    /**
     * Test GET /hello is case-sensitive
     */
    @Test
    void testHelloEndpointPathCaseSensitive() throws Exception {
        mockMvc.perform(get("/Hello"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test GET /hello with trailing slash
     */
    @Test
    void testHelloEndpointWithTrailingSlash() throws Exception {
        mockMvc.perform(get("/hello/"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test GET /hello without path returns 404
     */
    @Test
    void testRootPathNotFound() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test POST /hello is not allowed
     */
    @Test
    void testPostNotAllowed() throws Exception {
        mockMvc.perform(post("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test PUT /hello is not allowed
     */
    @Test
    void testPutNotAllowed() throws Exception {
        mockMvc.perform(put("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test DELETE /hello is not allowed
     */
    @Test
    void testDeleteNotAllowed() throws Exception {
        mockMvc.perform(delete("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }
}

package com.example.ipfsdemon;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IPFSController.class)
public class IPFSControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IPFSService ipfsService;

    public IPFSControllerTest() {
    }

    @Test
    public void testSaveText() throws Exception {
        String filepath = "example.txt";
        Mockito.when(ipfsService.saveFile(filepath)).thenReturn("Hash123");

        mockMvc.perform(MockMvcRequestBuilders.get("/?filepath=" + filepath))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hash123"));

        verify(ipfsService, times(1)).saveFile(filepath);
    }

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        Mockito.when(ipfsService.saveFile(any(MultipartFile.class))).thenReturn("Hash456");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hash456"));

        verify(ipfsService, times(1)).saveFile(any(MultipartFile.class));
    }

    @Test
    public void testGetFile() throws Exception {
        String hash = "Hash789";
        byte[] content = "File Content".getBytes();
        Mockito.when(ipfsService.loadFile(hash)).thenReturn(content);

        mockMvc.perform(MockMvcRequestBuilders.get("/file/{hash}", hash))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(content));

        verify(ipfsService, times(1)).loadFile(hash);
    }
}

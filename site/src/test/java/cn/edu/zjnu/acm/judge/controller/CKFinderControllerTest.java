package cn.edu.zjnu.acm.judge.controller;

import cn.edu.zjnu.acm.judge.Application;
import cn.edu.zjnu.acm.judge.service.SystemService;
import cn.edu.zjnu.acm.judge.util.DeleteHelper;
import cn.edu.zjnu.acm.judge.util.Platform;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = Application.class)
@Transactional
@WebAppConfiguration
public class CKFinderControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private SystemService systemService;
    private String pathName = "test.jpg";
    private Path dir;
    private Path path;
    private byte[] content;

    public void setUp() throws IOException {
        dir = Files.createDirectories(systemService.getUploadDirectory());
        path = dir.getFileSystem().getPath(dir.toString(), pathName);
        content = "Hello! But I'm not a picture!".getBytes(StandardCharsets.UTF_8);
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(path, content);
    }

    public void tearDown() throws IOException {
        DeleteHelper.delete(path);
    }

    /**
     * Test of legacySupport method, of class CKFinderController.
     *
     * @see CKFinderController#legacySupport(HttpServletRequest,
     * HttpServletResponse, String)
     */
    @Test
    public void testLegacySupport() throws Exception {
        log.info("legacySupport");
        setUp();
        mvc.perform(get("/support/ckfinder.action").param("path", pathName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(content))
                .andReturn();
        mvc.perform(get("/support/ckfinder.action?path=" + pathName + "?hash-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(content))
                .andReturn();
        tearDown();
    }

    @Test
    public void testGetParent() throws Exception {
        pathName = "../../hello.txt";
        setUp();
        mvc.perform(get("/support/ckfinder.action?path=" + pathName))
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(CKFinderController.class))
                .andReturn();
        tearDown();
    }

    @Test
    public void testSlashRoot() throws Exception {
        for (String s : new String[]{
            "/aj.txt",
            "//ba.txt",
            "///cc.txt"
        }) {
            pathName = s;
            setUp();
            assertTrue(path.startsWith(dir));
            mvc.perform(get("/support/ckfinder.action?path=" + pathName))
                    .andExpect(status().isOk())
                    .andExpect(handler().handlerType(CKFinderController.class))
                    .andReturn();
            tearDown();
        }
    }

    /**
     * Test of attachment method, of class CKFinderController.
     *
     * @see CKFinderController#attachment(HttpServletRequest,
     * HttpServletResponse, String)
     */
    @Test
    public void testAttachment() throws Exception {
        log.info("attachment");
        setUp();
        mvc.perform(get("/userfiles/" + pathName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(content))
                .andReturn();
        // query handled by framework
        mvc.perform(get("/userfiles/" + pathName + "?hash=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(content))
                .andReturn();
        for (String uri : new String[]{
            "/userfiles/images",
            "/userfiles/images/",
            "/userfiles/images/sample.png",
            "/userfiles/images/folder/sample.png",
            "/userfiles/images/f1/f2/sample.png"
        }) {
            mvc.perform(get(uri))
                    .andExpect(handler().handlerType(CKFinderController.class));
        }
        tearDown();
    }

    @Test
    public void testDotDirectory() throws Exception {
        pathName = ".git/head";
        setUp();
        mvc.perform(get("/userfiles/" + pathName))
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(CKFinderController.class))
                .andReturn();
        tearDown();
    }

    @Test
    public void testIseOnWindows() throws Exception {
        assumeTrue(Platform.isWindows());
        pathName = "::::::";
        try {
            setUp();
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // ok
        }
        mvc.perform(get("/userfiles/" + pathName))
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(CKFinderController.class))
                .andReturn();
    }

    @Test
    public void testColonOnUnix() throws Exception {
        assumeTrue(!Platform.isWindows());
        pathName = "::::::";
        setUp();
        mvc.perform(get("/userfiles/" + pathName))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(CKFinderController.class))
                .andReturn();
        tearDown();
    }

}
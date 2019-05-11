/*
 * Copyright 2016 ZJNU ACM.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zhanhb.judge.common;

import cn.edu.zjnu.acm.judge.util.DeleteHelper;
import com.github.zhanhb.judge.GroovyHolder;
import com.google.common.collect.ImmutableList;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import jnc.foreign.Platform;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 *
 * @author zhanhb
 */
@RunWith(Parameterized.class)
@Slf4j
public class JudgeBridgeTest {

    private static Path tmp;
    private static Path input;
    private static Path output;
    private static Path groovy;

    private static String build(String... args) {
        return Arrays.stream(args)
                .map(s -> new StringTokenizer(s).countTokens() > 1 ? '"' + s + '"' : s)
                .collect(Collectors.joining(" "));
    }

    @Parameterized.Parameters(name = "{index}: {0} {1}")
    public static List<Object[]> data() throws Exception {
        if (!Platform.getNativePlatform().getOS().isWindows()) {
            return ImmutableList.of();
        }
        URI uri = JudgeBridgeTest.class.getResource("/sample/program").toURI();
        Path program = Paths.get(uri);
        Path data = program.resolve("../data").toRealPath();
        input = data.resolve("b.in");
        output = data.resolve("b.out");
        tmp = Files.createDirectories(Files.createDirectories(Paths.get("C:", "tmp")));
        Path[] groovyJars = GroovyHolder.getPaths();
        assertThat("groovyJars", groovyJars, arrayWithSize(1));
        Path groovyPath = groovyJars[0];
        groovy = Files.copy(groovyPath, tmp.resolve(groovyPath.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);

        Checker[] values = Checker.values();
        ArrayList<Object[]> list = new ArrayList<>(values.length);
        for (Checker checker : values) {
            Path path = program.resolve(checker.name());
            Files.list(path)
                    .filter(p -> p.getFileName().toString().endsWith(".groovy"))
                    .map(Object::toString)
                    .forEach(executable -> list.add(new Object[]{checker, executable}));
        }
        return list;
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        DeleteHelper.delete(tmp);
    }

    private final Validator validator = SimpleValidator.NORMAL;

    private final Checker checker;
    private final String executable;
    private JudgeBridge judgeBridge;

    public JudgeBridgeTest(Checker checker, String executable) {
        this.checker = checker;
        this.executable = executable;
    }

    @Before
    public void setUp() {
        judgeBridge = new JudgeBridge();
    }

    @After
    public void tearDown() {
        judgeBridge.close();
    }

    @Test
    public void test() {
        test(executable, checker);
    }

    @SneakyThrows
    private void test(String executable, Checker checker) {
        Options options = Options.builder().command(build("java", "-cp", groovy.toString(), groovy.ui.GroovyMain.class.getName(), executable))
                .inputFile(input)
                .outputFile(tmp.resolve(output.getFileName()))
                .standardOutput(output)
                .errFile(tmp.resolve("NUL"))
                .memoryLimit(256 * 1024 * 1024)
                .outputLimit(16 * 1024 * 1024)
                .workDirectory(tmp)
                .timeLimit(6000)
                .build();
        boolean stopOnError = false;
        ExecuteResult er = judgeBridge.judge(new Options[]{options}, stopOnError, validator)[0];
        log.info("{}", er);
        assertEquals(executable, checker.getStatus(), er.getCode());
    }

}

/*
 * Copyright 2019 ZJNU ACM.
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
package cn.edu.zjnu.acm.judge.sandbox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author zhanhb
 */
public class GroovyHolder {

    private static final Path[] groovyJars = getGroovy();

    private static Path[] getGroovy() {
        return Arrays.stream(System.getProperty("java.class.path").split(File.pathSeparator))
                .filter(s -> s.contains("groovy-"))
                .map(Paths::get).toArray(Path[]::new);
    }

    public static Path[] getPaths() {
        return groovyJars.clone();
    }

}
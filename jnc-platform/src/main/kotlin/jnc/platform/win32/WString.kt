/*
 * Copyright 2017-2019 ZJNU ACM.
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
package jnc.platform.win32

import jnc.foreign.Foreign
import jnc.foreign.Pointer
import java.nio.charset.StandardCharsets

/**
 * @author zhanhb
 */
interface WString {
    companion object {

        @JvmStatic
        fun toNative(string: String?): Pointer? {
            return if (string == null) null else Foreign.getDefault().memoryManager.allocateString(string, StandardCharsets.UTF_16LE)
        }

        @JvmStatic
        fun fromNative(ptr: Pointer?): String? {
            return ptr?.getString(0, StandardCharsets.UTF_16LE)
        }
    }

}

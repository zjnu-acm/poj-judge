/*
 * Copyright 2018 ZJNU ACM.
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

/**
 * @author zhanhb
 */
@Suppress("ClassName")
class TOKEN_USER : TOKEN_INFORMATION() {

    val user: SID_AND_ATTRIBUTES = inner(SID_AND_ATTRIBUTES())

    companion object {
        @JvmStatic
        fun withPadding(padding: Int): TOKEN_USER {
            val tokenUser = TOKEN_USER()
            tokenUser.padding(padding)
            return tokenUser
        }
    }

}

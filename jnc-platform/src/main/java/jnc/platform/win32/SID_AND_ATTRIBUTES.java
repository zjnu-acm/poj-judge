/*
 * Copyright 2017 ZJNU ACM.
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
package jnc.platform.win32;

/**
 * @see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa379595">SID_AND_ATTRIBUTES</a>
 */
public final class SID_AND_ATTRIBUTES extends jnc.foreign.Struct {

    /**
     * Pointer to a SID structure.
     */
    private final Pointer Sid = new Pointer();
    /**
     * Specifies attributes of the SID. This value contains up to 32 one-bit
     * flags. Its meaning depends on the definition and use of the SID.
     */
    private final DWORD Attributes = new DWORD();

    public final jnc.foreign.Pointer getSid() {
        return this.Sid.get();
    }

    public final void setSid(jnc.foreign.Pointer value) {
        this.Sid.set(value);
    }

    public final int getAttributes() {
        return this.Attributes.intValue();
    }

    public final void setAttributes(int value) {
        this.Attributes.set(value);
    }
}

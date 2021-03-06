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
 * Memory statistics for a process.
 *
 * @see <a href="http://msdn.microsoft.com/en-us/library/ms684877">PROCESS_MEMORY_COUNTERS</a>
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class PROCESS_MEMORY_COUNTERS extends jnc.foreign.Struct {

    /**
     * The size of the structure, in bytes.
     */
    private final DWORD cb = new DWORD();
    /**
     * The number of page faults.
     */
    private final DWORD PageFaultCount = new DWORD();
    /**
     * The peak working set size, in bytes.
     */
    private final size_t PeakWorkingSetSize = new size_t();
    /**
     * The current working set size, in bytes.
     */
    private final size_t WorkingSetSize = new size_t();
    /**
     * The peak paged pool usage, in bytes.
     */
    private final size_t QuotaPeakPagedPoolUsage = new size_t();
    /**
     * The current paged pool usage, in bytes.
     */
    private final size_t QuotaPagedPoolUsage = new size_t();
    /**
     * The peak nonpaged pool usage, in bytes.
     */
    private final size_t QuotaPeakNonPagedPoolUsage = new size_t();
    /**
     * The current nonpaged pool usage, in bytes.
     */
    private final size_t QuotaNonPagedPoolUsage = new size_t();
    /**
     * The Commit Charge value in bytes for this process. Commit Charge is the
     * total amount of memory that the memory manager has committed for a
     * running process.
     */
    private final size_t PagefileUsage = new size_t();
    /**
     * The peak value in bytes of the Commit Charge during the lifetime of this
     * process.
     */
    private final size_t PeakPagefileUsage = new size_t();

    public final int getCb() {
        return this.cb.intValue();
    }

    public final void setCb(int value) {
        this.cb.set(value);
    }

    public final int getPageFaultCount() {
        return this.PageFaultCount.intValue();
    }

    public final void setPageFaultCount(int value) {
        this.PageFaultCount.set(value);
    }

    public final long getPeakWorkingSetSize() {
        return this.PeakWorkingSetSize.get();
    }

    public final void setPeakWorkingSetSize(long value) {
        this.PeakWorkingSetSize.set(value);
    }

    public final long getWorkingSetSize() {
        return this.WorkingSetSize.get();
    }

    public final void setWorkingSetSize(long value) {
        this.WorkingSetSize.set(value);
    }

    public final long getQuotaPeakPagedPoolUsage() {
        return this.QuotaPeakPagedPoolUsage.get();
    }

    public final void setQuotaPeakPagedPoolUsage(long value) {
        this.QuotaPeakPagedPoolUsage.set(value);
    }

    public final long getQuotaPagedPoolUsage() {
        return this.QuotaPagedPoolUsage.get();
    }

    public final void setQuotaPagedPoolUsage(long value) {
        this.QuotaPagedPoolUsage.set(value);
    }

    public final long getQuotaPeakNonPagedPoolUsage() {
        return this.QuotaPeakNonPagedPoolUsage.get();
    }

    public final void setQuotaPeakNonPagedPoolUsage(long value) {
        this.QuotaPeakNonPagedPoolUsage.set(value);
    }

    public final long getQuotaNonPagedPoolUsage() {
        return this.QuotaNonPagedPoolUsage.get();
    }

    public final void setQuotaNonPagedPoolUsage(long value) {
        this.QuotaNonPagedPoolUsage.set(value);
    }

    public final long getPagefileUsage() {
        return this.PagefileUsage.get();
    }

    public final void setPagefileUsage(long value) {
        this.PagefileUsage.set(value);
    }

    public final long getPeakPagefileUsage() {
        return this.PeakPagefileUsage.get();
    }

    public final void setPeakPagefileUsage(long value) {
        this.PeakPagefileUsage.set(value);
    }
}

package com.github.zhanhb.judge.win32;

import com.github.zhanhb.judge.win32.struct.PROCESS_MEMORY_COUNTERS;
import jnr.ffi.Pointer;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.types.int32_t;
import jnr.ffi.types.u_int32_t;

public interface Psapi {

    Psapi INSTANCE = Native.loadLibrary("psapi", Psapi.class);

    /**
     * Retrieves information about the memory usage of the specified process.
     *
     * @param hProcess [in] A handle to the process. The handle must have the
     * <strong>PROCESS_QUERY_INFORMATION</strong> or
     * <strong>PROCESS_QUERY_LIMITED_INFORMATION</strong> access right and the
     * <strong>PROCESS_VM_READ</strong> access right. For more information, see
     * Process Security and Access Rights.
     * @param ppsmemCounters A pointer to the
     * <strong>PROCESS_MEMORY_COUNTERS</strong> or
     * <strong>PROCESS_MEMORY_COUNTERS_EX</strong> structure that receives
     * information about the memory usage of the process.
     * @param cb The size of the <em>ppsmemCounters</em> structure, in bytes.
     * @return If the function succeeds, the return value is nonzero. If the
     * function fails, the return value is zero. To get extended error
     * information, call GetLastError.
     * @see
     * <a href="http://msdn.microsoft.com/en-us/library/ms683219(VS.85).aspx">GetProcessMemoryInfo</a>
     */
    @int32_t
    /* BOOL */ int GetProcessMemoryInfo(
            @In Pointer /*HANDLE*/ hProcess,
            // this parameter is for out only, cb is not set until the method called
            @Out PROCESS_MEMORY_COUNTERS ppsmemCounters,
            @In @u_int32_t int cb);

}

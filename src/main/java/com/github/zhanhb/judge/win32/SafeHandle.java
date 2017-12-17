package com.github.zhanhb.judge.win32;

import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import jnc.foreign.Platform;

public class SafeHandle implements Closeable {

    private static final long INVALID_HANDLE_VALUE;

    static {
        switch (Platform.getNativePlatform().getArch()) {
            case I386:
                INVALID_HANDLE_VALUE = 0xFFFFFFFFL;
                break;
            case X86_64:
                INVALID_HANDLE_VALUE = -1;
                break;
            default:
                throw new AssertionError();
        }
    }

    public static void validateHandle(long /*HANDLE*/ handle) {
        Preconditions.checkArgument(handle != 0 && handle != INVALID_HANDLE_VALUE, "invalid handle value");
    }

    public static void close(long /*HANDLE*/ handle) {
        try {
            validateHandle(handle);
        } catch (IllegalArgumentException ex) {
            return;
        }
        Kernel32Util.assertTrue(Kernel32.INSTANCE.CloseHandle(handle));
    }

    private final long /*HANDLE*/ handle;
    private final AtomicBoolean closed = new AtomicBoolean();

    public SafeHandle(long /*HANDLE*/ handle) {
        Objects.requireNonNull(handle);
        validateHandle(handle);
        this.handle = handle;
    }

    public long /*HANDLE*/ getValue() {
        return handle;
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            close(handle);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}

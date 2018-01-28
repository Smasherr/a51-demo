/*
 * NonClosingInputStream.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 * Thanks fge and Seelenvirtuose for solution!
 * http://stackoverflow.com/questions/23041258/try-with-resources-and-system-in?noredirect=1#comment35203182_23041258
 */

package de.ht.ak.a51.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class NonClosingInputStream extends InputStream
{
    private final InputStream wrappedStream;

    public NonClosingInputStream(final InputStream wrappedStream)
    {
        this.wrappedStream = Objects.requireNonNull(wrappedStream);
    }

    public NonClosingInputStream()
    {
        this(System.in);
    }

    @Override
    public int read() throws IOException
    {
        return wrappedStream.read();
    }

    @Override
    public int read(final byte[] b) throws IOException
    {
        return wrappedStream.read(b);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException
    {
        return wrappedStream.read(b, off, len);
    }

    @Override
    public long skip(final long n) throws IOException
    {
        return wrappedStream.skip(n);
    }

    @Override
    public int available() throws IOException
    {
        return wrappedStream.available();
    }

    @Override
    public void close() throws IOException
    {
    }

    @Override
    public synchronized void mark(final int readlimit)
    {
        wrappedStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException
    {
        wrappedStream.reset();
    }

    @Override
    public boolean markSupported()
    {
        return wrappedStream.markSupported();
    }

}

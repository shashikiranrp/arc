package org.kelvin.arc;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class GenericResult<T> extends AbstractFuture<T>
{
    public void setData(T value) {
        super.set(value);
    }

    public void setError(Throwable throwable) {
        super.setException(throwable);
    }
}

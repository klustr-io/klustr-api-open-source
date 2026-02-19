package io.klustr.kafka;

import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

/**
 * Overrides the default {@link DeferredObject} which currently has a notification method
 * that is wrapped in a sync call...
 *
 * <code><pre>
 *    notify(...) {
 *      synchronized(this) {
 *        // ...
 *      }
 *    }
 * </pre></code>
 * <p>
 * This has some negative performance issues so we override the DeferredObject default
 * notification method to avoid entirely the `synchronized(this)` call.
 *
 * @param <D>
 * @param <F>
 * @param <P>
 * @author Terrance A. Snyder
 */
public class AsynchronousDeferredObject<D, F, P> extends DeferredObject<D, F, P> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Deferred<D, F, P> notify(P progress) {
        if (!this.isPending()) {
            throw new IllegalStateException("Deferred object already finished, cannot notify progress");
        } else {
            this.triggerProgress(progress);
            return this;
        }
    }


}
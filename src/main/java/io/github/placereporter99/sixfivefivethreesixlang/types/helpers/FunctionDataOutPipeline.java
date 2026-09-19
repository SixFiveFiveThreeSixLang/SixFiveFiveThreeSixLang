package io.github.placereporter99.sixfivefivethreesixlang.types.helpers;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.emptyinterfaces.*;

import java.util.concurrent.*;

public class FunctionDataOutPipeline {
    private final SynchronousQueue<SFFTSObjectOrNull> queue = new SynchronousQueue<>();
    private boolean expired = false;

    public void push(SFFTSObject<?> obj) throws InterruptedException, IllegalStateException {
        if (!expired) {
            queue.put(obj);
        } else {
            throw new IllegalStateException("Cannot push to an expired FunctionDataOutPipeline");
        }
    }

    public SFFTSObjectOrNull pull() throws InterruptedException, IllegalStateException {
        if (!expired) {
            SFFTSObjectOrNull item = queue.take();
            if (item instanceof Return) {
                expired = true;
            }
            return item;
        } else {
            throw new IllegalStateException("Cannot pull from an expired FunctionDataOutPipeline");
        }
    }

    public void finalPush(SFFTSObject<?> obj) throws InterruptedException, IllegalStateException {
        if (!expired) {
            queue.put(new Return(obj));
        } else {
            throw new IllegalStateException("Cannot final push to an expired FunctionDataOutPipeline");
        }
    }
}

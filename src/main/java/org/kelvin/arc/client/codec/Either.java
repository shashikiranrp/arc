package org.kelvin.arc.client.codec;

import java.util.Optional;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class Either<L, R>
{

    final Optional<L> leftObj;
    final Optional<R> rightObj;

    public Either(L leftObj, R rightObj)
    {
        this.leftObj = Optional.ofNullable(leftObj);
        this.rightObj = Optional.ofNullable(rightObj);
    }

    public L left()
    {
        return leftObj.orElse(null);
    }

    public R right()
    {
        return rightObj.orElse(null);
    }

    public boolean isLeft()
    {
        return this.leftObj.isPresent();
    }

    public boolean isRight()
    {
        return this.rightObj.isPresent();
    }
}

package labs.common.patterns;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/8/12
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Comparer<T> {
    boolean equals(T x, T y);
}

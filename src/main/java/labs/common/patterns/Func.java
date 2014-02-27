/*
 * @(#)Func.java 2013-06-18
 * 
 * Copy Right@ 纽海信息技术有限公司
 */

package labs.common.patterns;

/**
 * <pre>
 *
 * @author jiaruizhi
 *         <p/>
 *         <p/>
 *         创建日期: 2013-06-18.
 *         修改人 :
 *         修改说明:
 *         评审人 ：
 *         </pre>
 */
public interface Func<T, TResult> {
    public TResult execute(T t);
}


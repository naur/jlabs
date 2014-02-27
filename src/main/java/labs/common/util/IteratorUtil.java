/*
 * @(#)ArchiveServiceTest.java 2013-06-19.
 * 
 * Copy Right@ 纽海信息技术有限公司
 */

package labs.common.util;

import labs.common.patterns.Func;
import labs.common.patterns.Funcm;

import java.util.*;

/**
 * <pre>
 *
 * @author jiaruizhi
 *         <p/>
 *         <p/>
 *         创建日期: 2013-06-19.
 *         修改人 :
 *         修改说明:
 *         评审人 ：
 *         </pre>
 */
public class IteratorUtil {

    /**
     * contains. 查询集合是否包含特定项
     *
     * @param list       入参
     * @param comparator 比较器
     * @param value      比较值
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> boolean contains(final List<T> list,
                                       final Comparator<T> comparator,
                                       final T... value) {
        for (T item : list) {
            if (null != item && comparator.compare(
                    item, value.length == 0 ? null : value[0]) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * contains. 查询集合是否包含特定项
     *
     * @param list       入参
     * @param comparator 比较器
     * @param value      比较值
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> boolean contains(final List<T> list,
                                       final Func<T, Boolean> comparator,
                                       final T... value) {
        for (T item : list) {
            if (null != item && comparator.execute(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * where.
     *
     * @param list       入参
     * @param comparator 比较器
     * @param value      比较值
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> List<T> where(final List<T> list,
                                    final Comparator<T> comparator,
                                    final T... value) {
        List<T> temp = new ArrayList<T>();
        for (T item : list) {
            if (comparator.compare(item,
                    value.length == 0 ? null : value[0]) >= 0) {
                temp.add(item);
            }
        }
        return temp;
    }

    /**
     * where.
     *
     * @param list       入参
     * @param comparator 比较器
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> List<T> where(final List<T> list,
                                    final Func<T, Boolean> comparator) {
        List<T> temp = new ArrayList<T>();
        for (T item : list) {
            if (comparator.execute(item)) {
                temp.add(item);
            }
        }
        return temp;
    }

    /**
     * where.
     *
     * @param list       入参
     * @param comparator 比较器
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T, U> List<T> where(final List<T> list,
                                    final Funcm<T, U, Boolean> comparator,
                                    final U value) {
        List<T> temp = new ArrayList<T>();
        for (T item : list) {
            if (comparator.execute(item, value)) {
                temp.add(item);
            }
        }
        return temp;
    }

    /**
     * find. 查找某一项
     *
     * @param list       入参
     * @param comparator 比较器
     * @param value      比较值
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> T find(final List<T> list,
                             final Comparator<T> comparator, final T... value) {
        if (null == list || list.isEmpty()) {
            return null;
        }

        for (T item : list) {
            if (null != item && comparator.compare(item,
                    value.length == 0 ? null : value[0]) >= 0) {
                return item;
            }
        }
        return null;
    }

    /**
     * find.
     *
     * @param list       入参
     * @param comparator 比较器
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> T find(final List<T> list,
                             final Func<T, Boolean> comparator) {
        for (T item : list) {
            if (null != item && comparator.execute(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * find.
     *
     * @param set        入参
     * @param comparator 比较器
     * @param value      比较值
     * @param <T>        入参类型
     * @return boolean 返回值
     */
    public static <T> T find(final Set<T> set,
                             final Comparator comparator,
                             final T... value) {
        T t = null;
        if (null == set || set.isEmpty() || !set.iterator().hasNext()) {
            return t;
        }

        for (Iterator<T> iter = set.iterator(); iter.hasNext(); ) {
            t = iter.next();
            if (null != t && comparator.compare(t,
                    value.length == 0 ? null : value[0]) >= 0) {
                return t;
            }
        }
        return null;
    }

    /**
     * find.
     *
     * @param set        入参
     * @param comparator 比较器
     * @param <T>        入参类型
     * @param <U>        入参类型
     * @return boolean 返回值
     */
    public static <T, U> T find(final Set<T> set,
                                final Func<T, Boolean> comparator) {
        T t = null;
        if (null == set || set.isEmpty() || !set.iterator().hasNext()) {
            return t;
        }

        for (Iterator<T> iter = set.iterator(); iter.hasNext(); ) {
            t = iter.next();
            if (null != t && comparator.execute(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * select.
     *
     * @param list     入参
     * @param selector 比较器
     * @param <T>      入参类型
     * @param <U>      入参类型
     * @return boolean 返回值
     */
    public static <T, U> List<U> select(final T[] list,
                                        final Func<T, U> selector) {
        List<U> temp = new ArrayList<U>();
        if (null == list || list.length <= 0) {
            return temp;
        }
        U u = null;
        for (T t : list) {
            if (null != t) {
                u = selector.execute(t);
                if (null != u) {
                    temp.add(u);
                }
            }
        }
        return temp;
    }

    /**
     * select.
     *
     * @param list     入参
     * @param selector 比较器
     * @param <T>      入参类型
     * @param <U>      入参类型
     * @return boolean 返回值
     */
    public static <T, U> List<U> select(final List<T> list,
                                        final Func<T, U> selector) {
        List<U> temp = new ArrayList<U>();
        if (null == list || list.isEmpty()) {
            return temp;
        }
        U u = null;
        for (T t : list) {
            if (null != t) {
                u = selector.execute(t);
                if (null != u) {
                    temp.add(u);
                }
            }
        }
        return temp;
    }

    /**
     * selectMany.
     *
     * @param list     入参
     * @param selector 比较器
     * @param <T>      入参类型
     * @param <U>      入参类型
     * @return boolean 返回值
     */
    public static <T, U> List<U> selectMany(final List<T> list,
                                            final Func<T, List<U>> selector) {
        List<U> temp = new ArrayList<U>();
        List<U> result = null;
        if (null == list || list.isEmpty()) {
            return temp;
        }
        for (T t : list) {
            if (null != t) {
                result = selector.execute(t);
                if (null != result) {
                    temp.addAll(result);
                }
            }
        }
        return temp;
    }
}

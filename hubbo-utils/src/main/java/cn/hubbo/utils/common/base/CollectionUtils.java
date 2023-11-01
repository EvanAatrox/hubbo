package cn.hubbo.utils.common.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-10-20 15:10
 * @usage 计算运算工具类
 * TODO 此类待完善，写的有点差劲
 */
@SuppressWarnings({"all"})
public final class CollectionUtils {


    /**
     * 合并所有类型，即求全集
     *
     * @param collection 集合
     * @param other      集合数组
     * @param <K>        期望类型
     * @return 合并后的集合
     */
    @SafeVarargs
    public static <K> Collection<K> all(Collection<K> collection, Collection<K>... other) {
        if (Objects.isNull(collection)) {
            return null;
        } else if (other == null || other.length == 0) {
            return collection;
        }
        for (Collection<K> coll : other) {
            collection.addAll(coll);
        }
        return collection;
    }


    /**
     * 交运算
     *
     * @param collection
     * @param other
     * @param <K>
     * @return
     */
    public static <K> Collection intersection(Collection<K> collection, Collection<K>... other) {
        if (Objects.isNull(collection)) {
            return null;
        } else if (other == null || other.length == 0) {
            return collection;
        }
        ArrayList<K> list = new ArrayList<>();
        for (K k : collection) {
            for (Collection<K> ks : other) {
                for (K kkk : ks) {
                    if (kkk.equals(k)) {
                        list.add(k);
                    }
                }
            }
        }
        return list;
    }


}

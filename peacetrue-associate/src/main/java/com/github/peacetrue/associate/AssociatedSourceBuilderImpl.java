package com.github.peacetrue.associate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author xiayx
 */
public class AssociatedSourceBuilderImpl implements AssociatedSourceBuilder {

    private Map<Key, AssociatedSource> cache = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <I, D, R> AssociatedSource<I, D, R> buildAssociatedSource(Function<I, D> findAssociate, Function<D, R> format) {
        return cache.computeIfAbsent(new Key(findAssociate, null, format),
                key -> new AssociatedSource<I, D, R>() {
                    public D findAssociate(I id) {
                        return findAssociate.apply(id);
                    }

                    public R format(D data) {
                        return format.apply(data);
                    }
                });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I, D, R> CollectionAssociatedSource<I, D, R> buildCollectionAssociatedSource(Function<Collection<I>, Collection<D>> findAssociate, Function<D, I> resolveId, Function<D, R> format) {
        return (CollectionAssociatedSource<I, D, R>)
                cache.computeIfAbsent(new Key(findAssociate, resolveId, format),
                        key -> new CollectionAssociatedSource<I, D, R>() {
                            public Collection<D> findCollectionAssociate(Collection<I> ids) {
                                return findAssociate.apply(ids);
                            }

                            public I resolveId(D data) {
                                return resolveId.apply(data);
                            }

                            public R format(D data) {
                                return format.apply(data);
                            }
                        });
    }

    private static class Key {
        private Function findAssociate;
        private Function resolveId;
        private Function format;

        public Key(Function findAssociate, Function resolveId, Function format) {
            this.findAssociate = Objects.requireNonNull(findAssociate);
            this.resolveId = resolveId;
            this.format = Objects.requireNonNull(format);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) return false;
            Key other = (Key) obj;
            return Objects.equals(findAssociate, other.findAssociate)
                    & Objects.equals(resolveId, other.resolveId)
                    & Objects.equals(format, other.format);
        }
    }

}

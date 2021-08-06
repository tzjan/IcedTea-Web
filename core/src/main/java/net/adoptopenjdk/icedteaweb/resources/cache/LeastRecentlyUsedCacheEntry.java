package net.adoptopenjdk.icedteaweb.resources.cache;

import net.adoptopenjdk.icedteaweb.jnlp.version.VersionId;
import net.adoptopenjdk.icedteaweb.jnlp.version.VersionString;

import java.net.URL;
import java.util.Objects;

/**
 * ...
 */
class LeastRecentlyUsedCacheEntry implements Comparable<LeastRecentlyUsedCacheEntry> {
    private final String id;
    private final long lastAccessed;
    private final boolean markedForDeletion;

    private final CacheKey key;

    LeastRecentlyUsedCacheEntry(String id, long lastAccessed, CacheKey key) {
        this.id = id;
        this.lastAccessed = lastAccessed;
        this.markedForDeletion = false;
        this.key = key;
    }

    LeastRecentlyUsedCacheEntry(String id, CacheKey key) {
        this.id = id;
        this.lastAccessed = 0;
        this.markedForDeletion = true;
        this.key = key;
    }

    String getId() {
        return id;
    }

    CacheKey getCacheKey() {
        return key;
    }

    URL getResourceHref() {
        return key.getLocation();
    }

    VersionId getVersion() {
        return key.getVersion();
    }

    String getProtocol() {
        return key.getLocation().getProtocol();
    }

    String getDomain() {
        return key.getLocation().getHost();
    }

    long getLastAccessed() {
        return lastAccessed;
    }

    boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    boolean matches(URL resource) {
        return key.getLocation().equals(resource);
    }

    boolean matches(CacheKey key) {
        return this.key.equals(key);
    }

    boolean matches(URL resource, VersionString versionString) {
        if (matches(resource)) {
            final VersionId version = key.getVersion();
            if (versionString == null && version == null) {
                return true;
            }
            if (versionString != null && version != null) {
                return versionString.contains(version);
            }
        }
        return false;
    }

    @Override
    public int compareTo(LeastRecentlyUsedCacheEntry o) {
        // this will sort in least recently used order
        return Long.compare(o.lastAccessed, this.lastAccessed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeastRecentlyUsedCacheEntry entry = (LeastRecentlyUsedCacheEntry) o;
        return id.equals(entry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

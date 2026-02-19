package io.klustr.storage.docs.rethinkdb;

import java.util.List;

import java.util.List;

public record RethinkDbStats(
        String db,
        List<String> id,
        QueryEngine query_engine,
        String server,
        StorageEngine storage_engine,
        String table
) {
    public record QueryEngine(
            double read_docs_per_sec,
            long read_docs_total,
            double written_docs_per_sec,
            long written_docs_total
    ) {}

    public record StorageEngine(
            Cache cache,
            Disk disk
    ) {
        public record Cache(
                long in_use_bytes
        ) {}

        public record Disk(
                double read_bytes_per_sec,
                long read_bytes_total,
                SpaceUsage space_usage,
                double written_bytes_per_sec,
                long written_bytes_total
        ) {
            public record SpaceUsage(
                    long data_bytes,
                    long garbage_bytes,
                    long metadata_bytes,
                    long preallocated_bytes
            ) {}
        }
    }
}

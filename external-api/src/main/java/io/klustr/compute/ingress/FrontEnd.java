package io.klustr.compute.ingress;

/**
 * These need to match the frontend names in haproxy for example;
 *
 * frontend vscode_in from unnamed_defaults_1
 *   mode http
 *   bind *:9000 ssl crt-list /usr/local/etc/haproxy/crt.txt
 *
 *   frontend mongo_in from unnamed_defaults_1
 *   mode tcp
 *   bind *:27017 ssl crt-list /usr/local/etc/haproxy/crt.txt
 *
 *   frontend https_in from unnamed_defaults_1
 *   mode http
 *   bind *:443 ssl crt-list /usr/local/etc/haproxy/crt.txt
 */
public enum FrontEnd {
    https_in,
    mongo_in,
    postgres_in,
    redis_in,
    kafka_in,
    vscode_in,
    rethinkdb_in
}

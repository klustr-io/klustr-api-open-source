package io.klustr.storage.docs;

/**
 * Will resolve the current document database configured for
 * the active application.
 */
public interface DocumentDatabaseFactory {
     /**
      * Resolves the current document database.
      * @return The currently configured document database.
      */
     DocumentDatabase resolve();
}

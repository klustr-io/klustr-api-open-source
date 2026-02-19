package io.klustr.storage;

/**
 * Listens to changes made to a DB and enables triggers on this information.
 *
 * @param <T>
 */
public interface DbAdapterListener<T> {
    /**
     * Handles when an object is created.
     *
     * @param id  The ID of the object created
     * @param obj The object that was created.
     */
    void onCreate(String id, T obj);

    /**
     * Handles when an object was updated.
     *
     * @param id  The ID of the object that was updated
     * @param obj The obj that was updated.
     */
    void onUpdate(String id, T obj);

    /**
     * Handles when an object was deleted
     *
     * @param id  The ID of the object deleted.
     * @param obj The object that was deleted.
     */
    void onDelete(String id, T obj);
}

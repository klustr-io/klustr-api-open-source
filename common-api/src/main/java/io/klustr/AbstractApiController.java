package io.klustr;

import com.webcohesion.enunciate.metadata.Ignore;
import com.webcohesion.enunciate.metadata.swagger.OperationId;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * A basic service that does the CRUD operations on a document.
 *
 * @param <T> The type of object being stored, assumed to be JSON compliant.
 */
public abstract class AbstractApiController<T> extends AbstractApiService {

    private static final Logger log = LoggerFactory.getLogger(AbstractApiController.class);

    protected final DbAdapter<T> db;

    protected final AbstractApiSecurityPolicy<T> policy;

    public AbstractApiController(String schema, String table, Class<T> clazz, DocumentDatabaseFactory pool) {
        this(schema, table, clazz, pool, new NoApiSecurityPolicy<>());
    }

    public AbstractApiController(String schema, String table, Class<T> clazz, DocumentDatabaseFactory pool, AbstractApiSecurityPolicy<T> policy) {
        this.db = new DbAdapter<T>(schema, table, pool, clazz);
        this.policy = policy;
    }

    /**
     * Returns the currently configured security policy.
     *
     * @return The security policy active for this API controller.
     */
    protected AbstractApiSecurityPolicy<T> security() {
        return this.policy;
    }

    /**
     * Will get the document key to use when storing this object.
     *
     * @param obj The object to extract the document key for.
     * @return The document KEY to use when storing this object.
     */
    protected abstract String getKey(T obj);

    /**
     * Callback when an update operation has completed to perform
     * additional things like calling webhooks, sending to kafka, etc.
     *
     * @param obj The object that was updated.
     */
    protected void onUpdate(T obj, OAuth2AuthenticatedPrincipal principal) {
    }

    /**
     * Callback when a delete operation has completed to perform
     * additional things like calling webhooks, sending to kafka, etc.
     *
     * @param obj The object that was deleted.
     */
    protected void onDelete(T obj, OAuth2AuthenticatedPrincipal principal) {
    }

    /**
     * Callback when a create operation has completed to perform
     * additional things like calling webhooks, sending to kafka, etc.
     *
     * @param obj The object that was created.
     */
    protected void onCreate(T obj, OAuth2AuthenticatedPrincipal principal) {
    }

    /**
     * Searches all the specified objects for the given field and term.
     *
     * @param field       The field in the document to search for.
     * @param term        The term to search
     * @param oauth       The authentication context
     * @param customer_id The customer ID making this request
     * @return Returns the objects that match the specified result.
     */
    @GetMapping("/search")
    @OperationId("Search Objects")
    public List<T> search(@RequestParam("field") String field,
                          @RequestParam("term") String term,
                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                          @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {

        DbQuery fq = Db.query(field).matchesRegex(".*" + term + ".*");
        DocumentResult<T> result = this.db.insecureQuery(fq, Pagination.all());

        result.docs.removeIf(x -> !policy.hasReadAccess(oauth, x));
        return result.docs;
    }

    /**
     * Returns the specified document by the ID passed in
     *
     * @param id          The ID of the document to fetch
     * @param oauth       The context of the authentication to request
     * @param customer_id The customer ID who is making the request
     * @return Return the document that matches or error.
     */
    @GetMapping("/{id}")
    @OperationId("Get Object By ID")
    public T getById(@PathVariable("id") String id,
                     @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                     @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {

        Optional<T> match = db.tryGetObject(id);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        if (policy.hasReadAccess(oauth, match.get())) {
            return match.get();
        } else {
            // avoid over disclosure that an identity event exists to
            // potential attacker
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Creates a new document.
     *
     * @param obj         The object of the document to create
     * @param oauth       The context of the request being made for authN and authZ
     * @param customer_id The context of the client making this request
     * @return Returns the freshing created object.
     */
    @PostMapping
    @OperationId("Create Object")
    public T post(@RequestBody T obj,
                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                  @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {

        if (!policy.hasCreateAccess(oauth, obj)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        String key = getKey(obj);
        this.db.insertObject(key, obj);
        this.onCreate(obj, oauth);
        return obj;
    }

    protected T sanitize(T obj) {
        return obj;
    }


    /**
     * Removes and deletes all documents.
     *
     * @param oauth       The context of the request being made for authN and authZ
     * @param customer_id The context of the client making this request
     */
    @GetMapping("/admin/delete")
    @OperationId("Delete Objects")
    @Ignore
    public void deleteAllRecords(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                                 @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {
        this.db.deleteAllRecords();
    }


    /**
     * Deletes the specified document from the repository.
     *
     * @param id          The ID of the document to delete.
     * @param oauth       The context of the request being made for authN and authZ
     * @param customer_id The context of the client making this request
     * @return True if the object was deleted.
     */
    @DeleteMapping("/{id}")
    @OperationId("Delete Object")
    public void delete(@PathVariable("id") String id,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                       @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {
        T obj = this.db.getObject(id);
        if (obj != null && !policy.hasDeleteAccess(oauth, obj)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }
        this.db.deleteObject(id);
        this.onDelete(obj, oauth);
    }

    /**
     * Updates the specified document from the repository.
     *
     * @param id          The ID of the document to delete.
     * @param oauth       The context of the request being made for authN and authZ
     * @param customer_id The context of the client making this request
     * @return The object was created or updated.
     */
    @PutMapping("/{id}")
    @OperationId("Update Object")
    public T put(@PathVariable("id") String id, @RequestBody T obj,
                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                 @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {

        T existing = this.db.getObject(id);

        if (existing != null && !policy.hasUpdateAccess(oauth, existing)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (obj != null && !policy.hasUpdateAccess(oauth, obj)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        this.db.updateObject(id, obj);
        this.onUpdate(obj, oauth);
        return obj;
    }

}

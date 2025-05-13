package com.wyd.zmhkmiddleware.util.transactional;

/**
 * @author Stone
 * @since 2025-05-13
 */
@FunctionalInterface
public interface TransactionalForSyncRecord {

    void execute();

}

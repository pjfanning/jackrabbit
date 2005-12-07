/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jackrabbit.webdav.simple;

import org.apache.jackrabbit.webdav.jcr.JcrDavException;
import org.apache.jackrabbit.webdav.lock.LockManager;
import org.apache.jackrabbit.webdav.DavResourceFactory;
import org.apache.jackrabbit.webdav.DavResource;
import org.apache.jackrabbit.webdav.DavMethods;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavServletResponse;
import org.apache.jackrabbit.webdav.DavServletRequest;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.DavSession;

import javax.jcr.RepositoryException;

/**
 * ResourceFactoryImpl implements a simple DavResourceFactory
 */
public class ResourceFactoryImpl implements DavResourceFactory {

    private LockManager lockMgr;
    private ResourceConfig resourceConfig;

    /**
     * Create a new <code>ResourceFactory</code> that uses the given lock
     * manager and the default {@link ResourceConfig resource config}.
     *
     * @param lockMgr
     */
    public ResourceFactoryImpl(LockManager lockMgr) {
        this.lockMgr = lockMgr;
        this.resourceConfig = new ResourceConfig();
    }

    /**
     * Create a new <code>ResourceFactory</code> that uses the given lock
     * manager and resource filter.
     *
     * @param lockMgr
     * @param resourceConfig
     */
    public ResourceFactoryImpl(LockManager lockMgr, ResourceConfig resourceConfig) {
        this.lockMgr = lockMgr;
        this.resourceConfig = (resourceConfig != null) ? resourceConfig : new ResourceConfig();
    }

    /**
     * Create a new <code>DavResource</code> from the given locator and
     * request.
     *
     * @param locator
     * @param request
     * @param response
     * @return
     * @throws DavException
     * @see DavResourceFactory#createResource(DavResourceLocator,
     *      DavServletRequest, DavServletResponse)
     */
    public DavResource createResource(DavResourceLocator locator, DavServletRequest request,
                                      DavServletResponse response) throws DavException {
        DavResourceImpl resource = (DavResourceImpl)createResource(locator, request.getDavSession());
        if (isCreateRequest(request) && ! resource.exists()) {
            resource.setIsCollection(isCreateCollectionRequest(request));
        }
        return resource;
    }

    /**
     * Create a new <code>DavResource</code> from the given locator and webdav
     * session.
     *
     * @param locator
     * @param session
     * @return
     * @throws DavException
     * @see DavResourceFactory#createResource(DavResourceLocator, DavSession)
     */
    public DavResource createResource(DavResourceLocator locator, DavSession session) throws DavException {
        try {
            DavResourceImpl res = new DavResourceImpl(locator, this, session, resourceConfig);
            res.addLockManager(lockMgr);
            return res;
        } catch (RepositoryException e) {
            throw new JcrDavException(e);
        }
    }
 
    /**
     * Returns <code>true</code> if the request is to create a
     * resource. True for <code>MKCOL</code>, <code>PUT</code> and
     * <code>POST</code> requests.
     */
    protected boolean isCreateRequest(DavServletRequest request) {
        int methodCode = DavMethods.getMethodCode(request.getMethod());
        return (methodCode == DavMethods.DAV_MKCOL ||
                methodCode == DavMethods.DAV_PUT ||
                methodCode == DavMethods.DAV_POST);
    }
 
    /**
     * Returns <code>true</code> if the request is to create a
     * collection resource. True for <code>MKCOL</code> requests.
     */
    protected boolean isCreateCollectionRequest(DavServletRequest request) {
        return (DavMethods.getMethodCode(request.getMethod()) ==
                DavMethods.DAV_MKCOL);
    }
 
    /**
     */
    public LockManager getLockManager() {
        return lockMgr;
    }
 
    /**
     */
    public void setLockManager(LockManager lockMgr) {
        this.lockMgr = lockMgr;
    }
 
    /**
     */
    public ResourceConfig getResourceConfig() {
        return resourceConfig;
    }
 
    /**
     */
    public void setResourceConfig(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
    }
}

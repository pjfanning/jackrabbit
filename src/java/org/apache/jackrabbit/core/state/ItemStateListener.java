/*
 * Copyright 2004 The Apache Software Foundation.
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
package org.apache.jackrabbit.core.state;

/**
 * The <code>ItemStateListener</code> interface allows an implementing object
 * to be informed about changes on an <code>ItemState</code>.
 *
 * @author Stefan Guggisberg
 * @version $Revision: 1.5 $, $Date: 2004/08/02 16:19:48 $
 * @see ItemState#addListener
 */
public interface ItemStateListener {

    /**
     * Called when an <code>ItemState</code> has successfully
     * been created (i.e. its underlying persistent state was created).
     *
     * @param created the <code>ItemState</code> that has been 'created'
     */
    public void stateCreated(ItemState created);

    /**
     * Called when an <code>ItemState</code> has successfully
     * been modified (i.e. its underlying persistent state has changed).
     *
     * @param modified the <code>ItemState</code> that has been 'modified'
     */
    public void stateModified(ItemState modified);

    /**
     * Called when an <code>ItemState</code> has successfully been
     * removed (i.e. its underlying persistent state has been destroyed).
     *
     * @param destroyed the <code>ItemState</code> that has been 'destroyed'
     */
    public void stateDestroyed(ItemState destroyed);

    /**
     * Called when an <code>ItemState</code> has been discarded (i.e. it has
     * been rendered 'invalid').
     *
     * @param discarded the <code>ItemState</code> that has been discarded
     * @see ItemState#discard
     */
    public void stateDiscarded(ItemState discarded);
}

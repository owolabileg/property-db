/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package javax.naming.event;

/** {@collect.stats}
  * Specifies the methods that a listener interested in namespace changes
  * must implement.
  * Specifically, the listener is interested in <tt>NamingEvent</tt>s
  * with event types of <tt>OBJECT_ADDED</TT>, <TT>OBJECT_RENAMED</TT>, or
  * <TT>OBJECT_REMOVED</TT>.
  *<p>
  * Such a listener must:
  *<ol>
  *<li>Implement this interface and its methods.
  *<li>Implement <tt>NamingListener.namingExceptionThrown()</tt> so that
  * it will be notified of exceptions thrown while attempting to
  * collect information about the events.
  *<li>Register with the source using the source's <tt>addNamingListener()</tt>
  *    method.
  *</ol>
  * A listener that wants to be notified of <tt>OBJECT_CHANGED</tt> event types
  * should also implement the <tt>ObjectChangeListener</tt>
  * interface.
  *
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingEvent
  * @see ObjectChangeListener
  * @see EventContext
  * @see EventDirContext
  * @since 1.3
  */
public interface NamespaceChangeListener extends NamingListener {

    /** {@collect.stats}
     * Called when an object has been added.
     *<p>
     * The binding of the newly added object can be obtained using
     * <tt>evt.getNewBinding()</tt>.
     * @param evt The nonnull event.
     * @see NamingEvent#OBJECT_ADDED
     */
    void objectAdded(NamingEvent evt);

    /** {@collect.stats}
     * Called when an object has been removed.
     *<p>
     * The binding of the newly removed object can be obtained using
     * <tt>evt.getOldBinding()</tt>.
     * @param evt The nonnull event.
     * @see NamingEvent#OBJECT_REMOVED
     */
    void objectRemoved(NamingEvent evt);

    /** {@collect.stats}
     * Called when an object has been renamed.
     *<p>
     * The binding of the renamed object can be obtained using
     * <tt>evt.getNewBinding()</tt>. Its old binding (before the rename)
     * can be obtained using <tt>evt.getOldBinding()</tt>.
     * One of these may be null if the old/new binding was outside the
     * scope in which the listener has registered interest.
     * @param evt The nonnull event.
     * @see NamingEvent#OBJECT_RENAMED
     */
    void objectRenamed(NamingEvent evt);
}
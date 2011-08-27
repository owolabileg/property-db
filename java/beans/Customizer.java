/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

/** {@collect.stats}
 * A customizer class provides a complete custom GUI for customizing
 * a target Java Bean.
 * <P>
 * Each customizer should inherit from the java.awt.Component class so
 * it can be instantiated inside an AWT dialog or panel.
 * <P>
 * Each customizer should have a null constructor.
 */

public interface Customizer {

    /** {@collect.stats}
     * Set the object to be customized.  This method should be called only
     * once, before the Customizer has been added to any parent AWT container.
     * @param bean  The object to be customized.
     */
    void setObject(Object bean);

    /** {@collect.stats}
     * Register a listener for the PropertyChange event.  The customizer
     * should fire a PropertyChange event whenever it changes the target
     * bean in a way that might require the displayed properties to be
     * refreshed.
     *
     * @param listener  An object to be invoked when a PropertyChange
     *          event is fired.
     */
     void addPropertyChangeListener(PropertyChangeListener listener);

    /** {@collect.stats}
     * Remove a listener for the PropertyChange event.
     *
     * @param listener  The PropertyChange listener to be removed.
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

}

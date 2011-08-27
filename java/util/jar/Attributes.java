/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.util.jar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.Comparator;
import sun.misc.ASCIICaseInsensitiveComparator;

/** {@collect.stats}
 * The Attributes class maps Manifest attribute names to associated string
 * values. Valid attribute names are case-insensitive, are restricted to
 * the ASCII characters in the set [0-9a-zA-Z_-], and cannot exceed 70
 * characters in length. Attribute values can contain any characters and
 * will be UTF8-encoded when written to the output stream.  See the
 * <a href="../../../../technotes/guides/jar/jar.html">JAR File Specification</a>
 * for more information about valid attribute names and values.
 *
 * @author  David Connelly
 * @see     Manifest
 * @since   1.2
 */
public class Attributes implements Map<Object,Object>, Cloneable {
    /** {@collect.stats}
     * The attribute name-value mappings.
     */
    protected Map<Object,Object> map;

    /** {@collect.stats}
     * Constructs a new, empty Attributes object with default size.
     */
    public Attributes() {
        this(11);
    }

    /** {@collect.stats}
     * Constructs a new, empty Attributes object with the specified
     * initial size.
     *
     * @param size the initial number of attributes
     */
    public Attributes(int size) {
        map = new HashMap(size);
    }

    /** {@collect.stats}
     * Constructs a new Attributes object with the same attribute name-value
     * mappings as in the specified Attributes.
     *
     * @param attr the specified Attributes
     */
    public Attributes(Attributes attr) {
        map = new HashMap(attr);
    }


    /** {@collect.stats}
     * Returns the value of the specified attribute name, or null if the
     * attribute name was not found.
     *
     * @param name the attribute name
     * @return the value of the specified attribute name, or null if
     *         not found.
     */
    public Object get(Object name) {
        return map.get(name);
    }

    /** {@collect.stats}
     * Returns the value of the specified attribute name, specified as
     * a string, or null if the attribute was not found. The attribute
     * name is case-insensitive.
     * <p>
     * This method is defined as:
     * <pre>
     *      return (String)get(new Attributes.Name((String)name));
     * </pre>
     *
     * @param name the attribute name as a string
     * @return the String value of the specified attribute name, or null if
     *         not found.
     * @throws IllegalArgumentException if the attribute name is invalid
     */
    public String getValue(String name) {
        return (String)get(new Attributes.Name(name));
    }

    /** {@collect.stats}
     * Returns the value of the specified Attributes.Name, or null if the
     * attribute was not found.
     * <p>
     * This method is defined as:
     * <pre>
     *     return (String)get(name);
     * </pre>
     *
     * @param name the Attributes.Name object
     * @return the String value of the specified Attribute.Name, or null if
     *         not found.
     */
    public String getValue(Name name) {
        return (String)get(name);
    }

    /** {@collect.stats}
     * Associates the specified value with the specified attribute name
     * (key) in this Map. If the Map previously contained a mapping for
     * the attribute name, the old value is replaced.
     *
     * @param name the attribute name
     * @param value the attribute value
     * @return the previous value of the attribute, or null if none
     * @exception ClassCastException if the name is not a Attributes.Name
     *            or the value is not a String
     */
    public Object put(Object name, Object value) {
        return map.put((Attributes.Name)name, (String)value);
    }

    /** {@collect.stats}
     * Associates the specified value with the specified attribute name,
     * specified as a String. The attributes name is case-insensitive.
     * If the Map previously contained a mapping for the attribute name,
     * the old value is replaced.
     * <p>
     * This method is defined as:
     * <pre>
     *      return (String)put(new Attributes.Name(name), value);
     * </pre>
     *
     * @param name the attribute name as a string
     * @param value the attribute value
     * @return the previous value of the attribute, or null if none
     * @exception IllegalArgumentException if the attribute name is invalid
     */
    public String putValue(String name, String value) {
        return (String)put(new Name(name), value);
    }

    /** {@collect.stats}
     * Removes the attribute with the specified name (key) from this Map.
     * Returns the previous attribute value, or null if none.
     *
     * @param name attribute name
     * @return the previous value of the attribute, or null if none
     */
    public Object remove(Object name) {
        return map.remove(name);
    }

    /** {@collect.stats}
     * Returns true if this Map maps one or more attribute names (keys)
     * to the specified value.
     *
     * @param value the attribute value
     * @return true if this Map maps one or more attribute names to
     *         the specified value
     */
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    /** {@collect.stats}
     * Returns true if this Map contains the specified attribute name (key).
     *
     * @param name the attribute name
     * @return true if this Map contains the specified attribute name
     */
    public boolean containsKey(Object name) {
        return map.containsKey(name);
    }

    /** {@collect.stats}
     * Copies all of the attribute name-value mappings from the specified
     * Attributes to this Map. Duplicate mappings will be replaced.
     *
     * @param attr the Attributes to be stored in this map
     * @exception ClassCastException if attr is not an Attributes
     */
    public void putAll(Map<?,?> attr) {
        // ## javac bug?
        if (!Attributes.class.isInstance(attr))
            throw new ClassCastException();
        for (Map.Entry<?,?> me : (attr).entrySet())
            put(me.getKey(), me.getValue());
    }

    /** {@collect.stats}
     * Removes all attributes from this Map.
     */
    public void clear() {
        map.clear();
    }

    /** {@collect.stats}
     * Returns the number of attributes in this Map.
     */
    public int size() {
        return map.size();
    }

    /** {@collect.stats}
     * Returns true if this Map contains no attributes.
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /** {@collect.stats}
     * Returns a Set view of the attribute names (keys) contained in this Map.
     */
    public Set<Object> keySet() {
        return map.keySet();
    }

    /** {@collect.stats}
     * Returns a Collection view of the attribute values contained in this Map.
     */
    public Collection<Object> values() {
        return map.values();
    }

    /** {@collect.stats}
     * Returns a Collection view of the attribute name-value mappings
     * contained in this Map.
     */
    public Set<Map.Entry<Object,Object>> entrySet() {
        return map.entrySet();
    }

    /** {@collect.stats}
     * Compares the specified Attributes object with this Map for equality.
     * Returns true if the given object is also an instance of Attributes
     * and the two Attributes objects represent the same mappings.
     *
     * @param o the Object to be compared
     * @return true if the specified Object is equal to this Map
     */
    public boolean equals(Object o) {
        return map.equals(o);
    }

    /** {@collect.stats}
     * Returns the hash code value for this Map.
     */
    public int hashCode() {
        return map.hashCode();
    }

    /** {@collect.stats}
     * Returns a copy of the Attributes, implemented as follows:
     * <pre>
     *     public Object clone() { return new Attributes(this); }
     * </pre>
     * Since the attribute names and values are themselves immutable,
     * the Attributes returned can be safely modified without affecting
     * the original.
     */
    public Object clone() {
        return new Attributes(this);
    }

    /*
     * Writes the current attributes to the specified data output stream.
     * XXX Need to handle UTF8 values and break up lines longer than 72 bytes
     */
     void write(DataOutputStream os) throws IOException {
        Iterator it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            StringBuffer buffer = new StringBuffer(
                                        ((Name)e.getKey()).toString());
            buffer.append(": ");

            String value = (String)e.getValue();
            if (value != null) {
                byte[] vb = value.getBytes("UTF8");
                value = new String(vb, 0, 0, vb.length);
            }
            buffer.append(value);

            buffer.append("\r\n");
            Manifest.make72Safe(buffer);
            os.writeBytes(buffer.toString());
        }
        os.writeBytes("\r\n");
    }

    /*
     * Writes the current attributes to the specified data output stream,
     * make sure to write out the MANIFEST_VERSION or SIGNATURE_VERSION
     * attributes first.
     *
     * XXX Need to handle UTF8 values and break up lines longer than 72 bytes
     */
    void writeMain(DataOutputStream out) throws IOException
    {
        // write out the *-Version header first, if it exists
        String vername = Name.MANIFEST_VERSION.toString();
        String version = getValue(vername);
        if (version == null) {
            vername = Name.SIGNATURE_VERSION.toString();
            version = getValue(vername);
        }

        if (version != null) {
            out.writeBytes(vername+": "+version+"\r\n");
        }

        // write out all attributes except for the version
        // we wrote out earlier
        Iterator it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            String name = ((Name)e.getKey()).toString();
            if ((version != null) && ! (name.equalsIgnoreCase(vername))) {

                StringBuffer buffer = new StringBuffer(name);
                buffer.append(": ");

                String value = (String)e.getValue();
                if (value != null) {
                    byte[] vb = value.getBytes("UTF8");
                    value = new String(vb, 0, 0, vb.length);
                }
                buffer.append(value);

                buffer.append("\r\n");
                Manifest.make72Safe(buffer);
                out.writeBytes(buffer.toString());
            }
        }
        out.writeBytes("\r\n");
    }

    /*
     * Reads attributes from the specified input stream.
     * XXX Need to handle UTF8 values.
     */
    void read(Manifest.FastInputStream is, byte[] lbuf) throws IOException {
        String name = null, value = null;
        byte[] lastline = null;

        int len;
        while ((len = is.readLine(lbuf)) != -1) {
            boolean lineContinued = false;
            if (lbuf[--len] != '\n') {
                throw new IOException("line too long");
            }
            if (len > 0 && lbuf[len-1] == '\r') {
                --len;
            }
            if (len == 0) {
                break;
            }
            int i = 0;
            if (lbuf[0] == ' ') {
                // continuation of previous line
                if (name == null) {
                    throw new IOException("misplaced continuation line");
                }
                lineContinued = true;
                byte[] buf = new byte[lastline.length + len - 1];
                System.arraycopy(lastline, 0, buf, 0, lastline.length);
                System.arraycopy(lbuf, 1, buf, lastline.length, len - 1);
                if (is.peek() == ' ') {
                    lastline = buf;
                    continue;
                }
                value = new String(buf, 0, buf.length, "UTF8");
                lastline = null;
            } else {
                while (lbuf[i++] != ':') {
                    if (i >= len) {
                        throw new IOException("invalid header field");
                    }
                }
                if (lbuf[i++] != ' ') {
                    throw new IOException("invalid header field");
                }
                name = new String(lbuf, 0, 0, i - 2);
                if (is.peek() == ' ') {
                    lastline = new byte[len - i];
                    System.arraycopy(lbuf, i, lastline, 0, len - i);
                    continue;
                }
                value = new String(lbuf, i, len - i, "UTF8");
            }
            try {
                if ((putValue(name, value) != null) && (!lineContinued)) {
                    Logger.getLogger("java.util.jar").warning(
                                     "Duplicate name in Manifest: " + name
                                     + ".\n"
                                     + "Ensure that the manifest does not "
                                     + "have duplicate entries, and\n"
                                     + "that blank lines separate "
                                     + "individual sections in both your\n"
                                     + "manifest and in the META-INF/MANIFEST.MF "
                                     + "entry in the jar file.");
                }
            } catch (IllegalArgumentException e) {
                throw new IOException("invalid header field name: " + name);
            }
        }
    }

    /** {@collect.stats}
     * The Attributes.Name class represents an attribute name stored in
     * this Map. Valid attribute names are case-insensitive, are restricted
     * to the ASCII characters in the set [0-9a-zA-Z_-], and cannot exceed
     * 70 characters in length. Attribute values can contain any characters
     * and will be UTF8-encoded when written to the output stream.  See the
     * <a href="../../../../technotes/guides/jar/jar.html">JAR File Specification</a>
     * for more information about valid attribute names and values.
     */
    public static class Name {
        private String name;
        private int hashCode = -1;

        /** {@collect.stats}
         * Constructs a new attribute name using the given string name.
         *
         * @param name the attribute string name
         * @exception IllegalArgumentException if the attribute name was
         *            invalid
         * @exception NullPointerException if the attribute name was null
         */
        public Name(String name) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            if (!isValid(name)) {
                throw new IllegalArgumentException(name);
            }
            this.name = name.intern();
        }

        private static boolean isValid(String name) {
            int len = name.length();
            if (len > 70 || len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                if (!isValid(name.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isValid(char c) {
            return isAlpha(c) || isDigit(c) || c == '_' || c == '-';
        }

        private static boolean isAlpha(char c) {
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }

        private static boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        /** {@collect.stats}
         * Compares this attribute name to another for equality.
         * @param o the object to compare
         * @return true if this attribute name is equal to the
         *         specified attribute object
         */
        public boolean equals(Object o) {
            if (o instanceof Name) {
                Comparator c = ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER;
                return c.compare(name, ((Name)o).name) == 0;
            } else {
                return false;
            }
        }

        /** {@collect.stats}
         * Computes the hash value for this attribute name.
         */
        public int hashCode() {
            if (hashCode == -1) {
                hashCode = ASCIICaseInsensitiveComparator.lowerCaseHashCode(name);
            }
            return hashCode;
        }

        /** {@collect.stats}
         * Returns the attribute name as a String.
         */
        public String toString() {
            return name;
        }

        /** {@collect.stats}
         * <code>Name</code> object for <code>Manifest-Version</code>
         * manifest attribute. This attribute indicates the version number
         * of the manifest standard to which a JAR file's manifest conforms.
         * @see <a href="../../../../technotes/guides/jar/jar.html#JAR Manifest">
         *      Manifest and Signature Specification</a>
         */
        public static final Name MANIFEST_VERSION = new Name("Manifest-Version");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Signature-Version</code>
         * manifest attribute used when signing JAR files.
         * @see <a href="../../../../technotes/guides/jar/jar.html#JAR Manifest">
         *      Manifest and Signature Specification</a>
         */
        public static final Name SIGNATURE_VERSION = new Name("Signature-Version");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Content-Type</code>
         * manifest attribute.
         */
        public static final Name CONTENT_TYPE = new Name("Content-Type");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Class-Path</code>
         * manifest attribute. Bundled extensions can use this attribute
         * to find other JAR files containing needed classes.
         * @see <a href="../../../../technotes/guides/extensions/spec.html#bundled">
         *      Extensions Specification</a>
         */
        public static final Name CLASS_PATH = new Name("Class-Path");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Main-Class</code> manifest
         * attribute used for launching applications packaged in JAR files.
         * The <code>Main-Class</code> attribute is used in conjunction
         * with the <code>-jar</code> command-line option of the
         * <tt>java</tt> application launcher.
         */
        public static final Name MAIN_CLASS = new Name("Main-Class");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Sealed</code> manifest attribute
         * used for sealing.
         * @see <a href="../../../../technotes/guides/extensions/spec.html#sealing">
         *      Extension Sealing</a>
         */
        public static final Name SEALED = new Name("Sealed");

       /** {@collect.stats}
         * <code>Name</code> object for <code>Extension-List</code> manifest attribute
         * used for declaring dependencies on installed extensions.
         * @see <a href="../../../../technotes/guides/extensions/spec.html#dependency">
         *      Installed extension dependency</a>
         */
        public static final Name EXTENSION_LIST = new Name("Extension-List");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Extension-Name</code> manifest attribute
         * used for declaring dependencies on installed extensions.
         * @see <a href="../../../../technotes/guides/extensions/spec.html#dependency">
         *      Installed extension dependency</a>
         */
        public static final Name EXTENSION_NAME = new Name("Extension-Name");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Extension-Name</code> manifest attribute
         * used for declaring dependencies on installed extensions.
         * @see <a href="../../../../technotes/guides/extensions/spec.html#dependency">
         *      Installed extension dependency</a>
         */
        public static final Name EXTENSION_INSTALLATION = new Name("Extension-Installation");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Implementation-Title</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_TITLE = new Name("Implementation-Title");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Implementation-Version</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_VERSION = new Name("Implementation-Version");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Implementation-Vendor</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_VENDOR = new Name("Implementation-Vendor");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Implementation-Vendor-Id</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_VENDOR_ID = new Name("Implementation-Vendor-Id");

       /** {@collect.stats}
         * <code>Name</code> object for <code>Implementation-Vendor-URL</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_URL = new Name("Implementation-URL");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Specification-Title</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name SPECIFICATION_TITLE = new Name("Specification-Title");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Specification-Version</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name SPECIFICATION_VERSION = new Name("Specification-Version");

        /** {@collect.stats}
         * <code>Name</code> object for <code>Specification-Vendor</code>
         * manifest attribute used for package versioning.
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name SPECIFICATION_VENDOR = new Name("Specification-Vendor");
    }
}

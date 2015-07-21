/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.net;

/** {@collect.stats} 
 * {@description.open}
 * This class represents a Network Interface address. In short it's an
 * IP address, a subnet mask and a broadcast address when the address is
 * an IPv4 one. An IP address and a network prefix length in the case
 * of IPv6 address.
 * {@description.close}
 *
 * @see java.net.NetworkInterface
 * @since 1.6
 */
public class InterfaceAddress {
    private InetAddress address = null;
    private Inet4Address broadcast = null;
    private short        maskLength = 0;

    /*
     * Package private constructor. Can't be built directly, instances are
     * obtained through the NetworkInterface class.
     */
    InterfaceAddress() {
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns an <code>InetAddress</code> for this address.
     * {@description.close}
     *
     * @return the {@code InetAddress} for this address.
     */
    public InetAddress getAddress() {
        return address;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns an <code>InetAddress</code> for the brodcast address
     * for this InterfaceAddress.
     * <p>
     * Only IPv4 networks have broadcast address therefore, in the case
     * of an IPv6 network, <code>null</code> will be returned.
     * {@description.close}
     *
     * @return the {@code InetAddress} representing the broadcast
     *         address or {@code null} if there is no broadcast address.
     */
    public InetAddress getBroadcast() {
        return broadcast;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the network prefix length for this address. This is also known
     * as the subnet mask in the context of IPv4 addresses.
     * Typical IPv4 values would be 8 (255.0.0.0), 16 (255.255.0.0)
     * or 24 (255.255.255.0). <p>
     * Typical IPv6 values would be 128 (::1/128) or 10 (fe80::203:baff:fe27:1243/10)
     * {@description.close}
     *
     * @return a {@code short} representing the prefix length for the
     *         subnet of that address.
     */
     public short getNetworkPrefixLength() {
        return maskLength;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and it represents the same interface address as
     * this object.
     * <p>
     * Two instances of {@code InterfaceAddress} represent the same
     * address if the InetAddress, the prefix length and the broadcast are
     * the same for both.
     * {@description.close}
     *
     * @param   obj   the object to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see     java.net.InterfaceAddress#hashCode()
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof InterfaceAddress)) {
            return false;
        }
        InterfaceAddress cmp = (InterfaceAddress) obj;
        if ( !(address == null ? cmp.address == null : address.equals(cmp.address)) )
            return false;
        if ( !(broadcast  == null ? cmp.broadcast == null : broadcast.equals(cmp.broadcast)) )
            return false;
        if (maskLength != cmp.maskLength)
            return false;
        return true;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns a hashcode for this Interface address.
     * {@description.close}
     *
     * @return  a hash code value for this Interface address.
     */
    public int hashCode() {
        return address.hashCode() + ((broadcast != null) ? broadcast.hashCode() : 0) + maskLength;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Converts this Interface address to a <code>String</code>. The
     * string returned is of the form: InetAddress / prefix length [ broadcast address ].
     * {@description.close}
     *
     * @return  a string representation of this Interface address.
     */
    public String toString() {
        return address + "/" + maskLength + " [" + broadcast + "]";
    }

}
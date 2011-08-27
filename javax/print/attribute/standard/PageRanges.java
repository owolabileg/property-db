/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.attribute.standard;

import javax.print.attribute.Attribute;
import javax.print.attribute.SetOfIntegerSyntax;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/** {@collect.stats}
 * Class PageRanges is a printing attribute class, a set of integers, that
 * identifies the range(s) of print-stream pages that the Printer object uses
 * for each copy of each document which are to be printed. Nothing is printed
 * for any pages identified that do not exist in the document(s). The attribute
 * is associated with <I>print-stream</I> pages, not application-numbered pages
 * (for example, the page numbers found in the headers and or footers for
 * certain word processing applications).
 * <P>
 * In most cases, the exact pages to be printed will be generated by a device
 * driver and this attribute would not be required. However, when printing an
 * archived document which has already been formatted, the end user may elect to
 * print just a subset of the pages contained in the document. In this case, if
 * a page range of <CODE>"<I>n</I>-<I>m</I>"</CODE> is specified, the first page
 * to be printed will be page <I>n.</I> All subsequent pages of the document
 * will be printed through and including page <I>m.</I>
 * <P>
 * If a PageRanges attribute is not specified for a print job, all pages of
 * the document will be printed. In other words, the default value for the
 * PageRanges attribute is always <CODE>{{1, Integer.MAX_VALUE}}</CODE>.
 * <P>
 * The effect of a PageRanges attribute on a multidoc print job (a job with
 * multiple documents) depends on whether all the docs have the same page ranges
 * specified or whether different docs have different page ranges specified, and
 * on the (perhaps defaulted) value of the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} attribute.
 * <UL>
 * <LI>
 * If all the docs have the same page ranges specified, then any value of {@link
 * MultipleDocumentHandling MultipleDocumentHandling} makes sense, and the
 * printer's processing depends on the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} value:
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- All the input docs will be combined together into one
 * output document. The specified page ranges of that output document will be
 * printed.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- All the input docs will be combined together
 * into one output document, and the first impression of each input doc will
 * always start on a new media sheet. The specified page ranges of that output
 * document will be printed.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- For each separate input doc, the
 * specified page ranges will be printed.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- For each separate input doc, the
 * specified page ranges will be printed.
 * </UL>
 * <UL>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- For each separate input doc, its own
 * specified page ranges will be printed..
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- For each separate input doc, its own
 * specified page ranges will be printed..
 * </UL>
 * </UL>
 * <P>
 * <B>IPP Compatibility:</B> The PageRanges attribute's canonical array form
 * gives the lower and upper bound for each range of pages to be included in
 * and IPP "page-ranges" attribute. See class {@link
 * javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax} for an
 * explanation of canonical array form. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public final class PageRanges   extends SetOfIntegerSyntax
        implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 8639895197656148392L;


    /** {@collect.stats}
     * Construct a new page ranges attribute with the given members. The
     * members are specified in "array form;" see class {@link
     * javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax} for an
     * explanation of array form.
     *
     * @param  members  Set members in array form.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>members</CODE> is null or
     *     any element of <CODE>members</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if any element of
     *   <CODE>members</CODE> is not a length-one or length-two array. Also
     *     thrown if <CODE>members</CODE> is a zero-length array or if any
     *     member of the set is less than 1.
     */
    public PageRanges(int[][] members) {
        super (members);
        if (members == null) {
            throw new NullPointerException("members is null");
        }
        myPageRanges();
    }
    /** {@collect.stats}
     * Construct a new  page ranges attribute with the given members in
     * string form.
     * See class {@link javax.print.attribute.SetOfIntegerSyntax
     * SetOfIntegerSyntax}
     * for explanation of the syntax.
     *
     * @param  members  Set members in string form.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>members</CODE> is null or
     *     any element of <CODE>members</CODE> is null.
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>members</CODE> does not
     *    obey  the proper syntax.  Also
     *     thrown if the constructed set-of-integer is a
     *     zero-length array or if any
     *     member of the set is less than 1.
     */
    public PageRanges(String members) {
        super(members);
        if (members == null) {
            throw new NullPointerException("members is null");
        }
        myPageRanges();
    }

    private void myPageRanges() {
        int[][] myMembers = getMembers();
        int n = myMembers.length;
        if (n == 0) {
            throw new IllegalArgumentException("members is zero-length");
        }
        int i;
        for (i = 0; i < n; ++ i) {
          if (myMembers[i][0] < 1) {
            throw new IllegalArgumentException("Page value < 1 specified");
          }
        }
    }

    /** {@collect.stats}
     * Construct a new page ranges attribute containing a single integer. That
     * is, only the one page is to be printed.
     *
     * @param  member  Set member.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>member</CODE> is less than
     *     1.
     */
    public PageRanges(int member) {
        super (member);
        if (member < 1) {
            throw new IllegalArgumentException("Page value < 1 specified");
        }
    }

    /** {@collect.stats}
     * Construct a new page ranges attribute containing a single range of
     * integers. That is, only those pages in the one range are to be printed.
     *
     * @param  lowerBound  Lower bound of the range.
     * @param  upperBound  Upper bound of the range.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if a null range is specified or if a
     *     non-null range is specified with <CODE>lowerBound</CODE> less than
     *     1.
     */
    public PageRanges(int lowerBound, int upperBound) {
        super (lowerBound, upperBound);
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Null range specified");
        } else if (lowerBound < 1) {
            throw new IllegalArgumentException("Page value < 1 specified");
        }
    }

    /** {@collect.stats}
     * Returns whether this page ranges attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PageRanges.
     * <LI>
     * This page ranges attribute's members and <CODE>object</CODE>'s members
     * are the same.
     * </OL>
     *
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this page ranges
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof PageRanges);
    }

    /** {@collect.stats}
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PageRanges, the category is class PageRanges itself.
     *
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PageRanges.class;
    }

    /** {@collect.stats}
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PageRanges, the category name is <CODE>"page-ranges"</CODE>.
     *
     * @return  Attribute category name.
     */
    public final String getName() {
        return "page-ranges";
    }

}
